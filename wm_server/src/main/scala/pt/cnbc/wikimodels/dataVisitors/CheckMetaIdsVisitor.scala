/*
 * __NAME__.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataVisitors


/**
 * class that checks if all metaids of a an element are valid.
 * The rules are:
 *  - no metaId should be repeated across the entire knowledgebase
 *  - if metaId does not exist for a certain element it should be created from the id
 */

class CheckMetaIdVisitor {
  import pt.cnbc.wikimodels.dataModel._
  import pt.cnbc.wikimodels.exceptions.BadFormatException

  def visit[T](elem:T):T = {
    elem match  {
      case SBMLModel => elem
      case Compartment => elem
      case Constraint => elem
      case FunctionDefinition => elem
      case _ => {
          throw new BadFormatException("Unknow element inside SBMLModel when checking for metaids")
          elem
      }
    }

  }
}
