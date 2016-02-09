/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.cases;

import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.AParser;
import aclass.aparser.backup.APort;
import aclass.aparser.varible.basic.AStr;

/**
 *
 * @author Пользователь
 */
public class ABlock extends ACase
{
//---------------------------------------------------------------------------
 private final ACase START;
 private final ACase END;
//---------------------------------------------------------------------------
// APort portContentStart=new APort("ContentStart");
 APort portContent=new APort("Content");
// APort portContentEnd=new APort("ContentEnd");
//---------------------------------------------------------------------------
 public ABlock(final ACase START,final ACase END) throws AException
 {
  this.START=START;
  this.END=END;
//  this.portList.add(portContentStart);
  this.portList.add(portContent);
//  this.portList.add(portContentEnd);
 }
//---------------------------------------------------------------------------
 @Override
 public boolean done(AConstText TEXT,int POS) throws AException
 {
  if(TEXT==null)throw new AException("text=null");
  if(START==null)throw new AException("START=null");
  if(END==null)throw new AException("END=null");
  if(TEXT.value.isEmpty())throw new AException("text is empty");

  try
  {
   AStr substr=new AStr();
   int pos=POS;
   if(!START.done(TEXT,pos))
   {
    textCase=null;
    return false;
   }
   pos+=START.lenght();
   pos=AParser.readToCase(TEXT,pos,substr,END);
   if(pos==-1)throw new AException("\""+END.toString()+"\" not found");
   textCase=START.getTextCase()+substr.getStr()+END.getTextCase();
   
//   if(portContent.getOperation()!=null)
//   {
//    AUnionHeader inputHeader=new AUnionHeader();
//    AUnion input=new AUnion(inputHeader);
//    input.add(new AVarible("getTextCase",new AStr(textCase,AValue.VARIBLE)));
//    AUnion output=portContent.getOperation().process(input);
//    textProcess+=output.get("getTextCase").print();
//   }
//   else textProcess+=substr.getStr();

   
   return true;
  }
  catch(AException ex){throw new AException(ex);}
  
//  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
