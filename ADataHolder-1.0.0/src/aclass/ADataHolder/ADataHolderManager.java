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
public class ADataHolderManager extends AClass
{
//---------------------------------------------------------------------------
 public ADataHolderHandler Handler=null;
//---------------------------------------------------------------------------
// private static long idCurrentHolder=(long)0;
 private ADataHolderState FState=new ADataHolderState();
//---------------------------------------------------------------------------
 private ASessionList uploadSessionList=new ASessionList();
 private ASessionList downloadSessionList=new ASessionList();
//---------------------------------------------------------------------------
 public long getIdCurrentHolder()
 {
  return FState.getIdCurrentHolder();
 }
//---------------------------------------------------------------------------
 public void setIdCurrentHolder(long id)
 {
  FState.setIdCurrentHolder(id);
 }
//---------------------------------------------------------------------------
 public AHolderInfo getCurrentHolderInfo() throws AException
 {
  long id=FState.getIdCurrentHolder();
  AHolderInfo holder=Handler.getHolderItem(id);
  return holder;
 }
//---------------------------------------------------------------------------
 public AHolder getHolder(long idHolder) throws AException
 {
  AHolderInfo holderInfo=Handler.getHolderItem(idHolder);
  return new AHolder(holderInfo);
 }
//---------------------------------------------------------------------------
 public ASession openUploadSession(String name,String description,long size) throws AException
 {
  long id=FState.getIdCurrentHolder();
  AHolder holder=getHolder(id);
  ADataInfo dataInfo=new ADataInfo(name,description,size);
  AData data=holder.createData(dataInfo);
  ASession session=uploadSessionList.openSession(data);
//  Handler.insertDataInfoItem(dataInfo);
  return session;
 }
//---------------------------------------------------------------------------
 public ASession getUploadSession(long id) throws AException
 {
  return uploadSessionList.getSession(id);
 }
//---------------------------------------------------------------------------
 public long closeUploadSession(long id,String MD5) throws AException
 {
  uploadSessionList.checkSession(id,MD5);
  ASession session=uploadSessionList.closeSession(id,ASessionEvent.CLOSED);
  if(!session.isSuccessfull())return -1;
  return id;
 }
//---------------------------------------------------------------------------
 public long cancelUploadSession(long id) throws AException
 {
  uploadSessionList.closeSession(id,ASessionEvent.CANCELED);
  return id;
 }
//---------------------------------------------------------------------------
 public ASession openDownloadSession(long idData) throws AException
 {
  ADataInfo dataInfo=Handler.getDataInfoItem(idData);
//  if(dataInfo==null)throw new AException("dataInfo not found");
  long id=FState.getIdCurrentHolder();
  AHolder holder=getHolder(id);
  if(holder==null)throw new AException("getHolder return ERROR");
  AData data=holder.openData(dataInfo);
  if(data==null)throw new AException("openData return ERROR");
  return downloadSessionList.openSession(data);
 }
//---------------------------------------------------------------------------
 public ASession getDownloadSession(long id) throws AException
 {
  return downloadSessionList.getSession(id);
 }
//---------------------------------------------------------------------------
 public long closeDownloadSession(long id) throws AException
 {
  downloadSessionList.closeSession(id,ASessionEvent.CLOSED);
  return id;
 }
//---------------------------------------------------------------------------
 public long cancelDownloadSession(long id) throws AException
 {
  downloadSessionList.closeSession(id,ASessionEvent.CANCELED);
  return id;
 }
//---------------------------------------------------------------------------
 private ASessionAdapter uploadSessionListener=new ASessionAdapter()
 {
  @Override
  public void openSession(ASessionEvent evt) throws AException
  {
   ASession session=evt.getSession();
   sendInfo("openUploadSession",session.toString()+" "+evt.getStateName(),(int)session.getId());
  }
  @Override
  public void closeSession(ASessionEvent evt) throws AException
  {
   ASession session=evt.getSession();
   sendInfo("closeUploadSession",session.toString()+" "+evt.getStateName(),null);
   if(session.isSuccessfull())
   {
    Handler.insertDataInfoItem(session.getDataInfo());
    return;
   }
   AHolder holder=getHolder(session.getDataInfo().getIdHolder());
   holder.deleteData(session.getDataInfo());
  }
 };
//---------------------------------------------------------------------------
 private ASessionAdapter downloadSessionListener=new ASessionAdapter()
 {
  @Override
  public void openSession(ASessionEvent evt) throws AException
  {
   ASession session=evt.getSession();
   sendInfo("openDownloadSession",session.toString()+" "+evt.getStateName(),(int)session.getId());
  }
  @Override
  public void closeSession(ASessionEvent evt) throws AException
  {
   ASession session=evt.getSession();
   sendInfo("closeDownloadSession",session.toString()+" "+evt.getStateName(),(int)session.getId());
   if(evt.getState()==ASessionEvent.CLOSED)
   {
    Handler.updateDataInfoItem(session.getDataInfo());
   }
  }
 };
//---------------------------------------------------------------------------
 public void init() 
 {
//  AClass.Log.setPath("C:/FileHolder/log");
//  AClass.Log.setAutoSave("/"+getClass().getSimpleName()+".log");
  uploadSessionList.setListener(uploadSessionListener);
  downloadSessionList.setListener(downloadSessionListener);
 }
//---------------------------------------------------------------------------
 public void destroy() throws AException 
 {
  uploadSessionList.clear();
  downloadSessionList.clear();
//  AClass.Log.saveToFile("/"+getClass().getSimpleName()+".log");
 }
//---------------------------------------------------------------------------
 public void setState(ADataHolderState state)
 {
  FState.setIdCurrentHolder(state.getIdCurrentHolder());
 }
//---------------------------------------------------------------------------
 public ADataHolderState getState(){return FState;}
//---------------------------------------------------------------------------
}
