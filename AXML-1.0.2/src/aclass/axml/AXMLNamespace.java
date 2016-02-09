/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.axml;

import aclass.AException;

/**
 *
 * @author Anatol
 */
public class AXMLNamespace
{
//---------------------------------------------------------------------------
 public final String prefix;
 public final String uri;
//---------------------------------------------------------------------------
 public AXMLNamespace(AXMLNamespace value)throws AException
 {
  if(value==null)throw new AException("value=null");
  this.prefix=value.prefix;
  this.uri=value.uri;
 }
//---------------------------------------------------------------------------
 public AXMLNamespace(String prefix,String uri)throws AException
 {
  if(prefix==null)throw new AException("prefix=null");
  if(uri==null)throw new AException("uri=null");
  this.prefix=prefix;
  this.uri=uri;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "AXMLNameSpace{"+"prefix="+prefix+",uri="+uri+'}';
 }
//---------------------------------------------------------------------------
}
