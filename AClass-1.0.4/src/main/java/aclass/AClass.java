/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Суперкласс для классов генерирующих сообщения с иерархией классов.<br />
 *
 * @author Anatol
 * @version 0.1.0.5
 */
public class AClass
{
//---------------------------------------------------------------------------
    public static ALog log = ALog.getInstance();
//---------------------------------------------------------------------------
    private AClass FParent = null;
//---------------------------------------------------------------------------
    /**
     * Конструктор.<br />
     */
    public AClass()
    {
    }
//---------------------------------------------------------------------------
    /**
     * Генерирует строку со стеком имён классов родителей.<br />
     *
     * @return строка со стеком имён.
     */
    public final String classNameStack()
    {
        String value = "";
        if (FParent != null)
        {
            value += FParent.classNameStack();
//            value += ":" + getClass().getSimpleName();
            value += ":" + getClass().getName();
        }
//        else value += getClass().getSimpleName();
        else value += getClass().getName();
        return value;
    }
//---------------------------------------------------------------------------
    /**
     * Отправляет информационное сообщение runtime с указанием стека имён классов родителей.<br />
     * <br /><b>Пример:</b> {@code sendInfo("fnName","msg",null);}
     *
     * @param fnName - имя функции генерирующей сообщение.
     * @param msg - текст сообщения.
     */
    protected final void sendInfo(String fnName, String msg)
    {
        try
        {
            log.addInfo(classNameStack(), fnName, msg);
        }
        catch (Exception ex)
        {
            System.err.println("sendInfo exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
    /**
     * Отправляет сообщение об предупреждении runtime с указанием стека имён классов
     * родителей.<br />
     * <br /><b>Пример:</b> {@code sendError("fnName", "msg");}
     *
     * @param fnName - имя функции генерирующей сообщение.
     * @param msg - текст сообщения.
     */
    protected final void sendWarning(String fnName, String msg)
    {
        try
        {
            log.addWarning(classNameStack(), fnName, msg);
        }
        catch (Exception ex)
        {
            System.err.println("sendInfo exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
    /**
     * Отправляет сообщение об ошибке runtime с указанием стека имён классов родителей.<br />
     * <br /><b>Пример:</b> {@code sendError("fnName", "msg");}
     *
     * @param fnName - имя функции генерирующей сообщение.
     * @param msg - текст сообщения.
     */
    protected final void sendError(String fnName, String msg)
    {
        try
        {
            log.addError(classNameStack(), fnName, msg);
        }
        catch (Exception ex)
        {
            System.err.println("sendInfo exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
    /**
     * Устанавливает указанный класс как класс родитель.<br />
     * <br /><b>Пример:</b> {@code element.setParent(this);}
     *
     * @param value - класс родитель.
     */
    public final void setParent(AClass value)
    {
        FParent = value;
    }
//---------------------------------------------------------------------------
    protected boolean inRange(final int value, final int min, final int max)
    {
        return min <= value && value < max;
    }
//---------------------------------------------------------------------------
    protected boolean testRange(final int value, final int min, final int max,
            final String methodName, final String valueName)
    {
        if (value < min)
        {
            sendError(methodName, valueName + ":" + value + " < min:" + min);
            return false;
        }
        if (value >= max)
        {
            sendError(methodName, valueName + ":" + value + " >= max:" + max);
            return false;
        }
        return true;
    }
//---------------------------------------------------------------------------
    protected boolean testNull(final Object value, final String methodName, final String valueName)
    {
        if (value == null)
        {
            sendError(methodName, valueName + " = null");
            return false;
        }
        return true;
    }
//---------------------------------------------------------------------------
    protected boolean testReturnNull(final Object value, final String methodName, final String valueName)
    {
        if (value == null)
        {
            sendError(methodName, valueName + " return ERROR");
            return false;
        }
        return true;
    }
//---------------------------------------------------------------------------
    /**
     * Отправляет информационное сообщение runtime с указанием стека имён классов родителей.<br />
     * <br /><b>Пример:</b> {@code sendInfo("fnName", "msg");}
     *
     * @param fnName - имя функции генерирующей сообщение.
     * @param msg - текст сообщения.
     */
    public static void info(String fnName, String msg)
    {
        try
        {
            log.addInfo(null, fnName, msg);
        }
        catch (Exception ex)
        {
            System.err.println("sendGlobalInfo exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
    /**
     * Отправляет сообщение об предупреждении runtime с указанием стека имён классов
     * родителей.<br />
     * <br /><b>Пример:</b> {@code sendError("fnName", "msg");}
     *
     * @param fnName - имя функции генерирующей сообщение.
     * @param msg - текст сообщения.
     */
    public static void warning(String fnName, String msg)
    {
        try
        {
            log.addWarning(null, fnName, msg);
        }
        catch (Exception ex)
        {
            System.err.println("sendGlobalWarning exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
    /**
     * Отправляет сообщение об ошибке runtime с указанием стека имён классов родителей.<br />
     * <br /><b>Пример:</b> {@code sendError("fnName", "msg");}
     *
     * @param fnName - имя функции генерирующей сообщение.
     * @param msg - текст сообщения.
     */
    public static void error(String fnName, String msg)
    {
        try
        {
            log.addError(null, fnName, msg);
        }
        catch (Exception ex)
        {
            System.err.println("sendGlobalError exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
    public static void errorEx(String className, String fnName, String msg)
    {
        try
        {
            log.addError(className, fnName, msg);
        }
        catch (Exception ex)
        {
            System.err.println("error exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
    public static void stackTrace(String fnName, Exception exception)
    {
        try
        {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(new ByteArrayOutputStream());
            exception.printStackTrace(ps);
            ps.close();
            log.addError(null, fnName, byteBuffer.toString());
        }
        catch (Exception ex)
        {
            System.err.println("error exception: " + ex.getMessage());
        }
    }
//---------------------------------------------------------------------------
}
