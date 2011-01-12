package pt.cnbc.wikimodels.util

import xml.{Elem, UnprefixedAttribute}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11-01-2011
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */

object XMLHandler {
  def addAttributes(xml:Elem,a:(String, String)* ) = {
    var xxx = xml
    for(attr <- a){
      xxx = xxx.copy(xxx.prefix, xxx.label, new UnprefixedAttribute(attr._1, attr._2, xxx.attributes) )
    }
    xxx
  }
}
