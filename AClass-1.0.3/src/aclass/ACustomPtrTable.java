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
public class ACustomPtrTable extends AClass
{
//---------------------------------------------------------------------------
 private APtrArray ptrArray=new APtrArray();
//---------------------------------------------------------------------------
 private int autoincId;
//---------------------------------------------------------------------------
 private class AItem
 {
  public final int id;
  public Object ptr;
  public AItem(int id,Object ptr)
  {
   this.id=id;
   this.ptr=ptr;
  }
  public boolean isId(int id)
  {
   if(this.id==id)return true;
   return false;
  }
 }
//---------------------------------------------------------------------------
 public final boolean sorted;
//---------------------------------------------------------------------------
 public ACustomPtrTable(boolean sorted,int autoincId)
 {
  this.sorted=sorted;
  this.autoincId=autoincId;
 }
//---------------------------------------------------------------------------
// private int getAutoincId(){return autoincId;}
//---------------------------------------------------------------------------
 protected int PNewAutoincId(){return ++autoincId;}
//---------------------------------------------------------------------------
 private void updateAutoincId(int id){if(autoincId<id)autoincId=id;}
//---------------------------------------------------------------------------
 private AItem getItemByIndex(int index)throws AException{return (AItem)ptrArray.getPtr(index);}
//---------------------------------------------------------------------------
 private AItem getItemById(int id)throws AException
 {
  if(id<=0)throw new AException("id<=0");
  for(int q=0;q<ptrArray.getCount();q++)
  {
   AItem item=getItemByIndex(q);
//   System.out.println("item:"+item.id);
   if(item.isId(id))return item;
  }
  throw new AException("id=\""+id+"\" not found");
 }
//---------------------------------------------------------------------------
 private int getIndexById(int id)throws AException
 {
  if(id<=0)throw new AException("id<=0");
  for(int q=0;q<ptrArray.getCount();q++)if(getItemByIndex(q).isId(id))return q;
  throw new AException("id=\""+id+"\" not found");
 }
//---------------------------------------------------------------------------
 private int insertIndex(int id)throws AException
 {
  if(id<=0)throw new AException("id<=0");
  for(int q=0;q<ptrArray.getCount();q++)
  {
   if(getItemByIndex(q).id==id)return -1;
   if(getItemByIndex(q).id>id)return q;
  }
  return ptrArray.getCount();
 }
//---------------------------------------------------------------------------
/**
 * 
 * @param id идентификатор будущей записи или 0. Если id=0 то id вычисляется автоматически.
 * @return id новой записи.
 * @throws AException в случае ошибки.
 */
 protected int PAdd(int id)throws AException{return PAdd(id,null);}
//---------------------------------------------------------------------------
/**
 * 
 * @param id идентификатор будущей записи или 0. Если id=0 то id вычисляется автоматически.
 * @param ptr ссылка на объект.
 * @return id новой записи.
 * @throws AException в случае ошибки.
 */
 protected int PAdd(int id,Object ptr)throws AException
 {
  if(id<0)throw new AException("id<0");
  if(id==0)id=PNewAutoincId();
  else updateAutoincId(id);
  int index=insertIndex(id);
  if(index==-1)throw new AException("id=\""+id+"\" alredy exists");
  AItem item=new AItem(id,ptr);
  if(sorted)ptrArray.insert(item,index);
  else ptrArray.add(item);
  return id;
 }
//---------------------------------------------------------------------------
 protected void PSetPtrByIndex(int index,Object ptr)throws AException{getItemByIndex(index).ptr=ptr;}
//---------------------------------------------------------------------------
 protected void PSetPtr(int id,Object ptr)throws AException{getItemById(id).ptr=ptr;}
//---------------------------------------------------------------------------
 protected Object PGetPtrByIndex(int index)throws AException{return getItemByIndex(index).ptr;}
//---------------------------------------------------------------------------
 protected Object PGetPtr(int id)throws AException{return getItemById(id).ptr;}
//---------------------------------------------------------------------------
 protected int PGetId(int index)throws AException{return getItemByIndex(index).id;}
//---------------------------------------------------------------------------
 protected void PDeleteByIndex(int index)throws AException{ptrArray.delete(index);}
//---------------------------------------------------------------------------
 protected void PDelete(int id)throws AException{ptrArray.delete(getIndexById(id));}
//---------------------------------------------------------------------------
 protected void PSwapByIndex(int a,int b)throws AException
 {
  if(sorted)throw new AException("table is sorted");
  ptrArray.swap(a,b);
 }
//---------------------------------------------------------------------------
 protected void PMoveByIndex(int a,int b)throws AException
 {
  if(sorted)throw new AException("table is sorted");
  ptrArray.move(a,b);
 }
//---------------------------------------------------------------------------
 protected void PSwap(int aId,int bId)throws AException
 {
  if(sorted)throw new AException("table is sorted");
  ptrArray.swap(getIndexById(aId),getIndexById(bId));
 }
//---------------------------------------------------------------------------
 protected void PMove(int aId,int bId)throws AException
 {
  if(sorted)throw new AException("table is sorted");
  ptrArray.move(getIndexById(aId),getIndexById(bId));
 }
//---------------------------------------------------------------------------
 protected int PGetIndex(int id)
 {
  try
  {
   if(id<=0)throw new AException("id<=0");
   for(int q=0;q<ptrArray.getCount();q++)if(getItemByIndex(q).isId(id))return q;
  }
  catch(AException ex){sendError("PGetIndex",ex.getMessage());}
  return -1;
 }
//---------------------------------------------------------------------------
 protected int PCount(){return ptrArray.getCount();}
//---------------------------------------------------------------------------
 protected void PClear(){ptrArray.clear();}
//---------------------------------------------------------------------------
 protected boolean PExists(int id)
 {
  if(PGetIndex(id)==-1)return false;
  return true;
 }
//---------------------------------------------------------------------------
}
