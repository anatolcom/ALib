/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import java.util.EventObject;
/**
 * Класс описывающий события сессий.<br />
 * <br />
 * <b>Константы:</b>
 * <ul>
 * <li>PROCESSED - сессия в процессе работы
 * <li>CLOSED - сессия закрыта
 * <li>CANCELED - сессия закрыта из-за вызова отмены
 * <li>TIMEOUT - сессия закрыта из-зи превышения времени бездействия
 * </ul>
 * 
 * @author Anatol
 * @version 0.1.0.0
 */
public class ASessionEvent extends EventObject
{
//---------------------------------------------------------------------------
/**
 * Сессия в процессе работы
 */
 public static final int PROCESSED   =0x01;
/**
 * Сессия закрыта
 */
 public static final int CLOSED      =0x02;
/**
 * Сессия закрыта из-за вызова отмены
 */
 public static final int CANCELED    =0x04;
/**
 * Сессия закрыта из-зи превышения времени бездействия
 */
 public static final int TIMEOUT     =0x03;
//---------------------------------------------------------------------------
 private ASession session;
 private int sessionState;
//---------------------------------------------------------------------------
 public ASessionEvent(Object src,ASession session,int sessionState)
 {
  super(src);
  this.session=session;
  this.sessionState=sessionState;
 }
//---------------------------------------------------------------------------
 public ASession getSession()
 {
  return session;
 }
//---------------------------------------------------------------------------
 public int getState()
 {
  return sessionState;
 }
//---------------------------------------------------------------------------
 public String getStateName()
 {
  switch(sessionState)
  {
   case PROCESSED:return "Processed";
   case CLOSED:return "Closed";
   case CANCELED:return "Canceled";
   case TIMEOUT:return "Timeout";
  }
  return "";
 }
//---------------------------------------------------------------------------
 @Override
 public Object getSource()
 {
  return super.getSource();
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "ASessionEvent{"+session.getId()+" "+session.getDataInfo().getName()+"}";
 }
//---------------------------------------------------------------------------
}
