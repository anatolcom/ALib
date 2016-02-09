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
public class ADBEntryInfo extends ACustomPtrArray
{
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
 protected Object NewData()
 {
  ADBFieldInfo data=null;
  data=new ADBFieldInfo();
  data.setParent(this);
  return data;
 }
//---------------------------------------------------------------------------
 protected Object DeleteData(Object value)
 {
// if(value==null)return null;
// delete value;
  return null;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
 protected int New()
 {
  ADBFieldInfo data=(ADBFieldInfo)NewData();
  return super.PAdd(data);
 }
//---------------------------------------------------------------------------
 protected void SetCount(final int value)
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
  while(PGetCount()<value)New(); ////~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  while(PGetCount()>value)Delete(PGetCount()-1);
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public ADBEntryInfo()
 {
 }
//---------------------------------------------------------------------------
 public ADBEntryInfo(final ADBEntryInfo value)
 {
  Assign(value);
 }
//---------------------------------------------------------------------------
 public final void Assign(final ADBEntryInfo value)
 {
  SetCount(value.PGetCount());
  for(int q=0;q<PGetCount();q++)
  {
   ((ADBFieldInfo)PGetPtr(q)).assign((ADBFieldInfo)value.PGetPtrConst(q));
  }
 }
//---------------------------------------------------------------------------
 public ADBFieldInfo FieldInfo(int index)
 {
  ADBFieldInfo value=(ADBFieldInfo)PGetPtrConst(index);
  if(value==null)
  {
   sendError("FieldInfo","value=NULL",index);
   return null;
  }
  return value;
 }
//---------------------------------------------------------------------------
 public int Add(final ADBFieldInfo value)
 {
  for(int q=0;q<PGetCount();q++)
  {
   if(((ADBFieldInfo)PGetPtr(q)).getName().equals(value.getName()))
   {
    sendError("Add","Name \""+value.getName()+"\" alredy exist",q);
    return -1;
   }
  }
  ADBFieldInfo data=(ADBFieldInfo)NewData();
  data.assign(value);
  return super.PAdd(data);
 }
//---------------------------------------------------------------------------
 public int Insert(final ADBFieldInfo value,final int index)
 {
  for(int q=0;q<PGetCount();q++)
  {
   if(((ADBFieldInfo)PGetPtr(q)).getName().equals(value.getName()))
   {
    sendError("Insert","Name \""+value.getName()+"\" alredy exist",q);
    return -1;
   }
  }
  ADBFieldInfo data=(ADBFieldInfo)NewData();
  data.assign(value);
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
 public int GetCount()
 {
  return super.PGetCount();
 }
//---------------------------------------------------------------------------
// __property int Count={read=GetCount}
//---------------------------------------------------------------------------
 public int Index(String Name)
 {
  for(int q=0;q<GetCount();q++)
  {
//  ADBFieldInfo value=new ADBFieldInfo((ADBFieldInfo)GetPtrConst(q));
   ADBFieldInfo value=(ADBFieldInfo)PGetPtrConst(q);
   if(value==null)
   {
    sendError("Index","value=NULL",q);
    return -1;
   }
   if(value.getName().equals(Name))return q;
  }
  return -1;
 }
//---------------------------------------------------------------------------
 public int WriteToBuffer(ABuffer buf)
 {
  if(buf==null)
  {
   sendError("WriteToBuffer","buf=NULL",null);
   return -1;
  }
  int pos;
  int count=PGetCount();
  pos=buf.write(ABuffer.IntToBuf(count));//,4);//sizeof(count));
  for(int q=0;q<count;q++)
  {
   ADBFieldInfo value=(ADBFieldInfo)PGetPtrConst(q);
   pos=value.WriteToBuffer(buf);
  }
  return pos;
 }
//---------------------------------------------------------------------------
 public int ReadFromBuffer(ABuffer buf)
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
   ADBFieldInfo value=new ADBFieldInfo();
   pos=value.ReadFromBuffer(buf);
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
 @Override
 public boolean equals(Object obj)
 {
  if(obj==null)return false;
  if(getClass()!=obj.getClass())return false;
  final ADBEntryInfo other=(ADBEntryInfo)obj;
  for(int q=0;q<PGetCount();q++)
  {
   ADBFieldInfo info=(ADBFieldInfo)PGetPtrConst(q);
   ADBFieldInfo otherinfo=(ADBFieldInfo)other.PGetPtrConst(q);
   if(!info.equals(otherinfo))return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public int hashCode()
 {
  int hash=5;
  return hash;
 }
//---------------------------------------------------------------------------
}
