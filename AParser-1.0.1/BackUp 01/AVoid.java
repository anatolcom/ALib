/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.varible;
import aclass.AException;

/**
 *
 * @author Пользователь
 */
public class AVoid extends AValue
{
//---------------------------------------------------------------------------
// @Override public int count(){return 0;}
//---------------------------------------------------------------------------
 public AVoid() throws AException{super(VOID,AValue.VARIBLE);}
//---------------------------------------------------------------------------
 @Override
 public void assign(AValue data) throws AException
 {
//  throw new AException("assign for void not realised");
  throw new AException("void could not be assigned");
//  if(!data.isType(getType()))throw new AException("type missmatch");
 }
//---------------------------------------------------------------------------
 @Override
 public int compare(AValue data) throws AException
 {
  throw new AException("compare for void not realised");
//  if(!data.isType(getType()))throw new AException("type missmatch");
//  return 0;
 }
//---------------------------------------------------------------------------
 @Override
 public AValue getCopy() throws AException{return new AVoid();}
//---------------------------------------------------------------------------
 @Override
 public String toStr()throws AException
 {
//  throw new AException("void not convert to str");
  return "void";
 }
//---------------------------------------------------------------------------
 @Override
 public void fromStr(String value)throws AException
 {
  throw new AException("void not convert from str");
 }
//---------------------------------------------------------------------------
}
