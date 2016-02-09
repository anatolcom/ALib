/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 *
 * @author Пользователь
 */
public class APtrTable extends ACustomPtrTable
{
//---------------------------------------------------------------------------
 public APtrTable(boolean sorted,int autoincId){super(sorted,autoincId);}
//---------------------------------------------------------------------------
 public void add(int id)throws AException{super.PAdd(id);}
//---------------------------------------------------------------------------
 public void add(int id,Object ptr)throws AException{super.PAdd(id,ptr);}
//---------------------------------------------------------------------------
 public void setPtrByIndex(int index,Object ptr)throws AException{super.PSetPtrByIndex(index,ptr);}
//---------------------------------------------------------------------------
 public void setPtr(int id,Object ptr)throws AException{super.PSetPtr(id,ptr);}
//---------------------------------------------------------------------------
 public Object getPtrByIndex(int index)throws AException{return super.PGetPtrByIndex(index);}
//---------------------------------------------------------------------------
 public Object getPtr(int id)throws AException{return super.PGetPtr(id);}
//---------------------------------------------------------------------------
 public int getId(int index)throws AException{return super.PGetId(index);}
//---------------------------------------------------------------------------
 public void deleteByIndex(int index)throws AException{super.PDeleteByIndex(index);}
//---------------------------------------------------------------------------
 public void delete(int id)throws AException{super.PDelete(id);}
//---------------------------------------------------------------------------
 public void swapByIndex(int a,int b)throws AException{super.PSwapByIndex(a,b);}
//---------------------------------------------------------------------------
 public void moveByIndex(int a,int b)throws AException{super.PMoveByIndex(a,b);}
//---------------------------------------------------------------------------
 public void swap(int aId,int bId)throws AException{super.PSwap(aId,bId);}
//---------------------------------------------------------------------------
 public void move(int aId,int bId)throws AException{super.PMove(aId,bId);}
//---------------------------------------------------------------------------
 public int getIndex(int id){return super.PGetIndex(id);}
//---------------------------------------------------------------------------
 public int count(){return super.PCount();}
//---------------------------------------------------------------------------
 public void clear(){super.PClear();}
//---------------------------------------------------------------------------
 public boolean exists(int id){return super.PExists(id);}
//---------------------------------------------------------------------------
}
