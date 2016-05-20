/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс описывающий ведение Log.
 *
 * @author Anatol
 * @version 0.1.0.4
 */
public class ALog extends AClass
{
//---------------------------------------------------------------------------
    public enum LogType
    {
        Info("I"),
        Warning("W"),
        Error("E");
        public final String value;
        LogType(String value)
        {
            this.value = value;
        }
        public static LogType fromValue(String value)
        {
            if (value == null) throw new IllegalArgumentException("value = null");
            for (LogType type : LogType.values()) if (value.equals(type.value)) return type;
            throw new IllegalArgumentException("unknow type: \"" + value + "\"");
        }
        @Override
        public String toString()
        {
            return value;
        }
    }
//---------------------------------------------------------------------------
    public interface Forvarding
    {
        public void log(LogItem item) throws Exception;
    }
//---------------------------------------------------------------------------
    private static ALog instance = null;
//---------------------------------------------------------------------------
    private ALog()
    {
    }
//---------------------------------------------------------------------------
    public static ALog getInstance()
    {
        if (instance == null) instance = new ALog();
        return instance;
    }
//---------------------------------------------------------------------------
    public class LogItem
    {
        public final LogType type;
        public final String text;
        public final Date date;
        public LogItem(LogType type, String text, Date date)
        {
            this.type = type;
            this.text = text;
            this.date = date;
        }
        @Override
        public String toString()
        {
            return type + " " + text;
        }
        public String toStringWithDate()
        {
            return type + " " + AFN.dateToIsoFormat(date) + " " + text;
        }
    }
//---------------------------------------------------------------------------
    public class ExLogItem
    {
        public final LogType type;
        public final String className;
        public final String functionName;
        public final String message;
        public final Date date;
        public ExLogItem(LogType type, String className, String functionName, String message, Date date)
        {
            this.type = type;
            this.className = className;
            this.functionName = functionName;
            this.message = message;
            this.date = date;
        }
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(type.value).append(" ");
            if (className == null) sb.append("...");
            else sb.append(className).append(".");
            if (functionName != null) sb.append(functionName);
            sb.append("(").append(message).append(")");
            return sb.toString();
        }
        public String toStringWithDate()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(type.value).append(" ");
            sb.append(AFN.dateToIsoFormat(date)).append(" ");
            if (className == null) sb.append("...");
            else sb.append(className).append(".");
            if (functionName != null) sb.append(functionName);
            sb.append("(").append(message).append(")");
            return sb.toString();
        }
    }
//---------------------------------------------------------------------------
    private final List<LogItem> logItems = new LinkedList();
    private final CopyOnWriteArrayList<ExLogItem> items = new CopyOnWriteArrayList();
//---------------------------------------------------------------------------
    private int itemLimit = 1024;
    private String path = "";
    private String autoSaveFileName = "";
//---------------------------------------------------------------------------
    Forvarding forvarding;
//---------------------------------------------------------------------------
    private synchronized void trimItemList()
    {
        if (itemLimit == 0) return;
        while (logItems.size() > itemLimit) logItems.remove(0);
    }
//---------------------------------------------------------------------------
    public synchronized int count()
    {
        return logItems.size();
    }
//---------------------------------------------------------------------------
    public Forvarding getForvarding()
    {
        return forvarding;
    }
//---------------------------------------------------------------------------
    public void setForvarding(Forvarding forvarding)
    {
        this.forvarding = forvarding;
    }
//---------------------------------------------------------------------------
    public synchronized void addInfo(String text) throws Exception
    {
        if (text == null) throw new Exception("text = null");
        LogItem item = new LogItem(LogType.Info, text, new Date());
        add(item);
    }
//---------------------------------------------------------------------------
    public void addWarning(String text) throws Exception
    {
        if (text == null) throw new Exception("text = null");
        LogItem item = new LogItem(LogType.Warning, text, new Date());
        add(item);
    }
//---------------------------------------------------------------------------
    public void addError(String text) throws Exception
    {
        if (text == null) throw new Exception("text = null");
        LogItem item = new LogItem(LogType.Error, text, new Date());
        add(item);
    }
//---------------------------------------------------------------------------
    public synchronized void addInfo(String className, String functionName, String message) throws Exception
    {
        if (message == null) throw new Exception("msg = null");
        ExLogItem item = new ExLogItem(LogType.Info, className, functionName, message, new Date());
        add(item);
    }
//---------------------------------------------------------------------------
    public synchronized void addWarning(String className, String functionName, String message) throws Exception
    {
        if (message == null) throw new Exception("msg = null");
        ExLogItem item = new ExLogItem(LogType.Warning, className, functionName, message, new Date());
        add(item);
    }
//---------------------------------------------------------------------------
    public synchronized void addError(String className, String functionName, String message) throws Exception
    {
        if (message == null) throw new Exception("msg = null");
        ExLogItem item = new ExLogItem(LogType.Error, className, functionName, message, new Date());
        add(item);
    }
//---------------------------------------------------------------------------
    private final Logger logger = Logger.getLogger(ALog.class.getName());
//---------------------------------------------------------------------------
    public synchronized void add(LogItem item) throws Exception
    {
        if (item == null) throw new Exception("item = null");
        logItems.add(item);
        trimItemList();
        if (forvarding != null)
        {
            try
            {
                forvarding.log(item);
            }
            catch (Exception ex)
            {
                System.out.println(item.toStringWithDate());
            }
        }
        else
        {
            switch (item.type)
            {
                case Info:
                    System.out.println(item.toStringWithDate());
//                    logger.info(item.toString());
                    logger.logp(Level.INFO, "..", "method", item.text);
                    break;
                case Warning:
                    System.out.println(item.toStringWithDate());
//                    logger.warning(item.toString());
                    logger.logp(Level.WARNING, "..", "method", item.text);
                    break;
                case Error:
                    System.err.println(item.toStringWithDate());
//                    logger.severe(item.toString());
                    logger.logp(Level.SEVERE, "..", "method", item.text);
                    break;
            }

//            System.out.println(item.toStringWithDate());
        }
        updated();
    }
//---------------------------------------------------------------------------
    public synchronized void add(ExLogItem item) throws Exception
    {
        if (item == null) throw new Exception("item = null");
        items.add(item);
        trimItemList();
        Level leval = Level.ALL;
        switch (item.type)
        {
            case Info:
                leval = Level.INFO;
                break;
            case Warning:
                leval = Level.WARNING;
                break;
            case Error:
                leval = Level.SEVERE;
                break;
        }
        if (item.className == null) logger.logp(leval, "..", item.functionName, item.message);
        else logger.logp(leval, item.className, item.functionName, item.message);
    }
//---------------------------------------------------------------------------
    private synchronized void updated() throws Exception
    {
        if (autoSaveFileName.isEmpty()) return;
        saveToFile(autoSaveFileName);
    }
//---------------------------------------------------------------------------
    public synchronized String get(int index) throws Exception
    {
        if (index < 0) throw new Exception("index < 0 : " + index);
        if (index >= logItems.size()) throw new Exception("index >= " + logItems.size() + " : " + index);
        return logItems.get(index).toString();
    }
//---------------------------------------------------------------------------
    public synchronized LogItem getLogItem(int index) throws Exception
    {
        if (index < 0) throw new Exception("index < 0 : " + index);
        if (index >= logItems.size()) throw new Exception("index >= " + logItems.size() + " : " + index);
        return logItems.get(index);
    }
//---------------------------------------------------------------------------
    /**
     * Метод записывает данные в поток Out.<br/>
     *
     * @param out - Выходной поток.
     * @return true - выполнено,<br/>
     * false - не выполнено.
     * @throws java.lang.Exception
     */
    public boolean write(OutputStream out) throws Exception
    {
        if (out == null) throw new Exception("out = null");
        PrintWriter Wrtr = new PrintWriter(out);
        StringBuilder str = new StringBuilder();
        for (LogItem logItem : logItems)
        {
            if (str.length() != 0) str.append((char) 13).append((char) 10);
            str.append(logItem.toStringWithDate());
        }
        Wrtr.write(str.toString());
        Wrtr.flush();
        return true;
    }
//---------------------------------------------------------------------------
    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        String enter = "";
        enter += (char) 13;
        enter += (char) 10;
        for (LogItem logItem : logItems)
        {
            if (str.length() != 0) str.append(enter);
            str.append(logItem.toString());
        }
        return str.toString();
    }
//---------------------------------------------------------------------------
    public void saveToFile(String fileName) throws Exception
    {
        try
        {
            File dir = new File(path);
            if (!dir.exists()) if (!dir.mkdirs()) throw new Exception("mkdir ERROR Log have not been saved");
            File file = new File(path, fileName);
            OutputStream fileOut = new FileOutputStream(file);
            write(fileOut);
            fileOut.close();
        }
        catch (FileNotFoundException ex)
        {
            throw new Exception("file not found");
        }
        catch (IOException ex)
        {
            throw new Exception(ex);
        }
    }
//---------------------------------------------------------------------------
    public String getAutoSave()
    {
        return autoSaveFileName;
    }
    public void setAutoSave(String fileName)
    {
        this.autoSaveFileName = fileName;
    }
    public void breakAutoSave()
    {
        this.autoSaveFileName = "";
    }
//---------------------------------------------------------------------------
    public String getPath()
    {
        return path;
    }
    public void setPath(String path)
    {
        this.path = path;
    }
//---------------------------------------------------------------------------
    public int getItemLimit()
    {
        return itemLimit;
    }
    public void setItemLimit(int limint)
    {
        this.itemLimit = limint;
    }
//---------------------------------------------------------------------------
//    public static String getCurrentDateStd()
//    {
//        return AFN.dateToIsoFormat(new Date());
//    }
//---------------------------------------------------------------------------
}
