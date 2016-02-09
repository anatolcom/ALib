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
 @Override
 public void warning(SAXParseException ex)
 {
  if(ignore)return;
  sendGlobalWarning("warning",ex.getMessage(),null);
 }
//---------------------------------------------------------------------------
 @Override
 public void error(SAXParseException ex) throws SAXParseException
 {
  if(ignore)return;
  String msg=ex.getMessage();
  if(msg.equals("Document is invalid: no grammar found."))return;
  sendGlobalError("error",msg,null);
  if(msg.contains("DOCTYPE"))return;//???????????????????????????????????????     Костыль
  throw ex;
 }
//---------------------------------------------------------------------------
 @Override
 public void fatalError(SAXParseException ex) throws SAXException
 {
  sendGlobalError("fatalError",ex.getMessage(),null);
  throw ex;
 }
//---------------------------------------------------------------------------
}
