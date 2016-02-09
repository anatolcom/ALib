/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.axml;
import aclass.AClass;
import aclass.AException;
import java.util.ArrayList;
/**
 * @author Anatol
 */
public class AXMLNodeList extends AClass
{
//---------------------------------------------------------------------------
 private ArrayList<AXMLNode> FNodeList;
//---------------------------------------------------------------------------
 public AXMLNodeList(){initialize();}
//---------------------------------------------------------------------------
 public AXMLNodeList(final AXMLNodeList value)throws AException
 {
  initialize();
  assign(value);
 }
//---------------------------------------------------------------------------
 private void initialize()
 {
  FNodeList=new ArrayList();
 }
//---------------------------------------------------------------------------
 public final void assign(final AXMLNodeList value)throws AException
 {
  if(value==null)throw new AException("value=null");
  FNodeList.clear();
  for(int q=0;q<value.FNodeList.size();q++)
  {
   AXMLNode node=new AXMLNode(value.FNodeList.get(q));
   node.setParent(this);
   FNodeList.add(node);
  }
 }
//---------------------------------------------------------------------------
 public int count(){return FNodeList.size();}
//---------------------------------------------------------------------------
 public int addNode(AXMLNode value)throws AException
 {
  if(value==null)throw new AException("value=null");
  AXMLNode node=value;
//  AXMLNode node=new AXMLNode(value);
  node.setParent(this);
  FNodeList.add(node);
  return FNodeList.size()-1;
 }
//---------------------------------------------------------------------------
 public AXMLNode Node(int index)throws AException
 {
  if(index<0)throw new AException("index: " + index + " < 0");
  if(index>FNodeList.size())throw new AException("index: " + index + " > count");
  return FNodeList.get(index);
 }
//---------------------------------------------------------------------------
 public AXMLNode newNode()
 {
  AXMLNode node=new AXMLNode();
  node.setParent(this);
  FNodeList.add(node);
  return node;
 }
//---------------------------------------------------------------------------
 public void delNode(int index)throws AException
 {
  if(index<0)throw new AException("index: " + index + " < 0");
  if(index>FNodeList.size())throw new AException("index: " + index + " > count");
  FNodeList.remove(index);
 }
//---------------------------------------------------------------------------
 public void clear(){FNodeList.clear();}
//---------------------------------------------------------------------------
}
