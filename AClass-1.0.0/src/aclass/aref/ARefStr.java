/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefStr
{
 public String value; 
 public ARefStr()
 {
  value="";
 }
 public ARefStr(ARefStr ref)
 {
  value=ref.value;
 }
 public ARefStr(String value)
 {
  this.value=value;
 }
 @Override
 public String toString()
 {
  return value;
 }
 @Override
 public boolean equals(Object obj)
 {
  if(obj==null)return false;
  if(getClass()!=obj.getClass())return false;
  final ARefStr other=(ARefStr)obj;
  if((value==null)?(other.value!=null):!value.equals(other.value))return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=5;
  hash=73*hash+(value!=null?this.value.hashCode():0);
  return hash;
 }
}
