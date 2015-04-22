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

import info.gianlucacosta.arcontes.fx.rendering.metainfo.DefaultGraphRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.GraphRenderingInfo;
import info.gianlucacosta.helios.exceptions.ValidationException;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraintsBuilder;

/**
 * Panel for letting the user edit an instance of GraphRenderingInfo
 */
public class GraphRenderingInfoPanel extends GridPane {

    private final TextField widthField;
    private final TextField heightField;
    private final ColorPicker backgroundColorPicker;

    private DefaultGraphRenderingInfo graphRenderingInfo;

    public GraphRenderingInfoPanel() {
        setPadding(new Insets(15));

        getColumnConstraints().addAll(
                ColumnConstraintsBuilder.create().prefWidth(150).build(),
                ColumnConstraintsBuilder.create().fillWidth(true).hgrow(Priority.ALWAYS).build());

        getRowConstraints().addAll(
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build());

        add(new Label("Width:"), 0, 0);
        widthField = new TextField();
        add(widthField, 1, 0);

        add(new Label("Height:"), 0, 1);
        heightField = new TextField();
        add(heightField, 1, 1);

        add(new Label("Background color:"), 0, 2);
        backgroundColorPicker = new ColorPicker();
        add(backgroundColorPicker, 1, 2);
    }

    public GraphRenderingInfo getGraphRenderingInfo() throws ValidationException {
        try {
            graphRenderingInfo.setWidth(Double.parseDouble(widthField.getText()));
        } catch (IllegalArgumentException ex) {
            widthField.requestFocus();
            throw new ValidationException("Invalid graph width value");
        }

        try {
            graphRenderingInfo.setHeight(Double.parseDouble(heightField.getText()));
        } catch (IllegalArgumentException ex) {
            heightField.requestFocus();
            throw new ValidationException("Invalid graph height value");
        }

        graphRenderingInfo.setBackgroundColor(backgroundColorPicker.getValue());

        return new DefaultGraphRenderingInfo(graphRenderingInfo);
    }

    public void setGraphRenderingInfo(GraphRenderingInfo value) {
        this.graphRenderingInfo = new DefaultGraphRenderingInfo(value);

        widthField.setText(Integer.toString((int) value.getWidth()));
        heightField.setText(Integer.toString((int) value.getHeight()));
        backgroundColorPicker.setValue(value.getBackgroundColor());
    }
}
