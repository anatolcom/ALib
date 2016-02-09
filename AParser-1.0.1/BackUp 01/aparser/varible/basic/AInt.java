/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible.basic;
import aclass.AClass;
import aclass.AException;
import aclass.aparser.varible.AData;
import aclass.aparser.varible.AValue;

/**
 *
 * @author Пользователь
 */
public class AInt extends AClass
{
//---------------------------------------------------------------------------
 public final AValue value;
//---------------------------------------------------------------------------
 public AInt()throws AException{value=new AValue(AValue.INT);}
//---------------------------------------------------------------------------
 public AInt(Integer value)throws AException
 {
  this.value=new AValue(AValue.INT,AData.IntToData(value));
 }
//---------------------------------------------------------------------------
 public AInt(Integer value,boolean constant)throws AException
 {
  this.value=new AValue(AValue.INT,AData.IntToData(value),constant);
 }
//---------------------------------------------------------------------------
 public final Integer getInt()throws AException{return value.getInt();}
//---------------------------------------------------------------------------
 public final void setInt(Integer value)throws AException{this.value.setInt(value);}
//---------------------------------------------------------------------------
}
