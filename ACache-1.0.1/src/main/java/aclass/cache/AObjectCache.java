/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.cache;

import aclass.AException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Anatol
 */
public class AObjectCache implements AObjectGetter, Iterable<AObjectCacheItem>
{
//---------------------------------------------------------------------------
 private AObjectGetter getter = null;
//---------------------------------------------------------------------------
 private final Set<AObjectCacheItem> cache = new HashSet<AObjectCacheItem>();
//---------------------------------------------------------------------------
 private void remove(AObjectCacheItem item) throws AException
 {
  if (item == null) throw new AException("item = null");
  cache.remove(item);
 }
//---------------------------------------------------------------------------
 public synchronized void set(AObjectGetter getter) throws AException
 {
  if (getter == null) throw new AException("getter = null");
  this.getter = getter;
 }
//---------------------------------------------------------------------------
 public synchronized void removeNoActual() throws AException
 {
  for (AObjectCacheItem item : cache) if (!item.isActual()) remove(item);
 }
//---------------------------------------------------------------------------
 public synchronized void push(AObjectCacheItem item) throws AException
 {
  if (item == null) throw new AException("item = null");
  remove(item.name);
  cache.add(item);
 }
//---------------------------------------------------------------------------
 public synchronized void remove(String name) throws AException
 {
  for (AObjectCacheItem item : cache) if (item.isName(name)) remove(item);
 }
//---------------------------------------------------------------------------
 public synchronized int count()
 {
  return cache.size();
 }
//---------------------------------------------------------------------------
 public synchronized void clear()
 {
  cache.clear();
 }
//---------------------------------------------------------------------------
// public synchronized AObjectCacheItem getItem(int index) throws AException
// {
//  return list.get(index);
// }
//---------------------------------------------------------------------------
 public synchronized AObjectCacheItem getItem(String name) throws AException
 {
  for (AObjectCacheItem item : cache) if (item.isName(name)) return item;
  return null;
 }
//---------------------------------------------------------------------------
 @Override
 public synchronized AObjectCacheItem get(String name) throws AException
 {
  removeNoActual();
  AObjectCacheItem item = getItem(name);
  if (item != null) return item;
  if (getter == null) throw new AException("getter = null");
  item = getter.get(name);
  if (!item.isName(name)) throw new AException("getter with param name: \"" + name + "\" return other name: \"" + item.name + "\"");
  cache.add(item);
  return item;
 }
//---------------------------------------------------------------------------
 @Override
 public synchronized void refresh(String name) throws AException
 {
  removeNoActual();
  if (getter == null) throw new AException("getter = null");
  getter.refresh(name);
 }
//---------------------------------------------------------------------------
 @Override
 public Iterator<AObjectCacheItem> iterator()
 {
  return cache.iterator();
 }
//---------------------------------------------------------------------------
}
