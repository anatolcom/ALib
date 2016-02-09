/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.cases;

import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.varible.basic.AInt;

/**
 *
 * @author Пользователь
 */
public class AWhile extends ACase
{
//---------------------------------------------------------------------------
 private final ACase CONDITION;
//---------------------------------------------------------------------------
 public AWhile(ACase CONDITION)
 {
  this.CONDITION=CONDITION;
 }
//---------------------------------------------------------------------------
 @Override
 public boolean done(AConstText TEXT,int POS) throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(POS>=TEXT.value.length())throw new AException("POS>=SIZE");
//  if(!SYMBOL.is(getTextCase.toStr.charAt(POS)))throw new AException("First symbol out of area");
//  if(!SYMBOL.is(getTextCase.toStr.charAt(POS)))return -1;
  int pos=POS;
  textCase="";
  while(true)
  {
   if(pos>=TEXT.value.length())break;
   if(!CONDITION.done(TEXT,POS))break;
   textCase+=CONDITION.getTextCase();
   pos+=CONDITION.lenght();
  }
  if(textCase.isEmpty())return false;
  return true;
//  throw new UnsupportedOperationException("Not supported yet.");
 }
}
