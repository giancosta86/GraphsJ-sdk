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

import info.gianlucacosta.arcontes.fx.rendering.metainfo.DefaultVertexRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.VertexRenderingInfo;
import info.gianlucacosta.helios.exceptions.ValidationException;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraintsBuilder;
import javafx.scene.text.Font;

/**
 * Panel for letting the user edit an instance of VertexRenderingInfo
 */
public class VertexRenderingInfoPanel extends GridPane {

    private final ColorPicker backgroundColorPicker;
    private final TextField borderSizeField;
    private final ColorPicker borderColorPicker;
    private final TextField fontSizeField;
    private final ColorPicker fontColorPicker;
    private final TextField paddingField;

    private DefaultVertexRenderingInfo vertexRenderingInfo;

    public VertexRenderingInfoPanel() {
        setPadding(new Insets(15));

        setPrefSize(550, 350);

        getColumnConstraints().addAll(
                ColumnConstraintsBuilder.create().prefWidth(150).build(),
                ColumnConstraintsBuilder.create().fillWidth(true).hgrow(Priority.ALWAYS).build());

        getRowConstraints().addAll(
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build());

        add(new Label("Background color:"), 0, 0);
        backgroundColorPicker = new ColorPicker();
        add(backgroundColorPicker, 1, 0);

        add(new Label("Border size:"), 0, 1);
        borderSizeField = new TextField();
        add(borderSizeField, 1, 1);

        add(new Label("Border color:"), 0, 2);
        borderColorPicker = new ColorPicker();
        add(borderColorPicker, 1, 2);

        add(new Label("Font size:"), 0, 3);
        fontSizeField = new TextField();
        add(fontSizeField, 1, 3);

        add(new Label("Font color:"), 0, 4);
        fontColorPicker = new ColorPicker();
        add(fontColorPicker, 1, 4);

        add(new Label("Padding:"), 0, 5);
        paddingField = new TextField();
        add(paddingField, 1, 5);
    }

    public VertexRenderingInfo getVertexRenderingInfo() throws ValidationException {
        vertexRenderingInfo.setBackgroundColor(backgroundColorPicker.getValue());

        try {
            vertexRenderingInfo.setBorderSize(Double.parseDouble(borderSizeField.getText()));
        } catch (IllegalArgumentException ex) {
            borderSizeField.requestFocus();
            throw new ValidationException("Invalid border size");
        }

        vertexRenderingInfo.setBorderColor(borderColorPicker.getValue());

        try {
            double fontSize = Double.parseDouble(fontSizeField.getText());
            vertexRenderingInfo.setFont(new Font(vertexRenderingInfo.getFont().getName(), fontSize));
        } catch (IllegalArgumentException ex) {
            fontSizeField.requestFocus();
            throw new ValidationException("Invalid font size");
        }

        vertexRenderingInfo.setFontColor(fontColorPicker.getValue());

        try {
            vertexRenderingInfo.setPadding(Double.parseDouble(paddingField.getText()));
        } catch (IllegalArgumentException ex) {
            paddingField.requestFocus();
            throw new ValidationException("Invalid padding");
        }

        return new DefaultVertexRenderingInfo(vertexRenderingInfo);
    }

    public void setVertexRenderingInfo(VertexRenderingInfo value) {
        this.vertexRenderingInfo = new DefaultVertexRenderingInfo(value);

        backgroundColorPicker.setValue(value.getBackgroundColor());
        borderSizeField.setText(Integer.toString((int) value.getBorderSize()));
        borderColorPicker.setValue(value.getBorderColor());
        fontSizeField.setText(Integer.toString((int) value.getFont().getSize()));
        fontColorPicker.setValue(value.getFontColor());
        paddingField.setText(Integer.toString((int) value.getPadding()));
    }

}
