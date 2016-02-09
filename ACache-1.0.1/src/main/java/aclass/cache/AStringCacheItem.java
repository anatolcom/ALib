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
 * запись кэша.<br/>
 * <br/>
 * имеет параметры:<br/>
 * уникальное имя - идентификатор.<br/>
 * строковые данные.<br/>
 * дата создания.<br/>
 * актуальный период в миллисекундах, 0 - бесконесность.<br/>
 * @author Anatol
 */
public class AStringCacheItem
{
//---------------------------------------------------------------------------
 public final String name;
 public final String data;
 public final long actualDate;
 public final long actualPeriod;
//---------------------------------------------------------------------------
 /**
  * констркутор.<br/>
  * @param name уникальное имя - идентификатор.<br/>
  * @param data строковые данные.<br/>
  * @param actualPeriod актуальный период в миллисекундах, 0 - бесконесность.<br/>
  * @throws AException 
  */
 public AStringCacheItem(String name, String data, long actualPeriod) throws AException
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
 /**
  * проверка соответсвия имени - идентификатора name.<br/>
  * @param name уникальное имя - идентификатор.<br/>
  * @return true - если имя соответсвует, false - если нет.
  * @throws AException 
  */
 public boolean isName(String name) throws AException
 {
  if (name == null) throw new AException("name = null");
  return this.name.equals(name);
 }
//---------------------------------------------------------------------------
 /**
  * проверка актуальности записи.<br/>
  * @return true - если запись актуальна, false - если нет.
  */
 public boolean isActual()
 {
  if (data == null) return false;
  long current = Calendar.getInstance().getTimeInMillis();
  if (actualPeriod == 0) return true;
  return current - actualDate < actualPeriod;
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "\"" + name + "\" : \"" + data + "\" [" + actualPeriod + "]";
 }
//---------------------------------------------------------------------------
}
