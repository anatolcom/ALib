/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aparser.cases;
import aclass.AException;
import aclass.APtrArray;
import aclass.aparser.AConstText;

/**
 *
 * @author Пользователь
 */
public class ACaseList extends ACase
{
//---------------------------------------------------------------------------
 private APtrArray ptrArray=new APtrArray();
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
 public int add(ACase value)throws AException{return ptrArray.add(value);}
//---------------------------------------------------------------------------
 public ACase get(int index)throws AException{return (ACase)ptrArray.getPtr(index);}
//---------------------------------------------------------------------------
 public void delete(int index)throws AException{ptrArray.delete(index);}
//---------------------------------------------------------------------------
 public void move(int a,int b)throws AException{ptrArray.move(a,b);}
//---------------------------------------------------------------------------
 public int count(){return ptrArray.getCount();}
//---------------------------------------------------------------------------
 @Override
 public boolean done(AConstText text,int POS) throws AException
 {
  throw new AException("under development");
/*  if(text==null)throw new AException("text=null");
//  if(len==null)throw new AException(this,"done","len=null");
  if(count()==0)throw new AException("count=0");
  if(mode==ALL_MODE)
  {
   int pos=POS;
   for(int q=0;q<count();q++)
   {
    AInt sublen=new AInt();
    if(!get(q).done(text,pos,sublen))
    {
     textCase=null;
     return false;
    }
    pos+=sublen.getInt();
//    int sublen=get(q).done(getTextCase,pos);
//    if(sublen==0)return 0;
//    pos+=sublen;
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
    pos+=sublen.getInt();
//    int sublen=get(q).done(getTextCase,pos);
//    if(sublen==0)continue;
//    pos+=sublen;
   }
   return pos-POS;
  }
  if(mode==AND_MODE)
  {
//   AInt len=new AInt();
   for(int q=0;q<count();q++)if(get(q).done(text,POS))return true;//len.getInt();
//   for(int len,q=0;q<count();q++)if((len=get(q).done(getTextCase,POS))!=0)return len;
   textCase=null;
   return false;
  }
  if(mode==OR_MODE)
  {
//   AInt len=new AInt();
   for(int q=0;q<count();q++)if(get(q).done(text,POS))return true;//len.getInt();
//   for(int len,q=0;q<count();q++)if((len=get(q).done(getTextCase,POS))!=0)return len;
   textCase=null;
   return false;
  }
  if(mode==XOR_MODE)
  {
   int cnt=0;
   AInt len=new AInt();
   for(int q=0;q<count();q++)if(get(q).done(text,POS,len))cnt++;
   if(cnt==0)return len.getInt();
//   int len=0;
//   for(int q=0;q<count();q++)if((len=get(q).done(getTextCase,POS))!=0)cnt++;
//   if(cnt==0)return len;
   textCase=null;
   return false;
  }
  throw new AException("mode is incorrect",mode);*/
 }
//---------------------------------------------------------------------------
}
