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
import info.gianlucacosta.arcontes.algorithms.DefaultCommonAlgorithmSettings;
import info.gianlucacosta.helios.exceptions.ValidationException;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraintsBuilder;

/**
 * Panel for letting the user edit an instance of CommonAlgorithmSettings
 */
public class CommonAlgorithmSettingsPanel extends GridPane {

    private final CheckBox verboseBox;
    private DefaultCommonAlgorithmSettings algorithmSettings;

    public CommonAlgorithmSettingsPanel() {
        setPadding(new Insets(15));

        getColumnConstraints().addAll(
                ColumnConstraintsBuilder.create().prefWidth(150).build());

        getRowConstraints().addAll(
                RowConstraintsBuilder.create().valignment(VPos.CENTER).vgrow(Priority.ALWAYS).build());

        verboseBox = new CheckBox("Verbose");

        add(verboseBox, 0, 0);
    }

    public CommonAlgorithmSettings getAlgorithmSettings() throws ValidationException {
        algorithmSettings.setVerbose(verboseBox.isSelected());

        return new DefaultCommonAlgorithmSettings(algorithmSettings);
    }

    public void setAlgorithmSettings(CommonAlgorithmSettings algorithmSettings) {
        this.algorithmSettings = new DefaultCommonAlgorithmSettings(algorithmSettings);

        verboseBox.setSelected(algorithmSettings.isVerbose());
    }

}
