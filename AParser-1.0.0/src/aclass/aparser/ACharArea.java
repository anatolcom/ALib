/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser;
import aclass.AClass;
import aclass.AException;
import aclass.aparser.varible.AInt;
import aclass.aparser.varible.AStr;
import java.util.ArrayList;

/**
 *
 * @author Anatol
 */
public class ACharArea extends AClass implements ACase
{
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
   sendError("addCharRange",ex.getMessage(),null);
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
 public boolean done(AStr text,int POS,AInt len) throws AException
 {
  if(len==null) throw new AException(this,"done","len=null");
  len.value=done(text,POS);
  if(len.value==0)return false;
  return true;
//  if(is(text.value.charAt(POS)))
//  {
//   len.value=1;
//   return true;
//  } 
//  len.value=0;
//  return false;
 }
//---------------------------------------------------------------------------
 @Override
 public int done(AStr text,int POS) throws AException
 {
  if(text==null) throw new AException(this,"done","text=null");
  if(POS>=text.value.length()) throw new AException(this,"done","POS>=SIZE");
  if(is(text.value.charAt(POS)))return 1;
  return 0;
 }
//---------------------------------------------------------------------------
}
