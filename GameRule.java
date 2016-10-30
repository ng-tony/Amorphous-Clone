/*
Programmed by: Tony Ng
Last Modified: 02/06/2014
Purpose: Holds all the constants for easy access and organization
*/
import java.awt.Font; 
interface GameRule
{
    //Predefined Constants
    public static final int WALL                = 1;
    public static final int PLAYER              = 2;
    public static final int SWORD               = 3;
    public static final int ENEMY               = 4;

    //public static final int PLAYER_BOX[]        = {200,248,248,200};
    public static final int PLAYER_BOX[]        = {140,180,340,300};
    public static final double PLAYER_ACCEL     = 2;
    public static final double PLAYER_STOPSPEED = 5;
    public static final double PLAYER_MOVESPEED = 6;
    
    public static final int DELETE_BOUNDARY      = 200;

    public static final int KEYSPERCLASS      = 4; //Keys in a Keys Class
    
    public static final int CALCFPS           = 30; //Calculation Period, in ms

    public static final Wall WALLS[]          = {new Wall(  0,   43,  260, 0), new Wall( 0,   104, 800,  261), new Wall(106,   800, 800, 787),
						 new Wall( 713, 759,  786,  738), new Wall(758,   800,  478, 570),
						 new Wall( 761, 800, 612,  573), new Wall(753, 800,  570, 121), 
						 new Wall(712, 751, 122,  0), new Wall(673,   701,  120, 39), new Wall(287, 711,  38,  0),
						 new Wall(167, 284,  30,  0), new Wall(110,  166,  21,  0), new Wall(44,  109, 30,  0),
						 new Wall(191,248,237,178), new Wall(338,338+83,330+90,330), new Wall(593,593+67,264+174,264),
						 new Wall(268,268+276,615+98,615)
						};
						
    public static final String KEYFILE       = "Keys.data";
    
    public static final int SWORD_BOUND      = 144;
    public static final int PUSH                = 3;
    public static final int MOUSE_UPDATE      = 3;
    public static final int SWORD_TIMER = 10;
    public static final int ENEMY_SIZE = 10;

    
    public static final String IMG_MAIN       = "images/MainMenu.JPG";
    public static final String IMG_OPTIONS    = "images/OptionsMenu.JPG";
    public static final String IMG_CONTROLS   = "images/ControlsMenu.JPG";
    public static final String IMG_CREDITS    = "images/CreditsMenu.JPG";
    public static final String IMG_PLAYMENU   = "images/PlayMenu.JPG";
    
    public static final String IMG_ENEMY   = "images/ENEMY.PNG";
    public static final String IMG_BLANK    = "images/Blank.PNG";
    public static final String IMG_TARGET   = "images/Target.PNG";
    public static final String[] IMG_DANNY   = {"images/Danny1.PNG", "images/Danny2.PNG", "images/Danny3.PNG",
						"images/Danny2.PNG", "images/Danny1.PNG",
						"images/Danny4.PNG", "images/Danny5.PNG", "images/Danny4.PNG"};
    public static final double    DANNY_SPRITE_DELAY = 2.5;
    
    public static final Font BUTTONFONT = new Font("Courier", Font.ITALIC, 13);
}
