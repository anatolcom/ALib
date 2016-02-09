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
 public int add(AVarible value)throws AException
 {
  int index=insertIndex(value.name);
  if(index==-1)throw new AException("Varible with name=\""+value.name+"\" alredy exists");
  return PInsert(value,index);
 }
//---------------------------------------------------------------------------
 public AVarible get(int index){return (AVarible)PGetPtr(index);}
//---------------------------------------------------------------------------
 public void delete(int index){PDelete(index);}
//---------------------------------------------------------------------------
 public void swap(int a,int b){PSwap(a,b);}
//---------------------------------------------------------------------------
 public void move(int a,int b){PMove(a,b);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 private int insertIndex(String name)
 {
  for(int q=0;q<count();q++)
  {
   int cmp=get(q).name.compareTo(name);
   if(cmp==0)return -1;
   if(cmp>0)return q;
  }
  return count();
 }
//---------------------------------------------------------------------------
 public boolean exists(String name)
 {
  for(int q=0;q<count();q++)if(get(q).name.equals(name))return true;
  return false;
 }
//---------------------------------------------------------------------------
}
