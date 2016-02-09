/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.xpath;

import aclass.AException;
import aclass.AFN;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Пользователь
 */
public class AXPath
{
//---------------------------------------------------------------------------
 public static Object evaluateExpression(String expression, Node source, NamespaceContext nsContexts, QName returnType) throws AException
 {
  if (expression == null) throw new AException("expression = null");
  if (source == null) throw new AException("source = null");
  if (returnType == null) throw new AException("returnType = null");
  try
  {
//   expression = expression.replace("@", "/attribute::");
   XPath xpath = XPathFactory.newInstance().newXPath();
   if (nsContexts != null) xpath.setNamespaceContext(nsContexts);
   XPathExpression exp = xpath.compile(expression);
   return exp.evaluate(source, returnType);
  }
  catch (XPathExpressionException ex)
  {
   if (ex.getCause() == null) throw new AException("expression \"" + expression + "\" " + ex.getMessage(), ex);
   else throw new AException("expression \"" + expression + "\" " + ex.getCause().getMessage(), ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Выборка одного узла в соответствии с XPath выражением expression относительно узла source.
  *
  * @param source узел, относительно которого производится поиск.
  * @param expression XPath выражение.
  * @param nsContexts нэймспэйс. может быть null.
  * @param required true - узел обязательно должен быть, иначе исключение. false - узла может не
  * быть, вернётся null.
  * @return найденный узел. Если required - false, то может вернутся null.
  * @throws AException
  */
 public static Node getNode(Node source, String expression, NamespaceContext nsContexts, boolean required) throws AException
 {
  Node node = (Node) evaluateExpression(expression, source, nsContexts, XPathConstants.NODE);
  if (node == null) if (required) throw new AException("Node with expression \"" + expression + "\" not found");
  return node;
 }
//---------------------------------------------------------------------------
 /**
  * Выборка одного узла в соответствии с XPath выражением expression относительно узла source.
  *
  * @param source узел, относительно которого производится поиск.
  * @param expression XPath выражение.
  * @param required true - узел обязательно должен быть, иначе исключение. false - узла может не
  * быть, вернётся null.
  * @return найденный узел. Если required - false, то может вернутся null.
  * @throws AException
  */
 public static Node getNode(Node source, String expression, boolean required) throws AException
 {
  return getNode(source, expression, null, required);
 }
//---------------------------------------------------------------------------
 /**
  * Выборка нескольких узлов в соответствии с XPath выражением expression относительно узла source.
  *
  * @param source узел, относительно которого производится поиск.
  * @param expression XPath выражение.
  * @param nsContexts нэймспэйс. может быть null.
  * @param required true - обязательно хотябы 1 узел в списке, иначе исключение. false - узлов может
  * не быть вовсе, вернётся пустой список.
  * @return список найденных узлов. Никогда не возвращает null, даже при required - false.
  * @throws AException
  */
 public static NodeList getNodeList(Node source, String expression, NamespaceContext nsContexts, boolean required) throws AException
 {
  NodeList nodeList = (NodeList) evaluateExpression(expression, source, nsContexts, XPathConstants.NODESET);
  if (nodeList.getLength() == 0) if (required) throw new AException("NodeList with expression \"" + expression + "\" not found");
  return nodeList;
 }
//---------------------------------------------------------------------------
 public static String getValue(Node source, String expression, NamespaceContext nsContexts, boolean required) throws AException
 {
  Node node = getNode(source, expression, nsContexts, required);
  if (node == null) return null;
  return node.getTextContent();
 }
//---------------------------------------------------------------------------
 public static String getValueAsString(Node source, String expression, NamespaceContext nsContexts, boolean required) throws AException
 {
  return getValue(source, expression, nsContexts, required);
 }
//---------------------------------------------------------------------------
 public static Integer getValueAsInteger(Node source, String expression, NamespaceContext nsContexts, boolean required) throws AException
 {
  String value = getValue(source, expression, nsContexts, required);
  try
  {
   if (value == null) return null;
   return Integer.valueOf(value);
  }
  catch (NumberFormatException ex)
  {
   throw new AException("value \"" + value + "\" is not a Integer");
  }
 }
//---------------------------------------------------------------------------
 public static Long getValueAsLong(Node source, String expression, NamespaceContext nsContexts, boolean required) throws AException
 {
  String value = getValue(source, expression, nsContexts, required);
  try
  {
   if (value == null) return null;
   return Long.valueOf(value);
  }
  catch (NumberFormatException ex)
  {
   throw new AException("value \"" + value + "\" is not a Long");
  }
 }
//---------------------------------------------------------------------------
 public static Double getValueAsDouble(Node source, String expression, NamespaceContext nsContexts, boolean required) throws AException
 {
  String value = getValue(source, expression, nsContexts, required);
  try
  {
   if (value == null) return null;
   return Double.valueOf(value);
  }
  catch (NumberFormatException ex)
  {
   throw new AException("value \"" + value + "\" is not a Double");
  }
 }
//---------------------------------------------------------------------------
 public static Date getValueAsDate(Node source, String expression, NamespaceContext nsContexts, boolean required, String format) throws AException
 {
  String value = getValue(source, expression, nsContexts, required);
  try
  {
   if (value == null) return null;
   return AFN.dateFromFormat(value, format);
  }
  catch (AException ex)
  {
   throw new AException("value \"" + value + "\" is not a Date");
  }
 }
//---------------------------------------------------------------------------
// public static String getValue(Node source, String expression, boolean required) throws AException
// {
//  return getValue(source, expression, null, required);
// }
//---------------------------------------------------------------------------
 public static void setValue(Node source, String expression, String value, NamespaceContext nsContexts, boolean required) throws AException
 {
  Node node = getNode(source, expression, nsContexts, required);
  if (node == null) return;
  node.setTextContent(value);
 }
//---------------------------------------------------------------------------
 public static void setValue(Node source, String expression, String value, boolean required) throws AException
 {
  setValue(source, expression, value, null, required);
 }
//---------------------------------------------------------------------------
 public static Document setValues(Document doc, NamespaceContext nsContexts, List<PathValue> list, boolean required) throws AException
 {
  for (PathValue item : list) setValue(doc, item.xpath, item.value, nsContexts, required);
  return doc;
 }
//---------------------------------------------------------------------------
 public static Node setValues(Node source, NamespaceContext nsContexts, List<PathValue> list, boolean required) throws AException
 {
  for (PathValue item : list) setValue(source, item.xpath, item.value, nsContexts, required);
  return source;
 }
//---------------------------------------------------------------------------
}
