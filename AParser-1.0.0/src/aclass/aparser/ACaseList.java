/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser;
import aclass.ACustomPtrArray;
import aclass.AException;
import aclass.aparser.varible.AInt;
import aclass.aparser.varible.AStr;

/**
 *
 * @author Пользователь
 */
public class ACaseList extends ACustomPtrArray implements ACase
{
//---------------------------------------------------------------------------
/**
 * должны выполниться поочереди все случаи из списка.<br/>
 * выполнение происходит с продвижением на длинну лсучая.<br/>
 * возвращается суммарная длина всех случаев.<br/>
 * очерёдность определяется списком.<br/>
 */
 public static final int  ALL_MODE=0x0001;
/**
 * по возможности выполняются поочереди случаи из списка.<br/>
 * выполнение происходит с продвижением на длинну выпоненного лсучая.<br/>
 * возвращается суммарная длина выполненных случаев.<br/>
 * очерёдность определяется списком.<br/>
 */
 public static final int SOME_MODE=0x0002;
/**
 * должны выполниться все случаи из списка.<br/>
 * список просматривается до первого не выполнения, оставшиеся случаи не рассматриваются.<br/>
 * возвращается длина случая (все случаи обязанны быть единой длинны).<br/>
 * очерёдность определяется списком.<br/>
 */
 public static final int  AND_MODE=0x0004;
/**
 * должно выполниться хотябы одно условие из списка.<br/>
 * список просматривается до первого выполнения, оставшиеся случаи не рассматриваются.<br/>
 * возвращается длина случая.<br/>
 * очерёдность определяется списком.<br/>
 */
 public static final int   OR_MODE=0x0008;
/**
 * должно выполниться только одно условие из списка.<br/>
 * рассматриваются все случаи.<br/>
 * возвращается длина случая.<br/>
 * очерёдность определяется списком.<br/>
 */
 public static final int  XOR_MODE=0x000F;
//---------------------------------------------------------------------------
 public int mode=ALL_MODE;
//---------------------------------------------------------------------------
 public int add(ACase value){return PAdd(value);}
//---------------------------------------------------------------------------
 public ACase get(int index){return (ACase)PGetPtr(index);}
//---------------------------------------------------------------------------
 public void delete(int index){PDelete(index);}
//---------------------------------------------------------------------------
 public void move(int a,int b){PMove(a,b);}
//---------------------------------------------------------------------------
 public int count(){return PGetCount();}
//---------------------------------------------------------------------------
 @Override
 public boolean done(AStr text,int POS,AInt len) throws AException
 {
  if(len==null)throw new AException("len=null");
  len.value=done(text,POS);
  if(len.value==0)return false;
  return true;
 }
//---------------------------------------------------------------------------
 @Override
 public int done(AStr text,int POS) throws AException
 {
//  throw new AException(this,"done","not realised");
  if(text==null)throw new AException("text=null");
//  if(len==null)throw new AException(this,"done","len=null");
  if(count()==0)throw new AException("count=0");
  if(mode==ALL_MODE)
  {
   int pos=POS;
   for(int q=0;q<count();q++)
   {
    AInt sublen=new AInt();
    if(!get(q).done(text,pos,sublen))return 0;
    pos+=sublen.value;
   }
   return pos-POS;
  }
  if(mode==SOME_MODE)
  {
   int pos=POS;
   for(int q=0;q<count();q++)
   {
    AInt sublen=new AInt();
    if(!get(q).done(text,pos,sublen))continue;
    pos+=sublen.value;
   }
   return pos-POS;
  }
  if(mode==AND_MODE)
  {
   AInt len=new AInt();
   for(int q=0;q<count();q++)if(get(q).done(text,POS,len))return len.value;
   return 0;
  }
  if(mode==OR_MODE)
  {
   AInt len=new AInt();
   for(int q=0;q<count();q++)if(get(q).done(text,POS,len))return len.value;
   return 0;
  }
  if(mode==XOR_MODE)
  {
   int cnt=0;
   AInt len=new AInt();
   for(int q=0;q<count();q++)if(get(q).done(text,POS,len))cnt++;
   if(cnt==0)return len.value;
   return 0;
  }
  throw new AException("mode is incorrect",mode);
 }
//---------------------------------------------------------------------------
}
