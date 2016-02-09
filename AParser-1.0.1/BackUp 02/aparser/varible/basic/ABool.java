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
public class ABool extends AClass
{
//---------------------------------------------------------------------------
 public final AValue value;
//---------------------------------------------------------------------------
 public ABool()throws AException{value=new AValue(AValue.BOOL);}
//---------------------------------------------------------------------------
 public ABool(boolean value)throws AException
 {
  this.value=new AValue(AValue.BOOL,AData.BoolToData(value));
 }
//---------------------------------------------------------------------------
 public ABool(boolean value,boolean constant)throws AException
 {
  this.value=new AValue(AValue.BOOL,AData.BoolToData(value),constant);
 }
//---------------------------------------------------------------------------
 public final String getStr()throws AException{return value.getStr();}
//---------------------------------------------------------------------------
 public final void setStr(String value)throws AException{this.value.setStr(value);}
//---------------------------------------------------------------------------
}
