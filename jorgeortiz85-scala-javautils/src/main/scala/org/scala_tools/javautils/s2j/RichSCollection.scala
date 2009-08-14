/**
 * Copyright 2009 Jorge Ortiz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 *
 **/
package org.scala_tools.javautils.s2j

import java.util.{Collection => JCollection}
import scala.collection.jcl.{IterableWrapper => JCLIterableWrapper}
import org.scala_tools.javautils.j2s.JCollectionWrapper

class RichSCollection[T](collection: Collection[T]) {
  def asJava: JCollection[T] = collection match {
    case iw: JCLIterableWrapper[_] =>
      iw.underlying.asInstanceOf[JCollection[T]]
    case iw: JCollectionWrapper[_] =>
      iw.asJava.asInstanceOf[JCollection[T]]
    case _ => new SCollectionWrapper[T] {
      type Wrapped = Collection[T]
      val underlying = collection
    }
  }
}
