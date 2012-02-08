/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * $Id: SimpleTransform.java 470245 2006-11-02 06:34:33Z minchau $
 */


package pt.cnbc.wikimodels.util

import javax.xml.transform.{Transformer, TransformerFactory}
import javax.xml.transform.stream.{StreamResult, StreamSource}
import java.io.{OutputStream, ByteArrayOutputStream, FileOutputStream, InputStream}

/** A simple demo of Xalan 1. This code was originally written using
 * Xalan 1.2.2.  It will not work with Xalan 2.
 * @author Alexandre Martins
 *  Date: 03/02/12
 *  Time: 12:46 */
object XSLTTransform extends App {
  def apply(xslt:InputStream) = new XSLTTransform(xslt)
}

class XSLTTransform(xslt: InputStream) {
  // Use the static TransformerFactory.newInstance() method to instantiate
  // a TransformerFactory. The javax.xml.transform.TransformerFactory
  // system property setting determines the actual class to instantiate --
  // org.apache.xalan.transformer.TransformerImpl.
  val tFactory:TransformerFactory  = TransformerFactory.newInstance();

  // Use the TransformerFactory to instantiate a Transformer that will work with
  // the stylesheet you specify. This method call also processes the stylesheet
  // into a compiled Templates object.
  val transformer :Transformer = tFactory.newTransformer(new StreamSource(xslt));


  def execute(xml:InputStream):String = {
    // Use the Transformer to apply the associated Templates object to an XML document
    // (foo.xml) and write the output to a file (foo.out).
    val byte1:ByteArrayOutputStream = new ByteArrayOutputStream();
    transformer.transform(new StreamSource(xml), new StreamResult(byte1));
    byte1.toString
  }

}