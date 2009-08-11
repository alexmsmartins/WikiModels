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
package org.scala_tools.javautils.j2s

import java.lang.Iterable
import java.util.{Iterator, ArrayList, HashSet, HashMap, Enumeration, Hashtable,
  IdentityHashMap, LinkedHashMap, LinkedHashSet, LinkedList, TreeMap, TreeSet, WeakHashMap,
  List, Map, Set, Collection, Deque}

object JImplicits extends JImplicits

trait JImplicits {
  implicit def RichJEnumeration[T](enumeration: Enumeration[T]) = new RichJEnumeration(enumeration)
  implicit def RichJIterator[T](iterator: Iterator[T]) = new RichJIterator(iterator)
  implicit def RichJIterable[T](iterable: Iterable[T]) = new RichJIterable(iterable)
  implicit def RichJCollection[T](collection: Collection[T]) = new RichJCollection[T](collection)
  implicit def RichJList[T](list: List[T]) = new RichJList(list)
  implicit def RichJSet[T](set: Set[T]) = new RichJSet(set)
  implicit def RichJMap[K, V](map: Map[K, V]) = new RichJMap(map)
  implicit def RichJListWithDeque[T](lwd: List[T] with Deque[T]) = new RichJListWithDeque(lwd)
}
