/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;

import aclass.AClass;
import aclass.AException;
import aclass.AIO;
import aclass.xml.dom.ADOM;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Пользователь
 */
public class AXSD extends AClass
{
//---------------------------------------------------------------------------
 public static Schema newSchema(AXSDSchemaMap schemaMap,LSResourceResolver resolver)throws AException
 {
  try
  {
   ArrayList<String> contentList=new ArrayList();
   for(int q=0;q<schemaMap.size();q++)contentList.add(schemaMap.get(q).getContent());
   Source sourceList[]=getSourceList(contentList);

   SchemaFactory schemaFactory=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);//"http://www.w3.org/2001/XMLSchema"
   ErrorHandler errorHandler = new AXSDErrorHandler();
   schemaFactory.setErrorHandler(errorHandler);
   if(resolver!=null)schemaFactory.setResourceResolver(resolver);
//   schemaFactory.setFeature("http://www.w3.org/2004/08/xop/include",false);
   return schemaFactory.newSchema(sourceList);
  }
  catch(SAXException ex)
  {
   Exception e=ex.getException();
   String eStr=null;
   if(e!=null)eStr=", E: "+e.getMessage();
   if(eStr==null)eStr="";
   Throwable c=ex.getCause();
   String cStr=null;
   if(c!=null)cStr=", C: "+c.getMessage();
   if(cStr==null)cStr="";
   throw new AException("Cannot create new schema. SAXException "+ex.getMessage()+eStr+cStr,ex);
  }
 }
//---------------------------------------------------------------------------
 public boolean validation(String xml,Schema schema)throws AException
 {
  if(xml==null)throw new AException("xml=null");
  try
  {
   InputStream io=AIO.openInputStreamFromString(xml);
   Source source=new StreamSource(io);
   boolean isValid=validation(source,schema);
//   io.close();
   return isValid;
  }
  catch(IOException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public boolean validation(String xml,AXSDSchemaMap schemaMap)throws AException
 {
  if(xml==null)throw new AException("xml=null");
  try
  {
   AXSDResourceResolver resolver=new AXSDResourceResolver("validation");
   Schema schema=newSchema(schemaMap,resolver);
   InputStream io=AIO.openInputStreamFromString(xml);
   Source source=new StreamSource(io);
   boolean isValid=validation(source,schema);
//   io.close();
   return isValid;
  }
  catch(IOException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static boolean validation(Source source, Schema schema) throws AException
 {
  if(source==null)throw new AException("source=null");
  if(schema==null)throw new AException("schema=null");
  Validator validator=schema.newValidator();
  try
  {
   ErrorHandler errorHandler = new AXSDErrorHandler();
   validator.setErrorHandler(errorHandler);
   validator.validate(source);
  }
  catch(SAXParseException ex)
  {
   int line=ex.getLineNumber();
   int column=ex.getColumnNumber();
   String msg="In line:"+line+" column:"+column+" found Error:\n "+ex.getMessage();
   throw new AException("SAXParseException "+msg,ex);
  }
  catch(SAXException ex)
  {
   String msg=ex.toString()+"\n"+ex.getMessage();
   throw new AException("SAXException "+msg,ex);
  }
  catch(IOException ex){throw new AException("IOException "+ex.getMessage(),ex);}
  return true;
 }
//---------------------------------------------------------------------------
 public static boolean validation(Node node,AXSDSchemaMap schemaMap) throws AException
 {
  if(node==null)throw new AException("node=null");
  Source source=new DOMSource(node);
  AXSDResourceResolver resolver=new AXSDResourceResolver("static validation");
  Schema schema=newSchema(schemaMap,resolver);
  return validation(source,schema);
 }
//---------------------------------------------------------------------------
 public static Source[] getSourceList(List<String> contentList)throws AException
 {
  if(contentList==null)throw new AException("contentList=null");
  ArrayList<Source> sourceList=new ArrayList();
  for(String content:contentList)
  {
   StringReader strRdr=new StringReader(content);
   Source source=new StreamSource(strRdr);
   sourceList.add(source);
  }
  Source[] srcs=new Source[sourceList.size()];
  for(int q=0;q<sourceList.size();q++) srcs[q]=sourceList.get(q);
  return srcs;
 }
//--------------------------------------------------------------------------- 
// public class ResourceResolver implements LSResourceResolver
// {
//  @Override
//  public LSInput resolveResource(String type,String namespaceURI,
//          String publicId,String systemId,String baseURI)
//  {
//   // note: in this sample, the XSD's are expected to be in the root of the classpath
////   InputStream resourceAsStream=this.getClass().getClassLoader().getResourceAsStream(systemId);
//   
////   return new AXSDInput(publicId,systemId,resourceAsStream);
//  }
// } 
//--------------------------------------------------------------------------- 
// public class Input implements LSInput
// {
//  private String publicId;
//  private String systemId;
//  @Override
//  public String getPublicId(){return publicId;}
//  @Override
//  public void setPublicId(String publicId){this.publicId=publicId;}
//  @Override
//  public String getBaseURI(){return null;}
//  @Override
//  public InputStream getByteStream(){return null;}
//  @Override
//  public boolean getCertifiedText(){return false;}
//  @Override
//  public Reader getCharacterStream(){return null;}
//  @Override
//  public String getEncoding(){return null;}
//  @Override
//  public String getStringData()
//  {
//   synchronized(inputStream)
//   {
//    try
//    {
//     byte[] input=new byte[inputStream.available()];
//     inputStream.read(input);
//     String contents=new String(input);
//     return contents;
//    }
//    catch(IOException ex)
//    {
//     //ex.printStackTrace();
//     System.out.println("Exception "+ex);
//     return null;
//    }
//   }
//  }
//  @Override
//  public void setBaseURI(String baseURI){}
//  @Override
//  public void setByteStream(InputStream byteStream){}
//  @Override
//  public void setCertifiedText(boolean certifiedText){}
//  @Override
//  public void setCharacterStream(Reader characterStream){}
//  @Override
//  public void setEncoding(String encoding){}
//  @Override
//  public void setStringData(String stringData){}
//  @Override
//  public String getSystemId(){return systemId;}
//  @Override
//  public void setSystemId(String systemId){this.systemId=systemId;}
//  public BufferedInputStream getInputStream(){return inputStream;}
//  public void setInputStream(BufferedInputStream inputStream){this.inputStream=inputStream;}
//  private BufferedInputStream inputStream;
//  public Input(String publicId,String sysId,InputStream input)
//  {
//   this.publicId=publicId;
//   this.systemId=sysId;
//   this.inputStream=new BufferedInputStream(input);
//  }
// }  
//--------------------------------------------------------------------------- 
 // note that if your XML already declares the XSD to which it has to conform, then there's no need to declare the schemaName here
 public void validate(String xml,String schemaName) throws Exception
 {
  // parse the XML into a document object
  Document document=ADOM.getDocument(xml);

  SchemaFactory factory=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

  // associate the schema factory with the resource resolver, which is responsible for resolving the imported XSD's
//  factory.setResourceResolver(new ResourceResolver());

  // note that if your XML already declares the XSD to which it has to conform, then there's no need to create a validator from a Schema object
  Source schemaFile=new StreamSource(getClass().getClassLoader().getResourceAsStream(schemaName));
  Schema schema=factory.newSchema(schemaFile);

  Validator validator=schema.newValidator();
  validator.validate(new DOMSource(document));
 }
//---------------------------------------------------------------------------
 
}
