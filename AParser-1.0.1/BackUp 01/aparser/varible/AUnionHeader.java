/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;

import aclass.ACustomPtrArray;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AUnionHeader extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public AUnionHeader(){super();}
//---------------------------------------------------------------------------
 public AUnionHeader(AUnionHeader value)throws AException
 {
  super();
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(AUnionHeader value)throws AException
 {
  if(value==null)throw new AException("value=null");
  PClear();
  for(int q=0;q<value.PGetCount();q++)
  {
   AVarible varible=(AVarible)value.PGetPtr(q);
   PAdd(new AVarible(varible.getName(),varible.getCopy()));
  }
 }
//---------------------------------------------------------------------------
 public int add(String name,AType type)throws AException
 {
  if(name==null)throw new AException("name=null");
  if(exists(name))throw new AException("Varible with name=\""+name+"\" alredy exists");
  AVarible varible=new AVarible(name,new AValue(type));
  return PAdd(varible);
 }
//---------------------------------------------------------------------------
 public int add(String name,AValue value)throws AException
 {
  if(name==null)throw new AException("name=null");
  if(exists(name))throw new AException("Varible with name=\""+name+"\" alredy exists");
  AVarible varible=new AVarible(name,value.getCopy());
  return PAdd(varible);
 }
//---------------------------------------------------------------------------
 public AVarible get(int index)throws AException{return (AVarible)PGetPtr(index);}
//---------------------------------------------------------------------------
 public AVarible get(String name)throws AException
 {
  int index=getIndex(name);
  if(index==-1)throw new AException("Varible with name=\""+name+"\" not found");
  return get(index);
 }
//---------------------------------------------------------------------------
// public String getName(int index)throws AException{return ((AVarible)PGetPtr(index)).getName();}
//---------------------------------------------------------------------------
 public int getIndex(String name)throws AException
 {
  for(int q=0;q<count();q++){if(get(q).getName().equals(name))return q;}
  return -1;
 }
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{PDelete(index);}
//---------------------------------------------------------------------------
 public void delete(String name)throws AException
 {
  int index=getIndex(name);
  if(index==-1)throw new AException("Varible with name=\""+name+"\" not found");
  PDelete(index);
 }
//---------------------------------------------------------------------------
 public void clear(){PClear();}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 public boolean exists(String name)
 {
  try{for(int q=0;q<count();q++)if(get(q).getName().equals(name))return true;}
  catch(AException ex){sendError("exists",ex.getMessage(),null);}
  return false;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  String str="(";
  for(int q=0;q<count();q++)
  {
   if(q!=0)str+=",";
   try{str+=get(q).toString();}
   catch(AException ex){sendError("toString",ex.getMessage(),null);}
  }
  str+=")";
  return str;
 }
//---------------------------------------------------------------------------
}
