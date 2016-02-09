/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AStr extends AValue
{
//---------------------------------------------------------------------------
 public String value="";
//---------------------------------------------------------------------------
 @Override public int getSize(){return value.length();}
//---------------------------------------------------------------------------
 @Override public int getType(){return TYPE_STR;}
//---------------------------------------------------------------------------
 public AStr(){}
//---------------------------------------------------------------------------
 public AStr(AStr data){assign(data);}
//---------------------------------------------------------------------------
 public AStr(String value){this.value=value;}
//---------------------------------------------------------------------------
 public final void assign(AStr data){this.value=data.value;}
//---------------------------------------------------------------------------
 @Override
 public void assign(AValue data)throws AException
 {
  if(!data.isType(getType()))throw new AException("type missmatch");
  assign((AStr)data);
 }
//---------------------------------------------------------------------------
 public int compare(AStr data)
 {
  return this.value.compareTo(data.value);
 }
//---------------------------------------------------------------------------
 @Override
 public int compare(AValue data) throws AException
 {
  if(!data.isType(getType()))throw new AException("type missmatch");
  return compare((AStr)data);
 }
//---------------------------------------------------------------------------
 @Override
 public AValue getCopy(){return new AStr(this);}
//---------------------------------------------------------------------------
// public String getValue()
// {
//  return value;
// }
////---------------------------------------------------------------------------
// public void setValue(String value)
// {
//  this.value=value;
// }
//---------------------------------------------------------------------------
 @Override
 public String toStr()throws AException
 {
  return value;
 }
//---------------------------------------------------------------------------
 @Override
 public void fromStr(String value)throws AException
 {
  this.value=value;
 }
//---------------------------------------------------------------------------
}
