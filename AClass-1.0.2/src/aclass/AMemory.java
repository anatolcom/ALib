/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

/**
 * Динамический массив типа byte
 * @author Anatol
 * @version 0.1.0.0
 */
public class AMemory extends AClass
{
//---------------------------------------------------------------------------
 private byte[] FMemory=null;
//---------------------------------------------------------------------------
 public AMemory(){}                                                     //!!!
//---------------------------------------------------------------------------
 public AMemory(int size) throws AException                             //!!!
 {
  if(size<0)throw new AException("size<0");
  if(size>=0x0fffffff)throw new AException("value>=Max");
//  if(size==0)
//  {
//   FMemory=null;
//   return;
//  }
  FMemory=new byte[size];
  for(int q=0;q<FMemory.length;q++)FMemory[q]=0;
 }
//---------------------------------------------------------------------------
 public AMemory(AMemory value) throws AException                        //!!!
 {
  if(value==null)throw new AException("value=null");
  if(value.FMemory==null)
  {
   FMemory=null;
   return;
  }
  FMemory=new byte[value.FMemory.length];
  for(int q=0;q<value.FMemory.length;q++)FMemory[q]=value.FMemory[q];
 }
//---------------------------------------------------------------------------
 public AMemory(int size,AMemory value) throws AException               //!!!
 {
  if(size<0)throw new AException("size<0");
  if(size>=0x0fffffff)throw new AException("value>=Max");
//  if(size==0)
//  {
//   FMemory=null;
//   return;
//  }
  FMemory=new byte[size];
  if(value==null)
  {
   for(int q=0;q<FMemory.length;q++)FMemory[q]=0;
   throw new AException("value=null");
  }
  for(int q=0;q<FMemory.length;q++)
  {
   if(q<value.getSize())FMemory[q]=value.FMemory[q];
   else FMemory[q]=0;
  }
 }
//---------------------------------------------------------------------------
 public final void assign(AMemory value) throws AException              //!!?
 {
  if(value==null)throw new AException("value=null");
  if(FMemory==null)return;                                              //???
  for(int q=0;q<FMemory.length;q++)
  {
   if(q<value.getSize())FMemory[q]=value.FMemory[q];
   else FMemory[q]=0;
  }
 }
//---------------------------------------------------------------------------
 public int getSize()                                                   //!!!
 {
  if(FMemory==null)return 0;
  return FMemory.length;
 }
//---------------------------------------------------------------------------
 public byte[] bytes(){return FMemory;}                                 //!!!
//---------------------------------------------------------------------------
 public byte get(int index) throws AException                           //!!!
 {
  if(FMemory==null)throw new AException("FMemory=null");
  if(index<0)throw new AException("index<0",index);
  if(index>=FMemory.length)throw new AException("index>=length",index);
  return FMemory[index];
 }
//---------------------------------------------------------------------------
 public void set(int index,byte value) throws AException                //!!!
 {
  if(FMemory==null)throw new AException("FMemory=null");
  if(index<0)throw new AException("index<0",index);
  if(index>=FMemory.length)throw new AException("index>=length",index);
  FMemory[index]=value;
 }
//---------------------------------------------------------------------------
 public void clear()                                                    //!!!
 {
  if(FMemory==null)return;
  for(int q=0;q<FMemory.length;q++)FMemory[q]=0;
 }
//---------------------------------------------------------------------------
 public boolean isNull()
 {
  if(FMemory==null)return true;
  return false;
 }
//---------------------------------------------------------------------------
// public void setNull()
// {
//  FMemory=null;
// }
//---------------------------------------------------------------------------
}
