/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anatol.servlet;

import static aclass.AClass.warning;
import aclass.AException;
import aclass.AFN;
import aclass.AIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Вспомогательные функции для сервлетов
 *
 * @author Anatol
 */
public class SFN
{
//---------------------------------------------------------------------------
    public static final String HTML_UTF8 = "text/html;charset=UTF-8";
    public static final String JSON_UTF8 = "application/json;charset=UTF-8";
    public static final String XML_UTF8 = "text/xml;charset=UTF-8";
//---------------------------------------------------------------------------
    public static String getHeaderAsString(HttpServletRequest request, String name, boolean required) throws AException
    {
        String str = (String) request.getHeader(name);
        if (str == null)
        {
            if (required) throw new AException("header \"" + name + "\" not found");
            return null;
        }
        return str;
    }
//---------------------------------------------------------------------------
    public static Integer getHeaderAsInt(HttpServletRequest request, String name, boolean required) throws AException
    {
        String numberStr = (String) request.getHeader(name);
        if (numberStr == null)
        {
            if (required) throw new AException("header \"" + name + "\" not found");
            return null;
        }
        if (numberStr.isEmpty()) throw new AException("header \"" + name + "\" is empty");
        int number;
        try
        {
            number = Integer.parseInt(numberStr);
        }
        catch (NumberFormatException ex)
        {
            throw new AException("header \"" + name + "\" not digital: " + numberStr);
        }
        return number;
    }
//---------------------------------------------------------------------------
    public static Long getHeaderAsLong(HttpServletRequest request, String name, boolean required) throws AException
    {
        String numberStr = (String) request.getHeader(name);
        if (numberStr == null)
        {
            if (required) throw new AException("header \"" + name + "\" not found");
            return null;
        }
        if (numberStr.isEmpty()) throw new AException("header \"" + name + "\" is empty");
        long number;
        try
        {
            number = Long.parseLong(numberStr);
        }
        catch (NumberFormatException ex)
        {
            throw new AException("header \"" + name + "\" not digital: " + numberStr);
        }
        return number;
    }
//---------------------------------------------------------------------------
    public static String getParamAsString(HttpServletRequest request, String name, boolean required) throws AException
    {
        return getParamAsString(request, name, required, null);
    }
//---------------------------------------------------------------------------
    public static Integer getParamAsInt(HttpServletRequest request, String name, boolean required) throws AException
    {
        return getParamAsInt(request, name, required, null);
    }
//---------------------------------------------------------------------------
    public static Long getParamAsLong(HttpServletRequest request, String name, boolean required) throws AException
    {
        return getParamAsLong(request, name, required, null);
    }
//---------------------------------------------------------------------------
    public static Boolean getParamAsBoolean(HttpServletRequest request, String name, boolean required) throws AException
    {
        return getParamAsBoolean(request, name, required, null);
    }
//---------------------------------------------------------------------------
    public static Date getParamAsDate(HttpServletRequest request, String name, String format, boolean required) throws AException
    {
        return getParamAsDate(request, name, format, required, null);
    }
//---------------------------------------------------------------------------
    public static String getParamAsString(HttpServletRequest request, String name, boolean required, String alt) throws AException
    {
        String str = (String) request.getParameter(name);
        if (str == null)
        {
            if (required) throw new AException("param \"" + name + "\" not found");
            return alt;
        }
        return str;
    }
//---------------------------------------------------------------------------
    public static Integer getParamAsInt(HttpServletRequest request, String name, boolean required, Integer alt) throws AException
    {
        String numberStr = (String) request.getParameter(name);
        if (numberStr == null)
        {
            if (required) throw new AException("param \"" + name + "\" not found");
            return alt;
        }
        if (numberStr.isEmpty()) throw new AException("param \"" + name + "\" is empty");
        int number;
        try
        {
            number = Integer.parseInt(numberStr);
        }
        catch (NumberFormatException ex)
        {
            throw new AException("param \"" + name + "\" not digital: " + numberStr);
        }
        return number;
    }
//---------------------------------------------------------------------------
    public static Long getParamAsLong(HttpServletRequest request, String name, boolean required, Long alt) throws AException
    {
        String numberStr = (String) request.getParameter(name);
        if (numberStr == null)
        {
            if (required) throw new AException("param \"" + name + "\" not found");
            return alt;
        }
        if (numberStr.isEmpty())
        {
            if (required) throw new AException("param \"" + name + "\" is empty");
            return alt;
        }
        long number;
        try
        {
            number = Long.parseLong(numberStr);
        }
        catch (NumberFormatException ex)
        {
            throw new AException("param \"" + name + "\" not digital: " + numberStr);
        }
        return number;
    }
//---------------------------------------------------------------------------
    public static Boolean getParamAsBoolean(HttpServletRequest request, String name, boolean required, Boolean alt) throws AException
    {
        String boolStr = (String) request.getParameter(name);
        if (boolStr == null)
        {
            if (required) throw new AException("param \"" + name + "\" not found");
            return alt;
        }
        if (boolStr.isEmpty())
        {
            if (required) throw new AException("param \"" + name + "\" is empty");
            return alt;
        }
        if ("true".equals(boolStr)) return true;
        if ("false".equals(boolStr)) return false;
        throw new AException("param \"" + name + "\" not boolean: " + boolStr);
    }
//---------------------------------------------------------------------------
    public static Date getParamAsDate(HttpServletRequest request, String name, String format, boolean required, Date alt) throws AException
    {
        String dateStr = (String) request.getParameter(name);
        if (dateStr == null)
        {
            if (required) throw new AException("param \"" + name + "\" not found");
            return alt;
        }
        if (dateStr.isEmpty())
        {
            if (required) throw new AException("param \"" + name + "\" is empty");
            return alt;
        }
        if (dateStr.equals("null"))
        {
            if (required) throw new AException("param \"" + name + "\" equals null");
            return alt;
        }
        Date date;
        try
        {
            date = AFN.dateFromFormat(dateStr, format);
        }
        catch (AException ex)
        {
            throw new AException("param \"" + name + "\" not date, because " + ex.getMessage());
        }
        return date;
    }
//---------------------------------------------------------------------------
    public static void response(String type, HttpServletResponse response, String str) throws AException
    {
        try (PrintWriter out = response.getWriter())
        {
            response.setContentType(type);
            out.write(str);
        }
        catch (IOException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    public static void forward(HttpServletRequest request, HttpServletResponse response, String page) throws AException
    {
        if (request == null) throw new AException("request=null");
        if (response == null) throw new AException("response=null");
        if (page == null) throw new AException("page=null");
        try
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            if (dispatcher == null) throw new AException("page \"" + page + "\" not forwarding");
            dispatcher.forward(request, response);
        }
        catch (ServletException | IOException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    public static void download(HttpServletResponse response, String filename, byte filedata[]) throws AException
    {
        if (response == null) throw new AException("response == null");
        if (filename == null) throw new AException("filename == null");
        if (filedata == null) throw new AException("filedata == null");
        try
        {
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
            response.setHeader("Content-Length", Integer.toString(filedata.length));
//   response.setHeader("Content-MD5",fileData.md5);

            warning("downloadDocument", "Нет ожидания ответа клиента");

            try (ServletOutputStream out = response.getOutputStream())
            {
                out.write(filedata);
            }
            catch (Exception ex)
            {
                throw new AException("Cancel download " + ex.getMessage(), ex);
            }
        }
        catch (Exception ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    public static void download(HttpServletResponse response, String downloadName, String filename) throws AException
    {
        if (response == null) throw new AException("response == null");
        if (downloadName == null) throw new AException("downloadName == null");
        if (filename == null) throw new AException("filename == null");
        
        File file = new File(filename);
        if (!file.exists()) throw new AException("file \"" + filename + "\" not exists");
        if (!file.canRead()) throw new AException("file \"" + filename + "\" cannot read");
        try
        {

            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\";");
            response.setHeader("Content-Length", Long.toString(file.length()));
//            response.setHeader("Content-MD5",fileData.md5);
            
            warning("downloadDocument", "Нет ожидания ответа клиента");

            try (FileInputStream inputStream = new FileInputStream(file))
            {
                try (ServletOutputStream outputStream = response.getOutputStream())
                {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) != -1) outputStream.write(buf, 0, len);
                    outputStream.flush();
                }
            }
            catch (Exception ex)
            {
                throw new AException("Cancel download " + ex.getMessage(), ex);
            }
        }
        catch (Exception ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    /**
     * Функция считывает файл ресурса с именем name, адрес которого указывается относительно
     * раположения context если файл ненайден, а required = true, то вызывается исключение.<br/>
     *
     * @param context контекст сервлета
     * @param name имя файла
     * @param required обязательность файла
     * @return содержимое файла в виде бинарных данных
     * @throws AException в случае ошибки
     */
    public static byte[] readBytesFromResource(ServletContext context, String name, boolean required) throws AException
    {
        if (context == null) throw new AException("context = null");
        if (name == null) throw new AException("name = null");
        try
        {
            InputStream inputStream = context.getResourceAsStream(name);
            if (inputStream == null)
            {
                if (required) throw new AException("Resource \"" + name + "\" not found");
                return null;
            }
            byte[] buf = null;
            try
            {
                buf = AIO.readBytesFromInputStream(inputStream);
            }
            finally
            {
                inputStream.close();
            }
            return buf;
        }
        catch (IOException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    /**
     * Функция считывает файл ресурса с именем name, адрес которого указывается относительно
     * раположения context если файл ненайден, а required = true, то вызывается исключение.<br/>
     *
     * @param context контекст сервлета
     * @param name имя файла
     * @param required обязательность файла
     * @return содержимое файла ввиде строки
     * @throws AException в случае ошибки
     */
    public static String readStringFromResource(ServletContext context, String name, boolean required) throws AException
    {
        try
        {
            byte[] buf = readBytesFromResource(context, name, required);
            if (buf == null) return null;
            return new String(AIO.removeBOM(buf), "UTF-8");
        }
        catch (IOException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    public static String getHost(HttpServletRequest request)
    {
        String str = request.getHeader("host");
        if (str == null) return "";
        int pos = str.lastIndexOf(":");
        if (pos == -1) return str;
        return str.substring(0, pos);
    }
//---------------------------------------------------------------------------
    public static String getPort(HttpServletRequest request)
    {
        String str = request.getHeader("host");
        if (str == null) return "";
        int pos = str.lastIndexOf(":");
        if (pos == -1) return "";
        return str.substring(pos + 1);
    }
//---------------------------------------------------------------------------
}
