/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.cases;

import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.AParser;
//import aclass.aparser.backup.APort;
import aclass.aparser.operation.AOperation;
import aclass.aparser.varible.basic.AStr;
import aclass.aparser.object.AUnion;
import aclass.aparser.object.AVarible;
import aclass.aparser.object.AVaribleList;

/**
 *
 * @author Anatol
 */
public class AMarker extends ACase
{
//---------------------------------------------------------------------------
 private final char VALUE[];
//---------------------------------------------------------------------------
 private boolean caseSensitive=true;
//---------------------------------------------------------------------------
// APort portMarker=new APort("Marker");
//---------------------------------------------------------------------------
 public AMarker(final String IDENTIFER) throws AException
 {
  this.VALUE=IDENTIFER.toCharArray();
//  this.portList.add(portMarker);
 }
//---------------------------------------------------------------------------
 public boolean isCaseSensitive(){return caseSensitive;}
//---------------------------------------------------------------------------
 public void setCaseSensitive(boolean caseSensitive){this.caseSensitive=caseSensitive;}
//---------------------------------------------------------------------------
 public String value(){return new String(VALUE);}
//---------------------------------------------------------------------------
 @Override
 public String toString(){return new String(VALUE);}
//---------------------------------------------------------------------------
 @Override
 public boolean done(AConstText TEXT,int POS) throws AException
 {
  if(TEXT==null) throw new AException("text=null");
  if(POS>=TEXT.value.length()) throw new AException("POS>=SIZE");
  String id=new String(VALUE);
  int length=id.length();
  if(TEXT.value.length()<POS+length) 
  {
   textCase=null;
   return false;
  }
  for(int q=0;q<length;q++)
  {
   char a=TEXT.value.charAt(POS+q);
   char b=VALUE[q];
   if(!caseSensitive)
   {
    a=Character.toLowerCase(a);
    b=Character.toLowerCase(b);
   }
   if(a==b) continue;
   textCase=null;
   return false;
  }
  AStr strTextCase=new AStr();          
  AParser.read(TEXT,POS,strTextCase,length);
  textCase=strTextCase.getStr();
//  AOperation operation=portMarker.getOperation();
//  if(operation!=null)
//  {
//   AUnion inputList=new AUnion(operation.getInputHeader());
//   inputList.add(new AVarible("getTextCase",new AStr(textCase,false),false));
//   AUnion outputList=operation.process(inputList);
//   textProcess=outputList.get("getTextCase").print();
//  }
  return true;
 }
//---------------------------------------------------------------------------
}
