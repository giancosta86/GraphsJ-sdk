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

package info.gianlucacosta.graphsj3.scenarios.common.metainfo;

import info.gianlucacosta.arcontes.fx.canvas.GraphCanvasAgentsFactory;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.agents.BasicGraphCanvasGraphMetaInfoAgent;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.agents.BasicGraphCanvasLinkMetaInfoAgent;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.agents.BasicGraphCanvasVertexMetaInfoAgent;
import info.gianlucacosta.arcontes.fx.metainfo.agents.InteractiveDoubleWeightInfoAgent;
import info.gianlucacosta.arcontes.fx.metainfo.agents.InteractiveUniqueVertexNameInfoAgent;
import info.gianlucacosta.arcontes.graphs.Graph;
import info.gianlucacosta.arcontes.graphs.GraphContext;
import info.gianlucacosta.arcontes.graphs.Link;
import info.gianlucacosta.arcontes.graphs.Vertex;
import info.gianlucacosta.arcontes.graphs.metainfo.DoubleWeightLabelInfo;
import info.gianlucacosta.arcontes.graphs.metainfo.NameBasedLabelInfo;
import info.gianlucacosta.arcontes.graphs.metainfo.agents.DefaultWeightInfoAgent;
import info.gianlucacosta.arcontes.graphs.metainfo.agents.ProgressiveVertexNameInfoAgent;
import info.gianlucacosta.graphsj3.scenarios.common.CommonScenarioSettings;
import info.gianlucacosta.helios.application.io.CommonInputService;
import info.gianlucacosta.helios.metainfo.CompositeMetaInfoAgent;
import info.gianlucacosta.helios.metainfo.MetaInfoAgent;

/**
 * Good starting point for creating a custom GraphCanvasAgentsFactory for your
 * scenario
 * <p>
 * It provides several metainfo agents (please, refer to the source code for
 * further details)
 */
public class CommonCanvasAgentsFactory implements GraphCanvasAgentsFactory {

    private final CommonScenarioSettings scenarioSettings;
    private final CommonInputService commonInputService;

    public CommonCanvasAgentsFactory(CommonScenarioSettings scenarioSettings, CommonInputService commonInputService) {
        this.scenarioSettings = scenarioSettings;
        this.commonInputService = commonInputService;
    }

    protected CommonScenarioSettings getScenarioSettings() {
        return scenarioSettings;
    }

    protected CommonInputService getCommonInputService() {
        return commonInputService;
    }

    @Override
    public MetaInfoAgent<Graph> getAgentForGraph(Graph graph) {
        return new BasicGraphCanvasGraphMetaInfoAgent(
                scenarioSettings.getGraphRenderingInfo(),
                scenarioSettings.getSelectionRectangleInfo());
    }

    @Override
    public MetaInfoAgent<Vertex> getAgentForVertexCreation(GraphContext graphContext, Vertex vertex) {
        return new CompositeMetaInfoAgent<>(
                new ProgressiveVertexNameInfoAgent(graphContext),
                new BasicGraphCanvasVertexMetaInfoAgent(
                        scenarioSettings.getDefaultGraphStyle().getVertexStyleInfo(), new NameBasedLabelInfo(graphContext.getMetaInfoRepository(), vertex)
                )
        );
    }

    @Override
    public MetaInfoAgent<Vertex> getAgentForVertexEditing(GraphContext graphContext, Vertex vertex) {
        return new InteractiveUniqueVertexNameInfoAgent(graphContext, commonInputService, "Vertex name:");
    }

    @Override
    public MetaInfoAgent<Link> getAgentForLinkCreation(GraphContext graphContext, Link link) {
        return new CompositeMetaInfoAgent<>(
                new DefaultWeightInfoAgent<Link, Double>(0.0),
                new BasicGraphCanvasLinkMetaInfoAgent(
                        getScenarioSettings().getDefaultGraphStyle().getLinkStyleInfo(), new DoubleWeightLabelInfo(graphContext.getMetaInfoRepository(), link)
                )
        );
    }

    @Override
    public MetaInfoAgent<Link> getAgentForLinkEditing(GraphContext graphContext, Link link) {
        return new InteractiveDoubleWeightInfoAgent<>(commonInputService);
    }
}
