/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefLong
{
 public long value; 
 public ARefLong()
 {
  value=0;
 }
 public ARefLong(ARefLong ref)
 {
  value=ref.value;
 }
 public ARefLong(long value)
 {
  this.value=value;
 }
 @Override
 public String toString()
 {
  return value+"";
 }
 @Override
 public boolean equals(Object obj)
 {
  if(obj==null)return false;
  if(getClass()!=obj.getClass())return false;
  final ARefLong other=(ARefLong)obj;
  if(value!=other.value)return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=7;
  hash=61*hash+(int)(value^(value>>>32));
  return hash;
 }
}
