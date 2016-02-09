/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AException;
import java.io.Closeable;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Класс описывающий поведение сессии чтения/записи данных типа {@link AData}.<br />
 * @author Anatol
 * @version 0.1.0.0
 */
public class ASession extends ASessionInfo implements Closeable
{
//---------------------------------------------------------------------------
 private AData data; //экземпляр данных
 private boolean successfull=false; //значение успешности заверщения сессии
 private boolean closed=false; //значение закрытости сессии
//---------------------------------------------------------------------------
 private MessageDigest digest; //экземпляр класса, вычисляющего MD5
//---------------------------------------------------------------------------
 private int indexDataPart=0;
 private int remainingIdleTime=FMaxIdleTime; //значение оставшегося времени бездействия
//---------------------------------------------------------------------------
/**
 * Конструктор.<br />
 * @param data Экземпляр класса AData, данные которого будут скопированны.
 * @throws Exception генерируется если data=null, 
 *                   или если алгоритм MD5 ненайден.
 */
 public ASession(AData data) throws AException
 {
  super();
  if(data==null)throw new AException("data=null");
  this.data=data;
  try
  {
   digest=MessageDigest.getInstance("MD5");
  }
  catch(NoSuchAlgorithmException ex)
  {
   throw new AException("No such algorithm MD5",ex);
  }
 }
//---------------------------------------------------------------------------
///**
// * Метод возвращает значение оставшегося времени бездействия.<br />
// * @return значение в секундах.
// */
// public int getRemainingIdleTime()
// {
//  return remainingIdleTime;
// }
//---------------------------------------------------------------------------
/**
 * Метод возвращает ADataInfo.<br />
 * @return - ссылку на ADataInfo.
 */
 public ADataInfo getDataInfo(){return data;}
//---------------------------------------------------------------------------
/**
 * Метод считывает партию данных с индексом index и возвращает их.<br />
 * @param index - индекс текущей партии.
 * @return экземпляр ADataPart,<br />
 *         null в случае ошибки.
 */
 public ADataPart readDataPart(int index) throws AException
 {
  refreshIdleTime();
  if(indexDataPart!=index)throw new AException("index is not "+indexDataPart,index);
//  {
//   sendError("readDataPart","index is not "+indexDataPart,index);
//   return null;
//  }
  try
  {
   byte bufRaed[]=new byte[getMaxDataPartSize()];
   int size=data.read(bufRaed);
   if(size<=0)throw new AException("read ERROR",size);
//   {
//    sendError("readDataPart","read ERROR",size);
//    return null;
//   }
   byte buf[]=new byte[size];
   System.arraycopy(bufRaed,0,buf,0,buf.length);
   digest.update(buf);
   ADataPart dataPart=new ADataPart();
   dataPart.setIdSession(getId());
   dataPart.setIndex(index);
   dataPart.setData(new sun.misc.BASE64Encoder().encode(buf));
   dataPart.setSize(buf.length);
   indexDataPart++;
   return dataPart;
  }
  catch(Exception ex)
  {
//   sendError("readDataPart","Exception "+ex.getMessage(),indexDataPart);
   throw new AException(ex,indexDataPart);
//   return null;
  }
 }
//---------------------------------------------------------------------------
/**
 * Метод записывает партию данных dataPart и возвращает индекс партии.<br />
 * @param dataPart - ссылка на партию данных ADataPart.
 * @return индекс партии,<br />
 *         -1 в случае ошибки.
 */
 public int writeDataPart(ADataPart dataPart) throws AException
 {
  refreshIdleTime();
  if(indexDataPart!=dataPart.getIndex())throw new AException("Index of dataPart is not "+indexDataPart,dataPart.getIndex());
//  {
//   sendError("writeDataPart","Index of dataPart is not "+indexDataPart,dataPart.getIndex());
//   return -1;
//  }
  try
  {
   byte buf[]=new sun.misc.BASE64Decoder().decodeBuffer(dataPart.getData());
   data.write(buf);
   digest.update(buf);
   return indexDataPart++;
  }
  catch(Exception ex)
  {
   throw new AException(ex,indexDataPart);
//   sendError("writeDataPart","Exception "+ex.getMessage(),indexDataPart);
//   return -1;
  }
 }
//---------------------------------------------------------------------------
/**
 * Метод сверяет MD5 и возвращает true в случае идентичности.
 * @param MD5 - значение MD5 в кодировке Base64.<br />
 * @return true - MD5 идентичны,<br />
 *         false - MD5 различны.
 */
 public boolean check(String MD5) throws AException
 {
  refreshIdleTime();
  successfull=false;
  try
  {
   byte md5a[]=digest.digest();
   byte md5b[]=new sun.misc.BASE64Decoder().decodeBuffer(MD5);
   if(md5a.length!=md5b.length)return false;
   for(int q=0;q<md5a.length;q++)if(md5a[q]!=md5b[q])return false;
  }
  catch(IOException ex)
  {
   throw new AException(ex,indexDataPart);
//   sendError("check","Exception "+ex.getMessage(),indexDataPart);
//   return false;
  }
  data.setMD5(MD5);
  sendInfo("check","MD5 "+MD5+" true",null);
  successfull=true;
  return true;
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает true если время бездействия вышло.<br />
 * Данный метод необхадимо вызывать с интервалом в секунду.<br />
 * @return 0 - время бездействия вышло,<br />
 *         оставшееся время бездействия.
 */
 public int checkTimeout()
 {
  if(remainingIdleTime==0)return 0;
  return remainingIdleTime--;
 }
//---------------------------------------------------------------------------
/**
 * Метод устанавливает оставшееся время бездействия равным максимальному.<br />
 */
 public void refreshIdleTime(){remainingIdleTime=FMaxIdleTime;}
//---------------------------------------------------------------------------
/**
 * Метод закрывает сессию.<br />
 * Обязателен для вызова.<br />
 * @throws IOException генерируется в случае ошибки.
 */
 @Override
 public void close() throws IOException
 {
  data.close();
  closed=true;
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает true если сессия закрыта.<br />
 * @return true - сессия закрыта,<br />
 *         false - сессия не закрыта.
 */
 public boolean isClosed(){return closed;}
//---------------------------------------------------------------------------
/**
 * Метод возвращает true если сессия выполнена полностью.<br />
 * @return true - сессия выполнена полностью,<br />
 *         false - сессия не выполнена.
 */
 public boolean isSuccessfull(){return successfull;}
//---------------------------------------------------------------------------
/**
 * Метод возвращает строковое описание сессии.<br />
 * @return строковое описание сессии.
 */
 @Override
 public String toString(){return getDataInfo().getName();}
//---------------------------------------------------------------------------
}
