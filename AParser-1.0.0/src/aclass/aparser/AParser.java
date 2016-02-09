/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser;

import aclass.AClass;
import aclass.AException;
import aclass.aparser.varible.AInt;
import aclass.aparser.varible.AStr;
import aclass.aparser.varible.AValue;
//import java.lang.Character;


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
  public boolean done(AStr text,int POS,AInt len) throws AException
  {
   if(len==null) throw new AException("len=null");
   len.value=done(text,POS);
   if(len.value==0) return false;
   return true;
  }
  @Override
  public int done(AStr text,int POS) throws AException
  {
   if(text==null)throw new AException("text=null");
   if(text.value.length()<POS+2) return 0;
   if(text.value.charAt(POS)!=13) return 0;
   if(text.value.charAt(POS+1)!=10) return 0;
   return 2;
  }
 };
//---------------------------------------------------------------------------
 public static final ACase END_OF_TEXT=new ACase()
 {
  @Override
  public boolean done(AStr text,int POS,AInt len) throws AException
  {
   if(len==null) throw new AException("len=null");
   len.value=done(text,POS);
   if(len.value==0) return false;
   return true;
  }
  @Override
  public int done(AStr text,int POS) throws AException
  {
   if(text==null)throw new AException("text=null");
   if(POS<text.value.length())return 0;
   return 1;
  }
 };
//---------------------------------------------------------------------------
// public static final ACase CHAR_AREA=new ACase()
// {
//  @Override
//  public boolean done(ARefStr text,int POS,ARefInt len) throws AException
//  {
//   if(text==null)throw new AException("AParser","done","text=null");
//   if(len==null)throw new AException("AParser","done","len=null");
//   if(POS<text.Value.length())return false;
//   len.Value=1;
//   return true;
//  }
// };
//---------------------------------------------------------------------------
//static final ACase END_OF_TEXT=new ACase()
// {
//  @Override
//  public boolean done(ARefStr text,int POS,ARefInt len)
//  {
//   if(text==null)
//   {
//    sendGlobalError("isEnter","test=null",null);
//    return false;
//   }
//   if(len==null)
//   {
//    sendGlobalError("isEnter","Len=null",null);
//    return false;
//   }
//   if(text.Value.length()<POS+2) return false;
//   if(text.Value.charAt(POS)!=13) return false;
//   if(text.Value.charAt(POS+1)!=10) return false;
//   len.Value=2;
//   return true;
//  }
// };
//---------------------------------------------------------------------------
/* *
  * Метод определяет является ли содержимое
  * в тексте text.Value в позиции POS
  * равным по значению переносу строки.<br/>
  * В переменну Len.Value записывается
  * длинна идентификатора переноса строки.<br/>
  * @param text - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param Len - В переменную Len.Value записывается длинна идентификатора переноса строки.
  * @return true - да,<br/>
  *         false - нет.
  * /
 public static boolean isEnter(ARefStr text,final int POS,ARefByte Len)  //!!!
 {
  if(text==null)
  {
   sendGlobalError("isEnter","TEXT=null",null);
   return false;
  }
  if(Len==null)
  {
   sendGlobalError("isEnter","Len=null",null);
   return false;
  }
  if(text.Value.length()<POS+2)return false;
  if(text.Value.charAt(POS)!=13)return false;
  if(text.Value.charAt(POS+1)!=10)return false;
  Len.Value=2;
  return true;
 }*/
//---------------------------------------------------------------------------
/* *
  * Метод определяет является ли содержимое в тексте text.Value
  * в позиции POS равным по значению идентификатору MARKER.VALUE.<br/>
  * В переменну len.Value записывается длинна идентификатора MARKER.VALUE.<br/>
  * @param text - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param MARKER - Маркер.
  * @param len - В переменную Len.Value записывается длинна идентификатора ID.VALUE.
  * @return true - да,<br/>
  *         false - нет.
  * /
 public static boolean isMarker(ARefStr text,final int POS,final AMarker MARKER,ARefInt len)//!!!
 {
  if(text==null)
  {
   sendGlobalError("isMarker","TEXT=null",null);
   return false;
  }
  if(len==null)
  {
   sendGlobalError("isMarker","len=null",null);
   return false;
  }
  if(POS>=text.Value.length())
  {
   sendGlobalError("isMarker","POS>=SIZE",POS);
   return false;
  }
  String id=new String(MARKER.VALUE);
  int lenght=id.length();
  if(text.Value.length()<POS+lenght)return false;
  for(int q=0;q<lenght;q++)
  {
   if(text.Value.charAt(POS+q)==MARKER.VALUE[q])continue;
   return false;
  }
  len.Value=lenght;
  return true;
 }*/
//---------------------------------------------------------------------------
/* *
  * Метод выбирает слово из текста text.Value
  * начиная с позиции POS и записывает его в переменную word.Value.<br/>
  * @param text - Ссылка на текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param word - В переменную Word.Value записывается найденое слово.
  * @return Позиция после найденого слова.<br/>
  *         -1 - В случае ошибки.
  * /
 public static int getWord(ARefStr text,final int POS,ARefStr word,final ACharArea SYMBOL) //!!!
 {
  if(text==null)
  {
   sendGlobalError("getWord","text=null",null);
   return -1;
  }
  if(word==null)
  {
   sendGlobalError("getWord","word=null",null);
   return -1;
  }
  if(POS>=text.Value.length())
  {
   sendGlobalError("getWord","POS>=SIZE",POS);
   return -1;
  }
  if(!SYMBOL.is(text.Value.charAt(POS)))
  {
   sendGlobalError("getWord","Is not Word",null);
   return -1;
  }
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=text.Value.length())break;
   if(text.Value.charAt(pos)<32)break;
   if(!SYMBOL.is(text.Value.charAt(pos)))break;
   str+=text.Value.charAt(pos++);
  }
  word.Value=str;
  return pos;
 }*/
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из текста text.value длинной len.
  * Выборка начинается с позиции POS, 
  * а её результат записывается в переменную subtext.value.<br/>
  * @param text Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.value записывается найденый подтекст.
  * @param len длина выбираемого подтекста.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
 public static int read(AStr text,final int POS,AStr subtext,final int len) throws AException //!!!
 {
  if(text==null)throw new AException("text=null");
  if(POS>=text.value.length())throw new AException("POS>=SIZE");
  if(POS+len>text.value.length())throw new AException("POS+len>SIZE");
  String str="";
  for(int q=0;q<len;q++)str+=text.value.charAt(POS+q);
  subtext.value=str;
  return POS+len;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из текста text.value 
  * пока найденые символы соответсвуют пространству SYMBOL.
  * Выборка начинается с позиции POS, 
  * а её результат записывается в переменную subtext.value.<br/>
  * @param text Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.value записывается найденый подтекст.
  * @param SYMBOL Пространство символов допустимых в искомом подтексте.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
 public static int readWhile(AStr text,final int POS,AStr subtext,final ACharArea SYMBOL) throws AException //!!!
 {
  if(text==null)throw new AException("text=null");
  if(POS>=text.value.length())throw new AException("POS>=SIZE");
//  if(!SYMBOL.is(text.value.charAt(POS)))throw new AException("AParser","readWhile","First symbol out of area");
//  if(!SYMBOL.is(text.value.charAt(POS)))return -1;
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=text.value.length())break;
   if(!SYMBOL.is(text.value.charAt(pos)))break;
   str+=text.value.charAt(pos++);
  }
  subtext.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из текста text.value
  * пока не будет найден символ из пространства SYMBOL.
  * Выборка начинается с позиции POS,
  * а её результат записывается в переменную subtext.value.<br/>
  * @param text Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.value записывается найденый подтекст.
  * @param SYMBOL Пространство символов запрещённыйх в искомом подтексте.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
 public static int readUntil(AStr text,final int POS,AStr subtext,final ACharArea SYMBOL) throws AException //!!!
 {
  if(text==null)throw new AException("text=null");
  if(POS>=text.value.length())throw new AException("POS>=SIZE");
//  if(!SYMBOL.is(text.value.charAt(POS)))throw new AException("AParser","readWhile","First symbol out of area");
//  if(SYMBOL.is(text.value.charAt(POS)))return -1;
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=text.value.length())break;
   if(SYMBOL.is(text.value.charAt(pos)))break;
   str+=text.value.charAt(pos++);
  }
  subtext.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/* *
  * Метод выбирает текст из text.Value начиная с позиции POS
  * и заканчивая позицией перед первым найденным маркером конца,
  * и записывает его в переменную phrase.Value.<br/>
  * @param text - Ссылка на текст для синтаксического анализа.
  * @param POS - Позиция в тексте с которой начинается чтение.
  * @param value - В переменную phrase.Value записывается найденая фраза.
  * @param END_OF_TEXT - маркер конца.
  * @return Позиция после найденого тега.<br/>
  *         -1 - В случае ошибки.
  * /
 public static int readToMarker(ARefStr text,final int POS,ARefStr value,final AMarker END) throws AException//???
 {
  return readToCase(text,POS,value,END);
 }*/
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из text.value начиная с позиции POS
  * и заканчивая позицией конца текста,
  * и записывает его в переменную subtext.value.<br/>
  * @param text Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.value записывается найденый подтекст.
  * @return Позиция после найденого подтекста.
  * @throws AException в случае ошибки.
  */
public static int readToEnd(AStr text,final int POS,AStr subtext) throws AException//???
 {
  if(text==null)throw new AException("text=null");
  if(subtext==null)throw new AException("value=null");
  final int textLenght=text.value.length();
  if(POS>=textLenght)throw new AException("POS>=SIZE");
  int pos=POS;
  String str="";
  while(pos<textLenght)str+=text.value.charAt(pos++);
  subtext.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает подтекст из text.value начиная с позиции POS
  * и заканчивая позицией перед первым найденным условием END,
  * и записывает его в переменную subtext.value.<br/>
  * @param text Ссылка на текст для синтаксического анализа.
  * @param POS Позиция в тексте с которой начинается чтение.
  * @param subtext В переменную subtext.Value записывается найденая фраза.
  * @param END Условие завершения чтения.
  * @return Позиция после найденого маркера.<br/>
  *         -1 - В случае если маркер ненайден.
  * @throws AException в случае ошибки.
  */
 public static int readToCase(AStr text,final int POS,AStr subtext,final ACase END) throws AException//???
 {
  if(text==null)throw new AException("text=null");
  if(subtext==null)throw new AException("value=null");
  final int textLenght=text.value.length();
  if(POS>=textLenght)throw new AException("POS>=SIZE",POS);
  int pos=POS;
  String str="";
  AInt len=new AInt(0);
  while(true)
  {
   if(pos>=textLenght)
   {
    subtext.value=str;
    return -1;
   }
   if(END.done(text,pos,len))
   {
    pos+=len.value;
    break;
   }
   str+=text.value.charAt(pos++);
  }
  subtext.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод пропускает символы UNUSED в тексте text.value
  * начиная с позиции POS.<br/>
  * @param text Ссылка на текст для синтаксического анализа.
  * @param POS Позиция сравнения.
  * @param word В переменную Word.Value записывается найденое слово.
  * @return Позиция после найденого слова.
  * @throws AException в случае ошибки.
  */
 public static int skip(AStr text,final int POS,final ACharArea UNUSED) throws AException 
 {
  if(text==null)throw new AException("text=null");
  if(POS>=text.value.length())throw new AException("POS>=SIZE");
//  if(!UNUSED.is(text.value.charAt(POS)))throw new AException("Is not Word");
  int pos=POS;
  while(true)
  {
   if(pos>=text.value.length())break;
   if(text.value.charAt(pos)<32)break;
   if(!UNUSED.is(text.value.charAt(pos)))break;
   pos++;
  }
  return pos;
 }
//---------------------------------------------------------------------------
/*// char* GetNumber(char* Ptr,int *Number);
/**
  * Метод выбирает номер из текста TEXT
  * начиная с позиции POS и записывает его в переменную Number.Value.<br/>
  * @param TEXT - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param Number - В переменную Number.Value записывается найденый номер.
  * @return Позиция после найденого номера.<br/>
  *         -1 - В случае ошибки.
  * /
 public static int getNumber(final String TEXT,final int POS,ARefInt Number)  //!!!
 {
  if(TEXT==null)
  {
   sendGlobalError("getNumber","TEXT=null",null);
   return -1;
  }
  if(Number==null)
  {
   sendGlobalError("getNumber","Number=null",null);
   return -1;
  }
  final int len=TEXT.length();
  if(POS>=len)
  {
   sendGlobalError("getNumber","POS>=SIZE",POS);
   return -1;
  }
  if(!Character.isDigit(TEXT.charAt(POS)))
  {
   sendGlobalError("getNumber","Is not digital",null);
   return -1;
  }
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=len)break;
   final char CHAR=TEXT.charAt(pos);
   if(CHAR<32)break;
   if(!Character.isDigit(CHAR))break;
   str+=CHAR;
   pos++;
  }
  try
  {
   int num=Integer.parseInt(str);
   Number.Value=num;
  }
  catch(NumberFormatException ex)
  {
   sendGlobalError("getNumber","str convert to int ERROR",null);
   return -1;
  }
  return pos;
 }*/
//---------------------------------------------------------------------------
 public static AChannelList split(AStr text,ACase condition)throws AException
 {
  if(text==null)throw new AException("text=null");
  if(condition==null)throw new AException("condition=null");
  if(text.value.isEmpty())throw new AException("text is empty");
  
  AChannelList list=new AChannelList();
  list.create("found",AValue.TYPE_STR);
  list.create("context",AValue.TYPE_STR);
  
  
  
//  final int textLenght=text.value.length();
//  if(POS>=textLenght)throw new AException("AParser","readToCase","POS>=SIZE",POS);
//  int pos=POS;
//  String str="";
//  AInt len=new AInt(0);
//  while(true)
//  {
//   if(pos>=textLenght)
//   {
//    subtext.value=str;
//    return -1;
//   }
//   if(END.done(text,pos,len))
//   {
//    pos+=len.value;
//    break;
//   }
//   str+=text.value.charAt(pos++);
//  }
//  subtext.value=str;
//  return pos;
  
  
  
  throw new AException("this function has not been realised");
//  return list;
 }
//---------------------------------------------------------------------------
}
