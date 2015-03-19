package ${toppackage};

import griffon.core.artifact.GriffonView;
import griffon.metadata.ArtifactProviderFor;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;

import java.util.Collections;

@ArtifactProviderFor(GriffonView.class)
public class ${simplename}View extends AbstractJavaFXGriffonView {
    private ${simplename}Controller controller;
    private ${simplename}Model model;

    @FXML
    private Label clickLabel;

    public void setController(${simplename}Controller controller) {
        this.controller = controller;
    }

    public void setModel(${simplename}Model model) {
        this.model = model;
    }

    @Override
    public void initUI() {
        Stage stage = (Stage) getApplication()
            .createApplicationContainer(Collections.<String,Object>emptyMap());
        stage.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        stage.setScene(init());
        stage.sizeToScene();
        getApplication().getWindowManager().attach("mainWindow", stage);
    }

    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);

        Node node = loadFromFXML();
        model.clickCountProperty().bindBidirectional(clickLabel.textProperty());
        ((Group) scene.getRoot()).getChildren().addAll(node);
        connectActions(node, controller);

        return scene;
    }
}
