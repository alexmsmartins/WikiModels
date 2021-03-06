package pt.cnbc.wikimodels.client.record.visitor

import scala.collection.JavaConversions._

import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel._
import net.liftweb.common.{Empty, Box, Full}
import net.liftweb.common.Box._
import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import visitor.SBMLFromRecord.createKineticLawFrom
import pt.cnbc.wikimodels.mathparser.{AsciiMathPrettyPrinter, MathMLMatchParser, MathMLPrettyPrinter, AsciiMathParser}
import pt.cnbc.wikimodels.mathml.elements.MathMLElem
import scala.xml.Utility

/*
* Copyright (c) 2011. Alexandre Martins. All rights reserved.
*/

object SBMLFromRecord extends LoggerWrapper {
  import scala.language.implicitConversions

  def createSBMLElementFrom[T <: SBaseRecord[T]](er:T):Element =  {
    er match {
      case mr:SBMLModelRecord => createModelFrom(mr)
      case cr:CompartmentRecord => createCompartmentFrom(cr)
      case sr:SpeciesRecord => createSpeciesFrom(sr)
      case pr:ParameterRecord => createParameterFrom(pr)
      case ct:ConstraintRecord => createConstraintFrom(ct)
      case r:ReactionRecord => createReactionFrom(r)
      case fd:FunctionDefinitionRecord => createFunctionDefinitionFrom(fd)
      case rt:ReactantRecord => createSpeciesReferenceFrom(rt)
      case pd:ProductRecord => createSpeciesReferenceFrom(pd)
      case mf:ModifierRecord => createModifierSpeciesReferenceFrom(mf)
      case t => throw new NotImplementedException("ERROR: Method create" + er.sbmlType + "From(_) not implemented yet"); null
    }
  }

  implicit def createModelFrom(mr:SBMLModelRecord):SBMLModel = {
    val m = new SBMLModel()
    m.metaid = mr.metaIdO.get
    m.id = mr.idO.get
    m.name = mr.nameO.get.getOrElse(null)
    m.notes = mr.notesO.get.getOrElse(null)
    m.listOfCompartments = Set.empty ++ mr.listOfCompartmentsRec.map(createCompartmentFrom(_))
    m.listOfSpecies = Set.empty ++ mr.listOfSpeciesRec.map(createSpeciesFrom(_))
    m.listOfParameters = Set.empty ++ mr.listOfParametersRec.map(createParameterFrom(_))
    m.listOfConstraints = Set.empty ++ mr.listOfConstraintsRec.map(createConstraintFrom(_))
    m.listOfReactions = Set.empty ++ mr.listOfReactionsRec.map(createReactionFrom(_))

    //TODO - write code for the remaining lists
    m
  }

  implicit def createCompartmentFrom(cr: CompartmentRecord):Compartment = {
    val c = new Compartment()
    c.metaid = cr.metaIdO.get
    c.id = cr.idO.get
    c.name = cr.nameO.get.getOrElse(null)
    c.notes = cr.notesO.get.getOrElse(null)
    //c.compartmentType = cr.compartmentType
    c.spatialDimensions = cr.spatialDimensions0.get.id
    c.size = cr.sizeO.get.getOrElse(null).asInstanceOf[java.lang.Double]
    //c.units = cr.units
    c.outside = cr.outsideO.get.getOrElse(null)
    c.constant = cr.constantO.get
    c
  }

  implicit def createSpeciesFrom(sr:SpeciesRecord):Species = {
    val s = new Species()
    s.metaid = sr.metaIdO.get
    s.id = sr.idO.get
    s.name = sr.nameO.get.getOrElse(null)
    s.notes = sr.notesO.get.getOrElse(null)
    s.compartment= sr.compartmentO.get
    s.initialAmount = sr.initialAmountO.get.getOrElse(null).asInstanceOf[java.lang.Double]
    s.initialConcentration = sr.initialConcentrationO.get.getOrElse(null).asInstanceOf[java.lang.Double]
    //s.substanceUnits = sr.substanceUnits
    //s.hasOnlySubstanceUnits = sr.hasOnlySubstanceUnits
    s.boundaryCondition = sr.boundaryConditionO.get
    s.constant = sr.constantO.get
    s
  }

  implicit def createParameterFrom(pr:ParameterRecord):Parameter = {
    val p = new Parameter
    p.metaid = pr.metaIdO.get
    p.id = pr.idO.get
    p.name = pr.nameO.get.getOrElse(null)
    p.notes = pr.notesO.get.getOrElse(null)
    p.value = pr.valueO.get.getOrElse(null).asInstanceOf[java.lang.Double]
    //p.units = pr.units
    p.constant = pr.constantO.get
    p
  }

  implicit def createFunctionDefinitionFrom(fdr:FunctionDefinitionRecord):FunctionDefinition = {
    val fd = new FunctionDefinition
    fd.metaid = fdr.metaIdO.get
    fd.id = fdr.idO.get
    fd.name = fdr.nameO.get.getOrElse(null)
    fd.notes = fdr.notesO.get.getOrElse(null)

    val amp =  AsciiMathParser()
    val ast:amp.ParseResult[amp.MME] = amp.parseAll( amp.LambdaExpr, fdr.mathO.get )
    fd.math = MathMLPrettyPrinter.toXML(ast.get).toString()
    debug("fdr.math0.get = " + fdr.mathO.get)
    debug("fd.math = " + fd.math)
    fd
  }

  implicit def createConstraintFrom(ctr: ConstraintRecord):Constraint = {
    val ct = new Constraint
    ct.metaid = ctr.metaIdO.get
    ct.id = ctr.idO.get
    ct.name = ctr.nameO.get.getOrElse(null)
    ct.notes = ctr.notesO.get.getOrElse(null)
    ct.message = ctr.mathO.get
    ct
  }

  implicit def createReactionFrom(rr: ReactionRecord): Reaction = {
    val r = new Reaction()
    r.metaid = rr.metaIdO.get
    r.id = rr.idO.get
    r.name = rr.nameO.get.getOrElse(null)
    r.notes = rr.notesO.get.getOrElse(null)
    //TODO r.reversible = rr.reversible
    //todo r.fast = rr.fast


    if (rr.listOfReactantsRec != null) {
      debug("Loaded listOfReactants has size " + r.listOfReactants.size)
      r.listOfReactants = rr.listOfReactantsRec.map(createSpeciesReferenceFrom(_)).toList
      debug("Finished copying list")
      debug("listOfReactantsRec has size " + rr.listOfReactantsRec.size)
    }


    var listOfProducts: java.util.Collection[SpeciesReference] = null
    if (rr.listOfProductsRec != null) {
      debug("Loaded listOfProducts has size " + r.listOfProducts.size)
      r.listOfProducts = rr.listOfProductsRec.map(createSpeciesReferenceFrom(_)).toList
      debug("Finished copying list")
      debug("listOfProductsRec has size " + rr.listOfProductsRec.size)
    }

    var listOfModifiers: java.util.Collection[ModifierSpeciesReference] = null
    if (rr.listOfModifiersRec != null) {
      debug("Loaded listOfModifiers has size " + r.listOfModifiers.size)
      r.listOfModifiers = rr.listOfModifiersRec.map(createModifierSpeciesReferenceFrom(_)).toList
        debug("Finished copying list")
      debug("listOfModifiersRec has size " + rr.listOfModifiersRec.size)
    }
    if (rr.kineticLawRec != null)
      r.kineticLaw = createKineticLawFrom(rr.kineticLawRec)
    r
  }

  implicit def createSpeciesReferenceFrom(rtr: ReactantRecord):SpeciesReference = {
    val rt = new SpeciesReference
    rt.metaid = rtr.metaIdO.get
    rt.id = rtr.idO.get
    rt.name = rtr.nameO.get.getOrElse(null)
    rt.notes = rtr.notesO.get.getOrElse(null)
    rt.stoichiometry = {
      throw new RuntimeException("Add stoichiometry field into SpeciesReference")
    }
    rt.stoichiometryMath = {
      throw new RuntimeException("Add stoichiometryMath field into SpeciesReference")
    }
    rt.species = {
      throw new RuntimeException("Add species field into SpeciesReference")
    }
    rt
  }

  implicit def createSpeciesReferenceFrom(pdr: ProductRecord):SpeciesReference = {
    val pd = new SpeciesReference
    pd.metaid = pdr.metaIdO.get
    pd.id = pdr.idO.get
    pd.name = pdr.nameO.get.getOrElse(null)
    pd.notes = pdr.notesO.get.getOrElse(null)
    pd.stoichiometry = {
      throw new RuntimeException("Add stoichiometry field into SpeciesReference")
    }
    pd.stoichiometryMath = {
      throw new RuntimeException("Add stoichiometryMath field into SpeciesReference")
    }
    pd.species = {
      throw new RuntimeException("Add species field into SpeciesReference")
    }
    pd
  }

  implicit def createModifierSpeciesReferenceFrom(mdr: ModifierRecord):ModifierSpeciesReference = {
    val md = new ModifierSpeciesReference
    md.metaid = mdr.metaIdO.get
    md.id = mdr.idO.get
    md.name = mdr.nameO.get.getOrElse(null)
    md.notes = mdr.notesO.get.getOrElse(null)
    md.species = {
      throw new RuntimeException("Add species field into ModifierSpeciesReference")
    }
    md
  }



  implicit def createKineticLawFrom(klr:KineticLawRecord):KineticLaw = {
    val kl = new KineticLaw
    kl.metaid = klr.metaIdO.get
    kl.notes = klr.notesO.get.getOrElse(null)
    kl.math = klr.mathO.get
    kl.listOfParameters = Set.empty ++ klr.listOfParametersRec.map(createParameterFrom(_))
    kl
  }
}

object RecordFromSBML extends LoggerWrapper {

  def createRecordFrom(er:Element):SBaseRecord[_] =  {
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

  implicit def createModelRecordFrom(m:SBMLModel):SBMLModelRecord = {
    val mr = new SBMLModelRecord()
    mr.metaIdO.set(m.metaid)
    mr.idO.set(m.id)
    mr.nameO.setBox(Box.legacyNullTest(m.name))
    mr.notesO.setBox(Box.legacyNullTest(m.notes))

    //TODO to simplify this code try to replace the initialization if listOf in wm_libjsbml from null to Set.empty or Nil
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

  implicit def createCompartmentRecordFrom(c: Compartment):CompartmentRecord = {
    val cr = new CompartmentRecord()
    cr.metaIdO.set(c.metaid)
    cr.idO.set(c.id)
    cr.nameO.setBox(Box.legacyNullTest(c.name))
    cr.notesO.setBox(Box.legacyNullTest(c.notes))
    //cr.compartmentType = c.compartmentType
    cr.spatialDimensions0.set(
      ValidSpatialDimensions(c.spatialDimensions))
    cr.sizeO.setBox(jDoubleToBoxDouble(c.size))
    //cr.units = c.units
    cr.outsideO.setBox(Box.legacyNullTest( c.outside))
    cr.constantO.set(c.constant)
    cr
  }

  implicit def createSpeciesRecordFrom(s: Species):SpeciesRecord = {
    val sr = new SpeciesRecord()
    sr.metaIdO.set(s.metaid)
    sr.idO.set(s.id)
    sr.nameO.setBox(Box.legacyNullTest(s.name))
    sr.notesO.setBox(Box.legacyNullTest(s.notes))
    sr.compartmentO.set(s.compartment)
    sr.initialAmountO.setBox(jDoubleToBoxDouble(s.initialAmount))
    sr.initialConcentrationO.setBox(jDoubleToBoxDouble(s.initialConcentration))
    //sr.substanceUnits = s.substanceUnits
    //sr.hasOnlySubstanceUnits = s.hasOnlySubstanceUnits
    sr.boundaryConditionO.setBox(Box.legacyNullTest(s.boundaryCondition))
    sr.constantO.set(s.constant)
    sr
  }

  implicit def createParameterRecordFrom(p: Parameter):ParameterRecord = {
    val pr = new ParameterRecord()
    pr.metaIdO.set(p.metaid)
    pr.idO.set(p.id)
    pr.nameO.setBox(Box.legacyNullTest(p.name))
    pr.notesO.setBox(Box.legacyNullTest(p.notes))
    pr.valueO.setBox(jDoubleToBoxDouble(p.value))
    //pr.units = p.units
    pr.constantO.set(p.constant)
    pr
  }

  implicit def createFunctionDefinitionRecordFrom(fd: FunctionDefinition):FunctionDefinitionRecord = {
    val fdr = new FunctionDefinitionRecord()
    fdr.metaIdO.set(fd.metaid)
    fdr.idO.set(fd.id)
    fdr.nameO.setBox(Box.legacyNullTest(fd.name))
    fdr.notesO.setBox(Box.legacyNullTest(fd.notes))

    debug("fd.math is " + fd.math)


    val <math>{xmlLambda}</math> = Utility.trim(scala.xml.XML.loadString(fd.math))

    debug("Lambda is " + xmlLambda)

    val p = MathMLMatchParser()
    val mathMLaST = p.parse(xmlLambda.asInstanceOf[scala.xml.Elem])
    val asciiLambda =  AsciiMathPrettyPrinter.toAsciiMathML(mathMLaST)
    fdr.mathO.set(asciiLambda)
    fdr
  }

  implicit def createConstraintRecordFrom(ct: Constraint):ConstraintRecord = {
    val ctr = new ConstraintRecord()
    ctr.metaIdO.set(ct.metaid)
    ctr.idO.set(ct.id)
    ctr.nameO.setBox(Box.legacyNullTest(ct.name))
    ctr.notesO.setBox(Box.legacyNullTest(ct.notes))
    ctr.mathO.set(ct.math)
    //TODO - ctr.message = ct.message
    ctr
  }

  implicit def createReactionRecordFrom(r: Reaction):ReactionRecord = {
    val rr = new ReactionRecord()
    rr.metaIdO.set(r.metaid)
    rr.idO.set(r.id)
    rr.nameO.setBox(Box.legacyNullTest(r.name))
    rr.notesO.setBox(Box.legacyNullTest(r.notes))
    //rr.reversible = r.reversible
    //rr.fast = r.fast

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

  implicit def createKineticLawRecordFrom(kl:KineticLaw) = {
    val klr = new KineticLawRecord()
    klr.metaIdO.set(kl.metaid)
    klr.notesO.setBox(Box.legacyNullTest(kl.notes))
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

  protected def jDoubleToBoxDouble(i:java.lang.Double):Box[Double] = {
    trace("RecordFromSBML.jDoubleToBoxDouble( " + i + " )")
    i match {
      case null => Empty
      case x => Full(x)
    }
  }
}