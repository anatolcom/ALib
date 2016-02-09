/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;

import aclass.ABuffer;
import aclass.AClass;
import aclass.AException;
import aclass.AMemory;
//---------------------------------------------------------------------------
//---------------------------------------------------<     C L A S S     >---
//---------------------------------------------------------------------------

/**
 *
 * @author Anatol
 */
public class AParserValue extends AClass
{
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
 private AMemory FMemory;
//---------------------------------------------------------------------------
 private void initialize()
 {
  FMemory=new AMemory();
  FMemory.setParent(this);
 }
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public AParserValue(){initialize();}
//---------------------------------------------------------------------------
 public AParserValue(final AParserValue value) throws AException
 {
  initialize();
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(final AParserValue value) throws AException
 {
  FMemory=new AMemory(value.FMemory);
  FMemory.setParent(this);
 }
//---------------------------------------------------------------------------
 public int getSize(){return FMemory.getSize();}
//---------------------------------------------------------------------------
 public byte[] getPtr()
 {
  byte buf[]=FMemory.bytes();                                                   //???
  if(buf==null)buf=new byte[0];                                                 //???
  return buf;                                                                   //???
 }
//---------------------------------------------------------------------------
 public void setPtr(byte[] buf) throws AException
 {
  if(buf.length!=FMemory.getSize())throw new AException("buf.length!=FMemory.getSize()");
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
 public int WriteToBuffer(ABuffer buf,final AParserValueType Info) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  if(Info.isStatic())
  {
   boolean isnull=false;
   if(FMemory.getSize()==0)isnull=true;
   buf.write(ABuffer.BoolToBuf(isnull));
   if(isnull)
   {
    for(int q=0;q<Info.getStaticSize();q++)buf.write(ABuffer.ByteToBuf((byte)0));
   }
   else buf.write(FMemory.bytes());
  }
  else
  {
   int len=FMemory.getSize();
   buf.write(ABuffer.IntToBuf(len));
   if(len>0)buf.write(FMemory.bytes());
  }
  return buf.getPos();
 }
//---------------------------------------------------------------------------
 int ReadFromBuffer(ABuffer buf,final AParserValueType Info) throws AException
 {
  if(buf==null)
  {
   sendError("ReadFromBuffer","buf=NULL",null);
   return -1;
  }
  FMemory.clear();
  if(Info.isStatic())
  {
   boolean isnull=ABuffer.BufToBool(buf.read(1));
   if(Info.getType()!=AParserVarible.TYPE_VOID)                                        //??? AParserVarible.TYPE_VOID
   {
    if((!isnull)||Info.NotNULL)resize(Info.getStaticSize());
   }
   if(isnull)
   {
    for(int q=0;q<Info.getStaticSize();q++)ABuffer.BufToByte(buf.read(1));
   }
   else
   {
    byte[] array=buf.read(FMemory.getSize());
    for(int q=0;q<array.length;q++)FMemory.set(q,array[q]);
   }
  }
  else
  {
   int len=ABuffer.BufToInt(buf.read(4));
   resize(len);
   if(len>0)
   {
    byte[] array=buf.read(FMemory.getSize());
    for(int q=0;q<array.length;q++)FMemory.set(q,array[q]);
   }
  }
  return buf.getPos();
 }
//---------------------------------------------------------------------------
}
