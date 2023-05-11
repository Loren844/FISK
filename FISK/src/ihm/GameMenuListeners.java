package ihm;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

import java.io.IOException;

public class GameMenuListeners {
    @FXML
    public void onLabelClick(MouseEvent event) throws IOException
    {
        Label clique = (Label) event.getSource();
        String polyId = "#p" + clique.getId().substring(1);
        Polygon polySource = (Polygon) clique.getScene().lookup(polyId);
        polySource.fireEvent(event);
    }

    @FXML
    public void onPolygonClick(MouseEvent event) throws IOException
    {
        Polygon source = (Polygon) event.getSource();
        System.out.println(source.getId());
    }
}
