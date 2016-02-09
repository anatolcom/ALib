/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import javax.xml.bind.annotation.*;
/**
 * Класс описывающий партию данных.<br />
 * @author Anatol
 * @version 0.1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder=
        {
         "FIdSession",
         "FIndex",
         "FData",
         "FSize"
        })
@XmlRootElement(name="DataPart")
public class ADataPart
{
 //---------------------------------------------------------------------------
 @XmlElement(name="idSession",required=true)
 private long FIdSession=(long)0;
 @XmlElement(name="Index",required=true)
 private int FIndex=-1;
 @XmlElement(name="Data",required=true)
 private String FData="";
 @XmlElement(name="Size",required=true)
 private int FSize=0;
//---------------------------------------------------------------------------
/**
 * Метод возвращает идентификатор сессии.<br />
 * @return идентификатор сессии.
 */
 public long getIdSession(){return FIdSession;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает идентификатор сессии равным idSession.<br />
 * @param idSession идентификатор сессии.
 */
 public void setIdSession(long idSession){this.FIdSession=idSession;}
//---------------------------------------------------------------------------
/**
 * Метод возвращает индекс партии данных.<br />
 * @return индекс партии данных.
 */
 public int getIndex(){return FIndex;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает индекс партии данных равным index.<br />
 * @param index индекс партии данных.
 */
 public void setIndex(int index){this.FIndex=index;}
//---------------------------------------------------------------------------
/**
 * Метод возвращает данные.<br />
 * @return данные.
 */
 public String getData(){return FData;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает данные равными data.<br />
 * @param data данные.
 */
 public void setData(String data){this.FData=data;}
//---------------------------------------------------------------------------
/**
 * Метод возвращает размер партии данных.<br />
 * @return размер партии данных.
 */
 public int getSize(){return FSize;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает размер партии данных равным size.<br />
 * @param size размер партии данных.
 */
 public void setSize(int size){this.FSize=size;}
//---------------------------------------------------------------------------
}
