/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adatetime;

import aclass.ABuffer;
import aclass.AClass;
import aclass.AException;
import java.text.ParseException;
/**
 *
 * @author Anatol
 */
public class ATime extends AClass
{
//---------------------------------------------------------------------------
 private class AStructTime
 {
  final public int Hour;
  final public int Minute;
  final public int Second;
  final public int MilliSecond;
  public AStructTime(AStructTime value)
  {
   Hour=value.Hour;
   Minute=value.Minute;
   Second=value.Second;
   MilliSecond=value.MilliSecond;
  }
  public AStructTime(int hour,int minute,int second,int millisecond)
  {
   Hour=hour;
   Minute=minute;
   Second=second;
   MilliSecond=millisecond;
  }
 }
//---------------------------------------------------------------------------
 private int FRawTime=1;
//---------------------------------------------------------------------------
 AStructTime FStructTime=null;
//---------------------------------------------------------------------------
 private int getIntTime(int hour,int minute,int second,int millisecond) //!!!
 {
  final int hours=(int)hour;
  final int minutes=(int)(minute+(hours*60));
  final int seconds=(int)(second+(minutes*60));
  final int milliseconds=(int)(millisecond+(seconds*1000));
  return milliseconds;
 }
//---------------------------------------------------------------------------
 private AStructTime setIntTime(int milliseconds)                       //!!!
 {
  int seconds=(int)(milliseconds/1000);
  int minutes=(int)(seconds/60);
  int hours=(int)(minutes/60);
  milliseconds=(int)(milliseconds-(seconds*1000));
  seconds=(int)(seconds-(minutes*60));
  minutes=(int)(minutes-(hours*60));
  return new AStructTime(hours,minutes,seconds,milliseconds);
 }
//---------------------------------------------------------------------------
 public ATime()
 {
 }
//---------------------------------------------------------------------------
 public ATime(ATime value)
 {
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(ATime value)
 {
  FRawTime=value.FRawTime;
  if(value.FStructTime==null)FStructTime=null;
  else FStructTime=new AStructTime(value.FStructTime);//null;
 }
//---------------------------------------------------------------------------
 public int setLong(final long value)                                   //!!!
 {
  int IDate=(int)(value/(86400000));//24*60*60*1000
  int ITime=(int)(value-((long)IDate*86400000));//24*60*60*1000
  if(value<0)ITime=-ITime;
  IDate+=719529;
  FRawTime=ITime;//FStructTime=setIntTime(milliseconds);
  return IDate;//FRawDate=IDate;//FStructDate=setIntDate(days);
 }
//---------------------------------------------------------------------------
 public long getLong()                                                  //!!!
 {
  final int days=-719529;//FRawDate-719529;
  final long ldate=(long)((long)days*86400000);//24*60*60*1000
  final int itime=FRawTime;//=getIntTime(FHour,FMinute,FSecond,FMilliSecond);
  if(ldate<0)return ldate-(long)itime;
  else return ldate+(long)itime;
 }
//---------------------------------------------------------------------------
 public int setDouble(final double value)                               //!!!
 {
  int IDate=(int)value;
  IDate=IDate+693960;
  double DTime=value-(int)value;
  if(DTime<0)DTime=-DTime;
  int ITime=(int)(DTime/((double)1/86400000));//24*60*60*1000
  FRawTime=ITime;//FStructTime=setIntTime(milliseconds);
  return IDate;//FRawDate=IDate;//FStructDate=setIntDate(days);
 }
//---------------------------------------------------------------------------
 public double getDouble()                                              //!!!
 {
  final double ddate=(double)-693960;//(FRawDate-693960);//=(double)getIntDate(FYear,FMonth,FDay)-693960;
  final int itime=FRawTime;//=getIntTime(FHour,FMinute,FSecond,FMilliSecond);
  final double dtime=(double)itime*((double)1/86400000);
  if(ddate<0)return (double)(ddate-dtime);
  else return (double)(ddate+dtime);
 }
//---------------------------------------------------------------------------
 public boolean setTime(final int hour,final int minute,final int second,final int millisecond)
 {
  if(!testRange(hour,0,24,"SetTime","hour"))return false;
  if(!testRange(minute,0,60,"SetTime","minute"))return false;
  if(!testRange(second,0,60,"SetTime","second"))return false;
  if(!testRange(millisecond,0,1000,"SetTime","millisecond"))return false;

  FRawTime=getIntTime(hour,minute,second,millisecond);
  FStructTime=new AStructTime(hour,minute,second,millisecond);

  return true;
 }
//---------------------------------------------------------------------------
 public boolean setTime(final int hour,final int minute,final int second)
 {
  return setTime(hour,minute,second,0);
 }
//---------------------------------------------------------------------------
 public boolean setTime(final int hour,final int minute)
 {
  return setTime(hour,minute,0,0);
 }
//---------------------------------------------------------------------------
 public int getHour()
 {
  if(FStructTime==null)FStructTime=setIntTime(FRawTime);
  return FStructTime.Hour;
 }
//---------------------------------------------------------------------------
 public int getMinute()
 {
  if(FStructTime==null)FStructTime=setIntTime(FRawTime);
  return FStructTime.Minute;
 }
//---------------------------------------------------------------------------
 public int getSecond()
 {
  if(FStructTime==null)FStructTime=setIntTime(FRawTime);
  return FStructTime.Second;
 }
//---------------------------------------------------------------------------
 public int getMilliSecond()
 {
  if(FStructTime==null)FStructTime=setIntTime(FRawTime);
  return FStructTime.MilliSecond;
 }
//---------------------------------------------------------------------------
 int WriteToBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  return buf.write(ABuffer.IntToBuf(FRawTime));
 }
//---------------------------------------------------------------------------
 int ReadFromBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  FRawTime=ABuffer.BufToInt(buf.read(4));
  return buf.getPos();
 }
//---------------------------------------------------------------------------
 private String IntToLStr(final int VALUE,final int LEN)                //!!!
 {
  String str=Integer.toString(VALUE);
  while(str.length()<LEN)str="0"+str;
  if(str.length()>LEN)str=str.substring(str.length()-LEN,str.length());
  return str;
 }
//---------------------------------------------------------------------------
 public void setNow(int RawOffset)                                                   //???
 {
//  java.util.SimpleTimeZone timezone=new java.util.SimpleTimeZone(4*3600000,"UTC+4");
  java.util.Calendar cal=java.util.Calendar.getInstance();
  setLong(cal.getTimeInMillis()+RawOffset);//timezone.getRawOffset());
 }
//---------------------------------------------------------------------------
 public String toString(String Format)                                  //???
 {
  String str=null;                                    //"dd.MM.yyyy HH:mm:ss" RU
  try                                                 //"yyyy-MM-dd HH:mm:ss" ISO
  {
   java.util.SimpleTimeZone timezone=new java.util.SimpleTimeZone(4*3600000,"UTC+4");//Europe/Moscow UTC=GTM 14400000
   final java.text.SimpleDateFormat DF=new java.text.SimpleDateFormat(Format,java.util.Locale.getDefault());
   DF.setTimeZone(timezone);
   java.util.Date date=new java.util.Date();
   date.setTime(getLong()-timezone.getRawOffset());
   str=DF.format(date);
  }
  catch(Exception e)
  {
   sendError("toString","Incorrect Format",null);
   return "";
  }
  return str;
 }
//---------------------------------------------------------------------------
 public boolean fromString(String Str,String Format)//throws ParseException//???
 {
  try
  {
   java.util.SimpleTimeZone timezone=new java.util.SimpleTimeZone(4*3600000,"UTC+4");//Europe/Moscow UTC=GTM 14400000
   final java.text.SimpleDateFormat DF=new java.text.SimpleDateFormat(Format,java.util.Locale.getDefault());
   DF.setTimeZone(timezone);
   setLong(DF.parse(Str).getTime()+timezone.getRawOffset());
  }
  catch(ParseException ex)
  {
   sendError("fromString","Str can not by parse",null);
   return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public int compareTo(final ATime other)
 {
  if(this.FRawTime<other.FRawTime)return -1;
  if(this.FRawTime>other.FRawTime)return 1;
  return 0;
 }
//---------------------------------------------------------------------------
 @Override
 public boolean equals(Object obj)
 {
  if(obj==null)return false;
  if(getClass()!=obj.getClass())return false;
  final ATime other=(ATime)obj;
  if(this.FRawTime!=other.FRawTime)return false;
  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public int hashCode()
 {
  int hash=3;
  hash=97*hash+this.FRawTime;
  return hash;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  if(FStructTime==null)FStructTime=setIntTime(FRawTime);
  return IntToLStr(FStructTime.Hour,2)
          +":"+IntToLStr(FStructTime.Minute,2)
          +":"+IntToLStr(FStructTime.Second,2)
          //+"."+FStructTime.MilliSecond
          +"";
 }
//---------------------------------------------------------------------------
}
