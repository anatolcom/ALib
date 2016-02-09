/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.axml;
import aclass.AClass;
import aclass.AException;
import aclass.aref.*;
import java.io.*;
import java.util.Stack;
//import java.util.Locale;
//import java.text.SimpleDateFormat;
/**
 *
 * @author Anatol
 */
public class AXML extends AClass
{
//---------------------------------------------------------------------------
 private AXMLNode FRootNode;
//---------------------------------------------------------------------------
// public final SimpleDateFormat DF;
//---------------------------------------------------------------------------
 public AXML()                                                          //!!!
 {
//  DF=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss",Locale.getDefault());
  initialize();
 }
//---------------------------------------------------------------------------
 private void initialize()                                              //!!!
 {
  FRootNode=new AXMLNode();
  FRootNode.setParent(this);
 }
//---------------------------------------------------------------------------
 public AXMLNode getRootNode()                                          //!!!
 {
  return FRootNode;
 }
//---------------------------------------------------------------------------
 public void clear()
 {
  FRootNode.clear();
 }
//---------------------------------------------------------------------------
//-----------------------------------------------------< XML type of tag >---
 private static final byte TT_ERROR=        0x00;   // Incorrect tag
 private static final byte TT_INSTRUCTION=  0x01;   // <?ProcessingInstructions?>
 private static final byte TT_DECLARATION=  0x02;   // <!Declaration>
 private static final byte TT_OPEN=         0x03;   // <tag>
 private static final byte TT_CLOSE=        0x04;   // </tag>
 private static final byte TT_SINGLE=       0x05;   // <tag />
 private static final byte TT_COMMENT=      0x06;   // <--!Comment-->
//------------------------------------------------------< XML identifers >---
 private static final char ID_TAG_BEGIN[]=         {'<'};             //  <
 private static final char ID_TAG_END[]=           {'>'};             //  >

 private static final char ID_CLOSE_SYMBOL[]=      {'/'};             //  /

 private static final char ID_INSTRUCTION_BEGIN[]= {'?'};             //  ?
 private static final char ID_INSTRUCTION_END[]=   {'?'};             //  ?

 private static final char ID_COMMENT_BEGIN[]=     {'-','-','!'};     //  --!
 private static final char ID_COMMENT_END[]=       {'-','-'};         //  --

 private static final char ID_DECLARATION[]=       {'!'};             //  !

 private static final char ID_PARAM_ASSIGN[]=      {'='};             //  =
 private static final char ID_PARAM_BEGIN[]=       {'"'};             //  "
 private static final char ID_ALT_PARAM_BEGIN[]=   {'\''};            //  '
 private static final char ID_PARAM_END[]=         {'"'};             //  "
 private static final char ID_ALT_PARAM_END[]=     {'\''};            //  '

 private static final char ID_SPECSYMBOL_BEGIN[]=  {'&'};             //  &
 private static final char ID_SPECSYMBOL_END[]=    {';'};             //  ;
 private static final char ID_SPECSYMBOL_NUMBER[]= {'#'};             //  #
//---------------------------------------------------------< XML entity >---
 private static final char ENTITY_AMP[]=    {'a','m','p'};     //  &amp;  &
 private static final char ENTITY_LT[]=     {'l','t'};         //  &lt;   <
 private static final char ENTITY_GT[]=     {'g','t'};         //  &gt;   >
 private static final char ENTITY_APOS[]=   {'a','p','o','s'}; //  &apos; '
 private static final char ENTITY_QUOT[]=   {'q','u','o','t'}; //  &quot; "
//---------------------------------------------------------------------------
 private static final char XMLNS[]=   {'x','m','l','n','s'};
//---------------------------------------------------------------------------
/**
  * Метод определяет, является ли value символом 'A'-'Z','a'-'z','_','-'.<br/>
  * @param VALUE - Значение.
  * @return true - да,<br/>
  *         false - нет.
  */
 public boolean isSymbol(final char VALUE)                              //!!!
 {
  if(((VALUE>='A')&(VALUE<='Z'))
    |((VALUE>='a')&(VALUE<='z'))
    |(VALUE=='_')
    |(VALUE=='-'))return true;
  return false;
 }
//---------------------------------------------------------------------------
/**
  * Метод определяет, является ли value цифрой '0'-'9'.<br/>
  * @param VALUE - Значение.
  * @return true - да,<br/>
  *         false - нет.
  */
 private static boolean isDigital(final char VALUE)
 {
  if(((VALUE>='0')&(VALUE<='9')))return true;
  return false;
 }
//---------------------------------------------------------------------------
/* *
  * Метод определяет, является ли value знаком '+','-'.<br/>
  * @param VALUE - Значение.
  * @return true - да,<br/>
  *         false - нет.
  * /
 private boolean isSign(final char VALUE)                               //!!!
 {
  if(((VALUE=='+')|(VALUE=='-')))return true;
  return false;
 }*/
//---------------------------------------------------------------------------
/**
  * Метод определяет является ли содержимое
  * в тексте TEXT в позиции POS
  * равным по значению переносу строки.<br/>
  * В переменну Len.value записывается
  * длинна идентификатора переноса строки.<br/>
  * @param TEXT - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param Len - В переменную Len.value записывается длинна идентификатора переноса строки.
  * @return true - да,<br/>
  *         false - нет.
  */
 private static boolean isEnter(final String TEXT,final int POS,ARefByte Len)throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(Len==null)throw new AException("Len=null");
  if(TEXT.length()<POS+2)return false;
  if(TEXT.charAt(POS)!=13)return false;
  if(TEXT.charAt(POS+1)!=10)return false;
  Len.value=2;
  return true;
 }
//---------------------------------------------------------------------------
/**
  * Метод преодразует ID в формат String.<br/>
  * @param - ID Идентификатор.
  * @return Строковый вариант ID.
  */
 private static String IDToStr(final char ID[])
 {
  return new String(ID);
 }
//---------------------------------------------------------------------------
/**
  * Метод определяет является ли содержимое в тексте TEXT
  * в позиции POS равным по значению идентификатору ID.<br/>
  * В переменну Len.value записывается длинна идентификатора ID.<br/>
  * @param TEXT - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param ID - Строка идентификатора.
  * @param Len - В переменную Len.value записывается длинна идентификатора ID.
  * @return true - да,<br/>
  *         false - нет.
  */
 private static boolean isID(final String TEXT,final int POS,final char ID[],ARefByte Len)throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(Len==null)throw new AException("Len=null");
  if(POS>=TEXT.length())throw new AException("POS>=SIZE",POS);
  String id=new String(ID);
  byte lenght=(byte)id.length();
  if(TEXT.length()<POS+lenght)return false;
  for(int q=0;q<lenght;q++)
  {
   if(TEXT.charAt(POS+q)==ID[q])continue;
   return false;
  }
  Len.value=lenght;
  return true;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает слово из текста TEXT
  * начиная с позиции POS и записывает его в переменную Word.value.<br/>
  * @param TXT текст для синтаксического анализа.
  * @param POS позиция сравнения.
  * @param word в переменную Word.value записывается найденое слово.
  * @return Позиция после найденого слова.<br/>
  * @throws AException в случае ошибки.
  */
 private int getWord(final String TEXT,final int POS,ARefStr word) throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(word==null)throw new AException("word=null");
  if(POS>=TEXT.length())throw new AException("POS>=SIZE",POS);
  if(!isSymbol(TEXT.charAt(POS)))throw new AException("Is not Word");
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=TEXT.length())break;
   if(TEXT.charAt(pos)<32)break;
   if(!isSymbol(TEXT.charAt(pos))&&!isDigital(TEXT.charAt(pos)))break;
   str+=TEXT.charAt(pos++);
  }
  word.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает номер из текста TEXT
  * начиная с позиции POS и записывает его в переменную Number.value.<br/>
  * @param TEXT - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param number - В переменную Number.value записывается найденый номер.
  * @return Позиция после найденого номера.<br/>
  * @throws AException в случае ошибки.
  */
 private static int getNumber(final String TEXT,final int POS,ARefInt number)throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(number==null)throw new AException("Number=null");
  final int len=TEXT.length();
  if(POS>=len)throw new AException("POS>=SIZE");
  if(!isDigital(TEXT.charAt(POS)))throw new AException("Is not digital");
  int pos=POS;
  String str="";
  while(true)
  {
   if(pos>=len)break;
   final char CHAR=TEXT.charAt(pos);
   if(CHAR<32)break;
   if(!isDigital(CHAR))break;
   str+=CHAR;
   pos++;
  }
  try{number.value=Integer.parseInt(str);}
  catch(NumberFormatException ex){throw new AException("str convert to int ERROR",ex);}
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает тег из текста TEXT начиная с позиции POS
  * и записывает его в переменную Tag.value.<br/>
  * @param TEXT - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param Tag - В переменную Tag.value записывается найденый тег.
  * @return Позиция после найденого тега.<br/>
  * @throws AException в случае ошибки.
  */
 private int getTag(final String TEXT,final int POS,ARefStr Tag)throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(Tag==null)throw new AException("Tag=null");
  final int len=TEXT.length();
  if(POS>=len)throw new AException("POS>=SIZE",POS);
  int pos=POS;
  String str="";
  ARefByte Len=new ARefByte((byte)0);
  while(true)
  {
   if(pos>=len)throw new AException("Statement missing "+IDToStr(ID_TAG_END),pos);
   if(isEnter(TEXT,pos,Len))throw new AException("Enter... Statement missing "+IDToStr(ID_TAG_END),pos);
   if(isID(TEXT,pos,ID_TAG_END,Len))
   {
    pos+=Len.value;
    break;
   }
   str+=TEXT.charAt(pos++);//[pos++];
  }
  Tag.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает параметр тега из текста TEXT
  * начиная с позиции POS и записывает его в переменную Param.value.<br/>
  * @param TEXT - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param Param - В переменную Param.value записывается найденый параметр.
  * @return Позиция после найденого параметра.<br/>
  *         -1 - В случае ошибки.
  */
 private int getParam(final String TEXT,final int POS,AXMLParam Param)throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(Param==null)throw new AException("Param=null");
  final int len=TEXT.length();
  if(POS>=len)throw new AException("POS>=SIZE",POS);
  int pos=POS;
  ARefStr Name=new ARefStr();
  pos=getWord(TEXT,pos,Name);
  if(pos==-1)throw new AException("getWord return ERROR");
  Param.name=Name.value;
  ARefByte Len=new ARefByte((byte)0);
  if(!isID(TEXT,pos,ID_PARAM_ASSIGN,Len))throw new AException("Statement missing "+IDToStr(ID_PARAM_ASSIGN));
  pos+=Len.value;
  if(!isID(TEXT,pos,ID_PARAM_BEGIN,Len))
  {
   if(!isID(TEXT,pos,ID_ALT_PARAM_BEGIN,Len))throw new AException("Statement missing "+IDToStr(ID_PARAM_BEGIN));
  }
  pos+=Len.value;
  String str="";
  while(true)
  {
   if(pos>=len)throw new AException("Statement missing "+IDToStr(ID_PARAM_END),pos);
   if(isEnter(TEXT,pos,Len))throw new AException("Enter... Statement missing "+IDToStr(ID_PARAM_END),pos);
//  if(IsSpecChar(Ptr,len))//                             <   Spec Char   >
//  {
//   Ptr+=len;
//   *value+=*Ptr++;//??????  \\  \"  \'  \t  \n    ???????
//   continue;
//  }
   if(isID(TEXT,pos,ID_PARAM_END,Len))
   {
    pos+=Len.value;
    break;
   }
   else
   {
    if(isID(TEXT,pos,ID_ALT_PARAM_END,Len))
    {
     pos+=Len.value;
     break;
    }
   }
   str+=TEXT.charAt(pos++);
  }
  Param.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод выбирает спецсимвол тега из текста TEXT
  * начиная с позиции POS и записывает его в переменную Param.value.<br/>
  * @param TEXT - Текст для синтаксического анализа.
  * @param POS - Позиция сравнения.
  * @param SpecSymbol - В переменную SpecSymbol.value записывается найденый спецсимвол.
  * @return Позиция после найденого спецсимвол.<br/>
  *         -1 - В случае ошибки.
  */
 private static int getSpecSymbol(final String TEXT,final int POS,ARefStr SpecSymbol)throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  if(SpecSymbol==null)throw new AException("SpecSymbol=null");
  final int len=TEXT.length();
  if(POS>=len)throw new AException("POS>=SIZE");
  int pos=POS;
  String str="";
  ARefByte Len=new ARefByte((byte)0);
  while(true)
  {
   if(pos>=len)throw new AException("Statement missing "+IDToStr(ID_SPECSYMBOL_END),pos);
   if(isEnter(TEXT,pos,Len))throw new AException("Enter... Statement missing "+IDToStr(ID_SPECSYMBOL_END),pos);
   if(isID(TEXT,pos,ID_SPECSYMBOL_END,Len))
   {
    pos+=Len.value;
    break;
   }
   str+=TEXT.charAt(pos++);
  }
  SpecSymbol.value=str;
  return pos;
 }
//---------------------------------------------------------------------------
/**
  * Метод определяет тип тега Tag.<br/>
  * @param Tag - Ссылка на тег.
  * @return TT_ERROR - Некорректный формат.<br/>
  *         TT_INSTRUCTION - Тег инструкций обработки: &lt;?ProcessInstuction?&gt;.<br/>
  *         TT_DECLARATION - Декларативный тег: &lt;!Declaration&gt;.<br/>
  *         TT_OPEN - Открывающий тег: &lt;tag&gt;.<br/>
  *         TT_CLOSE - Закрывающий тег: &lt;/tag&gt;.<br/>
  *         TT_SINGLE - Закрытый тег: &lt;tag /&gt;.<br/>
  *         TT_COMMENT - Коментарий: &lt;--!Comment--&gt;.<br/>
  */
 private byte getTagType(ARefStr Tag)throws AException
 {
  if(Tag==null)
  {
   sendError("getTagType","Tag=NULL",null);
   return TT_ERROR;
  }
  String str="";
  final int len=Tag.value.length();
  int pos=0;
  ARefByte Len=new ARefByte((byte)0);
  if(isID(Tag.value,pos,ID_INSTRUCTION_BEGIN,Len))//<   <?ProcessInstuction?>   >---
  {
   pos+=Len.value;
   while(true)
   {
    if(pos>=len)
    {
     sendError("getTagType","Statement missing "+
               IDToStr(ID_INSTRUCTION_END)+
               IDToStr(ID_TAG_END),pos);
     return TT_ERROR;
    }
    if(isID(Tag.value,pos,ID_INSTRUCTION_END,Len))
    {
     pos+=Len.value;
     break;
    }
    str+=Tag.value.charAt(pos++);
   }
   if(pos<len)
   {
    sendError("getTagType","Statement missing "+IDToStr(ID_TAG_END),pos);
    return TT_ERROR;
   }
   Tag.value=str;
   return TT_INSTRUCTION;
  }
  if(isID(Tag.value,pos,ID_COMMENT_BEGIN,Len))//----<   <--!Comment-->   >---
  {
   pos+=Len.value;
   while(true)
   {
    if(pos>=len)
    {
     sendError("getTagType","Statement missing "+
               IDToStr(ID_COMMENT_END)+
               IDToStr(ID_TAG_END),pos);
     return TT_ERROR;
    }
    if(isID(Tag.value,pos,ID_COMMENT_END,Len))
    {
     pos+=Len.value;
     break;
    }
    str+=Tag.value.charAt(pos++);
   }
   if(pos<len)
   {
    sendError("getTagType","Statement missing "+IDToStr(ID_TAG_END),pos);
    return TT_ERROR;
   }
   Tag.value=str;
   return TT_COMMENT;
  }
  if(isID(Tag.value,pos,ID_DECLARATION,Len))//------<   <!Declaration>   >---
  {
   pos+=Len.value;
   while(true)
   {
    if(pos>=len)break;
    str+=Tag.value.charAt(pos++);
   }
   Tag.value=str;
   return TT_DECLARATION;
  }
  if(isID(Tag.value,pos,ID_CLOSE_SYMBOL,Len))//-------------<   </tag>   >---
  {
   pos+=Len.value;
   while(true)
   {
    if(pos>=len)break;
    str+=Tag.value.charAt(pos++);
   }
   Tag.value=str;
   return TT_CLOSE;
  }
  while(true)//--------------------------------------------<   <tag />   >---
  {
   if(pos>=len)break;
   if(isID(Tag.value,pos,ID_CLOSE_SYMBOL,Len))
   {
    pos+=Len.value;
    if(pos<len)
    {
     sendError("getTagType","Statement missing "+IDToStr(ID_TAG_END),pos);
     return TT_ERROR;
    }
    Tag.value=str;
    return TT_SINGLE;
   }
   str+=Tag.value.charAt(pos++);
  }
  Tag.value=str;//-------------------------------------------<   <tag>   >---
  return TT_OPEN;
 }
//---------------------------------------------------------------------------
 public static AXMLNamespace strToNamespace(String xmlns)throws AException
 {
  xmlns=xmlns.trim();
  if(!xmlns.startsWith("xmlns:"))throw new AException("\""+xmlns+"\"is not xmlns");
  xmlns=xmlns.substring(6);
  int pos=xmlns.indexOf("=");
  String prefix=xmlns.substring(0,pos);
  String uri=xmlns.substring(pos+2,xmlns.length()-1);
  return new AXMLNamespace(prefix,uri);
 }
//---------------------------------------------------------------------------
/**
  * Метод определяет имя тега Tag.<br/>
  * @param tag - Ссылка на тег.
  * @return имя тега.<br/>
  *         null - в случае ошибки.
  */
 private String getTagName(ARefStr tag)throws AException
 {
  if(tag==null)throw new AException("tag=NULL");
  ARefStr Word=new ARefStr();
  int pos=0;
  pos=getWord(tag.value,pos,Word);
  String str="";
  final int len=tag.value.length();
  while(true)
  {
   if(pos>=len)break;
   str+=tag.value.charAt(pos++);
  }
  tag.value=str;
  return Word.value;
 }
//---------------------------------------------------------------------------
/**
  * Метод выберает из тега Tag параметры
  * и записывает их в список параметров ParamList.<br/>
  * @param Tag - Ссылка на тег.
  * @param ParamList - Ссылка на список параметров.
  * @return количество найденных параметров.<br/>
  *         -1 - В случае ошибки.
  */
 private int getTagParams(ARefStr Tag,AXMLParamList ParamList)throws AException
 {
  if(Tag==null)throw new AException("Tag=null");
  if(ParamList==null)throw new AException("ParamList=null");
  final int len=Tag.value.length();
  int pos=0;
  int paramcount=0;
  while(true)
  {
   if(pos>=len)break;
   if(isSymbol(Tag.value.charAt(pos)))
   {
    AXMLParam param=new AXMLParam();
    pos=getParam(Tag.value,pos,param);
    if(pos==-1)throw new AException("getParam return ERROR");
    ParamList.addParam(param);
    paramcount++;
    continue;
   }
   pos++;
  }
  return paramcount;
 }
//---------------------------------------------------------------------------
/**
  * Метод преобразует сущьность ENTITY в спецсимвол.<br/>
  * @param ENTITY - Сущьность.
  * @return Спецсимвол.
  */
 private static String EntityToSpecSymbol(final char ENTITY[])
 {
  String test=IDToStr(ID_SPECSYMBOL_BEGIN)+IDToStr(ENTITY)+IDToStr(ID_SPECSYMBOL_END);
  if(test.contains("null"))sendGlobalError("EntityToSpecSymbol","contains null",null);
  return test;
 }
//---------------------------------------------------------------------------
/**
  * Метод определяет нужно ли символ VALUE
  * конвертировать в спецсимвол.<br/>
  * @param VALUE - Символ.
  * @return true - да,<br/>
  *         false - нет.
  */
 private static boolean needConvertToSpecSymbol(final char VALUE)
 {
  switch(VALUE)
  {
   case '&': return true;
   case '<': return true;
   case '>': return true;
   case '\'': return true;
   case '"': return true;
  }
  return false;
 }
//---------------------------------------------------------------------------
/**
  * Метод преобразует данные Node в XML код,
  * и в соответсвии с уровнем LEVEL
  * отодвигает код от края документа на расстояние SPACE.<br/>
  * @param Node - Узел, содержащий XML данные.
  * @param LEVEL - Уровень.
  * @param SPACE - Расстояние между уровнями.
  * @return XML код.
  */
 public String NodeToXML(AXMLNode Node,final int LEVEL,final int SPACE)throws AException
 {
  if(Node==null)
  {
   sendError("NodeToXML","NodeNode=null",null);
   return null;
  }
  String str="";
  for(int q=0;q<SPACE*LEVEL;q++)str+=" ";
  str+="<"+Node.getName();
  for(int q=0;q<Node.ParamList.count();q++)
  {
   str+=" "+Node.ParamList.getParam(q).name+
            "=\""+Node.ParamList.getParam(q).value+"\"";
  }
  if((Node.SubNodeList.count()>0)||(!Node.getContent().isEmpty()))
  {
   str+=">";
   if(Node.SubNodeList.count()>0)
   {
    str+=(char)13;
    str+=(char)10;
    for(int q=0;q<Node.SubNodeList.count();q++)
    {
     str+=NodeToXML(Node.SubNodeList.Node(q),LEVEL+1,SPACE);
    }
    for(int q=0;q<SPACE*LEVEL;q++)str+=" ";
   }
   if(!Node.getContent().isEmpty())str+=StrToXMLStr(Node.getContent(),true);
   str+="</"+Node.getName()+">";
  }
  else str+=" />";
  str+=(char)13;
  str+=(char)10;
  return str;
 }
//---------------------------------------------------------------------------
/**
  * Метод конвертирует символ CHAR в спецсимвол.<br/>
  * @param CHAR - Символ.
  * @return Спецсимвол.
  */
 private static String CharToSpecSymbol(final char CHAR)
 {
  switch(CHAR)
  {
   case '&': return EntityToSpecSymbol(ENTITY_AMP);
   case '<': return EntityToSpecSymbol(ENTITY_LT);
   case '>': return EntityToSpecSymbol(ENTITY_GT);
   case '\'': return EntityToSpecSymbol(ENTITY_APOS);
   case '"': return EntityToSpecSymbol(ENTITY_QUOT);
  }
  return IDToStr(ID_SPECSYMBOL_BEGIN)
        +IDToStr(ID_SPECSYMBOL_NUMBER)
        +Integer.toString((int)CHAR)
        +IDToStr(ID_SPECSYMBOL_END);
//  return String(IdSpecSymbolBegin)+String(IdSpecSymbolNumber)+IntToStr(value)+String(IdSpecSymbolEnd);
 }
//---------------------------------------------------------------------------
/**
  * Метод конвертирует спецсимвол SPECSYMBOL в символ.<br/>
  * @param SPECSYMBOL - Спецсимвол.
  * @return Cимвол.<br/>
  *         null - В случае ошибки.
  */
 private static Character SpecSymbolToChar(final String SPECSYMBOL)throws AException
 {
  if(SPECSYMBOL==null)throw new AException("SPECSYMBOL=null");
  int pos=0;
  ARefByte Len=new ARefByte((byte)0);
  if(isID(SPECSYMBOL,pos,ID_SPECSYMBOL_NUMBER,Len))
  {
   pos+=Len.value;
   ARefInt Number=new ARefInt();
   pos=getNumber(SPECSYMBOL,pos,Number);
   if(pos==-1)throw new AException("GetNumber return ERROR");
   return (char)Number.value;
  }
  if(SPECSYMBOL.equals(IDToStr(ENTITY_AMP)))return '&';
  if(SPECSYMBOL.equals(IDToStr(ENTITY_LT)))return '<';
  if(SPECSYMBOL.equals(IDToStr(ENTITY_GT)))return '>';
  if(SPECSYMBOL.equals(IDToStr(ENTITY_APOS)))return '\'';
  if(SPECSYMBOL.equals(IDToStr(ENTITY_QUOT)))return '"';
//  throw new AException("Invalid spec symbol \\\"\"+SPECSYMBOL+\"\\\"");
  throw new AException("Invalid spec symbol \""+SPECSYMBOL+"\"");
 }
//---------------------------------------------------------------------------
/**
  * Метод конвертирует строку STR в строку стандарта XML.
  * Если FORMATED=true то форматирование строки сохраняется.<br/>
  * @param STR - Строка.
  * @param FORMATED - Сохраниение форматирования.
  * @return Строка стандарта XML.<br/>
  *         null - в случае ошибки.
  */
 public static String StrToXMLStr(String STR,boolean FORMATED)throws AException
 {
  if(STR==null)throw new AException("STR=null");
  if(STR.isEmpty())return "";
  String xmlstr="";
  final int len=STR.length();
  int pos=0;
  boolean first=true;
  while(true)
  {
   if(pos>=len)break;
   final char CHAR=STR.charAt(pos);
   if(CHAR==0)sendGlobalError("StrToXMLStr","contains null",5);
//   if(CHAR<=32)
//   {
//    if(!FORMATED)
//    {
//     if(first)xmlstr+=" ";
//     first=false;
//    }
//    else xmlstr+=CharToSpecSymbol(CHAR);
//    pos++;
//    continue;
//   }
   if(CHAR<32)
   {
    if(FORMATED)xmlstr+=CharToSpecSymbol(CHAR);
    pos++;
    continue;
   }
   if(CHAR==32)
   {
    if(!FORMATED)
    {
     if(first)xmlstr+=" ";
     first=false;
    }
    else xmlstr+=CharToSpecSymbol(CHAR);
    pos++;
    continue;
   }
//   first=true;//??????
   if(CHAR>127)
   {
    xmlstr+=CharToSpecSymbol(CHAR);
    pos++;
    continue;
   }
   first=true;
   if(needConvertToSpecSymbol(CHAR))
   {
    xmlstr+=CharToSpecSymbol(CHAR);
    pos++;
    continue;
   }
   xmlstr+=CHAR;
   pos++;
  }
  return xmlstr;
 }
//---------------------------------------------------------------------------
/**
  * Метод конвертирует строку стандарта XML XMLSTR в строку.<br/>
  * @param XMLSTR - Строка стандарта XML.
  * @return Строка.<br/>
  *         null - в случае ошибки.
  */
 public static String XMLStrToStr(String XMLSTR)throws AException
 {
  if(XMLSTR==null)throw new AException("XMLSTR=null");
  if(XMLSTR.isEmpty())return "";
  final int len=XMLSTR.length();
  String str="";
  int pos=0;
  ARefByte Len=new ARefByte((byte)0);
  while(true)
  {
   if(pos>=len)break;
   if(isID(XMLSTR,pos,ID_SPECSYMBOL_BEGIN,Len))
   {
    pos+=Len.value;
    ARefStr SpecSymbol=new ARefStr();
    pos=getSpecSymbol(XMLSTR,pos,SpecSymbol);
    if(pos==-1)throw new AException("getSpecSymbol return ERROR");
    Character Symbol=SpecSymbolToChar(SpecSymbol.value);
    if(Symbol==null)throw new AException("SpecSymbolToChar return ERROR");
    str+=Symbol;
    continue;
   }
   str+=XMLSTR.charAt(pos++);
  }
  return str;
 }
//---------------------------------------------------------------------------
/**
  * Метод считывает текст TEXT, содержащий XML код.<br/>
  * @param TEXT - текст, содержащий XML код.
  * @return true - да,<br/>
  *         false - нет.
  */
 public boolean fromXML(final String TEXT)throws AException
 {
  if(TEXT==null)throw new AException("TEXT=null");
  final int len=TEXT.length();
  AXMLNode Root=new AXMLNode();
  AXMLNode Node=Root;
  Stack<AXMLNode> NodeStack=new Stack<AXMLNode>();
  int pos=0;
  ARefByte Len=new ARefByte((byte)0);
  String Content="";
  String Test="";
  while(true)
  {
   if(pos>=len)break;
   if(isID(TEXT,pos,ID_TAG_BEGIN,Len))
   {
    pos+=Len.value;
    ARefStr Tag=new ARefStr();
    pos=getTag(TEXT,pos,Tag);
    if(pos==-1)throw new AException("getTag return ERROR");
    byte type=getTagType(Tag);
    String Name;
    AXMLParamList ParamList=new AXMLParamList();
    switch(type)
    {
     case TT_ERROR://--------------------------------<   Incorrect tag   >---
     sendError("fromXML","getTagType return ERROR",null);
     return false;

     case TT_INSTRUCTION://-------------<   <?ProcessingInstructions?>   >---
     sendInfo("fromXML","Parse for <? ... ?> don't realised",null);
     break;

     case TT_DECLARATION://-------------------------<   <!Declaration>   >---
     sendInfo("fromXML","Parse for <! ... > don't realised",null);
     break;

     case TT_OPEN://-----------------------------------------<   <tag>   >---
     Name=getTagName(Tag);
     if(Name==null)throw new AException("getTagName return ERROR");
     int paramcount=getTagParams(Tag,ParamList);
     if(paramcount==-1)throw new AException("getTagParams return ERROR");
     Node.setContent(Node.getContent()+XMLStrToStr(Content.trim()));
     Content="";
     NodeStack.push(Node);
     Node=Node.SubNodeList.newNode();
     Node.setName(Name);
     Node.ParamList.assign(ParamList);
     break;

     case TT_CLOSE://---------------------------------------<   </tag>   >---
     Name=getTagName(Tag);
     if(!Name.equals(Node.getName()))throw new AException("Tag <"+Node.getName()
                +"> and close tag </"+Name+"> does not conform");
     Node.setContent(Node.getContent()+XMLStrToStr(Content.trim()));
     Content="";
     Node=NodeStack.pop();
     break;

     case TT_SINGLE://-------------------------------------<   <tag />   >---
     Name=getTagName(Tag);
     paramcount=getTagParams(Tag,ParamList);
     if(paramcount==-1)throw new AException("getTagParams return ERROR");
     Node.setContent(Node.getContent()+XMLStrToStr(Content.trim()));
     Content="";
     NodeStack.push(Node);
     Node=Node.SubNodeList.newNode();
     Node.setName(Name);
     Node.ParamList.assign(ParamList);
     Node=NodeStack.pop();
     break;

     case TT_COMMENT://-----------------------------<   <--!Comment-->   >---
     break;
    }
    continue;
   }
   Content+=TEXT.charAt(pos++);
  }
  int rootcnt=Root.SubNodeList.count();
  if(rootcnt!=1)throw new AException("Count of root node is incorrect");
  FRootNode.assign(Root.SubNodeList.Node(0));
  return true;
 }
//---------------------------------------------------------------------------
/**
  * Метод преобразует данные Node в XML код.<br/>
  * @return XML код.
  */
 public String toXML()throws AException
 {
  String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  str+=(char)13;
  str+=(char)10;
  str+=NodeToXML(FRootNode,0,3);
  return str;
 }
//---------------------------------------------------------------------------
/**
  * Метод записывает данные в поток Out.<br/>
  * @param Out - Выходной поток.
  * @return true - выполнено,<br/>
  *         false - не выполнено.
  */
 public boolean write(OutputStream Out)throws AException
 {
  if(Out==null)
  {
   sendError("write","Out=null",null);
   return false;
  }
  PrintWriter Wrtr=new PrintWriter(Out);
  Wrtr.write(toXML());
  Wrtr.flush();
  return true;
 }
//---------------------------------------------------------------------------
/**
  * Метод считывает данные из потока In.<br/>
  * @param In - Входной поток.
  * @return true - выполнено,<br/>
  *         false - не выполнено.
  */
 public boolean read(InputStream In)throws AException
 {
  if(In==null)throw new AException("In=null");
  try
  {
   InputStreamReader ISRdr=new InputStreamReader(In);
   String Str="";
   int symbol;
   while(true)
   {
    symbol=ISRdr.read();
    if(symbol==-1)break;
    Str+=(char)symbol;
   }
   if(!fromXML(Str))throw new AException("fromXML return ERROR");
  }
  catch(IOException ex){throw new AException(ex);}
  return true;
 }
//---------------------------------------------------------------------------
 public static String clearMetaData(String xml)throws AException 
 {
  if(xml==null)throw new AException("xml=null");
  if(!xml.startsWith("<?xml"))return xml;
  int pos=xml.indexOf("?>");
  if(pos==-1)throw new AException("Expected \"?>\"");
  return xml.substring(pos+2);
 }
//---------------------------------------------------------------------------
 public static String addMetaData(String xml,String version,String encoding,String standalone)throws AException 
 {
  if(xml==null)throw new AException("xml=null");
  if(xml.startsWith("<?xml"))return xml;
  String meta="<?xml";
  if(version!=null)meta+=" version=\""+version+"\"";//1.0
  if(encoding!=null)meta+=" encoding=\""+encoding+"\"";//UTF-8
  if(standalone!=null)meta+=" standalone=\""+standalone+"\"";//yes no
  meta+="?>";
  return meta+xml;
 }
//---------------------------------------------------------------------------
}
