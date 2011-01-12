import util.parsing.combinator.{RegexParsers, PackratParsers}

object ExampleParser extends RegexParsers with PackratParsers{

  lazy val aaabbb:PackratParser[Any]= aaa~"+"~bbb^^{ case e~"+"~f => println("e = " + e + " and f = " + f) }
  def aaa:Parser[Any]="""aaa""".r
  def bbb:Parser[Any]="""bbb""".r

  def main(args: Array[String]) {
    println("Parsing is going to start!!")
    val result = parseAll(aaabbb, "aaa+bbb")
    def parsingWasSuccessful(parseResult:ParseResult[Any]):Boolean = {
      parseResult match {
        case Success(_,_) => true
        case Failure(_,_) => false
        case _ => {
          println("Something is wrong with this test")
          false
        }
      }
    }
    if(parsingWasSuccessful(result))
      println("Parsing went OK!")
    else
      println("Parsing failed!")
  }
}
//ExampleParser.main(args)