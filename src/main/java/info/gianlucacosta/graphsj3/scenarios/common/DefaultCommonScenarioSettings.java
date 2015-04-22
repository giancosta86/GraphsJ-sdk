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

import info.gianlucacosta.arcontes.algorithms.CommonAlgorithmSettings;
import info.gianlucacosta.arcontes.algorithms.DefaultCommonAlgorithmSettings;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.*;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.DefaultGraphRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.GraphRenderingInfo;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * Implementation of CommonScenarioSettings
 */
public class DefaultCommonScenarioSettings implements CommonScenarioSettings {

    private CommonAlgorithmSettings algorithmSettings;
    private GraphRenderingInfo graphRenderingInfo;
    private SelectionRectangleInfo selectionRectangleInfo;
    private GraphStyle defaultGraphStyle;
    private GraphStyle partialSolutionGraphStyle;
    private GraphStyle completeSolutionGraphStyle;

    /**
     * Creates the settings instance
     *
     * @param directedGraph true if the graph shown by the scenario is oriented
     */
    public DefaultCommonScenarioSettings(boolean directedGraph) {
        algorithmSettings = new DefaultCommonAlgorithmSettings();

        graphRenderingInfo = new DefaultGraphRenderingInfo();

        selectionRectangleInfo = new DefaultSelectionRectangleInfo();

        defaultGraphStyle = createDefaultGraphStyle(directedGraph);

        partialSolutionGraphStyle = createPartialSolutionGraphStyle(directedGraph);

        completeSolutionGraphStyle = createCompleteSolutionGraphStyle(directedGraph);
    }

    public DefaultCommonScenarioSettings(CommonScenarioSettings source) {
        algorithmSettings = new DefaultCommonAlgorithmSettings(source.getAlgorithmSettings());

        graphRenderingInfo = new DefaultGraphRenderingInfo(source.getGraphRenderingInfo());

        selectionRectangleInfo = new DefaultSelectionRectangleInfo(source.getSelectionRectangleInfo());

        defaultGraphStyle = new DefaultGraphStyle(source.getDefaultGraphStyle());

        partialSolutionGraphStyle = new DefaultGraphStyle(source.getPartialSolutionGraphStyle());

        completeSolutionGraphStyle = new DefaultGraphStyle(source.getCompleteSolutionGraphStyle());
    }

    public DefaultCommonScenarioSettings(CommonAlgorithmSettings algorithmSettings, GraphRenderingInfo graphRenderingInfo, SelectionRectangleInfo selectionRectangleInfo, GraphStyle defaultGraphStyle, GraphStyle partialSolutionGraphStyle, GraphStyle completeSolutionGraphStyle) {
        this.algorithmSettings = new DefaultCommonAlgorithmSettings(algorithmSettings);
        this.graphRenderingInfo = new DefaultGraphRenderingInfo(graphRenderingInfo);
        this.selectionRectangleInfo = new DefaultSelectionRectangleInfo(selectionRectangleInfo);
        this.defaultGraphStyle = new DefaultGraphStyle(defaultGraphStyle);
        this.partialSolutionGraphStyle = new DefaultGraphStyle(partialSolutionGraphStyle);
        this.completeSolutionGraphStyle = new DefaultGraphStyle(completeSolutionGraphStyle);
    }

    private GraphStyle createDefaultGraphStyle(boolean directedGraph) {
        DefaultVertexStyleInfo vertexStyleInfo = new DefaultVertexStyleInfo();
        vertexStyleInfo.getNonSelectedRenderingInfo().setBackgroundColor(Color.web("fbf6ce"));
        vertexStyleInfo.getSelectedRenderingInfo().setBackgroundColor(Color.web("ffce5a"));

        DefaultLinkStyleInfo linkStyleInfo = new DefaultLinkStyleInfo();
        linkStyleInfo.getNonSelectedRenderingInfo().setColor(Color.web("9fecf3"));
        linkStyleInfo.getNonSelectedTailRenderingInfo().setVisible(directedGraph);

        linkStyleInfo.getSelectedRenderingInfo().setColor(Color.web("b3ccff"));
        linkStyleInfo.getSelectedLabelRenderingInfo().setFontColor(Color.web("990000"));
        linkStyleInfo.getSelectedTailRenderingInfo().setVisible(directedGraph);

        return new DefaultGraphStyle(vertexStyleInfo, linkStyleInfo);
    }

    private GraphStyle createPartialSolutionGraphStyle(boolean directedGraph) {
        DefaultVertexStyleInfo vertexStyleInfo = new DefaultVertexStyleInfo();
        vertexStyleInfo.getNonSelectedRenderingInfo().setBackgroundColor(Color.web("fbf6ce"));
        vertexStyleInfo.getSelectedRenderingInfo().setBackgroundColor(Color.web("ffce5a"));

        DefaultLinkStyleInfo linkStyleInfo = new DefaultLinkStyleInfo();
        linkStyleInfo.getNonSelectedRenderingInfo().setColor(Color.web("e5a2cc"));
        linkStyleInfo.getNonSelectedTailRenderingInfo().setVisible(directedGraph);

        linkStyleInfo.getSelectedRenderingInfo().setColor(Color.web("b72c84"));
        linkStyleInfo.getSelectedTailRenderingInfo().setVisible(directedGraph);

        return new DefaultGraphStyle(vertexStyleInfo, linkStyleInfo);
    }

    private GraphStyle createCompleteSolutionGraphStyle(boolean directedGraph) {
        DefaultVertexStyleInfo vertexStyleInfo = new DefaultVertexStyleInfo();
        vertexStyleInfo.getNonSelectedRenderingInfo().setBackgroundColor(Color.web("fbf6ce"));
        vertexStyleInfo.getSelectedRenderingInfo().setBackgroundColor(Color.web("ffce5a"));

        DefaultLinkStyleInfo linkStyleInfo = new DefaultLinkStyleInfo();
        linkStyleInfo.getNonSelectedRenderingInfo().setColor(Color.web("c2f8aa"));
        linkStyleInfo.getNonSelectedTailRenderingInfo().setVisible(directedGraph);

        linkStyleInfo.getSelectedRenderingInfo().setColor(Color.web("74ba55"));
        linkStyleInfo.getSelectedTailRenderingInfo().setVisible(directedGraph);

        return new DefaultGraphStyle(vertexStyleInfo, linkStyleInfo);
    }

    @Override
    public CommonAlgorithmSettings getAlgorithmSettings() {
        return algorithmSettings;
    }

    public void setAlgorithmSettings(CommonAlgorithmSettings algorithmSettings) {
        this.algorithmSettings = algorithmSettings;
    }

    @Override
    public GraphRenderingInfo getGraphRenderingInfo() {
        return graphRenderingInfo;
    }

    public void setGraphRenderingInfo(GraphRenderingInfo graphRenderingInfo) {
        this.graphRenderingInfo = graphRenderingInfo;
    }

    @Override
    public SelectionRectangleInfo getSelectionRectangleInfo() {
        return selectionRectangleInfo;
    }

    public void setGraphCanvasInfo(SelectionRectangleInfo selectionRectangleInfo) {
        this.selectionRectangleInfo = selectionRectangleInfo;
    }

    @Override
    public GraphStyle getDefaultGraphStyle() {
        return defaultGraphStyle;
    }

    public void setDefaultGraphStyle(DefaultGraphStyle defaultGraphStyle) {
        this.defaultGraphStyle = defaultGraphStyle;
    }

    @Override
    public GraphStyle getPartialSolutionGraphStyle() {
        return partialSolutionGraphStyle;
    }

    public void setPartialSolutionGraphStyle(DefaultGraphStyle partialSolutionGraphStyle) {
        this.partialSolutionGraphStyle = partialSolutionGraphStyle;
    }

    @Override
    public GraphStyle getCompleteSolutionGraphStyle() {
        return completeSolutionGraphStyle;
    }

    public void setCompleteSolutionGraphStyle(DefaultGraphStyle completeSolutionGraphStyle) {
        this.completeSolutionGraphStyle = completeSolutionGraphStyle;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommonScenarioSettings)) {
            return false;
        }

        CommonScenarioSettings other = (CommonScenarioSettings) obj;

        return Objects.equals(algorithmSettings, other.getAlgorithmSettings())
                && Objects.equals(graphRenderingInfo, other.getGraphRenderingInfo())
                && Objects.equals(selectionRectangleInfo, other.getSelectionRectangleInfo())
                && Objects.equals(defaultGraphStyle, other.getDefaultGraphStyle())
                && Objects.equals(partialSolutionGraphStyle, other.getPartialSolutionGraphStyle())
                && Objects.equals(completeSolutionGraphStyle, other.getCompleteSolutionGraphStyle());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
