/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible.basic;
import aclass.AClass;
import aclass.AException;
import aclass.aparser.object.AData;
import aclass.aparser.object.AValue;

/**
 *
 * @author Пользователь
 */
public class AStr extends AClass
{
//---------------------------------------------------------------------------
 public final AValue value;
//---------------------------------------------------------------------------
 public AStr()throws AException{value=new AValue(AValue.STR);}
//---------------------------------------------------------------------------
 public AStr(String value)throws AException
 {
  this.value=new AValue(AValue.STR,AData.StrToData(value));
 }
//---------------------------------------------------------------------------
 public AStr(String value,boolean constant)throws AException
 {
  this.value=new AValue(AValue.STR,AData.StrToData(value),constant);
 }
//---------------------------------------------------------------------------
 public final String getStr()throws AException{return value.getStr();}
//---------------------------------------------------------------------------
 public final void setStr(String value)throws AException{this.value.setStr(value);}
//---------------------------------------------------------------------------
}
