/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aweb;

import aclass.AClass;
import aclass.AException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * @author Пользователь
 */
public class AWebParamMap extends AClass
{
//---------------------------------------------------------------------------
 private String charset="UTF-8";
 private AWebParamList list=new AWebParamList();
//---------------------------------------------------------------------------
 public AWebParamMap(){}
//---------------------------------------------------------------------------
 public AWebParamMap(String charset)throws AException{this.charset=charset;}
//---------------------------------------------------------------------------
 public void add(AWebParam param)throws AException
 {
  if(exists(param.name))throw new AException("Param with name \""+param.name+"\" alredy exists");
  list.add(param);
 }
//---------------------------------------------------------------------------
 public int index(String name)
 {
  for(int q=0;q<list.count();q++)if(list.get(q).name.equals(name))return q;
  return -1;
 }
//---------------------------------------------------------------------------
 public boolean exists(String name)
 {
  if(index(name)==-1)return false;
  return true;
 }
//---------------------------------------------------------------------------
// public AWebParamList getWebParamList(){return list;}
//---------------------------------------------------------------------------
 public String getCharset(){return charset;}
//---------------------------------------------------------------------------
 public void setCharset(String charset){this.charset=charset;}
//---------------------------------------------------------------------------
 public AWebParam get(int index) throws AException{return list.get(index);}
//---------------------------------------------------------------------------
 public AWebParam get(String name) throws AException
 {
  int index=index(name);
  if(index==-1)throw new AException("Param with name \""+name+"\" param not found");
  return list.get(index);
 }
//---------------------------------------------------------------------------
 public String getValue(String name) throws AException{return get(name).value;}
//---------------------------------------------------------------------------
 public int count(){return list.count();}
//---------------------------------------------------------------------------
 public void fromWebStr(String webStr)throws AException
 {
  if(webStr==null)throw new AException("webStr=null");
  if(webStr.isEmpty())throw new AException("webStr is empty");
  try
  {
   list.clear();
   String[] params=webStr.split("&");
   for(String param:params)add(toWebParam(param));
  }
  catch(Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public String toWebStr()throws AException
 {
  StringBuilder builder=new StringBuilder();
  for(int q=0;q<list.count();q++)
  {
   if(q<0)builder.append("&");
   builder.append(fromWebParam(list.get(q)));
  }
  return builder.toString();
 }
//---------------------------------------------------------------------------
 private AWebParam toWebParam(String param)throws AException
 {
  try
  {
   String[] str=param.split("=",2);
   return new AWebParam(URLDecoder.decode(str[0],charset),URLDecoder.decode(str[1],charset));
  }
  catch(UnsupportedEncodingException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 private String fromWebParam(AWebParam param)throws AException
 {
  try
  {
   return URLEncoder.encode(param.name,charset)+"="+URLEncoder.encode(param.value,charset);
  }
  catch(UnsupportedEncodingException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
// private void fromMultipart 
//---------------------------------------------------------------------------
}
