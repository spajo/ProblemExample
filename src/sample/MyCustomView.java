package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MyCustomView extends GridPane {

    private static final String FXML_RESOURCE_PATH = "my_custom_view.fxml";

    public MyCustomView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_RESOURCE_PATH));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertRowsAndLabels() {
        this.getChildren().clear();
        if (rows.get() != null) {
            for (RowView rv : rows.get()) {
                Label label = new Label(String.valueOf(rv.getRow()));
                Group group = new Group(label);
                group.setRotate(90);
                this.add(group, 1, rv.getRow());
                this.add(rv, 0, rv.getRow());
            }
        }
    }

    public final ObjectProperty<ObservableList<RowView>> rowsProperty() {
        return rows;
    }

    private ObjectProperty<ObservableList<RowView>> rows =
            new SimpleObjectProperty<ObservableList<RowView>>(this, "rows") {

                WeakReference<ObservableList<RowView>> oldItemsRef;

                @Override
                protected void invalidated() {
                    final ObservableList<RowView> oldItems = oldItemsRef == null ? null : oldItemsRef.get();
                    final ObservableList<RowView> newItems = getRows();

                    // Fix for RT-36425
                    if (newItems != null && newItems == oldItems) {
                        return;
                    }

                    oldItemsRef = new WeakReference<>(newItems);
                }
            };

    public final void setRows(ObservableList<RowView> value) {
        rowsProperty().set(value);
        insertRowsAndLabels();
    }

    public final ObservableList<RowView> getRows() {
        return rows.get();
    }

    public final BooleanProperty autoFillProperty() {
        if (autoFill == null) {
            autoFill = new SimpleBooleanProperty(this, "autoFill", false);
        }
        return autoFill;
    }

    private BooleanProperty autoFill;

    public boolean getAutoFill() {
        return autoFillProperty().get();
    }

    public void setAutoFill(boolean value) {
        autoFillProperty().set(value);
    }

    public static MyCustomView createMyCustomView() {
        ObservableList<RowView> rowViews = FXCollections.observableArrayList();
        for (int i = 0; i < 20; i++) {
            RowView rv = new RowView();
            rv.setRow(i);
            rowViews.add(rv);
        }
        MyCustomView view = new MyCustomView();
        view.setRows(rowViews);
        return view;
    }
}
