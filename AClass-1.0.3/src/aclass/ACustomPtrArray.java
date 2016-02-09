/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

//import aclass.AClass;
/**
 *
 * @author Anatol
 * @version 0.1.0.2
 */
//---------------------------------------------------------------------------
//---------------------------------------------------<     C L A S S     >---
//---------------------------------------------------------------------------
public class ACustomPtrArray extends AClass
{
//---------------------------------------------------------------------------
 private final int MaxInt = 2147483647;
//---------------------------------------------------------------------------
 public class APtrElement
 {
  Object ptr;
  APtrElement next,back;
  APtrElement()
  {
   ptr=null;
   next=null;
   back=null;
  }
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<   P R I V A T E   >---
//---------------------------------------------------------------------------
 private APtrElement fes=null;
 private APtrElement fen=null;
 private APtrElement fee=null;
 private int fcount=0;
 private int fn=-1;
//---------------------------------------------------------------------------
/**
  * Метод оптимизированно ищет элемент с индексом index.<br/>
  * можно добавить историю позиций *fen<br/>
  * @param index индекс элемента.
  * @return указатель на элемент(index).
  */
 private APtrElement CFindElement(final int index)//const
 {
// /*
 if(index==0)return fes;
 if(index==fcount-1)return fee;
 if(index==fn)return fen;
 if(index==fn+1)return fen.next;
 if(index==fn-1)return fen.back;
 int q;
 APtrElement e;
 if((fn==0)||(fn==fcount-1))
 {
  if(index<fcount-index)for(e=fes,q=0;q<index;q++)e=e.next;
  else for(e=fee,q=fcount-index-1;q>0;q--)e=e.back;
 }
 else
 {
  if(index<fn)
  {
   if(index<fn-index)for(e=fes,q=0;q<index;q++)e=e.next;
   else for(e=fen,q=fn-index;q>0;q--)e=e.back;
  }
  else
  {
   if(index-fn<fcount-index-1)for(e=fen,q=0;q<index-fn;q++)e=e.next;
   else for(e=fee,q=fcount-index-1;q>0;q--)e=e.back;
  }
 }
 return e;
// */

// APtrElement *e=fes;
// for(int q=0;q<index;q++)e=e->next;
// return e;

// APtrElement *e=fee;
// for(int q=0;q<fcount-index-1;q++)e=e->back;
// return e;
}
//---------------------------------------------------------------------------
/**
  * Метод оптимизированно перемещает курсор fen по массиву в позицию index.<br/>
  * Можно добавить историю позиций fen.<br/>
  * @param index Новая позиция курсора.
  * @return Ссылку на fen.
  */
 private APtrElement CfenGoTo(final int index)
 {
  fen=CFindElement(index);
  fn=index;
  return fen;
 }
//---------------------------------------------------------------------------
/**
  * Метод вставляет елемент a перед элементом b в позицию bindex,
  * если елемент b=null то функция вставляет елемент a в конец массива.<br/>
  * @param a Вставляемый элемент.
  * @param b Элемент, перед которым будет вставлен элемент a.
  * @param bindex Позиция элемента b, которую займёт элемент a.
  */
 private void CInsert(APtrElement a,APtrElement b,final int bindex)
//функция
//то функция вставляет елемент *a в конец массива
 {
  if(b==null)
  {
   a.next=null;
   a.back=fee;
   if(fes==null)fes=a;
   else fee.next=a;
   fee=a;
   fn=fcount;
  }
  else
  {
   a.next=b;
   a.back=b.back;
   if(b.back!=null)b.back.next=a;
   else fes=a;
   b.back=a;
   fn=bindex;
  }
  fen=a;
  fcount++;
 }
//---------------------------------------------------------------------------
/**
  * Метод вырезает елемент a возвращая на него указатель
  * и переводит fen в позицию index.<br/>
  * @param a Вырезаемый элемент.
  * @param index Позиция, в которую переводится fen.
  * @return Указатель на вырезанный элемент a.
  */
 private APtrElement CCut(APtrElement a,final int index)
 {
  fn=index;
  APtrElement e=a;
  if((a.next!=null)&&(a.back!=null))
  {
   e=a.next;
   a.next.back=a.back;
   a.back.next=a.next;
  }
  if((a.next==null)&&(a.back!=null))
  {
   e=a.back;
   a.back.next=null;
   fee=a.back;
   fn--;
  }
  if((a.next!=null)&&(a.back==null))
  {
   e=a.next;
   a.next.back=null;
   fes=a.next;
  }
  if((a.next==null)&&(a.back==null))
  {
   e=null;
   fee=null;
   fes=null;
  }
  a.next=null;
  a.back=null;
  fcount--;
  fen=e;
  return a;
 }
//---------------------------------------------------------------------------
/**
  * Метод меняет местами элементы a и b
  * и переводит fen в позициию b с индексом aindex,
  * т.е. в позицию которую до этого занимал элемент a.<br/>
  * @param a Элемент a.
  * @param b Элемент b.
  * @param aindex Индекс элемента a.
  */
 private void CSwap(APtrElement a,APtrElement b,final int aindex)
 {
  if((a.next!=b)&&(b.next!=a))
  {
   if((a.next!=null)&&(a.back!=null))
   {
    a.next.back=b;
    a.back.next=b;
   }
   else
   {
    if(a.back==null)
    {
     a.next.back=b;
     fes=b;
    }
    if(a.next==null)
    {
     a.back.next=b;
     fee=b;
    }
   }
   if((b.next!=null)&&(b.back!=null))
   {
    b.next.back=a;
    b.back.next=a;
   }
   else
   {
    if(b.back==null)
    {
     b.next.back=a;
     fes=a;
    }
    if(b.next==null)
    {
     b.back.next=a;
     fee=a;
    }
   }
   APtrElement an, ab;
   an=a.next;
   ab=a.back;
   a.next=b.next;
   a.back=b.back;
   b.next=an;
   b.back=ab;
  }
  else
  {
   boolean ab=true;
   if(a.next==b)ab=true;
   if(b.next==a)ab=false;
   if(ab)
   {
    if(a.back!=null)a.back.next=b;
    else fes=b;
    if(b.next!=null)b.next.back=a;
    else fee=a;
    a.next=b.next;
    b.back=a.back;
    a.back=b;
    b.next=a;
   }
   else
   {
    if(b.back!=null)b.back.next=a;
    else fes=a;
    if(a.next!=null)a.next.back=b;
    else fee=b;
    b.next=a.next;
    a.back=b.back;
    b.back=a;
    a.next=b;
   }
  }
  fen=b;
  fn=aindex;
 }
//---------------------------------------------------------------------------
/**
  * Метод перемещает элемент a в позицию b и переводит fen в позициию b.<br/>
  * @param a Элемент a.
  * @param aindex Индекс элемента a.
  * @param b Элемент b.
  * @param bindex Индекс элемента b.
  */
 private void CMove(APtrElement a,final int aindex,APtrElement b,final int bindex)
 {
  CInsert(CCut(a,aindex),b,bindex);
 }
//---------------------------------------------------------------------------
//---------------------------------------------------< P R O T E C T E D >---
//---------------------------------------------------------------------------
/**
  * Метод возвращает количество элементов массива.<br/>
  * @return Количество элементов массива.
  */
 protected int PGetCount()//const
 {
  return fcount;
 }
//---------------------------------------------------------------------------
/**
 * Метод добовляет в конец или удаляет последние элементы массива
 * до задонной длинны value.<br/>
 * @param value Количество элементов в массиве.
 * @throws AException в случае ошибки
 */
 protected void PSetCount(final int value)throws AException
 {
  if(value<0)throw new AException("Count<0");
  if(fcount==value)return;
  if(value==0)
  {
   PClear();
   return;
  }
  while(fcount<value)
  {
   APtrElement enew=new APtrElement();
   CInsert(enew,null,fcount);
  }
// while(fcount>value)delete CCut(fee,fcount-1);
  while(fcount>value)CCut(fee,fcount-1).ptr=null;
 }
//---------------------------------------------------------------------------
/**
 * Метод добавляет элемент value в конец массива.<br/>
 * @param value Добавляемый элемент.
 * @return Позиция элемента в массиве.
 * @throws AException в случае ошибки
 */
 protected int PAdd(Object value)throws AException
 {
  if(fcount==MaxInt)throw new AException("Count=Max");
  APtrElement enew=new APtrElement();
  CInsert(enew,null,fcount);
  enew.ptr=value;
  return fcount-1;
 }
//---------------------------------------------------------------------------
/**
 * Метод вставляет элемент value в массив, в позицию index.<br/>
 * Чтобы вставить элемент в конец массива, 
 * index должен быть равен текущему количеству элементов.<br/>
 * @param value Добавляемый элемент.
 * @param index Позиция добавляемого элемента.
 * @return Позиция элемента в массиве.
 * @throws AException в случае ошибки
 */
 protected int PInsert(Object value,final int index)throws AException
 {
  if(fcount==MaxInt)throw new AException("Count=Max");
  if(index<0)throw new AException("index:" + index + " < 0");
  if(index>fcount)throw new AException("index:" + index + " > Count");
  APtrElement enew=new APtrElement();
  if(index==fcount)CInsert(enew,null,fcount);
  else CInsert(enew,CfenGoTo(index),index);
  enew.ptr=value;
  return index;
 }
//---------------------------------------------------------------------------
/**
 * Метод добавляет пустой элемент в конец массива.<br/>
 * @return Позиция элемента в массиве.
 * @throws AException в случае ошибки
 */
 protected int PNew()throws AException
 {
  if(fcount==MaxInt)throw new AException("Count=Max");
  APtrElement enew=new APtrElement();
  CInsert(enew,null,fcount);
  return fcount-1;
 }
//---------------------------------------------------------------------------
/**
 * Метод вставляет пустой элемент в массив, в позицию index.<br/>
 * Чтобы вставить элемент в конец массива, 
 * index должен быть равен текущему количеству элементов.<br/>
 * @param index Позиция добавляемого элемента.
 * @return Позиция элемента в массиве.
 * @throws AException в случае ошибки
 */
 protected int PNew(final int index)throws AException
 {
  if(fcount==MaxInt)throw new AException("Count=Max");
  if(index<0)throw new AException("index:" + index + " < 0");
  if(index>fcount)throw new AException("index:" + index + " > Count");
  APtrElement enew=new APtrElement();
  if(index==fcount)CInsert(enew,null,fcount);
  else CInsert(enew,CfenGoTo(index),index);
  return index;
 }
//---------------------------------------------------------------------------
/**
 * Метод удаляет элемент с позицией index из массив.<br/>
 * @param index Позиция удаляемого элемента.
 * @throws AException в случае ошибки
 */
 protected void PDelete(final int index)throws AException
 {
  if(index<0)throw new AException("index:" + index + " < 0");
  if(index>=fcount)throw new AException("index:" + index + " >= Count");
  APtrElement ed=CfenGoTo(index);
// delete CCut(ed,index);
  CCut(ed,index).ptr=null;
 }
//---------------------------------------------------------------------------
/**
 * Метод меняет местами элементы a и b.<br/>
 * @param a Позиция элемента a.
 * @param b Позиция элемента b.
 * @throws AException в случае ошибки
 */
 protected void PSwap(final int a,final int b)throws AException
 {
  if(a==b)return;
  if(a<0)throw new AException("a:" + a + " < 0");
  if(b<0)throw new AException("b:" + b + " < 0");
  if(a>=fcount)throw new AException("a:" + a + " >= Count");
  if(b>=fcount)throw new AException("b:" + b + " >= Count");
  CSwap(CfenGoTo(a),CfenGoTo(b),a);
 }
//---------------------------------------------------------------------------
/**
 * Метод перемещает элемент a в позицию b.<br/>
 * @param a Позиция элемента a.
 * @param b Позиция элемента b.
 * @throws AException в случае ошибки
 */
 protected void PMove(final int a,final int b)throws AException
 {
  if(a==b)return;
  if(a<0)throw new AException("a:" + a + " < 0");
  if(b<0)throw new AException("b:" + b + " < 0");
  if(a>=fcount)throw new AException("a:" + a + " >= Count");
  if(b>=fcount)throw new AException("b:" + b + " >= Count");
  APtrElement bvalue=null;
  if(a>b)bvalue=CfenGoTo(b);
  else if(b!=fcount-1)bvalue=CfenGoTo(b+1);
// CInsert(CCut(CfenGoTo(a),a),bvalue,b);
  CMove(CfenGoTo(a),a,bvalue,b);
 }
//---------------------------------------------------------------------------
/**
 * Метод удаляет все элементы массива.<br/>
 */
 protected void PClear()
 {
  if(fcount==0) return;
  APtrElement ed;
  fen=fes;
  while(fen!=null)
  {
   ed=fen;
   fen=fen.next;
   ed.next=null;
   ed.back=null;
   ed.ptr=null;
//  delete ed;
  }
  fes=null;
  fen=null;
  fee=null;
  fcount=0;
  fn=-1;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
// __property int Count={read=GetCount,write=SetCount}
//---------------------------------------------------------------------------
/**
 * Метод определяет наличие элемент с позицией index.<br/>
 * @param index Позиция элемента.
 * @return true - элемент существует,<br />
 *         false - элемента нет.
 */
 protected boolean PExists(final int index)
 {
  if(index<0)return false;
  if(index>=fcount)return false;
  return true;
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает ссылку на элемент с позицией index, 
 * либо возвращает null если указанная позиция неверна.<br/>
 * @param index Позиция элемента.
 * @return Ссылка на элемент.
 * @throws AException в случае ошибки
 */
 protected Object PGetPtr(final int index)throws AException
 {
  if(index<0)throw new AException("index:" + index + " < 0");
  if(index>=fcount)throw new AException("index:" + index + " >= Count");
  CfenGoTo(index);
  return fen.ptr;
 }
//---------------------------------------------------------------------------
/**
 * Метод возвращает ссылку на элемент с позицией index, 
 * либо возвращает null если указанная позиция неверна.<br/>
 * Метод не меняет содержимого класса.<br/>
 * @param index Позиция элемента.
 * @return Ссылка на элемент.
 * @throws AException в случае ошибки
 */
 protected Object PGetPtrConst(final int index)throws AException//const
 {
  if(index<0)throw new AException("index:" + index + " < 0");
  if(index>=fcount)throw new AException("index:" + index + " >= Count");
  APtrElement e=CFindElement(index);
  return e.ptr;
 }
//---------------------------------------------------------------------------
/**
 * Метод устанавливает ссылку элемента с позицией index равной Ptr 
 * если указанная позиция верна.<br/>
 * @param index Позиция элемента.
 * @param Ptr Ссылка на элемент.
 * @throws AException в случае ошибки
 */
 protected void PSetPtr(final int index,Object Ptr)throws AException
 {
  if(index<0)throw new AException("index:" + index + " < 0");
  if(index>=fcount)throw new AException("index:" + index + " >= Count");
  CfenGoTo(index);
  fen.ptr=Ptr;
 }
//---------------------------------------------------------------------------
//---------------------------------------------------<    P U B L I C    >---
//---------------------------------------------------------------------------
// public ACustomPtrArray()
// {
//  fes=null;
//  fen=null;
//  fee=null;
//  fcount=0;
//  fn=-1;
// }
//---------------------------------------------------------------------------
// public ~ACustomPtrArray()
// {
//  Clear();
// }
//---------------------------------------------------------------------------
}
