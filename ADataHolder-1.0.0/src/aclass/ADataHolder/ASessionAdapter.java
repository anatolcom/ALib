/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;
import aclass.AClass;
import aclass.AException;

/**
 * 
 * @author Anatol
 * @version 0.1.0.0
 */
public class ASessionAdapter extends AClass implements ASessionListener 
{
//---------------------------------------------------------------------------
 @Override
 public void openSession(ASessionEvent evt) throws AException
 {
  sendWarning("openSession","This function is not developed",null);
 }
//---------------------------------------------------------------------------
 @Override
 public void closeSession(ASessionEvent evt) throws AException
 {
  sendWarning("closeSession","This function is not developed",null);
 }
//---------------------------------------------------------------------------
}
