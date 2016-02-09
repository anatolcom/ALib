/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;

import aclass.ACustomPtrArray;
import aclass.AException;


/**
 *
 * @author Пользователь
 */
public class AXSDSchemaMap extends ACustomPtrArray
{
//---------------------------------------------------------------------------
// private ArrayList<AXSDSchema> FList=new ArrayList();
//---------------------------------------------------------------------------
 public void add(AXSDSchema value)throws AException
 {
  if(value==null)throw new AException("value=null");
  if(value.getNamespaceURI().isEmpty())throw new AException("NamespaceURI of value is empty");
  for(int q=0;q<PGetCount();q++)
  {
   AXSDSchema schema=(AXSDSchema)PGetPtr(q);
   int cmp=schema.getNamespaceURI().compareTo(value.getNamespaceURI());
   if(cmp<0)continue;
   if(cmp==0)
   {
    sendWarning("add","Schema with NamespaceURI \""+schema.getNamespaceURI()+"\" alredy exists",q);
    return;
   }
   PInsert(value,q);
   return;
  }
  PAdd(value);
 }
//---------------------------------------------------------------------------
 public int getIndex(String namespaceURI)throws AException
 {
  if(namespaceURI==null)throw new AException("namespaceURI=null");
  for(int q=0;q<PGetCount();q++)
  {
   AXSDSchema schema=(AXSDSchema)PGetPtr(q);
   String ns=schema.getNamespaceURI();
   int cmp=ns.compareTo(namespaceURI);
   if(cmp==0)return q;
   if(cmp>0)break;
  }
  return -1;
 }
//---------------------------------------------------------------------------
 public AXSDSchema get(String namespaceURI)throws AException
 {
  for(int q=0;q<PGetCount();q++)
  {
   AXSDSchema schema=(AXSDSchema)PGetPtr(q);
   String ns=schema.getNamespaceURI();
   int cmp=ns.compareTo(namespaceURI);
   if(cmp==0)return schema;
   if(cmp>0)break;
  }
  return null;
 }
//---------------------------------------------------------------------------
 public void clear(){PClear();}
//---------------------------------------------------------------------------
 public int size(){return PGetCount();}
//---------------------------------------------------------------------------
 public AXSDSchema get(int index)throws AException {return (AXSDSchema)PGetPtr(index);}
//---------------------------------------------------------------------------
}
