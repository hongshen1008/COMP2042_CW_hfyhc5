package View;

import Model.Wall;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DebugPanelTest {

    Wall wall = new Wall(new Rectangle(0,0,600,450),30,3,6/2,new Point(300,430));
    DebugPanel debugPanel = new DebugPanel(wall);

    @Test
    void setValues() {
        debugPanel.setValues(4,4);
        assertNotNull(debugPanel);
    }
}