/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.core;

import aclass.ACustomPtrArray;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AObjectSpaceMap extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int create(String name)throws AException
 {
  AObjectSpaceItem space=new AObjectSpaceItem(name);
  return PAdd(space);
 }
//---------------------------------------------------------------------------
// public int insert(AObjectSpaceItem value,int index)throws AException{return PInsert(value,index);}
//---------------------------------------------------------------------------
 public AObjectSpaceItem get(int index)throws AException{return (AObjectSpaceItem)PGetPtr(index);}
//---------------------------------------------------------------------------
// public AObjectSpaceItem get(int index)throws AException{return (AObjectSpaceItem)PGetPtr(index);}
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{PDelete(index);}
//---------------------------------------------------------------------------
// public void swap(int a,int b)throws AException{PSwap(a,b);}
//---------------------------------------------------------------------------
// public void move(int a,int b)throws AException{PMove(a,b);}
//---------------------------------------------------------------------------
// public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 
//---------------------------------------------------------------------------
}
