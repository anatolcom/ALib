/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.adb;

import java.util.EventObject;
/**
 *
 * @author Anatol
 */
public class ADBTableEvent extends EventObject
{
//---------------------------------------------------------------------------
// public static final int CHANGE = 0;
 public static final int INSERT = 1;
 public static final int UPDATE = 2;
 public static final int DELETE = 3;
//---------------------------------------------------------------------------
 private int FMode;
 private ADBEntry FEntry;
 private boolean FSuccess;
//---------------------------------------------------------------------------
 public ADBTableEvent(Object src,int mode,ADBEntry entry)
 {
  super(src);
  FMode=mode;
  FEntry=entry;
  FSuccess=true;
 }
//---------------------------------------------------------------------------
 public ADBEntry getEntry()
 {
  return FEntry;
 }
//---------------------------------------------------------------------------
 public int getMode()
 {
  return FMode;
 }
//---------------------------------------------------------------------------
 public boolean isSuccess()
 {
  return FSuccess;
 }
//---------------------------------------------------------------------------
 public void setSuccess(boolean success)
 {
  this.FSuccess=success;
 }
//---------------------------------------------------------------------------
 @Override
 public Object getSource()
 {
  return super.getSource();
 }
//---------------------------------------------------------------------------
 @Override
 public String toString()
 {
  return "ADBTableEvent{"+"}";
 }
//---------------------------------------------------------------------------
}
