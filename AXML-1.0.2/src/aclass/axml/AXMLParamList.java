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
public class AXMLParamList extends AClass
{
//---------------------------------------------------------------------------
 private ArrayList<AXMLParam> FParamList=new ArrayList();
//---------------------------------------------------------------------------
 public AXMLParamList(){}
//---------------------------------------------------------------------------
 public AXMLParamList(final AXMLParamList value)throws AException{assign(value);}
//---------------------------------------------------------------------------
 public final void assign(final AXMLParamList value)throws AException
 {
  if(value==null)throw new AException("value=null");
  FParamList.clear();
  for(int q=0;q<value.FParamList.size();q++)
  {
   AXMLParam param=new AXMLParam(value.FParamList.get(q));
   param.setParent(this);
   FParamList.add(param);
  }
 }
//---------------------------------------------------------------------------
 public int count()                                                     //!!!
 {
  return FParamList.size();
 }
//---------------------------------------------------------------------------
 public int addParam(AXMLParam value)throws AException
 {
  if(value==null)throw new AException("value=null");
  if(getIndex(value.name)!=-1)throw new AException("param \""+value.name+"\" allredy exists");
  AXMLParam param=new AXMLParam(value);
  param.setParent(this);
  FParamList.add(param);
  return FParamList.size()-1;
 }
//---------------------------------------------------------------------------
 public AXMLParam getParam(int index)throws AException
 {
  if(index<0)throw new AException("index: " + index + " < 0");
  if(index>FParamList.size())throw new AException("index: " + index + " > count");
  return new AXMLParam(FParamList.get(index));
 }
//---------------------------------------------------------------------------
 public void  setParam(int index,AXMLParam value)throws AException
 {
  if(index<0)throw new AException("index: " + index + " < 0");
  if(index>FParamList.size())throw new AException("index: " + index + " > count");
  if(value==null)throw new AException("value=null");
  AXMLParam param=FParamList.get(index);
  if(!param.name.equals(value.name))throw new AException("param \""+value.name+"\" mismath of name");
  param.assign(value);
 }
//---------------------------------------------------------------------------
 public void  setParam(AXMLParam value)throws AException
 {
  if(value==null)throw new AException("value=null");
  int index=getIndex(value.name);
  if(index==-1)throw new AException("param \""+value.name+"\" not exists");
  FParamList.get(index).assign(value);
 }
//---------------------------------------------------------------------------
 public void delParam(int index)throws AException
 {
  if(index<0)throw new AException("index: " + index + " < 0");
  if(index>FParamList.size())throw new AException("index: " + index + " > count");
  FParamList.remove(index);
 }
//---------------------------------------------------------------------------
 public int getIndex(String name)
 {
  for(int q=0;q<FParamList.size();q++)
  {
   if(FParamList.get(q).name.equals(name))return q;
  }
  return -1;
 }
//---------------------------------------------------------------------------
 public AXMLParam getParam(String name)throws AException
 {
  int index=getIndex(name);
  if(index==-1)return null;
  return new AXMLParam(FParamList.get(index));
 }
//---------------------------------------------------------------------------
 public void clear(){FParamList.clear();}
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "AXMLParamList{"+"ParamList="+FParamList+'}';
 }
//---------------------------------------------------------------------------
}
