/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AClass;
import aclass.AException;
import aclass.aparser.action.AAction;
import aclass.aparser.varible.AUnion;
import aclass.aparser.varible.AUnionHeader;

/**
 * единица исполняемого кода
 * @author Пользователь
 */
public abstract class AOperation extends AClass
{
//---------------------------------------------------------------------------
// public static final int OPERATOR=   0x0001;
// public static final int FUNCTION=   0x0002;
// public static final int TYPECAST=   0x0003;
// public static final int CONSTRUCTOR=0x0004;
// public static final int DESTRUCTOR= 0x0005;
//---------------------------------------------------------------------------
 protected final String name;
 protected final AAction action;
//---------------------------------------------------------------------------
 public AOperation(String name,AAction action)throws AException
 {
  if(name==null)throw new AException("name=null");
  if(action==null)throw new AException("action=null");
  this.name=name;
  this.action=action;
 }
//---------------------------------------------------------------------------
 protected final AUnionHeader inputHeader=new AUnionHeader();
 protected final AUnionHeader outputHeader=new AUnionHeader();
//---------------------------------------------------------------------------
 public abstract void process(AUnion input,AUnion output)throws AException;
//---------------------------------------------------------------------------
 public AUnionHeader getInputHeader(){return inputHeader;};
//---------------------------------------------------------------------------
 public AUnionHeader getOutputHeader(){return outputHeader;};
//---------------------------------------------------------------------------
}
