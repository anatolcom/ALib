/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;

import aclass.ABuffer;
import aclass.AClass;
import aclass.AException;
import aclass.aref.*;

/**
 *
 * @author Anatol
 */
public class AParserVarible extends AClass
{
//---------------------------------------------------------------------------
 public final static byte TYPE_VOID=0;      //0     statik
 public final static byte TYPE_BOOL=1;      //1     statik
 public final static byte TYPE_CHAR=2;      //1     statik     signed
 public final static byte TYPE_BYTE=3;      //1     statik   unsigned
 public final static byte TYPE_INT=7;       //4     statik     signed
 public final static byte TYPE_FLOAT=9;     //4     statik     signed
 public final static byte TYPE_STR=13;   //user dynamic
//---------------------------------------------------------------------------
 public final static int ADBFIELD_TYPE_COUNT=15;
//---------------------------------------------------------------------------
 public static int GetDBFieldTypeConstSize(byte Type) throws AException         //???
 {
  switch(Type)
  {
   case TYPE_VOID:      return 0;               //0     statik
   case TYPE_BOOL:      return 1;               //1     statik
   case TYPE_CHAR:      return 1;               //1     statik     signed
   case TYPE_BYTE:      return 1;               //1     statik   unsigned
   case TYPE_INT:       return 4;               //4     statik     signed
   case TYPE_FLOAT:     return 4;               //4     statik     signed
   case TYPE_STR:       return 2;               //user dynamic                  //???
  }
  throw new AException("Unknow Type",new Integer(Type));
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeUserSize(byte Type)
 {
  switch(Type)
  {
   case TYPE_STR:    return true; //user dynamic
  }
  return false;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeStatic(byte Type)
 {
  switch(Type)
  {
   case TYPE_STR:    return false; //user dynamic
  }
  return true;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeComparable(byte Type)
 {
  switch(Type)
  {
   case TYPE_VOID:      return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeIndexable(byte Type)
 {
  switch(Type)
  {
   case TYPE_VOID:      return false;
   case TYPE_BOOL:      return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeLogic(byte Type)
 {
  switch(Type)
  {
   case TYPE_BOOL:
    return true;
  }
  return false;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeDigital(byte Type)
 {
  switch(Type)
  {
   case TYPE_BOOL:    return true;
   case TYPE_CHAR:    return true;
   case TYPE_BYTE:    return true;
   case TYPE_INT:     return true;
   case TYPE_FLOAT:   return true;
  }
  return false;
 }
//---------------------------------------------------------------------------
 public static String DBFieldTypeToStr(byte Type)
 {
  switch(Type)
  {
   case TYPE_VOID:     return "Void";
   case TYPE_BOOL:     return "Bool";
   case TYPE_CHAR:     return "Char";
   case TYPE_BYTE:     return "Byte";
   case TYPE_INT:      return "Int";
   case TYPE_FLOAT:    return "Float";
   case TYPE_STR:   return "String";
  }
  AClass.sendGlobalError("DBFieldTypeToStr","Unknow Type",new Integer(Type));
  return "";
 }
//---------------------------------------------------------------------------
 public static byte DBFieldTypeFromStr(String value)
 {
  if("Void".equals(value))return TYPE_VOID;
  if("Bool".equals(value))return TYPE_BOOL;
  if("Char".equals(value))return TYPE_CHAR;
  if("Byte".equals(value))return TYPE_BYTE;
  if("Int".equals(value))return TYPE_INT;
  if("Float".equals(value))return TYPE_FLOAT;
  if("String".equals(value))return TYPE_STR;
  AClass.sendGlobalError("DBFieldTypeFromStr","Unknow Type",null);
  return -1;
 }
//---------------------------------------------------------------------------
 /**
  * Метод определяет, является ли value символом 'A'-'Z','a'-'z','_'.<br/>
  * @param VALUE - Значение.
  * @return true - да,<br/>
  *         false - нет.
  */
 public static boolean isSymbol(final char VALUE)                       //!!!
 {
  if(((VALUE>='A')&(VALUE<='Z'))
    |((VALUE>='a')&(VALUE<='z'))
     |(VALUE=='_'))return true;
  return false;
 }
//---------------------------------------------------------------------------
 /**
  * Метод определяет, является ли value цифрой '0'-'9'.<br/>
  * @param VALUE Значение.
  * @return true - да,<br/>
  *         false - нет.
  */
 public static boolean isDigital(final char VALUE)                      //!!!
 {
  if(((VALUE>='0')&(VALUE<='9')))return true;
  return false;
 }
//---------------------------------------------------------------------------
 public static boolean ValidationName(final String Name)
 {
  if(Name.isEmpty())
  {
   AClass.sendGlobalWarning("ValidationName","Name is empty",null);
   return false;
  }
  char b[]=Name.toCharArray();
  if(!isSymbol(b[0])) return false;
  int pos=0;
  while(pos<b.length)//??? b[q]>=32
  {
   final char CHAR=b[pos];
   if(CHAR<32)break;
   if(isSymbol(CHAR)||isDigital(CHAR))pos++;
   else return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public final boolean TryStrToBool(String Str,ARefBool Ref)
 {
  if(Str.equalsIgnoreCase("true"))
  {
   Ref.value=true;
   return true;
  }
  if(Str.equalsIgnoreCase("false"))
  {
   Ref.value=false;
   return true;
  }
  Ref.value=false;
  return false;
 }
//---------------------------------------------------------------------------
/* public final boolean TryStrToDateTime(String Str,ADateTime Ref)
 {

  return true;
 }*/
//---------------------------------------------------------------------------
 public static boolean ConformInfoAndData(final AParserValueType Info,final AParserValue Data)
 {
  if(!Info.isStatic())return true;
  if((!Info.NotNULL)&&(Data.getSize()==0))return true;
  if(Info.getStaticSize()!=Data.getSize())return false;
  return true;
 }
//---------------------------------------------------------------------------
 public static char CompareDBField(final AParserValue Data1,final AParserValueType Info1,
         final AParserValue Data2,final AParserValueType Info2)throws AException
 {
  if(Info1.getType()!=Info2.getType())throw new AException("Type mismatch");
  if(Info1.isStatic())
  {
   if(Data1.getSize()!=Info1.getStaticSize())throw new AException("Incorrect size of Data1");
   if(Data2.getSize()!=Info2.getStaticSize())throw new AException("Incorrect size of Data2");
  }
  switch(Info1.getType())
  {
   case TYPE_VOID: 
    throw new AException("Void can not by comparable");
   case TYPE_BOOL:
    if(ABuffer.BufToBool(Data1.getPtr())==ABuffer.BufToBool(Data2.getPtr()))return '=';
    if(ABuffer.BufToByte(Data1.getPtr())>ABuffer.BufToByte(Data2.getPtr()))return '>';
    else return '<';
   case TYPE_CHAR:
    throw new AException("Function for comparable Char can not by realised");
   case TYPE_BYTE:
    if(ABuffer.BufToByte(Data1.getPtr())==ABuffer.BufToByte(Data2.getPtr()))return '=';
    if(ABuffer.BufToByte(Data1.getPtr())>ABuffer.BufToByte(Data2.getPtr()))return '>';
    else return '<';
   case TYPE_INT:
    if(ABuffer.BufToInt(Data1.getPtr())==ABuffer.BufToInt(Data2.getPtr()))return '=';
    if(ABuffer.BufToInt(Data1.getPtr())>ABuffer.BufToInt(Data2.getPtr()))return '>';
    else return '<';
   case TYPE_FLOAT:
    if(ABuffer.BufToFloat(Data1.getPtr())==ABuffer.BufToFloat(Data2.getPtr()))return '=';
    if(ABuffer.BufToFloat(Data1.getPtr())>ABuffer.BufToFloat(Data2.getPtr()))return '>';
    else return '<';
   case TYPE_STR:
    return CompareDBFieldAsString(Data1,Info1,Data2,Info2,false);
  }
  throw new AException("Unknow Type",(int)Info1.getType());
 }
//---------------------------------------------------------------------------
 public static char CompareDBFieldAsVarChar(final AParserValue Data1,final AParserValueType Info1,
         final AParserValue Data2,final AParserValueType Info2,boolean Reg) throws AException
 {
  if(Data1.getSize()!=Info1.getStaticSize())throw new AException("Incorrect size of Data1");
  if(Data2.getSize()!=Info2.getStaticSize())throw new AException("Incorrect size of Data2");
  if(Info1.getLength()!=Info2.getLength())throw new AException("VarChar with various length can not by comparable");
  int len=Info1.getLength();
  String str1=ABuffer.BufToStr(Data1.getPtr());
  String str2=ABuffer.BufToStr(Data2.getPtr());
  int cmp;
  if(Reg)cmp=str1.compareTo(str2);
  else cmp=str1.compareToIgnoreCase(str2);
  if(cmp<0)return '<';
  if(cmp>0)return '>';
  return '=';
 }
//---------------------------------------------------------------------------
 public static char CompareDBFieldAsString(final AParserValue Data1,final AParserValueType Info1,
         final AParserValue Data2,final AParserValueType Info2,boolean Reg) throws AException
 {
  if(Info1.getType()!=TYPE_STR)throw new AException("Type of Info1 mismatch");
  if(Info2.getType()!=TYPE_STR)throw new AException("Type of Info2 mismatch");
  String str1=ABuffer.BufToStr(Data1.getPtr());
  String str2=ABuffer.BufToStr(Data2.getPtr());
  int cmp;
  if(Reg)cmp=str1.compareTo(str2);
  else cmp=str1.compareToIgnoreCase(str2);
  if(cmp<0)return '<';
  if(cmp>0)return '>';
  return '=';
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<     C L A S S     >---
//---------------------------------------------------------------------------

//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
 private AParserValueType FInfo;
//---------------------------------------------------------------------------
 private AParserValue FData;
//---------------------------------------------------------------------------
 private void initialize()
 {
  FInfo=new AParserValueType();
  FData=new AParserValue();
  FInfo.setParent(this);
  FData.setParent(this);
 }
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
 
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public AParserVarible(){initialize();}
//---------------------------------------------------------------------------
 public AParserVarible(final AParserValueType Info,final AParserValue Data) throws AException
 {
  initialize();
  if(!ConformInfoAndData(Info,Data))
  {
   sendError("ADBField","Info and Data don't conformed",null);
   return;
  }
  FInfo.assign(Info);
  FData.assign(Data);
 }
//---------------------------------------------------------------------------
 public AParserVarible(final AParserValueType value) throws AException
 {
  initialize();
  FInfo.assign(value);
  if(FInfo.getType()==TYPE_VOID)return;
  if(FInfo.NotNULL)InitializeData();
 }
//---------------------------------------------------------------------------
 public AParserVarible(final AParserVarible value) throws AException
 {
  initialize();
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void InitializeData() throws AException
 {
  if(!getIsNULL())throw new AException("Alredy initialize");
  if(!FInfo.isStatic())return;
  FData.resize(FInfo.getStaticSize());
 }
//---------------------------------------------------------------------------
 public final void assign(final AParserVarible value) throws AException
 {
  FInfo.assign(value.FInfo);
  FData.assign(value.FData);
 }
//---------------------------------------------------------------------------
 public boolean Set(final AParserValueType Info,final AParserValue Data) throws AException
 {
  if(!ConformInfoAndData(Info,Data))
  {
   sendError("Set","Info and Data don't conformed",null);
   sendInfo("Set","InfoSize="+Info.getStaticSize()+"  DataSize="+Data.getSize(),null);
   return false;
  }
  FData.assign(Data);
  FInfo.assign(Info);
  return true;
 }
//---------------------------------------------------------------------------
 public void setInfo(final AParserValueType value) throws AException
 {
  FData.clear();
  FInfo.assign(value);
  if(FInfo.getType()==TYPE_VOID)return;
  if(FInfo.NotNULL)InitializeData();
 }
//---------------------------------------------------------------------------
 public int getSize()
 {
  if(FInfo.isStatic())return FInfo.getStaticSize();
  return FData.getSize();
 }
//---------------------------------------------------------------------------
 public int getLength() throws AException
 {
  if(FInfo.isStatic())return FInfo.getLength();
  return FData.getSize()/FInfo.getConstSize();
 }
//---------------------------------------------------------------------------
 public boolean getIsNULL()
 {
  if(FData.getSize()>0)return false;   //?????????????????????????????????
  return true;
 }
//---------------------------------------------------------------------------
 public void SetIsNULL(boolean value) throws AException
 {
  if(value)
  {
   if(FInfo.NotNULL)throw new AException("NotNULL");
   FData.clear();
   return;
  }
  if(getIsNULL())InitializeData();
 }
//---------------------------------------------------------------------------
 public boolean GetAsBool() throws AException
 {
  if(FInfo.getType()!=TYPE_BOOL)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToBool(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsBool(boolean value) throws AException
 {
  if(FInfo.getType()!=TYPE_BOOL)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.BoolToBuf(value));
 }
//---------------------------------------------------------------------------
 public char GetAsChar() throws AException                              //???
 {
  if(FInfo.getType()!=TYPE_CHAR)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToChar(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsChar(char value) throws AException                    //???
 {
  if(FInfo.getType()!=TYPE_CHAR)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.CharToBuf(value));
 }
//---------------------------------------------------------------------------
 public Byte GetAsByte() throws AException
 {
  if(FInfo.getType()!=TYPE_BYTE)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToByte(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsByte(byte value) throws AException                            //???? Byte_C++ no Byte_Java
 {
  if(FInfo.getType()!=TYPE_BYTE)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.ByteToBuf(value));
 }
//---------------------------------------------------------------------------
 public int GetAsInt() throws AException
 {
  if(FInfo.getType()!=TYPE_INT)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToInt(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsInt(int value) throws AException
 {
  if(FInfo.getType()!=TYPE_INT)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.IntToBuf(value));
 }
//---------------------------------------------------------------------------
 public float GetAsFloat() throws AException
 {
  if(FInfo.getType()!=TYPE_FLOAT)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToFloat(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsFloat(float value) throws AException
 {
  if(FInfo.getType()!=TYPE_FLOAT)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.FloatToBuf(value));
 }
//---------------------------------------------------------------------------
 public String GetAsString() throws AException
 {
  if(FInfo.getType()!=TYPE_STR)throw new AException("Type mismatch");
  return ABuffer.BufToStr(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsString(String value) throws AException
 {
  if(FInfo.getType()!=TYPE_STR)throw new AException("Type mismatch");
  int len=value.length();
  FData.resize(len*2);
  byte array[]=ABuffer.StrToBuf(value);
  FData.setPtr(array);
 }
//---------------------------------------------------------------------------
 public AParserValueType GetInfo() throws AException
 {
  return new AParserValueType(FInfo);
 }
//---------------------------------------------------------------------------
 public AParserValue GetData() throws AException
 {
  return new AParserValue(FData);
 }
//---------------------------------------------------------------------------
 public char Compare(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  return CompareDBField(FData,FInfo,value.FData,value.FInfo);
 }
//---------------------------------------------------------------------------
 public char CompareAsString(final AParserVarible value,boolean Reg) throws AException
 {
  if(FInfo.getType()!=TYPE_STR)throw new AException("Type mismatch");
  return CompareDBFieldAsString(FData,FInfo,value.FData,value.FInfo,Reg);
 }
//---------------------------------------------------------------------------
 public char CompareAsString(final AParserVarible value) throws AException
 {
  return CompareAsString(value,false);
 }
//---------------------------------------------------------------------------
 public String ToStr(boolean Formated) throws AException//=false
 {
  switch(FInfo.getType())
  {
   case TYPE_VOID:
    sendError("ToStr","Void can not convert to str",null);
    return "";
   case TYPE_STR:
    if(Formated)return "\""+GetAsString()+"\"";
    return GetAsString();
  }
  if(getIsNULL())return "NULL";
  switch(FInfo.getType())
  {
   case TYPE_BOOL:
    if(ABuffer.BufToBool(FData.getPtr()))return "true";
    else return "false";
   case TYPE_CHAR:
    if(Formated)return "'"+Character.toString(ABuffer.BufToChar(FData.getPtr()))+"'";
    return Character.toString(ABuffer.BufToChar(FData.getPtr()));
   case TYPE_BYTE:
    return Byte.toString(ABuffer.BufToByte(FData.getPtr()));
   case TYPE_INT:
    return Integer.toString(ABuffer.BufToInt(FData.getPtr()));
   case TYPE_FLOAT:
    return Float.toString(ABuffer.BufToFloat(FData.getPtr()));
   default:
    throw new AException("Unknow Type",(int)FInfo.getType());
  }
 }
//---------------------------------------------------------------------------
 public void FromStr(String value) throws AException
 {
  if(value.isEmpty()&&(FInfo.getType()!=TYPE_STR))
  {
   throw new AException("Value is empty");
  }

  switch(FInfo.getType())
  {
   case TYPE_VOID:
    throw new AException("Void can not convert from str");
  }

  if(value.equals("NULL"))
  {
   if(FInfo.NotNULL)throw new AException("NotNULL value can not by NULL");
   FData.clear();
   return;
  }

  if(getIsNULL())InitializeData();

  if(FInfo.getType()==TYPE_BOOL)
  {
   ARefBool ref=new ARefBool();
   if(!TryStrToBool(value,ref))throw new AException("Value do not converted to bool");
   FData.setPtr(ABuffer.BoolToBuf(ref.value));//*(bool*)FData.Ptr=data;
   return;
  }

  switch(FInfo.getType())
  {
   case TYPE_CHAR:
    throw new AException("this function don't realised for dbftChar");
   case TYPE_STR:
    SetAsString(value);
    return;
  }

  if(FInfo.getType()==TYPE_BYTE)
  {
   try
   {
    FData.setPtr(ABuffer.ByteToBuf(Byte.parseByte(value)));//*(Byte*)FData.Ptr=data;
    return;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to byte",ex);}
  }

  if(FInfo.getType()==TYPE_INT)
  {
   try
   {
    FData.setPtr(ABuffer.IntToBuf(Integer.parseInt(value)));
    return;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to int",ex);}
  }

  if(FInfo.getType()==TYPE_FLOAT)
  {
   try
   {
    FData.setPtr(ABuffer.FloatToBuf(Float.parseFloat(value)));//*(float*)FData.Ptr=data;
    return;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to float",ex);}
  }

  throw new AException("Unknow Type,(int)FInfo.getType()");
 }
//---------------------------------------------------------------------------
 public int WriteToBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  FInfo.WriteToBuffer(buf);
  FData.WriteToBuffer(buf,FInfo);
  return buf.getPos();
 }
//---------------------------------------------------------------------------
 public int ReadFromBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  FData.clear();
  FInfo.ReadFromBuffer(buf);
  FData.ReadFromBuffer(buf,FInfo);
  return buf.getPos();
 }
//---------------------------------------------------------------------------
 public void AssignValue(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  FData.assign(value.FData);
 }
//---------------------------------------------------------------------------
 public boolean isEqual(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator!=(final AParserVarible value)
 public boolean isInequal(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)!='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator>(final AParserVarible value)
 public boolean isMore(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='>')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator>=(final AParserVarible value)
 public boolean isMoreAndEqual(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='>')return true;
  if(Compare(value)=='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator<(final AParserVarible value)
 public boolean isLess(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='<')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator<=(final AParserVarible value)
 public boolean isLessAndEqual(final AParserVarible value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='<')return true;
  if(Compare(value)=='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
}
