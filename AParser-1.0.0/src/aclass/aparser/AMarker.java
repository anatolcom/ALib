/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser;

import aclass.AClass;
import aclass.AException;
import aclass.aparser.varible.AInt;
import aclass.aparser.varible.AStr;

/**
 *
 * @author Anatol
 */
public class AMarker extends AClass implements ACase
{
//---------------------------------------------------------------------------
 public final char VALUE[];
 public boolean caseSensitive=false;
//---------------------------------------------------------------------------
 public AMarker(final String IDENTIFER)
 {
  this.VALUE=IDENTIFER.toCharArray();
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return new String(VALUE);
 }
//---------------------------------------------------------------------------
 @Override
 public boolean done(AStr text,int POS,AInt len) throws AException
 {
  if(len==null) throw new AException("len=null");
  len.value=done(text,POS);
  if(len.value==0)return false;
  return true;
//  if(text==null) throw new AException(this,"done","text=null");
//  if(len==null) throw new AException(this,"done","len=null");
//  if(POS>=text.value.length()) throw new AException(this,"done","POS>=SIZE");
//  String id=new String(VALUE);
//  int lenght=id.length();
//  if(text.value.length()<POS+lenght) return false;
//  for(int q=0;q<lenght;q++)
//  {
//   char a=text.value.charAt(POS+q);
//   char b=VALUE[q];
//   if(caseSensitive)
//   {
//    a=Character.toLowerCase(a);
//    b=Character.toLowerCase(b);
//   }
//   if(a==b) continue;
//   return false;
//  }
//  len.value=lenght;
//  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public int done(AStr text,int POS) throws AException
 {
  if(text==null) throw new AException("text=null");
  if(POS>=text.value.length()) throw new AException("POS>=SIZE");
  String id=new String(VALUE);
  int lenght=id.length();
  if(text.value.length()<POS+lenght) return 0;
  for(int q=0;q<lenght;q++)
  {
   char a=text.value.charAt(POS+q);
   char b=VALUE[q];
   if(caseSensitive)
   {
    a=Character.toLowerCase(a);
    b=Character.toLowerCase(b);
   }
   if(a==b) continue;
   return 0;
  }
  return lenght;
 }
//---------------------------------------------------------------------------
}
