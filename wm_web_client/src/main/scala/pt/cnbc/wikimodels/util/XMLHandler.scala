/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.util

import xml.{Elem, UnprefixedAttribute}

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 11-01-2011
 * Time: 16:37
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
