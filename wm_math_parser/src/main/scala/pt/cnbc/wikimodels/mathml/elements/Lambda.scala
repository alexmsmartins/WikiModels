/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.mathml.elements

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 24/01/12
 *  Time: 23:13 */
case class Lambda(params:List[Ci], expr:MathMLElem) extends Container(params ::: ( expr :: Nil))
  //TODO MathML spec accepts any expression and not just an apply. Check if this makes sense in AsciiMath
