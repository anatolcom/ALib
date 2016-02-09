/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aclass.aref;

/**
 *
 * @author Anatol
 * @param <T>
 */
public interface AReference<T>
{
 public class ARef<T>
 {
  public T value; 
  public ARef()
  {
   value=null;
  }
  public ARef(ARef<T> ref)
  {
   this.value=ref.value;
  }
  public ARef(T value)
  {
   this.value=value;
  }
  @Override
  public String toString()
  {
   return value.toString();
  }
  @Override
  public boolean equals(Object obj)
  {
   if(obj==null)return false;
   if(getClass()!=obj.getClass())return false;
   final ARef<T> other=(ARef<T>)obj;
   if(value!=other.value&&(value==null||!value.equals(other.value)))return false;
   return true;
  }
  @Override
  public int hashCode()
  {
   int hash=5;
   hash=89*hash+(value!=null?value.hashCode():0);
   return hash;
  }

 }
}
