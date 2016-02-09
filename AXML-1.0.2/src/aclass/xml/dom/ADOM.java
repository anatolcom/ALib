/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.dom;

import aclass.AClass;
import aclass.AException;
import aclass.xml.xpath.Namespace;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Пользователь
 */
public class ADOM extends AClass
{
//---------------------------------------------------------------------------
 /**
  * Создаёт DocumentBuilder.<br/>
  *
  * @return экземпляр DocumentBuilder
  * @throws AException в случае ошибки
  */
 private static DocumentBuilder getBuilder() throws AException
 {
  try
  {
   DocumentBuilderFactory docBuilderfactory = DocumentBuilderFactory.newInstance();
   docBuilderfactory.setNamespaceAware(true);
   docBuilderfactory.setValidating(false);
   return docBuilderfactory.newDocumentBuilder();
  }
  catch (ParserConfigurationException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Воссоздаёт документ из потока in с данными XML.<br/>
  *
  * @param in поток с данными XML
  * @return документ с данными XML
  * @throws AException в случае ошибки
  */
 public static Document getDocument(InputStream in) throws AException
 {
  try
  {
   return getBuilder().parse(in);
  }
  catch (Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Воссоздаёт документ из байтового массива xml с данными XML.<br/>
  *
  * @param xml байтовый массив с данными XML
  * @return документ с данными XML
  * @throws AException в случае ошибки
  */
 public static Document getDocument(byte[] xml) throws AException
 {
  try
  {
   return getDocument(new ByteArrayInputStream(xml));
  }
  catch (Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Воссоздаёт документ из строки xml с данными XML.<br/>
  *
  * @param xml строка с данными XML в формате UTF-8
  * @return документ с данными XML
  * @throws AException в случае ошибки
  */
 public static Document getDocument(String xml) throws AException
 {
  try
  {
   return getDocument(xml.getBytes("UTF-8"));
  }
  catch (UnsupportedEncodingException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Создаёт пустой документ.<br/>
  *
  * @return пустой документ
  * @throws AException
  */
 public static Document createDocument() throws AException
 {
  return getBuilder().newDocument();
 }
//---------------------------------------------------------------------------
 public static String nodeToStr(Node node) throws AException
 {
  if (node == null) throw new AException("node = null");
  try
  {
   DOMSource domSource = new DOMSource(node);
   StringWriter writer = new StringWriter();
   StreamResult result = new StreamResult(writer);
   TransformerFactory tf = TransformerFactory.newInstance();
   Transformer transformer = tf.newTransformer();
   transformer.transform(domSource, result);
   return writer.toString();
  }
  catch (TransformerException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public static Node findNodeList(String nodeName, NodeList nodeList) throws AException
 {
  if (nodeName == null) throw new AException("nodeName = null");
  Node nodeFound = null;
  for (int q = 0; q < nodeList.getLength(); q++)
  {
   Node node = nodeList.item(q);
   if (node == null) return null;
   if (node.getNodeType() == Node.TEXT_NODE) continue;
   String localName = node.getLocalName();
   if (localName != null) if (localName.equals(nodeName)) return node;
   if (node.hasChildNodes()) nodeFound = findNodeList(nodeName, node.getChildNodes());
   if (nodeFound != null) break;
  }
  return nodeFound;
 }
//---------------------------------------------------------------------------
 public static Node findFirstNode(String nodeName, NodeList nodeList) throws AException
 {
  Node nodeFound = null;
  for (int q = 0; q < nodeList.getLength(); q++)
  {
   Node node = nodeList.item(q);
   if (node.getNodeType() == Node.TEXT_NODE) continue;
   if (node.getLocalName().equals(nodeName)) return node;
   if (node.hasChildNodes()) nodeFound = findNodeList(nodeName, node.getChildNodes());
   if (nodeFound != null) break;
  }
  return nodeFound;
 }
//---------------------------------------------------------------------------
 public static Node findLastNode(String nodeName, NodeList nodeList) throws AException
 {
  Node nodeFound = null;
  for (int q = 0; q < nodeList.getLength(); q++)
  {
   Node node = nodeList.item(q);
   if (node.getNodeType() == Node.TEXT_NODE) continue;
   if (node.getLocalName().equals(nodeName)) return node;
   if (node.hasChildNodes()) nodeFound = findNodeList(nodeName, node.getChildNodes());
   if (nodeFound != null) break;
  }
  return nodeFound;
 }
//---------------------------------------------------------------------------
 public static Node firstChild(Node source, boolean required) throws AException
 {
  if (source == null) throw new AException("source = null");
  if (source.hasChildNodes())
  {
   NodeList nodeList = source.getChildNodes();
   for (int q = 0; q < nodeList.getLength(); q++)
   {
    Node node = nodeList.item(q);
    if (node.getNodeType() == Node.ELEMENT_NODE) return node;
   }
  }
  if (required) throw new AException("source has not have child node");
  return null;
 }
//---------------------------------------------------------------------------
 public static Node getNode(Document doc, String nodeName) throws AException
 {
  return findFirstNode(nodeName, doc.getChildNodes());
 }
//---------------------------------------------------------------------------
 public static String getNodeValue(Document doc, String nodeName) throws AException
 {
//  sendGlobalWarning("getNodeValue","Function is under development",null);
  try
  {
   if (!doc.hasChildNodes()) return null;//???
   NodeList nodeList = doc.getChildNodes();
   Node node = findFirstNode(nodeName, nodeList);
   if (node == null) return null;
   return node.getTextContent();
  }
  catch (DOMException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public static String getNodeValue(Node rootNode, String nodeName) throws AException
 {
//  sendGlobalWarning("getNodeValue","Function is under development",null);
  try
  {
   if (!rootNode.hasChildNodes()) return null;//???
   NodeList nodeList = rootNode.getChildNodes();
   Node node = findFirstNode(nodeName, nodeList);
   if (node == null) return null;
   return node.getTextContent();
  }
  catch (DOMException ex)
  {
   throw new AException(nodeName + " not found because " + ex.getMessage(), ex);
  }
 }
//---------------------------------------------------------------------------
 public static List<Node> getNodeList(String nodeName, Node node) throws AException
 {
  sendGlobalWarning("getNodeList", "Function is under development");
  if (nodeName == null) throw new AException("nodeName = null");
  if (nodeName.isEmpty()) throw new AException("nodeName is empty");
  if (node == null) throw new AException("node = null");
  try
  {
   ArrayList<Node> nodes = new ArrayList<Node>();
   gettingNodeList(nodes, nodeName, node);
   return nodes;
  }
  catch (Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 private static void gettingNodeList(List<Node> nodes, String nodeName, Node node) throws AException
 {
  try
  {
//   if(node.getLocalName().equals(nodeName))nodes.add(node);
   if (!node.hasChildNodes()) return;
   NodeList nodeList = node.getChildNodes();
   for (int q = 0; q < nodeList.getLength(); q++)
   {
    Node subNode = nodeList.item(q);
    String name = subNode.getLocalName();
    if (node.getNodeType() != Node.ELEMENT_NODE) continue;
    if (name != null) if (name.equals(nodeName)) nodes.add(subNode);
    if (subNode.hasChildNodes()) gettingNodeList(nodes, nodeName, subNode);
   }
  }
  catch (Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public static List<String> getNodeValueList(Document doc, String nodeName) throws AException
 {
  sendGlobalWarning("getNodeValue", "Function is under development");
  try
  {
   NodeList nodeList = doc.getChildNodes();
   Node node = findNodeList(nodeName, nodeList);
   if (node == null) return null;
   return null;//node.getTextContent();
  }
  catch (DOMException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Получение значения атрибута с именем AttributeName в узле node.<br/>
  *
  * @param node узел, в котором ищится атрибут
  * @param AttributeName имя атрибута
  * @param required обязательность наличия атрибута
  * @return Значение или null если атрибут ненайден и не обязателен
  * @throws AException в случае если атрибут не найден но обязательно должен быть
  */
 public static String getAttribute(Node node, String AttributeName, boolean required) throws AException
 {
  if (node == null) throw new AException("node = null");
  if (AttributeName == null) throw new AException("AttributeName = null");
  for (int q = 0; q < node.getAttributes().getLength(); q++)
  {
   Node attribute = node.getAttributes().item(q);
   if (attribute == null) continue;
   if (!attribute.getLocalName().equals(AttributeName)) continue;
   return attribute.getNodeValue();
  }
  if (required) throw new AException("Attribute with name \"" + AttributeName + "\" not found");
  return null;
 }
//---------------------------------------------------------------------------
 /**
  * Установка значения value для атрибута с именем AttributeName в узле node.<br/>
  *
  * @param node узел, в котором ищится атрибут
  * @param AttributeName имя атрибута
  * @param value устанавливаемое значение
  * @param required обязательность наличия атрибута
  * @throws AException в случае если атрибут не найден но обязательно должен быть
  */
 public static void setAttribute(Node node, String AttributeName, String value, boolean required) throws AException
 {
  if (node == null) throw new AException("node = null");
  if (AttributeName == null) throw new AException("AttributeName = null");
  if (value == null) throw new AException("value = null");
  for (int q = 0; q < node.getAttributes().getLength(); q++)
  {
   Node attribute = node.getAttributes().item(q);
   if (attribute == null) continue;
   if (!attribute.getLocalName().equals(AttributeName)) continue;
   attribute.setNodeValue(value);
   return;
  }
  if (required) throw new AException("Attribute with name \"" + AttributeName + "\" not found");
 }
//---------------------------------------------------------------------------
 public static String printFormat(String xml, int indent) throws AException
 {
  if (xml == null) throw new AException("xml = null");
  try
  {
   Source xmlInput = new StreamSource(new StringReader(xml));
   StringWriter stringWriter = new StringWriter();
   StreamResult xmlOutput = new StreamResult(stringWriter);
   TransformerFactory transformerFactory = TransformerFactory.newInstance();
   transformerFactory.setAttribute("indent-number", indent);
   Transformer transformer = transformerFactory.newTransformer();
   transformer.setOutputProperty(OutputKeys.INDENT, "yes");
   transformer.transform(xmlInput, xmlOutput);
   return xmlOutput.getWriter().toString();
  }
  catch (Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public static Element appendNodeNS(Node target, Namespace ns, String name) throws AException
 {
  return appendNodeNS(target, ns, name, null);
 }
//---------------------------------------------------------------------------
 public static Element appendNodeNS(Node target, Namespace ns, String name, String value) throws AException
 {
  if (name == null) throw new AException("name = null");
  int index = name.indexOf(":");
  if (index == 0) throw new AException("prefix is empty");
  String uri = "";
  if (index > 0)
  {
   String prefix = name.substring(0, index);
   uri = ns.getNamespaceURI(prefix);
  }
  return appendNodeNS(target, uri, name, value);
 }
//---------------------------------------------------------------------------
 public static Element appendNodeNS(Node target, String uri, String name, String value) throws AException
 {
  if (target == null) throw new AException("target = null");
  if (uri == null) throw new AException("uri = null");
  if (name == null) throw new AException("name = null");
  try
  {
   Document owner = target.getOwnerDocument();
   Element element = owner.createElementNS(uri, name);
   if (value != null) element.setTextContent(value);
   target.appendChild(element);
   return element;
  }
  catch (DOMException ex)
  {
   throw new AException("append node " + uri + ":" + name + " error: " + ex.getMessage(), ex);
  }
 }
//---------------------------------------------------------------------------
 public static Attr appendAttrNS(Element target, Namespace ns, String name, String value) throws AException
 {
  if (name == null) throw new AException("name = null");
  int index = name.indexOf(":");
  if (index == 0) throw new AException("prefix is empty");
  String uri = "";
  if (index > 0)
  {
   String prefix = name.substring(0, index);
   uri = ns.getNamespaceURI(prefix);
  }
  return appendAttrNS(target, uri, name, value);
 }
//---------------------------------------------------------------------------
 public static Attr appendAttrNS(Element target, String uri, String name, String value) throws AException
 {
  if (target == null) throw new AException("target = null");
  if (uri == null) throw new AException("uri = null");
  if (name == null) throw new AException("name = null");
  try
  {
   Document owner = target.getOwnerDocument();
   Attr attr = owner.createAttributeNS(uri, name);
   if (value != null) attr.setTextContent(value);
//   target.appendChild(attr);
//   return attr;
   return target.setAttributeNodeNS(attr);
  }
  catch (DOMException ex)
  {
   throw new AException("append attr " + uri + ":" + name + " error: " + ex.getMessage(), ex);
  }
 }
//---------------------------------------------------------------------------
 public static Element appendNodeNS(Node target, String uri, String name) throws AException
 {
  return appendNodeNS(target, uri, name, null);
 }
//---------------------------------------------------------------------------
 public static void importNode(Element target, Node imported) throws AException
 {
  try
  {
   Document doc = target.getOwnerDocument();
   target.appendChild(doc.importNode(imported, true));
  }
  catch (DOMException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
}
