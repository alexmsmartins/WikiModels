package pt.cnbc.wikimodels.mathml.elements

/**
 * User: alex
 * Date: 30/Set/2010
 * Time: 23:49:57
 * This class assumes that Ci only has strings has identifiers. The use of presentation markup is allowed by the
 * MathML 2.0 spec but not by an ASCIIMathML representation. As such content is a string.
 * In SBML's subset the type attribute is not a part of Ci elements
 */
case class Ci(content:String,
              definitionURL:Option[String]=None) extends Token