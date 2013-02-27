package net.liftweb.record

import net.liftweb.common._
import net.liftweb.http.{S, SHtml}
import net.liftweb.record.field._
import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.client.record._
import net.liftweb.util.FieldError
import net.liftweb.util.ControlHelpers._
import xml.{Elem, Text, XML, NodeSeq}
import pt.cnbc.wikimodels.dataModel._
import pt.cnbc.wikimodels.dataModel.ValidSpatialDimensions
import pt.cnbc.wikimodels.dataModel.ValidSpatialDimensions._
import pt.cnbc.wikimodels.sbmlVisitors.SBMLLooseValidator
import pt.cnbc.wikimodels.exceptions.ValidationDefaultCase._
import xml.Text
import net.liftweb.http.js.JsCmds.JsCrVar
import net.liftweb.common.Full
import pt.cnbc.wikimodels.exceptions.BadFormatException

//Javascript handling imports
import net.liftweb.http.js.{JsExp, JE, JsCmd, JsCmds}
import JsCmds._ // For implicifts
import net.liftweb.http.js.JE.{JsVar, Call, JsRaw, Str}

import scala.util.parsing.combinator.Parsers

class MetaId[T <: SBaseRecord[T]](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayHTMLWithLabelInOneLine[String, T]{

  //the MetaId will be generated from concatenating the ids of any parent entities with '_' in between.
  override def toForm() = Empty
  //Appears when rendering the form or the visualization
  override def name: String = "Metaid"

  //override def toXHtml: NodeSeq = Text(this.value + "xxxxxxx")
}

class Id[T <: SBaseRecord[T]](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayHTMLWithLabelInOneLine[String, T]{

  def validateId(id:String):List[FieldError] =
    SBMLLooseValidator.checkMandatoryId(id)
      .map(FieldError( this, _ ))

  override def helpAsHtml:Box[NodeSeq] = Full(<a href="http://stackoverflow.com/questions/1252749/how-to-show-images-links-on-div-hover" target="_blank">(?)</a>)

  override def validations:List[ValidationFunction] =
    validateId _ ::
    valMinLen(1,"Field is empty") _ ::
    super.validations

  //Appears when rendering the form or the visualization
  override def name: String = "Id"
  //override def toXHtml: NodeSeq = Text(this.value)
}

/**
 *
 */
class Name[T <: SBaseRecord[T]](own:T, maxLength: Int) extends OptionalStringField[T](own, maxLength)
with DisplayHTMLWithLabelInOneLine[String, T]{

  def validateName(name:Option[String]):List[FieldError] =
    if(!name.isEmpty){
      SBMLLooseValidator.checkOptionalName(name.get)
        .map(FieldError( this, _ ))
    } else List[FieldError]()


  override def validations:List[ValidationFunction] =
    validateName _ ::
      super.validations

  //Appears when rendering the form or the visualization
  override def name = "Name"

  val msgName: String = S.attr("id_msgs") openOr "messages"
  //override def toXHtml: NodeSeq = Text(this.value)
}

class Notes[T <: SBaseRecord[T]](own:T, size:Int) extends OptionalTextareaField[T](own, size)
with GetSetOwnerField[String, T]{

  override def toForm() = Full(
    <div>
      <head>
          <link type="text/css" rel="stylesheet" href="/classpath/tree/jquery.treeview.css" />
        <script type="text/javascript" src="/classpath/tree/jquery.treeview.js"></script>
        {Script(
        JsRaw("""
        jQuery(document).ready(function() {jQuery('#'+"model_tree").treeview({"animated": 150});});
          """))
        }
      </head>
      <ul class="treeview-gray" id="model_tree">
        <head>
          <script type="text/javascript" src="/classpath/js/ckeditor/ckeditor.js"></script>
        </head>

        <li> <span><h3>Expand/Collapse</h3></span>
            <br />
          <ul>
            <li><span>
              {
              SHtml.textarea(
                valueBox.openOr(""),
                vv => setBox(
                  vv.trim match {
                    case "" => Empty
                    case x => Full(x)
                  }
                ),
                "id" -> "random", "class" -> "ckeditor", "maxlength" -> "20000")
              }
              {Script(JsRaw(
              """
          CKEDITOR.replace( "editor1",
					{
						/*
						 * Style sheet for the contents
						 */
						contentsCss : '/classpath/js/ckeditor/_samples/assets/output_xhtml.css',

						/*
						 * Core styles.
						 */
						coreStyles_bold	: { element : 'span', attributes : {'class': 'Bold'} },
						coreStyles_italic	: { element : 'span', attributes : {'class': 'Italic'}},
						coreStyles_underline	: { element : 'span', attributes : {'class': 'Underline'}},
						coreStyles_strike	: { element : 'span', attributes : {'class': 'StrikeThrough'}, overrides : 'strike' },

						coreStyles_subscript : { element : 'span', attributes : {'class': 'Subscript'}, overrides : 'sub' },
						coreStyles_superscript : { element : 'span', attributes : {'class': 'Superscript'}, overrides : 'sup' },

						/*
						 * Font face
						 */
						// List of fonts available in the toolbar combo. Each font definition is
						// separated by a semi-colon (;). We are using class names here, so each font
						// is defined by {Combo Label}/{Class Name}.
						font_names : 'Comic Sans MS/FontComic;Courier New/FontCourier;Times New Roman/FontTimes',

						// Define the way font elements will be applied to the document. The "span"
						// element will be used. When a font is selected, the font name defined in the
						// above list is passed to this definition with the name "Font", being it
						// injected in the "class" attribute.
						// We must also instruct the editor to replace span elements that are used to
						// set the font (Overrides).
						font_style :
						{
								element		: 'span',
								attributes		: { 'class' : '#(family)' }
						},

						/*
						 * Font sizes.
						 */
						fontSize_sizes : 'Smaller/FontSmaller;Larger/FontLarger;8pt/FontSmall;14pt/FontBig;Double Size/FontDouble',
						fontSize_style :
							{
								element		: 'span',
								attributes	: { 'class' : '#(size)' }
							} ,

						/*
						 * Font colors.
						 */
						colorButton_enableMore : false,

						colorButton_colors : 'FontColor1/FF9900,FontColor2/0066CC,FontColor3/F00',
						colorButton_foreStyle :
							{
								element : 'span',
								attributes : { 'class' : '#(color)' }
							},

						colorButton_backStyle :
							{
								element : 'span',
								attributes : { 'class' : '#(color)BG' }
							},

						/*
						 * Indentation.
						 */
						indentClasses : ['Indent1', 'Indent2', 'Indent3'],

						/*
						 * Paragraph justification.
						 */
						justifyClasses : [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull' ],

						/*
						 * Styles combo.
						 */
						stylesSet :
								[
									{ name : 'Strong Emphasis', element : 'strong' },
									{ name : 'Emphasis', element : 'em' },

									{ name : 'Computer Code', element : 'code' },
									{ name : 'Keyboard Phrase', element : 'kbd' },
									{ name : 'Sample Text', element : 'samp' },
									{ name : 'Variable', element : 'var' },

									{ name : 'Deleted Text', element : 'del' },
									{ name : 'Inserted Text', element : 'ins' },

									{ name : 'Cited Work', element : 'cite' },
									{ name : 'Inline Quotation', element : 'q' }
								]

					});
              	"""
              ) //end of JsRaw
              )}
            </span><br /></li>
          </ul>
        </li>
      </ul>
    </div>
  )
  //Appears when rendering the form or the visualization
  override def name: String = "Description"
  override def toXHtml: NodeSeq = {
    trace("Calling Notes.toXHtml")
      //TODO: this method is almost equal to with DisplayHTMLWithLabelInOneLine[String, T]. Refactor to use that instead if possible
      <div id={uniqueFieldId + "_holder"}>
        <span for={ uniqueFieldId.openTheBox }>
          <span class="sbml_field_label">{displayHtml}</span>
          <span class="sbml_field_content">
            {this.valueBox match{
              case Empty => Text("-- no description available -- ")
              case Full(content) =>{
                try{
                  XML.loadString( content )
                } catch{
                  //FIXME replace this hack by something more general
                  case _ => XML.loadString( "<root>"+content+"</root>" ).child
                }
              }
              case Failure(msg, exception, chain) => S.error(msg)
              case ParamFailure(msg, exception, chain, param) => S.error(msg + ". This occured in " + param)
            }
            }</span>
        </span>
          <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
      </div>
  }
}

class Message[T <: SBaseRecord[T]](own:T, size:Int) extends OptionalTextareaField[T](own, size)
with GetSetOwnerField[String, T]{

  override def toForm() = Full(
    <div>
      <head>
          <link type="text/css" rel="stylesheet" href="/classpath/tree/jquery.treeview.css" />
        <script type="text/javascript" src="/classpath/tree/jquery.treeview.js"></script>
        {Script(
        JsRaw("""
        jQuery(document).ready(function() {jQuery('#'+"model_tree").treeview({"animated": 150});});
          """))
        }
      </head>
      <ul class="treeview-gray" id="model_tree">
        <head>
          <script type="text/javascript" src="/classpath/js/ckeditor/ckeditor.js"></script>
        </head>

        <li><span><h3>Message:</h3></span>
            <br />
          <ul>
            <li><span>
              {
              SHtml.textarea(
                valueBox.openOr(""),
                vv => setBox(
                  vv.trim match {
                    case "" => Empty
                    case x => Full(x)
                  }
                ),
                "class" -> "ckeditor", "maxlength" -> "20000")
              }
            </span><br /></li>
          </ul>
        </li>
      </ul>
    </div>
  )
  //Appears when rendering the form or the visualization
  override def name: String = "Message"
  override def toXHtml: NodeSeq = {
    trace("Calling Message.toXHtml")
    //TODO: this method is almost equal to with DisplayHTMLWithLabelInOneLine[String, T]. Refactor to use that instead if possible
    <div id={uniqueFieldId + "_holder"}>
      <span for={ uniqueFieldId.openTheBox }>
        <span class="sbml_field_label">{displayHtml}</span>
        <span class="sbml_field_content">
          {this.valueBox match{
          case Empty => Text("-- no description available -- ")
          case Full(content) =>
            try {
              XML.loadString(content)
            } catch {
              case _ =>
                //FIXME replace this hack by something more general
                try {
                  XML.loadString("<root>" + content + "</root>").child
                } catch {
                  case _ => throw new BadFormatException("Strange error for field message!!")
                }
            }
          case Failure(msg, exception, chain) => S.error(msg)
          case ParamFailure(msg, exception, chain, param) => S.error(msg + ". This occured in " + param)
        }
          }</span>
      </span>
        <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
    </div>
  }
}


class SpatialDimensions[T <: SBaseRecord[T]](own:T) extends EnumField(own, ValidSpatialDimensions)
with DisplayHTMLWithLabelInOneLine[ValidSpatialDimensions, T] with LoggerWrapper{
  import pt.cnbc.wikimodels.dataModel.Compartment._

  //Appears when rendering the form or the visualization
  override def name: String = "Spatial dimensions"
  //override def toXHtml: NodeSeq = Text(this.value)
}


/**
 * Compartment constant
 */
class CConstant[OwnerType <: SBaseRecord[OwnerType]](rec:OwnerType) extends BooleanField[OwnerType](rec)
with DisplayHTMLWithLabelInOneLine[Boolean, OwnerType]
with LoggerWrapper{
  override def defaultValue = Compartment.defaultConstant

  override def name: String = "Constant"
}

/**
 * Compartment.outside
 */
class COutside(own:CompartmentRecord) extends OptionalStringField(own, 100)
 with DisplayHTMLWithLabelInOneLine[String, CompartmentRecord]{

  override def validate:List[FieldError] = {
    super.validate
  }

  //Appears when rendering the form or the visualization
  override def name: String = "Outside"
  //override def toXHtml: NodeSeq = Text(this.value)

  override def toForm:Box[NodeSeq] = {
    trace("Calling COutside.toForm")
    for (id <- uniqueFieldId; control <- super.toForm)
    yield
      <span id={id + "_holder"}>
        <!-- <label for={ id }> <span class="sbml_field_label">{ displayHtml }</span></label>-->
        {
          val theParent = this.own.parent.openTheBox

          theParent match {
            case model:SBMLModelRecord => {
              val op =
                theParent.asInstanceOf[SBMLModelRecord].listOfCompartmentsRec
                .filter(_.metaIdO.get != this.own.metaIdO.get)
                .filter(_.spatialDimensions0.is != 0)
            val opWithId = op.map(i => (Full(i), i.idO.is):(Box[CompartmentRecord], String) )
            val options = (List((Empty , "no compartment")) ::: opWithId.toList).toSeq
            SHtml.selectObj(options,
              Full(Box.option2Box(
                theParent.asInstanceOf[SBMLModelRecord].listOfCompartmentsRec.filter( _.idO.is == this.own.outsideO.is ).headOption)),
              (choice:Box[CompartmentRecord]) =>
                choice match {
                  case Empty => null
                  case Full(c) => c.idO.is
                  case Failure(msg, exception, chain) => S.error(msg)
                  case ParamFailure(msg, exception, chain, param) => S.error(msg + ". This occured in " + param)
                },
              ("xxx" -> "yyy"))
            }
            case _ => S.error("Only Models as parents of Compartments are implemented!")
          }

        }
        <lift:msg id={id}  errorClass="lift_error"/>
      </span>
  }
}


/**
 * Species.compartment
 */
class SCompartment[ T <: SpeciesRecord](own:SpeciesRecord) extends StringField(own, 100)
with DisplayHTMLWithLabelInOneLine[String, SpeciesRecord]{

  override def validate:List[FieldError] = {
    super.validate
  }

  //Appears when rendering the form or the visualization
  override def name: String = "Compartment"
  //override def toXHtml: NodeSeq = Text(this.value)

  override def toForm:Box[NodeSeq] = {
    trace("Calling SCompartment.toForm")
    for (id <- uniqueFieldId; control <- super.toForm)
    yield
      <span id={id + "_holder"}>
        <!-- <label for={ id }> <span class="sbml_field_label">{ displayHtml }</span></label> -->
        {
        val theParent = this.own.parent.openTheBox

        theParent match {
          case model:SBMLModel => {
            val op =
              theParent.asInstanceOf[SBMLModelRecord].listOfCompartmentsRec
            val opWithId = op.map(i => (i, i.idO.is):(CompartmentRecord, String) )
            // FIXME - since this is a wiki, this might be made optional nonetheless - CHECK IT
            // val options = (List((Empty , "no compartment")) ::: opWithId.toList).toSeq
            debug("Species.compartment value is {} before creating select box", this.own.compartmentO.is)
            SHtml.selectObj(
              opWithId,
              Box.option2Box(
                op.filter( _.idO.is == this.own.compartmentO.is ).headOption),
              (choice:CompartmentRecord) =>
                {
                  debug("Defining default choice of select box as {}", choice.idO.is)
                  this.own.compartmentO.set( choice.idO.is)
                },
                ("xxx" -> "yyy"))
          }
          case _ => {
            error("Only Models as parents of Species are implemented!")
            S.error("Only Models as parents of Species are implemented!")
          }
        }

        }
        <lift:msg id={id}  errorClass="lift_error"/>
      </span>
  }
}

/**
 * Species boundaryCondition
 */
class BoundaryCondition[OwnerType <: SBaseRecord[OwnerType]](rec:OwnerType) extends BooleanField[OwnerType](rec)
with DisplayHTMLWithLabelInOneLine[Boolean, OwnerType]
with LoggerWrapper{

  override def defaultValue = Species.defaultBoundaryCondition

  override def name: String = "BoundaryCondition"
}

/**
 * Species constant
 */
class SConstant(rec:SpeciesRecord) extends BooleanField(rec)
with DisplayHTMLWithLabelInOneLine[Boolean, SpeciesRecord]
with LoggerWrapper{

  override def defaultValue = Species.defaultConstant

  override def name: String = "Constant"
}

/**
 * Parameter constant
 */
class PConstant(rec:ParameterRecord) extends BooleanField(rec)
with DisplayHTMLWithLabelInOneLine[Boolean, ParameterRecord]
with LoggerWrapper{
  override def defaultValue = Parameter.defaultConstant

  override def name: String = "Constant"
}

trait UIOptionalDoubleField[OwnerType <: SBaseRecord[OwnerType]] extends Field[Double,OwnerType] with OptionalTypedField[Double] with DoubleTypedField
//  with DisplayFormWithLabelInOneLine[Double, OwnerType]
with DisplayHTMLWithLabelInOneLine[Double,OwnerType]
  with LoggerWrapper {

  //Appears when rendering the form or the visualization
}

/**
 * Compartment size
 */
class Size(rec:CompartmentRecord)
  extends OptionalDoubleField(rec){
  override def name: String = "Size"
}

/**
 * Species initialAmount
 */
class InitialAmount(rec:SpeciesRecord)
  extends OptionalDoubleField(rec) with DisplayHTMLWithLabelInOneLine[Double,SpeciesRecord]
  with LoggerWrapper with AuxValidators  {
  override def name: String = "InitialAmount"

  override def validations:List[ValidationFunction] =
      super.validations
}

/**
 * Species initialConcentration
 */
class InitialConcentration[OwnerType <: SBaseRecord[OwnerType]](rec:OwnerType)
  extends OptionalDoubleField[OwnerType](rec)
  with DisplayHTMLWithLabelInOneLine[Double,OwnerType]
  with LoggerWrapper with AuxValidators{
  override def name: String = "InitialConcentration"

  def validateInitialConcentration(ic:String):List[FieldError] =
    checkOptionalDoubleNumber(ic)
      .map(FieldError( this, _ ))

  override def validations:List[ValidationFunction] =
    super.validations
}

/**
 * Parameter value
 */
class Value[OwnerType <: SBaseRecord[OwnerType]](rec:OwnerType)
  extends OptionalDoubleField[OwnerType](rec)
  with DisplayHTMLWithLabelInOneLine[Double,OwnerType]
  with LoggerWrapper {
  override def name: String = "Value"
}


class Math[OwnerType <: SBaseRecord[OwnerType]](rec:OwnerType) extends TextareaField(rec,2000)
with GetSetOwnerField[String,OwnerType]{

  override def validations:List[ValidationFunction] =
    super.validations

  override def setFilter:List[ValueType => ValueType] =
    super.setFilter

  object Default{
    val mathmlFormula = <math xmlns="http://www.w3.org/1998/Math/MathML"/>
    val editMessHtml = <p id="parse_success" >Please insert new formula</p>
    val errorMess = "No error"
    val errorHtml = <p id="parse_success" >To start working please edit the AsciiMathML formula below</p>
    val asciiFormula = "2*x"
  }

  var asciiFormula: String  = Default.asciiFormula

  var mathmlFormula:Elem = mathmlFormula
  var mathmlFormulatosave:Elem = mathmlFormula



  override def name: String = "Math"

  //Appears when rendering the form or the visualization

  override def toXHtml: NodeSeq = {
    trace("Calling Math.toXHtml")
    //TODO: this method is almost equal to with DisplayHTMLWithLabelInOneLine[String, T]. Refactor to use that instead if possible
    <div id={uniqueFieldId + "_holder"}>
      <span for={ uniqueFieldId.openTheBox }>
        <span class="sbml_field_label">{displayHtml}</span>
        <span class="sbml_field_content">
          {this.valueBox match{
          case Empty => Text("-- no description available -- ")
          case Full(content) =>{
            try{
              XML.loadString( content )
            } catch{
              //FIXME replace this hack by something more general
              case _ => XML.loadString( "<root>"+content+"</root>" ).child
            }
          }
          case Failure(msg, exception, chain) => S.error(msg)
          case ParamFailure(msg, exception, chain, param) => S.error(msg + ". This occured in " + param)
        }
          }</span>
      </span>
        <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
    </div>
  }
}

class SRSpecies[T <: SBaseRecord[T]](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayHTMLWithLabelInOneLine[String, T]{
  override def name: String = "Math"
}

// Aux Record traits

trait DisplayHTMLWithLabelInOneLine[ThisType, OwnerType <: Record[OwnerType]] extends GetSetOwnerField[ThisType, OwnerType] {
  override def toXHtml: NodeSeq = {
    trace("Calling DisplayHTMLWithLabelInOneLine.toXHtml")
    //TODO: BIG ERRORS HERE... JUST CHECK http://localhost:9999/model/f
    <div id={uniqueFieldId + "_holder"}>
      <span for={ uniqueFieldId.openTheBox }>
        <span class="sbml_field_label">{displayHtml}</span>
        <span class="sbml_field_content">  {this.valueBox openOr "-- not defined --" }</span>
      </span>
        <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
    </div>
  }
}

/**
 * Convert the field to a String... usually of the form "displayName=value"
 */
trait GetSetOwnerField[ThisType, OwnerType <: Record[OwnerType]] extends OwnedField[OwnerType] with TypedField[ThisType]
with LoggerWrapper{
  //TODO delete this ASAP
  /**
   * defines if a default value should be attributed to this field
   */
  needsDefault = false
}

trait AuxValidators {
  protected def checkDoubleNumber(s: String):List[String] =
    try{
      java.lang.Double.parseDouble(s); Nil
    } catch {
      case e:NumberFormatException => List("'"+s+"' does not represent a valid floating point number.")
      case e => exceptionHandling(e)
    }

  protected def checkOptionalDoubleNumber(num:String):List[String] =
    if (num == null)
      Nil
    else checkDoubleNumber(num)

}

