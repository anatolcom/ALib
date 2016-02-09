/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adatetime;

import aclass.ABuffer;
import aclass.AClass;
import aclass.AException;
import java.text.ParseException;
//import java.util.Date;
//import java.util.Locale;
//import java.util.logging.Level;
//import java.util.logging.Logger;
/**
 *
 * @author Anatol
 */
public class ADateTime extends AClass
{
//---------------------------------------------------------------------------
 private int FRawDate=0;
 private int FRawTime=1;
//---------------------------------------------------------------------------
/* public class AStructDate
 {
  private int FYear;
  private int FMonth;
  private int FDay;
  public AStructDate(){FYear=1;FMonth=1;FDay=1;}
  public AStructDate(AStructDate value){assign(value);}
  public AStructDate(int Year,int Month,int Day){FYear=Year;FMonth=Month;FDay=Day;}
  public final void assign(AStructDate value){FYear=value.getYear();FMonth=value.getMonth();FDay=value.getDay();}
  public int getYear(){return FYear;}
  public void setYear(int Year){FYear=Year;}
  public int getMonth(){return FMonth;}
  public void setMonth(int Month){FMonth=Month;}
  public int getDay(){return FDay;}
  public void setDay(int Day){FDay=Day;}
 }*/

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

 AStructDate FStructDate=null;
 AStructTime FStructTime=null;

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
 private boolean isLeapYear(int year)
 {
  if(((year/4)*4)!=year)return false;
  if(((year/100)*100)==year)if(((year/400)*400)!=year)return false;
  return true;
 }
//---------------------------------------------------------------------------
 public ADateTime()
 {
  FRawDate=0;
  FRawTime=1;
  FStructDate=null;
  FStructTime=null;
 }
//---------------------------------------------------------------------------
 public ADateTime(ADateTime value){assign(value);}
//---------------------------------------------------------------------------
 public final void assign(ADateTime value)
 {
  if(!testNull(value,"assign","value"))return;
  FRawDate=value.FRawDate;
  FRawTime=value.FRawTime;
  if(value.FStructDate==null)FStructDate=null;
  else FStructDate=new AStructDate(value.FStructDate);//null;
  if(value.FStructTime==null)FStructTime=null;
  else FStructTime=new AStructTime(value.FStructTime);//null;
 }
//---------------------------------------------------------------------------
 public int getRawDate(){return FRawDate;}
//---------------------------------------------------------------------------
 public void setRawDate(int value)
 {
  FRawDate=value;
  FStructDate=null;
 }
//---------------------------------------------------------------------------
 public int getRawTime(){return FRawTime;}
//---------------------------------------------------------------------------
 public void setRawTime(int value)
 {
  FRawTime=value;
  FStructTime=null;
 }
//---------------------------------------------------------------------------
 public void setLong(final long value)                                  //!!!
 {
  int IDate=(int)(value/(86400000));//24*60*60*1000
  int ITime=(int)(value-((long)IDate*86400000));//24*60*60*1000
  if(value<0)ITime=-ITime;
  IDate+=719529;
  FRawDate=IDate;//FStructDate=setIntDate(days);
  FRawTime=ITime;//FStructTime=setIntTime(milliseconds);
 }
//---------------------------------------------------------------------------
 public long getLong()                                                  //!!!
 {
  final int days=FRawDate-719529;//=getIntDate(FYear,FMonth,FDay)-719529;
  final long ldate=(long)((long)days*86400000);//24*60*60*1000
  final int itime=FRawTime;//=getIntTime(FHour,FMinute,FSecond,FMilliSecond);
  if(ldate<0)return ldate-(long)itime;
  else return ldate+(long)itime;
 }
//---------------------------------------------------------------------------
 public void setDouble(final double value)                              //!!!
 {
  int IDate=(int)value;
  IDate=IDate+693960;
  double DTime=value-(int)value;
  if(DTime<0)DTime=-DTime;
  FRawDate=IDate;//FStructDate=setIntDate(days);
  int ITime=(int)(DTime/((double)1/86400000));//24*60*60*1000
  FRawTime=ITime;//FStructTime=setIntTime(milliseconds);
 }
//---------------------------------------------------------------------------
 public double getDouble()                                              //!!!
 {
  final double ddate=(double)(FRawDate-693960);//=(double)getIntDate(FYear,FMonth,FDay)-693960;
  final int itime=FRawTime;//=getIntTime(FHour,FMinute,FSecond,FMilliSecond);
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
 public int getDayOfWeek()
 {
  return (int)((FRawDate+3)%7+1);
 }
//---------------------------------------------------------------------------
 int WriteToBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=null");
  buf.write(ABuffer.IntToBuf(FRawDate));
  return buf.write(ABuffer.IntToBuf(FRawTime));
 }
//---------------------------------------------------------------------------
 int ReadFromBuffer(ABuffer buf) throws AException
 {
  if(buf==null)throw new AException("buf=null");
  FRawDate=ABuffer.BufToInt(buf.read(4));
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
  String str;//=null;                                    //"dd.MM.yyyy HH:mm:ss" RU
  try                                                 //"yyyy-MM-dd HH:mm:ss" ISO
  {
   java.util.SimpleTimeZone timezone=new java.util.SimpleTimeZone(3*3600000,"UTC+3");//Europe/Moscow UTC=GTM 14400000
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
   java.util.SimpleTimeZone timezone=new java.util.SimpleTimeZone(3*3600000,"UTC+3");//Europe/Moscow UTC=GTM 14400000
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
 public int compareTo(final ADateTime other)
 {
  if(this.FRawDate<other.FRawDate)return -1;
  if(this.FRawDate>other.FRawDate)return 1;
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
  final ADateTime other=(ADateTime)obj;
  if(this.FRawDate!=other.FRawDate)return false;
  if(this.FRawTime!=other.FRawTime)return false;
  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public int hashCode()
 {
  int hash=3;
  hash=97*hash+this.FRawDate;
  hash=97*hash+this.FRawTime;
  return hash;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  if(FStructDate==null)FStructDate=setIntDate(FRawDate);
  if(FStructTime==null)FStructTime=setIntTime(FRawTime);
  return IntToLStr(FStructDate.Year,4)
          +"-"+IntToLStr(FStructDate.Month,2)
          +"-"+IntToLStr(FStructDate.Day,2)
          +" "+IntToLStr(FStructTime.Hour,2)
          +":"+IntToLStr(FStructTime.Minute,2)
          +":"+IntToLStr(FStructTime.Second,2)
          //+"."+FStructTime.MilliSecond
          +"";
 }
//---------------------------------------------------------------------------
}
