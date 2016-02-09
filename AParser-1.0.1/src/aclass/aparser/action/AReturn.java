/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.action;

import aclass.AException;
import aclass.aparser.core.AObjectSpace;
import aclass.aparser.object.AObject;
import aclass.aparser.object.AUnion;

/**
 *
 * @author Пользователь
 */
public class AReturn extends AAction
{
//---------------------------------------------------------------------------
 private final AUnion union;
//---------------------------------------------------------------------------
 public AReturn(AUnion union)throws AException
 {
  this.union=union;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject run(AObjectSpace objectSpace)throws AException
 {
//  objectSpace.define("return",union);
//  throw new UnsupportedOperationException("Not supported yet.");
  return union;
 }
//---------------------------------------------------------------------------
}