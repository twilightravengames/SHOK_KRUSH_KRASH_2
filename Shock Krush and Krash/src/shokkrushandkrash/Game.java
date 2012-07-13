/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shokkrushandkrash;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 *
 * @author Ted
 */
public class Game extends JFrame {
    
    //Image objects for the game
    BufferedImage background1 = null;
    BufferedImage floor1 = null;
    BufferedImage [] shokattack = new BufferedImage[MAX_ANIMATION_FRAMES+1];
    BufferedImage [] shokidle = new BufferedImage[MAX_ANIMATION_FRAMES+1];
    BufferedImage shokhurt = null;
    BufferedImage menubar = null;
    BufferedImage [] box =  new BufferedImage[MAX_BOX_FRAMES+1];
    BufferedImage healthCross = null;
    BufferedImage extraLife = null;
    BufferedImage fireChameleon = null;
    BufferedImage fireball = null;
    BufferedImage iceChameleon = null;
    BufferedImage icedagger = null;
    BufferedImage windChameleon = null;
    BufferedImage tornado = null;
    BufferedImage mombo = null;
    BufferedImage [] voidImage = new BufferedImage[MAX_VOID_FRAMES+1];
    BufferedImage [] walkerSpawn = new BufferedImage[MAX_WALKER_FRAMES+1];
    BufferedImage voidWalkerIdle = null;
    BufferedImage [] walkerWalk = new BufferedImage[MAX_WALKER_FRAMES+1];
    BufferedImage [] walkerDie = new BufferedImage[MAX_WALKER_FRAMES+1];
    //NPC and PC status constants
    
    public static final int IDLE = 0;
    public static final int ATTACKING = 1;
    public static final int HURT = 2;
    public static final int JUMPING = 3;
    public static final int DUCKING = 4;
    public static final int FALLING = 5;
    public static final int DEAD = 6;
    public static final int SPAWNING = 7;
    public static final int WALKING = 8;
    public static final int SLOWING_DOWN = 9;
    public static final int WALKING_LEFT = 10;
    public static final int WALKING_RIGHT = 11;
    public static final int STOPPED = 12;

    //Game Constants
    public static final int MAX_ANIMATION_FRAMES = 7;
    public static final int MAX_FIREBALLS = 5;
    public static final int MAX_ICEDAGGERS = 5;
    public static final int MAX_TORNADOS = 5;
    public static final int JUMP_HEIGHT = 200;
    public static final int JUMP_SPEED = 100;
    public static final int JUMP_FORWARD = 5;
    public static final int WALKING_RATE = 20;


    public static final int MAX_VOIDS = 2;
    public static final int MOMBO_HEALTH = 10;
    public static final int BOX_PROBABILITY = 50;
    public static final int SCROLL_SPEED = 10;

    //Next Constants

    public static int next = 0;
    public static final int ZERO_TYPE = 0;
    public static final int BOX_TYPE = 1;


    //Box Contents
    public static final int EMPTY = 0;
    public static final int HEALTH_CROSS = 1;
    public static final int EXTRA_LIFE = 2;
    public static final int FIRE_CHAMELEON = 3;
    public static final int ICE_CHAMELEON = 4;
    public static final int WIND_CHAMELEON = 5;
    //input constructs
    public static int keyBuffer;

    //game global data
    public static int scrollCounter = 800;
    public static final int SCREEN_LIMIT_X = 800;
    public static final int SCREEN_LIMIT_Y = 600;

    //Timer info
    long time = 0;
    public static final int TIME_FACTOR = 1000;
    boolean breakingBox = false;


    //Shok's behavior status
    public static int shokStatus = IDLE;
    public static int shokCollisionStatus = IDLE;
    public static int shokJumpingStatus = IDLE;
    public static int shokAnimationFrame = 0;



    //Shok's Stats
    public static int health = 100;
    public static int maxHealth = 100;
    public static int magic = 100;
    public static int maxMagic = 100;
    public static int lives = 3;
    public static int maxLives = 10;
    public static int score = 0;
    public static int level = 1;
    public static int damage = 10;
    public static int chameleons = 0;
    public static int maxChameleons = 4;
    public static boolean hasFireChameleon = false;
    public static boolean hasIceChameleon = false;
    public static boolean hasWindChameleon = false;
    public static boolean hasEarthChameleon = false;
    public static int addictionFactor = 2;

    //shok's original position
    public static final int shokStandingX = 0;
    public static final int shokStandingY = 350;
    public static final int shokStandingWidth = 300;
    public static final int shokStandingHeight = 300;

    //shok's location
    public static int shokX = shokStandingX;
    public static int shokY = shokStandingY;
    public static int shokWidth = 300;
    public static int shokHeight = 300;


    //shoks collision detection info
    public static final int sx = 100;
    public static final int sy = 100;
    public static final int swx = 220;
    public static final int syh = 200;


    //box info
    Box [] boxes = new Box[400];
    public static final int MAX_BOX_FRAMES = 13;

    //mombo fruit
    Mombo [] mombos = new Mombo[400];


    //chameleon projectiles
    
    Fireball [] fireballs = new Fireball[MAX_FIREBALLS];
    public static int fireballCtr = 0;
    public static int numFireballs = 0;
    public static final int FIREBALL_SPEED = 15;

    Icedagger [] icedaggers = new Icedagger[MAX_ICEDAGGERS];
    public static int icedaggerCtr = 0;
    public static int numIcedaggers = 0;
    public static final int ICEDAGGER_SPEED = 15;

    Tornado [] tornados = new Tornado[MAX_TORNADOS];
    public static int tornadoCtr = 0;
    public static int numTornados = 0;
    public static final int TORNADO_SPEED = 15;


    //monster voids
    MonsterVoid [] monsterVoids = new MonsterVoid[MAX_VOIDS];
    public static final int MAX_VOID_FRAMES = 10;

    //void walker
    public static final int MAX_WALKERS = 2;
    public static final int WALKER_SPEED = 10;
    public static final int IDLE_SPEED = 10;
    VoidWalker [] voidWalker = new VoidWalker[MAX_WALKERS];
    public static final int MAX_WALKER_FRAMES = 6;



    public Game()
    {
        
        //Set up input handler
        InputHandler inputHandler = new InputHandler();
        //pane.setFocusable(true);
        addKeyListener(inputHandler);

        setSize(SCREEN_LIMIT_X, SCREEN_LIMIT_Y);
        validate();                // Make sure layout is ok

        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.gif"));
        setTitle("Shok, Krash, and Krush");

        setLocationRelativeTo(null); 
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIgnoreRepaint(true);

        Calendar calendar = Calendar.getInstance();
        time = calendar.getTimeInMillis();


        initializeBoxes();
        initializeMombos();
        initializeFireballs();
        initializeIcedaggers();
        initializeTornados();
        initializeMonsterVoid();
        loadImages();
        
        mainloop();
        
    }
    public void mainloop()
    {
        //gather input
        while (true)
        {
            

            pause();
            scroll();
            
            addictionHandler();

            //do collision detection
            collisionDetect();
            //animate pcs
            animatePlayerCharacters();
            //animate npcs
            animateNonPlayerCharacters();
            //draw scene
            jumpHandler();
            fallingHandler();
            slowdownHandler();
            walkHandler();
            duckHandler();
            stopHandler();
            nextHandler();
            boxHandler();
            chameleonHandler();
            voidHandler();
            AIHandler();
            NPCMover();
            drawScene();
            try
            {
                Thread.sleep(50);
            }
            catch (Exception e)
            {
                System.out.println("Thread sleep failed");
            }
        }
        
        
    }

    void addictionHandler()
    {
        Calendar calendar = Calendar.getInstance();


        long newtime = calendar.getTimeInMillis();

        System.out.println(newtime);

        long testtime = newtime - time;

        System.out.println(testtime);

        if (testtime > TIME_FACTOR)
        {
            System.out.println("Addiction is taking its toll");
            if ((health >= addictionFactor))
            {

                health -= addictionFactor;

            }
            if (health < addictionFactor)
            {
                if (lives > 0)
                {
                    lives -= 1;
                    health = maxHealth;
                }
                if (lives == 0)
                {
                    shokStatus = DEAD;
                    maxHealth = 0;
                }
                
                System.out.println(time);
                System.out.println("==============");
            }
            time = calendar.getTimeInMillis();
        }
    }


    void AIHandler()
    {
        for (int i = 0; i<MAX_WALKERS;i++)
        {
            int random = (int)(100*Math.random());
            if ((random > 50) && (voidWalker[i].getStatus() != DEAD))
            {
                voidWalker[i].setStatus(WALKING);
            }
            if ((random < 50) && (voidWalker[i].getStatus() != DEAD))
            {
                voidWalker[i].setStatus(IDLE);
            }
        }
    }
    void NPCMover()
    {
        for (int i=0;i<MAX_WALKERS;i++)
        {
            if (voidWalker[i].getStatus() == WALKING)
            {
                int x = voidWalker[i].getX();
                voidWalker[i].setX(x-WALKER_SPEED);
                if (voidWalker[i].getX() < 0)
                {
                    voidWalker[i].setActive(false);
                }
            }
            if (voidWalker[i].getStatus() == IDLE)
            {
                int x = voidWalker[i].getX();
                voidWalker[i].setX(x-IDLE_SPEED);
                if (voidWalker[i].getX() < 0)
                {
                    voidWalker[i].setActive(false);
                }
            }
        }
    }
    void voidHandler()
    {
        //create void
        //spawn void
        int random = (int)(100*Math.random());
        
        for (int i = 0;i<MAX_VOIDS;i++)
        {
            if ((monsterVoids[i].getActive() == false) && (voidWalker[i].getActive() == false))
            {
                int randomY = (int)(400*Math.random()+200);
                int randomX = (int)(700*Math.random()+100);

                if ((random > 90) && (randomY > 400) && (randomY < 600) && (randomX > 50) && (randomX < 700))
                {
                    
                    
                    

                    monsterVoids[i].setActive(true);
                    monsterVoids[i].setX(randomX);
                    monsterVoids[i].setY(randomY);

                }
            }
        }

        
        //create monster
        //update monster

    }


    void chameleonHandler()
    {
        //create fireballs

        //let loose fireballs
        
        if (Game.shokStatus == ATTACKING)
        {
            if (hasFireChameleon)
            {
                
                numFireballs++;
                fireballCtr++;

                if  (fireballCtr >= MAX_FIREBALLS)
                {
                    fireballCtr = MAX_FIREBALLS-1;
                }
                if (numFireballs >= MAX_FIREBALLS)
                {
                    numFireballs = MAX_FIREBALLS-1;
                }
                fireballs[fireballCtr].setActive(true);
            }

            if (hasIceChameleon)
            {

                numIcedaggers++;
                icedaggerCtr++;

                if  (icedaggerCtr >= MAX_ICEDAGGERS)
                {
                    icedaggerCtr = MAX_ICEDAGGERS-1;
                }
                if (numIcedaggers >= MAX_ICEDAGGERS)
                {
                    numIcedaggers = MAX_ICEDAGGERS-1;
                }
                icedaggers[icedaggerCtr].setActive(true);
            }
            if (hasWindChameleon)
            {

                numTornados++;
                tornadoCtr++;

                if  (tornadoCtr >= MAX_TORNADOS)
                {
                    tornadoCtr = MAX_TORNADOS-1;
                }
                if (tornadoCtr >= MAX_TORNADOS)
                {
                    tornadoCtr = MAX_TORNADOS-1;
                }
                tornados[tornadoCtr].setActive(true);
            }

        }

        //update fireball position
        
        for (int i = 0; i<MAX_FIREBALLS; i++)
        {
            if (fireballs[i].getActive() == true)
            {
                int x;
                x = fireballs[i].getX();
                fireballs[i].setX(x+FIREBALL_SPEED);
            }
        
        
            if ((fireballs[i].getX()+FIREBALL_SPEED) > SCREEN_LIMIT_X)
                {
                    fireballs[i].setActive(false);
                    fireballs[i].setX(50);
                    if (fireballCtr > 0)
                    {
                        fireballCtr--;
                        numFireballs--;
                    }
                }
        }
        //update ice dagger position

        for (int i = 0; i<MAX_ICEDAGGERS; i++)
        {
            if (icedaggers[i].getActive() == true)
            {
                int x;
                x = icedaggers[i].getX();
                icedaggers[i].setX(x+ICEDAGGER_SPEED);
            }


            if ((icedaggers[i].getX()+ICEDAGGER_SPEED) > SCREEN_LIMIT_X)
                {
                    icedaggers[i].setActive(false);
                    icedaggers[i].setX(50);
                    if (icedaggerCtr > 0)
                    {
                        icedaggerCtr--;
                        numIcedaggers--;
                    }
                }
        }

        //update tornado position


        for (int i = 0; i<MAX_TORNADOS; i++)
        {
            if (tornados[i].getActive() == true)
            {
                int x;
                x = tornados[i].getX();
                tornados[i].setX(x+TORNADO_SPEED);
            }


            if ((tornados[i].getX()+TORNADO_SPEED) > SCREEN_LIMIT_X)
                {
                    tornados[i].setActive(false);
                    tornados[i].setX(50);
                    if (tornadoCtr > 0)
                    {
                        tornadoCtr--;
                        numTornados--;
                    }
                }
        }

    }

    void pause()
    {
        boolean pause = false;
        while(pause)
        {
            if (keyBuffer == KeyEvent.VK_SPACE)
            {
                if (pause == true)
                {
                    pause = false;
                }
                if (pause == false)
                {
                    pause = true;
                }
                keyBuffer = 0;
            }
        }
    }



    void boxHandler()
    {
        for (int i=0;i<400;i++)
        {
            if (boxes[i].isBroken())
            {
                int frame = boxes[i].getBoxFrame();
                if (frame < MAX_BOX_FRAMES)
                {
                    frame++;
                    boxes[i].setBoxFrame(frame);
                }
            }
        }
    }

    void jumpHandler()
    {
        System.out.println("Running jump handler");
        if (shokJumpingStatus == JUMPING)
        {
            System.out.println("Shok's status is Jumping");
            if (shokY > (shokStandingY-JUMP_HEIGHT))
            {
                shokY -= JUMP_SPEED/((JUMP_HEIGHT+shokY)/100);
                
                if (shokX < SCREEN_LIMIT_X-150)
                    shokX += JUMP_FORWARD;

            }

            System.out.println("ShokY is: " + shokY + " shokStandingY is " + shokStandingY + "JUMP HIEGHT is "+JUMP_HEIGHT);
            if (shokY <= (shokStandingY-JUMP_HEIGHT))
            {
                
                System.out.println("Changing status to falling");
                shokJumpingStatus = FALLING;
                Game.shokAnimationFrame = 0;
            }
        }


        //if (shokY < shokStandingY && shokStatus != JUMPING)
        //{
        //    shokY += JUMP_SPEED;
        //}
       
    }


    void fallingHandler()
    {

        System.out.println("Running falling handler");
        System.out.println("Shok Status is: " + shokJumpingStatus);
        if (shokJumpingStatus == FALLING)
        {
            System.out.println("Falling...");
            if (shokY < shokStandingY)
            {

                int newShokY = shokY + JUMP_SPEED/((JUMP_HEIGHT+shokY)/100);
                if (newShokY < shokStandingY)
                {
                    shokY = newShokY;

                    if (shokX < SCREEN_LIMIT_X-150)
                        shokX += JUMP_FORWARD;
                }
                else
                {
                    shokY = shokStandingY;
                    shokJumpingStatus = SLOWING_DOWN;
                }

                System.out.println("Falling, current shoky is:" + shokY);
                System.out.println("Falling, current jump speed is: " + JUMP_SPEED);
            }

        }


    }

    void slowdownHandler()
    {
        if (shokJumpingStatus == SLOWING_DOWN)
        {
            if (shokX > shokStandingX)
            {
                shokX -= JUMP_FORWARD;
            }
            else if (shokX <= shokStandingX)
            {
                shokX = shokStandingX;
                shokJumpingStatus = IDLE;
            }
        }
    }

    void walkHandler()
    {
        if (shokCollisionStatus == IDLE)
        {
            if (shokStatus == WALKING_LEFT)
            {
                if (shokX > shokStandingX)
                    shokX -= WALKING_RATE;
                else
                    shokX = shokStandingX;
            }


            if (shokStatus == WALKING_RIGHT)
            {
                if (shokX < SCREEN_LIMIT_X-150)
                    shokX += WALKING_RATE;

            }
        }

    }


    void stopHandler()
    {
        System.out.println("Shok Collision Status is: " + shokCollisionStatus);
        if (shokCollisionStatus == STOPPED)
        {
            if (shokX > shokStandingX)
                shokX -= 10;
            else
                shokX = shokStandingX;
        }
    }

     void duckHandler()
     {
        if (shokStatus == DUCKING)
        {
            shokHeight = shokStandingY - shokStandingHeight/2;
            shokY = shokStandingY  + shokHeight/2;
        }

     }

     void nextHandler()
     {
         next = ZERO_TYPE;
         for (int i=0;i<400;i++)
         {
             if ((boxes[i].getX() > SCREEN_LIMIT_X) && (boxes[i].getX() < SCREEN_LIMIT_X+75) && (boxes[i].isActive()))
             {
                 next = BOX_TYPE;
                 if (boxes[i].getContents() == FIRE_CHAMELEON)
                    next = FIRE_CHAMELEON;

                 if (boxes[i].getContents() == ICE_CHAMELEON)
                    next = ICE_CHAMELEON;

                 if (boxes[i].getContents() == WIND_CHAMELEON)
                    next = WIND_CHAMELEON;


             }

         }
     }



     void collisionDetect()
     {
         collisionDetectMombos(shokX+sx, shokY+sy, shokX+shokWidth+sx-swx, shokY+shokHeight+sy-syh, 1);
         collisionDetectBoxes(shokX+sx, shokY+sy, shokX+shokWidth+sx-swx, shokY+shokHeight+sy-syh, 1);

         for (int i=0;i<MAX_FIREBALLS;i++)
         {
            collisionDetectBoxes(fireballs[i].getX(), fireballs[i].getY(), fireballs[i].getX()+75, fireballs[i].getY()+75, 2);
         }
         for (int i=0;i<MAX_ICEDAGGERS;i++)
         {
            collisionDetectBoxes(icedaggers[i].getX(), icedaggers[i].getY(), icedaggers[i].getX()+75, icedaggers[i].getY()+75, 2);
         }

         for (int i=0;i<MAX_TORNADOS;i++)
         {
            collisionDetectBoxes(tornados[i].getX(), tornados[i].getY(), tornados[i].getX()+75, tornados[i].getY()+75, 2);
         }

         for (int i=0;i<MAX_WALKERS;i++)
         {
             collisionDetectShok(shokX+sx, shokY+sy, shokX+shokWidth+sx-swx, shokY+shokHeight+sy-syh, 1);
         }
     }


    void collisionDetectBoxes(int x1, int y1, int x2, int y2, int mode)
    {
        
        //check to see if boxes are in range
        boolean collided = false;
        boolean hit = false;
        for (int i=0;i<400;i++)
        {
            
            
            for (int j=0;j<50;j++)
            {
            if ((boxes[i].getX() > x1) && (boxes[i].isActive()))
                {
                
                if (boxes[i].getX() < x2)
                {
                    if (boxes[i].getY() < y2)
                    {
                        if ((boxes[i].getY()+j) > y1)
                        {
                            if (mode == 1)
                            {
                                if ((shokStatus != ATTACKING) && (hit == false))
                                {
                                    //shok got hit by a box
                                    //damageShok(1);
                                    hit = true;
                                    collided = true;
                                }
                                else if (shokStatus == ATTACKING)
                                {
                                    breakBox(i);
                                    collided = true;
                                }

                                if (shokStatus != ATTACKING)
                                {
                                    System.out.println("Ran into a box. Stopped");
                                    shokCollisionStatus = STOPPED;
                                    collided = true;
                                }
                            }
                            else if (mode == 2)
                            {
                                breakBox(i);
                            }

                        }
                            
                    }
                }
                
            }
        if ((collided == false) && (mode == 1))
        {
                
                shokCollisionStatus = IDLE;
        }


        }


        }


    }



    void collisionDetectMombos(int x1, int y1, int x2, int y2, int mode)
    {

        //check to see if boxes are in range
        for (int i=0;i<400;i++)
        {
            boolean hit = false;
            for (int j=0;j<50;j++)
            {
            if (mombos[i].getX() > x1)
                {

                if (mombos[i].getX() < x2)
                {
                    if (mombos[i].getY() < y2)
                    {
                        if ((mombos[i].getY()+j) > y1)
                        {
                            eatMombo(i);
                        }

                    }
                }

            }
         }

        }


    }


    public void collisionDetectShok(int x1,int y1,int x2, int y2,int mode)
    {
        for (int i=0;i<MAX_WALKERS;i++)
        {
    
            if (voidWalker[i].getActive() == true)
            {
            for (int j=0;j<50;j++)
            {
                if (voidWalker[i].getX() > x1)
                {

                    if (voidWalker[i].getX() < x2)
                    {
                        if (voidWalker[i].getY() < y2)
                        {
                            if ((voidWalker[i].getY()+j) > y1)
                            {
                                if (mode == 1)
                                {
                                    if ((shokStatus != ATTACKING) && (shokStatus != HURT))
                                    {
                                    //shok got hit by a voidwalker
                                   
                                    damageShok(5);
                                    
                                    }
                                    else if (shokStatus == ATTACKING)
                                    {
                                        
                                        damageVoidWalker(i);
                                    }
                                }

                            }

                        }
                    }
                }
            }
            }

        }
    }

    public void breakBox(int index)
    {
        if (boxes[index].isBroken() == false)
        {
            boxes[index].setBoxBroken(true);
            score += 10;
            if (boxes[index].getContents() == HEALTH_CROSS)
            {
                healShok(10);
            

            }
            else if (boxes[index].getContents() == EXTRA_LIFE)
            {
                if (lives < maxLives)
                {
                    lives++;
                }
            
            }
            else if (boxes[index].getContents() == FIRE_CHAMELEON)
            {
                if (hasFireChameleon == false)
                {
                    hasFireChameleon = true;
                    chameleons++;
                }

            }
            else if (boxes[index].getContents() == ICE_CHAMELEON)
            {
                if (hasIceChameleon == false)
                {
                    hasIceChameleon = true;
                    chameleons++;
                }
            }

            else if (boxes[index].getContents() == WIND_CHAMELEON)
            {
                if (hasWindChameleon == false)
                {
                    hasWindChameleon = true;
                    chameleons++;
                }
            }

        }
   }

    public void eatMombo(int i)
    {
        if (health < maxHealth)
        {
            if (mombos[i].isAte() == false)
            {
                
                healShok(MOMBO_HEALTH);
            }
            if (health >= maxHealth)
            {
                health = maxHealth;
            }
        }
        mombos[i].setAte(true);
    }

    public void damageVoidWalker(int i)
    {
        if (voidWalker[i].getHealth() >= damage)
        {
            int voidHealth = voidWalker[i].getHealth();
            voidHealth -= damage;
            voidWalker[i].setHealth(voidHealth);
        }
        else
        {
            voidWalker[i].setStatus(DEAD);

            
        }
    }

    public void damageShok(int points)
    {
        if ((health >= points))
        {
            
            health -= points;
            shokStatus = HURT;
            shokAnimationFrame = 0;
        }
        if (health == 0)
        {
            if (lives > 0)
            {
                lives -= 1;
                health = maxHealth;
            }
            if (lives == 0)
            {
                shokStatus = DEAD;
            }

        }

    }


    public void healShok(int points)
    {
        if (shokStatus != DEAD)
        {
            if (health < maxHealth)
            {
                health = health + points;
            }

            if (health > maxHealth)
            {
                health = maxHealth;
            }
        }
    }

    void scroll()
    {
        if (scrollCounter > SCROLL_SPEED)
            scrollCounter -= SCROLL_SPEED;
        else
            scrollCounter = SCREEN_LIMIT_X;


        //scroll boxes
        for (int i=0;i<400;i++)
        {
            int xValue = boxes[i].getX();
            boxes[i].setX(xValue-10);
            

            if (xValue < 0)
            {
                boxes[i].setX(-100);
                boxes[i].setID(0);
            }

        }

        //scroll mombos
        for (int i=0;i<400;i++)
        {
            int xValue = mombos[i].getX();
            mombos[i].setX(xValue-10);


            if (xValue < 0)
            {
                mombos[i].setX(-100);
                mombos[i].setID(0);
            }

        }
    }

    public void animatePlayerCharacters()
    {
        //read keys and translate
        if (keyBuffer == KeyEvent.VK_A)
        {
            //pressed the attack button
            if (shokStatus != Game.ATTACKING)
            {
                Game.shokStatus = Game.ATTACKING;
                Game.shokAnimationFrame = 0;

                //clear key buffer
                keyBuffer = 0;
            }
        
        }
        if (keyBuffer == KeyEvent.VK_UP)
        {
            if ((shokJumpingStatus != Game.JUMPING) && (shokJumpingStatus != Game.FALLING))
            {
                Game.shokJumpingStatus = Game.JUMPING;
                Game.shokAnimationFrame = 0;

                keyBuffer = 0;
            }
        }

        if (keyBuffer == KeyEvent.VK_DOWN)
        {
            if (shokStatus != Game.DUCKING)
            {
                Game.shokStatus = Game.DUCKING;
                Game.shokAnimationFrame = 0;

                keyBuffer = 0;
            }
        }

        if (keyBuffer == KeyEvent.VK_LEFT)
        {
            if (shokStatus != WALKING_LEFT)
            {
                Game.shokStatus = WALKING_LEFT;
                Game.shokAnimationFrame = 0;

                keyBuffer = 0;
            }
        }

        if (keyBuffer == KeyEvent.VK_RIGHT)
        {
            if (shokStatus != WALKING_RIGHT)
            {
                Game.shokStatus = WALKING_RIGHT;
                Game.shokAnimationFrame = 0;

                keyBuffer = 0;
            }
        }


        //update frames

        if ((shokAnimationFrame >= Game.MAX_ANIMATION_FRAMES) && (shokStatus != Game.DEAD))
        {
            Game.shokStatus = Game.IDLE;
            Game.shokAnimationFrame = 0;
            //Game.shokY = shokStandingY;
            //Game.shokX = shokStandingX;
            //Game.shokHeight = shokStandingHeight;
            //Game.shokWidth = shokStandingWidth;
        }
        else if (shokStatus == Game.DEAD)
        {
            Game.shokAnimationFrame = 0;
        }
        else
        {
            Game.shokAnimationFrame++;
        }

        //reset keybuffer
        keyBuffer = 0;

    }

    void animateNonPlayerCharacters()
    {
        //animate void
        for (int i=0;i<MAX_VOIDS;i++)
        {
            if (monsterVoids[i].getActive() == true)
            {
                int frame = monsterVoids[i].getMonsterVoidFrame();
                if (frame < MAX_VOID_FRAMES)
                {
                    frame++;
                    monsterVoids[i].setMonsterVoidFrame(frame);

                }

                if (frame >= MAX_VOID_FRAMES)
                {
                    monsterVoids[i].setMonsterVoidFrame(0);
                    monsterVoids[i].setActive(false);
                }
                    
                //spawn void walkers
                if (voidWalker[i].getActive() == false)
                    {
                     
                     
                     
                     voidWalker[i].setActive(true);
                     voidWalker[i].setStatus(SPAWNING);
                     voidWalker[i].setX(monsterVoids[i].getX());
                     voidWalker[i].setY(monsterVoids[i].getY());
                   }
                 
                    //need to create a void walker if this happens
                }
            }
        

        //animate spawned void walker
        for (int i=0;i<MAX_WALKERS;i++)
        {
            if ((voidWalker[i].getActive() == true))
            {
                int frame = voidWalker[i].getMonsterFrame();
                if (frame < MAX_WALKER_FRAMES)
                {
                    frame++;
                    voidWalker[i].setMonsterFrame(frame);
                }
                if ((frame >= MAX_WALKER_FRAMES) && (voidWalker[i].getStatus() == DEAD))
                {
                    voidWalker[i].setMonsterFrame(0);
                    voidWalker[i].setActive(false);
                    
                }
                else if (frame >= MAX_WALKER_FRAMES)
                {
                    voidWalker[i].setMonsterFrame(0);
                    voidWalker[i].setStatus(IDLE);
                    
                }
            }
        }






    }


    public void drawScene()
    {
        createBufferStrategy(2);
        BufferStrategy myStrategy = getBufferStrategy();

        Graphics g = myStrategy.getDrawGraphics();
        render(g);
        g.dispose();
        myStrategy.show();

    }
    public void loadImages()
    {
        try
        {
            background1 = ImageIO.read(new File("graphics/background1.bmp"));
            floor1 = ImageIO.read(new File("graphics/floor1.bmp"));
            
            shokhurt = ImageIO.read(new File("graphics/shokhurt.png"));
            menubar = ImageIO.read(new File("graphics/menubar.png"));
            healthCross = ImageIO.read(new File("graphics/health.png"));
            extraLife = ImageIO.read(new File("graphics/extralife.png"));
            fireChameleon = ImageIO.read(new File("graphics/firechameleon.png"));
            fireball = ImageIO.read(new File("graphics/fireball.png"));
            iceChameleon = ImageIO.read(new File("graphics/icechameleon.png"));
            icedagger = ImageIO.read(new File("graphics/icedagger.png"));
            windChameleon = ImageIO.read(new File("graphics/windchameleon.png"));
            tornado = ImageIO.read(new File("graphics/tornado.png"));
            voidWalkerIdle = ImageIO.read(new File("graphics/voidwalker2.png"));
            mombo = ImageIO.read(new File("graphics/mombo.png"));
            
            //load shok attacking
            for (int i = 0;i<8;i++)
            {
                shokattack[i] = ImageIO.read(new File("graphics/shokattack"+i+".png"));
            }

            //load shok idle
            shokidle[0] = ImageIO.read(new File("graphics/shokidle0.png"));
            shokidle[1] = ImageIO.read(new File("graphics/shokidle1.png"));
            shokidle[2] = ImageIO.read(new File("graphics/shokidle1.png"));
            shokidle[3] = ImageIO.read(new File("graphics/shokidle2.png"));
            shokidle[4] = ImageIO.read(new File("graphics/shokidle0.png"));
            shokidle[5] = ImageIO.read(new File("graphics/shokidle1.png"));
            shokidle[6] = ImageIO.read(new File("graphics/shokidle1.png"));
            shokidle[7] = ImageIO.read(new File("graphics/shokidle2.png"));

            //load boxes
            for (int i=0;i<13;i++)
            {
                box[i] = ImageIO.read(new File("graphics/box"+i+".png"));
            }

            //load void
            for (int i=0;i<6;i++)
            {
                voidImage[i] = ImageIO.read(new File("graphics/void"+i+".png"));
            }
            //for (int i=0;i<6;i++)
            //{
            //    voidImage[6+i] = ImageIO.read(new File("void"+(6-i)+".png"));
            //}
            for (int i=0;i<MAX_WALKER_FRAMES;i++)
            {
                walkerSpawn[i] = ImageIO.read(new File("graphics/voidwalkerappear"+i+".png"));
            }

            for (int i=0;i<MAX_WALKER_FRAMES;i++)
            {
                walkerWalk[i] = ImageIO.read(new File("graphics/voidwalker"+i+".png"));
            }
            for (int i=0;i<MAX_WALKER_FRAMES;i++)
            {
                walkerDie[i] = ImageIO.read(new File("graphics/voidwalkerdie"+i+".png"));
            }
        }

        catch (IOException e)
        {
            System.out.println("IO Exception on image load");
            System.out.println(e.getMessage());
        }

    }

    ////////////BEGIN RENDER

    public void render(Graphics g)
    {
     
        //clear screen
        g.clearRect(0,0, SCREEN_LIMIT_X, SCREEN_LIMIT_Y);

        //draw background
        g.drawImage(background1, scrollCounter, 0, SCREEN_LIMIT_X, SCREEN_LIMIT_Y, 0, 0, SCREEN_LIMIT_X-scrollCounter, SCREEN_LIMIT_Y, null);
        g.drawImage(background1, 0, 0, scrollCounter, SCREEN_LIMIT_Y, 800-scrollCounter, 0, SCREEN_LIMIT_X, SCREEN_LIMIT_Y, null);
        
        //draw floor
        g.drawImage(floor1, scrollCounter, 400, SCREEN_LIMIT_X, SCREEN_LIMIT_Y, 0, 0, SCREEN_LIMIT_X-scrollCounter, SCREEN_LIMIT_Y/2, null);
        g.drawImage(floor1, 0, 400, scrollCounter, SCREEN_LIMIT_Y, SCREEN_LIMIT_X-scrollCounter, 0, SCREEN_LIMIT_X, SCREEN_LIMIT_Y/2, null);

        //draw menubar
        g.drawImage(menubar, 0, 0, 795, 179, null);
        g.setColor(Color.WHITE);
        Font menuFont = new Font("SansSerif", Font.BOLD, 12);
        g.setFont(menuFont);
        g.drawString("Next...", 50, 65);
        g.drawString("Health", 310, 65);
        g.drawString(health + "/" + maxHealth, 355, 65);
        g.drawString("Magic", 310, 90);
        g.drawString(magic + "/" + maxMagic, 355, 90);
        g.drawString("Lives", 310, 115);
        g.drawString(lives + "/" + maxLives, 355, 115);
        g.drawString("Score", 500, 65);
        g.drawString(score + "", 600, 65);
        g.drawString("Level", 500, 90);
        g.drawString(level + "", 600, 90);
        g.drawString("Chameleons", 500, 115);
        g.drawString(chameleons + "/" + maxChameleons, 600, 115);
        
        //status meters
            //health
        g.drawLine(150, 60, 300, 60);
            //magic
        g.drawLine(150, 85, 300, 85);
            //lives
        g.drawLine(150, 110, 300, 110);

        //fill in status meters
        g.setColor(Color.BLUE);
        float healthIndex = (float)health/maxHealth;
        float magicIndex = (float)magic/maxMagic;
        float livesIndex = (float)lives/maxLives;

        if (healthIndex < 0.35)
            g.setColor(Color.RED);
        else
            g.setColor(Color.BLUE);
        g.drawLine(150, 60, (int)(150+150*healthIndex), 60);
        
        if (magicIndex < 0.35)
            g.setColor(Color.RED);
        else
            g.setColor(Color.BLUE);
        g.drawLine(150, 85, (int)(150+150*magicIndex), 85);

        if (livesIndex < 0.35)
            g.setColor(Color.RED);
        else
            g.setColor(Color.BLUE);
        g.drawLine(150, 110, (int)(150+150*livesIndex), 110);

        
        //draw shok idle
        
        //draw shok attacking
        if (Game.shokStatus == ATTACKING)
        {
            g.drawImage(shokattack[shokAnimationFrame], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }

        //draw shok injured
        else if (Game.shokStatus == HURT)
        {
            g.drawImage(shokhurt, shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }

        //draw shok jumping
        
        else if (Game.shokJumpingStatus == JUMPING)
        {
            g.drawImage(shokattack[3], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }

        //draw shok falling

        else if (Game.shokJumpingStatus == FALLING)
        {
            g.drawImage(shokattack[3], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }

        //draw shok ducking

        else if (Game.shokStatus == DUCKING)
        {
            g.drawImage(shokattack[3], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }

        else if (Game.shokStatus == DEAD)
        {
            //g.drawImage(shokattack[3], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
            g.setColor(Color.RED);
            g.drawString("YOU DIED", shokX+sx, shokY+sy+50);
        }

        else if (Game.shokStatus == WALKING_RIGHT)
        {
            g.drawImage(shokidle[shokAnimationFrame], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }

        else if (Game.shokStatus == WALKING_LEFT)
        {
            g.drawImage(shokidle[shokAnimationFrame], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }

        else if (Game.shokStatus == IDLE)
        {
            g.drawImage(shokidle[shokAnimationFrame], shokX, shokY, shokX+shokWidth, shokY+shokHeight, 0, 0, 300, 300, null);
        }


        //draw boxes
        for (int i=0;i<400;i++)
        {
           if (boxes[i].isActive())
           {
            if (boxes[i].getX() < SCREEN_LIMIT_X)
            {
                int frame = boxes[i].getBoxFrame();

                if ((frame >= MAX_BOX_FRAMES) && (boxes[i].isBroken() == true))
                {
                    if (boxes[i].getContents() == HEALTH_CROSS)
                    {
                        g.drawImage(healthCross, boxes[i].getX(), boxes[i].getY(), null);
                    }
                    else if (boxes[i].getContents() == EXTRA_LIFE)
                    {
                        g.drawImage(extraLife, boxes[i].getX(), boxes[i].getY(), null);
                    }
                }

                if (boxes[i].getContents() == FIRE_CHAMELEON)
                    g.drawImage(fireChameleon, boxes[i].getX(), boxes[i].getY(), 50, 50, null);

                if (boxes[i].getContents() == ICE_CHAMELEON)
                    g.drawImage(iceChameleon, boxes[i].getX(), boxes[i].getY(), 50, 50, null);

                if (boxes[i].getContents() == WIND_CHAMELEON)
                    g.drawImage(windChameleon, boxes[i].getX(), boxes[i].getY(), 50, 50, null);

                g.drawImage( box[frame], boxes[i].getX(), boxes[i].getY(), null);

                g.setColor(Color.WHITE);
                //g.drawString("X:" + boxes[i].getX() + "," + boxes[i].getY(), boxes[i].getX(), boxes[i].getY());
                //g.drawString("ID:" + boxes[i].getID(), boxes[i].getX(), boxes[i].getY()+10);

            
            }

           }
        }

        //draw mombo berries
        for (int i=0;i<400;i++)
        {

            if (mombos[i].getX() < SCREEN_LIMIT_X)
            {


                if (mombos[i].isAte() == false)
                {

                g.drawImage(mombo, mombos[i].getX(), mombos[i].getY(), null);

                g.setColor(Color.WHITE);
                //g.drawString("X:" + mombos[i].getX() + "," + mombos[i].getY(), mombos[i].getX(), mombos[i].getY());
                //g.drawString("ID:" + mombos[i].getID(), mombos[i].getX(), mombos[i].getY()+10);

                }
            }
        }

        //draw next window
        if (next == BOX_TYPE)
        {
            g.drawImage(box[0], 47, 83, null);
        }

        else if (next == FIRE_CHAMELEON)
        {
            g.drawImage(fireChameleon, 35, 65, null);
        }
        else if (next == ICE_CHAMELEON)
        {
            g.drawImage(iceChameleon, 35, 65, null);
        }

        else if (next == WIND_CHAMELEON)
        {
            g.drawImage(windChameleon, 35, 65, null);
        }

        //draw collision detection rectangle for Shok
        //g.drawString((shokX+sx)+","+(shokY+sy), shokX+sx, shokY+sy);
        //g.drawString((shokWidth-swx) +","+(shokHeight-syh), shokX+sx, shokY+sy+10);
        //g.drawString((shokX+sx+shokWidth-swx) + ","+(shokY+sy+shokHeight-syh),shokX+sx+shokWidth-swx,shokY+sy+shokHeight-syh );
        //g.drawRect(shokX+sx, shokY+sy, shokWidth-swx, shokHeight-syh);

        // draw chameleons
        if (hasFireChameleon)
            g.drawImage(fireChameleon, 10, 170, null);

        if (hasIceChameleon)
            g.drawImage(iceChameleon, 10, 240, null);

        if (hasWindChameleon)
            g.drawImage(windChameleon, 10, 310, null);

        //draw fireballs
        for (int i=0;i<MAX_FIREBALLS;i++)
        {
            if (fireballs[i].getActive())
            {
               g.drawImage(fireball, fireballs[i].getX(), fireballs[i].getY(), null);
            }
        }
        //draw icedaggers
        for (int i=0;i<MAX_ICEDAGGERS;i++)
        {
            if (icedaggers[i].getActive())
            {
               g.drawImage(icedagger, icedaggers[i].getX(), icedaggers[i].getY(), null);
            }
        }

        //draw tornados
        for (int i=0;i<MAX_TORNADOS;i++)
        {
            if (tornados[i].getActive())
            {
               g.drawImage(tornado, tornados[i].getX(), tornados[i].getY(), null);
            }
        }

        //draw voids
        for (int i=0;i<MAX_VOIDS;i++)
        {
            if ((monsterVoids[i].getActive()) && (voidWalker[i].getActive()))
            {
                
                //grab frame
                int frame = monsterVoids[i].getMonsterVoidFrame();
                g.drawImage(voidImage[frame], monsterVoids[i].getX(), monsterVoids[i].getY(),100, 100,  null);
            }
        }
        for (int i=0;i<MAX_WALKERS;i++)
        {
            if (voidWalker[i].getActive())
            {
                if (voidWalker[i].getStatus() == SPAWNING)
                {
                    int frame = voidWalker[i].getMonsterFrame();
                    g.drawImage(walkerSpawn[frame], voidWalker[i].getX(), voidWalker[i].getY(),  100,  100, null);
                    
                }
                if ((voidWalker[i].getStatus() == IDLE))
                {
                    
                    g.drawImage(voidWalkerIdle, voidWalker[i].getX(), voidWalker[i].getY(), 100,  100, null);
                }   
                if ((voidWalker[i].getStatus() == WALKING))
                {
                    int frame = voidWalker[i].getMonsterFrame();
                    g.drawImage(walkerWalk[frame], voidWalker[i].getX(), voidWalker[i].getY(), 100,  100, null);
                   
                }
                if ((voidWalker[i].getStatus() == DEAD))
                {
                    int frame = voidWalker[i].getMonsterFrame();
                    g.drawImage(walkerDie[frame], voidWalker[i].getX(), voidWalker[i].getY(), 100,  100, null);
                    
                }
               // g.drawRect(voidWalker[i].getX(), voidWalker[i].getY(), 100, 100);

            }
        }

    }

    ///////////////END RENDER

    public void initializeFireballs()
    {
        for (int i = 0; i<MAX_FIREBALLS; i++)
        {
            fireballs[i] = new Fireball();
            fireballs[i].setActive(false);
        }
    }

    public void initializeIcedaggers()
    {
        for (int i = 0; i<MAX_ICEDAGGERS; i++)
        {
            icedaggers[i] = new Icedagger();
            icedaggers[i].setActive(false);
        }
     }

    public void initializeTornados()
    {
        for (int i = 0; i<MAX_TORNADOS; i++)
        {
            tornados[i] = new Tornado();
            tornados[i].setActive(false);
        }
     }


    public void initializeMonsterVoid()
    {
        for (int i=0; i<MAX_VOIDS;i++)
        {
            monsterVoids[i] = new MonsterVoid();
            monsterVoids[i].setActive(false);
        }
        for (int i=0; i<MAX_WALKERS;i++)
        {
            voidWalker[i] = new VoidWalker();
            voidWalker[i].setActive(false);
        }

    }

    public void initializeBoxes()
    {
        int boxctr = 1;
        for (int i = 0;i<400;i++)
        {
            boxes[i] = new Box();
            int randomX = (int)(i*1000*Math.random());
            int randomY = (int)(400*Math.random()+200);
            int randomContents = (int)(100*Math.random());
            int probability = (int)(100*Math.random());
            if (probability > BOX_PROBABILITY)
            {
                boxes[i].setActive(false);
                
            }
            else
            {
                boxes[i].setActive(true);
            }
            if ((randomX > 300) && (randomY > 200) && (randomY < 600))
            {
                    boxes[i].setID(boxctr);
                    boxes[i].setX(randomX);
                    boxes[i].setY(randomY);
                    
                    boxctr++;

            }
            else
            {
                boxes[i].setID(0);
            }
            if (randomContents < 10)
            {
                boxes[i].setContents(HEALTH_CROSS);
            }
            else if ((randomContents >= 10) && (randomContents < 20))
            {
                boxes[i].setContents(EXTRA_LIFE);
            }

            else if ((randomContents >= 20) && (randomContents < 30))
            {
                boxes[i].setContents(FIRE_CHAMELEON);
            }
            else if ((randomContents >= 30) && (randomContents < 40))
            {
                boxes[i].setContents(ICE_CHAMELEON);
            }

            else if ((randomContents >= 40) && (randomContents < 50))
            {
                boxes[i].setContents(WIND_CHAMELEON);
            }
            
        }

    }


    public void initializeMombos()
    {
        int momboctr = 1;
        for (int i = 0;i<400;i++)
        {
            mombos[i] = new Mombo();
            int randomX = (int)(i*1000*Math.random());
            int randomY = (int)(400*Math.random()+200);
            int randomContents = (int)(100*Math.random());
            if ((randomX > 300) && (randomY > 200) && (randomY < 600))
            {
                    mombos[i].setID(momboctr);
                    mombos[i].setX(randomX);
                    mombos[i].setY(randomY);

                    momboctr++;

            }
            else
            {
                mombos[i].setID(0);
            }
        }
    }
}
