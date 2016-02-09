/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatol
 * @version 0.1.0.0
 */
public class AFile extends AClass
{
//---------------------------------------------------------------------------
/**
 * Метод создаёт новый файл. 
 * Если файл с указанным именем уже существует, то происходит исключение.<br />
 * @param fileName имя файла
 * @return ссылка на файл
 * @throws AException в случае ошибки
 */
 public static File createFile(String fileName) throws AException
 {
  if(fileName==null)throw new AException("fileName=null");
  try
  {
   File file=new File(fileName);
   if(file.exists())throw new AException("File \""+fileName+"\" alredy exists");
   if(!file.createNewFile())throw new AException("File \""+fileName+"\" not created");
   sendGlobalInfo("createFile","\""+fileName+"\"",null);
   return file;
  }
  catch(IOException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает ссылку на файл.
 * Если файла с указанным именем не существует, то происходит исключение.<br />
 * @param fileName имя файла
 * @return ссылка на файл
 * @throws AException в случае ошибки
 */
 public static File openFile(String fileName)throws AException
 {
  sendGlobalWarning("openFile","This function is not developed",null);
  if(fileName==null)throw new AException("fileName=null");
  File file=new File(fileName);
  if(!file.exists())throw new AException("File \""+fileName+"\" not exists");
  sendGlobalInfo("openFile","\""+fileName+"\"",null);
  return file;
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает ссылку на файл.
 * Если файла с указанным именем не существует, то он создаётся.<br />
 * @param fileName
 * @return
 * @throws AException 
 */
 public static File openOrCreateFile(String fileName)throws AException
 {
  if(fileName==null)throw new AException("fileName=null");
  try
  {
   File file=new File(fileName);
   if(!file.exists())if(!file.createNewFile())throw new AException("File \""+fileName+"\" not created");
   sendGlobalInfo("openOrCreateFile","\""+fileName+"\"",null);
   return file;
  }
  catch(IOException ex){throw new AException(ex);}
 } 
//---------------------------------------------------------------------------
/**
 * Метод удаляет файл.
 * Если файла с указанным именем не существует, то происходит исключение.<br />
 * @param fileName
 * @throws AException 
 */ 
 public static void deleteFile(String fileName)throws AException
 {
  if(fileName==null)throw new AException("fileName=null");
  File file=new File(fileName);
  if(!file.exists())throw new AException("File \""+fileName+"\" not exists");
  if(!file.delete())throw new AException("File \""+fileName+"\"don't delete");
  sendGlobalInfo("deleteFile","\""+fileName+"\"",null);
 }
//---------------------------------------------------------------------------
/**
 * Метод создаёт папку и все промежуточные папки по указанному пути.<br />
 * @param dirName путь
 * @return ссылка на папку
 * @throws AException 
 */
 public static File forceDir(String dirName) throws AException
 {
  if(dirName==null)throw new AException("dirName=null");
  File dir=new File(dirName);
  if(!dir.mkdirs())throw new AException("Dir \""+dirName+"\" don't create");
  sendGlobalInfo("forceDir","\""+dirName+"\"",null);
  return dir;
 }
//---------------------------------------------------------------------------
 public static void deleteEmptyDir(String dirName) throws AException
 {
  sendGlobalWarning("deleteEmptyDir","This function is not developed",null);
  if(dirName==null)throw new AException("dirName=null");
  File dir=new File(dirName);
  if(!dir.exists())throw new AException("Dir \""+dirName+"\" not exists");
  
  
  
  
  sendGlobalInfo("deleteEmptyDir","\""+dirName+"\"",null);
 }
//---------------------------------------------------------------------------
 public static void deleteDirAndContent(String dirName) throws AException
 {
  sendGlobalWarning("deleteDirAndContent","This function is not developed",null);
  if(dirName==null)throw new AException("dirName=null");
  File dir=new File(dirName);
  if(!dir.exists())return;
  deleteDirContent(dir);
  AClass.sendGlobalInfo("deleteDirAndContent","Dir:"+dir.getName(),null);
  dir.delete();
  
  sendGlobalInfo("deleteDirAndContent","\""+dirName+"\"",null);
 }
//---------------------------------------------------------------------------
 private static void deleteDirContent(File dir)
 {
  File fileList[]=dir.listFiles();
  for(File file:fileList)
  {
   if(file.isDirectory())deleteDirContent(file);
   AClass.sendGlobalInfo("deleteDirContent",file.getName(),null);
   file.delete();
  }
 }
//---------------------------------------------------------------------------
 public static List<String> getFilePathList(String pathName,boolean subdir,final String extList[]) throws AException
 {
  List<String> filePathList=new ArrayList();
  File path=new File(pathName);
  processPach(path,subdir,extList,filePathList);
  return filePathList;
 }
//---------------------------------------------------------------------------
 private static void processPach(File path,boolean subdir,final String extList[],List<String> filePathList) throws AException
 {
  File pathList[]=path.listFiles();
  for(File p:pathList)
  {
   if(!p.isDirectory())continue;
   if(!subdir)continue;
   processPach(p,subdir,extList,filePathList);
  }
  FilenameFilter filter=new FilenameFilter()
  {
   @Override
   public boolean accept(File file,String string)
   {
    String ext=getExt(string);
    if(ext==null) return false;
    for(String mask:extList) if(ext.equalsIgnoreCase(mask)) return true;
    return false;
   }
  };
  File fileList[]=path.listFiles(filter);
  for(File file:fileList)
  {
   if(!file.isFile())continue;
   filePathList.add(file.getPath());
  }
 }
//---------------------------------------------------------------------------
 public static String getExt(String fileName)
 {
  String ext="";
  char buf[]=fileName.toCharArray();
  int index=buf.length-1;
  while(buf[index]!='.')
  {
   if(index<=0)return null;
   ext=buf[index]+ext;
   index--;
  }
  return ext;
 }
//---------------------------------------------------------------------------
/**
 * Метод разделяет имя файла на путь, имя и расширение.
 * Если какая либо часть имени файла не определена, она заменяется пустой сторкой. <br />
 * @param fileName имя файла
 * @return массив, в котором [0] - путь, [1] - имя, [2] - расширение.
 */
 public static String[] splitFileName(String fileName)
 {
  String[] list={"","",""};//new String[3];
  int s=fileName.lastIndexOf('/');
  if(s==-1)s=fileName.lastIndexOf('\\');
  if(s!=-1)
  {
   list[0]=fileName.substring(0,s);
   fileName=fileName.substring(s+1);
  }
  s=fileName.lastIndexOf('.');
  if(s!=-1)
  {
   list[1]=fileName.substring(0,s);
   list[2]=fileName.substring(s+1);
  }
  else list[1]=fileName;
  return list;
 }
//---------------------------------------------------------------------------

}
