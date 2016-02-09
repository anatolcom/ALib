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
public class AObjectCache implements AObjectGetter
{
//---------------------------------------------------------------------------
 private final ArrayList<AObjectCacheItem> list = new ArrayList<AObjectCacheItem>();
//---------------------------------------------------------------------------
 private AObjectGetter getter = null;
//---------------------------------------------------------------------------
 public synchronized void set(AObjectGetter getter) throws AException
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
//   sendGlobalInfo("removeNoActual", "name: \"" + list.get(q).name + "\", data: \"" + list.get(q).data + "\"", null);
   list.remove(q);
  }
 }
//---------------------------------------------------------------------------
 public synchronized void push(AObjectCacheItem item) throws AException
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
   AObjectCacheItem item = list.get(q);
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
 public synchronized AObjectCacheItem getItem(int index) throws AException
 {
  return list.get(index);
 }
//---------------------------------------------------------------------------
 public synchronized AObjectCacheItem getItem(String name) throws AException
 {
  for (int q = 0; q < list.size(); q++)
  {
   AObjectCacheItem item = list.get(q);
   if (!item.isName(name)) continue;
   return item;
  }
  return null;
 }
//---------------------------------------------------------------------------
 @Override
 public synchronized AObjectCacheItem get(String name) throws AException
 {
  removeNoActual();
  AObjectCacheItem item = getItem(name);
  if (item != null) return item;
  if (getter == null) throw new AException("get = null");
  item = getter.get(name);
  if (!item.isName(name)) throw new AException("getter with param name: \"" + name + "\" return other name: \"" + item.name + "\"");
  list.add(item);
  return item;
 }
//---------------------------------------------------------------------------
 @Override
 public synchronized void refresh(String name) throws AException
 {
  removeNoActual();
  remove(name);
  if (getter == null) throw new AException("get = null");
  AObjectCacheItem item = getter.get(name);
  if (!item.isName(name)) throw new AException("getter with param name: \"" + name + "\" return other name: \"" + item.name + "\"");
  list.add(getter.get(name));
 }
//---------------------------------------------------------------------------
}
