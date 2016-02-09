/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefBool
{
 public boolean value; 
 public ARefBool()
 {
  value=false;
 }
 public ARefBool(ARefBool ref)
 {
  value=ref.value;
 }
 public ARefBool(boolean value)
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
  final ARefBool other=(ARefBool)obj;
  if(value!=other.value)return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=5;
  hash=79*hash+(value?1:0);
  return hash;
 }
}
