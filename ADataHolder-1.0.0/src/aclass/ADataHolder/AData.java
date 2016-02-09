/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Класс описывающий поведение данных
 * @author Anatol
 * @version 0.1.0.0
 */
public class AData extends ADataInfo implements Closeable
{
//---------------------------------------------------------------------------
 private InputStream inputStream=null;
 private OutputStream outputStream=null;
//---------------------------------------------------------------------------
/**
 * Конструктор.<br />
 * @param dataInfo информация о данных.
 * @param in поток чтения данных.
 * @param out поток записи данных.
 * @throws AException генерируется в случае невозможности создать класс.
 */
 public AData(ADataInfo dataInfo,InputStream in,OutputStream out) throws AException
 {
  super(dataInfo);
  this.inputStream=in;
  this.outputStream=out;
 }
//---------------------------------------------------------------------------
/**
 * Метод закрывает потоки чтения и записи данных.<br />
 * Обязателен для вызова.<br />
 * @throws IOException генерируется в случае ошибки.
 */
 @Override
 public void close() throws IOException
 {
  if(inputStream!=null)
  {
   inputStream.close();
   inputStream=null;
   sendInfo("close","Input stream have been closed",null);
  }
  if(outputStream!=null)
  {
   outputStream.close();
   outputStream=null;
   sendInfo("close","Output stream have been closed",null);
  }
 }
//---------------------------------------------------------------------------
///**
// * Метод возвращает ссылку на поток чтения данных.<br />
// * @return ссылка на поток чтения данных.
// */ 
// public InputStream getIn()//read data         
// {
//  return in;
// }
//---------------------------------------------------------------------------
///**
// * Метод возвращает ссылку на записи чтения данных.<br />
// * @return ссылка на поток записи данных.
// */ 
// public OutputStream getOut()//write data
// {
//  return out;
// }
//---------------------------------------------------------------------------
/**
 * Метод считывает данные в масив value и возвращает количество считанных байт.<br />
 * @param value ссылка на массив данных для записи.
 * @return количество считанных байт.
 * @throws IOException генерируется в случае ошибки.
 */
 public int read(byte[] value) throws IOException
 {
  if(!testNull(value,"read","value"))throw new IOException("Input stream not opened");
  return inputStream.read(value);
 }
//---------------------------------------------------------------------------
/**
 * Метод записывает данные из масива value.<br />
 * @param value ссылка на массив данных для чтения.
 * @throws IOException генерируется в случае ошибки.
 */
 public void write(byte[] value) throws IOException
 {
  if(!testNull(value,"write","value"))throw new IOException("Output stream not opened");
  outputStream.write(value);
 }
//---------------------------------------------------------------------------
}
