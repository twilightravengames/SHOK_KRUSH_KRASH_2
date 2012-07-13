/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

/**
 *
 * @author Ted
 */
public class Fireball {
    private int id;

    private int x = 50;
    private int y = 170;

    private boolean active;

    private int fireballFrame;

    int getID()
    {
        return id;
    }

    void setID(int d)
    {
        id = d;
    }

    int getX()
    {
        return x;
    }

    int getY()
    {
        return y;
    }

    void setX(int l)
    {
        x = l;
    }

    void setY(int l)
    {
        y = l;
    }


    int getFireballFrame()
    {
        return fireballFrame;
    }

    void setFireballFrame(int b)
    {
        fireballFrame = b;
    }

    boolean getActive()
    {
        return active;
    }

    void setActive(boolean a)
    {
        active = a;
    }



}
