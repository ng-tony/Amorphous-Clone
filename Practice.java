import java.awt.Font; 
/*
Programmed by: Tony Ng
Last Modified: 02/06/2014
Purpose: Defines Practice mode variables
*/
interface Practice
{
    public static final int XTANK             = 75; //Tank x starting location
    public static final int YTANK             = 275; //Tank y starting location
    public static final int ANGLETANK         = 0;
    public static final Wall WALLS[]          = {new Wall(  0,   0,  28, 589), new Wall( 29,   0, 643,  28), new Wall(672,   0,  28, 589),
						 new Wall( 29, 644,  28,  28), new Wall(673,   0,  28, 589), new Wall( 27,  64,  28, 137),
						 new Wall( 29, 402,  50, 162), new Wall( 80,  25, 140,  20), new Wall(145, 190,  49, 190), 
						 new Wall(113, 547, 102,  20), new Wall(673,   0,  28, 589), new Wall(250, 475,  49,  92),
						 new Wall(252, 182,  36,  70), new Wall(261,  43,  36,  70), new Wall(376,  33, 208,  33),
						 new Wall(372, 158, 247,  35), new Wall(323, 322,  44,  88), new Wall(426, 348, 170,  44),
						 new Wall(  0, 566, 701,  24)
						};
    public static final String IMG_BACKGROUND = "images/PracticeBackground.JPG";
    public static final String IMG_TANK = "images/tank1.PNG";
    public static final String IMG_TANK2 = "images/tank2.PNG";
}    
    
