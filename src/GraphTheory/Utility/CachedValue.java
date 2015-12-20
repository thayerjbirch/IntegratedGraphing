/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Thayer
 */
public class CachedValue<T> implements ActionListener{
    T heldValue;
    
    public CachedValue(T value){

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
