package pt.cnbc.wikimodels.mathml.elements

/**
 * User: alex
 * Date: 30/Set/2010
 * Time: 23:49:57
 * This class assumes that Ci only has strings has identifiers. The use of presentation markup is allowed by the
 * MathML 2.0 spec but not by an ASCIIMathML representation. As such content is a string
 */

case class Ci(override val content:String,
              mtype:String = "real",
              definitionURL:String="") extends Token