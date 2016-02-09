/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.object;
import aclass.aparser.type.AType;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AValue extends AObject
{
//---------------------------------------------------------------------------
//                                            Id,Name      ,PredefinedSize,DynamicSize,Comparable,Indexable,Logic,Digital
 public static final AType BOOL    =new AType(1 ,"bool"    ,1             ,false      ,true      ,false    ,true ,false);
 public static final AType CHAR    =new AType(2 ,"char"    ,2             ,false      ,true      ,true     ,false,false);
// public static final AType BYTE    =new AType(3 ,"byte"    ,1             ,false      ,true      ,true     ,false,true );
 public static final AType INT     =new AType(4 ,"int"     ,4             ,false      ,true      ,true     ,false,true );
 public static final AType FLOAT   =new AType(5 ,"float"   ,4             ,false      ,true      ,true     ,false,true );
// public static final AType DATETIME=new AType(6 ,"datetime",8             ,false      ,true      ,false    ,false,false);
//---------------------------------------------------------------------------
 public static final AType STR     =new AType(7 ,"string"  ,2        ,true       ,true      ,false    ,false,false);
//---------------------------------------------------------------------------
 public static final AType UNION   =new AType(8 ,"union"   ,0        ,true       ,false     ,false    ,false,false);
//---------------------------------------------------------------------------
 public static AType TypeFromStr(String typeName) throws AException
 {
  if("bool".equals(typeName))return BOOL;
  if("char".equals(typeName))return CHAR;
//  if("byte".equals(typeName))return BYTE;
  if("int".equals(typeName))return INT;
  if("float".equals(typeName))return FLOAT;
//  if("datetime".equals(typeName))return DATETIME;
  if("string".equals(typeName))return STR;
  throw new AException("Unknow Type \""+typeName+"\"");
 }
//---------------------------------------------------------------------------
 protected final AData data=new AData();
//---------------------------------------------------------------------------
 protected final boolean constant;
//---------------------------------------------------------------------------
 public static final boolean VARIBLE=false;
 public static final boolean CONSTANT=true;
//---------------------------------------------------------------------------
// private void initialize()throws AException
// {
////  operationList.registred();
//  ACTION_META=new AAction(null)
//  {
//   @Override
//   public void run() throws AException
//   {
//    throw new UnsupportedOperationException("Not supported yet.");
//   }
//  };
// }
//---------------------------------------------------------------------------
 public AValue(AType type)throws AException
 {
  super(type);
  this.constant=VARIBLE;
  set(null);
 }
//---------------------------------------------------------------------------
 public AValue(AType type,byte[] value)throws AException
 {
  super(type);
  this.constant=VARIBLE;
  set(value);
 }
//---------------------------------------------------------------------------
 public AValue(AType type,byte[] value,boolean constant)throws AException
 {
  super(type);
  this.constant=constant;
  set(value);
 }
//---------------------------------------------------------------------------
 public final void set(byte[] value)throws AException                   //???
 {
  if(value==null)
  {
   data.setNull();
   return;
  }
  if(isDynamicSize())
  {
   int cnt=value.length/getConstSize();
   if(value.length!=getConstSize()*cnt)throw new AException("value.length!=ConstSize*cnt",value.length);
  }
  else 
  {
   if(value.length!=getConstSize())throw new AException("value.length!=ConstSize",value.length);
  } 
  data.resize(value.length);
  data.set(value);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public boolean isDynamicSize()
 {
//  if(isType(STR))sendError("getConstSize","size of STR under development",null);
  if(isType(STR))return true;
  return type.isDynamic();
 }
//---------------------------------------------------------------------------
 @Deprecated
 public int getConstSize()
 {
//  if(isType(STR))sendError("getConstSize","size of STR under development",null);
  if(isType(STR))return 2;
  return type.PredefinedSize;
 }
//---------------------------------------------------------------------------
///**
// * @return количество элементов с длиной type.ConstSize
// */
// @Deprecated
// public int count()throws AException
// {
//  if(type.DynamicSize)return data.getSize()/getConstSize();
//  else return 1;
// };
//---------------------------------------------------------------------------
 public int compare(AValue data)throws AException
 {
  if(!data.isType(getType()))throw new AException("type missmatch");
  if(isType(BOOL))
  {
   if(this.getBool()==true&data.getBool()==false)return  1;
   if(this.getBool()==false&data.getBool()==true)return -1;
   return 0;
  }
  if(isType(CHAR))return this.getChar().compareTo(data.getChar());
  if(isType(INT))
  {
   if(this.getInt()>data.getInt())return  1;
   if(this.getInt()<data.getInt())return -1;
   return 0;
  }
  if(isType(STR))return this.getStr().compareTo(data.getStr());
  throw new AException("Unknow type");
 }
//---------------------------------------------------------------------------
 public final byte[] get(){return data.get();}
//---------------------------------------------------------------------------
 public String getTypeName()
 {
  return type.Name;
 }
//---------------------------------------------------------------------------
 public AType getType(){return type;}
//---------------------------------------------------------------------------
 public boolean isType(AType type)
 {
  if(this.type.Id!=type.Id)return false;
  return true;
 }
//---------------------------------------------------------------------------
 public boolean isConst(){return constant;}
//---------------------------------------------------------------------------
 @Deprecated
 public final AValue getCopy()throws AException{return new AValue(type,get(),AValue.VARIBLE);}
//---------------------------------------------------------------------------
 public final Boolean getBool()throws AException{return AData.DataToBool(data.get());}
//---------------------------------------------------------------------------
 public final void setBool(Boolean value)throws AException{data.set(AData.BoolToData(value));}
//---------------------------------------------------------------------------
 public final Character getChar()throws AException{return AData.DataToChar(data.get());}
//---------------------------------------------------------------------------
 public final void setChar(Character value)throws AException{set(AData.CharToData(value));}
//---------------------------------------------------------------------------
 public final Integer getInt() throws AException{return AData.DataToInt(data.get());}
//---------------------------------------------------------------------------
 public final void setInt(Integer value)throws AException{set(AData.IntToData(value));}
//---------------------------------------------------------------------------
 public final Float getFloat() throws AException{return AData.DataToFloat(data.get());}
//---------------------------------------------------------------------------
 public final void setFloat(Float value)throws AException{set(AData.FloatToData(value));}
//---------------------------------------------------------------------------
 public final String getStr()throws AException{return AData.DataToStr(data.get());}
//---------------------------------------------------------------------------
 public final void setStr(String value)throws AException{set(AData.StrToData(value));}
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "AValue "+type.Name;
 }
//---------------------------------------------------------------------------
 @Override
 public int sizeof()
 {
  if(isType(AValue.STR))return data.getSize();
  return type.PredefinedSize;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject copy()throws AException{return new AValue(type,get(),AValue.VARIBLE);}
//---------------------------------------------------------------------------
 @Override
 protected void assignData(AObject object)throws AException
 {
  testTypeMissmatch(object);
  AValue value=(AValue)object;
  this.data.assign(value.data);
 }
//---------------------------------------------------------------------------
}
