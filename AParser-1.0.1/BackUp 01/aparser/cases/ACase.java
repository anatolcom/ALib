/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.cases;
import aclass.AClass;
import aclass.AException;
import aclass.aparser.AConstText;
import aclass.aparser.backup.APortList;
import aclass.aparser.varible.basic.AInt;
import aclass.aparser.varible.AVaribleList;


/**
 *
 * @author Anatol
 */
public abstract class ACase extends AClass
{
//---------------------------------------------------------------------------
 protected String textCase=null;
 protected String textProcess=null;
//---------------------------------------------------------------------------
 protected APortList portList=new APortList();
 protected AVaribleList varibleList=new AVaribleList();
//---------------------------------------------------------------------------
/**
 * 
 * @param TEXT
 * @param POS
 * @return true - если это тот случай<br />
 *         false - если это не тот случай
 * длинна подтекста последнего случаю может быть считана из lenght()
 * @throws AException 
 */
 public abstract boolean done(AConstText TEXT,final int POS)throws AException;
//---------------------------------------------------------------------------
// public boolean done(AConstText TEXT,final int POS,AInt len)throws AException
// {
//  if(len==null) throw new AException("len=null");
//  len.setInt(done(TEXT,POS));
//  if(len.getInt()==0)return false;
//  return true;
// }
//---------------------------------------------------------------------------
/**
 * 
 * @return длинну последнего случаяя, 0 если случая небыло
 */ 
 public int lenght()
 {
  if(textCase==null)return 0;
  return textCase.length();
 }
//---------------------------------------------------------------------------
/**
 * 
 * @return оригинальный текст последнего случаяя, "" если случая небыло
 */ 
 public String getTextCase()
 {
  if(textCase==null)return "";
  return textCase;
 }
//---------------------------------------------------------------------------
/**
 * 
 * @return обработаный текст последнего случаяя
 */ 
 public String getTextProcess()
 {
  if(textProcess==null)return "";
  return textProcess;
 }
//---------------------------------------------------------------------------
 public APortList getPortList(){return portList;}
//---------------------------------------------------------------------------
 public AVaribleList getVaribleList(){return varibleList;}
//---------------------------------------------------------------------------
}
