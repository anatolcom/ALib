/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Класс описывающий ведение Log.
 *
 * @author Anatol
 * @version 0.1.0.3
 */
public class ALog extends AClass
{
//---------------------------------------------------------------------------
 private static ALog instance = null;
//---------------------------------------------------------------------------
 private ALog()
 {
 }
//---------------------------------------------------------------------------
 public static ALog getInstance()
 {
  if (instance == null) instance = new ALog();
  return instance;
 }
//---------------------------------------------------------------------------
 public class ALogItem
 {
  public final String type;
  public final String text;
  public final String date;
  public ALogItem(String type, String text, String date)
  {
   this.type = type;
   this.text = text;
   this.date = date;
  }
  @Override
  public String toString()
  {
   return type + " " + text;
  }
  public String toStringWithDate()
  {
   return type + " " + date + " " + text;
  }
 }
//---------------------------------------------------------------------------
 private final ArrayList<ALogItem> FLogItemList = new ArrayList();
//---------------------------------------------------------------------------
 private synchronized void trimItemList()
 {
  if (FItemLimit == 0) return;
  while (FLogItemList.size() > FItemLimit) FLogItemList.remove(0);
 }
//---------------------------------------------------------------------------
 private int FItemLimit = 1024;
 private String FPath = "";
 private String FAutoSaveFileName = "";
//---------------------------------------------------------------------------
 public synchronized int count()
 {
  return FLogItemList.size();
 }
//---------------------------------------------------------------------------
 public synchronized void addInfo(String text) throws Exception
 {
  if (text == null) throw new Exception("text=null");
  ALogItem item = new ALogItem("I", text, getCurrentDateStd());
  FLogItemList.add(item);
  trimItemList();
  System.out.println(item.toString());
  updated();
 }
//---------------------------------------------------------------------------
 public synchronized void addWarning(String text) throws Exception
 {
  if (text == null) throw new Exception("text=null");
  ALogItem item = new ALogItem("W", text, getCurrentDateStd());
  FLogItemList.add(item);
  trimItemList();
  System.out.println(item.toString());
  updated();
 }
//---------------------------------------------------------------------------
 public synchronized void addError(String text) throws Exception
 {
  if (text == null) throw new Exception("text=null");
  ALogItem item = new ALogItem("E", text, getCurrentDateStd());
  FLogItemList.add(item);
  trimItemList();
  System.err.println(item.toString());
  updated();
 }
//---------------------------------------------------------------------------
 private synchronized void updated() throws Exception
 {
  if (FAutoSaveFileName.isEmpty()) return;
  saveToFile(FAutoSaveFileName);
 }
//---------------------------------------------------------------------------
 public synchronized String get(int index) throws Exception
 {
  if (index < 0) throw new Exception("index<0 : " + index);
  if (index >= FLogItemList.size()) throw new Exception("index>=" + FLogItemList.size() + " : " + index);
  return FLogItemList.get(index).toString();
 }
//---------------------------------------------------------------------------
 public synchronized ALogItem getLogItem(int index) throws Exception
 {
  if (index < 0) throw new Exception("index<0 : " + index);
  if (index >= FLogItemList.size()) throw new Exception("index>=" + FLogItemList.size() + " : " + index);
  return FLogItemList.get(index);
 }
//---------------------------------------------------------------------------
 /**
  * Метод записывает данные в поток Out.<br/>
  *
  * @param Out - Выходной поток.
  * @return true - выполнено,<br/>
  * false - не выполнено.
  * @throws java.lang.Exception
  */
 public boolean write(OutputStream Out) throws Exception      
 {
  if (Out == null) throw new Exception("Out=null");
  PrintWriter Wrtr = new PrintWriter(Out);
  String str = "";
  for (int q = 0; q < FLogItemList.size(); q++)
  {
   if (q != 0)
   {
    str += (char) 13;
    str += (char) 10;
   }
   str += FLogItemList.get(q).toStringWithDate();
  }
  Wrtr.write(str);
  Wrtr.flush();
  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  String enter = "";
  enter += (char) 13;
  enter += (char) 10;

  String str = "--- ALog ---" + enter;
  for (int q = 0; q < FLogItemList.size(); q++)
  {
   if (q != 0) str += enter;
   str += FLogItemList.get(q).toString();
  }
  return str;//"ALog{"+'}';
 }
//---------------------------------------------------------------------------
 public void saveToFile(String fileName) throws Exception
 {
  try
  {
   File dir = new File(FPath);
   if (!dir.exists()) if (!dir.mkdirs()) throw new Exception("mkdir ERROR Log have not been saved");
   File file = new File(FPath, fileName);
   OutputStream fileOut = new FileOutputStream(file);
   write(fileOut);
   fileOut.close();
  }
  catch (FileNotFoundException ex)
  {
   throw new Exception("file not found");
  }
  catch (IOException ex)
  {
   throw new Exception(ex);
  }
 }
//---------------------------------------------------------------------------
 public String getAutoSave()
 {
  return FAutoSaveFileName;
 }
 public void setAutoSave(String fileName)
 {
  this.FAutoSaveFileName = fileName;
 }
 public void breakAutoSave()
 {
  this.FAutoSaveFileName = "";
 }
//---------------------------------------------------------------------------
 public String getPath()
 {
  return FPath;
 }
 public void setPath(String path)
 {
  this.FPath = path;
 }
//---------------------------------------------------------------------------
 public int getItemLimit()
 {
  return FItemLimit;
 }
 public void setItemLimit(int limint)
 {
  this.FItemLimit = limint;
 }
//---------------------------------------------------------------------------
 public static String getCurrentDateStd()
 {
  return AFN.dateToIsoFormat(new Date());
 }
//---------------------------------------------------------------------------
}
