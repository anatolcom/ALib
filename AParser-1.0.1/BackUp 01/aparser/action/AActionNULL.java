/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.action;

import aclass.AException;
import aclass.aparser.core.AObjectSpace;
import aclass.aparser.varible.AObject;

/**
 *
 * @author Пользователь
 */
public class AActionNULL extends AAction
{
//---------------------------------------------------------------------------
 @Override
 public AObject run(AObjectSpace objectMap)throws AException{return null;/*бездействие*/}
//---------------------------------------------------------------------------
}
