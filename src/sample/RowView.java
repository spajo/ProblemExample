package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * @author erafaja
 * Created on 9/20/18.
 */
public class RowView extends Button {

    private static final String FXML_RESOURCE_PATH = "row_view.fxml";
    @FXML
    private Label boardLabel;

    //TODO: board property setters

    /**
     * TODO: pass in CppNode/CnNode?
     */
    public RowView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_RESOURCE_PATH));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        boardLabel.textProperty().bind(rowNameProperty());
    }

    public final StringProperty rowNameProperty() {
        if (rowName == null) {
            rowName = new SimpleStringProperty(this, "rowName", "EMPTY");
        }
        return rowName;
    }

    private StringProperty rowName;

    public final void setRowName(String value) {
        rowNameProperty().setValue(value);
    }

    public final String getRowName() {
        return rowName == null ? "" : rowName.getValue();
    }

    public final IntegerProperty rowProperty() {
        if (row == null) {
            row = new SimpleIntegerProperty(this, "row", -1);
        }
        return row;
    }

    private IntegerProperty row;

    public final void setRow(int value) {
        rowProperty().setValue(value);
    }

    public final int getRow() {
        return row == null ? -1 : row.getValue();
    }

}
