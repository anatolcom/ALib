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
 public AException(String msg)
 {
  super(msg == null ? nullMessage : msg);
  if (msg == null) msg = nullMessage;
  StackTraceElement element = this.getStackTrace()[0];
  AClass.errorEx(element.toString(), "", msg);
 }
//---------------------------------------------------------------------------
 public AException(String msg, Exception ex)
 {
  super(msg == null ? nullMessage : msg, ex);
  if (msg == null) msg = nullMessage;
  StackTraceElement element = this.getStackTrace()[0];
  String methodName = element.getMethodName();
  if (ex == null)
  {
   AClass.errorEx(element.toString(), "", msg + " and ex = null");
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
  AClass.errorEx(element.toString(), "", msg);
 }
//---------------------------------------------------------------------------
 public AException(Exception ex)
 {
  super(ex == null ? "ex == null" : ex.getMessage(), ex);
  StackTraceElement element = null;
  String methodName = "UNKNOW";
  if (this.getStackTrace().length > 0)
  {
   element = this.getStackTrace()[0];
   if (element != null) methodName = element.getMethodName();
  }
  if (ex == null)
  {
   AClass.errorEx(String.valueOf(element), "", "ex = null");
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
  AClass.errorEx(String.valueOf(element), "", ex.getMessage());
 }
//---------------------------------------------------------------------------
}
