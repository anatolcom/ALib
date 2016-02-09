/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser;

import aclass.AClass;
import aclass.AException;
import aclass.aparser.cases.ACase;
import aclass.aparser.cases.ACharArea;
import aclass.aparser.operation.AOperation;
import aclass.aparser.object.AObject;
import aclass.aparser.object.AUnion;
import aclass.aparser.type.AUnionHeader;
import aclass.aparser.object.AValue;
import aclass.aparser.varible.basic.AInt;
import aclass.aparser.varible.basic.AStr;



/**
 *
 * @author Anatol
 */
public class AParser extends AClass
{
//---------------------------------------------------------------------------
 public static final ACase ENTER=new ACase()
 {
  @Override
  public boolean done(AConstText TEXT,int POS) throws AException
  {
   if(TEXT==null)throw new AException("text=null");
   if(TEXT.value.charAt(POS)==10)
   {
    char[] enter={(char)10};
    textCase=new String(enter);
    return true;
   }
   if(TEXT.value.charAt(POS)==13)
   {
    if(TEXT.value.length()>POS+1)
    {
     if(TEXT.value.charAt(POS+1)==10)
     {
      char[] enter={(char)13,(char)10};
      textCase=new String(enter);
      return true;
     } 
    }
    char[] enter={(char)13};
    textCase=new String(enter);
    return true;
   }
   textCase=null;
   return false;
  }
 };
//---------------------------------------------------------------------------
 public static final ACase END_OF_TEXT=new ACase()
 {
  @Override
  public boolean done(AConstText TEXT,int POS) throws AException
  {
   if(TEXT==null)throw new AException("text=null");
   if(POS<TEXT.value.length())
   {
    textCase=null;
    return false;
   }
   char[] endOfText={(char)0};
   textCase=new String(endOfText);
   return true;
  }
 };
//---------------------------------------------------------------------------
/* *
  * Метод определяет является ли содержимое
  * в тексте getTextCase.Value в позиции POS
  * равным по значению переносу строки.<br/>
  * В переменну Len.Value записывается
  * длинна идентификатора переноса строки.<br/>
  * @param getTextCase - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param Len - В переменную Len.Value записывается длинна идентификатора переноса строки.
  * @return true - да,<br/>
  *         false - нет.
  * /
 public static boolean isEnter(ARefStr getTextCase,final int POS,ARefByte Len)  //!!!
 {
  if(getTextCase==null)
  {
   sendGlobalError("isEnter","TEXT=null",null);
   return false;
  }
  if(Len==null)
  {
   sendGlobalError("isEnter","Len=null",null);
   return false;
  }
  if(getTextCase.Value.length()<POS+2)return false;
  if(getTextCase.Value.charAt(POS)!=13)return false;
  if(getTextCase.Value.charAt(POS+1)!=10)return false;
  Len.Value=2;
  return true;
 }*/
//---------------------------------------------------------------------------
/* *
  * Метод определяет является ли содержимое в тексте getTextCase.Value
  * в позиции POS равным по значению идентификатору MARKER.VALUE.<br/>
  * В переменну len.Value записывается длинна идентификатора MARKER.VALUE.<br/>
  * @param getTextCase - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param MARKER - Маркер.
  * @param len - В переменную Len.Value записывается длинна идентификатора ID.VALUE.
  * @return true - да,<br/>
  *         false - нет.
  * /
 public static boolean isMarker(ARefStr getTextCase,final int POS,final AMarker MARKER,ARefInt len)//!!!
 {
  if(getTextCase==null)
  {
   sendGlobalError("isMarker","TEXT=null",null);
   return false;
  }
  if(len==null)
  {
   sendGlobalError("isMarker","len=null",null);
   return false;
  }
  if(POS>=getTextCase.Value.length())
  {
   sendGlobalError("isMarker","POS>=SIZE",POS);
   return false;
  }
  String id=new String(MARKER.VALUE);
  int lenght=id.length();
  if(getTextCase.Value.length()<POS+lenght)return false;
  for(int q=0;q<lenght;q++)
  {
   if(getTextCase.Value.charAt(POS+q)==MARKER.VALUE[q])continue;
   return false;
  }
  len.Value=lenght;
  return true;
 }*/
//---------------------------------------------------------------------------
/* *
  * Метод выбирает слово из текста getTextCase.Value
  * начиная с позиции POS и записывает его в переменную word.Value.<br/>
  * @param getTextCase - Ссылка на текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param word - В переменную Word.Value записывается найденое слово.
  * @return Позиция после найденого слова.<br/>
  *         -1 - В случае ошибки.
  * /
 public static int getWord(ARefStr getTextCase,final int POS,ARefStr word,final ACharArea SYMBOL) //!!!
 {
  if(getTextCase==null)
  {
   sendGlobalError("getWord","getTextCase=null",null);
   return -1;
  }
  if(word==null)
  {
   sendGlobalError("getWord","word=null",null);
   return -1;
  }
  if(POS>=getTextCase.Value.length())
  {
   sendGlobalError("getWord","POS>=SIZE",POS);
   return -1;
  }
  if(!SYMBOL.is(getTextCase.Value.charAt(POS)))
  {
   sendGlobalError("getWord","Is not Word",null);
   return -1;
  }
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=getTextCase.Value.length())break;
   if(getTextCase.Value.charAt(pos)<32)break;
   if(!SYMBOL.is(getTextCase.Value.charAt(pos)))break;
   str+=getTextCase.Value.charAt(pos++);
  }
  word.Value=str;
  return pos;
 }*/
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из текста TEXT.data длинной len.
  * Выборка начинается с позиции POS, 
  * а её результат записывается в переменную subtext.print.<br/>
  * @param TEXT Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.print записывается найденый подтекст.
  * @param len длина выбираемого подтекста.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
 public static int read(AConstText TEXT,final int POS,AStr subtext,final int len) throws AException //!!!
 {
  if(TEXT==null)throw new AException("text=null");
  if(POS>=TEXT.value.length())throw new AException("POS>=SIZE");
  if(POS+len>TEXT.value.length())throw new AException("POS+len>SIZE");
  String str="";
  for(int q=0;q<len;q++)str+=TEXT.value.charAt(POS+q);
  subtext.setStr(str);
  return POS+len;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из текста TEXT.data 
  * пока найденые символы соответсвуют пространству SYMBOL.
  * Выборка начинается с позиции POS, 
  * а её результат записывается в переменную subtext.print.<br/>
  * @param TEXT Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.print записывается найденый подтекст.
  * @param SYMBOL Пространство символов допустимых в искомом подтексте.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
 public static int readWhile(AConstText TEXT,final int POS,AStr subtext,final ACharArea SYMBOL) throws AException //!!!
 {
  if(TEXT==null)throw new AException("text=null");
  if(POS>=TEXT.value.length())throw new AException("POS>=SIZE");
//  if(!SYMBOL.is(getTextCase.print.charAt(POS)))throw new AException("AParser","readWhile","First symbol out of area");
//  if(!SYMBOL.is(getTextCase.print.charAt(POS)))return -1;
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=TEXT.value.length())break;
   if(!SYMBOL.is(TEXT.value.charAt(pos)))break;
   str+=TEXT.value.charAt(pos++);
  }
  subtext.setStr(str);
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из текста TEXT.data
  * пока не будет найден символ из пространства SYMBOL.
  * Выборка начинается с позиции POS,
  * а её результат записывается в переменную subtext.print.<br/>
  * @param TEXT Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.print записывается найденый подтекст.
  * @param SYMBOL Пространство символов запрещённыйх в искомом подтексте.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
 public static int readUntil(AConstText TEXT,final int POS,AStr subtext,final ACharArea SYMBOL) throws AException //!!!
 {
  if(TEXT==null)throw new AException("text=null");
  if(POS>=TEXT.value.length())throw new AException("POS>=SIZE");
//  if(!SYMBOL.is(getTextCase.print.charAt(POS)))throw new AException("AParser","readWhile","First symbol out of area");
//  if(SYMBOL.is(getTextCase.print.charAt(POS)))return -1;
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=TEXT.value.length())break;
   if(SYMBOL.is(TEXT.value.charAt(pos)))break;
   str+=TEXT.value.charAt(pos++);
  }
  subtext.setStr(str);
  return pos;
 }
//---------------------------------------------------------------------------
/* *
  * Метод выбирает текст из TEXT.data начиная с позиции POS
  * и заканчивая позицией перед первым найденным маркером конца,
  * и записывает его в переменную phrase.data.<br/>
  * @param getTextCase - Ссылка на текст для синтаксического анализа.
  * @param POS - Позиция в тексте с которой начинается чтение.
  * @param print - В переменную phrase.data записывается найденая фраза.
  * @param END_OF_TEXT - маркер конца.
  * @return Позиция после найденого тега.<br/>
  *         -1 - В случае ошибки.
  * /
 public static int readToMarker(ARefStr getTextCase,final int POS,ARefStr print,final AMarker END) throws AException//???
 {
  return readToCase(getTextCase,POS,print,END);
 }*/
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из TEXT.data начиная с позиции POS
  * и заканчивая позицией конца текста,
  * и записывает его в переменную subtext.print.<br/>
  * @param TEXT Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.print записывается найденый подтекст.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
public static int readToEnd(AConstText TEXT,final int POS,AStr subtext) throws AException//???
 {
  if(TEXT==null)throw new AException("text=null");
  if(subtext==null)throw new AException("value=null");
  final int textLenght=TEXT.value.length();
  if(POS>=textLenght)throw new AException("POS>=SIZE");
  int pos=POS;
  String str="";
  while(pos<textLenght)str+=TEXT.value.charAt(pos++);
  subtext.setStr(str);
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из TEXT.data начиная с позиции POS
  * и заканчивая позицией перед первым найденным условием END,
  * и записывает его в переменную subtext.print.<br/>
  * @param TEXT Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.Value записывается найденая фраза.
  * @param END Условие завершения чтения.
  * @return Позиция после найденого маркера.<br/>
  *         -1 - В случае если маркер ненайден.
  * @throws AException в случае ошибки.
  */
 public static int readToCase(AConstText TEXT,final int POS,AStr subtext,final ACase END) throws AException//???
 {
  if(TEXT==null)throw new AException("text=null");
  if(subtext==null)throw new AException("value=null");
  final int textLenght=TEXT.value.length();
  if(POS>=textLenght)throw new AException("POS>=SIZE",POS);
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=textLenght)
   {
    subtext.setStr(str);
    return -1;
   }
   if(END.done(TEXT,pos))
   {
    pos+=END.lenght();
    break;
   }
   str+=TEXT.value.charAt(pos++);
  }
  subtext.setStr(str);
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод пропускает символы UNUSED в тексте TEXT.data
  * начиная с позиции POS.<br/>
  * @param TEXT Ссылка на текст для синтаксического анализа.
  * @param POS Позиция сравнения.
  * @param word В переменную Word.data записывается найденое слово.
  * @return Позиция после найденого слова.
  * @throws AException в случае ошибки.
  */
 public static int skip(AConstText TEXT,final int POS,final ACharArea UNUSED) throws AException 
 {
  if(TEXT==null)throw new AException("text=null");
  if(POS>=TEXT.value.length())throw new AException("POS>=SIZE");
//  if(!UNUSED.is(getTextCase.print.charAt(POS)))throw new AException("Is not Word");
  int pos=POS;
  while(true)
  {
   if(pos>=TEXT.value.length())break;
   if(TEXT.value.charAt(pos)<32)break;
   if(!UNUSED.is(TEXT.value.charAt(pos)))break;
   pos++;
  }
  return pos;
 }
//---------------------------------------------------------------------------
 public static String split(final AConstText TEXT,
                            final ACase SPLITTER,
                            final AOperation CONTENT,
                            final AOperation CONTEXT)throws AException
 {
  if(TEXT==null)throw new AException("text=null");
  if(SPLITTER==null)throw new AException("SPLITTER=null");
  if(TEXT.value.isEmpty())throw new AException("text is empty");
  
  try
  {
   AStr substr=new AStr();
   int pos=0;
   String processedText="";
   int contentIndex=0;
   int contextIndex=0;
   AUnionHeader inputHeader=new AUnionHeader();
   inputHeader.add("text",AValue.STR);
   inputHeader.add("index",AValue.INT);
   AUnionHeader outputHeader=new AUnionHeader();
   outputHeader.add("text",AValue.STR);

   while(true)
   {
    pos=AParser.readToCase(TEXT,pos,substr,SPLITTER);
    if(CONTEXT!=null)
    {
     AUnion input=new AUnion(inputHeader);
     input.set("text",substr.value.copy());
     input.set("index",(new AInt(contextIndex++)).value);
     AUnion output=new AUnion(outputHeader);
     CONTEXT.process(input,output);
//     ABasicValue oText=new ABasicValue(ABasicValue.STR,output.get("text").get(),AValue.VARIBLE);
     AStr oText=new AStr();
     oText.value.assign(output.get("text"));
     processedText+=oText.getStr();//output.get("text").getStr();
    }
    else processedText+=substr.getStr();
    if(pos==-1)break;
    if(CONTENT!=null)
    {
     AUnion input=new AUnion(inputHeader);
     input.set("text",(new AStr(SPLITTER.getTextCase())).value);
     input.set("index",(new AInt(contentIndex++)).value);
     AUnion output=new AUnion(outputHeader);
     CONTENT.process(input,output);
//     ABasicValue oText=new ABasicValue(ABasicValue.STR,output.get("text").get(),AValue.VARIBLE);
     AStr oText=new AStr();
     oText.value.assign(output.get("text"));
     processedText+=oText.getStr();//output.get("text").getStr();
    }
    else processedText+=SPLITTER.getTextCase();
    if(AParser.END_OF_TEXT.done(TEXT,pos))break;
   }
   return processedText;
  }
  catch(AException ex){throw new AException(ex);}
//  throw new AException("this function has not been realised");
 }
//---------------------------------------------------------------------------
 public static String split(final AConstText TEXT,
                            final ACase START,
                            final ACase END,
//                            final AOperation CONTENT_START,
                            final AOperation CONTENT,
//                            final AOperation CONTENT_END,
                            final AOperation CONTEXT,
                            boolean nested,
                            int level)throws AException
 {
  if(TEXT==null)throw new AException("text=null");
  if(START==null)throw new AException("START=null");
  if(END==null)throw new AException("END=null");
  if(TEXT.value.isEmpty())throw new AException("text is empty");
  
  try
  {
   AStr substr=new AStr();
   int pos=0;
   String processedText="";
   AInt contentIndex=new AInt(0,AValue.VARIBLE);
   int contextIndex=0;
   while(true)
   {
    pos=AParser.readToCase(TEXT,pos,substr,START);
//    if(CONTEXT!=null)
//    {
//     AVaribleList inputList=new AVaribleList();
//     inputList.add(new AVarible("getTextCase",substr,AVarible.COPY));
//     inputList.add(new AVarible("index",new AInt(contextIndex++),AVarible.COPY));
//     AVaribleList outputList=CONTEXT.process(inputList);
//     processedText+=outputList.get("getTextCase").print();
//    }
//    else processedText+=substr.print;
    processedText+=substr.getStr();
    if(pos==-1)break;
    
//    if(CONTENT!=null)
//    {
//     AVaribleList varList=new AVaribleList();
//     varList.add(new AVarible("getTextCase",new AStr(SPLITTER.getTextCase())));
//     varList.add(new AVarible("index",new AInt(contentIndex++)));
//     processedText+=CONTENT.process(varList);
//    }
//    else processedText+=SPLITTER.toString();
    processedText+=START.getTextCase();
    
    
    pos=AParser.readToCase(TEXT,pos,substr,END);
    
    if(pos==-1)throw new AException("\""+END.toString()+"\" not found");
    
//    if(CONTENT!=null)
//    {
//     AUnion inputList=new AUnion();
//     inputList.add(new AVarible("text",substr.getCopy(),false));
//     inputList.add(new AVarible("index",contentIndex.getCopy(),false));
//     inputList.add(new AVarible("level",new AInt(level,AValue.VARIBLE),false));
//     AUnion outputList=CONTENT.process(inputList);
//     processedText+=outputList.get("text").print();
//     contentIndex.data++;
//    }
//    else processedText+=substr.data;
    processedText+=substr.getStr();
    processedText+=END.getTextCase();
    
    if(AParser.END_OF_TEXT.done(TEXT,pos))break;
    
   }
   return processedText;
  }
  catch(AException ex){throw new AException(ex);}
//  throw new AException("this function has not been realised");
 }
//---------------------------------------------------------------------------
}
