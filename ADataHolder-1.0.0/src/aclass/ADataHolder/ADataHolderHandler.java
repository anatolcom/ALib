/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ADataHolder;

import aclass.AException;

/**
 *
 * @author Пользователь
 */
public interface ADataHolderHandler
{
//---------------------------------------------------------------------------
 public ADataInfo getDataInfoItem(long id) throws AException;
//---------------------------------------------------------------------------
 public ADataInfoList getDataInfoList() throws AException;
//---------------------------------------------------------------------------
 public boolean insertDataInfoItem(ADataInfo Item) throws AException;
//---------------------------------------------------------------------------
 public boolean updateDataInfoItem(ADataInfo Item) throws AException;
//---------------------------------------------------------------------------
 public boolean deleteDataInfoItem(long id) throws AException;
//---------------------------------------------------------------------------
 public AHolderInfo getHolderItem(long id) throws AException;
//---------------------------------------------------------------------------
 public AHolderInfoList getHolderList() throws AException;
//---------------------------------------------------------------------------
 public boolean insertHolderItem(AHolderInfo Item) throws AException;
//---------------------------------------------------------------------------
 public boolean updateHolderItem(AHolderInfo Item) throws AException;
//---------------------------------------------------------------------------
 public boolean deleteHolderItem(long id) throws AException;
//---------------------------------------------------------------------------
 
}
