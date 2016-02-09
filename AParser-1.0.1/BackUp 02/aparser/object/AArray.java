/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.object;

import aclass.aparser.type.AType;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AArray extends AObject
{
//---------------------------------------------------------------------------
 private final AObject[] array;
//---------------------------------------------------------------------------
 public AArray(AType type,int count)throws AException
 {
  super(type) ;
  this.array=new AValue[count];
  
//  for(int q=0;q<this.array.length;q++)this.array[q]=new AObject(type);
 }
//---------------------------------------------------------------------------
 public AType getType(){return type;}
//---------------------------------------------------------------------------
 public AObject get(int index) throws AException
 {
  if(index<0)throw new AException("index<0",index);
  if(index>=array.length)throw new AException("index>=count",index);
  return array[index];
 }
//---------------------------------------------------------------------------
 public int count(){return array.length;}
//---------------------------------------------------------------------------
 @Override
 public int sizeof()throws AException
 {
  int size=0;
  for(int q=0;q<array.length;q++)size+=array[q].sizeof();
  return size;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject copy() throws AException
 {
  AArray object=new AArray(type,this.array.length);
  for(int q=0;q<object.array.length;q++)object.array[q].assignData(this.array[q]);
  return object;
 }
//---------------------------------------------------------------------------
 @Override
 protected void assignData(AObject object) throws AException
 {
  testTypeMissmatch(object);
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
