/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.object;

import aclass.aparser.type.AType;
import aclass.aparser.type.AUnionHeader;
import aclass.AClass;
import aclass.AException;
import aclass.aparser.action.AReturn;
import aclass.aparser.operation.AFunction;
import aclass.aparser.operation.AOperationList;

/**
 *
 * @author Пользователь
 */
public abstract class AObject extends AClass//AOperation
{
//---------------------------------------------------------------------------
 protected final AType type;
//---------------------------------------------------------------------------
 protected final AOperationList operationList=new AOperationList();
//---------------------------------------------------------------------------
// public final void initialize()throws AException
// {
//  AUnionHeader thisInputHeader=new AUnionHeader();
//  AUnionHeader thisOutputHeader=new AUnionHeader();
//  thisOutputHeader.add("sizeof",AValue.INT);
//  AUnion thisOutput=new AUnion(thisOutputHeader);
//  thisOutput.get("sizeof").assign(new AValue(AValue.INT,AData.IntToData(sizeof())));
//  AFunction f_this=new AFunction("this",new AReturn(thisOutput),thisInputHeader,thisOutputHeader);
//  operationList.registred("this",f_this);
// }
//---------------------------------------------------------------------------
 public AObject(AType type)throws AException
 {
  this.type=type;
//  initialize();
 }
//---------------------------------------------------------------------------
/**
 * @return тип объекта
 * @throws AException в случае ошибки
 */
 public final AType typeof(){return type;}
//---------------------------------------------------------------------------
// public final boolean isType(AObject object){return this.type.isType(object.type);}
 public final void testTypeMissmatch(AObject object)throws AException
 {
  if(this.type.isType(object.type))return;
  throw new AException("type missmatch. expected type: "+this.type.Name+". obtained type: "+object.type.Name);
 }
//---------------------------------------------------------------------------
/**
 * @return размер в байтах
 * @throws AException в случае ошибки
 */
 public abstract int sizeof()throws AException;
//---------------------------------------------------------------------------
 public final void construct()throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
 public final void destroy()throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
 public void assign(AObject object)throws AException
 {
  testTypeMissmatch(object);
  assignData(object);
  this.operationList.assign(object.operationList);
 }
//---------------------------------------------------------------------------
 protected abstract void assignData(AObject object)throws AException;
//---------------------------------------------------------------------------
 public abstract AObject copy()throws AException;
//---------------------------------------------------------------------------
 public final AOperationList getOperationList()throws AException{return operationList;}
//---------------------------------------------------------------------------
}
