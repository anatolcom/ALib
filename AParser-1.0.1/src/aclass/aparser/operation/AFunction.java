/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AException;
import aclass.aparser.action.AAction;
import aclass.aparser.core.AObjectSpace;
import aclass.aparser.object.AObject;
import aclass.aparser.object.AUnion;
import aclass.aparser.object.AValue;
import aclass.aparser.type.AUnionHeader;

/**
 *
 * @author Пользователь
 */
public class AFunction extends AOperation 
{
//---------------------------------------------------------------------------
 public final AUnionHeader inputHeader=new AUnionHeader();
 public final AUnionHeader outputHeader=new AUnionHeader();
//---------------------------------------------------------------------------
 public AFunction(String name,AAction action,AUnionHeader inputHeader,AUnionHeader outputHeader)throws AException
 {
  super(name,AValue.UNION,AValue.UNION,action);
  this.inputHeader.assign(inputHeader);
  this.outputHeader.assign(outputHeader);
 }
//---------------------------------------------------------------------------
 @Override
 public void process(AObject input,AObject output)throws AException
 {
  AUnion unionInput=(AUnion)input;
  AUnion unionOutput=(AUnion)output;
  sendError("process","under development");
  AObjectSpace space=new AObjectSpace();//???
  for(int q=0;q<unionInput.count();q++)space.define(unionInput.getName(q),unionInput.get(q));
  AObject object=action.run(space);
  unionOutput.assign(object);
//  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
