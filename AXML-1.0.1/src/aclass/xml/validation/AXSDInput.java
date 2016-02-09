/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;
import aclass.AClass;
import aclass.AIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.w3c.dom.ls.LSInput;
/**
 *
 * @author Anatol
 */
public class AXSDInput extends AClass implements LSInput
{
 private String publicId=null;
 private String systemId=null;
 private String baseURI=null;
 byte[] data=null;
 private boolean certifiedText=false;
 private String encoding="UTF-8";
//---------------------------------------------------------------------------
 @Override
 public String getPublicId(){return publicId;}
 @Override
 public void setPublicId(String publicId){this.publicId=publicId;}
//---------------------------------------------------------------------------
 @Override
 public String getSystemId(){return systemId;}
 @Override
 public void setSystemId(String systemId){this.systemId=systemId;}
//---------------------------------------------------------------------------
 @Override
 public String getBaseURI(){return baseURI;}
 @Override
 public void setBaseURI(String baseURI){this.baseURI=baseURI;}
//---------------------------------------------------------------------------
 @Override
 public boolean getCertifiedText(){return certifiedText;}
 @Override
 public void setCertifiedText(boolean certifiedText){this.certifiedText=certifiedText;}
//---------------------------------------------------------------------------
 @Override
 public String getEncoding(){return encoding;}
 @Override
 public void setEncoding(String encoding){this.encoding=encoding;}
//---------------------------------------------------------------------------
 @Override
 public InputStream getByteStream()
 {
  if(data==null)return null;
  try{return AIO.openInputStreamFromBytes(data);}
  catch(IOException ex){sendError("getByteStream",ex.getMessage(),null);}
  return null;
 }
 @Override
 public void setByteStream(InputStream byteStream){}
//---------------------------------------------------------------------------
 @Override
 public Reader getCharacterStream()
 {
//  try{return new InputStreamReader(AIO.openInputStreamFromBytes(data));}
//  catch(IOException ex){sendError("getCharacterStream",ex.getMessage(),null);}
  return null;
 }
 @Override
 public void setCharacterStream(Reader characterStream){}
//---------------------------------------------------------------------------
 @Override
 public String getStringData()
 {
  try{return new String(data,encoding);}
  catch(UnsupportedEncodingException ex){sendError("getStringData",ex.getMessage(),null);}
  return new String(data);
 }
 @Override
 public void setStringData(String stringData)
 {
  try{data=stringData.getBytes(encoding);}
  catch(UnsupportedEncodingException ex){sendError("setStringData",ex.getMessage(),null);}
 }
//---------------------------------------------------------------------------
 public AXSDInput(String publicId,String sysId,String baseURI,byte[] data)
 {
  this.publicId=publicId;
  this.systemId=sysId;
  this.baseURI=baseURI;
  this.data=data;
 }
//---------------------------------------------------------------------------
}
