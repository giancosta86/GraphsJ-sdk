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

import info.gianlucacosta.arcontes.algorithms.CommonAlgorithmSettings;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.DefaultSelectionRectangleInfo;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.GraphStyle;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.SelectionRectangleInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.GraphRenderingInfo;
import info.gianlucacosta.graphsj3.scenarios.common.CommonScenarioSettings;
import info.gianlucacosta.graphsj3.scenarios.common.DefaultCommonScenarioSettings;
import info.gianlucacosta.helios.application.io.CommonOutputService;
import info.gianlucacosta.helios.exceptions.ValidationException;
import info.gianlucacosta.helios.fx.dialogs.NodeDialog;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Panel for letting the user edit an instance of CommonScenarioSettings
 */
public class CommonScenarioSettingsDialog extends NodeDialog<CommonScenarioSettings> {

    private final CommonOutputService commonOutputService;
    private TabPane scenarioSettingsTabPane;
    private Tab graphRenderingInfoTab;
    private GraphRenderingInfoPanel graphRenderingInfoPanel;
    private Tab algorithmSettingsTab;
    private CommonAlgorithmSettingsPanel algorithmSettingsPanel;
    private Tab defaultGraphStyleTab;
    private GraphStylePanel defaultGraphStylePanel;
    private Tab partialSolutionGraphStyleTab;
    private GraphStylePanel partialSolutionGraphStylePanel;
    private Tab completeSolutionGraphStyleTab;
    private GraphStylePanel completeSolutionGraphStylePanel;

    private CommonScenarioSettings scenarioSettings;

    public CommonScenarioSettingsDialog(CommonOutputService commonOutputService, CommonScenarioSettings scenarioSettings) {
        super("Edit scenario settings...");

        this.commonOutputService = commonOutputService;
        this.scenarioSettings = new DefaultCommonScenarioSettings(scenarioSettings);
    }

    @Override
    protected Node createContentNode() {
        scenarioSettingsTabPane = new TabPane();
        scenarioSettingsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        graphRenderingInfoTab = new Tab("Graph");
        graphRenderingInfoPanel = new GraphRenderingInfoPanel();
        graphRenderingInfoTab.setContent(graphRenderingInfoPanel);
        scenarioSettingsTabPane.getTabs().add(graphRenderingInfoTab);

        algorithmSettingsTab = new Tab("Algorithm");
        algorithmSettingsPanel = new CommonAlgorithmSettingsPanel();
        algorithmSettingsTab.setContent(algorithmSettingsPanel);
        scenarioSettingsTabPane.getTabs().add(algorithmSettingsTab);

        defaultGraphStyleTab = new Tab("Default style");
        defaultGraphStylePanel = new GraphStylePanel();
        defaultGraphStyleTab.setContent(defaultGraphStylePanel);
        defaultGraphStylePanel.setTitle(defaultGraphStyleTab.getText());
        scenarioSettingsTabPane.getTabs().add(defaultGraphStyleTab);

        partialSolutionGraphStyleTab = new Tab("Partial solution style");
        partialSolutionGraphStylePanel = new GraphStylePanel();
        partialSolutionGraphStyleTab.setContent(partialSolutionGraphStylePanel);
        partialSolutionGraphStylePanel.setTitle(partialSolutionGraphStyleTab.getText());
        scenarioSettingsTabPane.getTabs().add(partialSolutionGraphStyleTab);

        completeSolutionGraphStyleTab = new Tab("Complete solution style");
        completeSolutionGraphStylePanel = new GraphStylePanel();
        completeSolutionGraphStyleTab.setContent(completeSolutionGraphStylePanel);
        completeSolutionGraphStylePanel.setTitle(completeSolutionGraphStyleTab.getText());
        scenarioSettingsTabPane.getTabs().add(completeSolutionGraphStyleTab);

        return scenarioSettingsTabPane;
    }

    @Override
    public void onDialogShown() {
        super.onDialogShown();
        algorithmSettingsPanel.setAlgorithmSettings(scenarioSettings.getAlgorithmSettings());
        graphRenderingInfoPanel.setGraphRenderingInfo(scenarioSettings.getGraphRenderingInfo());

        defaultGraphStylePanel.setGraphStyle(scenarioSettings.getDefaultGraphStyle());
        partialSolutionGraphStylePanel.setGraphStyle(scenarioSettings.getPartialSolutionGraphStyle());
        completeSolutionGraphStylePanel.setGraphStyle(scenarioSettings.getCompleteSolutionGraphStyle());
    }

    @Override
    protected CommonScenarioSettings retrieveValue() throws ValidationException {
        try {
            scenarioSettings = retrieveSettingsFromControls();

            return new DefaultCommonScenarioSettings(scenarioSettings);
        } catch (ValidationException ex) {
            commonOutputService.showWarning(ex.getMessage());
            throw new ValidationException(ex);
        }
    }

    private CommonScenarioSettings retrieveSettingsFromControls() throws ValidationException {
        CommonAlgorithmSettings algorithmSettings;
        GraphRenderingInfo graphRenderingInfo;
        SelectionRectangleInfo selectionRectangleInfo;
        GraphStyle defaultGraphStyle;
        GraphStyle partialSolutionGraphStyle;
        GraphStyle completeSolutionGraphStyle;

        try {
            algorithmSettings = algorithmSettingsPanel.getAlgorithmSettings();
        } catch (ValidationException ex) {
            scenarioSettingsTabPane.getSelectionModel().select(algorithmSettingsTab);
            throw ex;
        }

        try {
            graphRenderingInfo = graphRenderingInfoPanel.getGraphRenderingInfo();
        } catch (ValidationException ex) {
            scenarioSettingsTabPane.getSelectionModel().select(graphRenderingInfoTab);
            throw ex;
        }

        selectionRectangleInfo = new DefaultSelectionRectangleInfo(scenarioSettings.getSelectionRectangleInfo());

        try {
            defaultGraphStyle = defaultGraphStylePanel.getGraphStyle();
        } catch (ValidationException ex) {
            scenarioSettingsTabPane.getSelectionModel().select(defaultGraphStyleTab);
            throw ex;
        }

        try {
            partialSolutionGraphStyle = partialSolutionGraphStylePanel.getGraphStyle();
        } catch (ValidationException ex) {
            scenarioSettingsTabPane.getSelectionModel().select(partialSolutionGraphStyleTab);
            throw ex;
        }

        try {
            completeSolutionGraphStyle = completeSolutionGraphStylePanel.getGraphStyle();
        } catch (ValidationException ex) {
            scenarioSettingsTabPane.getSelectionModel().select(completeSolutionGraphStyleTab);
            throw ex;
        }

        return new DefaultCommonScenarioSettings(
                algorithmSettings,
                graphRenderingInfo,
                selectionRectangleInfo,
                defaultGraphStyle,
                partialSolutionGraphStyle,
                completeSolutionGraphStyle);
    }

}
