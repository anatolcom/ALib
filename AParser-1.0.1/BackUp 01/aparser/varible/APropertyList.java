/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;

import aclass.ACustomPtrArray;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class APropertyList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int add(AProperty value)throws AException{return PAdd(value);}
//---------------------------------------------------------------------------
 public int insert(AProperty value,int index)throws AException{return PInsert(value,index);}
//---------------------------------------------------------------------------
 public AProperty get(int index)throws AException{return (AProperty)PGetPtr(index);}
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{PDelete(index);}
//---------------------------------------------------------------------------
 public void swap(int a,int b)throws AException{PSwap(a,b);}
//---------------------------------------------------------------------------
 public void move(int a,int b)throws AException{PMove(a,b);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
}
