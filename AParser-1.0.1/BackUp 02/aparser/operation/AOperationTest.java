/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AException;
import aclass.aparser.AParser;
import aclass.aparser.action.AActionNULL;
import aclass.aparser.core.ACore;
import aclass.aparser.object.AObject;
import aclass.aparser.object.AUnion;
import aclass.aparser.type.AUnionHeader;
import aclass.aparser.object.AValue;

/**
 *
 * @author Пользователь
 */
public class AOperationTest extends AOperation
{
//---------------------------------------------------------------------------
 public final AUnionHeader inputHeader=new AUnionHeader();
 public final AUnionHeader outputHeader=new AUnionHeader();
//---------------------------------------------------------------------------
 public AOperationTest(String name)throws AException
 {
  super(name,AValue.UNION,AValue.UNION,new AActionNULL());
  outputHeader.clear();
  outputHeader.add("text",AValue.STR);
 }
//---------------------------------------------------------------------------
// @Override
 public String getName(){return name;}
//---------------------------------------------------------------------------
 @Override
 public void process(AObject input,AObject output)throws AException//AUnion
 {
  AUnion unionInput=(AUnion)input;
  AUnion unionOutput=(AUnion)output;
  System.out.println("AOperationTest.process: "+name+".i: "+ACore.print(input));
//  AUnion output=new AUnion(outputType);
  unionOutput.set("text",unionInput.get("text").copy());
  System.out.println("AOperationTest.process: "+name+".o: "+ACore.print(output));
//  return output;
 }
//---------------------------------------------------------------------------
}
