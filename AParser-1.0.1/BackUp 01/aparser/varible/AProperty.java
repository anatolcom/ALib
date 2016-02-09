/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;

import aclass.AClass;
import aclass.AException;
import aclass.aparser.operation.AOperation;

/**
 *
 * @author Пользователь
 */
public class AProperty extends AClass
{
//---------------------------------------------------------------------------
// private AObject object=null;
//---------------------------------------------------------------------------
 private final AOperation getter;
 private final AOperation setter;
//---------------------------------------------------------------------------
 public AProperty(AOperation getter,AOperation setter)throws AException
 {
  if((getter==null)&&(setter==null))throw new AException("getter=null and setter=null");
  this.getter=getter;
  this.setter=setter;
 }
//---------------------------------------------------------------------------
// public AProperty(AObject getter,AObject setter)
// {
//  AUnionHeader thisInputHeader=new AUnionHeader();
//  AUnionHeader thisOutputHeader=new AUnionHeader();
//  thisOutputHeader.add("sizeof",AValue.INT);
//  AUnion thisOutput=new AUnion(thisOutputHeader);
//  thisOutput.get("sizeof").assign(new AValue(AValue.INT,AData.IntToData(sizeof())));
//  AFunction f_this=new AFunction("this",new AReturn(thisOutput),thisInputHeader,thisOutputHeader);
//  operationList.registred("this",f_this);
//  
//  
//  this.getter=getter;
//  this.setter=setter;
// }
////---------------------------------------------------------------------------
 
//---------------------------------------------------------------------------
 public boolean canGet()
 {
  if(getter==null)return false;
  return true;
 }
//---------------------------------------------------------------------------
 public boolean canSet()
 {
  if(setter==null)return false;
  return true;
 }
//--------------------------------------------------------------------------- 
 public AObject get()throws AException
 {
  if(!canGet())throw new AException("getter not defined");
  return null;
 }
//---------------------------------------------------------------------------
 public void set(AObject object)throws AException
 {
  if(!canSet())throw new AException("setter not defined");
 }
//---------------------------------------------------------------------------
}
