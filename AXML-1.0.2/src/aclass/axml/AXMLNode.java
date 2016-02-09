/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.axml;
import aclass.AClass;
import aclass.AException;
/**
 * @author Anatol
 */
public class AXMLNode extends AClass
{
//---------------------------------------------------------------------------
 private String FName;
 private String FContent;
//---------------------------------------------------------------------------
 private void initialize()
 {
  FName="";
  ParamList=new AXMLParamList();
  ParamList.setParent(this);
  SubNodeList=new AXMLNodeList();
  SubNodeList.setParent(this);
  FContent="";
 }
//---------------------------------------------------------------------------
 public AXMLNode(){initialize();}
//---------------------------------------------------------------------------
 public AXMLNode(final AXMLNode value)throws AException
 {
  initialize();
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(final AXMLNode value)throws AException
 {
  if(value==null)throw new AException("value=null");
  FName=value.FName;
  ParamList.assign(value.ParamList);
  SubNodeList.assign(value.SubNodeList);
  FContent=value.FContent;
 }
//---------------------------------------------------------------------------
 public String getName(){return FName;}
//---------------------------------------------------------------------------
 public void setName(String value){FName=value;}
//---------------------------------------------------------------------------
 public boolean isName(String value){return FName.equals(value);}
//---------------------------------------------------------------------------
 public AXMLParamList ParamList;
 public AXMLNodeList SubNodeList;
//---------------------------------------------------------------------------
 public String getContent(){return FContent;}
//---------------------------------------------------------------------------
 public void setContent(String value){FContent=value;}
//---------------------------------------------------------------------------
 public void clear()
 {
  FName="";
  ParamList.clear();
  SubNodeList.clear();
  FContent="";
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()                                               //!!!
 {
  return "AXMLNode{"+"Name="+FName+",Content="+FContent+'}';
 }
//---------------------------------------------------------------------------
}
