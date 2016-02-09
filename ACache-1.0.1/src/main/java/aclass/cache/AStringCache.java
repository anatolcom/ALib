/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.cache;

import aclass.AException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Anatol
 */
public class AStringCache implements AStringGetter, Iterable<AStringCacheItem>
{
//---------------------------------------------------------------------------
 private AStringGetter getter = null;
//---------------------------------------------------------------------------
 private final Lock lock = new ReentrantLock();
//---------------------------------------------------------------------------
 private final Set<AStringCacheItem> cache = new CopyOnWriteArraySet<AStringCacheItem>();
//---------------------------------------------------------------------------
 private void remove(AStringCacheItem item) throws AException
 {
  if (item == null) throw new AException("item = null");
  cache.remove(item);
 }
//---------------------------------------------------------------------------
// public synchronized void set(AStringGetter getter) throws AException
// {
//  this.getter = getter;
// }
//---------------------------------------------------------------------------
 public void set(AStringGetter getter) throws AException
 {
  lock.lock();
  try
  {
   this.getter = getter;
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// public synchronized void removeNoActual() throws AException
// {
//  for (AStringCacheItem item : cache) if (!item.isActual()) remove(item);
// }
//---------------------------------------------------------------------------
 public void removeNoActual() throws AException
 {
  lock.lock();
  try
  {
   for (AStringCacheItem item : cache) if (!item.isActual()) remove(item);
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// public synchronized void push(AStringCacheItem item) throws AException
// {
//  if (item == null) throw new AException("item = null");
//  remove(item.name);
//  cache.add(item);
// }
//---------------------------------------------------------------------------
 public void push(AStringCacheItem item) throws AException
 {
  lock.lock();
  try
  {
   if (item == null) throw new AException("item = null");
   remove(item.name);
   cache.add(item);
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// public synchronized void remove(String name) throws AException
// {
//  for (AStringCacheItem item : cache) if (item.isName(name)) remove(item);
// }
//---------------------------------------------------------------------------
 public void remove(String name) throws AException
 {
  lock.lock();
  try
  {
   for (AStringCacheItem item : cache) if (item.isName(name)) remove(item);
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// public synchronized int count()
// {
//  return cache.size();
// }
//---------------------------------------------------------------------------
 public int count()
 {
  lock.lock();
  try
  {
   return cache.size();
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// public synchronized void clear()
// {
//  cache.clear();
// }
//---------------------------------------------------------------------------
 public void clear()
 {
  lock.lock();
  try
  {
   cache.clear();
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// public synchronized AStringCacheItem getCache(String name) throws AException
// {
//  for (AStringCacheItem item : cache) if (item.isName(name)) return item;
//  return null;
// }
//---------------------------------------------------------------------------
 public AStringCacheItem getCache(String name) throws AException
 {
  lock.lock();
  try
  {
   for (AStringCacheItem item : cache) if (item.isName(name)) return item;
   return null;
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// /**
//  * Получение записи кеша по имени name;
//  *
//  * @param name имя записи в кэше
//  * @param required при значении true никогда не возвращает null.
//  * @return запись кэша.
//  * @throws AException
//  */
// @Override
// public synchronized AStringCacheItem get(String name, boolean required) throws AException
// {
//  removeNoActual();
//  AStringCacheItem item = getCache(name);
//  if (item != null) return item;
//  if (getter == null)
//  {
//   if (required) throw new AException("unknow cache with name \"" + name + "\", and getter not set");
//   return null;
//  }
//  item = getter.get(name, required);
//  if (item == null)
//  {
//   if (required) throw new AException("unknow cache with name \"" + name + "\", and getter with param required true, return null");
//   return null;
//  }
//  if (!item.isName(name)) throw new AException("getter with param name: \"" + name + "\" return other name: \"" + item.name + "\"");
//  cache.add(item);
//  return item;
// }
//---------------------------------------------------------------------------
 /**
  * Получение записи кеша по имени name;
  *
  * @param name имя записи в кэше
  * @param required при значении true никогда не возвращает null.
  * @return запись кэша.
  * @throws AException
  */
 @Override
 public AStringCacheItem get(String name, boolean required) throws AException
 {
  lock.lock();
  try
  {
   removeNoActual();
   AStringCacheItem item = getCache(name);
   if (item != null) return item;
   if (getter == null)
   {
    if (required) throw new AException("unknow cache with name \"" + name + "\", and getter not set");
    return null;
   }
   item = getter.get(name, required);
   if (item == null)
   {
    if (required) throw new AException("unknow cache with name \"" + name + "\", and getter with param required true, return null");
    return null;
   }
   if (!item.isName(name)) throw new AException("getter with param name: \"" + name + "\" return other name: \"" + item.name + "\"");
   cache.add(item);
   return item;
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
// @Override
// public synchronized void refresh(String name) throws AException
// {
//  removeNoActual();
//  if (getter == null) return;
//  getter.refresh(name);
// }
//---------------------------------------------------------------------------
 @Override
 public void refresh(String name) throws AException
 {
  lock.lock();
  try
  {
   removeNoActual();
   if (getter == null) return;
   getter.refresh(name);
  }
  finally
  {
   lock.unlock();
  }
 }
//---------------------------------------------------------------------------
 @Override
 public Iterator<AStringCacheItem> iterator()
 {
  return cache.iterator();
 }
//---------------------------------------------------------------------------
}
