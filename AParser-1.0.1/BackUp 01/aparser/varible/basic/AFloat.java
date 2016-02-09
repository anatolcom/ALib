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
public class AFloat extends AClass
{
//---------------------------------------------------------------------------
 public final AValue value;
//---------------------------------------------------------------------------
 public AFloat()throws AException{value=new AValue(AValue.FLOAT);}
//---------------------------------------------------------------------------
 public AFloat(Float value)throws AException
 {
  this.value=new AValue(AValue.FLOAT,AData.FloatToData(value));
 }
//---------------------------------------------------------------------------
 public AFloat(Float value,boolean constant)throws AException
 {
  this.value=new AValue(AValue.FLOAT,AData.FloatToData(value),constant);
 }
//---------------------------------------------------------------------------
 public final Float getFloat()throws AException{return value.getFloat();}
//---------------------------------------------------------------------------
 public final void setFloat(Float value)throws AException{this.value.setFloat(value);}
//---------------------------------------------------------------------------
}
