package pt.cnbc.wikimodels.dataAccess

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 26/Mai/2010
 * Time: 12:24:52
 * To change this template use File | Settings | File Templates.
 */

import org.junit._
import org.junit.Assert._
import com.hp.hpl.jena.rdf.model.{Model, ModelFactory}
import scala.xml.Elem
import pt.cnbc.wikimodels.dataModel.{SBMLModel, Reaction}
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.setup.Setup
import org.slf4j.LoggerFactory

@Test
class ReactionsDAOTest {
  val logger = LoggerFactory.getLogger(getClass)

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


  @Before
  def setUp: Unit = {
  }

  @After
  def tearDown: Unit = {
  }

  @Test
  def reactionRoundTrip = {
    var model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)

    //create reaction
    val daoModel = new SBMLModelsDAO()
    daoModel.tryToCreateSBMLModel(new SBMLModel(modelWReaction),
                                  model)

    //load reaction
    val newSBMLModel = daoModel.deepLoadSBMLModel("metaid_01", model)

    //check XML
    logger.debug("Old model is " + modelWReaction )
    logger.debug("New model is " + newSBMLModel.toXML )

    //check local parameter list
    assertEquals(newSBMLModel.metaid, "metaid_01")
    assertEquals( newSBMLModel.toXML \\ "listOfSpecies" \ "species" length,
                   modelWReaction \\ "listOfSpecies" \ "species" length )
    assertEquals( newSBMLModel.toXML \\ "listOfReactions" \ "reaction" length,
                   modelWReaction \\ "listOfReactions" \ "reaction" length )
    assertEquals( newSBMLModel.toXML \\ "listOfReactions" \ "reaction" \ "kineticLaw" length,
                   modelWReaction \\ "listOfReactions" \ "reaction" \ "kineticLaw" length )
    assertEquals( newSBMLModel.toXML \\ "listOfReactions" \ "reaction" \ "kineticLaw" \ "listOfaramters" \ "paramter" length,
                   modelWReaction \\ "listOfReactions" \ "reaction" \ "kineticLaw" \ "listOfaramters" \ "paramter" length)

  }

  @Test
  def reactionRoundTripNoKineticLaw = {
    var model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)

    //create reaction
    val daoModel = new SBMLModelsDAO()
    if(model==null)
      throw new Exception("4 - Model is null")
    daoModel.tryToCreateSBMLModel(new SBMLModel(modelWithReactionNoKineticLaw),
                                  model)
    //load reaction
    val newSBMLModel = daoModel.deepLoadSBMLModel("metaid_02", model)

    //check XML
    logger.debug("Old model is " + modelWithReactionNoKineticLaw )
    logger.debug("New model is " + newSBMLModel.toXML )

    //check local parameter list
    assertEquals( newSBMLModel.metaid, "metaid_02")
    assertEquals( newSBMLModel.toXML \\ "listOfSpecies" \ "species" length,
                   modelWithReactionNoKineticLaw \\ "listOfSpecies" \ "species" length )
    assertEquals( newSBMLModel.toXML \\ "listOfReactions" \ "reaction" length,
                   modelWithReactionNoKineticLaw \\ "listOfReactions" \ "reaction" length )
    assertEquals( newSBMLModel.toXML \\ "listOfReactions" \ "reaction" \ "kineticLaw" length,
                  0 )
  }

  val modelWithReactionNoLocalParameters:Elem =
  <model metaid="metaid_03" id="simpleModel">
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


  @Test
  def reactionRoundTripNoLocalParameters = {
    var model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)

    //create reaction
    val daoModel = new SBMLModelsDAO()
    daoModel.tryToCreateSBMLModel(new SBMLModel(modelWithReactionNoLocalParameters),
                                  model)

    //load reaction
    val newSBMLModel = daoModel.deepLoadSBMLModel("metaid_03", model)

    //check XML
    logger.debug("Old model is " + modelWithReactionNoLocalParameters )
    logger.debug("New model is " + newSBMLModel.toXML )

    //check local parameter list
    assertEquals(newSBMLModel.metaid, "metaid_03")
    assertEquals( newSBMLModel.toXML \\ "listOfSpecies" \ "species" length,
                   modelWithReactionNoLocalParameters \\ "listOfSpecies" \ "species" length )
    assertEquals( newSBMLModel.toXML \\ "listOfReactions" \ "reaction" length,
                   modelWithReactionNoLocalParameters \\ "listOfReactions" \ "reaction" length )
    assertEquals( newSBMLModel.toXML \\ "listOfReactions" \ "reaction" \ "kineticLaw" \ "listOfaramters" \ "paramter" length,
                   0)
  }

}
