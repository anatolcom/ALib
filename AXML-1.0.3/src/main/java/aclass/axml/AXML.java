/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.axml;

import aclass.AClass;
import aclass.AException;

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
}
