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
 * @author Anatoly
 * @version 0.1.0.3
 */
public class AIO extends AClass
{
//---------------------------------------------------------------------------
 public static final byte[] BOM={(byte)0xEF,(byte)0xBB,(byte)0xBF};
//---------------------------------------------------------------------------
 public static byte[] readBytesFromInputStream(InputStream in) throws IOException
 {
  if(in==null)throw new IOException("in=null");
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
  if(file==null)throw new IOException("file=null");
  InputStream inputStream=new FileInputStream(file);
  byte [] buf=readBytesFromInputStream(inputStream);
  inputStream.close();
  return buf;
 }
//---------------------------------------------------------------------------
 public static byte[] readBytesFromFile(String fileName) throws IOException
 {
  if(fileName==null)throw new IOException("fileName=null");
  File file=new File(fileName);
  if(!file.exists())throw new IOException("File \""+fileName+"\" not exists");
  if(!file.canRead())throw new IOException("File \""+fileName+"\" cannot read");
  return readBytesFromFile(file);
 }
//---------------------------------------------------------------------------
 public static String readStringFromReader(Reader reader) throws IOException
 {
  if(reader==null)throw new IOException("reader=null");
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
  if(in==null)throw new IOException("in=null");
  return new String(removeBOM(readBytesFromInputStream(in)),"UTF-8");
 }
//---------------------------------------------------------------------------
 public static String readStringFromFile(File file) throws IOException
 {
  if(file==null)throw new IOException("file=null");
  InputStream inputStream=new FileInputStream(file);
  String buf=readStringFromInputStream(inputStream);
  inputStream.close();
  return buf;
 }
//---------------------------------------------------------------------------
 public static String readStringFromFile(String fileName) throws IOException
 {
  if(fileName==null)throw new IOException("fileName=null");
  File file=new File(fileName);
  if(!file.exists())throw new IOException("File \""+fileName+"\" not exists");
  if(!file.canRead())throw new IOException("File \""+fileName+"\" cannot read");
  return readStringFromFile(file);
 }
//---------------------------------------------------------------------------
 public static String readStringFromURL(URL url) throws IOException
 {
  if(url==null)throw new IOException("url=null");
  InputStream inputStream;
  inputStream = url.openStream();
  String value=readStringFromInputStream(inputStream);
  inputStream.close();
  return value;
 }
//---------------------------------------------------------------------------
 public static String readStringFromURL(String urlName) throws IOException
 {
  if(urlName==null)throw new IOException("urlName=null");
  try{return readStringFromURL(new URL(urlName));}
  catch(MalformedURLException ex){throw new IOException("URL \""+urlName+"\" malformed");}
 }
//---------------------------------------------------------------------------
 public static byte[] readBytesFromResource(Object resource,String name,boolean required) throws IOException
 {
  if(resource==null)throw new IOException("resource=null");
  if(name==null)throw new IOException("name=null");
  InputStream inputStream=resource.getClass().getResourceAsStream(name);
  if(inputStream==null)
  {
   if(required)throw new IOException("Resource \""+name+"\" not found");
   return null;
  }
  byte[] buf=readBytesFromInputStream(inputStream);
  inputStream.close();
  return buf;
 }
//---------------------------------------------------------------------------
 public static String readStringFromResource(Object resource,String name,boolean required) throws IOException
 {
  if(resource==null)throw new IOException("resource=null");
  if(name==null)throw new IOException("name=null");
  byte[] buf=readBytesFromResource(resource,name,required);
  if(buf==null)return null;
  return new String(removeBOM(buf),"UTF-8");
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static byte[] readBytesFromResource(Object resource,String name) throws IOException
 {
  return readBytesFromResource(resource,name,true);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static String readStringFromResource(Object resource,String name) throws IOException
 {
  return readStringFromResource(resource,name,true);
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

//*****
//   ByteArrayOutputStream byteBuffer=new ByteArrayOutputStream();
//   PrintStream ps=new PrintStream(byteBuffer);
//   ex.printStackTrace(ps);
//   ps.close();
//   String str=byteBuffer.toString();
//*****
 
// }
//---------------------------------------------------------------------------
 public static InputStream openInputStreamFromBytes(byte[] data) throws IOException
 {
  if(data==null)throw new IOException("data=null");
  return new ByteArrayInputStream(data);
 }
//---------------------------------------------------------------------------
 public static InputStream openInputStreamFromString(String data) throws IOException
 {
  if(data==null)throw new IOException("data=null");
  return openInputStreamFromBytes(data.getBytes("UTF-8"));
 }
//---------------------------------------------------------------------------
 public static void writeBytesToOutputStream(byte[] data,OutputStream out) throws IOException
 {
  if(data==null)throw new IOException("data=null");
  if(out==null)throw new IOException("out=null");
  for(byte b:data)out.write(b);
  out.flush();
 }
//---------------------------------------------------------------------------
 public static void writeStringToOutputStream(String text,OutputStream out) throws IOException
 {
  if(text==null)throw new IOException("text=null");
  writeBytesToOutputStream(text.getBytes("UTF-8"),out);
 }
//---------------------------------------------------------------------------
 public static void writeBytesToFile(byte[] data,File file) throws IOException
 {
  if(data==null)throw new IOException("data=null");
  if(file==null)throw new IOException("file=null");
  OutputStream out=new FileOutputStream(file);
  writeBytesToOutputStream(data,out);
  out.close();
 }
//---------------------------------------------------------------------------
 public static void writeStringToFile(String text,File file) throws IOException
 {
  if(text==null)throw new IOException("text=null");
  if(file==null)throw new IOException("file=null");
  writeBytesToFile(text.getBytes("UTF-8"),file);
 }
//---------------------------------------------------------------------------
 public static void writeBytesToFile(byte[] data,String fileName) throws IOException
 {
  if(data==null)throw new IOException("data=null");
  if(fileName==null)throw new IOException("fileName=null");
  File file=new File(fileName);
  if(!file.exists()) file.createNewFile();
  writeBytesToFile(data,file);
 }
//---------------------------------------------------------------------------
 public static void writeStringToFile(String text,String fileName) throws IOException
 {
  if(text==null)throw new IOException("text=null");
  if(fileName==null)throw new IOException("fileName=null");
  writeBytesToFile(text.getBytes("UTF-8"),fileName);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static List<String> getFilePathList(String pathName,boolean subdir,final String extList[]) throws IOException
 {
  System.out.println("getFilePathList is Deprecated");
  List<String> filePathList=new ArrayList();
  File path=new File(pathName);
  processPach(path,subdir,extList,filePathList);
  return filePathList;
 }
//---------------------------------------------------------------------------
 @Deprecated
 private static void processPach(File path,boolean subdir,final String extList[],List<String> filePathList) throws IOException
 {
  System.out.println("processPach is Deprecated");
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
  System.out.println("getExt is Deprecated");
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
  System.out.println("splitFileName is Deprecated");
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
 public static boolean haveBOM(byte[] buf)
 {
  if(buf==null)return false;
  if(buf.length<3)return false;
  if(buf[0]!=BOM[0])return false;
  if(buf[1]!=BOM[1])return false;
  if(buf[2]!=BOM[2])return false;
  return true;
 }
//---------------------------------------------------------------------------
 public static byte[] removeBOM(byte[] buf)
 {
  if(buf==null)return null;
  if(!haveBOM(buf))return buf;
  byte b[]=new byte[buf.length-3];
  for(int q=0;q<b.length;q++)b[q]=buf[q+3];
  return b;
 }
//---------------------------------------------------------------------------
 public static byte[] addBOM(byte[] buf)
 {
  if(haveBOM(buf))return buf;
  byte b[]=new byte[buf.length+3];
  b[0]=BOM[0];
  b[1]=BOM[1];
  b[2]=BOM[2];
  for(int q=0;q<buf.length;q++)b[q+3]=buf[q];
  return b;
 }
//---------------------------------------------------------------------------
}
