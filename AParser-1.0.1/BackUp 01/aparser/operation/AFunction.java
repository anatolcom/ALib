/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AException;
import aclass.aparser.action.AAction;
import aclass.aparser.core.AObjectSpace;
import aclass.aparser.varible.AObject;
import aclass.aparser.varible.AUnion;
import aclass.aparser.varible.AUnionHeader;

/**
 *
 * @author Пользователь
 */
public class AFunction extends AOperation 
{
//---------------------------------------------------------------------------
 public AFunction(String name,AAction action,AUnionHeader inputHeader,AUnionHeader outputHeader)throws AException
 {
  super(name,action);
  this.inputHeader.assign(inputHeader);
  this.outputHeader.assign(outputHeader);
 }
//---------------------------------------------------------------------------
 @Override
 public void process(AUnion input,AUnion output)throws AException
 {
  sendError("process","under development",null);
  AObjectSpace space=new AObjectSpace();//???
  for(int q=0;q<input.count();q++)space.define(input.getName(q),input.get(q));
  AObject object=action.run(space);
  output.assign(object);
//  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
