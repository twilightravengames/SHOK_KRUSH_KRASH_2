/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

/**
 *
 * @author Ted
 */
public class Tornado {
    private int id;

    private int x = 50;
    private int y = 310;

    private boolean active;

    private int tornadoFrame;

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getTornadoFrame() {
        return tornadoFrame;
    }

    public void setTornadoFrame(int tornadoFrame) {
        this.tornadoFrame = tornadoFrame;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
