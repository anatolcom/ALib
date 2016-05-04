/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXB;

/**
 *
 * @author anatol
 */
public class AFN
{
    //---------------------------------------------------------------------------
    public static final String DATE_TIME_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_TIME_XML_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String DATE_TIME_RU_FORMAT = "dd.MM.yyyy HH:mm:ss";
    //---------------------------------------------------------------------------
    public static java.util.Date currentDate()
    {
//  java.util.Calendar cal = java.util.Calendar.getInstance();
//  java.util.Date date = new java.util.Date();
//  date.setTime(cal.getTimeInMillis());
//  return date;
        return new java.util.Date();
    }
//---------------------------------------------------------------------------
    public static String dateToFormat(java.util.Date date, String format)
    {
        if (date == null) throw new IllegalArgumentException("date = null");
        if (format == null) throw new IllegalArgumentException("format = null");
//  return new java.text.SimpleDateFormat(format, java.util.Locale.getDefault()).format(date);
        return new java.text.SimpleDateFormat(format).format(date);
    }
//---------------------------------------------------------------------------
    public static String dateToIsoFormat(java.util.Date date)
    {
        return dateToFormat(date, DATE_TIME_ISO_FORMAT);
    }
//---------------------------------------------------------------------------
    public static String dateToRuFormat(java.util.Date date)
    {
        return dateToFormat(date, DATE_TIME_RU_FORMAT);
    }
//---------------------------------------------------------------------------
    public static java.util.Date dateFromFormat(String value, String format) throws AException
    {
        if (value == null) throw new IllegalArgumentException("value = null");
        if (format == null) throw new IllegalArgumentException("format = null");
        try
        {
//   java.util.SimpleTimeZone timezone = new java.util.SimpleTimeZone(4 * 3600000, "UTC+4");//Europe/Moscow UTC=GTM 14400000
//   java.util.SimpleTimeZone timezone = new java.util.SimpleTimeZone(3 * 3600000, "UTC+3");//Europe/Moscow UTC=GTM 14400000
            final java.text.SimpleDateFormat DF = new java.text.SimpleDateFormat(format);
//   DF.setTimeZone(timezone);
            java.util.Date date = new java.util.Date(DF.parse(value).getTime());
            return date;
        }
        catch (java.text.ParseException ex)
        {
            throw new AException("value \"" + value + "\" can not by parse as date with format: " + format, ex);
        }
    }
//---------------------------------------------------------------------------
    public static java.util.Date dateFromIsoFormat(String value) throws AException
    {
        return dateFromFormat(value, DATE_TIME_ISO_FORMAT);
    }
//---------------------------------------------------------------------------
    public static java.util.Date dateFromRuFormat(String value) throws AException
    {
        return dateFromFormat(value, DATE_TIME_RU_FORMAT);
    }
//---------------------------------------------------------------------------

    public static byte[] marshalToBytes(Object object) throws AException
    {
        if (object == null) throw new IllegalArgumentException("object = null");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStream bufOStream = new BufferedOutputStream(out);
        try
        {
            JAXB.marshal(object, out);
            bufOStream.flush();
        }
        catch (IOException ex)
        {
            throw new AException(ex);
        }
        return out.toByteArray();
    }
//---------------------------------------------------------------------------
    public static String marshalToString(Object object) throws AException
    {
        try
        {
            return new String(marshalToBytes(object), "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    public static <T extends Object> T unmarshalFromBytes(byte[] data, Class<T> type) throws AException
    {
        if (data == null) throw new IllegalArgumentException("data = null");
        if (type == null) throw new IllegalArgumentException("type = null");
        try
        {
            InputStream in = AIO.openInputStreamFromBytes(data);
            T t = JAXB.unmarshal(in, type);
            in.close();
            return t;
        }
        catch (IOException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    public static <T extends Object> T unmarshalFromString(String data, Class<T> type) throws AException
    {
        if (data == null) throw new IllegalArgumentException("data = null");
        try
        {
            return unmarshalFromBytes(data.getBytes("UTF-8"), type);
        }
        catch (UnsupportedEncodingException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
}
