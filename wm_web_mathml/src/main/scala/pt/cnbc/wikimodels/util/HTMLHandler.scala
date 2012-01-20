package pt.cnbc.wikimodels.util

import scala.xml._

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 16-01-2011
 * Time: 23:14
 */
object HTMLHandler{

  def string2html(str:String):scala.xml.Elem = {
    <p> { str split '\n' map {x => Unparsed(escape(x, new StringBuilder() )) ++ <br/> } reduceLeft (_ ++ _) } </p>
  }

  /**
 * Appends escaped string to <code>s</code>.
 *
 * @param text ...
 * @param s    ...
 * @return     ...
 */
final def escape(text: String, s: StringBuilder): String = {
  // Implemented per XML spec:
  // http://www.w3.org/International/questions/qa-controls
  // imperative code 3x-4x faster than current implementation
  // dpp (David Pollak) 2010/02/03
  val len = text.length
  var pos = 0
  while (pos < len) {
    text.charAt(pos) match {
      case ' ' => s.append("&nbsp;")
      case c => if (c >= ' ') s.append(c)
    }

    pos += 1
  }
  s.toString
}
}