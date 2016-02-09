/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefChar
{
 public char value; 
 public ARefChar()
 {
  value=0;
 }
 public ARefChar(ARefChar ref)
 {
  value=ref.value;
 }
 public ARefChar(char value)
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
  final ARefChar other=(ARefChar)obj;
  if(value!=other.value)return false;
  return true;
 }
 @Override
 public int hashCode()
 {
  int hash=5;
  hash=19*hash+value;
  return hash;
 }
}
