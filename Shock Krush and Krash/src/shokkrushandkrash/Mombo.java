 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

/**
 *
 * @author Ted
 */
public class Mombo {
    private int id;

    private int x;
    private int y;

    private int width;
    private int height;

    

    private boolean ate = false;
    

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

    boolean isAte()
    {
        return ate;
    }
    void setAte(boolean a)
    {
        ate = a;
    }


}
