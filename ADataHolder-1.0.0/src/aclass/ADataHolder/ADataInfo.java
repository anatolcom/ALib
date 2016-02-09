/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AClass;
import aclass.AException;
import java.util.Date;
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
         "idHolder",
         "fileName",
         "md5",
         "name",
         "description",
         "size",
         "uploaded",
         "lastRead",
         "readCount"
        })
@XmlRootElement(name="DataInfo")
public class ADataInfo extends AClass
{
//---------------------------------------------------------------------------
 @XmlElement(name="id",required=true)
 private long id=(long)0;
 @XmlElement(name="idHolder",required=true)
 private long idHolder=(long)0;
 @XmlElement(name="FileName",required=true)
 private String fileName="";
 @XmlElement(name="MD5",required=true)
 private String md5="";
 @XmlElement(name="Name",required=true)
 private String name="";
 @XmlElement(name="Description",required=true)
 private String description="";
 @XmlElement(name="Size",required=true)
 private long size=(long)0;
 @XmlElement(name="Uploaded",required=true)
 private Date uploaded=null;
 @XmlElement(name="LastRead",required=true)
 private Date lastRead=null;
 @XmlElement(name="ReadCount",required=true)
 private int readCount=0;
//---------------------------------------------------------------------------
 public ADataInfo()
 {
 }
//---------------------------------------------------------------------------
 public ADataInfo(String name,String description,long size)
 {
  this.name=name;
  this.description=description;
  this.size=size;
 }
//---------------------------------------------------------------------------
 public final void assign(ADataInfo value) throws AException
 {
  if(value==null)throw new AException("value=null");
  id=value.id;
  idHolder=value.idHolder;
  fileName=value.fileName;
  md5=value.md5;
  name=value.name;
  description=value.description;
  size=value.size;
  if(value.uploaded==null)uploaded=null;
  else uploaded=new Date(value.uploaded.getTime());
  if(value.lastRead==null)lastRead=null;
  else lastRead=new Date(value.lastRead.getTime());
  readCount=value.readCount;
 }         
//---------------------------------------------------------------------------
 public ADataInfo(ADataInfo value)throws AException
 {
  assign(value);
 }
//---------------------------------------------------------------------------
 public Long getId(){return id;}
//---------------------------------------------------------------------------
 public void setId(Long id){this.id=id;}
//---------------------------------------------------------------------------
 public Long getIdHolder(){return idHolder;}
//---------------------------------------------------------------------------
 public void setIdHolder(Long idHolder){this.idHolder=idHolder;}
//---------------------------------------------------------------------------
 public String getFileName(){return fileName;}
//---------------------------------------------------------------------------
 public void setFileName(String fileName){this.fileName=fileName;}
//---------------------------------------------------------------------------
 public String getMD5(){return md5;}
//---------------------------------------------------------------------------
 public void setMD5(String md5){this.md5=md5;}
//---------------------------------------------------------------------------
 public String getName(){return name;}
//---------------------------------------------------------------------------
 public void setName(String Name){this.name=Name;}
//---------------------------------------------------------------------------
 public Long getSize(){return size;}
//---------------------------------------------------------------------------
 public void setSize(Long fileSize){this.size=fileSize;}
//---------------------------------------------------------------------------
 public String getDescription(){return description;}
//---------------------------------------------------------------------------
 public void setDescription(String description){this.description=description;}
//---------------------------------------------------------------------------
 public Date getUploaded(){return uploaded;}
//---------------------------------------------------------------------------
 public void setUploaded(Date uploaded){this.uploaded=uploaded;}
//---------------------------------------------------------------------------
/* public void nowUploaded()
 {
  Calendar cal=Calendar.getInstance();
  Uploaded.setTime(cal.getTime().getTime());
 }*/
//---------------------------------------------------------------------------
 public Date getLastRead(){return lastRead;}
//---------------------------------------------------------------------------
 public void setLastRead(Date lastRead){this.lastRead=lastRead;}
//---------------------------------------------------------------------------
// public void nowLastRead()
// {
//  Calendar cal=Calendar.getInstance();
//  lastRead.setTime(cal.getTime().getTime());
// }
//---------------------------------------------------------------------------
 public Integer getReadCount(){return readCount;}
//---------------------------------------------------------------------------
 public void setReadCount(Integer readCount){this.readCount=readCount;}
//---------------------------------------------------------------------------
 public void upReadCount(){this.readCount++;}
//---------------------------------------------------------------------------
}
