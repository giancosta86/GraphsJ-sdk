/*§
  ===========================================================================
  GraphsJ - SDK
  ===========================================================================
  Copyright (C) 2009-2015 Gianluca Costa
  ===========================================================================
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public
  License along with this program.  If not, see
  <http://www.gnu.org/licenses/gpl-3.0.html>.
  ===========================================================================
*/

package info.gianlucacosta.graphsj3.scenarios;

import info.gianlucacosta.arcontes.algorithms.Algorithm;
import info.gianlucacosta.arcontes.algorithms.AlgorithmInput;
import info.gianlucacosta.arcontes.algorithms.AlgorithmOutput;
import info.gianlucacosta.arcontes.fx.canvas.GraphCanvas;
import info.gianlucacosta.arcontes.fx.canvas.GraphCanvasPermissions;
import info.gianlucacosta.arcontes.graphs.GraphContext;
import info.gianlucacosta.arcontes.graphs.Link;
import info.gianlucacosta.arcontes.graphs.Vertex;
import info.gianlucacosta.arcontes.graphs.wrappers.LinkWrapper;
import info.gianlucacosta.arcontes.graphs.wrappers.VertexWrapper;
import info.gianlucacosta.helios.application.io.CommonInputService;
import info.gianlucacosta.helios.application.io.CommonOutputService;

/**
 * The layer enabling you to interact with the internals of GraphsJ.
 * <p>
 * Scenario provides, on purpose, several features, orchestrating how GraphsJ
 * should deal with your algorithm; in particular:
 * <ul> <li>Creating the GraphContext on which the algorithm will operate</li>
 * <li>Creating the graph canvas employed by the user to edit the graph</li>
 * <li>Writing runtime metainfo on the dedicated copy of the graph context that
 * is provided by GraphsJ on algorithm execution - the purpose of that copy is
 * to allow both the developer and the user to change the graph context at will
 * at runtime, without affecting the original graph context</li>
 * <li>Creating an instance of the algorithm whenever the user requests to run it</li>
 * <li>Returning its settings and provide a way to let the user change them</li>
 * <li>Creating wrappers for both vertexes and links, used by GraphsJ's input
 * dialogs</li>
 * <li>Optionally providing HTML documentation to the user</li>
 * </ul>
 */
public interface Scenario {

    /**
     * Creates the graph context on which users draw their graph
     *
     * @return the graph context that must be created whenever the user creates,
     * in GraphsJ, a new document based on this scenario
     */
    GraphContext createGraphContext();

    /**
     * Clones the given graph context, making its components (the graph and the
     * metainfo repository) totally independent of the ones of the source graph
     * context
     *
     * @param source the source graph context
     * @return a clone of the source graph context
     */
    GraphContext cloneGraphContext(GraphContext source);

    /**
     * Creates the graph canvas used to show the graph when a document is
     * created or opened
     *
     * @param graphContext The graph context on which the graph canvas must be
     *                     based. It could have been just created by createGraphContext(), or
     *                     deserialized from a file
     * @return the graph canvas, based on the passed graph context
     */
    GraphCanvas createGraphCanvas(GraphContext graphContext);

    /**
     * Called by GraphsJ just before instantiating the algorithm
     *
     * @param runtimeGraphContext A copy of the original graph context - use it
     *                            as you wish, because you won't alter the original graph context
     */
    void initRuntimeGraphContext(GraphContext runtimeGraphContext);

    /**
     * Called by GraphsJ to set the permissions associated with the graph canvas
     * during the execution of an algorithm.
     *
     * @param runtimePermissions the runtime permissions
     */
    void setRuntimeCanvasPermissions(GraphCanvasPermissions runtimePermissions);

    /**
     * Creates the algorithm provided by this scenario. It is called whenever
     * the user requests algorithm execution
     *
     * @param graphContext    the current run-time graph context, the one passed to
     *                        initRuntimeGraphContext()
     * @param algorithmInput  AlgorithmInput instance provided by GraphsJ
     * @param algorithmOutput AlgorithmOutput instance provided by GraphsJ
     * @return an instance of your algorithm
     */
    Algorithm createAlgorithm(GraphContext graphContext, AlgorithmInput algorithmInput, AlgorithmOutput algorithmOutput);

    /**
     * @return The settings of this scenario
     */
    ScenarioSettings getSettings();

    /**
     * Enables the user to change the settings of this scenario, usually via a
     * dialog
     *
     * @param graphContext the graph context on which the user is working
     * @return true to notify GraphsJ that the user confirmed a change in the
     * settings
     */
    boolean editSettings(GraphContext graphContext);

    /**
     * This method is called by GraphsJ to wrap vertexes in its user interface,
     * in order to make them appear nicely printed (instead of showing their
     * memory address)
     *
     * @param graphContext the graph context
     * @param vertex       the vertex
     * @return the VertexWrapper instance wrapping the given vertex
     */
    VertexWrapper createInputVertexWrapper(GraphContext graphContext, Vertex vertex);

    /**
     * This method is called by GraphsJ to wrap links in its user interface, in
     * order to make them appear nicely printed (instead of showing their memory
     * address)
     *
     * @param graphContext the graph context
     * @param link         the link
     * @return the LinkWrapper instance wrapping the given link
     */
    LinkWrapper createInputLinkWrapper(GraphContext graphContext, Link link);

    /**
     * Use this method to provide online help to the users of your scenario
     *
     * @return the HTML markup to be shown in GraphsJ's help window, or null to
     * declare that no online help is available
     */
    String getHtmlHelpSource();

    /**
     * Called by GraphsJ to set the output service, used to output information
     * to the user
     *
     * @param outputService the output service
     */
    void setOutputService(CommonOutputService outputService);

    /**
     * Called by GraphsJ to set the input service, used to ask the user for data
     *
     * @param inputService the input service
     */
    void setInputService(CommonInputService inputService);

}
