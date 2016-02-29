/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.aweb;


import aclass.AClass;
import aclass.AException;
import aclass.AIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 *
 * @author Пользователь
 */
public class AWeb// extends AClass
{
//---------------------------------------------------------------------------
 public static byte[] invoke(byte[] request,
         String endpoint,
         String method,
         AWebParamList properties) throws AException
 {
  if (endpoint == null) throw new AException("endpoint = null");
  if (endpoint.isEmpty()) throw new AException("endpoint is empty");
  if (properties == null) throw new AException("properties = null");

  String invok = method + " endpoint:\"" + endpoint + "\"";
  AClass.info(AWeb.class.getName() + "." + "invoke", invok);
//  System.out.println(AWeb.class.getName()+"."+"invoke: "+invok);
  int connectTimeout = 60000;
  int readTimeout = 60000;
  try
  {
   URL url = new URL(endpoint);
   URLConnection urlConnection = url.openConnection();
   HttpURLConnection connection = (HttpURLConnection)urlConnection;
   connection.setDoInput(true);
   if ("GET".equals(method)) connection.setDoOutput(false);
   else connection.setDoOutput(true);
   connection.setConnectTimeout(connectTimeout);
   connection.setReadTimeout(readTimeout);
//   connection.setUseCaches(false);
   for (int q = 0; q < properties.count(); q++)
   {
    AWebParam property = properties.get(q);
    connection.setRequestProperty(property.name, property.value);
//    System.out.println(AWeb.class.getName()+"."+"invoke: "+property.name+": "+property.value);
   }
   connection.setRequestMethod(method);
   return invoke(request, connection);
  }
  catch (Exception ex)
  {
   throw new AException(invok + " " + ex.getMessage(), ex);
  }
 }
//---------------------------------------------------------------------------
 public static byte[] invoke(byte[] request, HttpURLConnection connection) throws AException
 {
  if (connection == null) throw new AException("connection=null");
  try
  {
   connection.connect();
   if (request != null)
   {
    OutputStream out = connection.getOutputStream();
    AIO.writeBytesToOutputStream(request, out);
    out.close();
   }
   InputStream in;
   try
   {
    in = connection.getInputStream();
    if (in == null) System.out.println("connection.getInputStream() return null"); //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   }
   catch (IOException ex)
   {
    in = connection.getErrorStream();
    if (in == null) System.out.println("connection.getErrorStream() return null"); //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   }
   if (in == null)
   {
    try
    {
     int respcode = connection.getResponseCode();
     System.out.println("respcode:" + respcode); //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     String respmsg = connection.getResponseMessage();
     throw new AException(respmsg + " " + respcode);
    }
    catch (IOException ex)
    {
     throw new AException(ex);
    }
    finally
    {
     connection.disconnect();
    }
   }
   byte[] response = AIO.readBytesFromInputStream(in);
   in.close();
   connection.disconnect();
   return response;
  }
  catch (UnknownHostException ex)
  {
   throw new AException("Unknown Host: " + ex.getMessage(), ex);
  }
  catch (IOException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public static String hostFromAddress(String address)
 {
  String host = address;
  int pos = host.indexOf("://");
  host = host.substring(pos + 3);
  pos = host.indexOf("/");
  if (pos != -1) host = host.substring(0, pos);
  return host;
 }
//---------------------------------------------------------------------------
}
