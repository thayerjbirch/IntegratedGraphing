/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

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
public class OptionsManagerTest {
    
    public OptionsManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of setShowVertexLabels method, of class OptionsManager.
     */
    @Test
    public void testSetShowVertexLabels() {
        System.out.println("setShowVertexLabels");
        boolean val = false;
        OptionsManager instance = null;
        instance.setShowVertexLabels(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getShowVertexLabels method, of class OptionsManager.
     */
    @Test
    public void testGetShowVertexLabels() {
        System.out.println("getShowVertexLabels");
        OptionsManager instance = null;
        boolean expResult = false;
        boolean result = instance.getShowVertexLabels();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setToDefaults method, of class OptionsManager.
     */
    @Test
    public void testSetToDefaults() {
        System.out.println("setToDefaults");
        OptionsManager instance = null;
        instance.setToDefaults();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
