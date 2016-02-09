/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adb;

import aclass.ABuffer;
import aclass.ACustomPtrArray;
import aclass.aref.*;
import java.util.ArrayList;
/**
 *
 * @author Anatol
 */
public class ADBTable extends ACustomPtrArray
{
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
// ADBBeforInsertEvent FOnBeforInsert;
// ADBAfterInsertEvent FOnAfterInsert;
// ADBBeforUpdateEvent FOnBeforUpdate;
// ADBAfterUpdateEvent FOnAfterUpdate;
// ADBBeforDeleteEvent FOnBeforDelete;
// ADBAfterDeleteEvent FOnAfterDelete;
//---------------------------------------------------------------------------
 private ArrayList<ADBTableEventListener> FCanModifyListener;
 private ArrayList<ADBTableEventListener> FDoModifyListener;
//---------------------------------------------------------------------------
 private String FName;
//---------------------------------------------------------------------------
 private ADBEntryInfo FInfo;
 private ADBEntryData FDefaultData;
 private int FPrimaryKeyField;
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
 protected Object NewData()
 {
  ADBEntryData data=new ADBEntryData();
  data.setParent(this);
  return data;
 }
//---------------------------------------------------------------------------
 protected Object DeleteData(Object value)
 {
  return null;
 }
//---------------------------------------------------------------------------
 protected ADBEntry GetDefault()
 {
  ADBEntry value=new ADBEntry();
  if(!value.Set(FInfo,FDefaultData))
  {
   sendError("GetDefault","value don't set",null);
   return null;
  }
  return value;
 }
//---------------------------------------------------------------------------
 protected void SetDefault(ADBEntry value)
 {
//// if(GetCount()>0)
//// {
////  SendError("SetDefault","Table not empty",eiImpossibleOperation,GetCount());
////  return;
//// }
  if(!FInfo.equals(value.GetInfo()))
  {
   sendError("SetDefault","Incorrect value",null);
   return;
  }
  FDefaultData.Assign(value.GetData());
//// SendError("SetDefault","this function don't realised");
 }
//---------------------------------------------------------------------------
 protected ADBEntry GetEntry(int index)
 {
  ADBEntry value=new ADBEntry();
  ADBEntryData entry=(ADBEntryData)PGetPtr(index);
  if(entry==null)
  {
   sendError("GetEntry","entry=NULL",null);
   return null;
  }
  if(!value.Set(FInfo,entry))
  {
   sendError("GetEntry","value don't set",null);
   return null;
  }
  return value;
 }
//---------------------------------------------------------------------------
 protected int GetInsertIndex(final ADBFieldData value,int field,boolean unique)
 {
  final ADBFieldInfo info=FInfo.FieldInfo(field);
  int index=PGetCount();
  for(int q=0;q<PGetCount();q++)
  {
   ADBEntryData entry=(ADBEntryData)PGetPtr(q);
   final ADBFieldData data=entry.FieldData(field);
   char compare=ADBField.CompareDBField(data,info,value,info);
   if(unique)if(compare=='=')return -1;
   if(compare!='>')continue;
   index=q;
   break;
  }
//// if(!unique)return index;
//// if(index==0)return index;

//// ADBEntryData *entry=(ADBEntryData*)GetPtr(index-1 );
//// const ADBFieldData *data=entry->FieldData(field);
//// if(CompareDBField(*data,*info,value,*info)!='>')continue;
  return index;
 }
//---------------------------------------------------------------------------
 protected ADBIndexList GetIndexList(final ADBFieldData value,int field)
 {
  final ADBFieldInfo info=FInfo.FieldInfo(field);
  ADBIndexList list=new ADBIndexList();

  for(int q=PGetCount()-1;q>=0;q--)
  {
   ADBEntryData entry=(ADBEntryData)PGetPtr(q);
   final ADBFieldData data=entry.FieldData(field);
   char c=ADBField.CompareDBField(data,info,value,info);
   if(c=='=')list.add(q);
   if(c=='e')
   {
    sendError("GetIndexList","CompareDBField return error",null);
    return null;
   }
  }
  return list;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public ADBTable(String Name)
 {
//  FOnBeforInsert=null;
//  FOnAfterInsert=null;
//  FOnBeforUpdate=null;
//  FOnAfterUpdate=null;
//  FOnBeforDelete=null;
//  FOnAfterDelete=null;
  FCanModifyListener = new ArrayList<ADBTableEventListener>();
  FDoModifyListener = new ArrayList<ADBTableEventListener>();
  FName=Name;
  FInfo=new ADBEntryInfo();
  FInfo.setParent(this);
  FDefaultData=new ADBEntryData();
  FDefaultData.setParent(this);
  FPrimaryKeyField=-1;
 }
//---------------------------------------------------------------------------
// __property String Name={read=FName};
//---------------------------------------------------------------------------
// __property ADBEntryInfo Info={read=FInfo};
//---------------------------------------------------------------------------
 public boolean Set(final ADBEntry value)
 {
  if(PGetCount()>0)
  {
   sendError("Set","Table not empty",PGetCount());
   return false;
  }
  FPrimaryKeyField=-1;
  FInfo.Assign(value.GetInfo());
  FDefaultData.Assign(value.GetData());
  return true;
 }
//---------------------------------------------------------------------------
 public boolean SetPrimaryKey(int field)
 {
  if(PGetCount()>0)
  {
   sendError("SetPrimaryKey","Table not empty",PGetCount());
   return false;
  }
  if(field<0)
  {
   FPrimaryKeyField=-1;
   return true;
  }
  if(field>=FInfo.GetCount())
  {
   sendError("SetPrimaryKey","field>=Count",field);
   return false;
  }
  if(!FInfo.FieldInfo(field).isIndexable())
  {
   sendError("SetPrimaryKey","Field isn't indexable",field);
   return false;
  }
  FPrimaryKeyField=field;
  return true;
 }
//---------------------------------------------------------------------------
 public int Insert(ADBEntry value)
 {
  if(!testNull(value,"Insert","value"))return -1;
// SendError("insert","this function don't realised");
//  boolean succes=true;
//  if(FOnBeforInsert!=null)FOnBeforInsert(this,value,succes);
//  if(!succes)return -1;
  if(!canInsertEvent(value))return -1;
  int index;
  if(FPrimaryKeyField==-1)index=PGetCount();
  else index=GetInsertIndex(value.GetData().FieldData(FPrimaryKeyField),FPrimaryKeyField,true);
  if(index==-1)
  {
   sendError("Insert","KeyField not unique",null);
   return -1;
  }
  ADBEntryData entry=(ADBEntryData)NewData();
  entry.Assign(value.GetData());
  index=super.PInsert(entry,index);
//  if(FOnAfterInsert!=null)FOnAfterInsert(this,value);
  doInsertEvent(value);
  return index;
 }
//---------------------------------------------------------------------------
 public int Update(int index,ADBEntry value)
 {
  if(!testNull(value,"Update","value"))return -1;
// SendError("Update","this function don't realised");
  ADBEntryData entry=(ADBEntryData)PGetPtr(index);
  /*boolean succes=true;
  if(FOnBeforUpdate!=null)
  {
   ADBEntry oldvalue=new ADBEntry();
   if(!oldvalue.Set(FInfo,entry))
   {
    sendError("Update","oldvalue don't set",null);
    return -1;
   }
   FOnBeforUpdate(this,value,oldvalue,succes);
  }
  if(!succes)return -1;*/
  if(!canUpdateEvent(value))return -1;

  if(FPrimaryKeyField!=-1)
  {
   final ADBFieldInfo info=FInfo.FieldInfo(FPrimaryKeyField);
   char compare=ADBField.CompareDBField(entry.FieldData(FPrimaryKeyField),info,
           value.GetData().FieldData(FPrimaryKeyField),info);
   if(compare!='=')
   {
    sendError("Update","KeyField has been modifed",null);
    return -1;
   }
  }
  entry.Assign(value.GetData());
//  if(FOnAfterUpdate!=null)FOnAfterUpdate(this,value);
  doUpdateEvent(value);
  return index;
 }
//---------------------------------------------------------------------------
 public int Delete(int index)
 {
// SendError("delete","this function don't realised");
  ADBEntryData entry=(ADBEntryData)PGetPtr(index);
  ADBEntry value=new ADBEntry();
  if(!value.Set(FInfo,entry))
  {
   sendError("Delete","value don't set",null);
   return -1;
  }
//  boolean succes=true;
//  if(FOnBeforDelete!=null)FOnBeforDelete(this,value,succes);
//  if(!succes)return -1;
  if(!canDeleteEvent(value))return -1;
  PSetPtr(index,DeleteData(PGetPtr(index)));
  super.PDelete(index);
//  if(FOnAfterDelete!=null)FOnAfterDelete(this,value);
  doDeleteEvent(value);
  return index;
 }
//---------------------------------------------------------------------------
 public int Delete(ADBIndexList List)
 {
  int count=0;
  for(int q=0;q<List.count();q++)if(Delete(List.get(q))!=-1)count++;
  return count;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
// __property int Count={read=GetCount};
//---------------------------------------------------------------------------
// __property ADBEntry Default={read=GetDefault,write=SetDefault};
//---------------------------------------------------------------------------
// __property int PrimaryKey={read=FPrimaryKeyField};
//---------------------------------------------------------------------------
// __property ADBEntry Entry[int index]={read=GetEntry};
//---------------------------------------------------------------------------
 public ADBIndexList IndexList(final ADBField value)
{
 ADBIndexList list=new ADBIndexList();
 int field=FInfo.Index(value.GetInfo().getName());
 if(field==-1)return list;
 list=GetIndexList(value.GetData(),field);
 return list;
}
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
 public void Clear()//virtual
 {
  for(int q=0;q<PGetCount();q++)PSetPtr(q,DeleteData(PGetPtr(q)));
  super.PClear();
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
 String str="ADBTable";//getClass().getSimpleName();
 pos=buf.writeLStr(str);
 pos=FInfo.WriteToBuffer(buf);
 pos=FDefaultData.WriteToBuffer(buf,FInfo);
 pos=buf.write(ABuffer.IntToBuf(FPrimaryKeyField));//,sizeof(FPrimaryKeyField));
 int count=PGetCount();
 pos=buf.write(ABuffer.IntToBuf(count));//,sizeof(count));
 for(int q=0;q<PGetCount();q++)
 {
  ADBEntryData entry=(ADBEntryData)PGetPtr(q);
  if(entry==null)
  {
   sendError("WriteToBuffer","entry=NULL",null);
   return -1;
  }
  pos=entry.WriteToBuffer(buf,FInfo);
 }
 return pos;
}
//---------------------------------------------------------------------------
 public int WriteToBuffer(ABuffer buf,ADBIndexList List)
{
 if(buf==null)
 {
  sendError("WriteToBuffer","buf=NULL",null);
  return -1;
 }
 int pos;
 String str="ADBTable";//getClass().getSimpleName();
 pos=buf.writeLStr(str);
 pos=FInfo.WriteToBuffer(buf);
 pos=FDefaultData.WriteToBuffer(buf,FInfo);
 pos=buf.write(ABuffer.IntToBuf(FPrimaryKeyField));//,sizeof(FPrimaryKeyField));
 int count=List.count();
 pos=buf.write(ABuffer.IntToBuf(count));//,sizeof(count));

 for(int q=0;q<count;q++)
 {
  ADBEntryData entry=(ADBEntryData)PGetPtr(List.get(q));
  if(entry==null)
  {
   sendError("WriteToBuffer","entry=NULL",null);
   return -1;
  }
  pos=entry.WriteToBuffer(buf,FInfo);
 }
 return pos;
}
//---------------------------------------------------------------------------
 public int ReadFromBuffer(ABuffer buf)
{
// SendError("ReadFromBuffer","this function don't realised");
// return -1;
 if(buf==null)
 {
  sendError("ReadFromBuffer","buf=NULL",null);
  return -1;
 }
 Clear();
 int pos=-1;
 ARefStr rstr=new ARefStr();
 pos=buf.readLStr(rstr);
 if(pos==-1)
 {
  sendError("ReadFromBuffer","IdentificationInfo not read",null);
  return -1;
 }
 if(rstr.Value.equals("ADBTable"))//getClass().getSimpleName();
 {
  sendError("ReadFromBuffer","Incorrect IdentificationInfo",null);
  return -1;
 }
 pos=FInfo.ReadFromBuffer(buf);
 if(pos==-1)
 {
  sendError("ReadFromBuffer","Info not read",null);
  return -1;
 }
 pos=FDefaultData.ReadFromBuffer(buf,FInfo);
 if(pos==-1)
 {
  sendError("ReadFromBuffer","DefaultData not read",null);
  return -1;
 }
 byte array[]=new byte[4];
 pos=buf.read(array,4);//sizeof(FPrimaryKeyField));
 FPrimaryKeyField=ABuffer.BufToInt(array);
 if(pos==-1)
 {
  sendError("ReadFromBuffer","PrimaryKeyField not read",null);
  return -1;
 }
 int count=0;
 array=new byte[4];
 pos=buf.read(array,4);//sizeof(count));
 count=ABuffer.BufToInt(array);
 if(pos==-1)
 {
  sendError("ReadFromBuffer","Count not read",null);
  return -1;
 }
 for(int q=0;q<count;q++)
 {
  ADBEntryData entry=(ADBEntryData)NewData();
  pos=entry.ReadFromBuffer(buf,FInfo);
  if(pos==-1)
  {
   DeleteData(entry);
   sendError("ReadFromBuffer","Entry["+Integer.toString(q)+"] not read",q);
   return -1;
  }
  super.PAdd(entry);
 }
 return pos;
}
//---------------------------------------------------------------------------
// __property ADBBeforInsertEvent OnBeforInsert={read=FOnBeforInsert,write=FOnBeforInsert};
// __property ADBAfterInsertEvent OnAfterInsert={read=FOnAfterInsert,write=FOnAfterInsert};
//---------------------------------------------------------------------------
// __property ADBBeforUpdateEvent OnBeforUpdate={read=FOnBeforUpdate,write=FOnBeforUpdate};
// __property ADBAfterUpdateEvent OnAfterUpdate={read=FOnAfterUpdate,write=FOnAfterUpdate};
//---------------------------------------------------------------------------
// __property ADBBeforDeleteEvent OnBeforDelete={read=FOnBeforDelete,write=FOnBeforDelete};
// __property ADBAfterDeleteEvent OnAfterDelete={read=FOnAfterDelete,write=FOnAfterDelete};
//---------------------------------------------------------------------------
 protected boolean canInsertEvent(ADBEntry entry)
 {
  ADBTableEvent e = new ADBTableEvent(this,ADBTableEvent.INSERT,entry);
  for(ADBTableEventListener listener : FCanModifyListener)listener.entryInsert(e);
  if(!e.isSuccess())return false;
  return true;
 }
//---------------------------------------------------------------------------
 protected boolean canUpdateEvent(ADBEntry entry)
 {
  ADBTableEvent e = new ADBTableEvent(this,ADBTableEvent.UPDATE,entry);
  for(ADBTableEventListener listener : FCanModifyListener)listener.entryUpdate(e);
  if(!e.isSuccess())return false;
  return true;
 }
//---------------------------------------------------------------------------
 protected boolean canDeleteEvent(ADBEntry entry)
 {
  ADBTableEvent e = new ADBTableEvent(this,ADBTableEvent.DELETE,entry);
  for(ADBTableEventListener listener : FCanModifyListener)listener.entryDelete(e);
  if(!e.isSuccess())return false;
  return true;
 }
//---------------------------------------------------------------------------
 protected void doInsertEvent(ADBEntry entry)
 {
  ADBTableEvent e = new ADBTableEvent(this,ADBTableEvent.INSERT,entry);
  for(ADBTableEventListener listener : FDoModifyListener)listener.entryInsert(e);
 }
//---------------------------------------------------------------------------
 protected void doUpdateEvent(ADBEntry entry)
 {
  ADBTableEvent e = new ADBTableEvent(this,ADBTableEvent.UPDATE,entry);
  for(ADBTableEventListener listener : FDoModifyListener)listener.entryUpdate(e);
 }
//---------------------------------------------------------------------------
 protected void doDeleteEvent(ADBEntry entry)
 {
  ADBTableEvent e = new ADBTableEvent(this,ADBTableEvent.DELETE,entry);
  for(ADBTableEventListener listener : FDoModifyListener)listener.entryDelete(e);
 }
//---------------------------------------------------------------------------
 public void addCanModifyListener(ADBTableEventListener l)
 {
  if(l==null)
  {
   sendError("addCanModifyListener","l=null",null);
   return;
  }
  FCanModifyListener.add(l);
 }
//---------------------------------------------------------------------------
 public ADBTableEventListener[] getCanModifyListeners()
 {
  return FCanModifyListener.toArray(new ADBTableEventListener[FCanModifyListener.size()]);
 }
//---------------------------------------------------------------------------
 public void removeCanModifyListener(ADBTableEventListener l)
 {
  if(l==null)
  {
   sendError("removeCanModifyListener","l=null",null);
   return;
  }
  FCanModifyListener.remove(l);
 }
//---------------------------------------------------------------------------
 public void addDoModifyListener(ADBTableEventListener l)
 {
  if(l==null)
  {
   sendError("addDoModifyListener","l=null",null);
   return;
  }
  FDoModifyListener.add(l);
 }
//---------------------------------------------------------------------------
 public ADBTableEventListener[] getDoModifyListeners()
 {
  return FDoModifyListener.toArray(new ADBTableEventListener[FDoModifyListener.size()]);
 }
//---------------------------------------------------------------------------
 public void removeDoModifyListener(ADBTableEventListener l)
 {
  if(l==null)
  {
   sendError("removeDoModifyListener","l=null",null);
   return;
  }
  FDoModifyListener.remove(l);
 }
//---------------------------------------------------------------------------
// public ~ADBTable();
//---------------------------------------------------------------------------
}
