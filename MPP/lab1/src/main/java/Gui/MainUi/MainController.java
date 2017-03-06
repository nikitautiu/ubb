package Gui.MainUi;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by archie on 12/15/2016.
 */
public class MainController {
    @FXML TabPane tabs;

    @FXML
    public void initialize() {
    }

    // TODO: add a purpose for tabhandler
    public void addTab(String tabName, Node tabNode, Object tabHandler) {
        Tab newTab = new Tab(tabName);
        newTab.setContent(tabNode);
        tabs.getTabs().add(newTab);
    }
}
