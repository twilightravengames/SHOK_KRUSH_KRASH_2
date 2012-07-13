/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import javax.swing.`
/**
 *
 * @author Ted
 */
public class InputHandler extends KeyAdapter implements KeyListener {
    public void keyPressed(KeyEvent e)
    {
        Game.keyBuffer = e.getKeyCode();
    }
}
