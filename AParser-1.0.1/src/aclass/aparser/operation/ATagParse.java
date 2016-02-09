/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.AParser;
import aclass.aparser.action.AActionNULL;
import aclass.aparser.cases.ACharArea;
import aclass.aparser.cases.AMarker;
import aclass.aparser.object.AObject;
import aclass.aparser.varible.basic.AInt;
import aclass.aparser.varible.basic.AStr;
import aclass.aparser.object.AUnion;
import aclass.aparser.type.AUnionHeader;
import aclass.aparser.object.AValue;


/**
 *
 * @author Пользователь
 */
public class ATagParse extends AOperation
{
//---------------------------------------------------------------------------
 public final AUnionHeader inputHeader=new AUnionHeader();
 public final AUnionHeader outputHeader=new AUnionHeader();
//---------------------------------------------------------------------------
 public ATagParse()throws AException
 {
  super("tagParse",AValue.UNION,AValue.UNION,new AActionNULL());
  
  inputHeader.add("text",AValue.STR);
  
  outputHeader.add("type",AValue.STR);
  outputHeader.add("prefix",AValue.STR);
  outputHeader.add("name",AValue.STR);
  outputHeader.add("params",AValue.STR);
 }
//---------------------------------------------------------------------------
 @Override
 public void process(AObject input,AObject output) throws AException//AUnion
 {
/*  System.out.println("ATagParse.process: i: "+input.toString());
  String text=((AValue)input.get("text")).getStr();
  AConstText constText=new AConstText(text);

  String type="";
  String prefix="";
  String name="";
  String params="";
  
  
  final AMarker TAG_START=new AMarker("<");
  final AMarker TAG_END=new AMarker(">");
  final AMarker CLOSE_SYMBOL=new AMarker("/");
  final AMarker META_SYMBOL=new AMarker("?");
//  final ACharArea NAME=new ACharArea();
//  NAME.add('a','z').add('A','Z').add('а','я').add('А','Я').add('ё').add('Ё');
  final AMarker PREFIX_SPLITTER=new AMarker(":");
  final ACharArea SPLITTER=new ACharArea();
  SPLITTER.add((char)0,(char)31);
  
  
  
  int start=0;
  int end=constText.value.length();
  
  if(!TAG_START.done(constText,start))throw new AException("< not found");
  start+=TAG_START.lenght();
  if(!TAG_END.done(constText,end-TAG_END.lenght()))throw new AException("> not found");
  end-=TAG_END.lenght();
  
  if(META_SYMBOL.done(constText,start))
  {
   start+=META_SYMBOL.lenght();
   if(!META_SYMBOL.done(constText,end-META_SYMBOL.lenght()))throw new AException("? end not found");
   end-=META_SYMBOL.lenght();
   type="meta";
  } 
  else
  {
   type="open";
   if(CLOSE_SYMBOL.done(constText,start))
   {
    start+=CLOSE_SYMBOL.lenght();
    type="close";
   }
   else 
   {
    if(!CLOSE_SYMBOL.done(constText,end-CLOSE_SYMBOL.lenght()))
    {
     end-=CLOSE_SYMBOL.lenght();
     type="single";
    }
   }
  } 
  AStr subStr=new AStr();
  AParser.read(constText,start,subStr,end-start);
//  AParser.split(constText,SPLITTER,null,null)
    
  
//  AUnion output=new AUnion(outputType);
  output.set("type",(new AStr(type)).value);
  output.set("prefix",(new AStr(prefix)).value);
  output.set("name",(new AStr(name)).value);
  output.set("params",(new AStr(params)).value);

  System.out.println("ATagParse.process: o: "+output.toString());
//  return output;*/
 }
//---------------------------------------------------------------------------
}
