/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.cache;

import aclass.AException;
import java.util.Calendar;
//import java.util.Date;

/**
 *
 * @author Anatol
 */
public class AObjectCacheItem
{
//---------------------------------------------------------------------------
 public final String name;
 public final Object data;
 public final long actualDate;
 public final long actualPeriod;
//---------------------------------------------------------------------------
 public AObjectCacheItem(String name, Object data, long actualPeriod) throws AException
 {
  if (name == null) throw new AException("name = null");
  if (data == null) throw new AException("data = null");
  if (actualPeriod < 0) throw new AException("actualPeriod < 0");
  this.name = name;
  this.data = data;
  this.actualDate = Calendar.getInstance().getTimeInMillis();
  this.actualPeriod = actualPeriod;
 }
//---------------------------------------------------------------------------
 public boolean isName(String name) throws AException
 {
  if (name == null) throw new AException("name = null");
  return this.name.equals(name);
 }
//---------------------------------------------------------------------------
 public boolean isActual()
 {
  if (data == null) return false;
  long current = Calendar.getInstance().getTimeInMillis();
  if (actualPeriod == 0) return true;
  return current - actualDate < actualPeriod;
 }
//---------------------------------------------------------------------------
}
