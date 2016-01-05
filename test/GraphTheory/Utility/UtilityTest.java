/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

import GraphTheory.IntegratedGraphing;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thayer
 */
public class UtilityTest {
    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    public UtilityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Thread t = new Thread("JavaFX Init Thread") {
        @Override
        public void run() {
                Application.launch(AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(UtilityTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadImage method, of class Utility.
     */
    @Test
    public void testLoadImage() {
        System.out.println("loadImage");
        assertEquals(true, null != Utility.loadImage("DeleteTool.png"));
    }

    /**
     * Test of loadView method, of class Utility.
     */
    @Test
    public void testLoadView() {
        System.out.println("loadView");
        assertEquals(true, null != Utility.loadView(Utility.loadImage("DeleteTool.png")));
    }

    /**
     * Test of hackTooltipStartTiming method, of class Utility.
     */
    @Test
    public void testHackTooltipStartTiming() {
        System.out.println("hackTooltipStartTiming");
        Tooltip tooltip = new Tooltip("Some random text");
        Utility.hackTooltipStartTiming(tooltip);
    }

    /**
     * Test of ensureAToggle method, of class Utility.
     */
    @Test
    public void testEnsureAToggle() {
        System.out.println("ensureAToggle");
        MouseEvent e = null;
        Utility.ensureAToggle(e);
    }

    /**
     * Test of setupToggleButton method, of class Utility.
     */
    @Test
    public void testSetupToggleButton() {
        System.out.println("setupToggleButton");
        String imgName = "";
        EventHandler<MouseEvent> clickHandler = null;
        String tooltipText = "";
        ToggleButton expResult = null;
        ToggleButton result = Utility.setupToggleButton(imgName, clickHandler, tooltipText);
        assertEquals(expResult, result);
    }

    /**
     * Test of setupButton method, of class Utility.
     */
    @Test
    public void testSetupButton() {
        System.out.println("setupButton");
        String imgName = "";
        EventHandler<MouseEvent> clickHandler = null;
        String tooltipText = "";
        Button expResult = null;
        Button result = Utility.setupButton(imgName, clickHandler, tooltipText);
        assertEquals(expResult, result);
    }
}
