/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.xpath;

import aclass.AException;
import java.util.List;
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
public class AXPath
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
   if(ex.getCause()==null)throw new AException("expression \""+expression+"\" "+ex.getMessage(),ex);
   else throw new AException("expression \""+expression+"\" "+ex.getCause().getMessage(),ex);
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
 public static Node getNode(Node source,String expression,boolean required)throws AException 
 {
  return getNode(source,expression,null,required);
 } 
//---------------------------------------------------------------------------
 public static String getValue(Node source,String expression,NamespaceContext nsContexts,boolean required)throws AException 
 {
  Node node=getNode(source,expression,nsContexts,required);
  if(node==null)return null;
  return node.getTextContent();
 } 
//---------------------------------------------------------------------------
 public static String getValue(Node source,String expression,boolean required)throws AException 
 {
  return getValue(source,expression,null,required);
 } 
//---------------------------------------------------------------------------
 public static void setValue(Node source,String expression,String value,NamespaceContext nsContexts,boolean required)throws AException 
 {
  Node node=getNode(source,expression,nsContexts,required);
  if(node==null)return;
  node.setTextContent(value);
 } 
//---------------------------------------------------------------------------
 public static void setValue(Node source,String expression,String value,boolean required)throws AException 
 {
  setValue(source,expression,value,null,required);
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
}
