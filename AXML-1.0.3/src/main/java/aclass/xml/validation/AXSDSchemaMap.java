/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;

import static aclass.AClass.warning;
import aclass.AException;
import java.util.ArrayList;

/**
 *
 * @author Пользователь
 */
public class AXSDSchemaMap
{
//---------------------------------------------------------------------------
 private ArrayList<AXSDSchema> FList = new ArrayList();
//---------------------------------------------------------------------------
 public void add(AXSDSchema value) throws AException
 {
  if (value == null) throw new AException("value = null");
//  if(value.getNamespaceURI().isEmpty())throw new AException("NamespaceURI of value is empty");
  for (int q = 0; q < FList.size(); q++)
  {
   AXSDSchema schema = FList.get(q);
   int cmp = schema.getNamespaceURI().compareTo(value.getNamespaceURI());
   if (cmp < 0) continue;
   if (cmp == 0)
   {
    warning("add", "Schema with NamespaceURI \"" + schema.getNamespaceURI() + "\" alredy exists. " + q);
    return;
   }
   FList.add(q, value);
   return;
  }
  FList.add(value);
 }
//---------------------------------------------------------------------------
 public int getIndex(String namespaceURI) throws AException
 {
  if (namespaceURI == null) throw new AException("namespaceURI = null");
  for (int q = 0; q < FList.size(); q++)
  {
   AXSDSchema schema = FList.get(q);
   String ns = schema.getNamespaceURI();
   int cmp = ns.compareTo(namespaceURI);
   if (cmp == 0) return q;
   if (cmp > 0) break;
  }
  return -1;
 }
//---------------------------------------------------------------------------
 public AXSDSchema get(String namespaceURI) throws AException
 {
  for (AXSDSchema schema : FList)
  {
   String ns = schema.getNamespaceURI();
   int cmp = ns.compareTo(namespaceURI);
   if (cmp == 0) return schema;
   if (cmp > 0) break;
  }
  return null;
 }
//---------------------------------------------------------------------------
 public void clear()
 {
  FList.clear();
 }
//---------------------------------------------------------------------------
 public int size()
 {
  return FList.size();
 }
//---------------------------------------------------------------------------
 public AXSDSchema get(int index) throws AException
 {
  return FList.get(index);
 }
//---------------------------------------------------------------------------
}
