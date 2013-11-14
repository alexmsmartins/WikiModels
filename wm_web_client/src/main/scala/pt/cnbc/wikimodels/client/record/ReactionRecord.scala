/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel._
import net.liftweb.record.{Notes, Name, Id, MetaId}
import net.liftweb.common.{Empty, Box}
import net.liftweb.http.{SHtml, S}
import scala.xml._

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 29-12-2011
 *  Time: 17:13 */
case class ReactionRecord() extends SBaseRecord[ReactionRecord]  {
  override val sbmlType = "Reaction"

  //listOf definitions for record
  //TODO - find a better solution to this. Parents' listOfXXX definitions is a big problem
  //TODO CHANGE SpeciesReference too a record version
  var listOfReactantsRec:List[ReactantRecord] = Nil
  var listOfProductsRec:List[ProductRecord] = Nil
  var listOfModifiersRec:List[ModifierRecord] = Nil
  var kineticLawRec:KineticLawRecord = null //optional


  override def meta = ReactionRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: this.metaIdO.get :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    <div>

      {super.toXHtml}
      <h3 id={"accord_r_"+this.metaIdO.get+"_react_"} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
        <a href={"#accord_r_"+this.metaIdO.get+"_react_"}>
          {this.listOfReactantsRec.size} Reactants
          <form style='display:inline;' >{SHtml.button(
            Text("Add Reactant"),
            () => {
              debug("Button to add Reactant, pressed.")
              S.redirectTo(this.relativeURL + "/createreactant" )
            },
            "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
          )}</form>
        </a>
      </h3>
        <div class="toggle_container">
          <div id="accordion_r_react" class="block">{
            this.listOfReactantsRec.map(i => {
              <h3 id={"accord_r_"+this.metaIdO.get+"_react_"+i.metaIdO.get} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href={"#accord_r_"+this.metaIdO.get+"_react_"+i.metaIdO.get }>
                  {i.idO.get}
                  <form style='display:inline;' >{SHtml.button(Text("Edit"),
                    () => {
                      debug("Button to edit compartment, pressed.")
                      S.redirectTo(i.relativeURL + "/edit" )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                  <form style='display:inline;' >{SHtml.button(Text("Delete"),
                    () => {
                      debug("Button to delete reactant, pressed.")
                      S.redirectTo(i.relativeURL + "/delete" )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                </a>
              </h3>
                <div class="toggle_container">
                  <div class="block">{
                    i.toXHtml
                    }
                  </div>
                </div>})
            }
          </div>
        </div>

        <h3 id={"accord_r_"+this.metaIdO.get+"_prod"} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
          <a href={"#accord_r_"+this.metaIdO.get+"_prod"}>
            {this.listOfProductsRec.size} Products
            <form style='display:inline;' >{SHtml.button(
              Text("Add Product"),
              () => {
                debug("Button to add Product, pressed.")
                S.redirectTo(this.relativeURL + "/createproduct" )
              },
              "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
            )}</form>
          </a>
        </h3>
        <div class="toggle_container">
          <div id="accordion_r_react" class="block">{
            this.listOfProductsRec.map(i => {
              <h3 id={"accord_r_"+this.metaIdO.get+"_prod_"+i.metaIdO.get} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href={"#accord_r_"+this.metaIdO.get+"_prod_"+i.metaIdO.get }>
                  {i.idO.get}
                  <form style='display:inline;' >{SHtml.button(Text("Edit"),
                    () => {
                      debug("Button to edit product, pressed.")
                      S.redirectTo(i.relativeURL + "/edit" )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                  <form style='display:inline;' >{SHtml.button(Text("Delete"),
                    () => {
                      debug("Button to delete product, pressed.")
                      S.redirectTo(i.relativeURL + "/delete" )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                </a>
              </h3>
                <div class="toggle_container">
                  <div class="block">{
                    i.toXHtml
                    }
                  </div>
                </div>})
            }
          </div>
        </div>

        <h3 id={"accord_r_"+this.metaIdO.get+"_modif"} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
          <a href={"#accord_r_"+this.metaIdO.get+"_modif"}>
            {this.listOfModifiersRec.size} Modifiers
            <form style='display:inline;' >{SHtml.button(
              Text("Add Modifier"),
              () => {
                debug("Button to add Modifier, pressed.")
                S.redirectTo(this.relativeURL + "/createmodifier" )
              },
              "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
            )}</form>
          </a>
        </h3>
        <div class="toggle_container">
          <div id="accordion_r_react" class="block">{
            this.listOfModifiersRec.map(i => {
              <h3 id={"accord_r_"+this.metaIdO.get+"_modif_"+i.metaIdO.get} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href={"#accord_r_"+this.metaIdO.get+"_modif_"+i.metaIdO.get }>
                  {i.idO.get}
                  <form style='display:inline;' >{SHtml.button(Text("Edit"),
                    () => {
                      debug("Button to edit modifier, pressed.")
                      S.redirectTo(i.relativeURL + "/edit" )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                  <form style='display:inline;' >{SHtml.button(Text("Delete"),
                    () => {
                      debug("Button to delete modifier, pressed.")
                      S.redirectTo(i.relativeURL + "/delete" )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                </a>
              </h3>
                <div class="toggle_container">
                  <div class="block">{
                    i.toXHtml
                    }
                  </div>
                </div>})
            }
          </div>
        </div>
      {
        //TODO - this if is temporary
        if(this.kineticLawRec != null)
          this.kineticLawRec
            .toXHtml
        else <div note="emptykineticlaw"/>
      }
    </div>
  }

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
object ReactionRecord extends ReactionRecord with RestMetaRecord[ReactionRecord] {
  override def fieldOrder = List(metaIdO, idO, nameO, /*sizeO,*/ notesO)
  override def fields = fieldOrder
}
