package pt.cnbc.wikimodels.dataAccess

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 26/Mai/2010
 * Time: 12:24:52
 * To change this template use File | Settings | File Templates.
 */

import org.junit._
import Assert._
import pt.cnbc.wikimodels.setup.Setup
import com.hp.hpl.jena.rdf.model.{Model, ModelFactory}
import xml.Elem
import pt.cnbc.wikimodels.dataModel.{SBMLModel, Reaction}
import java.io.File
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper

class ReactionsDAOTest {

  val modelWReaction:Elem =
  <model metaid="metaid_01" id="simpleModel">
    <listOfUnitDefinitions>
      <unitDefinition id="per_concent_per_time">
        <listOfUnits>
            <unit kind="litre"/>
            <unit kind="mole" exponent="-1"/>
            <unit kind="second" exponent="-1"/>
        </listOfUnits>
      </unitDefinition>
    </listOfUnitDefinitions>
    <listOfSpecies>
        <species id="S1" compartment="c1" initialConcentration="2.0"/>
        <species id="S2" compartment="c1" initialConcentration="0.5"/>
        <species id="X0" compartment="c1" initialConcentration="1.0"/>
    </listOfSpecies>
    <listOfReactions>
      <reaction id="J1">
        <listOfReactants>
            <speciesReference id="RefX0" species="X0"/>
        </listOfReactants>
        <listOfProducts>
            <speciesReference id="RefS1" species="S1"/>
        </listOfProducts>
        <listOfModifiers>
            <modifierSpeciesReference id="RefS2" species="S2"/>
        </listOfModifiers>
        <kineticLaw>
          <math xmlns="http://www.w3.org/1998/Math/MathML">
            <apply>
                <times/> <ci>k</ci> <ci>S2</ci> <ci>X0</ci> <ci>c1</ci>
            </apply>
          </math>
          <listOfParameters>
              <parameter id="k" value="0.1" units="per_concent_per_time"/>
          </listOfParameters>
        </kineticLaw>
      </reaction>
    </listOfReactions>
  </model>

  val modelWithReactionNoKineticLaw:Elem =
  <model metaid="metaid_02" id="simpleModel">
    <listOfUnitDefinitions>
      <unitDefinition id="per_concent_per_time">
        <listOfUnits>
            <unit kind="litre"/>
            <unit kind="mole" exponent="-1"/>
            <unit kind="second" exponent="-1"/>
        </listOfUnits>
      </unitDefinition>
    </listOfUnitDefinitions>
    <listOfSpecies>
        <species id="S1" compartment="c1" initialConcentration="2.0"/>
        <species id="S2" compartment="c1" initialConcentration="0.5"/>
        <species id="X0" compartment="c1" initialConcentration="1.0"/>
    </listOfSpecies>
    <listOfReactions>
      <reaction id="J1">
        <listOfReactants>
            <speciesReference id="RefX0" species="X0"/>
        </listOfReactants>
        <listOfProducts>
            <speciesReference id="RefS1" species="S1"/>
        </listOfProducts>
        <listOfModifiers>
            <modifierSpeciesReference id="RefS2" species="S2"/>
        </listOfModifiers>
      </reaction>
    </listOfReactions>
  </model>

  var model:Model  = null

  @Before
  def setUp: Unit = {
    model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)
  }

  @After
  def tearDown: Unit = {
    model.close
  }

  @Test
  def reactionRoundTrip = {
    //create reaction
    val daoModel = new SBMLModelsDAO()
    daoModel.trytoCreateSBMLModel(
      new SBMLModel(modelWReaction),
      this.model)
    //load reaction
    Console.println("---Pretty Printing Jena Model---")
    ManipulatorWrapper.iterateAndPrintModel(model)
    Console.println("-----------------================================================================================================================================---------------")
    val newSBMLModel = daoModel.deepLoadSBMLModel("metaid_01", model)
    //check XML
    Console.println("Old model is " + modelWReaction )
    Console.println("New model is " + newSBMLModel.toXML )
    //check local parameter list
    assertEquals(newSBMLModel.metaid, "metaid_01")
    assertEquals( (newSBMLModel.toXML \\ "listOfReactions" \ "reaction").length, 1)
  }

  @Test
  def reactionRoundTripNoKineticLaw = {
    //create reaction
    val daoModel = new SBMLModelsDAO()
    daoModel.trytoCreateSBMLModel(
      new SBMLModel(modelWithReactionNoKineticLaw),
      this.model)
    //load reaction

    val newSBMLModel = daoModel.deepLoadSBMLModel("metaid_02", model)
    //check XML
    Console.println("Old model is " + modelWithReactionNoKineticLaw )
    Console.println("New model is " + newSBMLModel.toXML )
    //check local parameter list
    assertEquals(newSBMLModel.metaid, "metaid_02")
    assertEquals( (newSBMLModel.toXML \\ "listOfReactions" \ "reaction").length, 1)
  }
}
