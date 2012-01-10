package pt.cnbc.wikimodels.dataAccess

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 22/Mai/2010
 * Time: 0:58:07
 * To change this template use File | Settings | File Templates.
 */
import org.junit._
import Assert._
import com.hp.hpl.jena.rdf.model.ModelFactory
import pt.cnbc.wikimodels.setup.Setup
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.sbmlVisitors.SBML2BeanConverter
import pt.cnbc.wikimodels.dataAccess._

class SBMLModelsDAOTest {

  @Before
  def setUp: Unit = {
    }

  @After
  def tearDown: Unit = {
    }

  @Test
  def example = {
    }

  val modelWithNotes =
<model metaid="xxx" id="mit_osc" name="Mitotic oscillator">
  <notes>
    <xhtml:body>
      <xhtml:center><xhtml:h2>A Simple Mitotic Oscillator</xhtml:h2></xhtml:center>
       <xhtml:p>A minimal cascade model for the mitotic oscillator
       involving cyclin and cdc2 kinase</xhtml:p>
     </xhtml:body>
 </notes>
</model>


  @Test
  def createModelAndTestNotes = {
    var model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)    
    val dao = new SBMLModelsDAO
    dao.createSBMLModel( SBML2BeanConverter.visitModel(modelWithNotes) , model)
    val modelWithNotes2 = dao.deepLoadSBMLModel("xxx", model).toXML

    //TODO A  SOLUTION must be found to handle explicit namespace or xhtml prefix usage gracefully
    assert( modelWithNotes2 \ "notes" \ "body" != null &&
      (modelWithNotes2 \ "notes" \ "body").length >0 )

  }

}
