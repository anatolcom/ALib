/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aref;

/**
 *
 * @author Anatol
 */
public class ARefByte
{
 public byte value;
 public ARefByte()
 {
  value=0;
 }
 public ARefByte(ARefByte ref)
 {
  value=ref.value;
 }
 public ARefByte(byte value)
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
  final ARefByte other=(ARefByte)obj;
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
