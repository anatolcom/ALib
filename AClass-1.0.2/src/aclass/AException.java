/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 *
 * @author Пользователь
 * @version 0.1.0.1
 */
public class AException extends Exception
{
//---------------------------------------------------------------------------
 private static final String nullMessage = "";
 public final Integer value;
//---------------------------------------------------------------------------
 /**
  * Формирование строки со стеком исключений.<br/>
  *
  * @param exception исклчение
  * @return строка со стеком исключений
  */
 public static String print(Exception exception)
 {
  try
  {
   ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
   PrintStream ps = new PrintStream(byteBuffer);
   exception.printStackTrace(ps);
   ps.close();
   return byteBuffer.toString();
  }
  catch (Exception ex)
  {
   return exception.getMessage();
  }
 }
//---------------------------------------------------------------------------
 public static String printCauseStack(Throwable throwable)
 {
  if (throwable == null) return "";
  String causeMessage = printCauseStack(throwable.getCause());
  if (causeMessage.isEmpty()) return throwable.getMessage();
  return throwable.getMessage() + " becouse: \n" + causeMessage;
 }
//---------------------------------------------------------------------------
// @Deprecated
// public static String printException(Exception exception)
// {
//  return print(exception);
// }
//---------------------------------------------------------------------------
 public AException(String msg)
 {
  super(msg == null ? nullMessage : msg);
  if (msg == null) msg = nullMessage;
  this.value = null;
//  AClass.sendGlobalError(element.getClassName(),element.getMethodName(),msg,null);
  StackTraceElement element = this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(), "", msg, null);
 }
//---------------------------------------------------------------------------
 public AException(String msg, int value)
 {
  super(msg == null ? nullMessage : msg);
  if (msg == null) msg = nullMessage;
  this.value = value;
//  AClass.sendGlobalError(element.getClassName(),element.getMethodName(),msg,null);
  StackTraceElement element = this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(), "", msg, value);
 }
//---------------------------------------------------------------------------
 public AException(String msg, Exception ex)
 {
  super(msg == null ? nullMessage : msg, ex);
  if (msg == null) msg = nullMessage;
  this.value = null;
  StackTraceElement element = this.getStackTrace()[0];
  String methodName = element.getMethodName();
  if (ex == null)
  {
   AClass.sendGlobalError(element.toString(), "", msg + " and ex=null", null);
   return;
  }
  StackTraceElement[] elements = ex.getStackTrace();
  for (int q = 0; q < elements.length; q++)
  {
   StackTraceElement e = ex.getStackTrace()[q];
   if (!e.getMethodName().equals(methodName)) continue;
   element = e;
   break;
  }
  AClass.sendGlobalError(element.toString(), "", msg, null);
 }
//---------------------------------------------------------------------------
 public AException(String msg, Exception ex, int value)
 {
  super(msg == null ? nullMessage : msg, ex);
  if (msg == null) msg = nullMessage;
  this.value = value;
  StackTraceElement element = this.getStackTrace()[0];
  String methodName = element.getMethodName();
  if (ex == null)
  {
   AClass.sendGlobalError(element.toString(), "", msg + " and ex=null", null);
   return;
  }
  StackTraceElement[] elements = ex.getStackTrace();
  for (int q = 0; q < elements.length; q++)
  {
   StackTraceElement e = ex.getStackTrace()[q];
   if (!e.getMethodName().equals(methodName)) continue;
   element = e;
   break;
  }
  AClass.sendGlobalError(element.toString(), "", msg, value);
 }
//---------------------------------------------------------------------------
 public AException(Exception ex)
 {
  super(ex.getMessage(), ex);
  this.value = null;
  StackTraceElement element = null;
  String methodName = "UNKNOW";
  if (this.getStackTrace().length > 0)
  {
   element = this.getStackTrace()[0];
   if (element != null) methodName = element.getMethodName();
  }
  if (ex == null)
  {
   AClass.sendGlobalError(element.toString(), "", ex.getMessage() + " and ex=null", null);
   return;
  }
  StackTraceElement[] elements = ex.getStackTrace();
  for (int q = 0; q < elements.length; q++)
  {
   StackTraceElement e = ex.getStackTrace()[q];
   if (!e.getMethodName().equals(methodName)) continue;
   element = e;
   break;
  }
  AClass.sendGlobalError(element.toString(), "", ex.getMessage(), null);
 }
//---------------------------------------------------------------------------
 public AException(Exception ex, int value)
 {
  super(ex.getMessage(), ex);
  this.value = value;
  StackTraceElement element = this.getStackTrace()[0];
  String methodName = element.getMethodName();
  StackTraceElement[] elements = ex.getStackTrace();
  for (int q = 0; q < elements.length; q++)
  {
   StackTraceElement e = ex.getStackTrace()[q];
   if (!e.getMethodName().equals(methodName)) continue;
   element = e;
   break;
  }
  AClass.sendGlobalError(element.toString(), "", ex.getMessage(), value);
 }
//---------------------------------------------------------------------------
// @Deprecated
// public AException(AClass aclass, String fnName, String msg)
// {
//  super(msg == null ? nullMessage : msg);
//  if (msg == null) msg = nullMessage;
//  this.value = null;
////  aclass.sendError(this.getStackTrace()[0].getMethodName(),msg,null);
//  StackTraceElement element = this.getStackTrace()[0];
//  AClass.sendGlobalError(element.toString(), "", msg, null);
// }
//---------------------------------------------------------------------------
// @Deprecated
// public AException(AClass aclass, String fnName, String msg, int value)
// {
//  super(msg == null ? nullMessage : msg);
//  if (msg == null) msg = nullMessage;
//  this.value = value;
////  aclass.sendError(fnName,msg,value);
//  StackTraceElement element = this.getStackTrace()[0];
//  AClass.sendGlobalError(element.toString(), "", msg, value);
// }
//---------------------------------------------------------------------------
// @Deprecated
// public AException(String className, String fnName, String msg)
// {
//  super(msg == null ? nullMessage : msg);
//  if (msg == null) msg = nullMessage;
//  this.value = null;
////  AClass.sendGlobalError(className,fnName,msg,null);
//  StackTraceElement element = this.getStackTrace()[0];
//  AClass.sendGlobalError(element.toString(), "", msg, null);
// }
//---------------------------------------------------------------------------
// @Deprecated
// public AException(String className, String fnName, String msg, int value)
// {
//  super(msg == null ? nullMessage : msg);
//  if (msg == null) msg = nullMessage;
//  this.value = value;
////  AClass.sendGlobalError(className,fnName,msg,value);
//  StackTraceElement element = this.getStackTrace()[0];
//  AClass.sendGlobalError(element.toString(), "", msg, value);
// }
//---------------------------------------------------------------------------
}
