/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.mathml.elements

/**
  * MathML spec lambda accepts any expression and not just an apply.
  *
  * From the MAthML spec:
  *
  *  "A lambda construct with n (possibly 0) internal variables is encoded by a lambda element, where the first n
  *  children are bvar elements containing the identifiers of the internal variables. This is followed by an optional
  *  domainofapplication qualifier and an expression defining the function. The defining expression is typically an
  *  apply, but can also be any expression."
  *
  * For the subset that is used in SBML, we don't have to take the ''domainofapplication'' in account and only the apply
 * and piecewise tags are relevant
 *
  *  @author Alexandre Martins
  *  Date: 24/01/12
  *  Time: 23:13 */
case class Lambda(params:List[Ci], expr:MathMLElem) extends Container(params ::: expr :: Nil)

