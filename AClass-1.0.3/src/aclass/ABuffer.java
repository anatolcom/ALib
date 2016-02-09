/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Anatol
 * @version 0.1.0.0
 */
public class ABuffer extends AClass
{
//---------------------------------------------------------------------------
// public final byte bcASCII=0;
// public final byte bcUnicode=1;
//---------------------------------------------------------------------------
 public static byte[] BoolToBuf(boolean value)                          //!!!
 {
  byte buf[]=new byte[1];
  if(value)buf[0]=1;
  else buf[0]=0;
  return buf;
 }
//---------------------------------------------------------------------------
 public static boolean BufToBool(byte buf[])                            //!!!
 {
  if(buf[0]==1)return true;
  else return false;
 }
//---------------------------------------------------------------------------
 public static byte[] ByteToBuf(byte value)                             //!!!
 {
  byte buf[]=new byte[1];
  buf[0]=(byte)value;
  return buf;
 }
//---------------------------------------------------------------------------
 public static byte BufToByte(byte buf[])                               //!!!
 {
  return buf[0];
 }
//---------------------------------------------------------------------------
 public static byte[] CharToBuf(char value)                             //???
 {
  byte buf[]=new byte[2];
  for(int q=0;q<2;q++)
  {
   buf[q]=(byte)value;
   value=(char)((int)value>>8);
  }
  return buf;
 }
//---------------------------------------------------------------------------
 public static char BufToChar(byte buf[])                               //???
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
  return (char)value;
 }
//---------------------------------------------------------------------------
 public static byte[] ShortToBuf(short value)                           //!!!
 {
  byte buf[]=new byte[2];
  for(int q=0;q<2;q++)
  {
   buf[q]=(byte)value;
   value=(short)((int)value>>8);
  }
  return buf;
 }
//---------------------------------------------------------------------------
 public static short BufToShort(byte buf[])                             //!!!
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
 }
//---------------------------------------------------------------------------
 public static byte[] IntToBuf(int value)                               //!!!
 {
  byte buf[]=new byte[4];
  for(int q=0;q<4;q++)
  {
   buf[q]=(byte)value;
   value=value>>8;
  }
  return buf;
 }
//---------------------------------------------------------------------------
 public static int BufToInt(byte buf[])                                 //!!!
 {
  int value=0;
  final int mask=0x000000ff;
  for(int q=4-1;q>=0;q--)
  {
   int b=buf[q];
   b&=mask;
   value=value<<8;
   value|=b;
  }
  return value;
 }
//---------------------------------------------------------------------------
 public static byte[] LongToBuf(long value)                             //!!!
 {
  byte buf[]=new byte[8];
  for(int q=0;q<8;q++)
  {
   buf[q]=(byte)value;
   value=value>>8;
  }
  return buf;
 }
//---------------------------------------------------------------------------
 public static long BufToLong(byte buf[])                               //!!!
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
 }
//---------------------------------------------------------------------------
 public static byte[] FloatToBuf(float value)                           //!!?
 {
  byte buf[]=new byte[4];
  int i=Float.floatToRawIntBits(value);//Float.floatToIntBits(value);
  for(int q=0;q<4;q++)
  {
   buf[q]=(byte)i;
   i=i>>8;
  }
  return buf;
 }
//---------------------------------------------------------------------------
 public static float BufToFloat(byte buf[])                             //!!!
 {
  int i=0;
  final int mask=0x000000ff;
  for(int q=4-1;q>=0;q--)
  {
   int b=buf[q];
   b&=mask;
   i=i<<8;
   i|=b;
  }
  return Float.intBitsToFloat(i);
 }
//---------------------------------------------------------------------------
 public static byte[] DoubleToBuf(double value)                         //!!?
 {
  byte buf[]=new byte[8];
  long l=Double.doubleToRawLongBits(value);//Double.doubleToLongBits(value);
  for(int q=0;q<8;q++)
  {
   buf[q]=(byte)l;
   l=l>>8;
  }
  return buf;
 }
//---------------------------------------------------------------------------
 public static double BufToDouble(byte buf[])                           //!!!
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
 }
//---------------------------------------------------------------------------
 public static byte[] StrToBuf(String value)                            //!!!
 {
  final int len=value.length();
  byte buf[]=new byte[len*2];
  for(int q=0;q<len;q++)
  {
   int c=(int)value.charAt(q);
   buf[q*2]=(byte)c;
   buf[q*2+1]=(byte)(c>>8);
  }
  return buf;
 }
//---------------------------------------------------------------------------
 public static String BufToStr(byte buf[])                              //!!!
 {
  String str="";
  int index=0;
  final int mask=0x000000ff;
  int value;
  int b;
  while(index<buf.length)
  {
   value=buf[index+1];
   value&=mask;
   b=buf[index];
   b&=mask;
   value=value<<8;
   value|=b;
   str+=(char)value;
   index+=2;
  }
  return str;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
 private byte FBuf[]=null;
 private int FCursor=-1;
 private int FSpace=0;
 private int FUsed=0;
 private boolean FSmartSize=true;
//---------------------------------------------------------------------------
 private byte[] CNewData(int len) throws AException
 {
  byte data[]=new byte[len];
  if(data==null)throw new AException("data=NULL");
  return data;
 }
//---------------------------------------------------------------------------
 private byte[] CDeleteData(byte value[])
 {
//  if(value==null)return null;
//  delete value;
  return null;
 }
//---------------------------------------------------------------------------
 private boolean CSetLenght(int newlen,boolean clear) throws AException
 {
  if(newlen==0)
  {
   clear(true);
   return true;
  }
  if(clear)
  {
   if(newlen==FSpace)//???
   {
    FUsed=0;
    return true;
   }
   clear(true);
   FBuf=CNewData(newlen);
   FCursor=0;//FBuf;
  }
  else
  {
   if(newlen==FSpace)return true;
   int pos=getPos();
   byte NewBuf[]=CNewData(newlen);
   if(NewBuf==null)
   {
    FBuf=null;
    FCursor=-1;
    FSpace=0;
    FUsed=0;
    throw new AException("NewBuf==NULL");
   }
   if(FUsed>newlen)FUsed=newlen;
   for(int q=0;q<FUsed;q++)NewBuf[q]=FBuf[q];
//   System.arraycopy(FBuf,0,NewBuf,0,FUsed);
   CDeleteData(FBuf);
   FBuf=NewBuf;
   if(pos<FUsed)FCursor=pos;//FBuf+pos;
   else FCursor=-1;
  }
  FSpace=newlen;
  return true;
 }
//---------------------------------------------------------------------------
 private int CCursorUp(int cursor,int len)
 {
  if(getPos()+len>=FUsed)return -1;
  return cursor+len;
 }
//---------------------------------------------------------------------------
 private int CRead(byte buf[],int len,int cursor)
 {
  if(len==0)return cursor;
//  for(int q=0;q<len;q++){buf[q]=FBuf[cursor+q];}
  System.arraycopy(FBuf,cursor,buf,0,len);
  return CCursorUp(cursor,len);
 }
//---------------------------------------------------------------------------
 private int CWrite(final byte buf[],int len,int cursor)
 {
  if(len==0)return cursor;
//  for(int q=0;q<len;q++){FBuf[cursor+q]=buf[q];}
  System.arraycopy(buf,0,FBuf,cursor,len);
  int used=getPos()+len;
  if(FUsed<used)FUsed=used;
  return CCursorUp(cursor,len);
 }
//---------------------------------------------------------------------------
 private int CInsert(final byte buf[],int len,int cursor)
 {
  if(len==0)return cursor;
// for(int q=FUsed-(cursor-FBuf);q>=0;q--)cursor[q+len]=cursor[q];
  for(int q=FUsed-cursor;q>=0;q--)FBuf[cursor+q+len]=FBuf[cursor+q];
//  for(int q=0;q<len;q++){FBuf[cursor+q]=buf[q];}
  System.arraycopy(buf,0,FBuf,cursor,len);
  FUsed+=len;
  return CCursorUp(cursor,len);
 }
//---------------------------------------------------------------------------
 private int CDelete(int len,int cursor)
 {
  if(len==0)return cursor;
// for(int q=0;q<FUsed-(cursor-FBuf)-len;q++)cursor[q]=cursor[q+len];
  for(int q=0;q<FUsed-cursor-len;q++)FBuf[cursor+q]=FBuf[cursor+q+len];
  FUsed-=len;
  return cursor;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public ABuffer(){super();}
//---------------------------------------------------------------------------
 public ABuffer(final ABuffer value) throws AException
 {
  super();
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(final ABuffer value) throws AException
 {
  CSetLenght(value.FSpace,false);
  System.arraycopy(value.FBuf,0,FBuf,0,value.FUsed);
  FUsed=value.FUsed;
  FCursor=0;//FBuf;
 }
//---------------------------------------------------------------------------
 public int lenght(){return FSpace;}
//---------------------------------------------------------------------------
 public int used(){return FUsed;}
//---------------------------------------------------------------------------
 public int free(){return FSpace-FUsed;}
//---------------------------------------------------------------------------
 public int getPos()
 {
  if(FCursor==-1)return FUsed;
  return FCursor;
 }
//---------------------------------------------------------------------------
 public void setPos(int value) throws AException
 {
  if(value<0)throw new AException("value<0");
  if(value>FUsed)throw new AException("value>Used");
  if(value==FUsed)FCursor=-1;
  else FCursor=value;
 }
//---------------------------------------------------------------------------
 public void setFirstPos(){FCursor=0;}
//---------------------------------------------------------------------------
 public void setLastPos(){FCursor=-1;}
//---------------------------------------------------------------------------
 public byte[] read(int len) throws AException
 {
  if(len<0)throw new AException("len:" + len + " < 0");
  if(len>FUsed-getPos())throw new AException("len:" + len + " > Used - Pos");
  byte[] buf=new byte[len];
  FCursor=CRead(buf,len,FCursor);
  return buf;
 }
//---------------------------------------------------------------------------
 public String readStr(int len) throws AException
 {
  if(len<0)throw new AException("len<0");
  if(len>FUsed-getPos())throw new AException("len:" + len + " > Used - Pos");
  try{return new String(read(len),"UTF-8");}
  catch(UnsupportedEncodingException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public String readLStr() throws AException
 {
  return readStr(BufToInt(read(4)));
 }
//---------------------------------------------------------------------------
 public int write(final byte buf[]) throws AException//,int len
 {
  if(buf==null)throw new AException("buf=NULL");
  int len=buf.length;
  if(len<0)throw new AException("len:" + len + " < 0");
  if(((int)getPos()+(int)len)>0x0fffffff)throw new AException("Result len>Max");
  if(len==0)
  {
   sendWarning("Write","len=0");
   return getPos();
  }
  if(len>free())
  {
   int newlen=getPos()+len;
   if(FSmartSize)newlen*=2;
   if(!CSetLenght(newlen,false))return -1;
  }
  if(FCursor==-1)FCursor=FUsed;//FBuf+FUsed;
  FCursor=CWrite(buf,len,FCursor);
  return getPos();
 }
//---------------------------------------------------------------------------
 public int writeStr(final String str) throws AException
 {
  if(str.isEmpty())
  {
   sendWarning("WriteStr","str Is Empty");
   return getPos();
  }
  try{return write(str.getBytes("UTF-8"));}
  catch(UnsupportedEncodingException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public int writeLStr(final String str) throws AException
 {
  try
  {
   byte[] buf=str.getBytes("UTF-8");
   int pos=write(IntToBuf(buf.length));
   if(buf.length>0)pos=write(buf);
   return pos;
  }
  catch(UnsupportedEncodingException ex){throw new AException(ex);}
//  int len=str.length()*2;//sizeof(wchar_t);
//  int pos=write(IntToBuf(len));//,4);//sizeof(len));
//  if(len>0)pos=writeStr(str);
//  return pos;
 }
 //---------------------------------------------------------------------------
 public int insert(final byte buf[]) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  if(((int)FUsed+(int)buf.length)>0x0fffffff)throw new AException("Result len>Max");
  if(buf.length==0)
  {
   sendWarning("Insert","len=0");
   return getPos();
  }
  if(buf.length>free())
  {
   int newlen=FUsed+buf.length;
   if(FSmartSize)newlen*=2;
   if(!CSetLenght(newlen,false))return -1;
  }
  if(FCursor==-1)FCursor=FUsed;
  FCursor=CInsert(buf,buf.length,FCursor);
  return getPos();
 }
//---------------------------------------------------------------------------
 public int insertStr(final String str) throws AException
 {
  if(str.isEmpty())
  {
   sendWarning("InsertStr","str Is Empty");
   return getPos();
  }
  try{return insert(str.getBytes("UTF-8"));}
  catch(UnsupportedEncodingException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public int insertLStr(final String str) throws AException
 {
  try
  {
   byte[] buf=str.getBytes("UTF-8");
   int pos=insert(IntToBuf(buf.length));
   if(buf.length>0)pos=insert(buf);
   return pos;
  }
  catch(UnsupportedEncodingException ex){throw new AException(ex);}
//  int len;
//  len=str.length()*2;//sizeof(wchar_t);
//  int pos=insert(IntToBuf(len));//,4);//sizeof(len));
//  if(len>0)pos=insertStr(str);
//  return pos;
 }
//---------------------------------------------------------------------------
 public int add(final byte buf[]) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  if(((int)FUsed+(int)buf.length)>0x0fffffff)throw new AException("Result len>Max");
  if(buf.length==0)
  {
   sendWarning("Add","len=0");
   return getPos();
  }
  if(buf.length>free())
  {
   int newlen=FUsed+buf.length;
   if(FSmartSize)newlen*=2;
   if(!CSetLenght(newlen,false))return -1;
  }
  FCursor=FUsed;
  FCursor=CWrite(buf,buf.length,FCursor);
  return getPos();
 }
//---------------------------------------------------------------------------
 public int addStr(final String str) throws AException//,byte charset=bcASCII)
 {
  if(str.isEmpty())
  {
   sendWarning("AddStr","str Is Empty");
   return getPos();
  }
  try{return add(str.getBytes("UTF-8"));}
  catch(UnsupportedEncodingException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public int addLStr(final String str) throws AException
 {
  byte[] buf=str.getBytes();
  int len=buf.length;
  int pos=add(IntToBuf(len));
  if(len>0)pos=add(buf);
  return pos;
 }
//---------------------------------------------------------------------------
 public int writeToBuffer(ABuffer buf,int len) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  if(len>FUsed-getPos())throw new AException("len>Used-Pos");
  if(len==0)
  {
   sendWarning("WriteToBuffer","len=0");
   return getPos();
  }
  if(FCursor==-1)throw new AException("Cursor=NULL");
  byte array[]=new byte[len];
  for(int q=0;q<len;q++)array[q]=FBuf[q+FCursor];
//  System.arraycopy(FBuf,FCursor,b,0,len);
  return buf.write(array);
 }
//---------------------------------------------------------------------------
 public int insertToBuffer(ABuffer buf,int len) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  if(len>FUsed-getPos())throw new AException("len>Used-Pos");
  if(len==0)
  {
   sendWarning("InsertToBuffer","len=0");
   return getPos();
  }
  if(FCursor==-1)throw new AException("Cursor=NULL");
  byte array[]=new byte[len];
  for(int q=0;q<len;q++)array[q]=FBuf[q+FCursor];
//  System.arraycopy(FBuf,FCursor,b,0,len);
  return buf.insert(array);//,len);
 }
//---------------------------------------------------------------------------
 public void delete(int len) throws AException
 {
  if(len<0)throw new AException("len<0");
  if(len>FUsed-getPos())throw new AException("len>Used-Pos");
  FCursor=CDelete(len,FCursor);
 }
//---------------------------------------------------------------------------
 public void resize(int newlen,boolean clear) throws AException
 {
  if(newlen<0)throw new AException("value<0");
  if(!CSetLenght(newlen,clear))throw new AException("Impossible set lenght");
 }
//---------------------------------------------------------------------------
 public void clear(boolean del)
 {
  if(del)
  {
   FBuf=CDeleteData(FBuf);
   FSpace=0;
  }
  FCursor=-1;
  FUsed=0;
 }
//---------------------------------------------------------------------------
 public void clear(){clear(true);}
//---------------------------------------------------------------------------
 public void trim() throws AException{CSetLenght(FUsed,false);}
//---------------------------------------------------------------------------
 public void FillNull(int len) throws AException                                //небезопасная функция
 {
  if(len<0)throw new AException("len<0");
  if(FSpace<len)CSetLenght(len,true);
  for(int q=0;q<len;q++)FBuf[q]=0;
  FUsed=len;
 }
//---------------------------------------------------------------------------
// public int getPos(){return getPos();}
//---------------------------------------------------------------------------
// public void setPos(int value) throws AException{setPos(value);}
//---------------------------------------------------------------------------
 public byte[] getPtr()                                                         //небезопасная функция
 {
  byte buf[]=new byte[FUsed];
  System.arraycopy(FBuf,0,buf,0,FUsed);
  return buf;
 }
//---------------------------------------------------------------------------
 public void setPtr(byte buf[]) throws AException                               //небезопасная функция
 {
  if(FUsed!=buf.length)throw new AException("FUsed!=buf.length");
  System.arraycopy(buf,0,FBuf,0,buf.length);
 }
//---------------------------------------------------------------------------
}
