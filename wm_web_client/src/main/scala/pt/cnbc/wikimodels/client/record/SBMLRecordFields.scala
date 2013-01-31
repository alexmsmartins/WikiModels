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

//Javascript handling imports
import net.liftweb.http.js.{JsExp, JE, JsCmd, JsCmds}
import JsCmds._ // For implicifts
import net.liftweb.http.js.JE.{JsVar, Call, JsRaw, Str}

import scala.util.parsing.combinator.Parsers

class MetaId[T <: SBaseRecord[T]](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling MetaId.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.metaid = x
      case _ => owner.metaid = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling MetaId.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.metaid != null) {
      debug("theData with metaid = "+ owner.metaid + " is being copied to the record Field.")
      _data = Full(owner.metaid)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) owner.metaid = defaultValue
        }
        case Full(x) => owner.metaid = x
        case _ => Nil
      }
    }
    trace("MetaId.theData returns " + _data)
    _data
  }

  //the MetaId will be generated from concatenating the ids of any parent entities with '_' in between.
  override def toForm() = Empty
  //Appears when rendering the form or the visualization
  override def name: String = "Metaid"

  //override def toXHtml: NodeSeq = Text(this.value + "xxxxxxx")
}

class Id[T <: SBaseRecord[T]{var id:String}](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayHTMLWithLabelInOneLine[String, T]{

  var _data:Box[MyType] = Empty

  def validateId(id:String):List[FieldError] =
    SBMLLooseValidator.checkMandatoryId(id)
      .map(FieldError( this, _ ))

  override def validations:List[ValidationFunction] =
    validateId _ ::
    valMinLen(1,"Field is empty") _ ::
    super.validations

  override def theData_=(in:Box[MyType]) {
    trace("Calling Id.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.id = x
      case _ => owner.id = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Id.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.id != null) {
      debug("theData with aid = "+ owner.id + " is being copied to the record Field.")
      _data = Full(owner.id)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) owner.id = defaultValue
        }
        case Full(x) => owner.id = x
        case _ => Nil
      }
    }
    trace("Id.theData returns " + _data)
    _data
  }

  //Appears when rendering the form or the visualization
  override def name: String = "Id"
  //override def toXHtml: NodeSeq = Text(this.value)
}

/**
 *
 */
class Name[T <: SBaseRecord[T]{var name:String}](own:T, maxLength: Int) extends OptionalStringField[T](own, maxLength)
with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Name.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.name = x
      case _ => owner.name = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Name.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.name != null) {
      debug("theData with name = "+ owner.name + " is being copied to the record Field.")
      _data = Full(owner.name)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) owner.name = defaultValue
        }
        case Full(x) => owner.name = x
        case _ => Nil
      }
    }
    trace("Name.theData returns " + _data)
    _data
  }

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

class Notes[T <: SBaseRecord[T]{var notes:String}](own:T, size:Int) extends OptionalTextareaField[T](own, size)
with GetSetOwnerField[String, T]{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Notes.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      //TODO put the entities conversion in their own class
      case Full(x) => owner.notes = x
        .replace("&nbsp;", " &#160;")
        .replace("&igrave;", "&#236;")
        .replace("&egrave;", "&#232;")
        .replace("&Egrave;", "&#200;")
      case _ => owner.notes = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Notes.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.notes != null) {
      debug("theData with notes = "+ owner.notes + " is being copied to the record Field.")
      _data = Full(owner.notes)
    } else {
      _data match {
        case Empty => {
          _data = Empty
          if (! this.optional_?) owner.notes = null
        }
        case Full(x) => owner.notes = x
        case _ => Nil
      }
    }
    trace("Notes.theData returns " + _data)
    _data
  }

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
              }}
            }</span>
        </span>
          <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
      </div>
  }
}

class Message[T <: SBaseRecord[T]{var message:String}](own:T, size:Int) extends OptionalTextareaField[T](own, size)
with GetSetOwnerField[String, T]{
  //TODO REFACTOR Record field Message to share more code with Notes field
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Message.theData_=" + in)
    _data = in
    _data match{
      //if a valid value ieditor1s set then update the owner class
      //TODO put the entities conversion in their own class
      case Full(x) => owner.message = x
        .replace("&nbsp;", " &#160;")
        .replace("&igrave;", "&#236;")
        .replace("&egrave;", "&#232;")
        .replace("&Egrave;", "&#200;")
      case _ => owner.message = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Message.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.message != null) {
      debug("theData with message = "+ owner.message + " is being copied to the record Field.")
      _data = Full(owner.message)
    } else {
      _data match {
        case Empty => {
          _data = Empty
          if (! this.optional_?) owner.message = null
        }
        case Full(x) => owner.message = x
        case _ => Nil
      }
    }
    trace("Message.theData returns " + _data)
    _data
  }

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
          case Full(content) =>{
            try{
              XML.loadString( content )
            } catch{
              //FIXME replace this hack by something more general
              case _ => XML.loadString( "<root>"+content+"</root>" ).child
            }
          }}
          }</span>
      </span>
        <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
    </div>
  }
}


class SpatialDimensions[T <: SBaseRecord[T]{var spatialDimensions:Int}](own:T) extends EnumField(own, ValidSpatialDimensions)
with DisplayHTMLWithLabelInOneLine[ValidSpatialDimensions, T] with LoggerWrapper{
  import pt.cnbc.wikimodels.dataModel.Compartment._
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling SpatialDimensions.theData_=" + in)
    _data = in
    in match{
      //if a valid value is set then update the owner class
      case Full(y) =>{
        owner.spatialDimensions = y.id
      }
      case Empty => {
        owner.spatialDimensions = Compartment.defaultSpatialDimensions.id
      }
      case _ => owner.spatialDimensions = 10000 //1000 is an unressonbable value that will stand out
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling SpatialDimensions.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    debug("theData with spatialDimensions = "+ owner.spatialDimensions + " is being copied to the record Field.")
    _data = Box.legacyNullTest(ValidSpatialDimensions(own.spatialDimensions))
      _data match {
        case Empty => {

          if (! this.optional_?){
            _data = Full(defaultSpatialDimensions)
            owner.spatialDimensions = Compartment.defaultSpatialDimensions.id
          }
        }
        case Full(x) => {
          owner.spatialDimensions = x.id
        }
        case _ => Nil
      }
    trace("SpatialDimensions.theData returns " + _data)
    _data
  }


  //Appears when rendering the form or the visualization
  override def name: String = "Spatial dimensions"
  //override def toXHtml: NodeSeq = Text(this.value)
}

trait UIMandatoryBooleanField[OwnerType <: SBaseRecord[OwnerType]]  extends Field[Boolean, OwnerType] with MandatoryTypedField[Boolean] with BooleanTypedField
with DisplayHTMLWithLabelInOneLine[Boolean, OwnerType]
with LoggerWrapper{
  var _data:Box[MyType] = Empty

  def ownerField_=(value:Boolean)
  def ownerField:Boolean
  def defaultFieldValue:Boolean

  override def theData_=(in:Box[MyType]) {
    trace("Calling Constant.theData_=" + in)
    _data = in
    in match{
      //if a valid value is set then update the owner class
      case Full(y) =>{
        ownerField = y
      }
      case Empty => {
        ownerField = defaultFieldValue
      }
      case _ =>{
        ownerField = true //lets give it a default even in case of error
      }
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Constant.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    debug("theData with " + name.toLowerCase +" "+ ownerField + " is being copied to the record Field.")
    _data match {
      case Empty => {
        if (! this.optional_?){
          _data = Full(defaultFieldValue)
          ownerField = defaultFieldValue
        }
      }
      case Full(x) => {
        ownerField = x
      }
      case _ => Nil
    }
    trace("Constant.theData returns " + _data)
    _data
  }

  //Appears when rendering the form or the visualization
  override def name: String = "Constant"
  //override def toXHtml: NodeSeq = Text(this.value)
}

/**
 * Compartment constant
 */
class CConstant[OwnerType <: SBaseRecord[OwnerType]{var constant:Boolean}](rec:OwnerType) extends UIMandatoryBooleanField[OwnerType]{
  def owner = rec
  override def ownerField_=(value:Boolean) = owner.constant = value
  override def ownerField:Boolean = owner.constant
  override def defaultFieldValue = Compartment.defaultConstant

  def defaultValue = Compartment.defaultConstant

  override def name: String = "Constant"
}

/**
 * Compartment.outside
 */
class COutside[ T <: SBaseRecord[T]{var outside:String}](own:T) extends OptionalStringField[T](own, 100)
 with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def validate:List[FieldError] = {
    super.validate
  }

  override def theData_=(in:Box[MyType]) {
    trace("Calling Outside.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.outside = x
      case _ => owner.outside = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Outside.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(own.outside != null) {
      debug("theData with outside = "+ own.outside + " is being copied to the record Field.")
      _data = Full(own.outside)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) own.outside = defaultValue
        }
        case Full(x) => own.outside = x
        case _ => Nil
      }
    }
    trace("Outside.theData returns " + _data)
    _data
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
            case model:SBMLModel => {
              val op =
                theParent.asInstanceOf[SBMLModelRecord].listOfCompartmentsRec
                .filter(_.metaid != this.own.metaid)
                .filter(_.spatialDimensions != 0)
            val opWithId = op.map(i => (Full(i), i.id):(Box[CompartmentRecord], String) )
            val options = (List((Empty , "no compartment")) ::: opWithId.toList).toSeq
            SHtml.selectObj(options,
              Full(Box.option2Box(
                theParent.asInstanceOf[SBMLModelRecord].listOfCompartmentsRec.filter( _.id == this.own.outside ).headOption)),
              (choice:Box[CompartmentRecord]) =>
                choice match {
                  case Empty => this.own.outside = null
                  case Full(c) => this.own.outside = c.id
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
class SCompartment[ T <: SBaseRecord[T]{var compartment:String}](own:T) extends StringField[T](own, 100)
with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def validate:List[FieldError] = {
    super.validate
  }

  override def theData_=(in:Box[MyType]) {
    trace("Calling SCompartment.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.compartment = x
      case _ => {
        S.error("No compartment was defined for this species ");
        owner.compartment = null //just to make sure the owner does not have valid values when errors occur
      }
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling SComaprtment.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(own.compartment != null) {
      debug("theData with compartment = "+ own.compartment + " is being copied to the record Field.")
      _data = Full(own.compartment)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) own.compartment = defaultValue
        }
        case Full(x) => own.compartment = x
        case _ => Nil
      }
    }
    trace("SCompartment.theData returns " + _data)
    _data
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
            val opWithId = op.map(i => (i, i.id):(CompartmentRecord, String) )
            // FIXME - since this is a wiki, this might be made optional nonetheless - CHECK IT
            // val options = (List((Empty , "no compartment")) ::: opWithId.toList).toSeq
            debug("Species.compartment value is {} before creating select box", this.own.compartment)
            SHtml.selectObj(
              opWithId,
              Box.option2Box(
                op.filter( _.id == this.own.compartment ).headOption),
              (choice:CompartmentRecord) =>
                {
                  debug("Defining default choice of select box as {}", choice.id)
                  this.own.compartment = choice.asInstanceOf[CompartmentRecord].id
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
class BoundaryCondition[OwnerType <: SBaseRecord[OwnerType]{var constant:Boolean}](rec:OwnerType) extends UIMandatoryBooleanField[OwnerType]{
  def owner = rec
  override def ownerField_=(value:Boolean) = owner.constant = value
  override def ownerField:Boolean = owner.constant
  override def defaultFieldValue = Species.defaultBoundaryCondition

  def defaultValue = Species.defaultBoundaryCondition

  override def name: String = "BoundaryCondition"
}

/**
 * Species constant
 */
class SConstant[OwnerType <: SBaseRecord[OwnerType]{var constant:Boolean}](rec:OwnerType) extends UIMandatoryBooleanField[OwnerType]{
  def owner = rec
  override def ownerField_=(value:Boolean) = owner.constant = value
  override def ownerField:Boolean = owner.constant
  override def defaultFieldValue = Species.defaultConstant

  def defaultValue = Species.defaultConstant

  override def name: String = "Constant"
}

/**
 * Parameter constant
 */
class PConstant[OwnerType <: SBaseRecord[OwnerType]{var constant:Boolean}](rec:OwnerType) extends UIMandatoryBooleanField[OwnerType]{
  def owner = rec
  override def ownerField_=(value:Boolean) = owner.constant = value
  override def ownerField:Boolean = owner.constant
  override def defaultFieldValue = Parameter.defaultConstant

  def defaultValue = Parameter.defaultConstant

  override def name: String = "Constant"
}

trait UIOptionalDoubleField[OwnerType <: SBaseRecord[OwnerType]] extends Field[Double,OwnerType] with OptionalTypedField[Double] with DoubleTypedField
//  with DisplayFormWithLabelInOneLine[Double, OwnerType]
with DisplayHTMLWithLabelInOneLine[Double,OwnerType]
  with LoggerWrapper {


  // * There is a strong reason to use both java.lang.Double and scala.Double here
  // * Do not change this without knowing what to do with the client and server code when there is no size value
  var _data:Box[MyType] = Empty

  def ownerField_=(value:java.lang.Double)
  def ownerField:java.lang.Double

  override def theData_=(in:Box[MyType]) {
    trace("Calling UIOptionalDoubleField.theData_=" + in)
    _data = in
    in match{
      //if a valid value is set then update the owner class
      case Full(y) =>{
        ownerField = y.asInstanceOf[java.lang.Double]
      }
      case Empty => {
        ownerField = null
      }
      case _ =>{
        ownerField = null //lets give it a default even in case of error
      }
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling UIOptionalDoubleField.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(ownerField != null){
      _data = Full(ownerField)
    } else {
      debug("theData with " + name.toLowerCase +" "+ ownerField + " is being copied to the record Field.")
      _data match {
        case Empty => {
          if ( this.optional_?){
            _data = Empty
            ownerField = null
          }
        }
        case Full(x) => {
          ownerField = x.asInstanceOf[java.lang.Double]
        }
        case _ => Nil
      }
    }
    trace("Constant.theData returns " + _data)
    _data
  }

//  override def setFromString(s: String): Box[Double] = s match {
//    case "" if optional_? => setBox(Empty)
//    case _ =>setBox(tryo(java.lang.Double.parseDouble(s)))
//  }


  //Appears when rendering the form or the visualization
}

/**
 * Compartment size
 */
class Size[OwnerType <: SBaseRecord[OwnerType]{var size:java.lang.Double}](rec:OwnerType)
  extends UIOptionalDoubleField[OwnerType]  {
  def owner = rec
  override def ownerField_=(value:java.lang.Double) = owner.size = value
  override def ownerField:java.lang.Double = owner.size
  override def name: String = "Size"
}

/**
 * Species initialAmount
 */
class InitialAmount[OwnerType <: SBaseRecord[OwnerType]{var initialAmount:java.lang.Double}](rec:OwnerType)
  extends UIOptionalDoubleField[OwnerType] with AuxValidators  {
  def owner = rec
  override def ownerField_=(value:java.lang.Double) = owner.initialAmount = value
  override def ownerField:java.lang.Double = owner.initialAmount
  override def name: String = "InitialAmount"

  override def validations:List[ValidationFunction] =
      super.validations
}

/**
 * Species initialConcentration
 */
class InitialConcentration[OwnerType <: SBaseRecord[OwnerType]{var initialConcentration:java.lang.Double}](rec:OwnerType)
  extends UIOptionalDoubleField[OwnerType] with AuxValidators{
  def owner = rec
  override def ownerField_=(value:java.lang.Double) = owner.initialConcentration = value
  override def ownerField:java.lang.Double = owner.initialConcentration
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
class Value[OwnerType <: SBaseRecord[OwnerType]{var value:java.lang.Double}](rec:OwnerType)
  extends UIOptionalDoubleField[OwnerType]  {
  def owner = rec
  override def ownerField_=(value:java.lang.Double) = owner.value = value
  override def ownerField:java.lang.Double = owner.value
  override def name: String = "Value"
}


class Math[OwnerType <: SBaseRecord[OwnerType]{var math:String}](rec:OwnerType) extends StringField(rec,2000)
with GetSetOwnerField[String,OwnerType]{

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
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Math.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      //TODO put the entities conversion in their own class
      case Full(x) => owner.math = x
      case _ => owner.math = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Math.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.math != null) {
      debug("theData with math = "+ owner.math + " is being copied to the record Field.")
      _data = Full(owner.math )
    } else {
      _data match {
        case Empty => {
          _data = Empty
          if (! this.optional_?) owner.math = null
        }
        case Full(x) => owner.math = x
        case _ => Nil
      }
    }
    trace("Math.theData returns " + _data)
    _data
  }


  override def toForm() = {
    val textAreaId = "math_textarea"
    val ajaxCheckTextArea = () => {
      JsCrVar("textAreaId",textAreaId)&
        Call("textAreaContentBy", JsVar("textAreaId"))
      //TODO get formula from textarea

      // validate the content to sse if its valid AsciiMathML

      //if valid generate athML from formula
      //         include MathML into the page
      //         render success message
      //if not valid render success message
    }


    Full(
      <div id="some-div">
        <div>
          <!-- FIXME recheck the possibility of adding this if there is a way to make it work on webkit
              FIXME this browser conversion is much faster than the one done on the server side -->
          <!--    <head>
                  <script id="xslttransform" src="/js/xsltTransformer.js" type="text/javascript">
              </script>
              </head>-->
          <script type="text/x-mathjax-config">
            /* <![CDATA[ */
              $.log("MathJax is being configured.");
              MathJax.Hub.Config({
                config: ["MMLorHTML.js"],
                jax: ["input/TeX","input/MathML","output/HTML-CSS","output/NativeMML"],
                extensions: ["tex2jax.js","mml2jax.js","MathMenu.js","MathZoom.js"],
                TeX: {
                  extensions: ["AMSmath.js","AMSsymbols.js","noErrors.js","noUndefined.js"]
                },
                NativeMML: { showMathMenuMSIE: false },
                menuSettings: { zoom: "Double-Click" },
                errorSettings: { message: ["[Math Error]"] }
              });
            /* ]]> */
          </script>
          <script type="text/javascript"
                  src="http://cdn.mathjax.org/mathjax/latest/MathJax.js">
            /* <![CDATA[ */
              $(document).ready(function() {
              displayResult();
              //TODO replace this function call by Sarissa - http://dev.abiss.gr/sarissa/
              $.log("MathJax is executing");
              });
            /* ]]> */
          </script>

          <link rel="stylesheet" type="text/css" href="/css/mathml_editor.css"/>

          <div class="lift:Msg?id=parsing_error;errorClass=error"></div>
          <a href="http://www1.chapman.edu/~jipsen/asciimath.html">To get help in ASCIIMathML syntax click here.</a>

          <div>
            <div class="mathml_output" id="formula"  >{mathmlFormula}</div>
            {SHtml.textarea(asciiFormula, {asciiFormula = _}, "class" -> "asciimath_input", "id" -> textAreaId)}
            <input type="button" id="valid_funct_def"> Check the formula</input>
          </div>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
        </div>
        <br/>
      </div>
    )



  }

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
          }}
          }</span>
      </span>
        <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
    </div>
  }
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

  /**
   * accessor for the data in this Field record
   * The implementation should:
   *  - attribute in to data
   *  - attribute:
   *   - to the owner field if Full
   *   - attribute a default value if Empty
   *   - a "strange" value if Failure (debugging becomes easier)
   */
  private[record] def theData_=(in:Box[MyType]):Unit
  private[record] def theData:Box[MyType]

  /**
   * defines if a default value should be attributed to this field
   */
  needsDefault = false

  override def setBox(in: Box[MyType]): Box[MyType] = synchronized {
    trace("Calling GetSetOnwerField.setBox(" + in + ")")
    needsDefault = false
    theData = in match {
      case _ if !canWrite_?      => Failure(noValueErrorMessage)
      case Full(_)               => set_!(in)
      case _ if optional_?       => set_!(in)
      case (f: Failure)          => set_!(f) // preserve failures set in
      case _                     => Failure(notOptionalErrorMessage)
    }
    dirty_?(true)
    theData
  }

  override def valueBox: Box[MyType] = synchronized {
    trace("Calling GetSetOnwerField.valueBox")
    if (needsDefault && !optional_?) { //FIXME - THIS CODE CAME FROM THE TypeField trait. Delete it
      needsDefault = false
      theData = defaultValueBox
    }
    trace("Data returned is {}",
      if (canRead_?) theData
      else theData.flatMap(debug("Data obscured by {}", obscure) )
    )
  }

  override def asString = displayName + "=" + theData.openTheBox
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

