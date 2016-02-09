/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefDouble
{
 public double value; 
 public ARefDouble()
 {
  value=0;
 }
 public ARefDouble(ARefDouble ref)
 {
  value=ref.value;
 }
 public ARefDouble(double value)
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
  final ARefDouble other=(ARefDouble)obj;
  if(Double.doubleToLongBits(value)!=Double.doubleToLongBits(other.value))return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=7;
  hash=97*hash+(int)(Double.doubleToLongBits(value)^(Double.doubleToLongBits(value)>>>32));
  return hash;
 }
}
