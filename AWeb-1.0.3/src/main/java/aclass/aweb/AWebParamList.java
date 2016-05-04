/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aweb;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Класс управляющий списком веб параметров {@link AWebParam}.<br />
 *
 * @author Anatol
 * @version 0.1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
{
})
@XmlRootElement(name = "WebParamList")
public class AWebParamList
{
//---------------------------------------------------------------------------
 @XmlElement(name = "TemplateInfo")
 private ArrayList<AWebParam> list = new ArrayList<AWebParam>();
//---------------------------------------------------------------------------
 public ArrayList<AWebParam> getList()
 {
  return list;
 }
//---------------------------------------------------------------------------
 public void add(AWebParam value)
 {
  list.add(value);
 }
//---------------------------------------------------------------------------
 public AWebParam get(int index)
 {
  return list.get(index);
 }
//---------------------------------------------------------------------------
 public void set(int index, AWebParam item)
 {
  list.set(index, item);
 }
//---------------------------------------------------------------------------
 public int count()
 {
  return list.size();
 }
//---------------------------------------------------------------------------
 public void remove(int index)
 {
  list.remove(index);
 }
//---------------------------------------------------------------------------
 public void clear()
 {
  list.clear();
 }
//---------------------------------------------------------------------------
}
