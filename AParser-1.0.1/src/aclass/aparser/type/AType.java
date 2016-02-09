
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.type;

import aclass.AClass;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AType extends AClass
{
//---------------------------------------------------------------------------
 public final ADescription description;
//---------------------------------------------------------------------------
/**
 * Уникальный идентификатор
 */
 public final int Id;
/**
 * Уникальное имя
 */
 public final String Name;
/**
 * Предопределённый постоянный размер элемента
 */
 public final int PredefinedSize;
/**
 * Возможность задавать размер динамически
 */
// public final boolean DynamicSize;
///**
// * Возможность сравнивать значения
// */ 
 public final boolean Comparable;
/**
 * Возможность индексировать значения
 */
 public final boolean Indexable;
/**
 * Является ли логическим типом
 */
 public final boolean Logic;
/**
 * Является ли числовым значением
 */
 public final boolean Digital;
//---------------------------------------------------------------------------
 public AType(int Id,
              String Name,
              int PredefinedSize,
              boolean DynamicSize,
              boolean Comparable,
              boolean Indexable,
              boolean Logic,
              boolean Digital)
 {
  this.description=null;
  this.Id=Id;
  this.Name=Name;
  this.PredefinedSize=PredefinedSize;
//  this.DynamicSize=DynamicSize;
  this.Comparable=Comparable;
  this.Indexable=Indexable;
  this.Logic=Logic;
  this.Digital=Digital;
 }
//---------------------------------------------------------------------------
 public AType(AType value)throws AException
 {
  if(value.description==null)this.description=null;
  else this.description=value.description.copy();
  this.Id=value.Id;
  this.Name=value.Name;
  this.PredefinedSize=value.PredefinedSize;
//  this.DynamicSize=value.DynamicSize;
  this.Comparable=value.Comparable;
  this.Indexable=value.Indexable;
  this.Logic=value.Logic;
  this.Digital=value.Digital;
 }
//---------------------------------------------------------------------------
 public String print(){return Name;}        
//---------------------------------------------------------------------------
 public boolean isType(String name){return this.Name.equals(name);}
//---------------------------------------------------------------------------
 public boolean isType(AType type){return isType(type.Name);}
//---------------------------------------------------------------------------
 @Override
 public String toString(){return Name;}
//---------------------------------------------------------------------------
 public boolean isDynamic()
 {
  if(PredefinedSize==0)return true;
  return false;
 }
//---------------------------------------------------------------------------
}
