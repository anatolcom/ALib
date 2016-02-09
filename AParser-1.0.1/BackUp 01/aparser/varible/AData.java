/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;

import aclass.AClass;
import aclass.AException;
import aclass.AMemory;
//import java.io.UnsupportedEncodingException;

/**
 *
 * @author Пользователь
 */
public class AData extends AClass
{
//---------------------------------------------------------------------------
/**
 * @param value значение типа Boolean (true=1,false=0)
 * @return массив байт длиной 1 или null если value=null
 * @throws AException в случае ошибки
 */
 public static byte[] BoolToData(Boolean value)throws AException        //!!!
 {
  if(value==null)return null;
  byte data[]=new byte[1];
  if(value)data[0]=1;
  else data[0]=0;
  return data;
 }
//---------------------------------------------------------------------------
/**
 * @param data массив байт длиной 1 (true=1,false=0)
 * @return значение типа Boolean или null если data=null
 * @throws AException в случае ошибки
 */
 public static Boolean DataToBool(byte data[])throws AException         //!!!
 {
  if(data==null)return null;
  if(data[0]==1)return true;
  else return false;
 }
//---------------------------------------------------------------------------
/* public static byte[] ByteToData(byte value)throws AException           //!!!
 {
  byte buf[]=new byte[1];
  buf[0]=(byte)value;
  return buf;
 }*/
//---------------------------------------------------------------------------
/* public static byte DataToByte(byte buf[])throws AException             //!!!
 {
  return buf[0];
 }*/
//---------------------------------------------------------------------------
/**
 * @param value значение типа Character
 * @return массив байт длиной 2 или null если value=null
 * @throws AException в случае ошибки
 */
 public static byte[] CharToData(Character value)throws AException      //???
 {
  if(value==null)return null;
  byte data[]=new byte[2];
  for(int q=0;q<2;q++)
  {
   data[q]=(byte)(char)value;
   value=(char)((int)value>>8);
  }
  return data;
 }
//---------------------------------------------------------------------------
/**
 * @param data массив байт длиной 2
 * @return значение типа Character или null если data=null
 * @throws AException в случае ошибки
 */
 public static Character DataToChar(byte data[])throws AException       //???
 {
  if(data==null)return null;
  int value=0;
  final int mask=0x000000ff;
  for(int q=2-1;q>=0;q--)
  {
   int b=data[q];
   b&=mask;
   value=value<<8;
   value|=b;
  }
  return (char)value;
 }
//---------------------------------------------------------------------------
/* public static byte[] ShortToData(short value)                           //!!!
 {
  byte buf[]=new byte[2];
  for(int q=0;q<2;q++)
  {
   buf[q]=(byte)value;
   value=(short)((int)value>>8);
  }
  return buf;
 }*/
//---------------------------------------------------------------------------
/* public static short DataToShort(byte buf[])                             //!!!
 {
  int value=0;
  final int mask=0x000000ff;
  for(int q=2-1;q>=0;q--)
  {
   int b=buf[q];
   b&=mask;
   value=value<<8;
   value|=b;
  }
  return (short)value;
 }*/
//---------------------------------------------------------------------------
/**
 * @param value значение типа Integer
 * @return массив байт длиной 4 или null если value=null
 * @throws AException в случае ошибки
 */
 public static byte[] IntToData(Integer value)throws AException         //!!!
 {
  if(value==null)return null;
  byte data[]=new byte[4];
  for(int q=0;q<4;q++)
  {
   data[q]=(byte)(int)value;
   value=value>>8;
  }
  return data;
 }
//---------------------------------------------------------------------------
/**
 * @param data массив байт длиной 4
 * @return значение типа Integer или null если data=null
 * @throws AException в случае ошибки
 */
 public static Integer DataToInt(byte data[])throws AException          //!!!
 {
  if(data==null)return null;
  int value=0;
  final int mask=0x000000ff;
  for(int q=4-1;q>=0;q--)
  {
   int b=data[q];
   b&=mask;
   value=value<<8;
   value|=b;
  }
  return value;
 }
//---------------------------------------------------------------------------
/* public static byte[] LongToData(long value)                             //!!!
 {
  byte buf[]=new byte[8];
  for(int q=0;q<8;q++)
  {
   buf[q]=(byte)value;
   value=value>>8;
  }
  return buf;
 }*/
//---------------------------------------------------------------------------
/* public static long DataToLong(byte buf[])                               //!!!
 {
  long value=0;
  final int mask=0x000000ff;
  for(int q=8-1;q>=0;q--)
  {
   int b=buf[q];
   b&=mask;
   value=value<<8;
   value|=b;
  }
  return value;
 }*/
//---------------------------------------------------------------------------
/**
 * @param value значение типа Float
 * @return массив байт длиной 4 или null если value=null
 * @throws AException в случае ошибки
 */
 public static byte[] FloatToData(Float value)                           //!!?
 {
  if(value==null)return null;
  byte data[]=new byte[4];
  int i=Float.floatToRawIntBits(value);//Float.floatToIntBits(value);
  for(int q=0;q<4;q++)
  {
   data[q]=(byte)i;
   i=i>>8;
  }
  return data;
 }
//---------------------------------------------------------------------------
/**
 * @param data массив байт длиной 4
 * @return значение типа Float или null если data=null
 * @throws AException в случае ошибки
 */
 public static Float DataToFloat(byte data[])                             //!!!
 {
  if(data==null)return null;
  int i=0;
  final int mask=0x000000ff;
  for(int q=4-1;q>=0;q--)
  {
   int b=data[q];
   b&=mask;
   i=i<<8;
   i|=b;
  }
  return Float.intBitsToFloat(i);
 }
//---------------------------------------------------------------------------
/* public static byte[] DoubleToData(double value)                        //!!?
 {
  byte buf[]=new byte[8];
  long l=Double.doubleToRawLongBits(value);//Double.doubleToLongBits(value);
  for(int q=0;q<8;q++)
  {
   buf[q]=(byte)l;
   l=l>>8;
  }
  return buf;
 }*/
//---------------------------------------------------------------------------
/* public static double DataToDouble(byte buf[])                          //!!!
 {
  long l=0;
  final int mask=0x000000ff;
  for(int q=8-1;q>=0;q--)
  {
   int b=buf[q];
   b&=mask;
   l=l<<8;
   l|=b;
  }
  return Double.longBitsToDouble(l);
 }*/
//---------------------------------------------------------------------------
/**
 * @param value значение типа String
 * @return массив байт длиной value.lenght*2 (UTF-8) или null если value=null
 * @throws AException в случае ошибки
 */
 public static byte[] StrToData(String value)throws AException          //!!!
 {
  if(value==null)return null;
  final int len=value.length();
  byte data[]=new byte[len*2];
  for(int q=0;q<len;q++)
  {
   int c=(int)value.charAt(q);
   data[q*2]=(byte)c;
   data[q*2+1]=(byte)(c>>8);
  }
  return data;
/*  if(value==null)return null;
  try{return value.getBytes("UTF-8");}
  catch(UnsupportedEncodingException ex){throw new AException(ex);}*/
 }
//---------------------------------------------------------------------------
/**
 * @param data массив байт длиной кратной 2 (UTF-8)
 * @return значение типа String или null если data=null
 * @throws AException в случае ошибки
 */
 public static String DataToStr(byte data[]) throws AException           //!!!
 {
  if(data==null)return null;
  String str="";
  int index=0;
  final int mask=0x000000ff;
  int value;
  int b;
  while(index<data.length)
  {
   value=data[index+1];
   value&=mask;
   b=data[index];
   b&=mask;
   value=value<<8;
   value|=b;
   str+=(char)value;
   index+=2;
  }
  return str;
  /*if(data==null)return null;
  try{return new String(data,"UTF-8");}
  catch(UnsupportedEncodingException ex){throw new AException(ex);}*/
 }
//---------------------------------------------------------------------------
 private AMemory FMemory;
//---------------------------------------------------------------------------
 private void initialize()
 {
  FMemory=new AMemory();
  FMemory.setParent(this);
 }
//---------------------------------------------------------------------------
 public AData(){initialize();}
//---------------------------------------------------------------------------
 public AData(final AData value) throws AException
 {
  initialize();
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(final AData value) throws AException
 {
  FMemory=new AMemory(value.FMemory);
  FMemory.setParent(this);
 }
//---------------------------------------------------------------------------
 public int getSize(){return FMemory.getSize();}
//---------------------------------------------------------------------------
// @Deprecated
// public byte[] bytes()
// {
//  return FMemory.bytes();
// }
//---------------------------------------------------------------------------
 public byte[] get()
 {
  if(FMemory.bytes()==null)return null;
  byte[] buf=new byte[FMemory.getSize()];
  for(int q=0;q<buf.length;q++)buf[q]=FMemory.bytes()[q];
  return buf;
 }
//---------------------------------------------------------------------------
 public void set(byte[] buf) throws AException
 {
  if(buf.length!=FMemory.getSize())throw new AException("buf.length!=FMemory.getSize() "+buf.length+"!="+FMemory.getSize());
  for(int q=0;q<FMemory.getSize();q++)FMemory.set(q,buf[q]);
 }
//---------------------------------------------------------------------------
 public void resize(int value) throws AException
 {
  FMemory=new AMemory(value,FMemory);
  FMemory.setParent(this);
 }
//---------------------------------------------------------------------------
 public void clear(){FMemory.clear();}
//---------------------------------------------------------------------------
 boolean isNull()
 {
  if(FMemory.bytes()==null)return true;
  return false;
 }
//---------------------------------------------------------------------------
 void setNull()
 {
  FMemory=new AMemory();
 }
//---------------------------------------------------------------------------
}
