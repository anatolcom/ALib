/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser;


/**
 *
 * @author Пользователь
 */
public class AConstText
{
//---------------------------------------------------------------------------
 public final String value; 
//---------------------------------------------------------------------------
 public AConstText(AConstText ref){value=ref.value;}
//---------------------------------------------------------------------------
 public AConstText(String value){this.value=value;}
//---------------------------------------------------------------------------
 @Override
 public String toString(){return value;}
//---------------------------------------------------------------------------
// @Override
// public boolean equals(Object obj)
// {
//  if(obj==null)return false;
//  if(getClass()!=obj.getClass())return false;
//  final AConstText other=(AConstText)obj;
//  if((value==null)?(other.value!=null):!value.equals(other.value))return false;
//  return true;
// }
// @Override
// public int hashCode()
// {
//  int hash=5;
//  hash=73*hash+(value!=null?this.value.hashCode():0);
//  return hash;
// }
}

//396+
//374
//378
//390+
//393+
//400
//401
//402
//379
//380+