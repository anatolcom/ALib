/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.type;

import aclass.AException;
import aclass.APtrMap;

/**
 *
 * @author Пользователь
 */
public class AUnionHeader extends ADescription
{
//---------------------------------------------------------------------------
 private APtrMap ptrMap=new APtrMap(true);
//---------------------------------------------------------------------------
 public AUnionHeader(){super();}
//---------------------------------------------------------------------------
 public AUnionHeader(AUnionHeader header)throws AException
 {
  super();
  assign(header);
 }
//---------------------------------------------------------------------------
 public final void assign(AUnionHeader header)throws AException
 {
  if(header==null)throw new AException("value=null");
  ptrMap.clear();
  for(int q=0;q<header.count();q++)ptrMap.add(header.getName(q),new AType(header.get(q)));
 }
//---------------------------------------------------------------------------
 public void add(String name,AType type)throws AException{ptrMap.add(name,type);}
//---------------------------------------------------------------------------
 public AType get(int index)throws AException{return (AType)ptrMap.getPtr(index);}
//---------------------------------------------------------------------------
 public AType get(String name)throws AException{return (AType)ptrMap.getPtr(name);}
//---------------------------------------------------------------------------
 public String getName(int index)throws AException{return ptrMap.getName(index);}
//---------------------------------------------------------------------------
 public int getIndex(String name)throws AException{return ptrMap.getIndex(name);}
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{ptrMap.delete(index);}
//---------------------------------------------------------------------------
 public void delete(String name)throws AException{ptrMap.delete(name);}
//---------------------------------------------------------------------------
 public void clear(){ptrMap.clear();}
//---------------------------------------------------------------------------
 public int count(){return ptrMap.count();}
//---------------------------------------------------------------------------
 public boolean exists(String name){return ptrMap.exists(name);}
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
 @Override
 public ADescription copy()throws AException{return new AUnionHeader(this);}
//---------------------------------------------------------------------------
}
