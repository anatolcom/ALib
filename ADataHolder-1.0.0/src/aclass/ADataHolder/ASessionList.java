/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AClass;
import aclass.AException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Класс управляющий списком сессий {@link ASession}.<br />
 * @author Anatol
 * @version 0.1.0.0
 */
public class ASessionList extends AClass
{
//---------------------------------------------------------------------------
/**
 * Класс описывающий события таймера
 */
 private class TimerEvent extends EventObject
 {
  private int number=0;
  public TimerEvent(Object src,int number)
  {
   super(src);
   this.number=number;
  }
  public int getNumber()
  {
   return number;
  }
  @Override
  public Object getSource()
  {
   return super.getSource();
  }
  @Override
  public String toString()
  {
   return "TimerEvent "+number;
  }
 }
//---------------------------------------------------------------------------
/**
 * Интерфейс слушателя событий таймера
 */
 private interface TimerListener extends EventListener
 {
  public void checkTimeout(TimerEvent evt) throws AException;
 }
//---------------------------------------------------------------------------
/**
 * Класс описывающий таймер, который каждую секунду вызывает событие {@link TimerEvent}.<br />
 */
 private class Timer extends AClass implements Runnable
 {
  private final int DEFAULT_INDEDX=0;
  private TimerListener listener=null;
  private boolean runed=true;
  private int index=DEFAULT_INDEDX;
  public void stop()
  {
   runed=false;
  }
  public void reset()
  {
   index=DEFAULT_INDEDX;
  }
  @Override
  public void run()
  {
   sendInfo("run","Timer thread START",null);
   try
   {
    while(runed)
    {
     Thread.sleep(1000);
      //     sendInfo("run","Timer thread doEvent",index);
     doEvent(index);
     index++;
    }
    runed=true;
   }
   catch(InterruptedException ex)
   {
    sendError("run","Timer thread interrupted Exception "+ex,null);
   }
   sendInfo("run","Timer thread STOP",null);
  }
  protected void doEvent(int number)
  {
   if(listener==null) return;
   try
   {
    listener.checkTimeout(new TimerEvent(this,number));
   }
   catch(AException ex)
   {
    sendError("doEvent",ex.getMessage(),number);
   }
  }
  public TimerListener getListeners()
  {
   return listener;
  }
  public void setListener(TimerListener listener)
  {
   this.listener=listener;
  }
 }
//---------------------------------------------------------------------------
/**
 * Максимальный размер данных для {@link ADataPart} по умолчанию.
 */
 public static final int DEFAULT_MAX_DATA_PART_SIZE=1024*256;
//---------------------------------------------------------------------------
/**
 * Значение максимального времени бездействия по умолчанию.
 */
 public final int DEFAULT_MAX_IDLE_TIME =32;
//---------------------------------------------------------------------------
 private Timer timer=new Timer();
 private Thread timerThreade=null;
//---------------------------------------------------------------------------
 private long currentIdSession=0;
 private int maxDataPartSize=DEFAULT_MAX_DATA_PART_SIZE;//???
 private int maxIdleTime=DEFAULT_MAX_IDLE_TIME;//???
//---------------------------------------------------------------------------
 private ArrayList<ASession> list=new ArrayList();
//---------------------------------------------------------------------------
 private ASessionListener listener=null;
//---------------------------------------------------------------------------
/**
 * Конструктор.
 */
 public ASessionList()
 {
  timer.setParent(this);
  timer.setListener(timerListener);
 }
//---------------------------------------------------------------------------
 private TimerListener timerListener=new TimerListener()
 {
  @Override
  public void checkTimeout(TimerEvent evt) throws AException
  {
   for(int q=0;q<list.size();q++)
   {
    ASession session=list.get(q);
    int remainingIdleTime=session.checkTimeout();
//    sendInfo("checkTimeout","RemainingIdleTime of "+session.toString(),remainingIdleTime);
    if(remainingIdleTime==0)closeSession(session.getId(),ASessionEvent.TIMEOUT);
   }
  }
 };
//---------------------------------------------------------------------------
 private void startTimer()
 {
  if(timerThreade!=null)return;
  timerThreade=new Thread(timer,"MyTimer");
  timerThreade.start();
 }
//---------------------------------------------------------------------------
 private void stopTimer()
 {
  timer.stop();
  timerThreade=null;
 }
//---------------------------------------------------------------------------
 private long getCurrentIdSession()
 {
  return currentIdSession;
 }
//---------------------------------------------------------------------------
 private long getNewIdSession()
 {
  if(++currentIdSession==0x0FFFFFFF)currentIdSession=0;
  return currentIdSession;
 }
//---------------------------------------------------------------------------
 private int getIndex(long idSession)
 {
  for(int q=0;q<list.size();q++)if(list.get(q).getId()==idSession)return q;
  return -1;
 }
//---------------------------------------------------------------------------
 private ASession doOpenSession(AData data) throws AException
 {
  ASession session=new ASession(data);
  session.setId(getNewIdSession());
  session.setMaxDataPartSize(maxDataPartSize);
  session.setMaxIdleTime(maxIdleTime);
  list.add(session);
  if(list.size()==1)startTimer();
  doOpenSessionEvent(session,ASessionEvent.PROCESSED);
  return session;
 }
//---------------------------------------------------------------------------
 private ASession doCloseSession(int index,int stateSession) throws AException
 {
  if(!inRange(index,0,list.size()))throw new AException("index out of range 0-"+list.size(),index);
  try
  {
   ASession session=list.get(index);
   list.remove(index);
   if(list.isEmpty())stopTimer();
   session.close();
   doCloseSessionEvent(session,stateSession);
   return session;
  }
  catch(Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
/**
 * Метод открывает новую сессию на основе данных data,
 * заносит её в список и возврвщает на неё ссылку.<br />
 * @param data ссылка на данные типа {@link AData}.
 * @return ссылку на открытую сессию.
 * @throws AException в случае ошибки. 
 * @see ASession
 * @see ASessionEvent
 * @see AData
 */
 public ASession openSession(AData data) throws AException
 {
  if(data==null)throw new AException("data=null");
  return doOpenSession(data);
 }
//---------------------------------------------------------------------------
/**
 * Метод закрывает сессию с идентияикатором idSession
 * и состоянием stateSession, описанном в {@link ASessionEvent}.<br />
 * @param idSession идентияикатор сессии
 * @param stateSession состояние сесии
 * @return ссылку на закрытую сессию.
 * @throws AException в случае ошибки. 
 * @see ASession
 * @see ASessionEvent
 */
 public ASession closeSession(long idSession,int stateSession) throws AException
 {
  int index=getIndex(idSession);
  if(index==-1)throw new AException("Session with id "+idSession+" not found");
  return doCloseSession(index,stateSession);
 }
//---------------------------------------------------------------------------
/**
 * Метод сверяет MD5 для сессии с идентификатором idSession и возвращает true в случае идентичности.<br />
 * @param idSession идентификатор сессии.
 * @param MD5 значение MD5 в кодировке Base64.
 * @return true - MD5 идентичны,<br />
 *         false - MD5 различны или в случае ошибки.
 * @throws AException в случае ошибки. 
 */
 public boolean checkSession(long idSession,String MD5) throws AException
 {
  ASession session=getSession(idSession);
  if(!session.check(MD5))return false;
  return true;
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает ссылку на сессию по её идентификатору idSession.<br />
 * @param idSession идентификатор сессии.
 * @return ссылка на сессию.
 * @throws AException в случае ошибки. 
 */
 public ASession getSession(long idSession) throws AException
 {
  int index=getIndex(idSession);
  if(index==-1)throw new AException("Session with id "+idSession+" not found");
  return list.get(index);
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает максимальный размер данных для {@link ADataPart}.<br />
 * @return максимальный размер данных.
 */
 public int getMaxDataPartSize(){return maxDataPartSize;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает максимальный размер данных для {@link ADataPart} равным value.<br />
 * @param value максимальный размер данных (не менее 1024).
 * @throws AException в случае ошибки. 
 */
 public void setMaxDataPartSize(int value) throws AException
 {
  if(value<1024)throw new AException("value<1024",value);
  maxDataPartSize=value;
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает максимальный размер данных для {@link ADataPart}.<br />
 * @return максимальный размер данных.
 */
 public int getMaxIdleTime(){return maxIdleTime;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает максимальный размер данных для {@link ADataPart} равным value.<br />
 * @param value максимальный размер данных (не менее 1024).
 * @throws AException в случае ошибки. 
 */
 public void setMaxIdleTime(int value) throws AException
 {
  if(value<1)throw new AException("value<1",value);
  maxIdleTime=value;
 }
//---------------------------------------------------------------------------
/**
 * Метод закрывает все сессии.<br />
 * @throws AException в случае ошибки. 
 */
 public void clear() throws AException
 {
  while(!list.isEmpty())doCloseSession(0,ASessionEvent.CANCELED);
 }
//---------------------------------------------------------------------------
 private void doOpenSessionEvent(ASession session,int sessionState) throws AException
 {
  if(listener==null) return;
  listener.openSession(new ASessionEvent(this,session,sessionState));
 }
//---------------------------------------------------------------------------
 private void doCloseSessionEvent(ASession session,int sessionState) throws AException
 {
  if(listener==null) return;
  listener.closeSession(new ASessionEvent(this,session,sessionState));
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает ссылку на слушателя событий.
 * @return ссылка на слушателя событий.
 * @see ASessionEvent
 * @see ASessionListener
 */
 public ASessionListener getListeners(){return listener;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает ссылку на слушателя событий равную listener.
 * @param listener ссылка на слушателя событий.
 * @see ASessionEvent
 * @see ASessionListener
 */
 public void setListener(ASessionListener listener){this.listener=listener;}
//---------------------------------------------------------------------------
}
