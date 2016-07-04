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
  * OutputConsole implementation whose text can be inspected.
  *
  * Very useful for testing.
  */
class BufferedOutputConsole extends OutputConsole {
  private val stringBuilder =
    new StringBuilder


  override def write(value: Any): Unit =
    stringBuilder.append(value)


  override def writeln(value: Any): Unit =
    stringBuilder.append(s"${value}\n")


  override def writeln(): Unit =
    stringBuilder.append("\n")


  def text: String =
    stringBuilder.toString()
}
