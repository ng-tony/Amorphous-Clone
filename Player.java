// The "Tank" class.
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;

public final class Player extends Blob //implements Runnable
{
    private BufferedImage images[];
    private int spriteCounter;
    boolean commands[];
    Thread t;
    public Player (int b[], int ang, Game g, String[] img)
    {
	spriteCounter = 0;
	tag = GameRule.PLAYER;
	commands = new boolean [5];
	for (int i = 0 ; i < 5 ; i++)
	{
	    commands [i] = false;
	}
	boundBox = b;
	xLoc = 0;
	yLoc = 0;
	xDrawLoc = b [3] - 24;
	yDrawLoc = b [0] - 24;
	rot = ang;
	images = new BufferedImage [img.length];
	try
	{
	    for (int i = 0 ; i < img.length ; i++)
	    {
		images [i] = ImageIO.read (new File (img [i]));
	    }
	}
	catch (Exception e)
	{
	}
	;
	/*t = new Thread(this);
	t.start();*/
    }


    public void sendCommand (int command)
    {
	commands [command] = true;

    }


    public void revokeCommand (int command)
    {
	commands [command] = false;
    }


    public void calculateControl ()
    {
	int xMov = 0;
	int yMov = 0;
	if (commands [0])
	{
	    yMov--;
	}
	if (commands [1])
	{
	    yMov++;
	}
	if (commands [2])
	{
	    xMov--;
	}
	if (commands [3])
	{
	    xMov++;
	}
	if (xMov != 0 || yMov != 0)
	{
	    rot = (int) Math.toDegrees (Math.atan (((double) yMov) / ((double) xMov)));
	    if (xMov > 0)
	    {
		if (yMov == 1)
		{
		    rot = 45;
		}
		else if (yMov == 0)
		{
		    rot = 0;
		}
		else if (yMov == -1)
		{
		    rot = -45;
		}
	    }
	    else if (xMov == 0)
	    {
		if (yMov == 1)
		{
		    rot = 90;
		}
		else if (yMov == -1)
		{
		    rot = -90;
		}
	    }
	    else if (xMov < 0)
	    {
		if (yMov == 1)
		{
		    rot = 135;
		}
		else if (yMov == 0)
		{
		    rot = 180;
		}
		else if (yMov == -1)
		{
		    rot = 225;
		}
	    }
	    xAccel = Math.cos (Math.toRadians (rot)) * GameRule.PLAYER_ACCEL;
	    yAccel = Math.sin (Math.toRadians (rot)) * GameRule.PLAYER_ACCEL;
	}
	else
	{
	    xAccel = 0;
	    yAccel = 0;
	}
	/*double h = Math.sqrt((double)(xMov*xMov + yMov*yMov))
	xVel = Math.acos( ((double)xMov) / h)) * GameRule.PLAYER_MOVESPEED;
	yVel = Math.asin( ((double)yMov) / h)) * GameRule.PLAYER_MOVESPEED;*/ //Calculation way of finding velocity
    }


    public void calculateTurn ()
    {
	calculateControl ();

	if ((int) xAccel == 0 && xVel != 0)
	{
	    xVel -= xVel / Math.abs (xVel) * GameRule.PLAYER_STOPSPEED;
	    if (Math.abs (xVel) <= Math.abs (GameRule.PLAYER_STOPSPEED))
	    {
		xVel = 0;
	    }
	}
	if (xAccel != 0)
	{
	    xVel += xAccel;
	    if (Math.abs (xVel) > Math.abs (Math.cos (Math.toRadians (rot)) * GameRule.PLAYER_MOVESPEED))
	    {
		xVel = Math.cos (Math.toRadians (rot)) * GameRule.PLAYER_MOVESPEED;
	    }
	}

	if ((int) yAccel == 0 && yVel != 0)
	{
	    yVel -= yVel / Math.abs (yVel) * GameRule.PLAYER_STOPSPEED;
	    if (Math.abs (yVel) <= Math.abs (GameRule.PLAYER_STOPSPEED))
	    {
		yVel = 0;
	    }
	}
	if (yAccel != 0)
	{
	    yVel += yAccel;
	    if (Math.abs (yVel) > Math.abs (Math.sin (Math.toRadians (rot)) * GameRule.PLAYER_MOVESPEED))
	    {
		yVel = Math.sin (Math.toRadians (rot)) * GameRule.PLAYER_MOVESPEED;
	    }
	}
	boundBox [Consts.NORTH] += yVel;
	boundBox [Consts.SOUTH] += yVel;
	boundBox [Consts.EAST] += xVel;
	boundBox [Consts.WEST] += xVel;

	//System.out.println(xVel + " " + yVel + " " +Math.sqrt(xVel*xVel + yVel*yVel));
    }


    public BufferedImage draw ()
    {
	xDrawLoc = boundBox [Consts.EAST] - 68;
	yDrawLoc = boundBox [Consts.NORTH] - 24;
	image = images [(int) ((spriteCounter++ / GameRule.DANNY_SPRITE_DELAY) % images.length)];
	BufferedImage newImage = new BufferedImage (image.getWidth () + image.getHeight (),
		image.getWidth () + image.getHeight (),
		BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = (Graphics2D) newImage.getGraphics ();
	AffineTransform at = new AffineTransform ();
	at.rotate (Math.toRadians (rot - 90), newImage.getWidth () / 2, newImage.getHeight () / 2);
	at.translate (newImage.getWidth () / 2 - image.getWidth () / 2, newImage.getHeight () / 2 - image.getHeight () / 2);
	AffineTransformOp op = new AffineTransformOp (at, AffineTransformOp.TYPE_BILINEAR);
	g.drawImage (op.filter (image, null), 0, 0, null);
	g.dispose ();

	//xDrawLoc = xLoc - newImage.getWidth()/2 + image.getWidth()/2;
	//yDrawLoc = yLoc - newImage.getHeight()/2 + image.getHeight()/2;

	return newImage; //Return rotated image
    }


    public boolean onCollision (int id, int dir)
    {
	//System.out.println("Tank Collide");
	//System.out.println(id);
	if (id == Consts.WALL)
	{
	    if (dir == Consts.LEFTCOLLIDE)
	    {
		xLoc += Consts.COLLIDEPUSH;
		xAccel = 0;
		xVel = Consts.WALLSLOW * xVel;
		//System.out.println("Left");
	    }
	    else if (dir == Consts.RIGHTCOLLIDE)
	    {
		xLoc -= Consts.COLLIDEPUSH;
		xAccel = 0;
		xVel = Consts.WALLSLOW * xVel;
		//System.out.println("Right");
	    }
	    else if (dir == Consts.UPCOLLIDE)
	    {
		yLoc += Consts.COLLIDEPUSH;
		yAccel = 0;
		yVel = Consts.WALLSLOW * yVel;
		//System.out.println("Up");
	    }
	    else if (dir == Consts.DOWNCOLLIDE)
	    {
		yLoc -= Consts.COLLIDEPUSH;
		yAccel = 0;
		yVel = Consts.WALLSLOW * yVel;
		//System.out.println("Down");
	    }
	    return false;
	}
	return false;
    }


    public boolean onCollision (Blob blob)
    {
	int xBound1 = boundBox [3];
	int xBound2 = boundBox [2];
	int yBound1 = boundBox [0];
	int yBound2 = boundBox [1];

	int kxBound1 = blob.boundBox [3];
	int kxBound2 = blob.boundBox [2];
	int kyBound1 = blob.boundBox [0];
	int kyBound2 = blob.boundBox [1];

	if (blob.tag == GameRule.WALL)
	{
	    //Bump back


	    int hits[] = { - this.boundBox [0] + blob.boundBox [1], this.boundBox [1] - blob.boundBox [0],
		this.boundBox [2] - blob.boundBox [3], -this.boundBox [3] + blob.boundBox [2] };
	    /*int max = 0;
	    int maxi = 0;
	    for(int i = 0; i < hits.length; i++)
	    {
		if(hits[i] > max)
		{
		    max = hits[i];
		    maxi = i;
		}
	    }*/
	    /*if (rot != 45 && rot != 135 && rot != 225 && rot != -45)
	    {*/
		if (hits [0] > 0 && commands [0])
		{
		    yVel = 0;
		    yAccel = 0;
		    boundBox [Consts.NORTH] += GameRule.PUSH;
		    boundBox [Consts.SOUTH] += GameRule.PUSH;
		}
		if (hits [1] > 0 && commands [1])
		{
		    yVel = 0;
		    yAccel = 0;
		    boundBox [Consts.NORTH] -= GameRule.PUSH;
		    boundBox [Consts.SOUTH] -= GameRule.PUSH;
		}
		if (hits [2] > 0 && commands [3])
		{
		    xVel = 0;
		    xAccel = 0;
		    boundBox [Consts.EAST] -= GameRule.PUSH;
		    boundBox [Consts.WEST] -= GameRule.PUSH;
		}
		if (hits [3] > 0 && commands [2])
		{
		    xVel = 0;
		    xAccel = 0;
		    boundBox [Consts.EAST] += GameRule.PUSH;
		    boundBox [Consts.WEST] += GameRule.PUSH;
		}
	    /*}
	    else
	    {
		if(hits[0] > 0)
		{
		    if(hits[2] > 0)
		    {
			if(hits[0] < hits[2])
			{
			    yVel = 0;
			    yAccel = 0;
			    boundBox [Consts.NORTH] += GameRule.PUSH;
			    boundBox [Consts.SOUTH] += GameRule.PUSH;
			}
			else
			{
			    xVel = 0;
			    xAccel = 0;
			    boundBox [Consts.EAST] += GameRule.PUSH;
			    boundBox [Consts.WEST] += GameRule.PUSH;
			}
		    }
		    else if(hits[3] > 0)
		    {
			if(hits[0] < hits[3])
			{
			    yVel = 0;
			    yAccel = 0;
			    boundBox [Consts.NORTH] += GameRule.PUSH;
			    boundBox [Consts.SOUTH] += GameRule.PUSH;
			}
			else
			{
			    xVel = 0;
			    xAccel = 0;
			    boundBox [Consts.EAST] -= GameRule.PUSH;
			    boundBox [Consts.WEST] -= GameRule.PUSH;
			}
		    }
		}
		else if(hits[1] >0)
		{
		    if(hits[2] > 0)
		    {
			if(hits[1] < hits[2])
			{
			    yVel = 0;
			    yAccel = 0;
			    boundBox [Consts.NORTH] -= GameRule.PUSH;
			    boundBox [Consts.SOUTH] -= GameRule.PUSH;
			}
			else
			{
			    xVel = 0;
			    xAccel = 0;
			    boundBox [Consts.EAST] += GameRule.PUSH;
			    boundBox [Consts.WEST] += GameRule.PUSH;
			}
		    }
		    else if(hits[3] > 0)
		    {
			if(hits[1] < hits[3])
			{
			    yVel = 0;
			    yAccel = 0;
			    boundBox [Consts.NORTH] -= GameRule.PUSH;
			    boundBox [Consts.SOUTH] -= GameRule.PUSH;
			}
			else
			{
			    xVel = 0;
			    xAccel = 0;
			    boundBox [Consts.EAST] -= GameRule.PUSH;
			    boundBox [Consts.WEST] -= GameRule.PUSH;
			}
		    }
		}
	    }*/

	    /*if (maxi == 0) //Top is hitting
	    {
		yVel = 0;
		yAccel = 0;
		boundBox [Consts.NORTH] += GameRule.PUSH;
		boundBox [Consts.SOUTH] += GameRule.PUSH;
		yDrawLoc = boundBox [Consts.NORTH] - 24;
	    }
	    if (maxi == 1) //Botton is hitting
	    {
		yVel = 0;
		yAccel = 0;
		boundBox [Consts.NORTH] -= GameRule.PUSH;
		boundBox [Consts.SOUTH] -= GameRule.PUSH;
		yDrawLoc = boundBox [Consts.NORTH] - 24;
	    }
	    if (maxi == 2) //Right is hitting
	    {
		xVel = 0;
		xAccel = 0;
		boundBox [Consts.EAST] -= GameRule.PUSH;
		boundBox [Consts.WEST] -= GameRule.PUSH;
		xDrawLoc = boundBox [Consts.EAST] - 24;
	    }
	    if (maxi == 3) // Left is hitting
	    {
		xVel = 0;
		xAccel = 0;
		boundBox [Consts.EAST] += GameRule.PUSH;
		boundBox [Consts.WEST] += GameRule.PUSH;
		xDrawLoc = boundBox [Consts.EAST] - 24;
	    }*/
	    return false;
	}
	else if (blob.tag == GameRule.PLAYER)
	{
	    //what
	    return false;
	}
	else if (blob.tag == GameRule.SWORD)
	{
	    //Do nothing
	    return false;
	}
	else if (blob.tag == GameRule.ENEMY)
	{
	    //Should explode
	    return true;
	}
	return false;
    } //Boolean indicates if deleted


    /*public void run()
    {
	while(true)
	{
	    try{Thread.sleep(Consts.CALCFPS);}catch(Exception e){};
	    this.calculateControl();
	}
    }*/

} // Tank class


