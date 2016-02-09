/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anatol.aweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Anatol
 * @version 0.1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
{
 "name", "value"
})
@XmlRootElement(name = "WebParam")
public class AWebParam
{
//---------------------------------------------------------------------------
 @XmlAttribute(name = "Name", required = true)
 public String name = null;
 @XmlElement(name = "Value", required = true)
 public String value = null;
//---------------------------------------------------------------------------
 public AWebParam()
 {
 }
//---------------------------------------------------------------------------
 public AWebParam(String name, String value)
 {
  this.name = name;
  this.value = value;
 }
//---------------------------------------------------------------------------
 public AWebParam(AWebParam value) throws Exception
 {
  assign(value);
 }
//---------------------------------------------------------------------------
 public final void assign(AWebParam value) throws Exception
 {
  if (value == null) throw new Exception("value=null");
  this.name = value.name;
  this.value = value.value;
 }
//---------------------------------------------------------------------------
 public String getName()
 {
  return name;
 }
//---------------------------------------------------------------------------
 public void setName(String name)
 {
  this.name = name;
 }
//---------------------------------------------------------------------------
 public String getValue()
 {
  return value;
 }
//---------------------------------------------------------------------------
 public void setValue(String value)
 {
  this.value = value;
 }
//---------------------------------------------------------------------------
}
