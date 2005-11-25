package scbaz ;

import scala.xml._ ;

class EmptyUniverse extends Universe("The Empty Universe",
				     "A universe with no packages.")
{
  def retrieveAvailable() = new PackageSet(Nil) ;
  override def simpleUniverses = List() ;
  override def toXML = Elem(null, "emptyuniverse", Null, TopScope) ;
}