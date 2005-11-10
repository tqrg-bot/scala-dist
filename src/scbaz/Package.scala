package scbaz;

import scala.collection.immutable._ ;
import scala.xml._ ;

// XXX version should be a Version, not a string
// XXX location should be a URL
// XXX compiler error if I make this a case class
class Package(val name: String,
	      val version: String,
	      val link: String,
	      val filename: String,
	      val depends: Set[String],
	      val description: String)
{
  override def toString() = name + " " + version;

  def toXML : Node = {
    Elem(null, "package", Null, TopScope,
	 Elem(null, "version", Null, TopScope,
 	      Text(version)),
 	 Elem(null, "link", Null, TopScope,
 	      Text(link)),
 	 Elem(null, "filename", Null, TopScope,
 	      Text(filename)),
 	 Elem(null, "depends", Null, TopScope,
 	      (depends.toList.map
	       (x => Elem(null, "name", Null, TopScope, Text(x)))):_*),
 	 Elem(null, "description", Null, TopScope,
 	      Text(description)))
  }
}

object Package {
  def fromXML (node : Node) : Package = {
// XXX have not considered how to handle malformed XML trees
// XXX surely this should use something DTD-based...
    val name =  (node \ "name")(0).child(0).toString(true) ;
    val version = (node \ "version")(0).child(0).toString(true) ;;
    val link = (node \ "link")(0).child(0).toString(true) ;;
    val filename = (node \ "filename")(0).child(0).toString(true) ;;
    val description = (node \ "description")(0).child(0).toString(true) ;

    val dependsList = ((node \ "depends")(0) \ "name")
                      .toList.map(n => n(0).child(0).toString()) ;

    val depends = dependsList.foldLeft(ListSet.Empty[String])((x,y) => x+y) ;

    return new Package(name,
		       version,
		       link,
		       filename,
		       depends,
		       description)
  }
}


object TestPackage {
  def main(args:Array[String]) = {
    val xml = 
      "<package>\n" +
      "<name>DDP docs</name>\n" +
      "<version>2005-11-09</version>\n" +
      "<link>http://www.lexspoon.org/ti/index.html</link>\n" +
      "<filename>ddp-index.html</filename>\n" +
      "<depends>\n" +
      "<name>DDP tech report</name>\n" +
      "<name>DDP dissertation</name>\n" +
      "</depends>\n" +
      "<description>(meta-package) A variety of docs about the DDP type\n" +
      "inference framework.</description>\n" +
      "</package>\n" ;
    val reader = new java.io.StringReader(xml);
    val node = XML.load(reader) ;

    val pack = Package.fromXML(node) ;

    Console.println(pack);
    Console.println(pack.name);
    Console.println(pack.version);
    Console.println(pack.link);
    Console.println(pack.filename);
    Console.println(pack.depends);
    Console.println(pack.description);

    Console.println(pack.toXML);
  }
}

