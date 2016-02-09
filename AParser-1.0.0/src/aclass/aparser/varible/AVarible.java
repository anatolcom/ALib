/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;
import aclass.AClass;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AVarible extends AClass
{
//---------------------------------------------------------------------------
 public String name="";
 public AValue value=null;
//---------------------------------------------------------------------------
 public AVarible(String name,AValue value)
 {
  super();
  this.name=name;
  this.value=value;
 }
//---------------------------------------------------------------------------
 public final void assign(AVarible param) throws AException
 {
  this.name=param.name;
  this.value.assign(param.value);
 }
//---------------------------------------------------------------------------
// public AStr getName()
// {
//  return name;
// }
////---------------------------------------------------------------------------
// public void setName(AStr name)
// {
//  this.name.assign(name);
// }
////---------------------------------------------------------------------------
// public AValue getValue()
// {
//  return value;
// }
////---------------------------------------------------------------------------
// public void setValue(AValue value)
// {
//  this.value=value;
// }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  try
  {
   if(value==null)return name+"=NULL";
   return value.getTypeName()+" "+name+"="+value.toStr();
  }
  catch(AException ex)
  {
   sendError("toString",ex.getMessage(),null);
   return name;
  }
 }
}
