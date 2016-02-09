/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.*;
import java.util.ArrayList;
//import jurnal.JurnalView;

/**
 * Класс описывающий ведение Log.
 * @author Anatol
 * @version 0.1.0.2
 */
public class ALog extends AClass
{
//---------------------------------------------------------------------------
 private ArrayList<String> FItemList=new ArrayList();
//---------------------------------------------------------------------------
 private String FPath=""; 
 private String FAutoSaveFileName=""; 
//---------------------------------------------------------------------------
 public int count()
 {
  return FItemList.size();
 }
//---------------------------------------------------------------------------
 public void addInfo(String logItem)
 {
  if(logItem==null)
  {
   sendError("addInfo","Item=null",null);
   return;
  }
  FItemList.add("I "+logItem);
  System.out.println("I "+logItem);
  updated();
 }
//---------------------------------------------------------------------------
 public void addWarning(String logItem)
 {
  if(logItem==null)
  {
   sendError("addWarning","Item=null",null);
   return;
  }
  FItemList.add("W "+logItem);
  System.out.println("W "+logItem);
  updated();
 }
//---------------------------------------------------------------------------
 public void addError(String logItem)
 {
  if(logItem==null)
  {
   sendError("addError","Item=null",null);
   return;
  }
  FItemList.add("E "+logItem);
  System.err.println("E "+logItem);
  updated();
 }
//---------------------------------------------------------------------------
 private void updated()
 {
  if(FAutoSaveFileName.isEmpty())return;
  saveToFile(FAutoSaveFileName);
 }
//---------------------------------------------------------------------------
 public String get(int index)
 {
  if(index<0)
  {
   sendError("get","index<0",index);
   return null;
  }
  if(index>=FItemList.size())
  {
   sendError("get","index>="+FItemList.size(),index);
   return null;
  }
  return FItemList.get(index);
 }
//---------------------------------------------------------------------------
/**
  * Метод записывает данные в поток Out.<br/>
  * @param Out - Выходной поток.
  * @return true - выполнено,<br/>
  *         false - не выполнено.
  */
 public boolean write(OutputStream Out)                                 //!!!
 {
  if(Out==null)
  {
   sendError("write","Out=null",null);
   return false;
  }
  PrintWriter Wrtr=new PrintWriter(Out);
  String str="";
  for(int q=0;q<FItemList.size();q++)
  {
   if(q!=0)
   {
    str+=(char)13;
    str+=(char)10;
   }
   str+=FItemList.get(q);
  }
  Wrtr.write(str);
  Wrtr.flush();
  return true;
 }
//---------------------------------------------------------------------------
 public void saveToFile(String fileName)
 {
  try
  {
   File dir=new File(FPath);
   if(!dir.exists())if(!dir.mkdirs())
   {
//    sendError("saveToFile","mkdir ERROR",null);
    System.err.println("ALog.saveToFile(mkdir ERROR Log have not been saved)");
    return;
   }
   File file=new File(FPath,fileName);
   try (OutputStream fileOut=new FileOutputStream(file))
   {
    write(fileOut);
    fileOut.close();
   }
  }
  catch(FileNotFoundException ex)
  {
   sendError("saveToFile","FileNotFoundException "+ex.getMessage(),null);
  }
  catch(IOException ex)
  {
   sendError("saveToFile","IOException "+ex.getMessage(),null);
  }
 }
//---------------------------------------------------------------------------
 public void setAutoSave(String fileName)
 {
  this.FAutoSaveFileName=fileName;
 }
//---------------------------------------------------------------------------
 public String getAutoSave()
 {
  return FAutoSaveFileName;
 }
//---------------------------------------------------------------------------
 public void breakAutoSave()
 {
  this.FAutoSaveFileName="";
 }
//---------------------------------------------------------------------------
 public void setPath(String path)
 {
  this.FPath=path;
 }
//---------------------------------------------------------------------------
 public String getPath()
 {
  return FPath;
 }
//---------------------------------------------------------------------------
}
