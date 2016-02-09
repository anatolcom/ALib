/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;
import aclass.AClass;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public abstract class AValue extends AClass
{
//---------------------------------------------------------------------------
 public static final int TYPE_VOID=0x0000;
 public static final int TYPE_BOOL=0x0001;
 public static final int TYPE_INT=0x0002;
 public static final int TYPE_STR=0x0004;
//---------------------------------------------------------------------------
 public abstract void assign(AValue data)throws AException;
//---------------------------------------------------------------------------
 public abstract int compare(AValue data)throws AException;
//---------------------------------------------------------------------------
 public static String getTypeName(int type)
 {
  switch(type) 
  {
   case TYPE_VOID:return "void";
   case TYPE_BOOL:return "bool";
   case TYPE_INT:return "int";
   case TYPE_STR:return "str";
  }
  return null;
 }
//---------------------------------------------------------------------------
 public String getTypeName(){return getTypeName(getType());}
//---------------------------------------------------------------------------
 public abstract int getType();
//---------------------------------------------------------------------------
 public boolean isType(int type)
 {
  if(type!=getType())return false;
  return true;
 }
//---------------------------------------------------------------------------
 public abstract int getSize();
//---------------------------------------------------------------------------
 public abstract AValue getCopy();
//---------------------------------------------------------------------------
 public abstract String toStr()throws AException;
//---------------------------------------------------------------------------
 public abstract void fromStr(String value)throws AException;
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  try
  {
   if(isType(TYPE_VOID))return getTypeName()+"()";
   else return getTypeName()+"("+toStr()+")";
  }
  catch(AException ex){return null;}
 }
//---------------------------------------------------------------------------
}
