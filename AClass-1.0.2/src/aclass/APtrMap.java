/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 *
 * @author Пользователь
 */
public class APtrMap extends ACustomPtrMap
{
//---------------------------------------------------------------------------
 public APtrMap(boolean sorted){super(sorted);}
//---------------------------------------------------------------------------
 public void add(String name)throws AException{super.PAdd(name);}
//---------------------------------------------------------------------------
 public void add(String name,Object ptr)throws AException{super.PAdd(name,ptr);}
//---------------------------------------------------------------------------
 public void setPtr(int index,Object ptr)throws AException{super.PSetPtr(index,ptr);}
//---------------------------------------------------------------------------
 public void setPtr(String name,Object ptr)throws AException{super.PSetPtr(name,ptr);}
//---------------------------------------------------------------------------
 public Object getPtr(int index)throws AException{return super.PGetPtr(index);}
//---------------------------------------------------------------------------
 public Object getPtr(String name)throws AException{return super.PGetPtr(name);}
//---------------------------------------------------------------------------
 public String getName(int index)throws AException{return super.PGetName(index);}
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{super.PDelete(index);}
//---------------------------------------------------------------------------
 public void delete(String name)throws AException{super.PDelete(name);}
//---------------------------------------------------------------------------
 public void swap(int a,int b)throws AException{super.PSwap(a,b);}
//---------------------------------------------------------------------------
 public void move(int a,int b)throws AException{super.PMove(a,b);}
//---------------------------------------------------------------------------
 public void swap(String aName,String bName)throws AException{super.PSwap(aName,bName);}
//---------------------------------------------------------------------------
 public void move(String aName,String bName)throws AException{super.PMove(aName,bName);}
//---------------------------------------------------------------------------
 public int getIndex(String name){return super.PGetIndex(name);}
//---------------------------------------------------------------------------
 public int count(){return super.PCount();}
//---------------------------------------------------------------------------
 public void clear(){super.PClear();}
//---------------------------------------------------------------------------
 public boolean exists(String name){return super.PExists(name);}
//---------------------------------------------------------------------------
}
