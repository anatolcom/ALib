/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adb;

//import aclass.AClass;
import aclass.ABuffer;
import aclass.ACustomPtrArray;
import aclass.AException;
//---------------------------------------------------------------------------
//---------------------------------------------------<     C L A S S     >---
//---------------------------------------------------------------------------
/**
 *
 * @author Anatol
 */
public class ADBEntry extends ACustomPtrArray
{
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
private boolean GetDBEntryValues(String value,ADBEntry Entry)
{
/* if(value==null)
 {
  sendError("GetDBEntryValues","value=NULL",null);
  return false;
 }
 char ptr[]=value.toCharArray();
 if(Entry==null)
 {
  sendError("GetDBEntryValues","Entry=NULL",null);
  return false;
 }
 Byte len=0;
// while(true)
 for(int q=0;q<Entry.GetCount();q++)
 {
  ptr=SkipUnnecessary(ptr);
  if(ptr==null)
  {
   sendError("GetDBEntryValues","SkipUnnecessary return NULL",null);
   return false;
  }
  if(*(unsigned char*)ptr==0)
  {
   sendError("GetDBEntryValues","Statement missing "+String(IdParamEnd),null);
   return false;
  }
  if(IsSymbol(*ptr))
  {
   String word;
   ptr=GetWord(ptr,&word);
   if(ptr==null)
   {
	sendError("GetDBEntryValues","GetWord return NULL",null);
	return false;
   }
   if(!Entry.Field(q).FromStr(word))
   {
	sendError("GetDBEntryValues","Word do not converted to Field",null);
	return false;
   }
  }
  if(isDigital(*ptr))
  {
   String digital;
   ptr=GetDigital(ptr,&digital);
   if(ptr==null)
   {
	sendError("GetDBEntryValues","GetDigital return NULL",null);
	return false;
   }
   if(!Entry.Field(q).FromStr(digital))
   {
	sendError("GetDBEntryValues","Digital do not converted to Field",null);
	return false;
   }
  }
  if(IsString(ptr,len))//----------------------------------< S T R I N G >---
  {
   String str;
   ptr=GetString(ptr+len,&str);
   if(ptr==null)
   {
	sendError("GetDBEntryValues","GetString return NULL",null);
	return false;
   }
   if(!Entry.Field(q).FromStr(str))
   {
	sendError("GetDBEntryValues","Str do not converted to Field",null);
	return false;
   }
  }

  if(q==Entry.GetCount()-1)break;


  ptr=SkipUnnecessary(ptr);
  if(ptr==null)
  {
   sendError("GetDBEntryValues","SkipUnnecessary return NULL",null);
   return false;
  }
//  if(*(unsigned char*)ptr==0)
//  if(*ptr==0)
//  {
//   SendError("GetDBEntryValues","Statement missing "+String(IdParamEnd),eiImpossibleOperation);
//   return NULL;
//  }

  if(IsParamNext(ptr,len))//-----------------------< P A R A M   N E X T >---
  {
   ptr+=len;
   continue;
  }
//  if(IsParamEnd(ptr,len))//--------------------------< P A R A M   E N D >---
//  {
//   ptr+=len;
//   break;
//  }
  sendError("GetDBEntryValues","Expression syntax "+String(*ptr),null);
  return false;
 }
 return true;*/
 sendError("GetDBEntryValues","this function don't realised ",null);
 return false;
}
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
 protected Object NewData()
 {
  ADBField data=new ADBField();
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
 protected int Add(final ADBField value)
 {
  if(value.GetInfo().getName().isEmpty())
  {
   sendError("Add","Invalid Name of field",null);
   return -1;
  }
  if(Index(value.GetInfo().getName())!=-1)
  {
   sendError("Add","Name of field not unique",null);
   return -1;
  }
  ADBField data=(ADBField)NewData();
  data.assign(value);
  return super.PAdd(data);
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public ADBEntry()
 {
 }
//---------------------------------------------------------------------------
 public ADBEntry(final ADBEntry value)
 {
  Assign(value);
 }
//---------------------------------------------------------------------------
 public final void Assign(final ADBEntry value)
 {
  Clear();
  for(int q=0;q<value.PGetCount();q++)Add(new ADBField((ADBField)value.PGetPtrConst(q)));
 }
//---------------------------------------------------------------------------
 public ADBEntryInfo GetInfo()
 {
  ADBEntryInfo value=new ADBEntryInfo();
  for(int q=0;q<PGetCount();q++)
  {
   ADBField field=(ADBField)PGetPtrConst(q);
   value.Add(new ADBFieldInfo(field.GetInfo()));//???
  }
  return value;
 }
//---------------------------------------------------------------------------
// __property ADBEntryInfo Info={read=GetInfo}
//---------------------------------------------------------------------------
 public ADBEntryData GetData()
 {
  ADBEntryData value=new ADBEntryData();
  for(int q=0;q<PGetCount();q++)
  {
   ADBField field=(ADBField)PGetPtrConst(q);
   value.Add(new ADBFieldData(field.GetData()));//???
  }
  return value;
 }
//---------------------------------------------------------------------------
// __property ADBEntryData Data={read=GetData}
//---------------------------------------------------------------------------
 public boolean Set(ADBEntryInfo Info,ADBEntryData Data)
 {
  if(Info.GetCount()!=Data.GetCount())
  {
   sendError("Set","Info and Data with various count of elements",null);
   return false;
  }
  for(int q=0;q<Info.GetCount();q++)
  {
   ADBField field=new ADBField();
   if(!field.Set(Info.FieldInfo(q),Data.FieldData(q)))
   {
    Clear();
    sendError("Set","Set field error",null);
    return false;
   }
   Add(field);
//  add(ADBField(*Info.FieldInfo(q),*Data.FieldData(q)));//без проверки
  }
  return true;
 }
//---------------------------------------------------------------------------
 public boolean SetInfo(final ADBEntryInfo Info)
 {
  for(int q=0;q<Info.GetCount();q++)
  {
   ADBField field=new ADBField();
   if(!field.setInfo(Info.FieldInfo(q)))
   {
    Clear();
    sendError("SetInfo","SetInfo field error",null);
    return false;
   }
   Add(field);
//  add(ADBField(*Info.FieldInfo(q)));//без проверки
  }
  return true;
 }
//---------------------------------------------------------------------------
 public int Index(String Name)
 {
  for(int q=0;q<PGetCount();q++)
  {
   ADBField value=(ADBField)PGetPtrConst(q);
   if(value==null)
   {
    sendError("Index","value=NULL",q);
    return -1;
   }
   if(value.GetInfo().getName().equals(Name))return q;
  }
  return -1;
 }
//---------------------------------------------------------------------------
 public ADBField Field(int index)
 {
  ADBField value=(ADBField)PGetPtrConst(index);
  if(value==null)
  {
   sendError("Field","value=NULL",index);
   return null;
  }
  return value;
 }
//---------------------------------------------------------------------------
 public ADBField Field(String Name)
 {
  ADBField value=null;
  for(int q=0;q<PGetCount();q++)
  {
   value=(ADBField)PGetPtrConst(q);
   if(value==null)
   {
    sendError("Field","value=NULL",q);
    return null;
   }
   if(value.GetInfo().getName().equals(Name))return value;
  }
  sendError("Field","Field \""+Name+"\" not exists",null);
  return null;
 }
//---------------------------------------------------------------------------
 public void Clear()
 {
  for(int q=0;q<PGetCount();q++)PSetPtr(q,DeleteData(PGetPtr(q)));
  super.PClear();
 }
//---------------------------------------------------------------------------
 public int GetCount()                                                           //???
 {
  return super.PGetCount();
 }
//---------------------------------------------------------------------------
 public String ToStr(boolean Formated)//=false
 {
  String str="";
  for(int q=0;q<PGetCount();q++)
  {
   if(q!=0)str+=",";
   str+=Field(q).ToStr(Formated);
  }
  return str;
 }
//---------------------------------------------------------------------------
 public String ToStr()
 {
  return ToStr(false);
 }
//---------------------------------------------------------------------------
 public boolean FromStr(String value)
 {
  if(value.isEmpty())
  {
   sendError("FromStr","Value is empty",null);
   return false;
  }
  if(!GetDBEntryValues(value,this))
  {
   sendError("FromStr","GetDBEntryValues return error",null);
   return false;
  }
// SendError("FromStr","this function don't realised");
  return true;
 }
//---------------------------------------------------------------------------
public int WriteToBuffer(ABuffer buf) throws AException
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
  ADBField value=(ADBField)PGetPtrConst(q);
  pos=value.WriteToBuffer(buf);
 }
 return pos;
}
//---------------------------------------------------------------------------
public int ReadFromBuffer(ABuffer buf) throws AException
{
// SendError("ReadFromBuffer","this function don't realised");
// return -1;
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
  ADBField value=new ADBField();
  pos=value.ReadFromBuffer(buf);
  if(pos==-1)
  {
   sendError("ReadFromBuffer","Field not read",q);
   return -1;
  }
  Add(value);
 }
 return pos;
}
//---------------------------------------------------------------------------
}
