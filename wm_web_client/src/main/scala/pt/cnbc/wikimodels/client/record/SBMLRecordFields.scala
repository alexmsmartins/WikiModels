package net.liftweb.record

import net.liftweb.common._
import net.liftweb.http.{S, SHtml}
import net.liftweb.record.field._
import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel.Compartment._
import pt.cnbc.wikimodels.dataModel.ValidSpatialDimensions._
import pt.cnbc.wikimodels.dataModel.{ValidSpatialDimensions, Compartment}
import net.liftweb.util.FieldError
import net.liftweb.util.ControlHelpers._
import xml.{Text, XML, NodeSeq}

//Javascript handling imports
import _root_.net.liftweb.http.js.{JE,JsCmd,JsCmds}
import JsCmds._ // For implicifts
import JE.{JsRaw,Str}


class MetaId[T <: SBaseRecord[T]](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayFormWithLabelInOneLine[String, T] with DisplayHTMLWithLabelInOneLine[String, T]{
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
with DisplayFormWithLabelInOneLine[String, T] with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def validate:List[FieldError] = {
      super.validate
  }

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
class Name[T <: SBaseRecord[T]{var name:String}](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayFormWithLabelInOneLine[String, T] with DisplayHTMLWithLabelInOneLine[String, T]{
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
      }
    }
    trace("Name.theData returns " + _data)
    _data
  }



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
        .replace("&nbsp;", " &#160;")
        .replace("&nbsp;", " &#160;")
        .replace("&nbsp;", " &#160;")
        .replace("&nbsp;", " &#160;")
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
          _data = defaultValueBox
          if (! this.optional_?) owner.notes = null
        }
        case Full(x) => owner.notes = x
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

        <li><span><h3>Description of the model:</h3></span>
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
            {this.value match{
              case None => Text("-- no description available -- ")
              case Some(content) => XML.loadString( content )}
            }</span>
        </span>
          <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
      </div>
  }
}

// TODO - THIS GIVES AN ERROR AND i HAVE NO CLEAR IDEA WHY
// class SpatialDimensions[T <: CompartmentRecord](own:T) extends EnumField(own, ValidSpatialDimensions)

class SpatialDimensions[T <: SBaseRecord[T]{var spatialDimensions:Int}](own:T) extends EnumField(own, ValidSpatialDimensions)
with DisplayFormWithLabelInOneLine[ValidSpatialDimensions, T] with DisplayHTMLWithLabelInOneLine[ValidSpatialDimensions, T] with LoggerWrapper{
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
      }
    trace("SpatialDimensions.theData returns " + _data)
    _data
  }


  //Appears when rendering the form or the visualization
  override def name: String = "Spatial dimensions"
  //override def toXHtml: NodeSeq = Text(this.value)
}


class Constant[T <: SBaseRecord[T]{var constant:Boolean}](own:T) extends BooleanField(own)
with DisplayFormWithLabelInOneLine[Boolean, T] with DisplayHTMLWithLabelInOneLine[Boolean, T] with LoggerWrapper{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Constant.theData_=" + in)
    _data = in
    in match{
      //if a valid value is set then update the owner class
      case Full(y) =>{
        owner.constant = y
      }
      case Empty => {
        owner.constant = Compartment.defaultConstant
      }
      case _ =>{
        owner.constant = true //lets give it a default even in case of error
        S.error("Strange error when reading the field constant in ")
      }  
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Constant.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    debug("theData with constant = "+ owner.constant + " is being copied to the record Field.")
    _data match {
      case Empty => {
        if (! this.optional_?){
          _data = Full(defaultConstant)
          owner.constant = Compartment.defaultConstant
        }
      }
      case Full(x) => {
        owner.constant = x
      }
    }
    trace("Constant.theData returns " + _data)
    _data
  }

  //Appears when rendering the form or the visualization
  override def name: String = "Constant"
  //override def toXHtml: NodeSeq = Text(this.value)
}


class Size[T <: SBaseRecord[T]{var size:java.lang.Double}](own:T) extends OptionalDoubleField(own)
with DisplayFormWithLabelInOneLine[Double, T] with DisplayHTMLWithLabelInOneLine[Double, T] with LoggerWrapper{
  // * There is a strong reason to use both java.lang.Double and scala.Double here
  // * Do not change this without knowing what to do with the client and server code when there is no size value
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Size.theData_=" + in)
    _data = in
    in match{
      //if a valid value is set then update the owner class
      case Full(y) =>{
        owner.size = y.asInstanceOf[java.lang.Double]
      }
      case Empty => {
        owner.size = null
      }
      case _ =>{
        owner.size = null //lets give it a default even in case of error
        S.error("Strange error when reading the field size in ")
      }
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Size.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.size != null){
      _data = Full(owner.size)
    } else {
      debug("theData with size = "+ owner.size + " is being copied to the record Field.")
      _data match {
        case Empty => {
          if ( this.optional_?){
            _data = Empty
            owner.size = null
          }
        }
        case Full(x) => {
          owner.size = x.asInstanceOf[java.lang.Double]
        }
      }
    }
    trace("Constant.theData returns " + _data)
    _data
  }

  override def setFromString(s: String): Box[Double] = s match {
    case "" if optional_? => setBox(Empty)
    case _ =>setBox(tryo(java.lang.Double.parseDouble(s)))
  }


  //Appears when rendering the form or the visualization
  override def name: String = "Size"
  //override def toXHtml: NodeSeq = Text(this.value)
}


//#### Aux Record traits

/**
 * Mix in to a field to change its form display to    for (id <- uniqueFieldId; control <- super.toXHtml) yield
   be formatted with the label aside.
 *
 * E.g.
 *   <div id={ id + "_holder" }>
 *     <div><label for={ id }>{ displayName }</label></div>
 *     { control }
 *   </div>
 */
trait DisplayFormWithLabelInOneLine[ThisType, OwnerType <: Record[OwnerType]] extends GetSetOwnerField[ThisType, OwnerType] {
  override abstract def toForm:Box[NodeSeq] = {
    trace("Calling DisplayFormWithLabelInOneLine.toForm")
    for (id <- uniqueFieldId; control <- super.toForm)
    yield
      <span id={id + "_holder"}>
        <label for={ id }> <span class="sbml_field_label">{ displayHtml }</span></label>
        {control}
          <lift:msg id={id}  errorClass="lift_error"/>
      </span>
  }
}

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

