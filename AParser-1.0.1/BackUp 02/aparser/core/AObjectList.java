/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.core;

import aclass.ACustomPtrArray;
import aclass.AException;
import aclass.aparser.object.AObject;

/**
 *
 * @author Пользователь
 */
public class AObjectList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int add(AObject value)throws AException{return PAdd(value);}
//---------------------------------------------------------------------------
 public AObject get(int index)throws AException{return (AObject)PGetPtr(index);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
}