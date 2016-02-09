/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AClass;
import aclass.AException;
import aclass.aparser.action.AAction;
import aclass.aparser.object.AObject;
import aclass.aparser.type.AType;

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
//---------------------------------------------------------------------------
 protected final AType inputType;
 protected final AType outputType;
//---------------------------------------------------------------------------
 protected final AAction action;
//---------------------------------------------------------------------------
 public AOperation(String name,AType inputType,AType outputType,AAction action)throws AException
 {
  if(name==null)throw new AException("name=null");
  if(inputType==null)throw new AException("inputType=null");
  if(outputType==null)throw new AException("outputType=null");
  if(action==null)throw new AException("action=null");
  this.name=name;
  this.inputType=inputType;
  this.outputType=outputType;
  this.action=action;
 }
//---------------------------------------------------------------------------
 public abstract void process(AObject input,AObject output)throws AException;
//---------------------------------------------------------------------------
 public AType getInputHeader(){return inputType;};
//---------------------------------------------------------------------------
 public AType getOutputHeader(){return outputType;};
//---------------------------------------------------------------------------
}
