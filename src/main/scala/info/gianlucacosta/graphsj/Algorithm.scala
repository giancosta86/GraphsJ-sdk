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

import info.gianlucacosta.eighthbridge.graphs.point2point.visual.{VisualGraph, VisualLink, VisualVertex}

import scala.annotation.tailrec

/**
  * Interactive lightweight algorithm, based on the VisualGraph drawn by the user
  */
trait Algorithm[V <: VisualVertex[V], L <: VisualLink[L], G <: VisualGraph[V, L, G]] {
  /**
    * Executes a step of the algorithm.
    *
    * The algorithm must internally store information between successive steps.
    *
    * @param stepIndex 0-based index of the current step
    * @param graph     The current runtime graph, starting from the design graph.
    *                  For steps having stepIndex >= 1, it is the graph
    *                  resulting from the previous call to runStep(), plus interactive modifications,
    *                  performed by the user, that were allowed by the scenario's runtime controller
    * @param console   Handle for writing to the application's text console
    * @return the pair (NewGraph, TheAlgorithmContinues) where:
    *         <ul>
    *         <li><b>NewGraph</b> is the new visual graph (for example, with links of the
    *         partial solution having a dedicated color). It <em>cannot</em> be null - at least return the graph itself</li>
    *         <li><b>TheAlgorithmContinues</b> is a boolean flag telling the framework that more steps
    *         should be performed by the algorithm</li>
    *         </ul>
    */
  def runStep(
               stepIndex: Int,
               graph: G,
               console: OutputConsole
             ): (G, Boolean)


  /**
    * Executes the algorithm until completion, returning the result graph.
    *
    * @param graph   The design graph
    * @param console The output console
    * @return The graph returned by the last algorithm iteration
    */
  def fullRun(graph: G, console: OutputConsole): G = {
    fullRun(0, graph, console)._1
  }


  @tailrec
  private def fullRun(stepIndex: Int, graph: G, console: OutputConsole): (G, Boolean) = {
    val (newGraph, theAlgorithmContinues) =
      runStep(stepIndex, graph, console)

    if (theAlgorithmContinues)
      fullRun(stepIndex + 1, newGraph, console)
    else
      (newGraph, theAlgorithmContinues)
  }
}
