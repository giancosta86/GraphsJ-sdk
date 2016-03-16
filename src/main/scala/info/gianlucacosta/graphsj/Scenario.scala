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

import info.gianlucacosta.eighthbridge.fx.canvas.GraphCanvasController
import info.gianlucacosta.eighthbridge.fx.canvas.basic.DragDropController
import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph

/**
  * A descriptor for an interactive problem that can be plugged into the application.
  *
  * You can create one or more concrete classes and package them into a JAR file to deploy them.
  */
trait Scenario extends Cloneable {
  /**
    * The scenario name, as shown in the "New" dialog
    */
  val name: String

  /**
    * Show help for the scenario - for example, show a dialog, or open a web page
    */
  def showHelp(): Unit

  /**
    * Show settings for the scenario. Usually, that means showing a dialog, but you could also
    * notify the user that no settings are available.
    *
    * Please, note that a settings dialog should actually alter var fields within the scenario
    * instance: such instance is always saved by the application with the design graph whenever
    * the user saves their current workspace.
    */
  def showSettings(): Unit

  /**
    * Creates the design graph. You could want to return an instance of DefaultVisualGraph.
    *
    * @return The graph interactively changed by the user while not running an algorithm
    */
  def createDesignGraph(): VisualGraph

  /**
    * Creates the GraphCanvas controller to be used when not running the algorithm.
    * In particular, such controller says how to render graph components (vertexes, links, ...)
    * and what the user can interactively do.
    *
    * Usually, you'll have to create a custom class implementing BasicController
    * or one or more of its subtraits.
    *
    * @return The GraphCanvas controller for the design phase (=> when not running the algorithm)
    */
  def createDesignController(): GraphCanvasController

  /**
    * Creates the GraphCanvas controller to be used when running the algorithm.
    *
    * Usually, the default DragDropController is a good choice, as it limits
    * the user's interactivity to drag&drop only.
    *
    * @return The GraphCanvas controller for the runtime phase
    */
  def createRuntimeController(): GraphCanvasController =
    new DragDropController


  /**
    * Create the algorithm instance when the runtime phase starts; the very same algorithm
    * instance is then used at every algorithm step until that execution ends.
    *
    * @return The algorithm instance for a single execution
    */
  def createAlgorithm(): Algorithm


  override def toString: String = name

  override def clone(): Scenario = super.clone().asInstanceOf[Scenario]
}
