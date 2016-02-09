/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.cases;
import aclass.aparser.cases.ACase;
import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.AParser;
import aclass.aparser.varible.basic.AStr;
import aclass.aparser.object.AValue;
import java.util.ArrayList;

/**
 *
 * @author Anatol
 */
public class ACharArea extends ACase
{
//---------------------------------------------------------------------------
 private boolean caseSensitive=true;
//---------------------------------------------------------------------------
 private class AChar
 {
  public final char CHAR;
  AChar(final char CHAR)
  {
   this.CHAR=CHAR;
  }
  public boolean isChar(final char CHAR)
  {
   if(this.CHAR!=CHAR)return false;
   return true;
  }
  @Override
  public String toString()
  {
   return "'"+CHAR+"'";
  }
 } 
//---------------------------------------------------------------------------
 private class ACharRange
 {
  public final char START;
  public final char END;
  ACharRange(final char START,final char END) throws Exception
  {
   if(START>=END)throw new Exception("start>=end");
   this.START=START;
   this.END=END;
  }
  public boolean inRange(final char CHAR)
  {
   if(CHAR<START)return false;
   if(CHAR>END)return false;
   return true;
  }
  @Override
  public String toString()
  {
   return "'"+START+"'-'"+END+"'";
  }
 } 
//---------------------------------------------------------------------------
 private ArrayList<ACharRange> charRangeList=new ArrayList();
 private ArrayList<AChar> charList=new ArrayList();
//---------------------------------------------------------------------------
/**
 * Метод добавляет символ в список сравнения
 * @param CHAR
 * @return 
 */
 public ACharArea add(final char CHAR)                                  //!!~   нет проверки пересечений с диапазонами
 {
  for(AChar c:charList)if(c.isChar(CHAR))return this;
  charList.add(new AChar(CHAR));
  return this;
 }
//---------------------------------------------------------------------------
 public ACharArea add(final char START,final char END)                  //!!~   нет проверки пересечений диапазонов друг с другом и символами
 {
  try
  {
   ACharRange range=new ACharRange(START,END);
   charRangeList.add(range);
  }
  catch(Exception ex)
  {
   sendError("addCharRange",ex.getMessage());
  }
  return this;
 }
//---------------------------------------------------------------------------
 public boolean is(char VALUE)                                          //~~~
 {
  for(AChar c:charList)if(c.isChar(VALUE))return true;
  for(ACharRange r:charRangeList)if(r.inRange(VALUE))return true;
  return false;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  String str="";
  boolean first=true;
  for(ACharRange r:charRangeList)
  {
   if(first)
   {
    str+=r.toString();
    first=false;
   }
   else str+=","+r.toString();
  }
  for(AChar c:charList)
  {
   if(first)
   {
    str+=c.toString();
    first=false;
   }
   else str+=","+c.toString();
  }
  return str;
 }
//---------------------------------------------------------------------------
 @Override
 public boolean done(AConstText text,int POS) throws AException
 {
  if(text==null) throw new AException("text=null");
  if(POS>=text.value.length()) throw new AException("POS>=SIZE");
  if(!is(text.value.charAt(POS)))
  {
   textCase="";
   return false;
  }
  AStr strTextCase=new AStr();          
  AParser.read(text,POS,strTextCase,1);
  textCase=strTextCase.getStr();
  return true;
 }
//---------------------------------------------------------------------------
}
