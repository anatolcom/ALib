/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.core;

import aclass.AClass;
import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.AParser;
import aclass.aparser.object.AObject;
import aclass.aparser.object.AUnion;
import aclass.aparser.object.AValue;
import java.util.List;

/**
 *
 * @author Пользователь
 */
public class ACore extends AClass
{
//---------------------------------------------------------------------------
 private final AObjectSpaceMap objectSpaceMap=new AObjectSpaceMap();
//---------------------------------------------------------------------------
 public ACore()throws AException
 {
  
 }
//---------------------------------------------------------------------------
 public void run(List<AInstruction> instructionList)
 {
//  for(AInstruction instruction:instructionList)instruction.run();
 }
//---------------------------------------------------------------------------
// public void doInstruction(AInstruction instruction)
// {
//  
// }
////---------------------------------------------------------------------------
 public static String toFormatedChar(final char CHARACTER)
 {
  char slash='\\';
  char apostrof='\'';
  char tab='\t';
  if(CHARACTER==slash)return "\\"+slash;
  if(CHARACTER==tab)return "\\"+"t";
  if(CHARACTER==apostrof)return "\\"+apostrof;
  return ""+CHARACTER;
 }
//---------------------------------------------------------------------------
 public static String toFormatedString(final String TEXT)throws AException
 {
  if(TEXT==null) throw new AException("TEXT=null");
  AConstText text=new AConstText(TEXT);
  String formatedStr="";
  char quot='"';
  char slash='\\';
  char tab='\t';
  for(int q=0;q<TEXT.length();q++)
  {
   char a=TEXT.charAt(q);
   if(AParser.ENTER.done(text,q))
   {
    formatedStr+="\\n";
    q+=AParser.ENTER.lenght();//????????????????????????????????????????????????????????????????
    continue;
   }
   if(a==tab)
   {
    formatedStr+="\\t";
    continue;
   }
   if(a==quot)formatedStr+="\\";
   if(a==slash)formatedStr+="\\";
   formatedStr+=a;
  }
  return formatedStr;
 }
//---------------------------------------------------------------------------
 public static String print(AObject obj)throws AException
 {
//  AFunction operation=(AFunction)obj.getOperationList().get("this");
//  AUnion input=new AUnion(operation.inputHeader);
//  AUnion output=new AUnion(operation.outputHeader);
//  operation.process(input,output);
//  AValue vSizeof=(AValue)output.get("sizeof");
//  Integer sizeof=vSizeof.getInt();
  Integer sizeof=obj.sizeof();
  if(sizeof==null)sendGlobalError("print","value sizeof not initialized"); //throw new AException("value sizeof not initialized");
  if(obj.typeof().isType(AValue.BOOL))  
  {
   AValue value=(AValue)obj;
   return printBool(value.getBool())+"$"+sizeof;
  }
  if(obj.typeof().isType(AValue.CHAR))  
  {
   AValue value=(AValue)obj;
   return printChar(value.getChar())+"$"+sizeof;
  }
  if(obj.typeof().isType(AValue.INT))  
  {
   AValue value=(AValue)obj;
   return printInt(value.getInt())+"$"+sizeof;
  }
  if(obj.typeof().isType(AValue.FLOAT))  
  {
   AValue value=(AValue)obj;
   return printFloat(value.getFloat())+"$"+sizeof;
  }
  if(obj.typeof().isType(AValue.STR))  
  {
   AValue value=(AValue)obj;
   return printStr(value.getStr())+"$"+sizeof;
  }
  if(obj.typeof().isType(AValue.UNION))
  {
   AUnion union=(AUnion)obj;
   String str="";
   for(int q=0;q<union.count();q++)
   {
    if(q!=0)str+=",";
    AObject item=union.get(q);
    str+=item.typeof().Name+" "+union.getHeader().getName(q)+"="+print(item);
   }
   return "("+str+")"+"$"+sizeof;
  }
  return "Value with type "+obj.typeof().Name+" not printed"+"$"+sizeof;
 }
//---------------------------------------------------------------------------
 public static String printBool(Boolean value)throws AException
 {
  if(value==null)return "null";
  if(value)return "true";
  return "false";
 }
//---------------------------------------------------------------------------
 public static String printChar(Character value)throws AException
 {
  if(value==null)return "null";
  return "\'"+toFormatedChar(value)+"\'";
//  return value;
 }
//---------------------------------------------------------------------------
 public static String printInt(Integer value)throws AException
 {
  if(value==null)return "null";
  return Integer.toString(value);
 }
//---------------------------------------------------------------------------
 public static String printFloat(Float value)throws AException
 {
  if(value==null)return "null";
  return Float.toString(value);
 }
//---------------------------------------------------------------------------
 public static String printStr(String value)throws AException
 {
  if(value==null)return "null";
  return "\""+toFormatedString(value)+"\"";
 }
//---------------------------------------------------------------------------
}
