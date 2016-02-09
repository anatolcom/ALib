/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.type;

import aclass.AClass;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public abstract class ADescription extends AClass
{
//---------------------------------------------------------------------------
 public abstract ADescription copy()throws AException;
//---------------------------------------------------------------------------
}
