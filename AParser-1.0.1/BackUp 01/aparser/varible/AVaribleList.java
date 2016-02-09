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
public class AVaribleList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public AVaribleList(){super();}
//---------------------------------------------------------------------------
 public int add(AVarible varible)throws AException
 {
  int index=insertIndex(varible.getName());
  if(index==-1)throw new AException("Varible with name=\""+varible.getName()+"\" alredy exists");
  return PInsert(varible,index);
 }
//---------------------------------------------------------------------------
 public AVarible get(int index)throws AException{return (AVarible)PGetPtr(index);}
//---------------------------------------------------------------------------
 public AVarible get(String name)throws AException
 {
  for(int q=0;q<count();q++)
  {
   AVarible varible=get(q);
   if(varible.getName().equals(name))return varible;
  }
  throw new AException("Varible with name=\""+name+"\" not found");
 }
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{PDelete(index);}
//---------------------------------------------------------------------------
 public void swap(int a,int b)throws AException{PSwap(a,b);}
//---------------------------------------------------------------------------
 public void move(int a,int b)throws AException{PMove(a,b);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 private int insertIndex(String name)throws AException
 {
  for(int q=0;q<count();q++)
  {
   int cmp=get(q).getName().compareTo(name);
   if(cmp==0)return -1;
   if(cmp>0)return q;
  }
  return count();
 }
//---------------------------------------------------------------------------
 public boolean exists(String name)throws AException
 {
  for(int q=0;q<count();q++)if(get(q).getName().equals(name))return true;
  return false;
 }
//---------------------------------------------------------------------------
}
