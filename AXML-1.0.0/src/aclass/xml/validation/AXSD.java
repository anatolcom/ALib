/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;

import aclass.AClass;
import aclass.AException;
import aclass.AIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Node;
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
 public boolean validation(String xml,AXSDSchemaMap schemaMap)throws AException
 {
  if(xml==null)throw new AException("xml=null");
  try
  {
   Schema schema=newSchema(schemaMap);
   InputStream io=AIO.openInputStreamFromString(xml);
   Source source=new StreamSource(io);
   if(!validation(source,schema))return false;
  }
  catch(IOException ex){throw new AException(ex);}
  return true;
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
//   SAXParserFactory saxParserfactory=SAXParserFactory.newInstance();
//   saxParserfactory.setValidating(true);
//   saxParserfactory.setNamespaceAware(true);
////   saxParserfactory.setFeature("http://xml.org/sax/features/validation", true);
////   saxParserfactory.setFeature("http://apache.org/xml/features/validation/schema", true);
////   saxParserfactory.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
//   saxParserfactory.setContent(schema);
//   SAXParser parser = saxParserfactory.newSAXParser();
//   XMLReader reader=parser.getXMLReader();
//   ErrorHandler errorHandler = new AErrorHandler();
//   reader.setErrorHandler(errorHandler);
//   reader.parse(new InputSource(xmlFileName));
  }
  catch(SAXParseException ex)
  {
   int line=ex.getLineNumber();
   int column=ex.getColumnNumber();
   String msg="In line:"+line+" column:"+column+" found Error:\n "+ex.getMessage();
   throw new AException("SAXException "+msg,ex);
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
// public static boolean validation(Node node,Schema schema) throws AException
// {
//  if(node==null)throw new AException("node=null");
//  Source source=new DOMSource(node);
//  return validation(source,schema);
// }
//---------------------------------------------------------------------------
 public static boolean validation(Node node,AXSDSchemaMap schemaMap) throws AException
 {
  if(node==null)throw new AException("node=null");
  Source source=new DOMSource(node);
  Schema schema=newSchema(schemaMap);
  return validation(source,schema);
 }
//---------------------------------------------------------------------------
 public static Schema newSchema(AXSDSchemaMap schemaMap)throws AException
 {
  try
  {
   SchemaFactory schemaFactory=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
   ArrayList<String> contentList=new ArrayList();
   for(int q=0;q<schemaMap.size();q++)contentList.add(schemaMap.get(q).getContent());
   Source sourceList[]=getSourceList(contentList);
   return schemaFactory.newSchema(sourceList);
  }
  catch(SAXException ex)
  {
   throw new AException("Cannot create new schema. SAXException "+ex.getMessage(),ex);
  }
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
}
