/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс с набором статичных методов работающих с системой ввода/вывода.
 * @author Пользователь
 * @version 0.1.0.2
 */
public class AIO extends AClass
{
//---------------------------------------------------------------------------
 public static String ReaderToString(Reader reader) throws IOException
 {
  StringBuilder strBuff=new StringBuilder();
  char[] buf=new char[1024];
  int numRead;
  while((numRead=reader.read(buf))!=-1)
  {
   String readData=String.valueOf(buf,0,numRead);
   strBuff.append(readData);
   buf=new char[1024];
  }
  reader.close();
  return strBuff.toString();
 }
//---------------------------------------------------------------------------
 public static String InputStreamToString(InputStream in) throws IOException
 {
  Reader reader;
  reader=new InputStreamReader(in,"UTF-8");
  String value=ReaderToString(reader);
  reader.close();
  return value;
 }
//---------------------------------------------------------------------------
 public static String readFileToString(File file) throws IOException
 {
  InputStream inputStream;
  inputStream=new FileInputStream(file);
  String value=InputStreamToString(inputStream);
  inputStream.close();
  return value;
 }
//---------------------------------------------------------------------------
 public static String readFileToString(String fileName) throws IOException
 {
  File file=new File(fileName);
  if(!file.exists())throw new IOException("File "+fileName+" not exists");
  if(!file.canRead())throw new IOException("File "+fileName+" cannot read");
  return readFileToString(file);
 }
//---------------------------------------------------------------------------
 public static String readURLToString(URL url) throws IOException
 {
  InputStream inputStream;
  inputStream = url.openStream();
  String value=InputStreamToString(inputStream);
  inputStream.close();
  return value;
 }
//---------------------------------------------------------------------------
 public static String readURLToString(String urlName) throws IOException
 {
  URL url;
  try
  {
   url=new URL(urlName);
  }
  catch(MalformedURLException ex)
  {
   throw new IOException("URL \""+urlName+"\" malformed");
  }
  return readURLToString(url);
 }
//---------------------------------------------------------------------------
 public static String ResourceToString(Object obj,String name) throws IOException
 {
  InputStream inputStream=obj.getClass().getResourceAsStream(name);
  if(inputStream==null)throw new IOException("Resource "+name+" not found");
  String xml=InputStreamToString(inputStream);
  return xml;
 }
//---------------------------------------------------------------------------
 public static byte[] InputStreamToBytes(InputStream in) throws IOException
 {
  ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
  byte[] buf=new byte[1024];
  int len;
  while((len=in.read(buf))!=-1)byteBuffer.write(buf,0,len);
  byteBuffer.flush();
  return byteBuffer.toByteArray();
 }
//---------------------------------------------------------------------------
 public static byte[] readFileToBytes(File file) throws IOException
 {
  InputStream inputStream;
  inputStream=new FileInputStream(file);
  byte[] value=InputStreamToBytes(inputStream);
  inputStream.close();
  return value;
 }
//---------------------------------------------------------------------------
 public static byte[] readFileToBytes(String fileName) throws IOException
 {
  File file=new File(fileName);
  if(!file.exists())throw new IOException("File "+fileName+" not exists");
  if(!file.canRead())throw new IOException("File "+fileName+" cannot read");
  return readFileToBytes(file);
 }
////---------------------------------------------------------------------------
// public static byte[] InputStreamToBytes(InputStream in) throws IOException
// {
//  ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//  try(OutputStream bufOStream=new BufferedOutputStream(byteBuffer);)
//  {
//   //use pdfOStream
//   bufOStream.flush();
//  }
//  catch(Exception ex)
//  {
//   ex.printStackTrace(System.err);
//  }
//  return byteBuffer.toByteArray();
// }
//---------------------------------------------------------------------------
 public static void writeToOutputStream(byte[] data,OutputStream out) throws IOException
 {
  for(byte b:data)out.write(b);
  out.flush();
 }
//---------------------------------------------------------------------------
 public static void writeToFile(byte[] data,File file) throws IOException
 {
  try (OutputStream out=new FileOutputStream(file))
  {
   writeToOutputStream(data,out);
   out.close();
  }
 }
//---------------------------------------------------------------------------
 public static void writeToFile(byte[] data,String fileName) throws IOException
 {
  File file=new File(fileName);
  if(!file.exists()) file.createNewFile();
  writeToFile(data,file);
 }
//---------------------------------------------------------------------------
 public static List<String> getFilePathList(String pathName,boolean subdir,final String extList[]) throws IOException
 {
  List<String> filePathList=new ArrayList();
  File path=new File(pathName);
  processPach(path,subdir,extList,filePathList);
  return filePathList;
 }
//---------------------------------------------------------------------------
 private static void processPach(File path,boolean subdir,final String extList[],List<String> filePathList) throws IOException
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
 public static boolean haveBOM(byte[] buf)
 {
  if(buf.length<3)return false;
  final byte[] BOM={(byte)0xEF,(byte)0xBB,(byte)0xBF};
  if(buf[0]!=BOM[0])return false;
  if(buf[1]!=BOM[1])return false;
  if(buf[2]!=BOM[2])return false;
  return true;
 }
//---------------------------------------------------------------------------
 public static byte[] removeBOM(byte[] buf)
 {
  if(!haveBOM(buf))return buf;
  byte b[]=new byte[buf.length-3];
  for(int q=0;q<b.length;q++)b[q]=buf[q+3];
  return b;
 }
//---------------------------------------------------------------------------
}
