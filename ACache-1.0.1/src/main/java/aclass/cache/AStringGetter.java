/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aclass.cache;

import aclass.AException;

/**
 * @author Anatol
 */
public interface AStringGetter
{
 public AStringCacheItem get(String name, boolean required) throws AException;
 public void refresh(String name) throws AException;
}