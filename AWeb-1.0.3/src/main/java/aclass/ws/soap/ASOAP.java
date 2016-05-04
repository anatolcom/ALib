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
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
 private static final HostnameVerifier defaultHostnameVerifier = new HostnameVerifier()
 {
  @Override
  public boolean verify(String hostname, SSLSession session)
  {
   return true;
  }
 };
//---------------------------------------------------------------------------
 private static final TrustManager[] defaultTrustManagers = new TrustManager[]
 {
  new X509TrustManager()
  {
   @Override
   public java.security.cert.X509Certificate[] getAcceptedIssuers()
   {
    return null;
   }
   @Override
   public void checkClientTrusted(X509Certificate[] certs, String authType)
   {
   }
   @Override
   public void checkServerTrusted(X509Certificate[] certs, String authType)
   {
   }
  }
 };
//---------------------------------------------------------------------------
 private static String sendFromHttp(byte[] request,
         String endpoint,
         String soapAction,
         int connectTimeout,
         int readTimeout,
         boolean required) throws UnknownHostException, ASOAPFault, AException
 {
  try
  {
   String contentLength = Integer.toString(request.length);

   URL url = new URL(endpoint);
   URLConnection urlConnection = url.openConnection();
   HttpURLConnection connection = (HttpURLConnection) urlConnection;
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
   connection.setRequestProperty("SOAPAction", soapAction);
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
    if (in == null) errorEx(ASOAP.class.getName(), "invoke", "connection.getInputStream() return null");
   }
   catch (IOException ex)
   {
    error = true;
    in = connection.getErrorStream();
    if (in == null) errorEx(ASOAP.class.getName(), "invoke", "connection.getErrorStream() return null. " + ex.getMessage());
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
   if (error)
   {
    warning("invoke", "ResponseCode " + respcode);
    if (required)
    {
     SOAPFault fault;
     try
     {
      SOAPMessage soapMessage = soapMessageFromXml(responseMessage);
      SOAPBody soapBody = soapMessage.getSOAPBody();
      fault = soapBody.getFault();
     }
     catch (Exception ex)
     {
      throw new AException(responseMessage + " " + respcode);
     }
     if (fault != null) throw new ASOAPFault(fault, responseMessage);
    }
    //if (required) throw new AException(responseMessage + " " + respcode);
   }
   return responseMessage;
  }
  catch (IOException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 
 private static String sendFromHttps(byte[] request,
         String endpoint,
         String soapAction,
         int connectTimeout,
         int readTimeout,
         boolean required) throws UnknownHostException, ASOAPFault, AException
 {
  try
  {
   String contentLength = Integer.toString(request.length);

   URL url = new URL(endpoint);
   URLConnection urlConnection = url.openConnection();
   HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
   connection.setRequestMethod("POST");
   try
   {
    SSLContext sslContext = SSLContext.getInstance("TLS");
//    sslContext.init(new KeyManager[0], defaultTrustManagers, new SecureRandom());
    sslContext.init(null, defaultTrustManagers, null);
    connection.setSSLSocketFactory(sslContext.getSocketFactory());
   }
   catch (KeyManagementException ex)
   {
    throw new AException(ex);
   }
   catch (NoSuchAlgorithmException ex)
   {
    throw new AException(ex);
   }

   connection.setHostnameVerifier(defaultHostnameVerifier);

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
   connection.setRequestProperty("SOAPAction", soapAction);
   connection.setRequestProperty("Host", host);
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
    if (in == null) errorEx(ASOAP.class.getName(), "invoke", "connection.getInputStream() return null");
   }
   catch (IOException ex)
   {
    error = true;
    in = connection.getErrorStream();
    if (in == null) errorEx(ASOAP.class.getName(), "invoke", "connection.getErrorStream() return null. " + ex.getMessage());
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
   if (error)
   {
    warning("invoke", "ResponseCode " + respcode);
    if (required)
    {
     SOAPFault fault;
     try
     {
      SOAPMessage soapMessage = soapMessageFromXml(responseMessage);
      SOAPBody soapBody = soapMessage.getSOAPBody();
      fault = soapBody.getFault();
     }
     catch (Exception ex)
     {
      throw new AException(responseMessage + " " + respcode);
     }
     if (fault != null) throw new ASOAPFault(fault, responseMessage);
    }
    //if (required) throw new AException(responseMessage + " " + respcode);
   }
   return responseMessage;
  }
  catch (IOException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  *
  * @param requestMessage SOAP запрос
  * @param endpoint адрес сервиса
  * @param soapAction название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @param connectTimeout время для соединения
  * @param readTimeout время для выполнения
  * @param required обязательность положительного ответа, если true и ответ с ошибкой то происходит
  * исключение
  * @return SOAP ответ
  * @throws java.net.UnknownHostException
  * @throws aclass.ws.soap.ASOAPFault
  * @throws AException в случае ошибки
  */
 public static String invoke(String requestMessage,
         String endpoint,
         String soapAction,
         String wsaAction,
         int connectTimeout,
         int readTimeout,
         boolean required) throws UnknownHostException, ASOAPFault, AException
 {
  if (requestMessage == null) throw new AException("requestMessage = null");
  if (endpoint == null) throw new AException("endpoint = null");
  if (endpoint.isEmpty()) throw new AException("endpoint is empty");
//  request.setCharacterEncoding("utf-8");
//  response.setCharacterEncoding("utf-8");
  String invok = "endpoint:\"" + endpoint + "\"";
  if (soapAction == null) soapAction = "";
  if (wsaAction == null) wsaAction = "";
  if (!soapAction.isEmpty()) invok += ", soapAction:\"" + soapAction + "\"";
  if (!wsaAction.isEmpty()) invok += ", wsaAction:\"" + wsaAction + "\"";
  try
  {
   if (!wsaAction.isEmpty()) requestMessage = setWSAAction(requestMessage, wsaAction);
   byte[] request = requestMessage.getBytes("UTF-8");
   if (endpoint.startsWith("https")) return sendFromHttps(request, endpoint, soapAction, connectTimeout, readTimeout, required);
   if (endpoint.startsWith("http")) return sendFromHttp(request, endpoint, soapAction, connectTimeout, readTimeout, required);
   throw new AException("incorrect endpoint: \"" + endpoint + "\"");
  }
//  catch (UnknownHostException ex)
//  {
//   throw new AException("Unknown Host: " + ex.getMessage(), ex);
//  }
  catch (IOException ex)
  {
   throw new AException(invok + " Exception: " + ex.getMessage(), ex);
  }
  catch (AException ex)
  {
   throw new AException(invok + " Exception: " + ex.getMessage(), ex);
  }

 }
//---------------------------------------------------------------------------
 public static String invokeOLD(String requestMessage,
         String endpoint,
         String soapAction,
         String wsaAction,
         int connectTimeout,
         int readTimeout,
         boolean required) throws UnknownHostException, ASOAPFault, AException
 {
  if (requestMessage == null) throw new AException("requestMessage = null");
  if (endpoint == null) throw new AException("endpoint = null");
  if (endpoint.isEmpty()) throw new AException("endpoint is empty");
//  request.setCharacterEncoding("utf-8");
//  response.setCharacterEncoding("utf-8");
  String invok = "endpoint:\"" + endpoint + "\"";
  if (soapAction == null) soapAction = "";
  if (wsaAction == null) wsaAction = "";
  if (!soapAction.isEmpty()) invok += ", soapAction:\"" + soapAction + "\"";
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
   HttpURLConnection connection = (HttpURLConnection) urlConnection;
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
   connection.setRequestProperty("SOAPAction", soapAction);
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
    if (in == null) errorEx(ASOAP.class.getName(), "invoke", "connection.getInputStream() return null");
   }
   catch (IOException ex)
   {
    error = true;
    in = connection.getErrorStream();
    if (in == null) errorEx(ASOAP.class.getName(), "invoke", "connection.getErrorStream() return null. " + ex.getMessage());
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
   if (error)
   {
    warning("invoke", "ResponseCode " + respcode);
    if (required)
    {
     SOAPFault fault;
     try
     {
      SOAPMessage soapMessage = soapMessageFromXml(responseMessage);
      SOAPBody soapBody = soapMessage.getSOAPBody();
      fault = soapBody.getFault();
     }
     catch (Exception ex)
     {
      throw new AException(responseMessage + " " + respcode);
     }
     if (fault != null) throw new ASOAPFault(fault, responseMessage);
    }
    //if (required) throw new AException(responseMessage + " " + respcode);
   }
   return responseMessage;
  }
//  catch (UnknownHostException ex)
//  {
//   throw new AException("Unknown Host: " + ex.getMessage(), ex);
//  }
  catch (IOException ex)
  {
   throw new AException(invok + " Exception: " + ex.getMessage(), ex);
  }
  catch (AException ex)
  {
   throw new AException(invok + " Exception: " + ex.getMessage(), ex);
  }

 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  *
  * @param requestMessage SOAP запрос
  * @param params параметры запроса
  * @param required обязательность положительного ответа, если true и ответ с ошибкой то происходит
  * исключение
  * @return SOAP ответ
  * @throws java.net.UnknownHostException
  * @throws aclass.ws.soap.ASOAPFault
  * @throws AException в случае ошибки
  */
 public static String invoke(String requestMessage, ASOAPParams params, boolean required) throws UnknownHostException, ASOAPFault, AException
 {
  return invoke(requestMessage, params.getEndpoint(), params.getSoapAction(), params.getWsaAction(), params.getConnectTimeout(), params.getReadTimeout(), required);
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  *
  * @param requestMessage SOAP запрос
  * @param endpoint адрес сервиса
  * @param soapAction название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @param connectTimeout время для соединения
  * @param readTimeout время для выполнения
  * @return SOAP ответ
  * @throws java.net.UnknownHostException
  * @throws aclass.ws.soap.ASOAPFault
  * @throws AException в случае ошибки
  */
 public static String invoke(String requestMessage,
         String endpoint,
         String soapAction,
         String wsaAction,
         int connectTimeout,
         int readTimeout) throws UnknownHostException, ASOAPFault, AException
 {
  return invoke(requestMessage, endpoint, soapAction, wsaAction, connectTimeout, readTimeout, false);
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  * connectTimeout=60000<br/>
  * readTimeout=60000<br/>
  *
  * @param requestMessage запрос SOAP в виде строки
  * @param endpoint адрес сервиса
  * @param soapAction название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @return ответ SOAP в виде строки
  * @throws java.net.UnknownHostException
  * @throws aclass.ws.soap.ASOAPFault
  * @throws AException в случае ошибки
  */
 public static String invoke(String requestMessage, String endpoint, String soapAction, String wsaAction) throws UnknownHostException, ASOAPFault, AException
 {
  int connectTimeout = 60000;
  int readTimeout = 60000;
  return invoke(requestMessage, endpoint, soapAction, wsaAction, connectTimeout, readTimeout, false);
 }
//---------------------------------------------------------------------------
 /**
  * Выполняется SOAP запрос.<br/>
  *
  * @param request SOAP запрос
  * @param endpoint адрес сервиса
  * @param soapAction название вызываемого метода
  * @param wsaAction название вызываемого метода по Web Service Addressing
  * @return SOAP ответ
  * @throws java.net.UnknownHostException
  * @throws aclass.ws.soap.ASOAPFault
  * @throws AException в случае ошибки
  */
 public static byte[] invoke(byte[] request, String endpoint, String soapAction, String wsaAction) throws UnknownHostException, ASOAPFault, AException
 {
  try
  {
   return invoke(new String(request, "UTF-8"), endpoint, soapAction, wsaAction).getBytes("UTF-8");
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
   for (String part1 : part) str += part1;
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
