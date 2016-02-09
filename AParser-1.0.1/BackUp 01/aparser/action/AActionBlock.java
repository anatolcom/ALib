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
public class AActionBlock extends AAction
{
//---------------------------------------------------------------------------
 private final AActionList list;
//---------------------------------------------------------------------------
 public AActionBlock(AActionList list)throws AException
 {
  if(list==null)throw new AException("list=null");
  this.list=list;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject run(AObjectSpace objectMap)throws AException
 {
  try{for(int q=0;q<list.count();q++)list.get(q).run(objectMap);}
  catch(AException ex){throw new AException(ex);}
  return null;
 }
//---------------------------------------------------------------------------
}
