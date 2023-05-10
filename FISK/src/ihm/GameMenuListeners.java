package ihm;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

import java.io.IOException;

public class GameMenuListeners {
    @FXML
    public void onPolygonClick(MouseEvent event) throws IOException {
        Polygon source = (Polygon) event.getSource();
        System.out.println(source.getId());

    }
}
