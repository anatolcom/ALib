/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.core;

import aclass.ACustomPtrArray;
import aclass.AException;
import aclass.aparser.object.AObject;

/**
 *
 * @author Пользователь
 */
public class AObjectSpace extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 private class AItem
 {
  /**
   * имя ссылки
   */
  String name;
  /**
   * список ссылок на объекты
   */
  AObjectList objectList=new AObjectList();
 }
//---------------------------------------------------------------------------
 public AObjectSpace(){super();}
//---------------------------------------------------------------------------
/**
 * метод добавляет именованную ссылку на объект.<br/>
 * @param name имя ссылки
 * @param object ссылка на объект
 * @return
 * @throws AException 
 */
 public void define(String name,AObject object)throws AException
 {
  sendError("registred","under development",null);
  int index=index(name);
  if(index!=-1)throw new AException("Varible with name=\""+name+"\" alredy exists");
  AItem item=new AItem();
  item.name=name;
  item.objectList.add(object);
  PAdd(item);
//  return PInsert(item,index);
 }
//---------------------------------------------------------------------------
 public AObject reference(String name)throws AException
 {
  sendError("registred","under development",null);
  int index=index(name);
  if(index==-1)throw new AException("name=\""+name+"\" not found");
  AItem item=(AItem)PGetPtr(index);
  return item.objectList.get(0);
 }
//---------------------------------------------------------------------------
 private AItem getItem(int index)throws AException{return (AItem)PGetPtr(index);}
//---------------------------------------------------------------------------
// public AVarible get(String name)throws AException
// {
//  for(int q=0;q<count();q++)
//  {
//   AVarible varible=get(q);
//   if(varible.getName().equals(name))return varible;
//  }
//  throw new AException("Varible with name=\""+name+"\" not found");
// }
//---------------------------------------------------------------------------
// public void delete(int index)throws AException{PDelete(index);}
//---------------------------------------------------------------------------
// public void swap(int a,int b)throws AException{PSwap(a,b);}
//---------------------------------------------------------------------------
// public void move(int a,int b)throws AException{PMove(a,b);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
// private int insertIndex(String name)throws AException
// {
//  for(int q=0;q<count();q++)
//  {
//   int cmp=get(q).name.compareTo(name);
//   if(cmp==0)return -1;
//   if(cmp>0)return q;
//  }
//  return count();
// }
//---------------------------------------------------------------------------
 private int index(String name) throws AException
 {
  for(int q=0;q<count();q++)if(getItem(q).name.equals(name))return q;
  return -1;
 }
//---------------------------------------------------------------------------
 public boolean exists(String name)throws AException
 {
  for(int q=0;q<count();q++)if(getItem(q).name.equals(name))return true;
  return false;
 }
//---------------------------------------------------------------------------
 public void test() throws AException
 {
  for(int q=0;q<count();q++)sendInfo("test",getItem(q).name,null);
 }
}