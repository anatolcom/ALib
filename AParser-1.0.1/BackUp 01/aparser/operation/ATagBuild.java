/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.operation;

import aclass.AException;
import aclass.aparser.action.AActionNULL;
import aclass.aparser.varible.AUnion;

/**
 *
 * @author Пользователь
 */
public class ATagBuild extends AOperation
{
//---------------------------------------------------------------------------
 public ATagBuild()throws AException
 {
  super("tagBuild",new AActionNULL());
 }
//---------------------------------------------------------------------------
 @Override
 public void process(AUnion input,AUnion output) throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
