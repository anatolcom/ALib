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
 @Override public int getSize(){return 0;}
//---------------------------------------------------------------------------
 @Override public int getType(){return TYPE_VOID;}
//---------------------------------------------------------------------------
 public AVoid(){}
//---------------------------------------------------------------------------
 public AVoid(AVoid data){}
//---------------------------------------------------------------------------
 @Override
 public void assign(AValue data) throws AException
 {
//  throw new AException("assign for void not realised");
  if(!data.isType(getType()))throw new AException("type missmatch");
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
 public AValue getCopy(){return new AVoid(this);}
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
