/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.dom;

import aclass.AClass;
import aclass.AException;
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
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Пользователь
 */
public class ADOM extends AClass
{
//---------------------------------------------------------------------------
 private static DocumentBuilder getBuilder()throws AException
 {
  try
  {
   DocumentBuilderFactory docBuilderfactory=DocumentBuilderFactory.newInstance();
   docBuilderfactory.setNamespaceAware(true);
   docBuilderfactory.setValidating(false);
   return docBuilderfactory.newDocumentBuilder();
  }
  catch(ParserConfigurationException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static Document getDocument(InputStream in)throws AException
 {
  try{return getBuilder().parse(in);}
  catch(Exception ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static Document getDocument(byte[] xml)throws AException
 {
  try{return getDocument(new ByteArrayInputStream(xml));}
  catch(Exception ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static Document getDocument(String xml)throws AException
 {
  try{return getDocument(xml.getBytes("UTF-8"));}
  catch(UnsupportedEncodingException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static Document createDocument()throws AException
 {
  return getBuilder().newDocument();
 }
//---------------------------------------------------------------------------
 public static String nodeToStr(Node node)throws AException
 {
  if(node==null)throw new AException("node=null");
  try
  {
   DOMSource domSource=new DOMSource(node);
   StringWriter writer=new StringWriter();
   StreamResult result=new StreamResult(writer);
   TransformerFactory tf=TransformerFactory.newInstance();
   Transformer transformer=tf.newTransformer();
   transformer.transform(domSource,result);
   return writer.toString();
  }
  catch(TransformerException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static Node findNodeList(String nodeName,NodeList nodeList)throws AException
 {
  if(nodeName==null)throw new AException("nodeName=null");
  Node nodeFound=null;
  for(int q=0;q<nodeList.getLength();q++)
  {
   Node node=nodeList.item(q);
   if(node==null)return null;
   if(node.getNodeType()==Node.TEXT_NODE)continue;
   String localName=node.getLocalName();
   if(localName!=null)if(localName.equals(nodeName))return node;
   if(node.hasChildNodes())nodeFound=findNodeList(nodeName,node.getChildNodes());
   if(nodeFound!=null)break;
  }
  return nodeFound;
 }
//---------------------------------------------------------------------------
 public static Node findFirstNode(String nodeName,NodeList nodeList)throws AException
 {
  Node nodeFound=null;
  for(int q=0;q<nodeList.getLength();q++)
  {
   Node node=nodeList.item(q);
   if(node.getNodeType()==Node.TEXT_NODE)continue;
   if(node.getLocalName().equals(nodeName))return node;
   if(node.hasChildNodes())nodeFound=findNodeList(nodeName,node.getChildNodes());
   if(nodeFound!=null)break;
  }
  return nodeFound;
 }
//---------------------------------------------------------------------------
 public static Node findLastNode(String nodeName,NodeList nodeList)throws AException
 {
  Node nodeFound=null;
  for(int q=0;q<nodeList.getLength();q++)
  {
   Node node=nodeList.item(q);
   if(node.getNodeType()==Node.TEXT_NODE)continue;
   if(node.getLocalName().equals(nodeName))return node;
   if(node.hasChildNodes())nodeFound=findNodeList(nodeName,node.getChildNodes());
   if(nodeFound!=null)break;
  }
  return nodeFound;
 }
//---------------------------------------------------------------------------
 public static Node firstChild(Node source,boolean required)throws AException
 {
  if(source==null)throw new AException("source=null");
  if(source.hasChildNodes())
  {
   NodeList nodeList=source.getChildNodes();
   for(int q=0;q<nodeList.getLength();q++)
   {
    Node node=nodeList.item(q);
    if(node.getNodeType()==Node.ELEMENT_NODE)return node;
   }
  }
  if(required)throw new AException("source has not have child node");
  return null;
 }
//---------------------------------------------------------------------------
 @Deprecated
 private static String printNode(Node node,int level)
 {
  String tab="";
  for(int q=0;q<level;q++)tab+=" ";
  String xml="";
  if(node.getNodeType()==Node.TEXT_NODE)return node.getTextContent();
  xml+=tab+"<";
  if(node.getPrefix()!=null)
  {
   if(!node.getPrefix().isEmpty())xml+=node.getPrefix()+":";
  }
  xml+=node.getNodeName()+printAttribute(node);
  if(!node.hasChildNodes())
  {
   xml+=" />\n";
   return xml;
  }
  xml+=">";
  if(node.getFirstChild().getNodeType()!=Node.TEXT_NODE)xml+="\n";
  NodeList nodeList=node.getChildNodes();
  for(int q=0;q<nodeList.getLength();q++)
  {
   if(q>0)xml+="\n";
   Node subnode=nodeList.item(q);
   if(node.getNodeType()!=Node.ELEMENT_NODE)continue;
   xml+=printNode(subnode,level+1);
//   System.out.println(tab+node.getLocalName());
////    String str="<"+node.getLocalName()+">";
////    return str;
//   if(node.hasChildNodes())nodeFound=printNodeList(node.getChildNodes(),level+1);
  }
//  return nodeFound;
  if(node.getFirstChild().getNodeType()!=Node.TEXT_NODE)xml+="\n"+tab;
  xml+="</"+node.getNodeName()+">";
  return xml;
 }
//---------------------------------------------------------------------------
 @Deprecated
 private static String printAttribute(Node node)
 {
  if(!node.hasAttributes())return "";
  String attribute=" ";
  NamedNodeMap map=node.getAttributes();

  for(int q=0;q<map.getLength();q++)
  {
   Node subnode=map.item(q);
   if(q>0)attribute+=", ";
   attribute+=subnode.getNodeName()+"=\""+subnode.getNodeValue()+"\"";
  }
  return attribute;
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static String nodeToFormatedStr(Node node)
 {
  String xml="";
  if(node.getNodeType()==Node.DOCUMENT_NODE)
  {
   xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
   if(node.hasChildNodes())xml+=printNode(node.getFirstChild(),0);
  }
  else
  {
   xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
   xml+=printNode(node,0);
  }
  return xml;
 }
//---------------------------------------------------------------------------
 public static Node getNode(Document doc,String nodeName)throws AException
 {
  return findFirstNode(nodeName,doc.getChildNodes());
 }
//---------------------------------------------------------------------------
 public static String getNodeValue(Document doc,String nodeName) throws AException
 {
//  sendGlobalWarning("getNodeValue","Function is under development",null);
  try
  {
   if(!doc.hasChildNodes())return null;
   NodeList nodeList=doc.getChildNodes();
   Node node=findFirstNode(nodeName,nodeList);
   if(node==null)return null;
   return node.getTextContent();
  }
  catch(DOMException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static String getNodeValue(Node rootNode,String nodeName) throws AException
 {
//  sendGlobalWarning("getNodeValue","Function is under development",null);
  try
  {
   if(!rootNode.hasChildNodes())return null;
   NodeList nodeList=rootNode.getChildNodes();
   Node node=findFirstNode(nodeName,nodeList);
   if(node==null)return null;
   return node.getTextContent();
  }
  catch(DOMException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static List<Node> getNodeList(String nodeName,Node node) throws AException
 {
  sendGlobalWarning("getNodeList","Function is under development",null);
  if(nodeName==null)throw new AException("nodeName=null");
  if(nodeName.isEmpty())throw new AException("nodeName is empty");
  if(node==null)throw new AException("node=null");
  try
  {
   ArrayList<Node> nodes=new ArrayList<Node>();
   gettingNodeList(nodes,nodeName,node);
   return nodes;
  }
  catch(Exception ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 private static void gettingNodeList(List<Node> nodes,String nodeName,Node node) throws AException
 {
  try
  {
//   if(node.getLocalName().equals(nodeName))nodes.add(node);
   if(!node.hasChildNodes())return;
   NodeList nodeList=node.getChildNodes();
   for(int q=0;q<nodeList.getLength();q++)
   {
    Node subNode=nodeList.item(q);
    String name=subNode.getLocalName();
    if(node.getNodeType()!=Node.ELEMENT_NODE)continue;
    if(name!=null)if(name.equals(nodeName))nodes.add(subNode);
    if(subNode.hasChildNodes())gettingNodeList(nodes,nodeName,subNode);
   }
  }
  catch(Exception ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 public static List<String> getNodeValueList(Document doc,String nodeName) throws AException
 {
  sendGlobalWarning("getNodeValue","Function is under development",null);
  try
  {
   NodeList nodeList=doc.getChildNodes();
   Node node=findNodeList(nodeName,nodeList);
   if(node==null)return null;
   return null;//node.getTextContent();
  }
  catch(DOMException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
/**
 * Получение значения атрибута с именем AttributeName в узле node.<br/>
 * @param node узел, в котором ищится атрибут
 * @param AttributeName имя атрибута
 * @param required обязательность наличия атрибута
 * @return Значение или null если атрибут ненайден и не обязателен 
 * @throws AException в случае если атрибут не найден но обязательно должен быть
 */
 public static String getAttribute(Node node,String AttributeName,boolean required)throws AException
 {
  if(node==null)throw new AException("node=null");
  if(AttributeName==null)throw new AException("AttributeName=null");
  for(int q=0;q<node.getAttributes().getLength();q++)
  {
   Node attribute=node.getAttributes().item(q);
   if(attribute==null)continue;
   if(!attribute.getLocalName().equals(AttributeName))continue;
   return attribute.getNodeValue();
  } 
  if(required)throw new AException("Attribute with name \""+AttributeName+"\" not found");
  return null;
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static String getAttribute(Node node,String AttributeName) 
 {
  try{return getAttribute(node,AttributeName,false);}
  catch(AException ex){return null;}
 }
//---------------------------------------------------------------------------
/**
 * Установка значения value для атрибута с именем AttributeName в узле node.<br/>
 * @param node узел, в котором ищится атрибут
 * @param AttributeName имя атрибута
 * @param value устанавливаемое значение
 * @param required обязательность наличия атрибута
 * @throws AException в случае если атрибут не найден но обязательно должен быть
 */
 public static void setAttribute(Node node,String AttributeName,String value,boolean required)throws AException
 {
  if(node==null)throw new AException("node=null");
  if(AttributeName==null)throw new AException("AttributeName=null");
  if(value==null)throw new AException("value=null");
  for(int q=0;q<node.getAttributes().getLength();q++)
  {
   Node attribute=node.getAttributes().item(q);
   if(attribute==null)continue;
   if(!attribute.getLocalName().equals(AttributeName))continue;
   attribute.setNodeValue(value);
   return;
  } 
  if(required)throw new AException("Attribute with name \""+AttributeName+"\" not found");
 }
//---------------------------------------------------------------------------
 public static String printFormat(String xml,int indent)throws AException
 {
  if(xml==null)throw new AException("xml=null");
  try
  {
   Source xmlInput=new StreamSource(new StringReader(xml));
   StringWriter stringWriter=new StringWriter();
   StreamResult xmlOutput=new StreamResult(stringWriter);
   TransformerFactory transformerFactory=TransformerFactory.newInstance();
   transformerFactory.setAttribute("indent-number",indent);
   Transformer transformer=transformerFactory.newTransformer();
   transformer.setOutputProperty(OutputKeys.INDENT,"yes");
   transformer.transform(xmlInput,xmlOutput);
   return xmlOutput.getWriter().toString();
  }
  catch(Exception ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static Element appendNodeNS(Document doc,Node target,String uri,String name)throws AException 
 {
  if(doc==null)throw new AException("doc=null");
  if(target==null)throw new AException("target=null");
  if(uri==null)throw new AException("uri=null");
  if(name==null)throw new AException("name=null");
  Document owner=target.getOwnerDocument();
  Element element=owner.createElementNS(uri,name);
  target.appendChild(element);
  return element;
 }
//---------------------------------------------------------------------------
 public static Element appendNodeNS(Node target,String uri,String name)throws AException 
 {
  if(target==null)throw new AException("target=null");
  if(uri==null)throw new AException("uri=null");
  if(name==null)throw new AException("name=null");
  Document owner=target.getOwnerDocument();
  Element element=owner.createElementNS(uri,name);
  target.appendChild(element);
  return element;
 }
//---------------------------------------------------------------------------
 public static Element appendNodeNS(Node target,String uri,String name,String value)throws AException 
 {
  if(target==null)throw new AException("target=null");
  if(uri==null)throw new AException("uri=null");
  if(name==null)throw new AException("name=null");
  if(value==null)throw new AException("value=null");
  Document owner=target.getOwnerDocument();
  Element element=owner.createElementNS(uri,name);
  element.setTextContent(value);
  target.appendChild(element);
  return element;
 }
//---------------------------------------------------------------------------
 public static void importNode(Element target,Node imported)
 {
  Document doc=target.getOwnerDocument();
  target.appendChild(doc.importNode(imported,true));
 }
//---------------------------------------------------------------------------
// public static String to()
// {
//  
// }
}
