/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 *
 * @author Пользователь
 */
public class APtrArray extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int getCount(){return super.PGetCount();}
//---------------------------------------------------------------------------
 public void setCount(int value){super.PSetCount(value);}
//---------------------------------------------------------------------------
 public int add(Object value){return super.PAdd(value);}
//---------------------------------------------------------------------------
 public int insert(Object value,int index){return super.PInsert(value,index);}
//---------------------------------------------------------------------------
 public int create(){return super.PNew();}
//---------------------------------------------------------------------------
 public int create(int index){return super.PNew(index);}
//---------------------------------------------------------------------------
 public void delete(int index){super.PDelete(index);}
//---------------------------------------------------------------------------
 public void swap(int a,int b){super.PSwap(a,b);}
//---------------------------------------------------------------------------
 public void move(int a,int b){super.PMove(a,b);}
//---------------------------------------------------------------------------
 public void clear(){super.PClear();}
//---------------------------------------------------------------------------
 public boolean exists(int index){return super.PExists(index);}
//---------------------------------------------------------------------------
 public Object getPtr(int index){return super.PGetPtr(index);}
//---------------------------------------------------------------------------
 public Object getPtrConst(int index){return super.PGetPtrConst(index);}
//---------------------------------------------------------------------------
 public void setPtr(int index,Object Ptr){super.PSetPtr(index,Ptr);}
//---------------------------------------------------------------------------
}
