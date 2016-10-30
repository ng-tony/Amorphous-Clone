/*
Programmed by: Tony Ng
Last Modified: 02/06/2014
Purpose: Holds all the constants for easy access and organization
*/
import java.awt.Font; 
interface Consts
{
    //Predefined Constants
    public static final int XSCREENSIZE       = 800;
    public static final int YSCREENSIZE       = 800;
    
    public static final int NORTH  = 0;
    public static final int SOUTH  = 1;
    public static final int EAST   = 2;
    public static final int WEST   = 3;

    public static final int WALL              = 1;
    public static final int PLAYER            = 2;

    //Directions that they were collided with
    public static final int LEFTCOLLIDE       = 1;
    public static final int RIGHTCOLLIDE      = 2;
    public static final int UPCOLLIDE         = 3;
    public static final int DOWNCOLLIDE       = 4;
    
    public static final int COLLIDEPUSH       = 1; //The bump from hitting the wall 
    public static final double WALLSLOW       = 0.25; //Lower is higher, zero is not acceptable
    
    public static final int MENUMAIN          = 1;
    public static final int MENUOPTIONS       = 2;
    public static final int MENUCREDITS       = 3;
    
    public static final int KEYSPERCLASS      = 4; //Keys in a Keys Class
    
    public static final int CALCFPS           = 30; //Calculation Period, in ms


    public static final int XBULLETSIZE       = 10;
    public static final int YBULLETSIZE       = 10;
    public static final int BULLETCOOLDOWN    = 1; //In frames  
    public static final int MAX_BOUNCE        = 5;
    public static final double BULLETVELOCITY = 12;   
    
    public static final int TARGETXSIZE       = 40;
    public static final int TARGETYSIZE       = 40;
    
    public static final String KEYFILE       = "Keys.data";
    
    public static final String IMG_MAIN       = "images/MainMenu.JPG";
    public static final String IMG_OPTIONS    = "images/OptionsMenu.JPG";
    public static final String IMG_CONTROLS   = "images/ControlsMenu.JPG";
    public static final String IMG_CREDITS    = "images/CreditsMenu.JPG";
    public static final String IMG_PLAYMENU   = "images/PlayMenu.JPG";
    
    public static final String IMG_BULLET   = "images/Bullet.PNG";
    public static final String IMG_BLANK    = "images/Blank.PNG";
    public static final String IMG_TARGET   = "images/Target.PNG";
    
    
    public static final Font BUTTONFONT = new Font("SansSerif", Font.PLAIN, 500);
}
