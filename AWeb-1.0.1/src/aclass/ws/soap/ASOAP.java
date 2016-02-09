/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.ws.soap;

import aclass.AClass;
import aclass.AED;
import aclass.AException;
import aclass.AIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.soap.*;

/**
 *
 * @author Anatoly
 */
public class ASOAP extends AClass
{
//---------------------------------------------------------------------------
 public static boolean isSOAP(String xml)
 {
  if (xml == null) return false;
  return xml.lastIndexOf("http://schemas.xmlsoap.org/soap/envelope/") != -1;
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  *
  * @param requestMessage SOAP запрос
  * @param endpoint адрес сервиса
  * @param soapActoin название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @param connectTimeout время для соединения
  * @param readTimeout время для выполнения
  * @param required обязательность положительного ответа, если true и ответ с
  * ошибкой то происходит исключение
  * @return SOAP ответ
  * @throws AException в случае ошибки
  */
 public static String soapInvoke(String requestMessage,
                                 String endpoint,
                                 String soapActoin,
                                 String wsaAction,
                                 int connectTimeout,
                                 int readTimeout,
                                 boolean required) throws AException
 {
  if (requestMessage == null) throw new AException("requestMessage = null");
  if (endpoint == null) throw new AException("endpoint = null");
  if (endpoint.isEmpty()) throw new AException("endpoint is empty");
//  request.setCharacterEncoding("utf-8");
//  response.setCharacterEncoding("utf-8");
  String invok = "endpoint:\"" + endpoint + "\"";
  if (soapActoin == null) soapActoin = "";
  if (wsaAction == null) wsaAction = "";
  if (!soapActoin.isEmpty()) invok += ", soapActoin:\"" + soapActoin + "\"";
  if (!wsaAction.isEmpty()) invok += ", wsaAction:\"" + wsaAction + "\"";
  try
  {
   if (!wsaAction.isEmpty()) requestMessage = setWSAAction(requestMessage, wsaAction);
   byte[] request = requestMessage.getBytes("UTF-8");
   String contentLength = Integer.toString(request.length);
   //invok+=", Content-Length: "+contentLength;
   //AClass.sendGlobalInfo(ASOAP.class.getName()+"."+"invoke",invok,request.length);

   URL url = new URL(endpoint);
   URLConnection urlConnection = url.openConnection();
   HttpURLConnection connection = (HttpURLConnection)urlConnection;
   String host = url.getHost();
   if (url.getPort() != -1) host += ":" + url.getPort();

   connection.setDoInput(true);
   connection.setDoOutput(true);
   connection.setConnectTimeout(connectTimeout);
   connection.setReadTimeout(readTimeout);
//   connection.setUseCaches(false);

   connection.setRequestProperty("User-Agent", "Jakarta Commons-HttpClient/3.1");
   connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
   connection.setRequestProperty("Content-Language", "ru-RU");
   connection.setRequestProperty("SOAPAction", soapActoin);
//   connection.setRequestProperty("Host",url.getHost()+":"+url.getPort());
   connection.setRequestProperty("Host", host);
   connection.setRequestMethod("POST");
//   connection.setRequestProperty("Connection","Keep-Alive");
   connection.setRequestProperty("Content-Length", contentLength);

   //AClass.sendGlobalInfo(ASOAP.class.getName(),"soapInvoke host "+host,request.length);
   connection.connect();
//   AClass.sendGlobalInfo("soapInvoke","ContentEncoding: "+connection.getContentEncoding(),null);
   OutputStream out = connection.getOutputStream();
   //AIO.writeStringToOutputStream(requestMessage,out);
   AIO.writeBytesToOutputStream(request, out);
   out.close();

   int respcode = 0;
   InputStream in;
   boolean error = false;
   try
   {
    respcode = connection.getResponseCode();
    if (respcode != 200) error = true;
    in = connection.getInputStream();
    if (in == null) AClass.sendGlobalErrorEx(ASOAP.class.getName(), "invoke", "connection.getInputStream() return null");
   }
   catch (IOException ex)
   {
    error = true;
    //AClass.sendGlobalError(ASOAP.class.getName(), "invoke", "connection.getErrorStream() return null. " + ex.getMessage(), null);
    in = connection.getErrorStream();
    if (in == null) AClass.sendGlobalErrorEx(ASOAP.class.getName(), "invoke", "connection.getErrorStream() return null. " + ex.getMessage());
   }
   if (in == null)
   {
    String respmsg = "";
    try
    {
     respcode = connection.getResponseCode();
     respmsg = connection.getResponseMessage();
    }
    finally
    {
     connection.disconnect();
    }
    throw new AException(respmsg + " " + respcode);
   }
   String responseMessage = AIO.readStringFromInputStream(in);
   in.close();
   connection.disconnect();
   //if (error && required) throw new AException(responseMessage, respcode);
   if (error)
   {
    AClass.sendGlobalWarning("invoke", "ResponseCode " + respcode);
    if (required) throw new AException(responseMessage + " " + respcode);
   }
   return responseMessage;
  }
  catch (UnknownHostException ex)
  {
   throw new AException("Unknown Host: " + ex.getMessage(), ex);
  }
  catch (Exception ex)
  {
//   ex.printStackTrace(System.out);
   throw new AException(invok + " Exception: " + ex.getMessage(), ex);
  }

//  try{return new String(soapInvoke(requestMessage.getBytes("UTF-8"),endpoint,soapActoin,wsaAction),"UTF-8");}
//  catch(UnsupportedEncodingException ex){throw new AException(ex);}
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  *
  * @param requestMessage SOAP запрос
  * @param endpoint адрес сервиса
  * @param soapActoin название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @param connectTimeout время для соединения
  * @param readTimeout время для выполнения
  * @return SOAP ответ
  * @throws AException в случае ошибки
  */
 public static String soapInvoke(String requestMessage,
                                 String endpoint,
                                 String soapActoin,
                                 String wsaAction,
                                 int connectTimeout,
                                 int readTimeout) throws AException
 {
  return soapInvoke(requestMessage, endpoint, soapActoin, wsaAction, connectTimeout, readTimeout, false);
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  * connectTimeout=60000<br/>
  * readTimeout=60000<br/>
  *
  * @param requestMessage запрос SOAP в виде строки
  * @param endpoint адрес сервиса
  * @param soapActoin название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @return ответ SOAP в виде строки
  * @throws AException
  */
 public static String soapInvoke(String requestMessage, String endpoint, String soapActoin, String wsaAction) throws AException
 {
  int connectTimeout = 60000;
  int readTimeout = 60000;
  return soapInvoke(requestMessage, endpoint, soapActoin, wsaAction, connectTimeout, readTimeout, false);
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  *
  * @param request SOAP запрос
  * @param endpoint адрес сервиса
  * @param soapActoin название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @return SOAP ответ
  * @throws AException в случае ошибки
  */
 public static byte[] soapInvoke(byte[] request, String endpoint, String soapActoin, String wsaAction) throws AException //!!!
 {
  try
  {
   return soapInvoke(new String(request, "UTF-8"), endpoint, soapActoin, wsaAction).getBytes("UTF-8");
  }
  catch (UnsupportedEncodingException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public static SOAPMessage soapMessageFromXml(String xml) throws AException
 {
  try
  {
   MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
   InputStream responseIn = AIO.openInputStreamFromString(xml);
   SOAPMessage message = messageFactory.createMessage(null, responseIn);
   responseIn.close();
   return message;
  }
  catch (IOException ex)
  {
   throw new AException(ex);
  }
  catch (SOAPException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 private static String insertTextBeforeRegex(String src, String regex, String text) throws AException
 {
  int pos = getPosFirstRegex(src, regex);
  return src.substring(0, pos) + text + src.substring(pos);
 }
//---------------------------------------------------------------------------
 private static int getPosFirstRegex(String text, String regex) throws AException
 {
  try
  {
   String part[] = text.split(regex, 2);
   if (part.length == 1) return -1;
   return part[0].length();
  }
  catch (Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 private static String deleteRegex(String src, String regex) throws AException
 {
  try
  {
   String part[] = src.split(regex);
   if (part.length <= 1) return src;
   String str = "";
   for (int q = 0; q < part.length; q++) str += part[q];
   return str;
  }
  catch (Exception ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Добавляет в xml данные WS-A addressing из wsaAction.<br/>
  *
  * @param xml
  * @param wsaAction данные WS-A addressing
  * @return XML
  * @throws AException в случае ошибки
  */
 public static String setWSAAction(String xml, String wsaAction) throws AException
 {
  if (wsaAction == null) throw new AException("WSAAction=null");
  if (wsaAction.isEmpty()) throw new AException("WSAAction is empty");
  xml = deleteRegex(xml, "<wsa:Action.[^<]*</wsa:Action>");
  String tagAction = "<wsa:Action xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" + wsaAction + "</wsa:Action>";
  xml = insertTextBeforeRegex(xml, "</.[^<>]*Header>", tagAction);
  return xml;
 }
//---------------------------------------------------------------------------
 public static String generateWsSecurity(String username, String password) throws AException
 {
  try
  {
   String nonce = new String(SecureRandom.getSeed(4));
   DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:s.S'Z'");
   String created = df.format(new Date());
   MessageDigest mdSHA = MessageDigest.getInstance("SHA1");
   MessageDigest md5 = MessageDigest.getInstance("MD5");
   password = AED.encodeHex(md5.digest(password.getBytes())).toLowerCase();
   byte[] digest = mdSHA.digest((nonce + created + password).getBytes());
   return "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
           + "<wsse:UsernameToken wsu:Id=\"8607e404-15f2-4e21-9dab-449e95d0730a\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
           + "<wsse:Username>" + username + "</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + AED.encodeBase64(digest) + "</wsse:Password>"
           + "<wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" + AED.encodeBase64(nonce.getBytes()) + "</wsse:Nonce>"
           + "<wsu:Created>" + created + "</wsu:Created>"
           + "</wsse:UsernameToken>"
           + "</wsse:Security>";
  }
  catch (NoSuchAlgorithmException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
/* @Deprecated
  public Document getCertificateInfo(String certificate) throws AException
  {
  try
  {
  SOAPMessage request=createCertificateInfoRequest(certificate);
  String requestMessage=ADOM.nodeToStr(request.getSOAPPart());
  //   System.out.println("---------------------------------- request <");
  //   System.out.println(requestMessage);
  //   System.out.println("---------------------------------- request >"); 
  String responseMessage=soapInvoke(requestMessage,endpointAddress,methodAddress);
  responseMessage=updaetEntity(responseMessage);//Fixing bug with incorrect entities &#x4;
  //   System.out.println("---------------------------------- response <");
  //   System.out.println(responseMessage);
  //   System.out.println("---------------------------------- response >");
  SOAPMessage response=soapMessageFromXml(responseMessage);
  //   return ADOM.nodeToStr(response.getSOAPBody().extractContentAsDocument());
  return response.getSOAPBody().extractContentAsDocument();
  }
  catch(Exception ex){throw new AException(ex);}
  }*/
//---------------------------------------------------------------------------
/* private String updaetEntity(String badXml) throws AException
  {
  AStr xml=new AStr(badXml);
  final AMarker ENTITY_START=new AMarker("&#");
  final AMarker ENTITY_END=new AMarker(";");
  int pos=0;
  AInt len=new AInt();
  AStr substr=new AStr();
  String text="";
  try
  {
  while(true)
  {
  pos=AParser.readToCase(xml,pos,substr,ENTITY_START);
  text+=substr.value;
  if(pos==-1)break;
  pos=AParser.readToCase(xml,pos,substr,ENTITY_END);
  if(pos==-1)throw new AException("\""+ENTITY_END+"\" not found");
  try{text+=fix(ENTITY_START.toString(),substr.value,ENTITY_END.toString());}
  catch(Exception ex){sendWarning("updaetEntity","Bad entity: "+ENTITY_START+substr.value+ENTITY_END,null);}
  if(AParser.END_OF_TEXT.done(xml,pos,len))break;
  }
  }
  catch(Exception ex){throw new AException(ex);}
  return text;
  }*/
//---------------------------------------------------------------------------
/* private String fix(String es,String entityData,String ee) throws AException
  {
  if(entityData==null)throw new AException("value=null");
  if(Character.toLowerCase(entityData.charAt(0))=='x')return ""; //entityData="0"+entityData;
  try
  {
  int data=Integer.decode(entityData);
  return es+Integer.toString(data)+ee;
  }
  catch(NumberFormatException ex){throw new AException(ex);}
  }*/
//---------------------------------------------------------------------------
 public static String getSoapEnvelop(String prefix, boolean header, boolean body) throws AException
 {
  String nsName = "";
  String xmlnsName = "";
  if (prefix != null && !prefix.isEmpty())
  {
   nsName = prefix + ":";
   xmlnsName = ":" + prefix;
  }
  StringBuilder xml = new StringBuilder();
  xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  xml.append("<").append(nsName).append("Envelope xmlns").append(xmlnsName).append("=\"http://schemas.xmlsoap.org/soap/envelope/\">");
  if (header) xml.append("<").append(nsName).append("Header/>");
  if (body) xml.append("<").append(nsName).append("Body/>");
  xml.append("</").append(nsName).append("Envelope>");
  return xml.toString();
 }
//---------------------------------------------------------------------------
 public static String getSoapEnvelop(String prefix, String header, String body)
 {
  String nsName = "";
  String xmlnsName = "";
  if (prefix != null && !prefix.isEmpty())
  {
   nsName = prefix + ":";
   xmlnsName = ":" + prefix;
  }
  StringBuilder xml = new StringBuilder();
  xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  xml.append("<").append(nsName).append("Envelope xmlns").append(xmlnsName).append("=\"http://schemas.xmlsoap.org/soap/envelope/\">");
  if (header != null)
  {
   if (header.isEmpty()) xml.append("<").append(nsName).append("Header/>");
   else xml.append("<").append(nsName).append("Header>").append(header).append("</").append(nsName).append("Header>");
  }
  if (body != null)
  {
   if (body.isEmpty()) xml.append("<").append(nsName).append("Body/>");
   else xml.append("<").append(nsName).append("Body>").append(body).append("</").append(nsName).append("Body>");
  }
  xml.append("</").append(nsName).append("Envelope>");
  return xml.toString();
 }
//---------------------------------------------------------------------------
 public static String getSoapFault(String prefix, boolean header, String faultcode, String faultstring)
 {
  String nsName = "";
  String xmlnsName = "";
  if (prefix != null && !prefix.isEmpty())
  {
   nsName = prefix + ":";
   xmlnsName = ":" + prefix;
  }
  StringBuilder xml = new StringBuilder();
  xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  xml.append("<").append(nsName).append("Envelope xmlns").append(xmlnsName).append("=\"http://schemas.xmlsoap.org/soap/envelope/\">");
  if (header) xml.append("<").append(nsName).append("Header/>");
  xml.append("<").append(nsName).append("Body>");
  xml.append("<").append(nsName).append("Fault>");
  xml.append("<faultcode>").append(faultcode).append("</faultcode>");
  xml.append("<faultstring>").append(faultstring).append("</faultstring>");
  xml.append("</").append(nsName).append("Fault>");
  xml.append("</").append(nsName).append("Body>");
  xml.append("</").append(nsName).append("Envelope>");
  return xml.toString();
 }
//---------------------------------------------------------------------------
}
