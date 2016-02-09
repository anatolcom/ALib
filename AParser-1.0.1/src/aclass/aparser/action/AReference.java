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
public class AReference extends AAction
{
//---------------------------------------------------------------------------
 @Override
 public AObject run(AObjectSpace objectSpace)throws AException
 {
  //ищет в объект по имени и возвращает его значение какимто чюдесным образом 
  //возможно делает текущим
  String name="";//???
  AObject object=objectSpace.reference(name);
  return object;
//  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------

}
