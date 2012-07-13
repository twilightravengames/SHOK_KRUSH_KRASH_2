/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

/**
 *
 * @author Ted
 */
public class Icedagger {
    private int id;

    private int x = 50;
    private int y = 240;

    private boolean active;

    private int icedaggerFrame;

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


    int getIcedaggerFrame()
    {
        return icedaggerFrame;
    }

    void setIcedaggerFrame(int b)
    {
        icedaggerFrame = b;
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
