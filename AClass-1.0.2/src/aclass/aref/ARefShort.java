/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefShort
{
 public short value; 
 public ARefShort()
 {
  value=0;
 }
 public ARefShort(ARefShort ref)
 {
  value=ref.value;
 }
 public ARefShort(short value)
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
  final ARefShort other=(ARefShort)obj;
  if(value!=other.value)return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=7;
  hash=29*hash+value;
  return hash;
 }
}
