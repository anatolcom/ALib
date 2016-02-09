/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AClass;
import aclass.AException;
import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 * Класс управляющий списком информации накопителей {@link AHolderInfo}.<br />
 * @author Anatol
 * @version 0.1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={})
@XmlRootElement(name="HolderInfoList")
public class AHolderInfoList extends AClass
{
//---------------------------------------------------------------------------
 @XmlElement(name="HolderInfo")
 private ArrayList<AHolderInfo> list=new ArrayList();
//---------------------------------------------------------------------------
 public final void assign(AHolderInfoList value) throws AException
 {
  if(value==null)throw new AException("value=mull");
  this.list.clear();
  for(int q=0;q<value.count();q++)this.list.add(value.list.get(q));
 }
//---------------------------------------------------------------------------
 public ArrayList<AHolderInfo> getList(){return list;}
//---------------------------------------------------------------------------
 public void add(AHolderInfo value) throws AException
 {
  if(value==null)throw new AException("value=mull");
  list.add(value);
 }
//---------------------------------------------------------------------------
 public AHolderInfo get(int index) throws AException
 {
  if(!inRange(index,0,list.size()))throw new AException("index out of range 0-"+list.size(),index);
  return list.get(index);
 }
//---------------------------------------------------------------------------
 public int count(){return list.size();}
//---------------------------------------------------------------------------
 public void remove(int index) throws AException
 {
  if(!inRange(index,0,list.size()))throw new AException("index out of range 0-"+list.size(),index);
  list.remove(index);
 }
//---------------------------------------------------------------------------
 public void clear(){list.clear();}
//---------------------------------------------------------------------------
}
