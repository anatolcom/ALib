/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.action;

import aclass.AException;
import aclass.aparser.core.AObjectSpace;
import aclass.aparser.varible.AObject;

/**
 *
 * @author Пользователь
 */
public class AAssign extends AAction
{
//---------------------------------------------------------------------------
 private final String name;
 private final AObject object;
//---------------------------------------------------------------------------
 public AAssign(String name,AObject object)throws AException
 {
  this.name=name;
  this.object=object;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject run(AObjectSpace objectMap)throws AException
 {
  objectMap.reference(name).assign(object);
  return object;
 }
//---------------------------------------------------------------------------
}
