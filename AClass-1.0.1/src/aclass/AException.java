/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 *
 * @author Пользователь
 * @version 0.1.0.1
 */
public class AException extends Exception
{
//---------------------------------------------------------------------------
 public AException(String msg)
 {
  super(msg);
//  AClass.sendGlobalError(element.getClassName(),element.getMethodName(),msg,null);
  StackTraceElement element=this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(),"",msg,null);
 }
//---------------------------------------------------------------------------
 public AException(String msg,int value)
 {
  super(msg);
//  AClass.sendGlobalError(element.getClassName(),element.getMethodName(),msg,null);
  StackTraceElement element=this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(),"",msg,value);
 }
//---------------------------------------------------------------------------
 public AException(String msg,Exception ex)
 {
  super(msg,ex);
  StackTraceElement element=this.getStackTrace()[0];
  String methodName=element.getMethodName();
  StackTraceElement[] elements=ex.getStackTrace();
  for(int q=0;q<elements.length;q++)
  {
   StackTraceElement e=ex.getStackTrace()[q];
   if(!e.getMethodName().equals(methodName))continue;
   element=e;
  } 
  AClass.sendGlobalError(element.toString(),"",msg,null);
 }
//---------------------------------------------------------------------------
 public AException(String msg,Exception ex,int value)
 {
  super(msg,ex);
  StackTraceElement element=this.getStackTrace()[0];
  String methodName=element.getMethodName();
  StackTraceElement[] elements=ex.getStackTrace();
  for(int q=0;q<elements.length;q++)
  {
   StackTraceElement e=ex.getStackTrace()[q];
   if(!e.getMethodName().equals(methodName))continue;
   element=e;
  } 
  AClass.sendGlobalError(element.toString(),"",msg,value);
 }
//---------------------------------------------------------------------------
 public AException(Exception ex)
 {
  super(ex.getMessage(),ex);
  StackTraceElement element=this.getStackTrace()[0];
  String methodName=element.getMethodName();
  StackTraceElement[] elements=ex.getStackTrace();
  for(int q=0;q<elements.length;q++)
  {
   StackTraceElement e=ex.getStackTrace()[q];
   if(!e.getMethodName().equals(methodName))continue;
   element=e;
  } 
  AClass.sendGlobalError(element.toString(),"",ex.getMessage(),null);
 }
//---------------------------------------------------------------------------
 public AException(Exception ex,int value)
 {
  super(ex.getMessage(),ex);
  StackTraceElement element=this.getStackTrace()[0];
  String methodName=element.getMethodName();
  StackTraceElement[] elements=ex.getStackTrace();
  for(int q=0;q<elements.length;q++)
  {
   StackTraceElement e=ex.getStackTrace()[q];
   if(!e.getMethodName().equals(methodName))continue;
   element=e;
  } 
  AClass.sendGlobalError(element.toString(),"",ex.getMessage(),value);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public AException(AClass aclass,String fnName,String msg)
 {
  super(msg);
//  aclass.sendError(this.getStackTrace()[0].getMethodName(),msg,null);
  StackTraceElement element=this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(),"",msg,null);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public AException(AClass aclass,String fnName,String msg,int value)
 {
  super(msg);
//  aclass.sendError(fnName,msg,value);
  StackTraceElement element=this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(),"",msg,value);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public AException(String className,String fnName,String msg)
 {
  super(msg);
//  AClass.sendGlobalError(className,fnName,msg,null);
  StackTraceElement element=this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(),"",msg,null);
 }
//---------------------------------------------------------------------------
 @Deprecated
 public AException(String className,String fnName,String msg,int value)
 {
  super(msg);
//  AClass.sendGlobalError(className,fnName,msg,value);
  StackTraceElement element=this.getStackTrace()[0];
  AClass.sendGlobalError(element.toString(),"",msg,value);
 }
//---------------------------------------------------------------------------
}
