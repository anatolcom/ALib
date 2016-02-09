/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;
import aclass.ACustomPtrArray;

/**
 *
 * @author Пользователь
 */
public class AValueList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int add(AValue value){return PAdd(value);}
//---------------------------------------------------------------------------
 public int insert(AValue value,int index){return PInsert(value,index);}
//---------------------------------------------------------------------------
 public AValue get(int index){return (AValue)PGetPtr(index);}
//---------------------------------------------------------------------------
 public void delete(int index){PDelete(index);}
//---------------------------------------------------------------------------
 public void swap(int a,int b){PSwap(a,b);}
//---------------------------------------------------------------------------
 public void move(int a,int b){PMove(a,b);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
}
