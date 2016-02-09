/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;
import aclass.AClass;
import aclass.AException;
import aclass.aparser.varible.AType;
import aclass.aparser.varible.AValue;
import aclass.aparser.varible.AValueList;

/**
 *
 * @author Пользователь
 */
public class AChannel extends AClass
{
//---------------------------------------------------------------------------
 public String name="";
 public final AType type;
 private final AValueList list=new AValueList();
//---------------------------------------------------------------------------
 public AChannel(String name,AType type)
 {
  this.name=name;
  this.type=type;
 }
//---------------------------------------------------------------------------
 public void add(AValue value) throws AException
 {
  if(!value.isType(type))throw new AException("Type missmatch. this."+type.Name
          +" and value."+value.getTypeName());
  list.add(value);
 }
//---------------------------------------------------------------------------
 public AValue value(int index)throws AException{return list.get(index);}
//---------------------------------------------------------------------------
 public int count(){return list.count();}
//---------------------------------------------------------------------------
}
