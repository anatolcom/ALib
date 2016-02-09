/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefInt
{
 public int value; 
 public ARefInt()
 {
  value=0;
 }
 public ARefInt(ARefInt ref)
 {
  value=ref.value;
 }
 public ARefInt(int value)
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
  final ARefInt other=(ARefInt)obj;
  if(value!=other.value)return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=3;
  hash=89*hash+value;
  return hash;
 }
}
