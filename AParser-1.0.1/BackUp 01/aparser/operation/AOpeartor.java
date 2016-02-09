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
public class AOpeartor extends AOperation
{
//---------------------------------------------------------------------------
// public static int UNARY  =1;
// public static int BINARY =2;
// public static int TERNARY=3;
//---------------------------------------------------------------------------
 public AOpeartor()throws AException
 {
  super("operator",new AActionNULL());
 }
//---------------------------------------------------------------------------
// public String nameof();//+-/*.()[]...
//---------------------------------------------------------------------------
 @Override
 public void process(AUnion input,AUnion output) throws AException
 {
  throw new UnsupportedOperationException("Not supported yet.");
 }
//---------------------------------------------------------------------------
}
