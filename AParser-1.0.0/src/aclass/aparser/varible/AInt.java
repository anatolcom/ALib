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
public class AInt extends AValue
{
//---------------------------------------------------------------------------
 public int value=0;
//---------------------------------------------------------------------------
 @Override public int getSize(){return 4;}
//---------------------------------------------------------------------------
 @Override public int getType(){return TYPE_INT;}
//---------------------------------------------------------------------------
 public AInt(){}
//---------------------------------------------------------------------------
 public AInt(AInt data){assign(data);}
//---------------------------------------------------------------------------
 public AInt(int value){this.value=value;}
//---------------------------------------------------------------------------
 public final void assign(AInt data){this.value=data.value;}
//---------------------------------------------------------------------------
 @Override
 public void assign(AValue data)throws AException
 {
  if(!data.isType(getType()))throw new AException("type missmatch");
  assign((AInt)data);
 }
//---------------------------------------------------------------------------
 public int compare(AInt data)
 {
  if(this.value>data.value)return  1;
  if(this.value<data.value)return -1;
  return 0;
 }
//---------------------------------------------------------------------------
 @Override
 public int compare(AValue data) throws AException
 {
  if(!data.isType(getType()))throw new AException("type missmatch");
  return compare((AInt)data);
 }
//---------------------------------------------------------------------------
 @Override
 public AValue getCopy(){return new AInt(this);}
//---------------------------------------------------------------------------
// public int getValue()
// {
//  return value;
// }
////---------------------------------------------------------------------------
// public void setValue(int value)
// {
//  this.value=value;
// }
//---------------------------------------------------------------------------
 @Override
 public String toStr()throws AException
 {
  return Integer.toString(value);
 }
//---------------------------------------------------------------------------
 @Override
 public void fromStr(String value)throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
