/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.object;

import aclass.aparser.type.AUnionHeader;
import aclass.AException;

/**
 * ------------------ 
 * union U(int a=1,str b="param",char[] c=['A','B','C']); 
 * описание со значениями по умолчанию U u;
 * объявление 
 * ------------------ 
 * union U(int a,str b,char[] c)u=new U(1,"param",['A','B','C']);
 * описание с объявлением с присвоением u;//использование
 * ------------------ 
 * union U(int a,str b,char[] c);
 * описание U u=new U(1,"param",['A','B','C']);
 * присвоение 
 * ------------------ 
 * union U(int a,str b,char[] c);
 * описание U u=new U; u.a=1; 
 * присвоение... u.b="param"; u.c[0]='A'; u.c[1]='B'; u.c[2]='C';
 * ------------------
 *
 * @author Пользователь
 */
public class AUnion extends AObject
{
//---------------------------------------------------------------------------
 private final AUnionHeader header = new AUnionHeader();
 private final AObject[] array;
//---------------------------------------------------------------------------
 public AUnion(AUnionHeader header) throws AException
 {
  super(AValue.UNION);
  if (header == null) throw new AException("header=null");
  this.header.assign(header);//=new AUnionHeader(header);
  array = new AValue[header.count()];
  for (int q = 0; q < header.count(); q++) array[q] = new AValue(header.get(q));
 }
//---------------------------------------------------------------------------
 public AUnionHeader getHeader() throws AException
 {
  return new AUnionHeader(header);
 }
//---------------------------------------------------------------------------
 public AObject get(int index) throws AException
 {
  if (index < 0) throw new AException("index: " + index + " < 0");
  if (index >= array.length) throw new AException("index: " + index + " >= count: " + array.length);
  AObject obj = array[index];
//  obj.initialize();
  return obj;
 }
//---------------------------------------------------------------------------
 public AObject get(String name) throws AException
 {
  int index = getIndex(name);
  if (index == -1) throw new AException("name = \"" + name + "\" not found");
  return get(index);
 }
//---------------------------------------------------------------------------
 public String getName(int index) throws AException
 {
  if (index < 0) throw new AException("index: " + index + " < 0");
  if (index >= array.length) throw new AException("index: " + index + " >= count: " + array.length);
  return header.getName(index);
 }
//---------------------------------------------------------------------------
 public void set(int index, AObject value) throws AException
 {
  if (index < 0) throw new AException("index: " + index + " < 0");
  if (index >= array.length) throw new AException("index: " + index + " >= count: " + array.length);
//  values[index].set(value.get());
  array[index].assignData(value);
//  array[index].
 }
//---------------------------------------------------------------------------
 public void set(String name, AObject value) throws AException
 {
  int index = header.getIndex(name);
  if (index == -1) throw new AException("Name=\"" + name + "\" not found");
  set(index, value);
 }
//---------------------------------------------------------------------------
 public int getIndex(String name) throws AException
 {
  return header.getIndex(name);
 }
//---------------------------------------------------------------------------
 public int count()
 {
  return array.length;
 }
//---------------------------------------------------------------------------
 public boolean exists(String name)
 {
  return header.exists(name);
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "union";
 }
//---------------------------------------------------------------------------
 @Override
 public int sizeof() throws AException
 {
  int size = 0;
  for (int q = 0; q < array.length; q++) size += array[q].sizeof();
  return size;
 }
//---------------------------------------------------------------------------
 @Override
 public AObject copy() throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
 @Override
 protected void assignData(AObject object) throws AException
 {
  sendError("assignData", "under development");
  testTypeMissmatch(object);
  AUnion union = (AUnion)object;

  if (this.count() != union.count()) throw new AException("type missmatch by count UNDER DEVELOPMENT");
  for (int q = 0; q < this.count(); q++) this.set(q, union.get(q));
//  if(this.header.union.)
//  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
