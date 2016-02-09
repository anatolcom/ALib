/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefFloat
{
 public float value; 
 public ARefFloat()
 {
  value=0;
 }
 public ARefFloat(ARefFloat ref)
 {
  value=ref.value;
 }
 public ARefFloat(float value)
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
  final ARefFloat other=(ARefFloat)obj;
  if(Float.floatToIntBits(value)!=Float.floatToIntBits(other.value))return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=7;
  hash=79*hash+Float.floatToIntBits(value);
  return hash;
 }
}
