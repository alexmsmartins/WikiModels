/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.mathparser

import util.parsing.combinator.Parsers
import javax.xml.XMLConstants
import javax.xml.validation.{Schema, SchemaFactory}
import pt.cnbc.wikimodels.mathml.elements._
import scala.xml._

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 18/01/12
 *  Time: 22:31 */
class
MathMLMatchParser extends MathMLMatchParserHandlers{
  type MME = MathMLElem

  def convertStringToMathML(xmlStr:String, s:Schema):Elem = {
    import pt.cnbc.wikimodels.mathparser.XSDAwareXML
    var e:Elem = null
    try{
      e = XSDAwareXML(s).loadString(xmlStr)
    } catch {
      case e: org.xml.sax.SAXParseException =>{
        Console.println("XML_SCHMA_VALID ERROR at [" + e.getLineNumber + ","+ e.getColumnNumber + "]: " + e.getMessage);
        throw e
      }
    }
    e
  }

  def convertStringToXML(xmlStr:String):Elem = {
    // A schema can be loaded in like ...
    val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val s = sf.newSchema(this.getClass.getClassLoader.getResource("sbml-mathml.xsd"))
    //Use our class:
    convertStringToMathML(xmlStr, s)
  }

  def parse(mathml:Elem):MME = {
    mathml match{
      case <apply>{ns @ _*}</apply> => handleApply(ns.head.asInstanceOf[Elem], ns.tail)
      case ci if (ci.label == "ci") => Ci(ci.child.head.toString ,(ci \ "@type") text,(ci \ "@definitionURL") text)
      case cn if (
        cn.label == "cn" &&
        cn \ "@type" == "real" &&
        ! java.lang.Double.parseDouble(cn.child.head.toString).toString.isEmpty
        )  => Cn(cn.child.head.toString()::Nil ,"real",10,(cn \ "@definitionURL") text)
      case cn if (
        cn.label == "cn" &&
          cn \ "@type" == "integer" &&
          ! java.lang.Integer.parseInt(cn.child.head.toString).toString.isEmpty
        )  => Cn(cn.child.head.toString()::Nil ,"integer",10,cn \ "@definitionURL" text)
      case cn if (
        cn.label == "cn" &&
          cn \ "@type" == "e-notation" &&
          ! java.lang.Double.parseDouble(cn.child.head.toString).toString.isEmpty &&
          ! java.lang.Integer.parseInt(cn.child(2).toString).toString.isEmpty
        )  => Cn(cn.child(0).toString::cn.child(2).toString::Nil ,"e-notation",10,(cn \ "@definitionURL") text)
      case cs if (cs.label == "csymbol" ) => CSymbol(cs.child.toString, cs \ "@definitionURL" text, cs \ "@encoding" text)
      case default => throw new RuntimeException( "XML conversion of " + default + " is not implemented")
    }
  }
}

object MathMLMatchParser{
  def apply() = new MathMLMatchParser
}

trait MathMLMatchParserHandlers {
  def handleApply(operator:Elem, params:NodeSeq):Apply = {
    operator match {
      case op if( op.label == "csymbol") => new Apply(
        CSymbol(op.child.toString), params.map(p => parse(p.asInstanceOf[Elem])).toList
      )
      case op if(KnownOperators.validOps.contains(op.label) ) => new Apply(
        KnownOperators.validOps.get(op.label).get, params.map(p => parse(p.asInstanceOf[Elem])).toList
      )
      case default => throw new RuntimeException( "Apply element " + default.toString + " has a strange format!")
    }
  }
  def parse(mathml:Elem):MathMLElem
}

