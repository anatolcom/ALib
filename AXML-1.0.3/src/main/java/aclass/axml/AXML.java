/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.axml;

import aclass.AClass;
import aclass.AException;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Anatol
 */
public class AXML
{
//---------------------------------------------------------------------------
    public static boolean isXML(String xml)
    {
        if (xml == null) return false;
        return xml.startsWith("<");
    }
//---------------------------------------------------------------------------
    public static String clearMetaData(String xml) throws AException
    {
        if (xml == null) throw new AException("xml = null");
        if (!xml.startsWith("<?xml")) return xml;
        int pos = xml.indexOf("?>");
        if (pos == -1) throw new AException("Expected \"?>\"");
        return xml.substring(pos + 2);
    }
//---------------------------------------------------------------------------
    public static String addMetaData(String xml, String version, String encoding, String standalone) throws AException
    {
        if (xml == null) throw new AException("xml = null");
        if (xml.startsWith("<?xml")) return xml;
        String meta = "<?xml";
        if (version != null) meta += " version=\"" + version + "\"";//1.0
        if (encoding != null) meta += " encoding=\"" + encoding + "\"";//UTF-8
        if (standalone != null) meta += " standalone=\"" + standalone + "\"";//yes no
        meta += "?>";
        return meta + xml;
    }
//---------------------------------------------------------------------------
    /**
     * Оборачивает данные в <![CDATA[data]]>
     *
     * @param data данные
     * @return <![CDATA[data]]>. если data = null, то null
     */
    public static String cdata(String data)
    {
        if (data == null) return null;
        return "<![CDATA[" + data + "]]>";
    }
//---------------------------------------------------------------------------
    /**
     * Приводит xml в строку, удаляя все переносы, табуляторы и лишние пробелы.
     *
     * @param xml xml
     * @return xml в строку. если xml = null, то null
     */
    public static String trim(String xml)
    {
        if (xml == null) return null;
        xml = xml.trim();
        xml = xml.replaceAll(">(\\s*)<", "><");
        xml = xml.replaceAll("\n", " ");
        xml = xml.replaceAll("\r", " ");
        xml = xml.replaceAll("\t", " ");
        xml = xml.replaceAll("  *", " ");
        xml = xml.replaceAll("> ", ">");
        xml = xml.replaceAll(" <", "<");
        return xml;
    }
//---------------------------------------------------------------------------
    /**
     * Метод конвертирует символ CHAR в спецсимвол.<br/>
     *
     * @param symbol - Символ.
     * @return Спецсимвол.
     */
    private static String charToEscape(final char symbol)
    {
        switch (symbol)
        {
            case '&': return "&amp;";
            case '<': return "&lt;";
            case '>': return "&gt;";
            case '\'': return "&apos;";
            case '"': return "&quot;";
        }
        return "&#" + (int) symbol + ";";
    }
//---------------------------------------------------------------------------
    private static char escapeToChar(final String escape) throws AException
    {
        if (escape == null) throw new AException("escape = null");
        if (escape.equals("&amp;")) return '&';
        if (escape.equals("&lt;")) return '<';
        if (escape.equals("&gt;")) return '>';
        if (escape.equals("&apos;")) return '\'';
        if (escape.equals("&quot;")) return '"';
        if (escape.startsWith("&#"))
        {
            return (char) Integer.valueOf(escape.substring(2, escape.length() - 1)).intValue();
        }
        throw new AException("unknow format escape \"" + escape + "\"");
    }
//---------------------------------------------------------------------------
    /**
     * Метод определяет нужно ли символ VALUE конвертировать в спецсимвол.<br/>
     *
     * @param symbol - Символ.
     * @return true - да,<br/>
     * false - нет.
     */
    private static boolean needConvertToEscape(final char symbol)
    {
        switch (symbol)
        {
            case '&': return true;
            case '<': return true;
            case '>': return true;
            case '\'': return true;
            case '"': return true;
        }
        return false;
    }
//---------------------------------------------------------------------------
    /**
     * Метод конвертирует строку STR в строку стандарта XML. Если FORMATED=true то форматирование
     * строки сохраняется.<br/>
     *
     * @param text - Строка.
     * @param formated - Сохраниение форматирования.
     * @return Строка стандарта XML.<br/>
     * null - в случае ошибки.
     * @throws aclass.AException
     */
    public static String strToXmlStr(String text, boolean formated) throws AException
    {
        if (text == null) throw new AException("text = null");
        if (text.isEmpty()) return "";

        String in = text;
        StringBuilder xmlstr = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < in.length(); i++)
        {
            char c = in.charAt(i);
            if (c < 32)
            {
                if (formated) xmlstr.append(charToEscape(c));
                continue;
            }
            if (c == 32)
            {
                if (!formated)
                {
                    if (first) xmlstr.append(" ");
                    first = false;
                }
                else xmlstr.append(charToEscape(c));
                continue;
            }
            if (c > 127)
            {
                xmlstr.append(charToEscape(c));
                continue;
            }
            first = true;
            if (needConvertToEscape(c))
            {
                xmlstr.append(charToEscape(c));
                continue;
            }
            xmlstr.append(c);
        }
        return xmlstr.toString();
    }
//---------------------------------------------------------------------------
    /**
     * Метод определяет является ли содержимое в тексте TEXT в позиции POS равным по значению
     * переносу строки.<br/>
     * В переменну Len.value записывается длинна идентификатора переноса строки.<br/>
     *
     * @param text - Текст для синтаксического анализа.
     * @param pos - Позиция сравнения.
     * @param Len - В переменную Len.value записывается длинна идентификатора переноса строки.
     * @return true - да,<br/>
     * false - нет.
     */
    private static boolean isEnter(final String text, final int pos) throws AException
    {
        if (text == null) throw new AException("TEXT = null");
        if (text.length() < pos + 2) return false;
        if (text.charAt(pos) != 13) return false;
        if (text.charAt(pos + 1) != 10) return false;
        return true;
    }
//---------------------------------------------------------------------------
    /**
     * Метод выбирает спецсимвол тега из текста TEXT начиная с позиции POS и возвращает его.<br/>
     *
     * @param text - Текст для синтаксического анализа.
     * @param pos - Позиция сравнения.
     * @param SpecSymbol - В переменную SpecSymbol.value записывается найденый спецсимвол.
     * @return найденый спецсимвол, Позиция после найденого спецсимвол равна pos + длинна
     * спецсимвола.<br/>
     * null - Если это не спецсимвол.
     */
//---------------------------------------------------------------------------
    private static String readEscape(final String text, int pos) throws AException
    {
        if (text == null) throw new AException("TEXT = null");
        final int len = text.length();
        if (pos >= len) throw new AException("pos: " + pos + " >= len");
        if (text.charAt(pos) != '&') return null;
        pos += 1;
        StringBuilder sb = new StringBuilder();
        sb.append("&");
        while (true)
        {
            if (pos >= len) throw new AException("Statement missing ;. " + pos);
            if (isEnter(text, pos)) throw new AException("Enter... Statement missing ;. " + pos);
            if (text.charAt(pos) == ';')
            {
                sb.append(";");
                break;
            }
            sb.append(text.charAt(pos));
            pos++;
        }
        return sb.toString();
    }
//---------------------------------------------------------------------------
    /**
     * Метод конвертирует строку стандарта XML XMLSTR в строку.<br/>
     *
     * @param xmlstr - Строка стандарта XML.
     * @return Строка.<br/>
     * null - в случае ошибки.
     */
    public static String xmlStrToStr(String xmlstr) throws AException
    {
        if (xmlstr == null) throw new AException("xmlstr = null");
        if (xmlstr.isEmpty()) return "";

//        StringEscapeUtils escapeUtils 
        final int len = xmlstr.length();
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (true)
        {
            if (pos >= len) break;
//            if (xmlstr.charAt(pos) == '&')
//            {
//                pos += 1;
//                ARefStr SpecSymbol = new ARefStr();
//                pos = getSpecSymbol(xmlstr, pos, SpecSymbol);
//                if (pos == -1) throw new AException("getSpecSymbol return ERROR");
//                Character Symbol = SpecSymbolToChar(SpecSymbol.value);
//                if (Symbol == null) throw new AException("SpecSymbolToChar return ERROR");
//                str += Symbol;
//                continue;
//            }
            String escape = readEscape(xmlstr, pos);
            if (escape != null)
            {
                pos += escape.length();
                sb.append(escapeToChar(escape));
                continue;
            }
            sb.append(xmlstr.charAt(pos++));
        }
        return sb.toString();
    }
//---------------------------------------------------------------------------
    public static Date xmlDateToDate(XMLGregorianCalendar gregorianCalendar) throws AException
    {
        if (gregorianCalendar == null) throw new AException("date = null");
        return gregorianCalendar.toGregorianCalendar().getTime();
    }
//--------------------------------------------------------------------------- 
    public static XMLGregorianCalendar dateToXmlDate(Date date) throws AException
    {
        if (date == null) throw new AException("date = null");
        try
        {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        }
        catch (DatatypeConfigurationException ex)
        {
            throw new AException(ex);
        }
    }
//--------------------------------------------------------------------------- 

}
