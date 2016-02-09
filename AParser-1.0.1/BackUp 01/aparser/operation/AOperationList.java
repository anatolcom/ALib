/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.ACustomPtrArray;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AOperationList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 private class AItem
 {
  public String name;
  public AOperation operation;
  public AItem(String name,AOperation operation)
  {
   this.name=name;
   this.operation=operation;
  }
 }
//---------------------------------------------------------------------------
 public void assign(AOperationList value)throws AException
 {
  PClear();
  for(int q=0;q<PGetCount();q++)PAdd(value.getItem(q));
 }
//---------------------------------------------------------------------------
 public int registred(String name,AOperation operation)throws AException
 {
  AItem item=new AItem(name,operation);
  return PAdd(item);
 }
//---------------------------------------------------------------------------
 public AOperation get(String name)throws AException{return getItem(name).operation;}
//---------------------------------------------------------------------------
 private AItem getItem(int index)throws AException{return (AItem)PGetPtr(index);}
//---------------------------------------------------------------------------
 private AItem getItem(String name)throws AException
 {
  for(int q=0;q<PGetCount();q++)
  {
   AItem item=getItem(q);
   sendInfo("getItem","item:"+item.name,null);
   if(item.name.equals(name))return item;
  }
  throw new AException("Operation with name=\""+name+"\" not found");
 }
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
}