/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.cache;

import aclass.AException;
import java.util.ArrayList;

/**
 *
 * @author Anatol
 */
public class AStringCache implements AStringGetter
{
//---------------------------------------------------------------------------
 private final ArrayList<AStringCacheItem> list = new ArrayList<AStringCacheItem>();
//---------------------------------------------------------------------------
 private AStringGetter getter = null;
//---------------------------------------------------------------------------
 public synchronized void set(AStringGetter getter) throws AException
 {
  if (getter == null) throw new AException("getter = null");
  this.getter = getter;
 }
//---------------------------------------------------------------------------
 public synchronized void removeNoActual()
 {
  for (int q = list.size() - 1; q >= 0; q--)
  {
   if (list.get(q).isActual()) continue;
   list.remove(q);
  }
 }
//---------------------------------------------------------------------------
 public synchronized void push(AStringCacheItem item) throws AException
 {
  if (item == null) throw new AException("item = null");
  remove(item.name);
  list.add(item);
 }
//---------------------------------------------------------------------------
 public synchronized void remove(String name) throws AException
 {
  for (int q = 0; q < list.size(); q++)
  {
   AStringCacheItem item = list.get(q);
   if (!item.isName(name)) continue;
   list.remove(q);
  }
 }
//---------------------------------------------------------------------------
 public synchronized int count()
 {
  return list.size();
 }
//---------------------------------------------------------------------------
 public synchronized void clear()
 {
  list.clear();
 }
//---------------------------------------------------------------------------
 public synchronized AStringCacheItem getItem(int index) throws AException
 {
  return list.get(index);
 }
//---------------------------------------------------------------------------
 public synchronized AStringCacheItem getItem(String name) throws AException
 {
  for (int q = 0; q < list.size(); q++)
  {
   AStringCacheItem item = list.get(q);
   if (!item.isName(name)) continue;
   return item;
  }
  return null;
 }
//---------------------------------------------------------------------------
 @Override
 public synchronized AStringCacheItem get(String name) throws AException
 {
  removeNoActual();
  AStringCacheItem item = getItem(name);
  if (item != null) return item;
  if (getter == null) throw new AException("getter = null");
  item = getter.get(name);
  if(!item.isName(name)) throw new AException("getter with param name: \""+name+"\" return other name: \""+item.name+"\"");
  list.add(item);
  return item;
 }
//---------------------------------------------------------------------------
 @Override
 public synchronized void refresh(String name) throws AException
 {
  removeNoActual();
  remove(name);
  if (getter == null) throw new AException("getter = null");
  AStringCacheItem item = getter.get(name);
  if(!item.isName(name)) throw new AException("getter with param name: \""+name+"\" return other name: \""+item.name+"\"");
  list.add(getter.get(name));
 }
//---------------------------------------------------------------------------
}
