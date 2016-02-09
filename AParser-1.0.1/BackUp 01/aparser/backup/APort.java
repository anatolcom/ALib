/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;
import aclass.aparser.operation.AOperation;

/**
 *
 * @author Пользователь
 */
public class APort
{
//---------------------------------------------------------------------------
 private final String name;
//---------------------------------------------------------------------------
 private AOperation operation=null;
//---------------------------------------------------------------------------
 public APort(String name){this.name=name;}
//---------------------------------------------------------------------------
 public APort(String name,AOperation operation)
 {
  this.name=name;
  this.operation=operation;
 }
//---------------------------------------------------------------------------
 public String getName(){return name;}
//---------------------------------------------------------------------------
 public AOperation getOperation(){return operation;}
//---------------------------------------------------------------------------
 public void setOperation(AOperation operation){this.operation=operation;}
//---------------------------------------------------------------------------
}
