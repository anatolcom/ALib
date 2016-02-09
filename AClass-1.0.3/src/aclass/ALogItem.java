/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;
/**
 *
 * @author Anatol
 */
public class ALogItem
{
 public final String type;
 public final String text;
 public final String date;
 public ALogItem(String type,String text,String date)
 {
  this.type=type;
  this.text=text;
  this.date=date;
 }
 @Override
 public String toString(){return type+" "+text;}
 public String toStringWithDate(){return type+" "+date+" "+text;}
}
