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
public class AChar extends AClass
{
//---------------------------------------------------------------------------
 public final AValue value;
//---------------------------------------------------------------------------
 public AChar()throws AException{value=new AValue(AValue.CHAR);}
//---------------------------------------------------------------------------
 public AChar(Character value)throws AException
 {
  this.value=new AValue(AValue.CHAR,AData.CharToData(value));
 }
//---------------------------------------------------------------------------
 public AChar(Character value,boolean constant)throws AException
 {
  this.value=new AValue(AValue.CHAR,AData.CharToData(value),constant);
 }
//---------------------------------------------------------------------------
 public final Character getChar()throws AException{return value.getChar();}
//---------------------------------------------------------------------------
 public final void setChar(Character value)throws AException{this.value.setChar(value);}
//---------------------------------------------------------------------------
}

