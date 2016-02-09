/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.action;

import aclass.AException;
import aclass.aparser.core.AObjectSpace;
import aclass.aparser.object.AObject;
import aclass.aparser.type.AType;

/**
 *
 * @author Пользователь
 */
public class ADefine extends AAction
{
//---------------------------------------------------------------------------
 private final String name;
 private final AType type;
//---------------------------------------------------------------------------
 public ADefine(String name,AType type)throws AException
 {
  this.name=name;
  this.type=type;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject run(AObjectSpace objectSpace)throws AException
 {
  //нужно объявить объект и поместить его в список обьектов определённого пространства имён
//  objectSpace.define(name,type);
//  return type;
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
