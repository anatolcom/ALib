/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;

import aclass.ABuffer;
import aclass.AClass;
import aclass.AException;
//---------------------------------------------------------------------------
//---------------------------------------------------<     C L A S S     >---
//---------------------------------------------------------------------------

/**
 *
 * @author Anatol
 */
public class AParserValueType extends AClass// implements AParserVarible
{
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
 private String FName;
//---------------------------------------------------------------------------
 private byte FType;
//---------------------------------------------------------------------------
 private int FStaticSize;
//---------------------------------------------------------------------------
 private void initialize()
 {
  FType=AParserVarible.TYPE_VOID;
  FStaticSize=0;
  NotNULL=false;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
 protected int getLength() throws AException
 {
  int constsize=getConstSize();
  if(constsize==-1)throw new AException("GetConstSize return error");
  return FStaticSize/constsize;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
 public boolean NotNULL;
//---------------------------------------------------------------------------
 public AParserValueType(){initialize();}
//---------------------------------------------------------------------------
 public AParserValueType(final AParserValueType value) throws AException
 {
  initialize();
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(final AParserValueType value) throws AException
 {
  if(value==null)throw new AException("value=null");
  FName=value.FName;
  FType=value.FType;
  FStaticSize=value.FStaticSize;
  NotNULL=value.NotNULL;
 }
//---------------------------------------------------------------------------
 public int getConstSize() throws AException{return AParserVarible.GetDBFieldTypeConstSize(FType);}
//---------------------------------------------------------------------------
 public int getStaticSize(){return FStaticSize;}
//---------------------------------------------------------------------------
 public String getName(){return FName;}
//---------------------------------------------------------------------------
 public boolean setName(final String Name)
 {
  if(Name.isEmpty())
  {
   FName="";
   return false;
  }
  if(!AParserVarible.ValidationName(Name))
  {
   sendError("SetName","Invalid Name",null);
   return false;
  }
  FName=Name;
  return true;
 }
//---------------------------------------------------------------------------
 public boolean isUserSize(){return AParserVarible.IsDBFieldTypeUserSize(FType);}
//---------------------------------------------------------------------------
 public boolean isStatic(){return AParserVarible.IsDBFieldTypeStatic(FType);}
//---------------------------------------------------------------------------
 public boolean isComparable(){return AParserVarible.IsDBFieldTypeComparable(FType);}
//---------------------------------------------------------------------------
 public boolean isIndexable(){return AParserVarible.IsDBFieldTypeIndexable(FType);}
//---------------------------------------------------------------------------
 public boolean isLogic(){return AParserVarible.IsDBFieldTypeLogic(FType);}
//---------------------------------------------------------------------------
 public boolean isDigital(){return AParserVarible.IsDBFieldTypeDigital(FType);}
//---------------------------------------------------------------------------
 public byte getType(){return FType;}
//---------------------------------------------------------------------------
 /**
  * Метод устанавливает тип данных Type и длинну даннх Lenght.<br/>
  * Длинна данных актуально только для данных с пользовательской длинной,
  * в остальных случаях этот параметр игнорируется.<br/>
  * @param Type - тип данных.
  * @param Lenght - длинна данных (только для данных с пользовательской длинной).
  * @return true - да,<br/>
  *         false - нет.
  */
 public boolean setType(byte Type,int Lenght) throws AException
 {
  FStaticSize=0;
  FType=Type;
  if(FType==AParserVarible.TYPE_VOID)return true;
  if(!isStatic())return true;
  int constsize=getConstSize();
  if(constsize==-1)throw new AException("GetConstSize return error");
  FStaticSize=constsize;
  if(isUserSize())
  {
   if(Lenght==0)throw new AException("Lenght=0");
   int maxlen=0x0FFFFFFF/constsize;
   if(Lenght>maxlen)throw new AException("Lenght > maximal lenght",Lenght);
   FStaticSize=Lenght*constsize;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public int WriteToBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  buf.writeLStr(FName);
  buf.write(ABuffer.ByteToBuf(FType));//,1);//sizeof(FType));
  buf.write(ABuffer.IntToBuf(FStaticSize));//,4);//sizeof(FStaticSize));
  buf.write(ABuffer.BoolToBuf(NotNULL));//,1);//sizeof(NotNULL));
  return buf.getPos();
 }
 //---------------------------------------------------------------------------
 public int ReadFromBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  FName=buf.readLStr();
  FType=ABuffer.BufToByte(buf.read(1));
  FStaticSize=ABuffer.BufToInt(buf.read(4));
  NotNULL=ABuffer.BufToBool(buf.read(1));
  return buf.getPos();
 }
//---------------------------------------------------------------------------
 @Override
 public boolean equals(Object obj)
 {
  if(obj==null)return false;
  if(getClass()!=obj.getClass())return false;
  final AParserValueType value=(AParserValueType)obj;
  if(FType!=value.FType)return false;
  if(isStatic()&&isUserSize())if(FStaticSize!=value.FStaticSize)return false;
  if(!FName.equals(value.FName))return false;
  if(NotNULL!=value.NotNULL)return false;
  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "ADBFieldInfo{"+"FName="+FName+", FType="+FType+", FStaticSize="+FStaticSize+'}';
 }
//---------------------------------------------------------------------------
}
