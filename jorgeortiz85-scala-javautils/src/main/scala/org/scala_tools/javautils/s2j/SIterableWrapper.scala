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

import java.lang.{Iterable => JIterable}
import java.util.{Iterator => JIterator}

trait SIterableWrapper[T] extends JIterable[T] with SWrapper {
  type Wrapped <: Iterable[T]
  def iterator: JIterator[T] =
    Implicits.RichSIterator(underlying.elements).asJava
}
