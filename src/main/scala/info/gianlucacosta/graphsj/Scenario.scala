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
import info.gianlucacosta.eighthbridge.fx.canvas.basic.{BasicLink, BasicVertex, DragDropController}
import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph

/**
  * A descriptor for an interactive problem that can be plugged into the application.
  *
  * You also need to implement a ScenarioFactory to instantiate it.
  *
  * Deployment is very easy, as you just need to package your scenarios and the related
  * factories in a JAR file, to be deployed to GraphsJ's scenarios directory.
  *
  */
trait Scenario[
V <: BasicVertex[V],
L <: BasicLink[L],
G <: VisualGraph[V, L, G]
] {
  /**
    * Scenario name, as shown in the "Scenario name" dialog. Can be different from the name
    * provided by the related ScenarioFactory.
    */
  val name: String

  /**
    * Shows help for the scenario - for example, opening a dialog, or a web page
    */
  def showHelp(): Unit

  /**
    * Show settings for the scenario. Usually, this means showing a dialog, but you could also
    * notify the user that no settings are available.
    *
    * Please, note that a settings dialog should actually alter var fields within the scenario
    * instance: such instance is always saved by the application with the design graph whenever
    * the user saves their current workspace.
    *
    * @param designGraph The design graph
    */
  def showSettings(designGraph: G): Option[G]

  /**
    * Creates the design graph. You could want to return an instance of DefaultVisualGraph.
    *
    * @return The graph interactively changed by the user at design time  (=> when not running the algorithm)
    */
  def createDesignGraph(): G

  /**
    * Creates the GraphCanvas controller to be used at design time.
    * In particular, such controller says how to render graph components (vertexes, links, ...)
    * and what the user can interactively do.
    *
    * Usually, you'll have to create a custom class implementing BasicController
    * or one or more of its subtraits.
    *
    * @return The GraphCanvas controller for the design time
    */
  def createDesignController(): GraphCanvasController[V, L, G]

  /**
    * Creates the GraphCanvas controller to be used when running the algorithm.
    *
    * Usually, the default DragDropController is a good choice, as it limits
    * the user's interactivity to drag&drop only.
    *
    * @return The GraphCanvas controller for the runtime phase
    */
  def createRuntimeController(): GraphCanvasController[V, L, G] =
    new DragDropController[V, L, G]


  /**
    * Create the algorithm instance when the runtime phase starts; the very same algorithm
    * instance is then used <i>at every algorithm step</i> until that execution ends.
    *
    * @return The algorithm instance for a single execution
    */
  def createAlgorithm(): Algorithm[V, L, G]


  /**
    * Returns a list of CSS stylesheets to be added to the scene when loading the scenario.
    *
    * Usually, unless you want to introduce very specific customization,
    * the very first stylesheet to add should be <b>BasicStyles.resourceUrl.toExternalForm</b>,
    * provided by the EighthBridge library
    *
    * @return A list of CSS URLs as expected by JavaFX
    */
  def stylesheets: List[String]


  /**
    * The number of algorithm steps that GraphsJ can perform before asking the user
    * if a <i>full</i> execution can go on. Does not affect step-by-step run and Algorithm methods.
    *
    * @return The interval (in steps) after which the framework pauses the execution with
    *         a confirmation dialog. If a value <= 0 is returned, no confirmation dialog will be shown
    */
  def runStepsBeforePausing: Int


  override def toString: String =
    name
}
