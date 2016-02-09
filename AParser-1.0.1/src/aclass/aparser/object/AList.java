/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.object;

import aclass.aparser.type.AType;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AList extends AObject
{
//---------------------------------------------------------------------------
 private final AValueList list=new AValueList();
//---------------------------------------------------------------------------
 public AList(AType type)throws AException
 {
  super(type);
 }
//---------------------------------------------------------------------------
 public int add(AValue value)throws AException
 {
  if(value==null)throw new AException("");
  if(!value.isType(type))throw new AException("type missmatch");
  return list.add(value);
 }
//---------------------------------------------------------------------------
// public int insert(AValue value,int index);
//---------------------------------------------------------------------------
 public AValue get(int index)throws AException{return list.get(index);}
//---------------------------------------------------------------------------
// public void delete(int index);
//---------------------------------------------------------------------------
// public void swap(int a,int b);
//---------------------------------------------------------------------------
// public void move(int a,int b);
//---------------------------------------------------------------------------
 public int count(){return list.count();}
//---------------------------------------------------------------------------
 @Override
 public int sizeof()throws AException
 {
  int size=0;
  for(int q=0;q<list.count();q++)size+=list.get(q).sizeof();
  return size;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject copy() throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
 @Override
 protected void assignData(AObject object) throws AException
 {
  testTypeMissmatch(object);
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
