/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import javax.xml.bind.DatatypeConverter;

//import sun.misc.BASE64Encoder;
/**
 * Encode/Decode
 * Класс с набором статичных методов работающих с системой кодирования/декодирования.
 *
 * @author Anatol
 */
public class AED
{
//---------------------------------------------------------------------------
 public static String encodeBase64(byte[] binaryData) throws AException
 {
  if (binaryData == null) throw new AException("binaryData = null");
  try
  {
   return DatatypeConverter.printBase64Binary(binaryData);
  }
  catch (IllegalArgumentException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 public static byte[] decodeBase64(String base64String) throws AException
 {
  if (base64String == null) throw new AException("base64String = null");
  try
  {
   base64String = base64String.replaceAll("[^A-Za-z0-9+/=]", "");
   return DatatypeConverter.parseBase64Binary(base64String);
  }
  catch (IllegalArgumentException ex)
  {
   throw new AException(ex);
  }
 }
//---------------------------------------------------------------------------
 /**
  * Encode a byte array to hex string
  *
  * @param binaryData array of byte to encode
  * @return return encoded string
  */
 static public String encodeHex(byte[] binaryData)
 {
  if (binaryData == null) return null;
  //initial
  final int LOOKUPLENGTH = 16;
  final char[] lookUpHexAlphabet = new char[LOOKUPLENGTH];
  for (int i = 0; i < 10; i++) lookUpHexAlphabet[i] = (char)('0' + i);
  for (int i = 10; i <= 15; i++) lookUpHexAlphabet[i] = (char)('A' + i - 10);
  //encode
  int lengthData = binaryData.length;
  int lengthEncode = lengthData * 2;
  char[] encodedData = new char[lengthEncode];
  int temp;
  for (int i = 0; i < lengthData; i++)
  {
   temp = binaryData[i];
   if (temp < 0) temp += 256;
   encodedData[i * 2] = lookUpHexAlphabet[temp >> 4];
   encodedData[i * 2 + 1] = lookUpHexAlphabet[temp & 0xf];
  }
  return new String(encodedData);
 }

 /**
  * Decode hex string to a byte array
  *
  * @param hexString encoded string
  * @return return array of byte to encode
  */
 static public byte[] decodeHex(String hexString)
 {
  if (hexString == null) return null;
  int lengthData = hexString.length();
  if (lengthData % 2 != 0) return null;
  //init
  final int BASELENGTH = 128;
  final byte[] hexNumberTable = new byte[BASELENGTH];
  for (int i = 0; i < BASELENGTH; i++) hexNumberTable[i] = -1;
  for (int i = '9'; i >= '0'; i--) hexNumberTable[i] = (byte)(i - '0');
  for (int i = 'F'; i >= 'A'; i--) hexNumberTable[i] = (byte)(i - 'A' + 10);
  for (int i = 'f'; i >= 'a'; i--) hexNumberTable[i] = (byte)(i - 'a' + 10);
  //decode
  char[] binaryData = hexString.toCharArray();
  int lengthDecode = lengthData / 2;
  byte[] decodedData = new byte[lengthDecode];
  byte temp1, temp2;
  char tempChar;
  for (int i = 0; i < lengthDecode; i++)
  {
   tempChar = binaryData[i * 2];
   temp1 = (tempChar < BASELENGTH) ? hexNumberTable[tempChar] : -1;
   if (temp1 == -1) return null;
   tempChar = binaryData[i * 2 + 1];
   temp2 = (tempChar < BASELENGTH) ? hexNumberTable[tempChar] : -1;
   if (temp2 == -1) return null;
   decodedData[i] = (byte)((temp1 << 4) | temp2);
  }
  return decodedData;
 }

}
