package pt.cnbc.wikimodels.client.record.visitor

import scala.collection.JavaConversions._

import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel._
import net.liftweb.common.Full
import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.exceptions.NotImplementedException

/*
* Copyright (c) 2011. Alexandre Martins. All rights reserved.
*/

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 15-11-2011
 * Time: 22:51
 */
object SBMLFromRecord extends LoggerWrapper {

  def createSBMLElementFrom(er:SBaseRecord[_]) =  {
    er match {
      case SBMLModelRecord => createModelFrom(_)
      case CompartmentRecord => createCompartmentFrom(_)
      case _ => new NotImplementedException("ERROR: Method create" + er.sbmlType + "From(_) not implemented yet")

    }
  }

  def createModelFrom(mr:SBMLModelRecord) = {
    val m = new SBMLModel()
    m.metaid = mr.metaid
    m.id = mr.id
    m.name = mr.name
    m.notes = mr.notes
    m.listOfCompartments = Set.empty ++ mr.listOfCompartmentsRec.map(createCompartmentFrom(_))
    //TODO - write code for the remaining lists
    mr
  }

  def createCompartmentFrom(cr: CompartmentRecord):Compartment = {
    val c = new Compartment()
    c.metaid = cr.metaid
    c.id = cr.id
    c.name = cr.name
    c.notes = cr.notes
    c.compartmentType = cr.compartmentType
    c.spatialDimensions = cr.spatialDimensions
    c.size = cr.size
    c.units = cr.units
    c.outside = cr.outside
    c.constant = cr.constant
    c
  }
}

object RecordFromSBML extends LoggerWrapper {

  def createRecordFrom(er:Element) =  {
    er match {
      case m:SBMLModel => createModelRecordFrom(m)
      case c:Compartment => createCompartmentRecordFrom(c)
      case s:Species => createSpeciesRecordFrom(s)
      case p:Parameter => createParameterRecordFrom(p)
      case fd:FunctionDefinition => createFunctionDefinitionRecordFrom(fd)
      case ct:Constraint => createConstraintRecordFrom(ct)
      case r:Reaction => createReactionRecordFrom(r)
      //TODO - write code for the remaining sbml types
      case _ => throw new NotImplementedException("ERROR: Method create" + er.sbmlType + "From(_) not implemented yet")
    }
  }

  def createModelRecordFrom(m:SBMLModel):SBMLModelRecord = {
    val mr = new SBMLModelRecord()
    mr.metaid = m.metaid
    mr.id = m.id
    mr.name = m.name
    mr.notes = m.notes

    if(m.listOfCompartments != null){
      debug("Loaded listOfCompartments has size " + m.listOfCompartments.size)
      mr.listOfCompartmentsRec = m.listOfCompartments.map(createCompartmentRecordFrom(_)).toList
        .map(i => {
          i.parent = Full(mr) //to build complete URLs
          i
        }
      )
      debug("Finished copying list")
      debug("ListOfCompartmentsRec has size " + mr.listOfCompartmentsRec.size)
    }

    if(m.listOfSpecies != null){
      debug("Loaded listOfSpecies has size " + m.listOfSpecies.size)
      mr.listOfSpeciesRec = m.listOfSpecies.map(createSpeciesRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(mr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("ListOfSpeciesRec has size " + mr.listOfSpeciesRec.size)
    }

    if(m.listOfParameters != null){
      debug("Loaded listOfParameters has size " + m.listOfParameters.size)
      mr.listOfParametersRec = m.listOfParameters.map(createParameterRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(mr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfParametersRec has size " + mr.listOfParametersRec.size)
    }

    if(m.listOfFunctionDefinitions != null){
      debug("Loaded listOfFunctionDefinitions has size " + m.listOfFunctionDefinitions.size)
      mr.listOfFunctionDefinitionsRec = m.listOfFunctionDefinitions.map(createFunctionDefinitionRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(mr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfFunctionDefinitionsRec has size " + mr.listOfFunctionDefinitionsRec.size)
    }

    if(m.listOfConstraints != null){
      debug("Loaded listOfConstraints has size " + m.listOfConstraints.size)
      mr.listOfConstraintsRec = m.listOfConstraints.map(createConstraintRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(mr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfConstraintsRec has size " + mr.listOfConstraintsRec.size)
    }

    if(m.listOfReactions != null){
      debug("Loaded listOfReactions has size " + m.listOfReactions.size)
      mr.listOfReactionsRec = m.listOfReactions.map(createReactionRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(mr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfReactionsRec has size " + mr.listOfReactionsRec.size)
    }


    //TODO - write code for the remaining lists
    mr
  }

  def createCompartmentRecordFrom(c: Compartment):CompartmentRecord = {
    val cr = new CompartmentRecord()
    cr.metaid = c.metaid
    cr.id = c.id
    cr.name = c.name
    cr.notes = c.notes
    cr.compartmentType = c.compartmentType
    cr.spatialDimensions = c.spatialDimensions
    cr.size = c.size
    cr.units = c.units
    cr.outside = c.outside
    cr.constant = c.constant
    cr
  }

  def createSpeciesRecordFrom(s: Species):SpeciesRecord = {
    val sr = new SpeciesRecord()
    sr.metaid = s.metaid
    sr.id = s.id
    sr.name = s.name
    sr.notes = s.notes
    sr.compartment= s.compartment
    sr.initialAmount = s.initialAmount
    sr.initialConcentration = s.initialConcentration
    sr.substanceUnits = s.substanceUnits
    sr.hasOnlySubstanceUnits = s.hasOnlySubstanceUnits
    sr.boundaryCondition = s.boundaryCondition
    sr.constant = s.constant
    sr
  }

  def createParameterRecordFrom(p: Parameter):ParameterRecord = {
    val pr = new ParameterRecord()
    pr.metaid = p.metaid
    pr.id = p.id
    pr.name = p.name
    pr.notes = p.notes
    pr.value = p.value
    pr.units = p.units
    pr.constant = p.constant
    pr
  }

  def createFunctionDefinitionRecordFrom(fd: FunctionDefinition):FunctionDefinitionRecord = {
    val fdr = new FunctionDefinitionRecord()
    fdr.metaid = fd.metaid
    fdr.id = fd.id
    fdr.name = fd.name
    fdr.notes = fd.notes
    fdr.math = fdr.math
    fdr
  }

  def createConstraintRecordFrom(ct: Constraint):ConstraintRecord = {
    val ctr = new ConstraintRecord()
    ctr.metaid = ct.metaid
    ctr.id = ct.id
    ctr.name = ct.name
    ctr.notes = ct.notes
    ctr.math = ct.math
    ctr.message = ct.message
    ctr
  }

  def createReactionRecordFrom(r: Reaction):ReactionRecord = {
    val rr = new ReactionRecord()
    rr.metaid = r.metaid
    rr.id = r.id
    rr.name = r.name
    rr.notes = r.notes
    rr.reversible = r.reversible
    rr.fast = r.fast

    /* TODO complete the conversion from species to speciesReference
    if(r.listOfReactants != null){
      debug("Loaded listOfReactants has size " + r.listOfReactants.size)
      rr.listOfReactantsRec = r.listOfReactants.map(createReactionRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(rr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfReactantsRec has size " + rr.listOfReactantsRec.size)
    }


    var listOfProducts:java.util.Collection[SpeciesReference] = null
    if(r.listOfProducts != null){
      debug("Loaded listOfProducts has size " + r.listOfProducts.size)
      rr.listOfProductsRec = r.listOfProducts.map(createReactionRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(rr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfProductsRec has size " + rr.listOfProductsRec.size)
    }

    var listOfModifiers:java.util.Collection[ModifierSpeciesReference] = null
    if(r.listOfModifiers != null){
      debug("Loaded listOfModifiers has size " + r.listOfModifiers.size)
      rr.listOfModifiersRec = r.listOfModifiers.map(createReactionRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(rr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfModifiersRec has size " + rr.listOfModifiersRec.size)
    }*/
    if(r.kineticLaw != null)
      rr.kineticLawRec = createKineticLawRecordFrom(r.kineticLaw)

    rr
  }

  def createKineticLawRecordFrom(kl:KineticLaw) = {
    val klr = new KineticLawRecord()
    klr.metaid = kl.metaid
    klr.notes = kl.notes
    klr.math = kl.math

    if(kl.listOfParameters != null){
      debug("Loaded listOfParameters has size " + kl.listOfParameters.size)
      klr.listOfParametersRec = kl.listOfParameters.map(createParameterRecordFrom(_)).toList
        .map(i => {
        i.parent = Full(klr) //to build complete URLs
        i
      }
      )
      debug("Finished copying list")
      debug("listOfParametersRec has size " + klr.listOfParametersRec.size)
    }
    klr
  }

  //TODO WRITE VISITING FUNCTIONS for the remaining SBML entities
}