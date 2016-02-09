/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass.xml.validation;
import aclass.AIO;
import java.io.IOException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
/**
 *
 * @author Anatol
 */
public class AXSDResourceResolver implements LSResourceResolver
{
//---------------------------------------------------------------------------
 public final String name;
//---------------------------------------------------------------------------
 public AXSDResourceResolver(String name)
 {
  this.name=name;
 }
//---------------------------------------------------------------------------
 @Override
 public LSInput resolveResource(String type,String namespaceURI,
         String publicId,String systemId,String baseURI)
 {
  // note: in this sample, the XSD's are expected to be in the root of the classpath
  System.out.println("("+name+") type:"+type+", namespaceURI:"+namespaceURI+", publicId:"+publicId+", systemId:"+systemId+", baseURI:"+baseURI);
  System.out.println("test");
  try
  {
   byte[] resource=AIO.readBytesFromResource(this,systemId,true);
   System.out.println("resolveResource "+systemId);
   return new AXSDInput(publicId,systemId,baseURI,resource);
  }
  catch(IOException ex)
  {
   System.out.println("resolveResource "+ex.getMessage());
   return null;
  }
 }
//---------------------------------------------------------------------------
}
