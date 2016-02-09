/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 * Суперкласс для классов генерирующих сообщения с иерархией классов.<br />
 * @author Anatol
 * @version 0.1.0.2
 */
public class AClass
{
//---------------------------------------------------------------------------
 public static ALog Log=new ALog();
//---------------------------------------------------------------------------
 private AClass FParent;
//---------------------------------------------------------------------------
/**
  * Инициализация данных.<br />
  */
 private void initialize()                                              //!!!
 {
//  Log.setParent(this);
  FParent=null;
 }
//---------------------------------------------------------------------------
 /**
  * Конструктор.<br />
  */
 public AClass()
 {
  initialize();
 }
//---------------------------------------------------------------------------
/**
  * Конструктор присвоения.<br />
  * @param value - экземпляр класса AClass, данные которого будут скопированны.
  */
 public AClass(AClass value)
 {
  initialize();
  assign(value);
 }
//---------------------------------------------------------------------------
/**
  * Присвоение данных.<br />
  * @param value - экземпряр класса.
  */
 public final void assign(final AClass value)
 {
  if(value==null)
  {
   sendError("assign","value=null",0);
   return;
  }
  FParent=value.FParent;
 }
//---------------------------------------------------------------------------
/**
  * Генерирует строку со стеком имён классов родителей.<br />
  * @return строка со стеком имён.
  */
 public final String classNameStack()
 {
  String value="";
  if(FParent!=null)
  {
   value+=FParent.classNameStack();
//   value+=":"+getClass().getSimpleName();
   value+=":"+getClass().getName();
  }
//  else value+=getClass().getSimpleName();
  else value+=getClass().getName();
  return value;
 }
//---------------------------------------------------------------------------
/**
  * Отправляет информационное сообщение runtime
  * с указанием стека имён классов родителей.<br />
  * <br /><b>Пример:</b> {@code sendInfo("fnName","msg",null);}
  * @param fnName - имя функции генерирующей сообщение.
  * @param msg - текст сообщения.
  * @param Value - целочисленное значение.
  */
 protected final void sendInfo(String fnName,String msg,Integer Value)
 {
  if(Value==null)Log.addInfo(classNameStack()+"."+fnName+"("+msg+")");
  else Log.addInfo(classNameStack()+"."+fnName+"("+msg+") "+Value);
//  if(Value==null)System.out.println(classNameStack()+"."+fnName+"("+msg+")");
//  else System.out.println(classNameStack()+"."+fnName+"("+msg+") "+Value);
 }
//---------------------------------------------------------------------------
/**
  * Отправляет сообщение об предупреждении runtime
  * с указанием стека имён классов родителей.<br />
  * <br /><b>Пример:</b> {@code sendError("fnName","msg",null);}
  * @param fnName - имя функции генерирующей сообщение.
  * @param msg - текст сообщения.
  * @param Value - целочисленное значение.
  */
 protected final void sendWarning(String fnName,String msg,Integer Value)
 {
  if(Value==null)Log.addWarning(classNameStack()+"."+fnName+"("+msg+")");
  else Log.addWarning(classNameStack()+"."+fnName+"("+msg+") "+Value);
//  if(Value==null)System.err.println(classNameStack()+"."+fnName+"("+msg+")");
//  else System.err.println(classNameStack()+"."+fnName+"("+msg+") "+Value);
 }
//---------------------------------------------------------------------------
/**
  * Отправляет сообщение об ошибке runtime
  * с указанием стека имён классов родителей.<br />
  * <br /><b>Пример:</b> {@code sendError("fnName","msg",null);}
  * @param fnName - имя функции генерирующей сообщение.
  * @param msg - текст сообщения.
  * @param Value - целочисленное значение.
  */
 protected final void sendError(String fnName,String msg,Integer Value)
 {
  if(Value==null)Log.addError(classNameStack()+"."+fnName+"("+msg+")");
  else Log.addError(classNameStack()+"."+fnName+"("+msg+") "+Value);
//  if(Value==null)System.err.println(classNameStack()+"."+fnName+"("+msg+")");
//  else System.err.println(classNameStack()+"."+fnName+"("+msg+") "+Value);
 }
//---------------------------------------------------------------------------
/**
  * Устанавливает указанный класс как класс родитель.<br />
  * <br /><b>Пример:</b> {@code element.setParent(this);}
  * @param value - класс родитель.
  */
 public final void setParent(AClass value)
 {
  FParent=value;
 }
//---------------------------------------------------------------------------
 protected boolean testRange(final int value,final int min,final int max,
         final String methodName,final String valueName)
 {
  if(value<min)
  {
   sendError(methodName,valueName+"<min",value);
   return false;
  }
  if(value>=max)
  {
   sendError(methodName,valueName+">=max",value);
   return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 protected boolean testNull(final Object value,final String methodName,final String valueName)
 {
  if(value==null)
  {
   sendError(methodName,valueName+"=null",null);
   return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
 protected boolean testReturnNull(final Object value,final String methodName,final String valueName)
 {
  if(value==null)
  {
   sendError(methodName,valueName+" return ERROR",null);
   return false;
  }
  return true;
 }
//---------------------------------------------------------------------------
/**
  * Отправляет информационное сообщение runtime
  * с указанием стека имён классов родителей.<br />
  * <br /><b>Пример:</b> {@code sendInfo("fnName","msg",null);}
  * @param fnName - имя функции генерирующей сообщение.
  * @param msg - текст сообщения.
  * @param Value - целочисленное значение.
  */
 public static void sendGlobalInfo(String fnName,String msg,Integer Value)
 {
  if(Value==null)Log.addInfo("Info."+fnName+"("+msg+")");
  else Log.addInfo("Info."+fnName+"("+msg+") "+Value);
 }
//---------------------------------------------------------------------------
/**
  * Отправляет сообщение об предупреждении runtime
  * с указанием стека имён классов родителей.<br />
  * <br /><b>Пример:</b> {@code sendError("fnName","msg",null);}
  * @param fnName - имя функции генерирующей сообщение.
  * @param msg - текст сообщения.
  * @param Value - целочисленное значение.
  */
 public static void sendGlobalWarning(String fnName,String msg,Integer Value)
 {
  if(Value==null)Log.addWarning("Warning."+fnName+"("+msg+")");
  else Log.addWarning("Warning."+fnName+"("+msg+") "+Value);
 }
//---------------------------------------------------------------------------
/**
  * Отправляет сообщение об ошибке runtime
  * с указанием стека имён классов родителей.<br />
  * <br /><b>Пример:</b> {@code sendError("fnName","msg",null);}
  * @param fnName - имя функции генерирующей сообщение.
  * @param msg - текст сообщения.
  * @param Value - целочисленное значение.
  */
 public static void sendGlobalError(String fnName,String msg,Integer Value)
 {
  if(Value==null)Log.addError("Error."+fnName+"("+msg+")");
  else Log.addError("Error."+fnName+"("+msg+") "+Value);
 }
//---------------------------------------------------------------------------
 public static void sendGlobalError(String className,String fnName,String msg,Integer Value)
 {
  if(Value==null)Log.addError(className+"."+fnName+"("+msg+")");
  else Log.addError(className+"."+fnName+"("+msg+") "+Value);
 }
//---------------------------------------------------------------------------
}
