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

package info.gianlucacosta.graphsj3.scenarios.common.settingsdialog;

import info.gianlucacosta.arcontes.fx.canvas.metainfo.*;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.LinkLabelRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.LinkRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.VertexRenderingInfo;
import info.gianlucacosta.helios.exceptions.ValidationException;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * Panel for letting the user edit an instance of GraphStyle
 */
public class GraphStylePanel extends BorderPane {

    private final Label titleLabel;
    private final TabPane graphStyleTabPane;
    private final Tab nonSelectedVertexTab;
    private final VertexRenderingInfoPanel nonSelectedVertexRenderingInfoPanel;
    private final Tab selectedVertexTab;
    private final VertexRenderingInfoPanel selectedVertexRenderingInfoPanel;
    private final Tab nonSelectedLinkTab;
    private final LinkRenderingInfoPanel nonSelectedLinkRenderingInfoPanel;
    private final Tab selectedLinkTab;
    private final LinkRenderingInfoPanel selectedLinkRenderingInfoPanel;

    private GraphStyle graphStyle;

    public GraphStylePanel() {
        setPadding(new Insets(15));

        titleLabel = new Label();
        titleLabel.getStyleClass().add("graphStyleTitleLabel");
        setTop(titleLabel);

        graphStyleTabPane = new TabPane();
        graphStyleTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        graphStyleTabPane.setSide(Side.BOTTOM);

        nonSelectedVertexTab = new Tab("Vertexes");
        nonSelectedVertexRenderingInfoPanel = new VertexRenderingInfoPanel();
        nonSelectedVertexTab.setContent(nonSelectedVertexRenderingInfoPanel);
        graphStyleTabPane.getTabs().add(nonSelectedVertexTab);

        selectedVertexTab = new Tab("Selected vertexes");
        selectedVertexRenderingInfoPanel = new VertexRenderingInfoPanel();
        selectedVertexTab.setContent(selectedVertexRenderingInfoPanel);
        graphStyleTabPane.getTabs().add(selectedVertexTab);

        nonSelectedLinkTab = new Tab("Links");
        nonSelectedLinkRenderingInfoPanel = new LinkRenderingInfoPanel();
        nonSelectedLinkTab.setContent(nonSelectedLinkRenderingInfoPanel);
        graphStyleTabPane.getTabs().add(nonSelectedLinkTab);

        selectedLinkTab = new Tab("Selected links");
        selectedLinkRenderingInfoPanel = new LinkRenderingInfoPanel();
        selectedLinkTab.setContent(selectedLinkRenderingInfoPanel);
        graphStyleTabPane.getTabs().add(selectedLinkTab);

        setCenter(graphStyleTabPane);
    }

    public GraphStyle getGraphStyle() throws ValidationException {
        VertexRenderingInfo nonSelectedVertexRenderingInfo;
        VertexRenderingInfo selectedVertexRenderingInfo;

        LinkRenderingInfo nonSelectedLinkRenderingInfo;
        LinkLabelRenderingInfo nonSelectedLinkLabelRenderingInfo;

        LinkRenderingInfo selectedLinkRenderingInfo;
        LinkLabelRenderingInfo selectedLinkLabelRenderingInfo;

        try {
            nonSelectedVertexRenderingInfo = nonSelectedVertexRenderingInfoPanel.getVertexRenderingInfo();
        } catch (ValidationException ex) {
            graphStyleTabPane.getSelectionModel().select(nonSelectedVertexTab);
            throw ex;
        }

        try {
            selectedVertexRenderingInfo = selectedVertexRenderingInfoPanel.getVertexRenderingInfo();
        } catch (ValidationException ex) {
            graphStyleTabPane.getSelectionModel().select(selectedVertexTab);
            throw ex;
        }

        try {
            nonSelectedLinkRenderingInfo = nonSelectedLinkRenderingInfoPanel.getLinkRenderingInfo();
            nonSelectedLinkLabelRenderingInfo = nonSelectedLinkRenderingInfoPanel.getLinkLabelRenderingInfo();
        } catch (ValidationException ex) {
            graphStyleTabPane.getSelectionModel().select(nonSelectedLinkTab);
            throw ex;
        }

        try {
            selectedLinkRenderingInfo = selectedLinkRenderingInfoPanel.getLinkRenderingInfo();
            selectedLinkLabelRenderingInfo = selectedLinkRenderingInfoPanel.getLinkLabelRenderingInfo();
        } catch (ValidationException ex) {
            graphStyleTabPane.getSelectionModel().select(selectedLinkTab);
            throw ex;
        }

        VertexStyleInfo vertexStyleInfo = new DefaultVertexStyleInfo(nonSelectedVertexRenderingInfo, selectedVertexRenderingInfo);

        LinkStyleInfo oldLinkStyleInfo = graphStyle.getLinkStyleInfo();
        LinkStyleInfo linkStyleInfo = new DefaultLinkStyleInfo(
                nonSelectedLinkRenderingInfo,
                nonSelectedLinkLabelRenderingInfo,
                oldLinkStyleInfo.getNonSelectedLabelConnectorRenderingInfo(),
                oldLinkStyleInfo.getNonSelectedTailRenderingInfo(),
                selectedLinkRenderingInfo,
                selectedLinkLabelRenderingInfo,
                oldLinkStyleInfo.getSelectedLabelConnectorRenderingInfo(),
                oldLinkStyleInfo.getSelectedTailRenderingInfo());

        return new DefaultGraphStyle(vertexStyleInfo, linkStyleInfo);
    }

    public void setGraphStyle(GraphStyle graphStyle) {
        this.graphStyle = new DefaultGraphStyle(graphStyle);

        VertexStyleInfo vertexStyleInfo = graphStyle.getVertexStyleInfo();
        nonSelectedVertexRenderingInfoPanel.setVertexRenderingInfo(vertexStyleInfo.getNonSelectedRenderingInfo());
        selectedVertexRenderingInfoPanel.setVertexRenderingInfo(vertexStyleInfo.getSelectedRenderingInfo());

        LinkStyleInfo linkStyleInfo = graphStyle.getLinkStyleInfo();
        nonSelectedLinkRenderingInfoPanel.setInfo(linkStyleInfo.getNonSelectedRenderingInfo(), linkStyleInfo.getNonSelectedLabelRenderingInfo());
        selectedLinkRenderingInfoPanel.setInfo(linkStyleInfo.getSelectedRenderingInfo(), linkStyleInfo.getSelectedLabelRenderingInfo());
    }

    /**
     * Gets the internal title of the panel
     *
     * @return the internal title of the panel
     */
    public String getTitle() {
        return titleLabel.getText();
    }

    /**
     * Sets the internal title of the panel
     *
     * @param title the title
     */
    public void setTitle(String title) {
        titleLabel.setText(title);
    }

}
