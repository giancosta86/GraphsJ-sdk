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

import info.gianlucacosta.arcontes.fx.rendering.metainfo.DefaultLinkLabelRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.DefaultLinkRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.LinkLabelRenderingInfo;
import info.gianlucacosta.arcontes.fx.rendering.metainfo.LinkRenderingInfo;
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
 * Panel for letting the user edit an instance of LinkRenderingInfo
 */
public class LinkRenderingInfoPanel extends GridPane {

    private final TextField lineSizeField;
    private final ColorPicker colorPicker;
    private final TextField labelFontSizeField;
    private final ColorPicker labelFontColorPicker;

    private DefaultLinkRenderingInfo linkRenderingInfo;
    private DefaultLinkLabelRenderingInfo linkLabelRenderingInfo;

    public LinkRenderingInfoPanel() {
        setPadding(new Insets(15));

        setPrefSize(550, 350);

        getColumnConstraints().addAll(
                ColumnConstraintsBuilder.create().prefWidth(150).build(),
                ColumnConstraintsBuilder.create().fillWidth(true).hgrow(Priority.ALWAYS).build());

        getRowConstraints().addAll(
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build(),
                RowConstraintsBuilder.create().fillHeight(true).vgrow(Priority.ALWAYS).build());

        add(new Label("Line size:"), 0, 0);
        lineSizeField = new TextField();
        add(lineSizeField, 1, 0);

        add(new Label("Line color:"), 0, 1);
        colorPicker = new ColorPicker();
        add(colorPicker, 1, 1);

        add(new Label("Line font size:"), 0, 2);
        labelFontSizeField = new TextField();
        add(labelFontSizeField, 1, 2);

        add(new Label("Label font color:"), 0, 3);
        labelFontColorPicker = new ColorPicker();
        add(labelFontColorPicker, 1, 3);
    }

    public LinkRenderingInfo getLinkRenderingInfo() throws ValidationException {
        try {
            linkRenderingInfo.setLineSize(Double.parseDouble(lineSizeField.getText()));
        } catch (IllegalArgumentException ex) {
            lineSizeField.requestFocus();
            throw new ValidationException("Invalid line size");
        }

        linkRenderingInfo.setColor(colorPicker.getValue());

        return new DefaultLinkRenderingInfo(linkRenderingInfo);
    }

    public LinkLabelRenderingInfo getLinkLabelRenderingInfo() throws ValidationException {
        try {
            linkLabelRenderingInfo.setFont(
                    new Font(linkLabelRenderingInfo.getFont().getName(),
                            Double.parseDouble(labelFontSizeField.getText())));
        } catch (IllegalArgumentException ex) {
            labelFontSizeField.requestFocus();
            throw new ValidationException("Invalid label font size");
        }

        linkLabelRenderingInfo.setFontColor(labelFontColorPicker.getValue());

        return new DefaultLinkLabelRenderingInfo(linkLabelRenderingInfo);
    }

    public void setInfo(LinkRenderingInfo linkRenderingInfo, LinkLabelRenderingInfo linkLabelRenderingInfo) {
        this.linkRenderingInfo = new DefaultLinkRenderingInfo(linkRenderingInfo);
        this.linkLabelRenderingInfo = new DefaultLinkLabelRenderingInfo(linkLabelRenderingInfo);

        lineSizeField.setText(Integer.toString((int) linkRenderingInfo.getLineSize()));
        colorPicker.setValue(linkRenderingInfo.getColor());

        labelFontSizeField.setText(Integer.toString((int) linkLabelRenderingInfo.getFont().getSize()));
        labelFontColorPicker.setValue(linkLabelRenderingInfo.getFontColor());
    }

}
