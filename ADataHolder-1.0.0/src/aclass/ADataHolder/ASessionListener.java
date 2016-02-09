/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AException;
import java.util.EventListener;
/**
 *
 * @author Anatol
 * @version 0.1.0.0
 */
public interface ASessionListener extends EventListener
{
//---------------------------------------------------------------------------
 public void openSession(ASessionEvent evt) throws AException;
 public void closeSession(ASessionEvent evt) throws AException;
//---------------------------------------------------------------------------
}
