/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adb;

import aclass.ACustomPtrArray;
/**
 *
 * @author Anatol
 */
public class ADBIndexList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public ADBIndexList(){}
//---------------------------------------------------------------------------
 public ADBIndexList(ADBIndexList value){assign(value);}
//---------------------------------------------------------------------------
 public final void assign(ADBIndexList value)
 {
  clear();
  for(int q=0;q<value.count();q++)add(value.get(q));
 }
//---------------------------------------------------------------------------
 public int add(final int value){return PAdd((Integer)value);}
//---------------------------------------------------------------------------
 public void remove(int index){PDelete(index);}
//---------------------------------------------------------------------------
 public int get(int index){return ((Integer)PGetPtr(index)).intValue();}
//---------------------------------------------------------------------------
 public void set(int index,int value){PSetPtr(index,(Integer)value);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 public void clear(){PClear();}
//---------------------------------------------------------------------------
}
