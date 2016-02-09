/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ws.soap;

/**
 *
 * @author anatol
 */
public class ASOAPParams
{
//---------------------------------------------------------------------------
 private String endpoint;
 private String soapAction = null;
 private String wsaAction = null;
 private int connectTimeout = 60000;
 private int readTimeout = 60000;
//---------------------------------------------------------------------------
 public String getEndpoint()
 {
  return endpoint;
 }
//---------------------------------------------------------------------------
 public void setEndpoint(String endpoint)
 {
  this.endpoint = endpoint;
 }
//---------------------------------------------------------------------------
 public String getSoapAction()
 {
  return soapAction;
 }
//---------------------------------------------------------------------------
 public void setSoapAction(String soapAction)
 {
  this.soapAction = soapAction;
 }
//---------------------------------------------------------------------------
 public String getWsaAction()
 {
  return wsaAction;
 }
//---------------------------------------------------------------------------
 public void setWsaAction(String wsaAction)
 {
  this.wsaAction = wsaAction;
 }
//---------------------------------------------------------------------------
 public int getConnectTimeout()
 {
  return connectTimeout;
 }
//---------------------------------------------------------------------------
 public void setConnectTimeout(int connectTimeout)
 {
  this.connectTimeout = connectTimeout;
 }
//---------------------------------------------------------------------------
 public int getReadTimeout()
 {
  return readTimeout;
 }
//---------------------------------------------------------------------------
 public void setReadTimeout(int readTimeout)
 {
  this.readTimeout = readTimeout;
 }
//---------------------------------------------------------------------------
}
