/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;

import static aclass.AClass.sendGlobalError;
import static aclass.AClass.sendGlobalWarning;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Пользователь
 */
public class AXSDErrorHandler implements ErrorHandler
{
//---------------------------------------------------------------------------
 private boolean ignore=false;
//---------------------------------------------------------------------------
 public boolean isIgnore(){return ignore;}
 public void setIgnore(boolean ignore){this.ignore=ignore;}
//---------------------------------------------------------------------------
 private String print(SAXParseException ex)
 {
  String msg=ex.getMessage();
  String systemId=ex.getSystemId();
  String publicId=ex.getPublicId();
  int lineNumber=ex.getLineNumber();
  int columnNumber=ex.getColumnNumber();
  String str="Parse [line:"+lineNumber+" col:"+columnNumber+"] "+msg;
  if(publicId!=null)str+="; publicId: "+publicId;
  if(systemId!=null)str+="; systemId: "+systemId;
  str+=".";
  return str;
 }
//---------------------------------------------------------------------------
 @Override
 public void warning(SAXParseException ex)
 {
  if(ignore)return;
  sendGlobalError("warning",print(ex));
 }
//---------------------------------------------------------------------------
 @Override
 public void error(SAXParseException ex) throws SAXParseException
 {
  if(ignore)return;
  String msg=ex.getMessage();
  if(msg.equals("Document is invalid: no grammar found."))return;
  sendGlobalError("error",print(ex));
  if(msg.contains("DOCTYPE"))return;//???????????????????????????????????????     Костыль
  throw ex;
 }
//---------------------------------------------------------------------------
 @Override
 public void fatalError(SAXParseException ex) throws SAXException
 {
  sendGlobalError("fatalError",print(ex));
  throw ex;
 }
//---------------------------------------------------------------------------
}
