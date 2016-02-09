/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 *
 * @author Anatol
 * @version 0.1.0.1
 */
public class APtrArray extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int getCount(){return super.PGetCount();}
//---------------------------------------------------------------------------
 public void setCount(int value)throws AException{super.PSetCount(value);}
//---------------------------------------------------------------------------
 public int add(Object value)throws AException{return super.PAdd(value);}
//---------------------------------------------------------------------------
 public int insert(Object value,int index)throws AException{return super.PInsert(value,index);}
//---------------------------------------------------------------------------
 public int create()throws AException{return super.PNew();}
//---------------------------------------------------------------------------
 public int create(int index)throws AException{return super.PNew(index);}
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{super.PDelete(index);}
//---------------------------------------------------------------------------
 public void swap(int a,int b)throws AException{super.PSwap(a,b);}
//---------------------------------------------------------------------------
 public void move(int a,int b)throws AException{super.PMove(a,b);}
//---------------------------------------------------------------------------
 public void clear(){super.PClear();}
//---------------------------------------------------------------------------
 public boolean exists(int index){return super.PExists(index);}
//---------------------------------------------------------------------------
 public Object getPtr(int index)throws AException{return super.PGetPtr(index);}
//---------------------------------------------------------------------------
 public Object getPtrConst(int index)throws AException{return super.PGetPtrConst(index);}
//---------------------------------------------------------------------------
 public void setPtr(int index,Object Ptr)throws AException{super.PSetPtr(index,Ptr);}
//---------------------------------------------------------------------------
}
