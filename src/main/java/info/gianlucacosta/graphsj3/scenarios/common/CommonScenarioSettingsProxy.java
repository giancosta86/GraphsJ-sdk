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
import info.gianlucacosta.arcontes.fx.canvas.metainfo.GraphStyle;
import info.gianlucacosta.arcontes.fx.canvas.metainfo.SelectionRectangleInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.GraphRenderingInfo;
import info.gianlucacosta.helios.reflection.Locator;

/**
 * Delegates every method to a CommonScenarioSettings object obtained via a
 * Locator, <strong>including</strong> equals() and hashcode()
 */
public class CommonScenarioSettingsProxy implements CommonScenarioSettings {

    private final Locator<CommonScenarioSettings> settingsLocator;

    public CommonScenarioSettingsProxy(Locator<CommonScenarioSettings> settingsLocator) {
        this.settingsLocator = settingsLocator;
    }

    private CommonScenarioSettings getSettings() {
        return settingsLocator.locate();
    }

    @Override
    public GraphRenderingInfo getGraphRenderingInfo() {
        return getSettings().getGraphRenderingInfo();
    }

    @Override
    public SelectionRectangleInfo getSelectionRectangleInfo() {
        return getSettings().getSelectionRectangleInfo();
    }

    @Override
    public GraphStyle getDefaultGraphStyle() {
        return getSettings().getDefaultGraphStyle();
    }

    @Override
    public CommonAlgorithmSettings getAlgorithmSettings() {
        return getSettings().getAlgorithmSettings();
    }

    @Override
    public GraphStyle getPartialSolutionGraphStyle() {
        return getSettings().getPartialSolutionGraphStyle();
    }

    @Override
    public GraphStyle getCompleteSolutionGraphStyle() {
        return getSettings().getCompleteSolutionGraphStyle();
    }

    @Override
    public boolean equals(Object obj) {
        return getSettings().equals(obj);
    }

    @Override
    public int hashCode() {
        return getSettings().hashCode();
    }
}
