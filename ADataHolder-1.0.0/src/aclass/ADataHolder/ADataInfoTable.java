/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AClass;
import aclass.AException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Пользователь
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"list","currentId"})
@XmlRootElement(name="DataInfoTable")
public class ADataInfoTable extends AClass
{
//---------------------------------------------------------------------------
 @XmlElement(name="DataInfoList")
 private ADataInfoList list=new ADataInfoList();
 @XmlElement(name="CurrentId")
 private long currentId=1;
//---------------------------------------------------------------------------
 public final void assign(ADataInfoTable value) throws AException
 {
  if(value==null)throw new AException("value=null");
  this.list.assign(value.list);
  this.currentId=value.currentId;
 }
//---------------------------------------------------------------------------
 public ADataInfoList getDataInfoList(){return list;}
//---------------------------------------------------------------------------
 public void insert(ADataInfo item) throws AException
 {
  item.setId(newCurrentId());
  list.add(item);
//  if(currentId<=item.getId())currentId=item.getId()+1;
 }
//---------------------------------------------------------------------------
 public void update(ADataInfo item) throws AException
 {
  ADataInfo info=getById(item.getId());
  if(info==null)throw new AException("Item with id="+item.getId()+" not found");
  info.assign(item);    
 }
//---------------------------------------------------------------------------
 public void delete(long id) throws AException
 {
  list.remove(getIndex(id));
 }
//---------------------------------------------------------------------------
 public int getIndex(long id)
 {
  for(int q=0;q<list.count();q++)if(list.getList().get(q).getId()==id)return q;
  return -1;
 }
//---------------------------------------------------------------------------
 public ADataInfo getById(long id) throws AException
 {
  for(int q=0;q<list.count();q++)
  {
   ADataInfo field=list.get(q);
   if(field.getId()==id)return field;
  }
  throw new AException("Item with id="+id+" alredy exists");
 }
//---------------------------------------------------------------------------
 public long newCurrentId(){return currentId++;}
//---------------------------------------------------------------------------
 public long getCurrentId(){return currentId;}
//---------------------------------------------------------------------------
 public void setCurrentId(long value){currentId=value;}
//---------------------------------------------------------------------------
 public void mathCurrentId()
 {
  for(int q=0;q<list.count();q++)
  {
   ADataInfo field=list.getList().get(q);
   if(currentId<=field.getId())currentId=field.getId()+1;
  }
 }
//---------------------------------------------------------------------------
}
