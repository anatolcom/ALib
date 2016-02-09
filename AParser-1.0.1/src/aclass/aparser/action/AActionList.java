/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.action;

import aclass.ACustomPtrArray;
import aclass.AException;
import aclass.aparser.object.AValue;

/**
 *
 * @author Пользователь
 */
public class AActionList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int add(AAction value)throws AException{return PAdd(value);}
//---------------------------------------------------------------------------
 public AAction get(int index)throws AException{return (AAction)PGetPtr(index);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
}
