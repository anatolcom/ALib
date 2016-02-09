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
 public static final byte[] BOM={(byte)0xEF,(byte)0xBB,(byte)0xBF};
//---------------------------------------------------------------------------
 public static byte[] readBytesFromInputStream(InputStream in) throws IOException
 {
  ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
  byte[] buf=new byte[1024];
  int len;
  while((len=in.read(buf))!=-1)byteBuffer.write(buf,0,len);
  byteBuffer.flush();
  return byteBuffer.toByteArray();
 }
//---------------------------------------------------------------------------
 public static byte[] readBytesFromFile(File file) throws IOException
 {
  InputStream inputStream=new FileInputStream(file);
  byte [] buf=readBytesFromInputStream(inputStream);
  inputStream.close();
  return buf;
 }
//---------------------------------------------------------------------------
 public static byte[] readBytesFromFile(String fileName) throws IOException
 {
  File file=new File(fileName);
  if(!file.exists())throw new IOException("File \""+fileName+"\" not exists");
  if(!file.canRead())throw new IOException("File \""+fileName+"\" cannot read");
  return readBytesFromFile(file);
 }
//---------------------------------------------------------------------------
 public static String readStringFromReader(Reader reader) throws IOException
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
 public static String readStringFromInputStream(InputStream in) throws IOException
 {
  return new String(removeBOM(readBytesFromInputStream(in)),"UTF-8");
 }
//---------------------------------------------------------------------------
 public static String readStringFromFile(File file) throws IOException
 {
  InputStream inputStream=new FileInputStream(file);
  String buf=readStringFromInputStream(inputStream);
  inputStream.close();
  return buf;
 }
//---------------------------------------------------------------------------
 public static String readStringFromFile(String fileName) throws IOException
 {
  File file=new File(fileName);
  if(!file.exists())throw new IOException("File \""+fileName+"\" not exists");
  if(!file.canRead())throw new IOException("File \""+fileName+"\" cannot read");
  return readStringFromFile(file);
 }
//---------------------------------------------------------------------------
 public static String readStringFromURL(URL url) throws IOException
 {
  InputStream inputStream;
  inputStream = url.openStream();
  String value=readStringFromInputStream(inputStream);
  inputStream.close();
  return value;
 }
//---------------------------------------------------------------------------
 public static String readStringFromURL(String urlName) throws IOException
 {
  try
  {
   return readStringFromURL(new URL(urlName));
  }
  catch(MalformedURLException ex)
  {
   throw new IOException("URL \""+urlName+"\" malformed");
  }
 }
//---------------------------------------------------------------------------
 public static byte[] readBytesFromResource(Object resource,String name) throws IOException
 {
  InputStream inputStream=resource.getClass().getResourceAsStream(name);
  if(inputStream==null)throw new IOException("Resource \""+name+"\" not found");
  byte[] buf=readBytesFromInputStream(inputStream);
  inputStream.close();
  return buf;
 }
//---------------------------------------------------------------------------
 public static String readStringFromResource(Object resource,String name) throws IOException
 {
  byte[] buf=readBytesFromResource(resource,name);
  return new String(removeBOM(buf),"UTF-8");
 }
//---------------------------------------------------------------------------
// public static byte[] readBytesFromFile(InputStream in) throws IOException
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
 public static InputStream openInputStreamFromBytes(byte[] data) throws IOException
 {
  return new ByteArrayInputStream(data);
 }
//---------------------------------------------------------------------------
 public static InputStream openInputStreamFromString(String data) throws IOException
 {
  return openInputStreamFromBytes(data.getBytes("UTF-8"));
 }
//---------------------------------------------------------------------------
 public static void writeBytesToOutputStream(byte[] data,OutputStream out) throws IOException
 {
  for(byte b:data)out.write(b);
  out.flush();
 }
//---------------------------------------------------------------------------
 public static void writeStringToOutputStream(String str,OutputStream out) throws IOException
 {
  byte[] data=str.getBytes("UTF-8");
  for(byte b:data)out.write(b);
  out.flush();
 }
//---------------------------------------------------------------------------
 public static void writeBytesToFile(byte[] data,File file) throws IOException
 {
  OutputStream out=new FileOutputStream(file);
  writeBytesToOutputStream(data,out);
  out.close();
 }
//---------------------------------------------------------------------------
 public static void writeBytesToFile(byte[] data,String fileName) throws IOException
 {
  File file=new File(fileName);
  if(!file.exists()) file.createNewFile();
  writeBytesToFile(data,file);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static List<String> getFilePathList(String pathName,boolean subdir,final String extList[]) throws IOException
 {
  List<String> filePathList=new ArrayList();
  File path=new File(pathName);
  processPach(path,subdir,extList,filePathList);
  return filePathList;
 }
//---------------------------------------------------------------------------
 @Deprecated
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
 @Deprecated
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
 @Deprecated
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
 public static boolean haveBOM(byte[] data)
 {
  if(data.length<3)return false;
  if(data[0]!=BOM[0])return false;
  if(data[1]!=BOM[1])return false;
  if(data[2]!=BOM[2])return false;
  return true;
 }
//---------------------------------------------------------------------------
 public static byte[] removeBOM(byte[] data)
 {
  if(!haveBOM(data))return data;
  byte b[]=new byte[data.length-3];
  for(int q=0;q<b.length;q++)b[q]=data[q+3];
  return b;
 }
//---------------------------------------------------------------------------
 public static byte[] addBOM(byte[] data)
 {
  if(haveBOM(data))return data;
  byte b[]=new byte[data.length+3];
  b[0]=BOM[0];
  b[1]=BOM[1];
  b[2]=BOM[2];
  for(int q=0;q<data.length;q++)b[q+3]=data[q];
  return b;
 }
//---------------------------------------------------------------------------
}
