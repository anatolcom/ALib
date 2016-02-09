/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ws.soap;

import javax.xml.soap.SOAPFault;


/*
 <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
 <S:Body>
 <S:Fault xmlns:ns4="http://www.w3.org/2003/05/soap-envelope">
 <faultcode>S:Server</faultcode>
 <faultstring>aclass.AException: can not create UIN, because: length of identificator > 12</faultstring>
 </S:Fault>
 </S:Body>
 </S:Envelope> 
 */
/**
 *
 * @author anatol
 */
public class ASOAPFault extends Exception
{
//---------------------------------------------------------------------------
 private final SOAPFault fault;
 private final String response;
//---------------------------------------------------------------------------
 public ASOAPFault(SOAPFault fault, String response)
 {
  super(message(fault));
  this.fault = fault;
  this.response = response;
 }
//---------------------------------------------------------------------------
 private static String message(SOAPFault fault)
 {
  String message = null;
  if (fault != null) message = fault.getFaultString();
  if (message == null) message = "";
  return message;
 }
//---------------------------------------------------------------------------
 public SOAPFault getFault()
 {
  return fault;
 }
//---------------------------------------------------------------------------
 public String getResponse()
 {
  return response;
 }
//---------------------------------------------------------------------------
}
