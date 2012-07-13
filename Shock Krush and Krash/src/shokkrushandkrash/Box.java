 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

/**
 *
 * @author Ted
 */
public class Box {
    private int id;

    private int x;
    private int y;

    private int width;
    private int height;

    private int contents;

    private boolean boxBroken;
    private int boxFrame;

    private boolean active = false;

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

    boolean isBroken()
    {
        return boxBroken;
    }
    void setBoxBroken(boolean b)
    {
        boxBroken = b;
    }

    int getBoxFrame()
    {
        return boxFrame;
    }

    void setBoxFrame(int b)
    {
        boxFrame = b;
    }

    int getContents()
    {
        return contents;
    }

    void setContents(int c)
    {
        contents = c;
    }

    void setActive(boolean a)
    {
        active = a;
    }

    boolean isActive()
    {
        return active;
    }
}
