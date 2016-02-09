/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser;
import aclass.AException;
import aclass.aparser.varible.AInt;
import aclass.aparser.varible.AStr;


/**
 *
 * @author Anatol
 */
public interface ACase
{
//---------------------------------------------------------------------------
 /**
  * 
  * @param TEXT
  * @param POS
  * @return длинна подтекста соответсвующего случаю<br />
  *         0 если это не тот случай
  * @throws AException 
  */
 public int done(AStr TEXT,final int POS)throws AException;
//---------------------------------------------------------------------------
// @Deprecated
 public boolean done(AStr TEXT,final int POS,AInt len)throws AException;
//---------------------------------------------------------------------------
}
