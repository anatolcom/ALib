/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;
import aclass.ACustomPtrArray;
import aclass.AException;
import aclass.aparser.varible.AType;

/**
 *
 * @author Пользователь
 */
public class AChannelList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public void create(String name,AType type) throws AException
 {
  int index=insertIndex(name);
  if(index==-1)throw new AException("Channel with name=\""+name+"\" alredy exists");
  AChannel channel=new AChannel(name,type);
  PInsert(channel,index);
 }
//---------------------------------------------------------------------------
// public int add(AChannel chanel) throws AException
// {
//  if(exists(chanel.name))throw new AException(this,"add","Chanel with name=\""+chanel.name+"\" alredy exists");
//  return PAdd(chanel);
// }
//---------------------------------------------------------------------------
// public int insert(AChannel chanel,int index) throws AException
// {
//  if(exists(chanel.name))throw new AException(this,"insert","Chanel with name=\""+chanel.name+"\" alredy exists");
//  return PInsert(chanel,index);
// }
//---------------------------------------------------------------------------
 public int index(String name)
 {
  try{for(int q=0;q<count();q++)if(channel(q).name.equals(name))return q;}
  catch(AException ex){sendError("index",ex.getMessage(),null);}
  return -1;
 }
//---------------------------------------------------------------------------
 public AChannel channel(int index)throws AException{return (AChannel)PGetPtr(index);}
//---------------------------------------------------------------------------
 public AChannel channel(String name)throws AException
 {
  int index=index(name);
  if(index==-1)throw new AException("Channel with name=\""+name+"\" not exists");
  return (AChannel)PGetPtr(index);
 }
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{PDelete(index);}
//---------------------------------------------------------------------------
// public void swap(int a,int b){PSwap(a,b);}
//---------------------------------------------------------------------------
// public void move(int a,int b){PMove(a,b);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 private int insertIndex(String name)throws AException
 {
  for(int q=0;q<count();q++)
  {
   int cmp=channel(q).name.compareTo(name);
   if(cmp==0)return -1;
   if(cmp>0)return q;
  }
  return count();
 }
//---------------------------------------------------------------------------
 public boolean exists(String name)
 {
  if(index(name)==-1)return false;
  return true;
 }
//---------------------------------------------------------------------------
}
