/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.azip;

import aclass.AClass;
import static aclass.AClass.sendGlobalError;
import aclass.AException;
import aclass.AIO;
//import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;


/**
 *
 * @author Anatol.com
 */
public class AZip extends AClass
{
//---------------------------------------------------------------------------
 public static boolean isUsableCharset()
 {
  String javaVersion[];
  String javaVersionStr = System.getProperty("java.version");  
  if(javaVersionStr==null)return false;
  javaVersion=javaVersionStr.split("\\.",3);
  if("1".equals(javaVersion[0]))
  {
   if("7".equals(javaVersion[1]))return true;
  }
  return false;
 }
//---------------------------------------------------------------------------
 public static ArrayList<AZipItem> readZip(InputStream in) throws AException
 {
  /*try
  {
   ArrayList<AZipItem> includeList=new ArrayList<AZipItem>();
   try
   {
    Charset charset=null;
    if(Charset.isSupported("IBM866"))charset=Charset.forName("IBM866");
    else sendGlobalError(AZip.class.getName(),"writeZip","Unsupported charset IBM866",null);
    if(!isUsableCharset())
    {
     charset=null;
     sendGlobalError(AZip.class.getName(),"readZip","Unsupported ZipInputStream with using charset",null);
    }
    ZipInputStream zipIStream;
    if(charset==null)zipIStream=new ZipInputStream(in);
    else zipIStream=new ZipInputStream(in,charset);
//    ZipInputStream zipIStream=new ZipInputStream(in,Charset.forName("CP866"));
//    sendGlobalInfo("readZip","Charset would be set to CP866",null);
//    ZipInputStream zipIStream=new ZipInputStream(in);
    while(true)
    {
     ZipEntry entry=zipIStream.getNextEntry();
     if(entry==null)break;
     sendGlobalInfo("readZip","zip entry: "+entry.getName(),null);
     byte[] buf=AIO.readBytesFromInputStream(zipIStream);
     zipIStream.closeEntry();
     includeList.add(new AZipItem(entry.getName(),buf));
    }
    zipIStream.close();
   }
   catch(Exception ex){sendGlobalError("readZip","Read from ZipInputStream Exception: "+ex.getMessage(),null);}
   return includeList;
  }
  catch(Exception ex){throw new AException("Read from zip error",ex);}*/
  return readZipApache(in);
 }
//---------------------------------------------------------------------------
 public static ArrayList<AZipItem> readZipApache(InputStream in) throws AException
 {
  try
  {
   ArrayList<AZipItem> includeList=new ArrayList<AZipItem>();
   try
   {
    ZipArchiveInputStream zip=new ZipArchiveInputStream(in,"IBM866");
    while(true)
    {
     ArchiveEntry entry=zip.getNextEntry();
     if(entry==null)break;
     byte[] buf=AIO.readBytesFromInputStream(zip);
     includeList.add(new AZipItem(entry.getName(),buf));
    }
    zip.close();
   }
   catch(Exception ex){sendGlobalError("readZipApache","Read from ZipInputStream Exception: "+ex.getMessage());}
   return includeList;
  }
  catch(Exception ex){throw new AException("Read from zip error",ex);}
 }
//---------------------------------------------------------------------------
 public static void writeZip(OutputStream out,List<AZipItem> includeList) throws AException
 {
  /*try
  {
   try
   {
    Charset charset=null;
    if(Charset.isSupported("IBM866"))charset=Charset.forName("IBM866");
    else sendGlobalError(AZip.class.getName(),"writeZip","Unsupported charset IBM866",null);
    if(!isUsableCharset())
    {
     charset=null;
     sendGlobalError(AZip.class.getName(),"writeZip","Unsupported ZipOutputStream with using charset",null);
    }
    ZipOutputStream zipOStream;
    if(charset==null)zipOStream=new ZipOutputStream(out);
    else zipOStream=new ZipOutputStream(out,charset);
//    ZipOutputStream zipOStream=new ZipOutputStream(out,Charset.forName("CP866"));
//    sendGlobalInfo("writeZip","Charset would be set to CP866",null);
//    ZipOutputStream zipOStream=new ZipOutputStream(out);
    for(AZipItem include:includeList)
    {
     ZipEntry entry=new ZipEntry(include.name);
     zipOStream.putNextEntry(entry);
     AIO.writeBytesToOutputStream(include.data,zipOStream);
     zipOStream.closeEntry();
    }
    zipOStream.close();
   }
   catch(Exception ex){sendGlobalError("writeZip","Write to ZipOutputStream Exception: "+ex.getMessage(),null);}
  }
  catch(Exception ex){throw new AException("Write to zip error",ex);}*/
  writeZipApache(out,includeList);
 }
//---------------------------------------------------------------------------
 public static void writeZipApache(OutputStream out,List<AZipItem> includeList) throws AException
 {
  try
  {
   try
   {
    //java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream(0);//size+xmlStr.length()+1024);
    ZipArchiveOutputStream zop=new ZipArchiveOutputStream(out);//baos
//	zop.setEncoding("UTF-8");
	zop.setEncoding("IBM866");
    for(AZipItem include:includeList)
    {
     zop.putArchiveEntry(new ZipArchiveEntry(include.name));
     zop.write(include.data);
     zop.closeArchiveEntry();
    }
    zop.close();
    //AIO.writeBytesToOutputStream(baos.toByteArray(),out);
   }
   catch(Exception ex){sendGlobalError("writeZipApache","Write to ZipOutputStream Exception: "+ex.getMessage());}
  }
  catch(Exception ex){throw new AException("Write to zip error",ex);}
 }
//---------------------------------------------------------------------------
 /*
		// Create zip
		ByteArrayOutputStream baos=new ByteArrayOutputStream(size+xmlStr.length()+1024);
		ZipArchiveOutputStream zop=new ZipArchiveOutputStream(baos);
		zop.setEncoding("UTF-8");

		// Write response
		zop.putArchiveEntry(new ZipArchiveEntry("req_" + guid + ".xml"));
		zop.write(xmlStr.getBytes("UTF-8"));
		zop.closeArchiveEntry();

		// Write attachments
		if (attachList != null) 
        {
			for (DataObject item : attachList) {
				byte[] bytes = item.getBytes("Body");
				if (bytes != null) {
					zop.putArchiveEntry(new ZipArchiveEntry(item.getString("Name")));
					zop.write(bytes);
					zop.closeArchiveEntry();
				}
				bytes = item.getBytes("Sign");
				if (bytes != null) {
					zop.putArchiveEntry(new ZipArchiveEntry(item.getString("Name")+ ".sgn"));
					zop.write(bytes);
					zop.closeArchiveEntry();
				}
			}
		}

		// Close stream and return
		zop.close();
		return baos.toByteArray(); 
*/
//---------------------------------------------------------------------------
}
