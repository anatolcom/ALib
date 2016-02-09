/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.object;
import aclass.ACustomPtrArray;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AValueList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int add(AValue value)throws AException{return PAdd(value);}
//---------------------------------------------------------------------------
 public int insert(AValue value,int index)throws AException{return PInsert(value,index);}
//---------------------------------------------------------------------------
 public AValue get(int index)throws AException{return (AValue)PGetPtr(index);}
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
