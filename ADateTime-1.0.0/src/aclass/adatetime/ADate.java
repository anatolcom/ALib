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
public class ADate extends AClass
{
//---------------------------------------------------------------------------
 private class AStructDate
 {
  final public int Year;
  final public int Month;
  final public int Day;
  public AStructDate(AStructDate value)
  {
   Year=value.Year;
   Month=value.Month;
   Day=value.Day;
  }
  public AStructDate(int year,int month,int day)
  {
   Year=year;
   Month=month;
   Day=day;
  }
 }
//---------------------------------------------------------------------------
 private int FRawDate=0;
//---------------------------------------------------------------------------
 AStructDate FStructDate=null;
//---------------------------------------------------------------------------
 private int FCountDaysOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
// private String FISONameOfMonth[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
// private String FNameOfMonth[]={"January","February","March","April","May","June","July","August","September","Oktober","November","December"};
// private String FISONameOfWeek[]={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
// private String FNameOfWeek[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
  //1  Jan January   31
  //2  Feb February  28(29)
  //3  Mar March     31
  //4  Apr April     30
  //5  May Muy       31
  //6  Jun June      30
  //7  Jul July      31
  //8  Aug August    31
  //9  Sep September 30
  //10 Okt Oktober   31
  //11 Nov November  30
  //12 Dec December  31

  //1  Mon Monday
  //2  Tue Tuesday
  //3  Wed Wednesday
  //4  Thu Thursday
  //5  Fri Friday
  //6  Sat Saturday
  //7  Sun Sunday

//---------------------------------------------------------------------------
 private int getIntDate(int year,int month,int day)                     //!!!
 {
  int days=0;
  for(int q=0;q<year;q++)
  {
   int yearlen=365;
   if(isLeapYear(q))yearlen=366;
   days+=yearlen;
  }
  for(int q=0;q<month-1;q++)
  {
   days+=FCountDaysOfMonth[q];
   if(q==1)if(isLeapYear(year))days++;
  }
  days+=day;
  return days;
 }
//---------------------------------------------------------------------------
 private AStructDate setIntDate(int days)                               //!!!
 {
  int Year=0;
  while(true)
  {
   int yearlen=365;
   if(isLeapYear(Year))yearlen=366;
   if(days<yearlen)break;
   days-=yearlen;
   Year++;
  }
  int Month=1;
  int Day=days;
  for(int q=0;q<12;q++)
  {
   int cnt=FCountDaysOfMonth[q];
   if(q==1)if(isLeapYear(Year))cnt++;
   if(Day<=cnt)break;
   Day-=cnt;
   Month++;
  }
  return new AStructDate(Year,Month,Day);
 }
//---------------------------------------------------------------------------
 private boolean isLeapYear(int year)
 {
  if(((year/4)*4)!=year)return false;
  if(((year/100)*100)==year)if(((year/400)*400)!=year)return false;
  return true;
 }
//---------------------------------------------------------------------------
 public ADate()
 {
 }
//---------------------------------------------------------------------------
 public ADate(ADate value)
 {
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(ADate value)
 {
  FRawDate=value.FRawDate;
  if(value.FStructDate==null)FStructDate=null;
  else FStructDate=new AStructDate(value.FStructDate);//null;
 }
//---------------------------------------------------------------------------
 public int setLong(final long value)                                   //!!!
 {
  int IDate=(int)(value/(86400000));//24*60*60*1000
  int ITime=(int)(value-((long)IDate*86400000));//24*60*60*1000
  if(value<0)ITime=-ITime;
  IDate+=719529;
  FRawDate=IDate;
  return ITime;//FRawTime=ITime;
 }
//---------------------------------------------------------------------------
 public long getLong()                                                  //!!!
 {
  final int days=FRawDate-719529;//=getIntDate(FYear,FMonth,FDay)-719529;
  final long ldate=(long)((long)days*86400000);//24*60*60*1000
  final int itime=0;//FRawTime;//=getIntTime(FHour,FMinute,FSecond,FMilliSecond);
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
  FRawDate=IDate;//FStructDate=setIntDate(days);
  int ITime=(int)(DTime/((double)1/86400000));//24*60*60*1000
  return ITime;//FRawTime=ITime;//FStructTime=setIntTime(milliseconds);
 }
//---------------------------------------------------------------------------
 public double getDouble()                                              //!!!
 {
  final double ddate=(double)(FRawDate-693960);//=(double)getIntDate(FYear,FMonth,FDay)-693960;
  final int itime=0;//FRawTime;//=getIntTime(FHour,FMinute,FSecond,FMilliSecond);
  final double dtime=(double)itime*((double)1/86400000);
  if(ddate<0)return (double)(ddate-dtime);
  else return (double)(ddate+dtime);
 }
//---------------------------------------------------------------------------
 public boolean setDate(final int year,final int month,final int day)
 {
  if(!testRange(year,-292269053,292269053,"setDate","year"))return false;
  boolean leapyear=isLeapYear(year);
  if(!testRange(month,1,13,"setDate","month"))return false;
  int countdaysofmonth=FCountDaysOfMonth[month-1];
  if(leapyear)if(month==2)countdaysofmonth++;
  if(!testRange(day,1,countdaysofmonth+1,"setDate","day"))return false;

  FRawDate=getIntDate(year,month,day);
  FStructDate=new AStructDate(year,month,day);
  return true;
 }
//---------------------------------------------------------------------------
 public int getYear()
 {
  if(FStructDate==null)FStructDate=setIntDate(FRawDate);
  return FStructDate.Year;
 }
//---------------------------------------------------------------------------
 public boolean isLeapYear()
 {
  if(FStructDate==null)FStructDate=setIntDate(FRawDate);
  return isLeapYear(FStructDate.Year);
 }
//---------------------------------------------------------------------------
 public int getMonth()
 {
  if(FStructDate==null)FStructDate=setIntDate(FRawDate);
  return FStructDate.Month;
 }
//---------------------------------------------------------------------------
 public int getDayOfMonth()
 {
  if(FStructDate==null)FStructDate=setIntDate(FRawDate);
  return FStructDate.Day;
 }
//---------------------------------------------------------------------------
 public int getDayOfWeek()
 {
  return (int)((FRawDate+3)%7+1);
 }
//---------------------------------------------------------------------------
// public int getDayOfYear()
// {
//  return FDayOfYear;
// }
////---------------------------------------------------------------------------
// public int getWeekOfMonth()
// {
//  return FWeekOfMonth;
// }
//---------------------------------------------------------------------------
// public int getWeekOfYear()
// {
//  return FWeekOfYear;
// }
//---------------------------------------------------------------------------
 int WriteToBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  return buf.write(ABuffer.IntToBuf(FRawDate));
 }
//---------------------------------------------------------------------------
 int ReadFromBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=NULL");
  FRawDate=ABuffer.BufToInt(buf.read(4));
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
 public void setNow(int RawOffset)                                      //???
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
//   Logger.getLogger(ADateTime.class.getName()).log(Level.SEVERE,null,ex);
//   throw new ParseException("Unparseable date: \"" + Str + "\"" ,ex.getErrorOffset());
   return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 public int compareTo(final ADate other)
 {
  if(this.FRawDate<other.FRawDate)return -1;
  if(this.FRawDate>other.FRawDate)return 1;
  return 0;
 }
//---------------------------------------------------------------------------
 @Override
 public boolean equals(Object obj)
 {
  if(obj==null)return false;
  if(getClass()!=obj.getClass())return false;
  final ADate other=(ADate)obj;
  if(this.FRawDate!=other.FRawDate)return false;
  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public int hashCode()
 {
  int hash=3;
  hash=97*hash+this.FRawDate;
  return hash;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  if(FStructDate==null)FStructDate=setIntDate(FRawDate);
  return IntToLStr(FStructDate.Year,4)
          +"-"+IntToLStr(FStructDate.Month,2)
          +"-"+IntToLStr(FStructDate.Day,2)
          +"";
 }
//---------------------------------------------------------------------------
}
