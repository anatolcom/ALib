/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.object;
import aclass.aparser.type.AType;
import aclass.AClass;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AVarible extends AClass
{
//---------------------------------------------------------------------------
// public static final int COPY=0x0001;
// public static final int REFERENCE=0x0002;
 
//---------------------------------------------------------------------------
 private String name="";
//---------------------------------------------------------------------------
 private AValue value=null;
//---------------------------------------------------------------------------
/**
 * 
 * @param name имя переменной
 */
 public AVarible(String name,AType type) throws AException
 {
  super();
  if(name==null)throw new AException("name=null");
  this.name=name;
  this.value=new AValue(type);
 }
//---------------------------------------------------------------------------
/**
 * 
 * @param name имя переменной, не может принимать значение null
 * @param value значение переменной, не может принимать значение null
 */
 public AVarible(String name,AValue value) throws AException
 {
  super();
  if(name==null)throw new AException("name=null");
  if(value==null)throw new AException("value=null");
  this.name=name;
  this.value=value;
 }
//---------------------------------------------------------------------------
// private void assignData(AVarible varible) throws AException
// {
//  if(varible==null)throw new AException("varible=null");
//  this.name=varible.name;
////  this.print.assignData(varible.print);
//  this.print=varible.print;
// }
//---------------------------------------------------------------------------
 public AValue getCopy() throws AException
 {
  if(value==null)return null;
  return value.getCopy();
 }
//---------------------------------------------------------------------------
 public AValue getReference(){return value;}
//---------------------------------------------------------------------------
// public AVarible Copy() throws AException
// {
//  sendInfo("Copy","in work",null);
//  return new AVarible(name,value,COPY,FConst);
// }
//---------------------------------------------------------------------------
 public String getName(){return name;}
//---------------------------------------------------------------------------
// public String toStr() throws AException
// {
//  if(value==null)return "NULL";
//  return value.print();
// }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  try
  {
//   if(value==null)return name+"=NULL";
//   if(value.isType(AValue.STR))
//    return value.getTypeName()+" "+name+"=\""+value.print()+"\"";
////   if(value.isType(AType.CHAR))return value.getTypeName()+" "+name+"=\'"+value.print()+"\'";
//   return value.getTypeName()+" "+name+"="+value.print();
   return value.getTypeName()+" "+name;/*+"="+value.print();*/
  }
  catch(Exception ex)
  {
   sendError("toString",ex.getMessage());
   ex.printStackTrace(System.err);
   return name;
  }
 }
//---------------------------------------------------------------------------
}
