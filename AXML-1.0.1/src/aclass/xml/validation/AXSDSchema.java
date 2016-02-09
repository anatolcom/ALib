/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;

import aclass.AClass;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AXSDSchema extends AClass
{
//---------------------------------------------------------------------------
 private String namespaceURI="";
 private String content="";
//---------------------------------------------------------------------------
 public AXSDSchema(){}
//---------------------------------------------------------------------------
 public String getNamespaceURI(){return namespaceURI;}
 public void setNamespaceURI(String namespaceURI)throws AException
 {
  if(namespaceURI==null)throw new AException("namespaceURI=null");
  this.namespaceURI=namespaceURI;
 }
//---------------------------------------------------------------------------
 public String getContent(){return content;}
 public void setContent(String content)throws AException
 {
  if(content==null)throw new AException("content=null");
  this.content=content;
 }
//---------------------------------------------------------------------------
}
