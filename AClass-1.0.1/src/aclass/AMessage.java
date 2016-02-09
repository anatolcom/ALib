/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Пользователь
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AMessage", propOrder = {
    "FMessageType",
    "FFunctionName",
    "FMessage",
    "FValue"
})
@XmlRootElement(name="Message")
public class AMessage
{
//---------------------------------------------------------------------------
 @XmlElement(name = "MessageType", required = true)
 private String FMessageType="";
 @XmlElement(name = "FunctionName", required = true)
 private String FFunctionName="";
 @XmlElement(name = "Message", required = true)
 private String FMessage="";
 @XmlElement(name = "Value", required = true)
 private int FValue=0;
//---------------------------------------------------------------------------
 public AMessage()
 {
 }
//---------------------------------------------------------------------------
 public AMessage(String messageType,String functionName,String message,int value)
 {
  this.FMessageType=messageType;
  this.FFunctionName=functionName;
  this.FMessage=message;
  this.FValue=value;
 }
//---------------------------------------------------------------------------
 public String getMessageType()
 {
  return FMessageType;
 }
//---------------------------------------------------------------------------
 public void setMessageType(String messageType)
 {
  this.FMessageType=messageType;
 }
//---------------------------------------------------------------------------
 public String getFunctionName()
 {
  return FFunctionName;
 }
//---------------------------------------------------------------------------
 public void setFunctionName(String functionName)
 {
  this.FFunctionName=functionName;
 }
//---------------------------------------------------------------------------
 public String getMessage()
 {
  return FMessage;
 }
//---------------------------------------------------------------------------
 public void setMessage(String message)
 {
  this.FMessage=message;
 }
//---------------------------------------------------------------------------
 public int getValue()
 {
  return FValue;
 }
//---------------------------------------------------------------------------
 public void setValue(int value)
 {
  this.FValue=value;
 }
//---------------------------------------------------------------------------
 
//---------------------------------------------------------------------------
}
