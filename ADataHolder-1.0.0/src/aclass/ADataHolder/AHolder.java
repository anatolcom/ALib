/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Anatol
 * @version 0.1.0.0
 */
public class AHolder extends AHolderInfo
{
//---------------------------------------------------------------------------
// public final byte CREATE_DATA = 0x01;
// public final byte OPEN_DATA   = 0x02;
//---------------------------------------------------------------------------
 private String Separator="/";//System.getProperty("file.separator");
//---------------------------------------------------------------------------
 public AHolder(AHolderInfo value)throws AException
 {
  super(value);
 }
//---------------------------------------------------------------------------
 private String getNowName()
 {
  Date date=Calendar.getInstance().getTime();
  SimpleDateFormat dateformat=new SimpleDateFormat("HHmmssSS");
  String nowName=dateformat.format(date);
  nowName=nowName.substring(0,8);
  return nowName+".dat";
 }
//---------------------------------------------------------------------------
 private String getNowDir() throws AException
 {
  Date date=Calendar.getInstance().getTime();
  SimpleDateFormat dateformat=new SimpleDateFormat("yyyy"+Separator+"MM"+Separator+"dd");
  String dirName=dateformat.format(date);
  File dir=new File(addres+Separator+dirName);
  if(dir.exists())return dirName;
  if(!dir.mkdirs())throw new AException("mkdir \""+addres+Separator+dirName+"\" ERROR");
  return dirName;
 }
//---------------------------------------------------------------------------
 private void deleteEmptyDir(String dirName)
 {
  sendWarning("deleteEmptyDir","This function is not developed",null);
 }
//---------------------------------------------------------------------------
 private File createFile(String fileName) throws AException
 {
  File file=new File(addres+Separator+fileName);
  if(file.exists())throw new AException("File "+addres+Separator+fileName+" alredy exists");
  try
  {
   if(!file.createNewFile())throw new AException("File "+addres+Separator+fileName+" not created");
  }
  catch(IOException ex)
  {
   throw new AException(ex);
  }
  sendInfo("createFile","Create "+addres+Separator+fileName,null);
  return file;
 }
//---------------------------------------------------------------------------
 private File openFile(String fileName) throws AException
 {
  sendWarning("openFile","This function is not developed",null);
  File file=new File(addres+Separator+fileName);
  if(!file.exists())throw new AException("File "+addres+Separator+fileName+" not exists");
  sendInfo("openFile","Open "+addres+Separator+fileName,null);
  return file;
 }
//---------------------------------------------------------------------------
 private void deleteFile(String fileName) throws AException
 {
  File file=new File(addres+Separator+fileName);
  if(!file.exists())throw new AException("File "+addres+Separator+fileName+" not exists");
  sendInfo("deleteFile","Delete "+addres+Separator+fileName,null);
  if(!file.delete())
  {
   sendInfo("deleteFile","можно вести список неудалённых файлов",null);
   throw new AException("File "+addres+Separator+fileName+"don't deleted");
  }
 }
//---------------------------------------------------------------------------
/**
 * Метод создаёт для записи новое хранилище данных
 * соответсвующее информации из dataInfo 
 * и возвращает на него ссылку.<br />
 * @param dataInfo информация о данных.
 * @return хранилище данных готовое для записи.
 * @throws AException в случае ошибки. 
 */
 public AData createData(ADataInfo dataInfo) throws AException
 {
  if(dataInfo==null)throw new AException("dataInfo=null");
//  InputStream fileIn;
  OutputStream fileOut;
  String dirName=getNowDir();
  String fileName=dirName+Separator+getNowName();
  try
  {
   File file=createFile(fileName);
//   fileIn=new FileInputStream(file);
//   if(fileIn==null)sendError("createData","fileIn=null",null);
   fileOut=new FileOutputStream(file);
   if(fileOut==null)throw new AException("fileOut=null");
//   if(fileOut==null)sendError("createData","fileOut=null",null);
  }
  catch(Exception ex)
  {
   deleteEmptyDir(dirName);
   throw new AException("Create file Exception: "+ex.getMessage(),ex);
  }
  AData data;
  try
  {
   data=new AData(dataInfo,null,fileOut);
   data.setIdHolder(id);
   data.setFileName(fileName);
  }
  catch(Exception ex)
  {
   deleteEmptyDir(dirName);
   throw new AException("Create data Exception: "+ex.getMessage(),ex);
  }
  return data;
 }
//---------------------------------------------------------------------------
/**
 * Метод открывает для чтения хранилище данных 
 * соответсвующее информации из dataInfo 
 * и возвращает на него ссылку.<br />
 * @param dataInfo информация о данных.
 * @return хранилище данных готовое для чтения.
 * @throws AException в случае ошибки. 
 */
 public AData openData(ADataInfo dataInfo) throws AException
 {
  if(dataInfo==null)throw new AException("dataInfo=null");
  InputStream fileIn;
//  OutputStream fileOut;
  String fileName=dataInfo.getFileName();
  try
  {
   File file=openFile(fileName);
   fileIn=new FileInputStream(file);
   if(fileIn==null)sendError("openData","fileIn=null",null);
//   fileOut=new FileOutputStream(file);
//   if(fileOut==null)sendError("openData","fileOut=null",null);
  }
  catch(Exception ex)
  {
   throw new AException("Open file Exception: "+ex.getMessage(),ex);
  }
  AData data;
  try
  {
   data=new AData(dataInfo,fileIn,null);
   data.setIdHolder(id);
   data.setFileName(fileName);
  }
  catch(Exception ex)
  {
   throw new AException("Data not opened "+ex.getMessage(),ex);
  }
  return data;
 }
//---------------------------------------------------------------------------
/**
 * Метод удаляет хранилище данных 
 * соответсвующее информации из dataInfo.<br />
 * @param dataInfo информация о данных.
 * @throws AException в случае ошибки. 
 */
 public void deleteData(ADataInfo dataInfo) throws AException
 {
  if(dataInfo==null)throw new AException("dataInfo=null");
  deleteFile(dataInfo.getFileName());
 }
//---------------------------------------------------------------------------
}
