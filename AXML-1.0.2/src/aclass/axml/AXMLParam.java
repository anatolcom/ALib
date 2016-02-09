/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.axml;
import aclass.AClass;
import aclass.AException;
/**
 * Параметр XML тега, состоящий из инени name и значения value.
 * @author Anatol
 */
public class AXMLParam extends AClass
{
//---------------------------------------------------------------------------
 public String name="";
 public String value="";
//---------------------------------------------------------------------------
 public AXMLParam(){}
//---------------------------------------------------------------------------
 public AXMLParam(AXMLParam value)throws AException{assign(value);}
//---------------------------------------------------------------------------
 public AXMLParam(String name,String value)
 {
  this.name=name;
  this.value=value;
 }
//---------------------------------------------------------------------------
 public final void assign(AXMLParam value)throws AException
 {
  if(value==null)throw new AException("value=null");
  this.name=value.name;
  this.value=value.value;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "AXMLParam{"+"name="+name+",value="+value+'}';
 }
//---------------------------------------------------------------------------
}
