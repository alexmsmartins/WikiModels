/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel.SpeciesReference
import xml.NodeSeq
import net.liftweb.common.Full._
import net.liftweb.http.{SHtml, S}
import net.liftweb.record._
import net.liftweb.common.{Full, Empty, Box}
import pt.cnbc.wikimodels.exceptions.NotImplementedException

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 29-12-2011
 *  Time: 16:49
 *  To change this template use File | Settings | File Templates. */
case class SpeciesReferenceRecord() extends SBaseRecord[SpeciesReferenceRecord]  {
  override val sbmlType = "SpeciesReference"

  var referenceType:String = {
    throw new RuntimeException("SpeciesReference referenceType should be a reactant, a product or a modifier.")
  }
  override def meta = SpeciesReferenceRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: referenceType :: this.metaIdO.get :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "species" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling SpeciesReferenceRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }

  override def toForm(f:SpeciesReferenceRecord => Unit):NodeSeq = {
    trace("Calling SpeciesReferenceRecord.toForm( "+f+" )")
    <div class="speciesreference_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      {
      val defaultOption:(Box[SpeciesRecord], String) = (Empty, "[no spcies")
      val op = parent.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").
        listOfSpeciesRec
      val defaultOp = parent.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").listOfSpeciesRec.filter( _.idO.is == species ).headOption
      val opWithId = op.map(i => (i, i.idO.is):(SpeciesRecord, String) )
      val options = (List((null , "no species")) ::: opWithId.toList).toSeq
      SHtml.selectObj(options, Box.option2Box(defaultOp),
        (choice:SpeciesRecord ) => {
          choice match {
            case null => species = null
            case c => species = c.idO.is
          }
        })
      }
    </div>
  }

  var species:String = throw new NotImplementedException(""" 'species' should be deleted from code!""")

  //  ### will contain fields which can be listed with allFields. ###
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)

  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[SBMLModelRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[SBMLModelRecord] = _parent.asInstanceOf[Box[SBMLModelRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[SBMLModelRecord]]
  }
}

//TODO - DELETE IF NOT USED FOR ANYTHING
object SpeciesReferenceRecord extends SpeciesReferenceRecord with RestMetaRecord[SpeciesReferenceRecord] {
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}


/** TODO: Please document.
  *  @author Alexandre Martins
  *  Date: 14-11-2013
  *  Time: 16:31
  *  To change this template use File | Settings | File Templates. */
case class ReactantRecord() extends SBaseRecord[ReactantRecord]  {
  override val sbmlType = "SpeciesReference"

  var referenceType:String = "reactant"

  override def meta = ReactantRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: referenceType :: this.metaIdO.get :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "species" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling ReactantRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }

  override def toForm(f:ReactantRecord => Unit):NodeSeq = {
    trace("Calling ReactantRecord.toForm( "+f+" )")
    <div class="reactant_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      {
      val defaultOption:(Box[SpeciesRecord], String) = (Empty, "[no spcies")
      val op:List[SpeciesRecord] = parent.openOrThrowException("Parent of ReactantRecord should be made available")
        .parent.openOrThrowException("Parent of ReactionRecord should be made available")
        .listOfSpeciesRec
      val defaultOp = parent.openOrThrowException("Parent of ReactantRecord should be made available")
        .parent.openOrThrowException("Parent of ReactionRecord should be made available")
        .listOfSpeciesRec.filter( _.idO.is == species ).headOption
      val opWithId = op.map(i => (i, i.idO.is):(SpeciesRecord, String) )
      val options = (List((null , "no species")) ::: opWithId.toList).toSeq
      SHtml.selectObj(options, Full(op.head),
        (choice:SpeciesRecord ) => {
           this.speciesO.set( choice.idO.get)
        })
      }
    </div>
  }

  var species:String = "TODO no SPECIES"

  //  ### will contain fields which can be listed with allFields. ###
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)
  object speciesO extends RtSpecies(this)

  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[ReactionRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[ReactionRecord] = _parent.asInstanceOf[Box[ReactionRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[ReactionRecord]]
  }
}



//TODO - DELETE IF NOT USED FOR ANYTHING
object ReactantRecord extends ReactantRecord with RestMetaRecord[ReactantRecord] {
  override def fieldOrder = List(metaIdO, idO, nameO, notesO, speciesO)
  override def fields = fieldOrder
}

/** TODO: Please document.
  *  @author Alexandre Martins
  *  Date: 14-11-2013
  *  Time: 16:49
  *  To change this template use File | Settings | File Templates. */
case class ProductRecord() extends SBaseRecord[ProductRecord]  {
  override val sbmlType = "SpeciesReference"

  var referenceType:String = "product"

  override def meta = ProductRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: referenceType :: this.metaIdO.get :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "species" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling ProductRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }

  override def toForm(f:ProductRecord => Unit):NodeSeq = {
    trace("Calling ProductRecord.toForm( "+f+" )")
    <div class="product_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      {
      val defaultOption:(Box[SpeciesRecord], String) = (Empty, "[no spcies")
      val op = parent.openOrThrowException("Parent of ProductRecord should be made available")
        .parent.openOrThrowException("Parent of ReactionRecord should be made available")
        .listOfSpeciesRec
      val defaultOp = parent.openOrThrowException("Parent of ProductRecord should be made available")
        .parent.openOrThrowException("Parent of ReactionRecord should be made available")
        .listOfSpeciesRec.filter( _.idO.is == species ).headOption
      val opWithId = op.map(i => (i, i.idO.is):(SpeciesRecord, String) )
      val options = (List((null , "no species")) ::: opWithId.toList).toSeq
      SHtml.selectObj(options, Box.option2Box(defaultOp),
        (choice:SpeciesRecord ) => {
          choice match {
            case null => species = null
            case c => species = c.idO.is
          }
        })
      }
    </div>
  }

  var species:String = throw new NotImplementedException(""" 'species' should be deleted from code!""")

  //  ### will contain fields which can be listed with allFields. ###
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)

  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[ReactionRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[ReactionRecord] = _parent.asInstanceOf[Box[ReactionRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[ReactionRecord]]
  }

}



//TODO - DELETE IF NOT USED FOR ANYTHING
object ProductRecord extends ProductRecord with RestMetaRecord[ProductRecord] {
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}


/** TODO: Please document.
  *  @author Alexandre Martins
  *  Date: 14-11-2013
  *  Time: 16:52
  *  To change this template use File | Settings | File Templates. */
case class ModifierRecord() extends SBaseRecord[ModifierRecord]  {
  override val sbmlType = "SpeciesReference"

  var referenceType:String = "modifier"

  override def meta = ModifierRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: referenceType :: this.metaIdO.get :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "species" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling ModifierRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }

  override def toForm(f:ModifierRecord => Unit):NodeSeq = {
    trace("Calling ModifierRecord.toForm( "+f+" )")
    <div class="modifier_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      {
      val defaultOption:(Box[SpeciesRecord], String) = (Empty, "[no spcies")
      val op = parent.openOrThrowException("Parent of ModifierRecord should be made available")
        .parent.openOrThrowException("Parent of ReactionRecord should be made available")
        .listOfSpeciesRec
      val defaultOp = parent.openOrThrowException("Parent of ModifierRecord should be made available")
        .parent.openOrThrowException("Parent of ReactionRecord should be made available")
        .listOfSpeciesRec.filter( _.idO.is == species ).headOption
      val opWithId = op.map(i => (i, i.idO.is):(SpeciesRecord, String) )
      val options = (List((null , "no species")) ::: opWithId.toList).toSeq
      SHtml.selectObj(options, Box.option2Box(defaultOp),
        (choice:SpeciesRecord ) => {
          choice match {
            case null => species = null
            case c => species = c.idO.is
          }
        })
      }
    </div>
  }

  var species:String = throw new NotImplementedException(""" 'species' should be deleted from code!""")

  //  ### will contain fields which can be listed with allFields. ###
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)

  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[ReactionRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[ReactionRecord] = _parent.asInstanceOf[Box[ReactionRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[ReactionRecord]]
  }

}



//TODO - DELETE IF NOT USED FOR ANYTHING
object ModifierRecord extends ModifierRecord with RestMetaRecord[ModifierRecord] {
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}
