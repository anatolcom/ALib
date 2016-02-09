/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AException;
import aclass.aparser.AParser;
import aclass.aparser.action.AActionNULL;
import aclass.aparser.core.ACore;
import aclass.aparser.varible.AUnion;
import aclass.aparser.varible.AValue;

/**
 *
 * @author Пользователь
 */
public class AOperationTest extends AOperation
{
//---------------------------------------------------------------------------
 public AOperationTest(String name)throws AException
 {
  super(name,new AActionNULL());
  outputHeader.clear();
  outputHeader.add("text",AValue.STR);
 }
//---------------------------------------------------------------------------
// @Override
 public String getName(){return name;}
//---------------------------------------------------------------------------
 @Override
 public void process(AUnion input,AUnion output)throws AException
 {
  System.out.println("AOperationTest.process: "+name+".i: "+ACore.print(input));
//  AUnion output=new AUnion(outputHeader);
  output.set("text",input.get("text").copy());
  System.out.println("AOperationTest.process: "+name+".o: "+ACore.print(output));
//  return output;
 }
//---------------------------------------------------------------------------
}
