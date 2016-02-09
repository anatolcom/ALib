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
public class ABool extends AValue
{
//---------------------------------------------------------------------------
 public boolean value=false;
//---------------------------------------------------------------------------
 @Override public int getSize(){return 1;}
//---------------------------------------------------------------------------
 @Override public int getType(){return TYPE_BOOL;}
//---------------------------------------------------------------------------
 public ABool(){}
//---------------------------------------------------------------------------
 public ABool(ABool data){assign(data);}
//---------------------------------------------------------------------------
 public ABool(boolean value){this.value=value;}
//---------------------------------------------------------------------------
 public final void assign(ABool data){this.value=data.value;}
//---------------------------------------------------------------------------
 @Override
 public void assign(AValue data)throws AException
 {
  if(!data.isType(getType()))throw new AException("type missmatch");
  assign((ABool)data);
 }
//---------------------------------------------------------------------------
 public int compare(ABool data)
 {
  if(this.value==true&data.value==false)return  1;
  if(this.value==false&data.value==true)return -1;
  return 0;
 }
//---------------------------------------------------------------------------
 @Override
 public int compare(AValue data) throws AException
 {
  if(!data.isType(getType()))throw new AException("type missmatch");
  return compare((ABool)data);
 }
//---------------------------------------------------------------------------
 @Override
 public AValue getCopy(){return new ABool(this);}
//---------------------------------------------------------------------------
// public boolean getValue()
// {
//  return value;
// }
////---------------------------------------------------------------------------
// public void setValue(boolean value)
// {
//  this.value=value;
// }
//---------------------------------------------------------------------------
 @Override
 public String toStr()throws AException
 {
  if(!value) return "false";
  return "true";
 }
//---------------------------------------------------------------------------
 @Override
 public void fromStr(String value)throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
