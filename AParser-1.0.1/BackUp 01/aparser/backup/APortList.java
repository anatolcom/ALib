/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;

import aclass.ACustomPtrArray;
import aclass.AException;
import aclass.aparser.operation.AOperation;

/**
 *
 * @author Пользователь
 */
public class APortList extends ACustomPtrArray
{
//---------------------------------------------------------------------------
 public int add(String name,AOperation operation) throws AException
 {
  if(exists(name))throw new AException("Port with name=\""+name+"\" alredy exists");
  return PAdd(new APort(name,operation));
 }
//---------------------------------------------------------------------------
 public int add(APort port) throws AException
 {
  return add(port.getName(),port.getOperation());
 }
//---------------------------------------------------------------------------
 public int index(String name)throws AException
 {
  for(int q=0;q<count();q++)if(get(q).getName().equals(name))return q;
  return -1;
 }
//---------------------------------------------------------------------------
 public APort get(int index)throws AException{return (APort)PGetPtr(index);}
//---------------------------------------------------------------------------
 public AOperation getOperation(String name) throws AException
 {
  int index=index(name);
  if(index==-1)throw new AException("Port with name=\""+name+"\" not exists");
  return ((APort)PGetPtr(index)).getOperation();
 }
//---------------------------------------------------------------------------
 public void setOperation(String name,AOperation operation) throws AException
 {
  int index=index(name);
  if(index==-1)throw new AException("Port with name=\""+name+"\" not exists");
  ((APort)PGetPtr(index)).setOperation(operation);
 }
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{PDelete(index);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 public boolean exists(String name)throws AException
 {
  if(index(name)==-1)return false;
  return true;
 }
//---------------------------------------------------------------------------
}
