/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adb;

import aclass.ABuffer;
import aclass.ACustomPtrArray;
//---------------------------------------------------------------------------
//---------------------------------------------------<     C L A S S     >---
//---------------------------------------------------------------------------
/**
 *
 * @author Anatol
 */
public class ADBEntryData extends ACustomPtrArray
{
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
 protected Object NewData()
 {
  ADBFieldData data=new ADBFieldData();
  data.setParent(this);
  return data;
 }
//---------------------------------------------------------------------------
 protected Object DeleteData(Object value)
 {
// if(value==NULL)return NULL;
// delete value;
  return null;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public ADBEntryData()
 {
 }
//---------------------------------------------------------------------------
 public ADBEntryData(final ADBEntryData value)
 {
  Assign(value);
 }
//---------------------------------------------------------------------------
 public final void Assign(final ADBEntryData value)
 {
  SetCount(value.PGetCount());
  for(int q=0;q<PGetCount();q++)
  {
   ((ADBFieldData)PGetPtr(q)).assign((ADBFieldData)value.PGetPtrConst(q));
  }
 }
//---------------------------------------------------------------------------
 public ADBFieldData FieldData(int index)
 {
  ADBFieldData value=(ADBFieldData)PGetPtrConst(index);
  if(value==null)
  {
   sendError("FieldInfo","value=NULL",index);
   return null;
  }
  return value;
 }
//---------------------------------------------------------------------------
 public int Add(final ADBFieldData value)
 {
  ADBFieldData data=(ADBFieldData)NewData();
  data.assign(value);
  return super.PAdd(data);
 }
//---------------------------------------------------------------------------
 public int Insert(final ADBFieldData value,final int index)
 {
  ADBFieldData data=(ADBFieldData)NewData();
  data.assign(value);
  return super.PInsert(data,index);
 }
//---------------------------------------------------------------------------
 public int New()
 {
  ADBFieldData data=(ADBFieldData)NewData();
  return super.PAdd(data);
 }
//---------------------------------------------------------------------------
 public int New(final int index)
 {
  ADBFieldData data=(ADBFieldData)NewData();
  return super.PInsert(data,index);
 }
//---------------------------------------------------------------------------
 public void Delete(final int index)
 {
  PSetPtr(index,DeleteData(PGetPtr(index)));
  super.PDelete(index);
 }
//---------------------------------------------------------------------------
 public void Clear()
 {
  for(int q=0;q<PGetCount();q++)PSetPtr(q,DeleteData(PGetPtr(q)));
  super.PClear();
 }
//---------------------------------------------------------------------------
 public void Swap(final int a,final int b)
 {
  super.PSwap(a,b);
 }
//---------------------------------------------------------------------------
 public void Move(final int a,final int b)
 {
  super.PMove(a,b);
 }
//---------------------------------------------------------------------------
 public int GetCount()                                                           //???
 {
  return super.PGetCount();
 }
//---------------------------------------------------------------------------
 public void SetCount(final int value)
 {
  if(value<0)
  {
   sendError("SetCount","Count<0",value);
   return;
  }
  if(PGetCount()==value)return;
  if(value==0)
  {
   Clear();
   return;
  }
  while(PGetCount()<value) New();
  while(PGetCount()>value) Delete(PGetCount()-1);
 }
//---------------------------------------------------------------------------
// __property int Count={read=GetCount,write=SetCount}
//---------------------------------------------------------------------------
 public int WriteToBuffer(ABuffer buf,final ADBEntryInfo Info)
 {
  if(buf==null)
  {
   sendError("WriteToBuffer","buf=NULL",null);
   return -1;
  }
// SendInfo("writeToBuffer","Test",0);
  int count=PGetCount();
  int pos;
  pos=buf.write(ABuffer.IntToBuf(count));//,4);//sizeof(count));
  for(int q=0;q<count;q++)
  {
   ADBFieldData value=(ADBFieldData)PGetPtrConst(q);
   pos=value.WriteToBuffer(buf,Info.FieldInfo(q));
  }
  return pos;
 }
//---------------------------------------------------------------------------
 public int ReadFromBuffer(ABuffer buf,final ADBEntryInfo Info)
 {
  if(buf==null)
  {
   sendError("ReadFromBuffer","buf=NULL",null);
   return -1;
  }
  Clear();
  byte array[]=new byte[4];
  int pos;
  pos=buf.read(array,4);//sizeof(count));
  int count=ABuffer.BufToInt(array);
  if(pos==-1)
  {
   sendError("ReadFromBuffer","Count not read",null);
   return -1;
  }
  for(int q=0;q<count;q++)
  {
   ADBFieldData value=new ADBFieldData();
   pos=value.ReadFromBuffer(buf,Info.FieldInfo(q));
   if(pos==-1)
   {
    sendError("ReadFromBuffer","Info not read",q);
    return -1;
   }
   Add(value);
  }
  return pos;
 }
//---------------------------------------------------------------------------
}
