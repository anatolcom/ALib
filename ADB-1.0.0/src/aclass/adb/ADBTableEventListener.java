/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adb;

import java.util.EventListener;
/**
 *
 * @author Anatol
 */
public interface ADBTableEventListener extends EventListener
{
//---------------------------------------------------------------------------
 public void entryInsert(ADBTableEvent e);
 public void entryUpdate(ADBTableEvent e);
 public void entryDelete(ADBTableEvent e);
//---------------------------------------------------------------------------
}
