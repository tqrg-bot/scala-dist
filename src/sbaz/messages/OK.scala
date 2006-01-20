package sbaz.messages;

import scala.xml._;

// A message claiming that the previous request succeeded.
case class OK()
extends Message {
  override def toXML =  <ok/> ;
}


object OKUtil {
  def fromXML(node:Node) = OK();
}