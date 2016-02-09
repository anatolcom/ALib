/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

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
@XmlType(propOrder={"idCurrentHolder"})
@XmlRootElement(name="DataHolderState")
public class ADataHolderState
{
//---------------------------------------------------------------------------
 @XmlElement(name="IdCurrentHolder",required=true)
 private long idCurrentHolder=(long)0;
//---------------------------------------------------------------------------
 public long getIdCurrentHolder(){return idCurrentHolder;}
//---------------------------------------------------------------------------
 public void setIdCurrentHolder(long idCurrentHolder){this.idCurrentHolder=idCurrentHolder;}
//---------------------------------------------------------------------------
}
