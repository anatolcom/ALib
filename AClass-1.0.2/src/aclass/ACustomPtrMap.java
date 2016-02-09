/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 *
 * @author Anatol
 * @version 0.1.0.0
 */
//---------------------------------------------------------------------------
//---------------------------------------------------<     C L A S S     >---
//---------------------------------------------------------------------------
public class ACustomPtrMap extends AClass
{
//---------------------------------------------------------------------------
 private APtrArray ptrArray=new APtrArray();
//---------------------------------------------------------------------------
 private class AItem
 {
  public final String name;
  public Object ptr;
  public AItem(String name,Object ptr)
  {
   this.name=name;
   this.ptr=ptr;
  }
  public boolean isName(String name)
  {
   if(this.name.equals(name))return true;
   return false;
  }
 }
//---------------------------------------------------------------------------
 public final boolean sorted;
//---------------------------------------------------------------------------
 public ACustomPtrMap(boolean sorted){this.sorted=sorted;}
//---------------------------------------------------------------------------
 private AItem getItem(int index)throws AException{return (AItem)ptrArray.getPtr(index);}
//---------------------------------------------------------------------------
 private AItem getItem(String name)throws AException
 {
  if(name==null)throw new AException("name=null");
  for(int q=0;q<ptrArray.getCount();q++)
  {
   AItem item=getItem(q);
   if(item.isName(name))return item;
  }
  throw new AException("name=\""+name+"\" not found");
 }
//---------------------------------------------------------------------------
 private int getIndex(String name)throws AException
 {
  if(name==null)throw new AException("name=null");
  for(int q=0;q<ptrArray.getCount();q++)if(getItem(q).isName(name))return q;
  throw new AException("name=\""+name+"\" not found");
 }
//---------------------------------------------------------------------------
 private int insertIndex(String name)throws AException
 {
  if(name==null)throw new AException("name=null");
  for(int q=0;q<ptrArray.getCount();q++)
  {
   int cmp=getItem(q).name.compareTo(name);
   if(cmp==0)return -1;
   if(cmp>0)return q;
  }
  return ptrArray.getCount();
 }
//---------------------------------------------------------------------------
 protected void PAdd(String name)throws AException{PAdd(name,null);}
//---------------------------------------------------------------------------
 protected void PAdd(String name,Object ptr)throws AException
 {
  if(name==null)throw new AException("name=null");
  int index=insertIndex(name);
  if(index==-1)throw new AException("name=\""+name+"\" alredy exists");
  AItem item=new AItem(name,ptr);
  if(sorted)ptrArray.insert(item,index);
  else ptrArray.add(item);
 }
//---------------------------------------------------------------------------
 protected void PSetPtr(int index,Object ptr)throws AException{getItem(index).ptr=ptr;}
//---------------------------------------------------------------------------
 protected void PSetPtr(String name,Object ptr)throws AException{getItem(name).ptr=ptr;}
//---------------------------------------------------------------------------
 protected Object PGetPtr(int index)throws AException{return getItem(index).ptr;}
//---------------------------------------------------------------------------
 protected Object PGetPtr(String name)throws AException{return getItem(name).ptr;}
//---------------------------------------------------------------------------
 protected String PGetName(int index)throws AException{return getItem(index).name;}
//---------------------------------------------------------------------------
 protected void PDelete(int index)throws AException{ptrArray.delete(index);}
//---------------------------------------------------------------------------
 protected void PDelete(String name)throws AException{ptrArray.delete(getIndex(name));}
//---------------------------------------------------------------------------
 protected void PSwap(int a,int b)throws AException
 {
  if(sorted)throw new AException("map is sorted");
  ptrArray.swap(a,b);
 }
//---------------------------------------------------------------------------
 protected void PMove(int a,int b)throws AException
 {
  if(sorted)throw new AException("map is sorted");
  ptrArray.move(a,b);
 }
//---------------------------------------------------------------------------
 protected void PSwap(String aName,String bName)throws AException
 {
  if(sorted)throw new AException("map is sorted");
  ptrArray.swap(getIndex(aName),getIndex(bName));
 }
//---------------------------------------------------------------------------
 protected void PMove(String aName,String bName)throws AException
 {
  if(sorted)throw new AException("map is sorted");
  ptrArray.move(getIndex(aName),getIndex(bName));
 }
//---------------------------------------------------------------------------
 protected int PGetIndex(String name)
 {
  try
  {
   if(name==null)throw new AException("name=null");
   for(int q=0;q<ptrArray.getCount();q++)if(getItem(q).isName(name))return q;
  }
  catch(AException ex){sendError("PGetIndex",ex.getMessage(),null);}
  return -1;
 }
//---------------------------------------------------------------------------
 protected int PCount(){return ptrArray.getCount();}
//---------------------------------------------------------------------------
 protected void PClear(){ptrArray.clear();}
//---------------------------------------------------------------------------
 protected boolean PExists(String name)
 {
  if(PGetIndex(name)==-1)return false;
  return true;
 }
//---------------------------------------------------------------------------
}
