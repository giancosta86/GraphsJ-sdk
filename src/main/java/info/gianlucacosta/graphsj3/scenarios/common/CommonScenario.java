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

package info.gianlucacosta.graphsj3.scenarios.common;

import info.gianlucacosta.arcontes.algorithms.*;
import info.gianlucacosta.arcontes.fx.canvas.GraphCanvasPermissions;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.GraphStyle;
import info.gianlucacosta.arcontes.graphs.*;
import info.gianlucacosta.graphsj3.scenarios.AbstractScenario;
import info.gianlucacosta.graphsj3.scenarios.common.settingsdialog.CommonScenarioSettingsDialog;
import info.gianlucacosta.helios.conversions.StreamToStringConverter;
import info.gianlucacosta.helios.metainfo.DefaultMetaInfoRepository;
import info.gianlucacosta.helios.metainfo.MetaInfoRepository;
import info.gianlucacosta.helios.reflection.Locator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

/**
 * Scenario providing an instance of CommonAlgorithm as its algorithm.
 * <p>
 * This class also provides additional features:
 * <ul>
 * <li> it introduces the concept of <i>ScenarioSolution</i> at the end of
 * each step of the algorithm: if the step was the last, the solution is said
 * <i>complete</i>, otherwise it is said <i>partial</i>.
 * Partial solutions and complete solutions are rendered using different graph
 * styles. </li>
 * <li>it provides a default dialog to let the user edit its settings</li>
 * <li>if you include a file called "Help.htm" in the same package as your
 * scenario, it will be automatically loaded by GraphsJ whenever the user
 * requests the scenario's help. Within it, you can also use the CSS classes
 * supported by the Help-related stylesheet provided by GraphsJ!
 * </li>
 * </ul>
 */
public abstract class CommonScenario extends AbstractScenario {

    private static final ScenarioSolution emptySolution;
    private static final Logger logger = LoggerFactory.getLogger(CommonScenario.class);

    private final CommonScenarioSettingsProxy settingsProxy;
    private CommonScenarioSettings settings;
    private transient String htmlHelpSource;

    static {
        emptySolution = new ScenarioSolution() {
            @Override
            public Collection<Vertex> getVertexes() {
                return Collections.emptySet();
            }

            @Override
            public Collection<Link> getLinks() {
                return Collections.emptySet();
            }

        };
    }

    public CommonScenario(boolean directedGraph) {
        settingsProxy = new CommonScenarioSettingsProxy(new Locator<CommonScenarioSettings>() {
            @Override
            public CommonScenarioSettings locate() {
                return settings;
            }

        });

        settings = new DefaultCommonScenarioSettings(directedGraph);
    }

    @Override
    public CommonScenarioSettings getSettings() {
        return settingsProxy;
    }

    protected void setSettings(CommonScenarioSettings settings) {
        this.settings = settings;
    }

    @Override
    public GraphContext createGraphContext() {
        Graph graph = new DefaultGraph();

        MetaInfoRepository metaInfoRepository = new DefaultMetaInfoRepository();

        return new DefaultGraphContext(graph, metaInfoRepository);
    }

    @Override
    public GraphContext cloneGraphContext(GraphContext source) {
        return new DefaultGraphContext(
                new DefaultGraph(source.getGraph()),
                new DefaultMetaInfoRepository(source.getMetaInfoRepository()));
    }

    @Override
    public Algorithm createAlgorithm(final GraphContext graphContext, AlgorithmInput algorithmInput, AlgorithmOutput algorithmOutput) {
        final Algorithm algorithm = doCreateAlgorithm(graphContext, settings.getAlgorithmSettings(), algorithmInput, algorithmOutput);

        algorithm.addAlgorithmListener(new AlgorithmAdapter() {
            @Override
            public void onStepCompleted(int step, AlgorithmStepOutcome stepOutcome) {
                if (stepOutcome == AlgorithmStepOutcome.CONTINUE) {
                    ScenarioSolution partialSolution = getPartialSolution(algorithm, step);
                    applySolutionStyle(graphContext, partialSolution, settings.getPartialSolutionGraphStyle());
                } else {
                    applyDefaultStyle(graphContext);
                }
            }

            @Override
            public void onInterrupted() {
                applyDefaultStyle(graphContext);
            }

            @Override
            public void onFinished() {
                ScenarioSolution completeSolution = getCompleteSolution(algorithm);
                applySolutionStyle(graphContext, completeSolution, settings.getCompleteSolutionGraphStyle());
            }

        });

        return algorithm;
    }

    protected abstract CommonAlgorithm doCreateAlgorithm(GraphContext graphContext, CommonAlgorithmSettings algorithmSettings, AlgorithmInput algorithmInput, AlgorithmOutput algorithmOutput);

    protected abstract ScenarioSolution getPartialSolution(Algorithm algorithm, int step);

    protected abstract ScenarioSolution getCompleteSolution(Algorithm algorithm);

    private void applyDefaultStyle(GraphContext graphContext) {
        applySolutionStyle(graphContext, emptySolution, null);
    }

    private void applySolutionStyle(GraphContext graphContext, ScenarioSolution solution, GraphStyle solutionGraphStyle) {
        Graph graph = graphContext.getGraph();
        MetaInfoRepository metaInfoRepository = graphContext.getMetaInfoRepository();

        GraphStyle defaultGraphStyle = settings.getDefaultGraphStyle();

        for (Vertex vertex : graph.getVertexes()) {
            if (solution.getVertexes().contains(vertex)) {
                metaInfoRepository.putMetaInfo(vertex, solutionGraphStyle.getVertexStyleInfo());
            } else {
                metaInfoRepository.putMetaInfo(vertex, defaultGraphStyle.getVertexStyleInfo());
            }
        }

        for (Link link : graph.getLinks()) {
            if (solution.getLinks().contains(link)) {
                metaInfoRepository.putMetaInfo(link, solutionGraphStyle.getLinkStyleInfo());
            } else {
                metaInfoRepository.putMetaInfo(link, defaultGraphStyle.getLinkStyleInfo());
            }
        }
    }

    @Override
    public boolean editSettings(GraphContext graphContext) {
        CommonScenarioSettingsDialog settingsDialog = new CommonScenarioSettingsDialog(getOutputService(), settings);

        CommonScenarioSettings newSettings = settingsDialog.show();

        if (newSettings == null || settings.equals(newSettings)) {
            return false;
        }

        settings = newSettings;

        MetaInfoRepository metaInfoRepository = graphContext.getMetaInfoRepository();
        Graph graph = graphContext.getGraph();

        for (Vertex vertex : graph.getVertexes()) {
            metaInfoRepository.putMetaInfo(vertex, settings.getDefaultGraphStyle().getVertexStyleInfo());
        }

        for (Link link : graph.getLinks()) {
            metaInfoRepository.putMetaInfo(link, settings.getDefaultGraphStyle().getLinkStyleInfo());
        }

        return true;
    }

    @Override
    public String getHtmlHelpSource() {
        if (htmlHelpSource != null) {
            return htmlHelpSource;
        }

        try (InputStream helpInputStream = getClass().getResourceAsStream("Help.htm")) {
            if (helpInputStream == null) {
                return null;
            }

            htmlHelpSource = new StreamToStringConverter().convert(helpInputStream);
            return htmlHelpSource;
        } catch (IOException ex) {
            logger.trace("Cannot retrieve the scenario's online help");
            return null;
        }
    }

    @Override
    public void setRuntimeCanvasPermissions(GraphCanvasPermissions runtimePermissions) {
        runtimePermissions.disableAll();

        runtimePermissions.setCanDragVertexes(true);
        runtimePermissions.setCanDragLinkLabels(true);
        runtimePermissions.setCanCreateLinkPoints(true);
        runtimePermissions.setCanDragLinkPoints(true);
        runtimePermissions.setCanRemoveLinkPoints(true);
    }
}
