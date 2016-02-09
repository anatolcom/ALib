/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.backup;

import aclass.aparser.backup.APortList;
import aclass.AClass;
import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.varible.AVaribleList;

/**
 *
 * @author Пользователь
 */
public class ASplitter extends AClass 
{
//---------------------------------------------------------------------------
 public final APortList operationList=new APortList();
 public final AVaribleList inputList=new AVaribleList();
 public final AVaribleList outputList=new AVaribleList();
//---------------------------------------------------------------------------
 public String process(AConstText text) throws AException
 {
  try
  {
   throw new AException("not realised");
  }
  catch(AException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------

//---------------------------------------------------------------------------
 public APortList getOperationList(){return operationList;}
 public AVaribleList getInputList(){return inputList;}
 public AVaribleList getOutputList(){return outputList;}
//---------------------------------------------------------------------------
}
