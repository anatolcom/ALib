/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AClass;
import aclass.AException;
import javax.xml.bind.annotation.*;
/**
 *
 * @author Anatol
 * @version 0.1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder=
        {
         "id",
         "name",
         "addres",
         "comment"
        })
@XmlRootElement(name="HolderInfo")
public class AHolderInfo extends AClass
{
//---------------------------------------------------------------------------
 @XmlElement(name="id",required=true)
 protected long id=(long)0;
 @XmlElement(name="Name",required=true)
 protected String name="";
 @XmlElement(name="Addres",required=true)
 protected String addres="";
 @XmlElement(name="Comment",required=true)
 protected String comment="";
//---------------------------------------------------------------------------
 public AHolderInfo()
 {
 }
//---------------------------------------------------------------------------
 public final void assign(AHolderInfo value) throws AException
 {
  if(value==null)throw new AException("value=null");
  id=value.id;
  name=value.name;
  addres=value.addres;
  comment=value.comment;
 }         
//---------------------------------------------------------------------------
 public AHolderInfo(AHolderInfo value)throws AException
 {
  assign(value);
 }
//---------------------------------------------------------------------------
 public AHolderInfo(Long id,String name,String addres,String comment)
 {
  this.id=id;
  this.name=name;
  this.addres=addres;
  this.comment=comment;
 }
//---------------------------------------------------------------------------
 public Long getId(){return id;}
//---------------------------------------------------------------------------
 public void setId(Long id){this.id=id;}
//---------------------------------------------------------------------------
 public String getName(){return name;}
//---------------------------------------------------------------------------
 public void setName(String name){this.name=name;}
//---------------------------------------------------------------------------
 public String getAddres(){return addres;}
//---------------------------------------------------------------------------
 public void setAddres(String addres){this.addres=addres;}
//---------------------------------------------------------------------------
 public String getComment(){return comment;}
//---------------------------------------------------------------------------
 public void setComment(String comment){this.comment=comment;}
//---------------------------------------------------------------------------
}
