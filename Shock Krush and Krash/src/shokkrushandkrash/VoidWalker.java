/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

/**
 *
 * @author Ted
 */
public class VoidWalker {
    private int id;

    private int x = 50;
    private int y = 200;

    private boolean active = false;

    private int monsterFrame;

    private int status = Game.IDLE;

    private int health = 50;

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


    int getMonsterFrame()
    {
        return monsterFrame;
    }

    void setMonsterFrame(int b)
    {
        monsterFrame = b;
    }

    boolean getActive()
    {
        return active;
    }

    void setActive(boolean a)
    {
        active = a;
   }
    void setStatus(int s)
    {
        status = s;
    }
    int getStatus()
    {
        return status;
    }
    void setHealth(int h)
    {
        health = h;
    }
    int getHealth()
    {
        return health;
    }

}


