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
public class ADefine extends AAction
{
//---------------------------------------------------------------------------
 private final String name;
 private final AObject object;
//---------------------------------------------------------------------------
 public ADefine(String name,AObject object)throws AException
 {
  this.name=name;
  this.object=object;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject run(AObjectSpace objectSpace)throws AException
 {
  //нужно объявить объект и поместить его в список обьектов определённого пространства имён
  objectSpace.define(name,object);
  return object;
//  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
