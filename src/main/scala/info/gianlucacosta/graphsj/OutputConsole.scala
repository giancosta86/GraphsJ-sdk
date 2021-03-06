/*§
  ===========================================================================
  GraphsJ - SDK
  ===========================================================================
  Copyright (C) 2009-2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.graphsj

/**
  * Text output console
  */
trait OutputConsole {
  /**
    * Prints the string representation of a value
    *
    * @param value
    */
  def write(value: Any)

  /**
    * Prints the string representation of a value, followed by a newline character
    *
    * @param value
    */
  def writeln(value: Any)

  /**
    * Prints a newline character
    */
  def writeln()

  /**
    * Prints a header
    *
    * @param header The header text
    */
  def writeHeader(header: String): Unit = {
    val headerLine =
      "-" * header.length

    writeln(headerLine)
    writeln(header)
    writeln(headerLine)
  }
}
