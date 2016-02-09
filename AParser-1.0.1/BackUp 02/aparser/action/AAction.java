/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.action;

import aclass.AException;
import aclass.aparser.core.AObjectSpace;
import aclass.aparser.object.AObject;

/**
 *
 * @author Пользователь
 */
public abstract class AAction 
{
//---------------------------------------------------------------------------
// public static final int DEFINE=      0x0001;
// public static final int OPERATION=   0x0002;
//---------------------------------------------------------------------------
// protected final AObjectSpace objectMap;
//---------------------------------------------------------------------------
// public AAction()throws AException
// {
////  if(objectMap==null)throw new AException("objectMap=null");
//  this.objectMap=objectMap;
// }
//---------------------------------------------------------------------------
// public void registred(String name,AObject object)throws AException{objectMap.registred(name,object);}
//---------------------------------------------------------------------------
 public abstract AObject run(AObjectSpace objectMap)throws AException;
//---------------------------------------------------------------------------
}
