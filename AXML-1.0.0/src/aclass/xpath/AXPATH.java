/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xpath;

import aclass.AException;
import java.util.Iterator;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
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
public class AXPATH
{
//---------------------------------------------------------------------------
 public static Node getNode(Node source,String expression,NamespaceContext nsContexts,boolean required)throws AException 
 {
  if(source==null)throw new AException("source=null");
  if(expression==null)throw new AException("expression=null");
  try
  {
   XPath xpath=XPathFactory.newInstance().newXPath();
   if(nsContexts!=null)xpath.setNamespaceContext(nsContexts);
   XPathExpression exp=xpath.compile(expression);
   Node node=(Node)exp.evaluate(source,XPathConstants.NODE);
   if(node==null)
   {
    if(required)throw new AException("Node with expression \""+expression+"\" not found");
    return null;
   }
   return node;
  }
  catch(XPathExpressionException ex)
  {
   if(ex.getCause()==null)throw new AException(ex);
   else throw new AException(ex.getCause().getMessage(),ex);
  }
 } 
//---------------------------------------------------------------------------
 public static NodeList getNodeSet(Node source,String expression,NamespaceContext nsContexts,boolean required)throws AException 
 {
  try
  {
   XPath xpath=XPathFactory.newInstance().newXPath();
   if(nsContexts!=null)xpath.setNamespaceContext(nsContexts);
   XPathExpression exp=xpath.compile(expression);
   NodeList nodeList=(NodeList)exp.evaluate(source,XPathConstants.NODESET);
   if(nodeList==null)
   {
    if(required)throw new AException("NodeSet with expression \""+expression+"\" not found");
    return null;
   }
   return nodeList;
  }
  catch(XPathExpressionException ex)
  {
   if(ex.getCause()==null)throw new AException(ex);
   else throw new AException(ex.getCause().getMessage());
  }
 } 
//---------------------------------------------------------------------------
/* public static Node getNode(Document doc,String expression,NamespaceContext nsContexts,boolean required)throws AException 
 {
  try
  {
   XPath xpath=XPathFactory.newInstance().newXPath();
   if(nsContexts!=null)xpath.setNamespaceContext(nsContexts);
   XPathExpression exp=xpath.compile(expression);
   Node node=(Node)exp.evaluate(doc,XPathConstants.NODE);
   if(node==null)
   {
    if(required)throw new AException("Node with expression \""+expression+"\" not found");
    return null;
   }
   return node;
  }
  catch(XPathExpressionException ex)
  {
   if(ex.getCause()==null)throw new AException(ex);
   else throw new AException(ex.getCause().getMessage());
  }
 }*/ 
//---------------------------------------------------------------------------
/* public static NodeList getNodeSet(Document doc,String expression,NamespaceContext nsContexts,boolean required)throws AException 
 {
  try
  {
   XPath xpath=XPathFactory.newInstance().newXPath();
   if(nsContexts!=null)xpath.setNamespaceContext(nsContexts);
   XPathExpression exp=xpath.compile(expression);
   NodeList nodeList=(NodeList)exp.evaluate(doc,XPathConstants.NODESET);
   if(nodeList==null)
   {
    if(required)throw new AException("NodeSet with expression \""+expression+"\" not found");
    return null;
   }
   return nodeList;
  }
  catch(XPathExpressionException ex)
  {
   if(ex.getCause()==null)throw new AException(ex);
   else throw new AException(ex.getCause().getMessage());
  }
 }*/
//---------------------------------------------------------------------------
 @Deprecated
 public static Node getNode(Document doc,String expression,NamespaceContext nsContexts)throws AException 
 {
  return getNode(doc,expression,nsContexts,false);
 } 
//---------------------------------------------------------------------------
 public static Node getNode(Node source,String expression,boolean required)throws AException 
 {
  return getNode(source,expression,null,required);
 } 
//---------------------------------------------------------------------------
 @Deprecated
 public static Node getNode(Document doc,String expression)throws AException 
 {
  return getNode(doc,expression,null,false);
 } 
//---------------------------------------------------------------------------
 public static String getValue(Node source,String expression,NamespaceContext nsContexts,boolean required)throws AException 
 {
  Node node=getNode(source,expression,nsContexts,required);
  if(node==null)return null;
  return node.getTextContent();
 } 
//---------------------------------------------------------------------------
 @Deprecated
 public static String getValue(Document doc,String expression,NamespaceContext nsContexts)throws AException 
 {
  return getValue(doc,expression,nsContexts,false);
 } 
//---------------------------------------------------------------------------
 public static String getValue(Node source,String expression,boolean required)throws AException 
 {
  return getValue(source,expression,null,required);
 } 
//---------------------------------------------------------------------------
 @Deprecated
 public static String getValue(Document doc,String expression)throws AException 
 {
  return getValue(doc,expression,null,false);
 } 
//---------------------------------------------------------------------------
 public static void setValue(Node source,String expression,String value,NamespaceContext nsContexts,boolean required)throws AException 
 {
  Node node=getNode(source,expression,nsContexts,required);
  if(node==null)return;
  node.setTextContent(value);
 } 
//---------------------------------------------------------------------------
 @Deprecated
 public static void setValue(Document doc,String expression,String value,NamespaceContext nsContexts)throws AException 
 {
  setValue(doc,expression,value,nsContexts,false);
 } 
//---------------------------------------------------------------------------
 public static void setValue(Node source,String expression,String value,boolean required)throws AException 
 {
  setValue(source,expression,value,null,required);
 } 
//---------------------------------------------------------------------------
 @Deprecated
 public static void setValue(Document doc,String expression,String value)throws AException 
 {
  setValue(doc,expression,value,null,false);
 } 
//---------------------------------------------------------------------------
 @Deprecated
 public static NamespaceContext getNsContext(final String name,final String URI)throws AException
 {
  return new NamespaceContext() 
  {
   @Override
   public String getNamespaceURI(String prefix)
   {
    if(prefix==null)throw new NullPointerException("Null prefix");
    if(prefix.equals(name))return URI;
    return XMLConstants.NULL_NS_URI;
   }
   @Override
   public String getPrefix(String namespaceURI)
   {
    if(URI.equals(namespaceURI))return name;
    return "";
   }
   @Override
   public Iterator getPrefixes(String namespaceURI){throw new UnsupportedOperationException("Not supported yet.");}
  };
 }
//---------------------------------------------------------------------------
 public static Document setValues(Document doc,NamespaceContext nsContexts, List<PathValue> list,boolean required)throws AException
 {
  for(PathValue item:list)setValue(doc,item.xpath,item.value,nsContexts,required);
  return doc;
 }
//---------------------------------------------------------------------------
 public static Node setValues(Node source,NamespaceContext nsContexts, List<PathValue> list,boolean required)throws AException
 {
  for(PathValue item:list)setValue(source,item.xpath,item.value,nsContexts,required);
  return source;
 }
//---------------------------------------------------------------------------
 @Deprecated
 public static Document setValues(Document doc,NamespaceContext nsContexts, List<PathValue> list)throws AException
 {
  return setValues(doc,nsContexts,list,false);
 }
//---------------------------------------------------------------------------
}
