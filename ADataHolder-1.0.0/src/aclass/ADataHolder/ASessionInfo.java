/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AClass;
import aclass.AException;
import javax.xml.bind.annotation.*;
/**
 * Класс описывающий информацию о сессии.<br />
 * @author Anatol
 * @version 0.1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder=
        {
         "FId",
         "FMaxDataPartSize",
         "FMaxIdleTime"
        })
@XmlRootElement(name="SessionInfo")
public class ASessionInfo extends AClass
{
//---------------------------------------------------------------------------
/**
 * идентификатор сессии.
 */
 @XmlElement(name="id",required=true)
 private long FId=(long)-1;
/**
 * максимальны размер партии данных.
 */
 @XmlElement(name="MaxDataPartSize",required=true)
 private int FMaxDataPartSize=1024;//DEFAULT_MAX_DATA_PART_SIZE in ASessionList
/**
 * значение максимального времени бездействия
 */ 
 @XmlElement(name="MaxIdleTime",required=true)
 protected int FMaxIdleTime=32;//DEFAULT_MAX_IDLE_TIME in ASessionList
//---------------------------------------------------------------------------
/**
 * Конструктор.<br />
 */
 public ASessionInfo()
 {
 }
//---------------------------------------------------------------------------
/**
  * Метод выполняющий присвоение данных.<br />
  * @param value - экземпряр класса.
  */
 public final void assign(ASessionInfo value) throws AException
 {
  if(value==null)throw new AException("value=null");
  this.FId=value.FId;
  this.FMaxDataPartSize=value.FMaxDataPartSize;
 }         
//---------------------------------------------------------------------------
/**
 * Конструктор присвоения.<br />
 * @param value экземпляр класса ASessionInfo, данные которого будут скопированны.
 * @throws AException
 */
 public ASessionInfo(ASessionInfo value)throws AException
 {
  assign(value);
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращающий идентификатор сессии.<br />
 * @return идентификатор сессии.
 */
 public long getId(){return FId;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливающий идентификатор сессии равным id.<br />
 * @param id идентификатор сессии.
 */
 public void setId(long id){this.FId=id;}
//---------------------------------------------------------------------------
/**
 * Метод возвращающий максимальны размер партии данных.<br />
 * @return максимальны размер партии данных
 */
 public int getMaxDataPartSize(){return FMaxDataPartSize;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливающий максимальны размер партии данных равным maxDataPartSize.<br />
 * @param maxDataPartSize максимальны размер партии данных.
 */
 public void setMaxDataPartSize(int maxDataPartSize){this.FMaxDataPartSize=maxDataPartSize;}
//---------------------------------------------------------------------------
/**
 * Метод возвращает значение максимального времени бездействия.<br />
 * @return значение в секундах.
 */
 public int getMaxIdleTime(){return FMaxIdleTime;}
//---------------------------------------------------------------------------
/**
 * Метод устанавливает значение максимального времени бездействия.<br />
 * @param maxIdleTime значение в секундах.
 */
 public void setMaxIdleTime(int maxIdleTime){this.FMaxIdleTime=maxIdleTime;}
//---------------------------------------------------------------------------
}
