/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adb;

import aclass.AClass;
import aclass.ABuffer;
import aclass.AException;
import aclass.adatetime.ADateTime;
import aclass.aref.*;

/**
 *
 * @author Anatol
 */
public class ADBField extends AClass
{
//---------------------------------------------------------------------------
 public final static byte dbftVoid=0;      //0     statik
 public final static byte dbftBool=1;      //1     statik
 public final static byte dbftChar=2;      //1     statik     signed
 public final static byte dbftByte=3;      //1     statik   unsigned
 public final static byte dbftWChar=4;     //2     static     signed
 public final static byte dbftShort=5;     //2     statik     signed
 public final static byte dbftWord=6;      //2     statik   unsigned
 public final static byte dbftInt=7;       //4     statik     signed
 public final static byte dbftUint=8;      //4     statik   unsigned
 public final static byte dbftFloat=9;     //4     statik     signed
 public final static byte dbftDouble=10;   //8     statik     signed
 public final static byte dbftDateTime=11; //8     statik
 public final static byte dbftVarChar=12;  //user  statik
 public final static byte dbftString=13;   //user dynamic
 public final static byte dbftBuffer=14;   //user dynamic
//---------------------------------------------------------------------------
 public final static int ADBFIELD_TYPE_COUNT=15;
//---------------------------------------------------------------------------
 public static int GetDBFieldTypeConstSize(byte Type)                            //???
 {
  switch(Type)
  {
   case dbftVoid:      return 0;               //0     statik
   case dbftBool:      return 1;               //1     statik
   case dbftChar:      return 1;               //1     statik     signed
   case dbftByte:      return 1;               //1     statik   unsigned
   case dbftWChar:     return 2;               //2     statik     signed
   case dbftShort:     return 2;               //2     statik     signed
   case dbftWord:      return 2;               //2     statik   unsigned
   case dbftInt:       return 4;               //4     statik     signed
   case dbftUint:      return 4;               //4     statik   unsigned
   case dbftFloat:     return 4;               //4     statik     signed
   case dbftDouble:    return 8;               //8     statik     signed
   case dbftDateTime:  return 8;               //8     statik                    //???
   case dbftVarChar:   return 2;               //user  statik                    //???
   case dbftString:    return 2;               //user dynamic                    //???
   case dbftBuffer:    return 1;               //user dynamic
  }
  AClass.sendGlobalError("GetDBFieldTypeConstSize","Unknow Type",new Integer(Type));
  return -1;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeUserSize(byte Type)
 {
  switch(Type)
  {
   case dbftVarChar:   return true; //user  statik
   case dbftString:    return true; //user dynamic
   case dbftBuffer:    return true; //user dynamic
  }
  return false;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeStatic(byte Type)
 {
  switch(Type)
  {
   case dbftString:    return false; //user dynamic
   case dbftBuffer:    return false; //user dynamic
  }
  return true;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeComparable(byte Type)
 {
  switch(Type)
  {
   case dbftVoid:      return false;
   case dbftBuffer:    return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeIndexable(byte Type)
 {
  switch(Type)
  {
   case dbftVoid:      return false;
   case dbftBool:      return false;
   case dbftDateTime:  return false; //???
   case dbftBuffer:    return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeLogic(byte Type)
 {
  switch(Type)
  {
   case dbftBool:
    return true;
  }
  return false;
 }
//---------------------------------------------------------------------------
 public static boolean IsDBFieldTypeDigital(byte Type)
 {
  switch(Type)
  {
   case dbftBool:    return true;
   case dbftChar:    return true;
   case dbftByte:    return true;
   case dbftWChar:   return true;
   case dbftShort:   return true;
   case dbftWord:    return true;
   case dbftInt:     return true;
   case dbftUint:    return true;
   case dbftFloat:   return true;
   case dbftDouble:  return true;
  }
  return false;
 }
//---------------------------------------------------------------------------
 public static String DBFieldTypeToStr(byte Type)
 {
  switch(Type)
  {
   case dbftVoid:     return "Void";
   case dbftBool:     return "Bool";
   case dbftChar:     return "Char";
   case dbftByte:     return "Byte";
   case dbftWChar:    return "WChar";
   case dbftShort:    return "Short";
   case dbftWord:     return "Word";
   case dbftInt:      return "Int";
   case dbftUint:     return "Uint";
   case dbftFloat:    return "Float";
   case dbftDouble:   return "Double";
   case dbftDateTime: return "DateTime";
   case dbftVarChar:  return "VarChar";
   case dbftString:   return "String";
   case dbftBuffer:   return "Buffer";
  }
  AClass.sendGlobalError("DBFieldTypeToStr","Unknow Type",new Integer(Type));
  return "";
 }
//---------------------------------------------------------------------------
 public static byte DBFieldTypeFromStr(String value)
 {
  if("Void".equals(value))return dbftVoid;
  if("Bool".equals(value))return dbftBool;
  if("Char".equals(value))return dbftChar;
  if("Byte".equals(value))return dbftByte;
  if("WChar".equals(value))return dbftWChar;
  if("Short".equals(value))return dbftShort;
  if("Word".equals(value))return dbftWord;
  if("Int".equals(value))return dbftInt;
  if("Uint".equals(value))return dbftUint;
  if("Float".equals(value))return dbftFloat;
  if("Double".equals(value))return dbftDouble;
  if("DateTime".equals(value))return dbftDateTime;
  if("VarChar".equals(value))return dbftVarChar;
  if("String".equals(value))return dbftString;
  if("Buffer".equals(value))return dbftBuffer;
  AClass.sendGlobalError("DBFieldTypeFromStr","Unknow Type",null);
  return -1;
 }
//---------------------------------------------------------------------------
 /**
  * Метод определяет, является ли value символом 'A'-'Z','a'-'z','_','-'.<br/>
  * @param VALUE - Значение.
  * @return true - да,<br/>
  *         false - нет.
  */
 public static boolean isSymbol(final char VALUE)                       //!!!
 {
  if(((VALUE>='A')&(VALUE<='Z'))
    |((VALUE>='a')&(VALUE<='z'))
     |(VALUE=='_')
     |(VALUE=='-'))return true;
  return false;
 }
//---------------------------------------------------------------------------
 /**
  * Метод определяет, является ли value цифрой '0'-'9'.<br/>
  * @param VALUE - Значение.
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
 public static boolean ConformInfoAndData(final ADBFieldInfo Info,final ADBFieldData Data)
 {
  if(!Info.isStatic())return true;
  if((!Info.NotNULL)&&(Data.getSize()==0))return true;
  if(Info.getStaticSize()!=Data.getSize())return false;
  return true;
 }
//---------------------------------------------------------------------------
 public static char CompareDBField(final ADBFieldData Data1,final ADBFieldInfo Info1,
         final ADBFieldData Data2,final ADBFieldInfo Info2)
 {
  if(Info1.getType()!=Info2.getType())
  {
   AClass.sendGlobalError("CompareDBField","Type mismatch",null);
   return 'e';
  }
  if(Info1.isStatic())
  {
   if(Data1.getSize()!=Info1.getStaticSize())
   {
    AClass.sendGlobalError("CompareDBField","Incorrect size of Data1",null);
    return 'e';
   }
   if(Data2.getSize()!=Info2.getStaticSize())
   {
    AClass.sendGlobalError("CompareDBField","Incorrect size of Data2",null);
    return 'e';
   }
  }
  switch(Info1.getType())
  {
   case dbftVoid:
    AClass.sendGlobalError("CompareDBField","Void can not by comparable",null);
    return 'e';
   case dbftBool:
    if(ABuffer.BufToBool(Data1.getPtr())==ABuffer.BufToBool(Data2.getPtr()))return '=';
    if(ABuffer.BufToByte(Data1.getPtr())>ABuffer.BufToByte(Data2.getPtr()))return '>';
    else return '<';
   case dbftChar:
//    if(ABuffer.BufToChar(Data1.getPtr())==ABuffer.BufToChar(Data2.getPtr()))return '=';//???
//    if(ABuffer.BufToChar(Data1.getPtr())>ABuffer.BufToChar(Data2.getPtr()))return '>';//???
//    else return '<';
    AClass.sendGlobalError("CompareDBField","Function for comparable Char can not by realised",null);
    return 'e';
   case dbftByte:
    if(ABuffer.BufToByte(Data1.getPtr())==ABuffer.BufToByte(Data2.getPtr()))return '=';
    if(ABuffer.BufToByte(Data1.getPtr())>ABuffer.BufToByte(Data2.getPtr()))return '>';
    else return '<';
   case dbftWChar:
    if(ABuffer.BufToChar(Data1.getPtr())==ABuffer.BufToChar(Data2.getPtr()))return '=';
    if(ABuffer.BufToChar(Data1.getPtr())>ABuffer.BufToChar(Data2.getPtr()))return '>';
    else return '<';
   case dbftShort:
    if(ABuffer.BufToShort(Data1.getPtr())==ABuffer.BufToShort(Data2.getPtr()))return '=';
    if(ABuffer.BufToShort(Data1.getPtr())>ABuffer.BufToShort(Data2.getPtr()))return '>';
    else return '<';
   case dbftWord:
    if(ABuffer.BufToShort(Data1.getPtr())==ABuffer.BufToShort(Data2.getPtr()))return '=';
    if(ABuffer.BufToShort(Data1.getPtr())>ABuffer.BufToShort(Data2.getPtr()))return '>';
    else return '<';
   case dbftInt:
    if(ABuffer.BufToInt(Data1.getPtr())==ABuffer.BufToInt(Data2.getPtr()))return '=';
    if(ABuffer.BufToInt(Data1.getPtr())>ABuffer.BufToInt(Data2.getPtr()))return '>';
    else return '<';
   case dbftUint:
    if(ABuffer.BufToInt(Data1.getPtr())==ABuffer.BufToInt(Data2.getPtr()))return '=';
    if(ABuffer.BufToInt(Data1.getPtr())>ABuffer.BufToInt(Data2.getPtr()))return '>';
    else return '<';
   case dbftFloat:
    if(ABuffer.BufToFloat(Data1.getPtr())==ABuffer.BufToFloat(Data2.getPtr()))return '=';
    if(ABuffer.BufToFloat(Data1.getPtr())>ABuffer.BufToFloat(Data2.getPtr()))return '>';
    else return '<';
   case dbftDouble:
    if(ABuffer.BufToDouble(Data1.getPtr())==ABuffer.BufToDouble(Data2.getPtr()))return '=';
    if(ABuffer.BufToDouble(Data1.getPtr())>ABuffer.BufToDouble(Data2.getPtr()))return '>';
    else return '<';
   case dbftDateTime:
    if(ABuffer.BufToDouble(Data1.getPtr())==ABuffer.BufToDouble(Data2.getPtr()))return '=';//???
    if(ABuffer.BufToDouble(Data1.getPtr())>ABuffer.BufToDouble(Data2.getPtr()))return '>';//???
    else return '<';
   case dbftVarChar:
    return CompareDBFieldAsVarChar(Data1,Info1,Data2,Info2,false);
   case dbftString:
    return CompareDBFieldAsString(Data1,Info1,Data2,Info2,false);
   case dbftBuffer:
    AClass.sendGlobalError("CompareDBField","Buffer can not by comparable",null);
    return 'e';
//   default:
//    sendError("CompareDBField","Unknow Type",(int)Info1.getType());
//    return 'e';
  }
  AClass.sendGlobalError("CompareDBField","Unknow Type",(int)Info1.getType());
  return 'e';
 }
//---------------------------------------------------------------------------
 public static char CompareDBFieldAsVarChar(final ADBFieldData Data1,final ADBFieldInfo Info1,
         final ADBFieldData Data2,final ADBFieldInfo Info2,boolean Reg)
 {
  if(Info1.getType()!=dbftVarChar)
  {
   AClass.sendGlobalError("CompareDBFieldAsVarChar","Type of Info1 mismatch",null);
   return 'e';
  }
  if(Info2.getType()!=dbftVarChar)
  {
   AClass.sendGlobalError("CompareDBFieldAsVarChar","Type of Info2 mismatch",null);
   return 'e';
  }
  if(Data1.getSize()!=Info1.getStaticSize())
  {
   AClass.sendGlobalError("CompareDBFieldAsVarChar","Incorrect size of Data1",null);
   return 'e';
  }
  if(Data2.getSize()!=Info2.getStaticSize())
  {
   AClass.sendGlobalError("CompareDBFieldAsVarChar","Incorrect size of Data2",null);
   return 'e';
  }
  if(Info1.GetLength()!=Info2.GetLength())
  {
   AClass.sendGlobalError("CompareDBFieldAsVarChar","VarChar with various length can not by comparable",null);
   return 'e';
  }
  int len=Info1.GetLength();
  /*for(int q=0;q<len;q++)
  {
   char c1=*(Data1.Ptr+q);
   char c2=*(Data2.Ptr+q);
   if(!Reg)
   {
    c1=Character.toLowerCase(c1);//c1=*strlwr(&c1);
    c2=Character.toLowerCase(c2);//c2=*strlwr(&c2);
   }
   if(c1==c2)
    continue;
   if(c1>c2)
    return '>';
   else
    return '<';
  }
  return '=';*/
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
 public static char CompareDBFieldAsString(final ADBFieldData Data1,final ADBFieldInfo Info1,
         final ADBFieldData Data2,final ADBFieldInfo Info2,boolean Reg)
 {
  if(Info1.getType()!=dbftString)
  {
   AClass.sendGlobalError("CompareDBFieldAsString","Type of Info1 mismatch",null);
   return 'e';
  }
  if(Info2.getType()!=dbftString)
  {
   AClass.sendGlobalError("CompareDBFieldAsString","Type of Info2 mismatch",null);
   return 'e';
  }
  /*int len1=Data1.getSize();
  String str1="";
  str1=ABuffer.BufToStr(Data1.getPtr());//for(int q=0;q<len1;q++){str1+=(char)*(Data1.Ptr+q);}
  int len2=Data2.getSize();
  String str2="";
  str2=ABuffer.BufToStr(Data2.getPtr());//for(int q=0;q<len2;q++){str2+=(char)*(Data2.Ptr+q);}
  if(!Reg)
  {
   str1=str1.toLowerCase();
   str2=str2.toLowerCase();
  }
  if(str1.equals(str2))
   return '=';
  if(str1.compareTo(str2)>0)// >str2)                                           //???
   return '>';
  else
   return '<';*/
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
// class ADBField:public AClass
//  {
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
 private ADBFieldInfo FInfo;
//---------------------------------------------------------------------------
 private ADBFieldData FData;
//---------------------------------------------------------------------------
 private void initialize()
 {
  FInfo=new ADBFieldInfo();
  FData=new ADBFieldData();
  FInfo.setParent(this);
  FData.setParent(this);
 }
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
// System::Currency __fastcall GetAsCurrency(void);
// void __fastcall SetAsCurrency(System::Currency Value);
// Sqltimst::TSQLTimeStamp __fastcall GetAsSqlTimeStamp();
// void __fastcall SetAsSQLTimeStamp(const Sqltimst::TSQLTimeStamp &Value);
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public ADBField(){initialize();}
//---------------------------------------------------------------------------
 public ADBField(final ADBFieldInfo Info,final ADBFieldData Data) throws AException
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
 public ADBField(final ADBFieldInfo value) throws AException
 {
  initialize();
  FInfo.assign(value);
  if(FInfo.getType()==dbftVoid)return;
  if(FInfo.NotNULL)InitializeData();
 }
//---------------------------------------------------------------------------
 public ADBField(final ADBField value) throws AException
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
 public final void assign(final ADBField value) throws AException
 {
  FInfo.assign(value.FInfo);
  FData.assign(value.FData);
 }
//---------------------------------------------------------------------------
 public boolean Set(final ADBFieldInfo Info,final ADBFieldData Data) throws AException
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
 public void setInfo(final ADBFieldInfo value) throws AException
 {
  FData.clear();
  FInfo.assign(value);
  if(FInfo.getType()==dbftVoid)return;
  if(FInfo.NotNULL)InitializeData();
 }
//---------------------------------------------------------------------------
 public int getSize()
 {
  if(FInfo.isStatic())return FInfo.getStaticSize();
  return FData.getSize();
 }
//---------------------------------------------------------------------------
 public int getLength()
 {
  if(FInfo.isStatic())return FInfo.GetLength();
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
  if(FInfo.getType()!=dbftBool)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToBool(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsBool(boolean value) throws AException
 {
  if(FInfo.getType()!=dbftBool)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.BoolToBuf(value));
 }
//---------------------------------------------------------------------------
 public char GetAsChar() throws AException                              //???
 {
  if(FInfo.getType()!=dbftChar)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToChar(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsChar(char value) throws AException                    //???
 {
  if(FInfo.getType()!=dbftChar)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.CharToBuf(value));
 }
//---------------------------------------------------------------------------
 public char GetAsWChar() throws AException
 {
  if(FInfo.getType()!=dbftWChar)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToChar(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsWChar(char value) throws AException
 {
  if(FInfo.getType()!=dbftWChar)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.CharToBuf(value));
 }
//---------------------------------------------------------------------------
 public Byte GetAsByte() throws AException
 {
  if(FInfo.getType()!=dbftByte)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToByte(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsByte(byte value) throws AException                            //???? Byte_C++ no Byte_Java
 {
  if(FInfo.getType()!=dbftByte)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.ByteToBuf(value));
 }
//---------------------------------------------------------------------------
 public int GetAsInt() throws AException
 {
  if(FInfo.getType()!=dbftInt)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToInt(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsInt(int value) throws AException
 {
  if(FInfo.getType()!=dbftInt)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.IntToBuf(value));
 }
//---------------------------------------------------------------------------
 public short GetAsShort() throws AException
 {
  if(FInfo.getType()!=dbftShort)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToShort(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsShort(short value) throws AException
 {
  if(FInfo.getType()!=dbftShort)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.ShortToBuf(value));
 }
//---------------------------------------------------------------------------
 public short GetAsWord() throws AException                             //???
 {
  if(FInfo.getType()!=dbftWord)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToShort(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsWord(short value) throws AException                   //???
 {
  if(FInfo.getType()!=dbftWord)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.ShortToBuf(value));
 }
//---------------------------------------------------------------------------
 public int GetAsUint() throws AException                               //???
 {
  if(FInfo.getType()!=dbftUint)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToInt(FData.getPtr());//*(UINT*)FData.Ptr;
 }
//---------------------------------------------------------------------------
 public void SetAsUint(int value) throws AException                     //???
 {
  if(FInfo.getType()!=dbftUint)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.IntToBuf(value));
 }
//---------------------------------------------------------------------------
 public float GetAsFloat() throws AException
 {
  if(FInfo.getType()!=dbftFloat)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToFloat(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsFloat(float value) throws AException
 {
  if(FInfo.getType()!=dbftFloat)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.FloatToBuf(value));
 }
//---------------------------------------------------------------------------
 public double GetAsDouble() throws AException
 {
  if(FInfo.getType()!=dbftDouble)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  return ABuffer.BufToDouble(FData.getPtr());//*(double*)FData.Ptr;
 }
//---------------------------------------------------------------------------
 public void SetAsDouble(double value) throws AException
 {
  if(FInfo.getType()!=dbftDouble)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.DoubleToBuf(value));
 }
//---------------------------------------------------------------------------
 public ADateTime GetAsDateTime() throws AException
 {
  if(FInfo.getType()!=dbftDateTime)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  ADateTime value=new ADateTime();
  value.setDouble(ABuffer.BufToDouble(FData.getPtr()));
  return value;//return *(ADateTime*)FData.Ptr;
 }
//---------------------------------------------------------------------------
 public void SetAsDateTime(ADateTime value) throws AException
 {
  if(FInfo.getType()!=dbftDateTime)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  FData.setPtr(ABuffer.DoubleToBuf(value.getDouble()));
//  *(ADateTime*)FData.Ptr=value;
 }
//---------------------------------------------------------------------------
 public String GetAsVarChar() throws AException
 {
  if(FInfo.getType()!=dbftVarChar)throw new AException("Type mismatch");
  if(getIsNULL())throw new AException("Is null");
  /*int len=FData.getSize()/2;//sizeof(wchar_t);
  String value;
  wchar_t*data=(wchar_t*)FData.Ptr;
  for(int q=0;q<len;q++)
  {
   if(*(data+q)==0)
    break;
   value+=*(data+q);
  }
  return value;*/

  return ABuffer.BufToStr(FData.getPtr());

 /* String str1=ABuffer.BufToStr(Data1.getPtr());
  String str2=ABuffer.BufToStr(Data2.getPtr());
  int cmp;
  if(Reg)cmp=str1.compareTo(str2);
  else cmp=str1.compareToIgnoreCase(str2);
  if(cmp<0)return '<';
  if(cmp>0)return '>';
  return '=';*/
 }
//---------------------------------------------------------------------------
 public void SetAsVarChar(String value) throws AException
 {
  if(FInfo.getType()!=dbftVarChar)throw new AException("Type mismatch");
  if(getIsNULL())InitializeData();
  int len=value.length();
  if(len>FInfo.GetLength())
  {
   sendWarning("SetAsVarChar","Length>Max",len);
   len=FInfo.GetLength();
  }
  /*wchar_t*ptr=value.w_str();
  wchar_t*data=(wchar_t*)FData.Ptr;
  for(int q=0;q<len;q++)
  {
   *(data+q)=*(ptr+q);
  }
  if(len<FInfo.getLength())
   *(data+len)=0;*/
  byte buf[]=ABuffer.StrToBuf(value);
  byte array[]=new byte[len];
  for(int q=0;q<len;q++){array[q]=buf[q];}
  FData.setPtr(array);
 }
//---------------------------------------------------------------------------
 public String GetAsString() throws AException
 {
  if(FInfo.getType()!=dbftString)throw new AException("Type mismatch");
  /*int len=FData.getSize()/2;//sizeof(wchar_t);
  String value="";
  wchar_t*data=(wchar_t*)FData.Ptr;
  for(int q=0;q<len;q++)
  {
   value+=*(data+q);
  }
  return value;*/
  return ABuffer.BufToStr(FData.getPtr());
 }
//---------------------------------------------------------------------------
 public void SetAsString(String value) throws AException
 {
  if(FInfo.getType()!=dbftString)throw new AException("Type mismatch");
  /*int len=value.length();
  FData.resize(len*2);//sizeof(wchar_t));
  wchar_t*ptr=value.w_str();
  wchar_t*data=(wchar_t*)FData.Ptr;
  for(int q=0;q<len;q++)
  {
   *(data+q)=*(ptr+q);
  }
  if(len<FInfo.getLength())
   *(data+len)=0; //???*/

  int len=value.length();
  FData.resize(len*2);//sizeof(wchar_t));
  byte array[]=ABuffer.StrToBuf(value);
  FData.setPtr(array);
 }
//---------------------------------------------------------------------------
 public ABuffer GetAsBuffer() throws AException
 {
  if(FInfo.getType()!=dbftBuffer)throw new AException("Type mismatch");
  ABuffer buf=new ABuffer();
  buf.write(FData.getPtr());
  return buf;//FData.GetBuffer();
 }
//---------------------------------------------------------------------------
 public ADBFieldInfo GetInfo() throws AException
 {
  return new ADBFieldInfo(FInfo);
 }
//---------------------------------------------------------------------------
//  __property ADBFieldInfo Info={read=FInfo};
//---------------------------------------------------------------------------
 public ADBFieldData GetData() throws AException
 {
  return new ADBFieldData(FData);
 }
//---------------------------------------------------------------------------
//  __property ADBFieldData Data={read=FData};
//---------------------------------------------------------------------------
//  __property int IsNULL={read=getIsNULL,write=SetIsNULL};
//---------------------------------------------------------------------------
//  __property int Size={read=getSize};
//---------------------------------------------------------------------------
//  __property int Length={read=getLength};
//---------------------------------------------------------------------------
//  __property bool AsBool={read=GetAsBool,write=SetAsBool};
//  __property char AsChar={read=GetAsChar,write=SetAsChar};
//  __property Byte AsByte={read=GetAsByte,write=SetAsByte};
//  __property short AsShort={read=GetAsShort,write=SetAsShort};
//  __property Word AsWord={read=GetAsWord,write=SetAsWord};
//  __property int AsInt={read=GetAsInt,write=SetAsInt};
//  __property UINT AsUint={read=GetAsUint,write=SetAsUint};
//  __property float AsFloat={read=GetAsFloat,write=SetAsFloat};
//  __property double AsDouble={read=GetAsDouble,write=SetAsDouble};
//  __property TDateTime AsDateTime={read=GetAsDateTime,write=SetAsDateTime};
//  __property String AsVarChar={read=GetAsVarChar,write=SetAsVarChar};
//  __property String AsString={read=GetAsString,write=SetAsString};
//  __property ABuffer* AsBuffer={read=GetAsBuffer};
//---------------------------------------------------------------------------
 public char Compare(final ADBField value)
 {
  if(FInfo.getType()!=value.FInfo.getType())
  {
   sendError("Compare","Type mismatch",null);
   return 'e';
  }
  return CompareDBField(FData,FInfo,value.FData,value.FInfo);
 }
//---------------------------------------------------------------------------
 public char CompareAsVarChar(final ADBField value,boolean Reg)
 {
  if(FInfo.getType()!=dbftVarChar)
  {
   sendError("CompareAsVarChar","Type mismatch",null);
   return 'e';
  }
  return CompareDBFieldAsString(FData,FInfo,value.FData,value.FInfo,Reg);
 }
//---------------------------------------------------------------------------
 public char CompareAsVarChar(final ADBField value)//,boolean Reg=false
 {
  return CompareAsVarChar(value,false);
 }
//---------------------------------------------------------------------------
 public char CompareAsString(final ADBField value,boolean Reg)
 {
  if(FInfo.getType()!=dbftString)
  {
   sendError("CompareAsString","Type mismatch",null);
   return 'e';
  }
  return CompareDBFieldAsString(FData,FInfo,value.FData,value.FInfo,Reg);
 }
//---------------------------------------------------------------------------
 public char CompareAsString(final ADBField value)//,boolean Reg=false
 {
  return CompareAsString(value,false);
 }
//---------------------------------------------------------------------------
 public String ToStr(boolean Formated) throws AException//=false
 {
  switch(FInfo.getType())
  {
   case dbftVoid:
    sendError("ToStr","Void can not convert to str",null);
    return "";
   case dbftBuffer:
    sendError("ToStr","Buffer can not convert to str",null);
    return "";
   case dbftString:
    if(Formated)return "\""+GetAsString()+"\"";
    return GetAsString();
  }
  if(getIsNULL())return "NULL";
  switch(FInfo.getType())
  {
   case dbftBool:
    if(ABuffer.BufToBool(FData.getPtr()))return "true";// *(bool*)FData.Ptr)
    else return "false";
//   return BoolToStr(*(bool*)FData.Ptr);
   case dbftChar:
    if(Formated)return "'"+Character.toString(ABuffer.BufToChar(FData.getPtr()))+"'";//"'"+String(*(char*)FData.Ptr)+"'";//???
    return Character.toString(ABuffer.BufToChar(FData.getPtr()));//*(char*)FData.Ptr;                                    //???
   case dbftByte:
    return Byte.toString(ABuffer.BufToByte(FData.getPtr()));//IntToStr(*(Byte*)FData.Ptr);
   case dbftWChar:
    if(Formated)return "'"+Character.toString(ABuffer.BufToChar(FData.getPtr()))+"'";//"'"+String(*(char*)FData.Ptr)+"'";
    return Character.toString(ABuffer.BufToChar(FData.getPtr()));//*(char*)FData.Ptr;
   case dbftShort:
    return Short.toString(ABuffer.BufToShort(FData.getPtr()));//IntToStr(*(short*)FData.Ptr);
   case dbftWord:
    return Short.toString(ABuffer.BufToShort(FData.getPtr()));//IntToStr(*(Word*)FData.Ptr);
   case dbftInt:
    return Integer.toString(ABuffer.BufToInt(FData.getPtr()));//IntToStr(*(int*)FData.Ptr);
   case dbftUint:
    return Long.toString(ABuffer.BufToLong(FData.getPtr()));//IntToStr((__int64)*(UINT*)FData.Ptr);
   case dbftFloat:
    return Float.toString(ABuffer.BufToFloat(FData.getPtr()));//FloatToStr(*(float*)FData.Ptr);
   case dbftDouble:
    return Double.toString(ABuffer.BufToDouble(FData.getPtr()));//FloatToStr(*(double*)FData.Ptr);
   case dbftDateTime:
   {
    ADateTime dt=new ADateTime();
    dt.setDouble(ABuffer.BufToDouble(FData.getPtr()));
    if(Formated)return "\""+dt.toString("yyyy-MM-dd HH:mm:ss")+"\"";
    return dt.toString("yyyy-MM-dd HH:mm:ss");
   }
   case dbftVarChar:
    if(Formated)return "\""+GetAsVarChar()+"\"";
    return GetAsVarChar();
   default:
    sendError("ToStr","Unknow Type",(int)FInfo.getType());
    return "";
  }
 }
//---------------------------------------------------------------------------
 public boolean FromStr(String value) throws AException
 {
// if(value.isEmpty()&&((!FInfo.getType()==dbftVarChar)||(!FInfo.getType()==dbftString)))
  if(value.isEmpty()&&((FInfo.getType()!=dbftVarChar)||(FInfo.getType()!=dbftString)))
  {
   throw new AException("Value is empty");
  }

  switch(FInfo.getType())
  {
   case dbftVoid:
    throw new AException("Void can not convert from str");
   case dbftBuffer:
    throw new AException("Buffer can not convert from str");
  }

  if(value.equals("NULL"))
  {
   if(FInfo.NotNULL)throw new AException("NotNULL value can not by NULL");
   FData.clear();
   return true;
  }

  if(getIsNULL())InitializeData();

  if(FInfo.getType()==dbftBool)
  {
   ARefBool ref=new ARefBool();
   if(!TryStrToBool(value,ref))throw new AException("Value do not converted to bool");
   FData.setPtr(ABuffer.BoolToBuf(ref.value));//*(bool*)FData.Ptr=data;
   return true;
  }

  switch(FInfo.getType())
  {
   case dbftChar:
//   return *(char*)FData.Ptr;
    sendError("FromStr","this function don't realised for dbftChar",null);
    return false;
   case dbftVarChar:
    SetAsVarChar(value);
    return true;
//   if(Formated)return "\""+GetAsVarChar()+"\"";
   case dbftString:
    SetAsString(value);
    return true;
//   if(Formated)return "\""+GetAsString()+"\"";
  }

//if(FInfo.getType()==dbftChar)
//{
// char data=0;
// try
// {
//  data=Character.parseChar(value);
// }
// catch(NumberFormatException e)
// {
//  sendError("FromStr","Value do not converted to char",null);
//  return false;
// }
// FData.setPtr(ABuffer.CharToBuf(data));
// return true;
//}

  if(FInfo.getType()==dbftByte)
  {
   try
   {
    FData.setPtr(ABuffer.ByteToBuf(Byte.parseByte(value)));//*(Byte*)FData.Ptr=data;
    return true;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to byte",ex);}
  }

  if(FInfo.getType()==dbftShort)
  {
   try
   {
    FData.setPtr(ABuffer.ShortToBuf(Short.parseShort(value)));
    return true;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to short",ex);}
  }

  if(FInfo.getType()==dbftWord)
  {
   try
   {
    FData.setPtr(ABuffer.ShortToBuf(Short.parseShort(value)));
    return true;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to short",ex);}
  }

  if(FInfo.getType()==dbftInt)
  {
   try
   {
    FData.setPtr(ABuffer.IntToBuf(Integer.parseInt(value)));
    return true;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to int",ex);}
  }

  if(FInfo.getType()==dbftUint)
  {
   try
   {
    FData.setPtr(ABuffer.IntToBuf(Integer.parseInt(value)));
    return true;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to int",ex);}
  }

//if(FInfo.getType()==dbftLong)
//{
// long data=0;
// try
// {
//  data=Long.parseLong(value);
// }
// catch(NumberFormatException e)
// {
//  sendError("FromStr","Value do not converted to long",null);
//  return false;
// }
// FData.setPtr(ABuffer.LongToBuf(data));
// return true;
//}

  if(FInfo.getType()==dbftFloat)
  {
   try
   {
    FData.setPtr(ABuffer.FloatToBuf(Float.parseFloat(value)));//*(float*)FData.Ptr=data;
    return true;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to float",ex);}
  }

  if(FInfo.getType()==dbftDouble)
  {
   try
   {
    FData.setPtr(ABuffer.DoubleToBuf(Double.parseDouble(value)));//*(double*)FData.Ptr=data;
    return true;
   }
   catch(NumberFormatException ex){throw new AException("Value do not converted to double",ex);}
  }

  if(FInfo.getType()==dbftDateTime)
  {
   ADateTime data=new ADateTime();
   if(!data.fromString(value,"yyyy-MM-dd HH:mm:ss"))throw new AException("Value do not converted to TDateTime");
   FData.setPtr(ABuffer.DoubleToBuf(data.getDouble()));//*(ADateTime*)FData.Ptr=data;
   return true;
  }

  throw new AException("Unknow Type,(int)FInfo.getType()");
//  sendError("FromStr","this function don't realised",null);
//  return false;
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
 public void AssignValue(final ADBField value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  FData.assign(value.FData);
 }
//---------------------------------------------------------------------------
 public boolean isEqual(final ADBField value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator!=(final ADBField value)
 public boolean isInequal(final ADBField value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)!='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator>(final ADBField value)
 public boolean isMore(final ADBField value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='>')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator>=(final ADBField value)
 public boolean isMoreAndEqual(final ADBField value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='>')return true;
  if(Compare(value)=='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator<(final ADBField value)
 public boolean isLess(final ADBField value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='<')return true;
  return false;
 }
//---------------------------------------------------------------------------
// boolean operator<=(final ADBField value)
 public boolean isLessAndEqual(final ADBField value) throws AException
 {
  if(FInfo.getType()!=value.FInfo.getType())throw new AException("Type mismatch");
  if(Compare(value)=='<')return true;
  if(Compare(value)=='=')return true;
  return false;
 }
//---------------------------------------------------------------------------
}
