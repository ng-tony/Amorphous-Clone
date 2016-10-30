// The "Tank" class.
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;

public class Enemy extends Blob //implements Runnable
{
    Player player;
    int accelCD = 0;

    public Enemy (int a, int b, String img, Player s)
    {
	tag = GameRule.ENEMY;
	player = s;
	//boundBox = b;
	xLoc = 0;
	yLoc = 0;
	xDrawLoc = player.xDrawLoc;
	yDrawLoc = player.yDrawLoc;
	yVel = 0;
	xVel = 0;
	xAccel = 0;
	yAccel = 0;
	rot = 0;
	boundBox [Consts.NORTH] = a;
	boundBox [Consts.SOUTH] = a + GameRule.ENEMY_SIZE;
	boundBox [Consts.WEST] = b;
	boundBox [Consts.EAST] = b + GameRule.ENEMY_SIZE;
	

	try
	{
	    image = ImageIO.read (new File (img));
	}
	catch (Exception e)
	{
	}
	;
	/*t = new Thread(this);
	t.start();*/
    }


    public void calculateTurn ()
    {
	int dX = (player.boundBox[Consts.WEST]-boundBox[Consts.WEST]);
	xAccel = 0;
	yAccel = 0;
	int dY = (player.boundBox[Consts.NORTH]-boundBox[Consts.NORTH]);
	double angle = Math.toRadians(90);
	if(dX != 0.0)
	{
	    angle = Math.atan(((double)player.boundBox[Consts.NORTH]-(double)boundBox[Consts.NORTH])/((double)player.boundBox[Consts.WEST]-(double)boundBox[Consts.WEST]));
	    //System.out.println("ANGLE: " + Math.toDegrees(angle));
	    }
	double accel = 1.0;

	if(accelCD > 0)
	{
	    xAccel = 0;
	    yAccel = 0;
	    accel = 0;
	    accelCD--;
	}
	double vel = Math.sqrt(xVel*xVel + yVel*yVel);
	if (Math.abs (accel) < 0.05)
	{
	    accel = 0;
	}
	if (vel !=0.0)
	{
	    accel = (accel - (vel / Math.abs (vel) * Math.abs (vel) * 0.1));
	}
       // System.out.println("ACCEL " + accel); 
	xAccel =  Math.cos(angle)*accel;
	yAccel =  Math.sin(angle)*accel;
	
	xVel += xAccel;
	yVel += yAccel;
	if (Math.abs (xVel) < 0.02)
	{
	    xVel = 0;
	}
	if (Math.abs (yVel) < 0.02)
	{
	    yVel = 0;
	}
	if(dX < 0)
	{
	    xVel*=-1;
	}
	if(dY < 0)
	{
	    yVel*=-1;
	}
	
	boundBox [Consts.NORTH] += yVel;
	boundBox [Consts.SOUTH] += yVel;
	boundBox [Consts.EAST] += xVel;
	boundBox [Consts.WEST] += xVel;
	
	xDrawLoc = boundBox [Consts.EAST];
	yDrawLoc = boundBox [Consts.NORTH];
    }


    public BufferedImage draw ()
    {
	BufferedImage newImage = new BufferedImage (image.getWidth () + image.getHeight (),
		image.getWidth () + image.getHeight (),
		BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = (Graphics2D) newImage.getGraphics ();
	AffineTransform at = new AffineTransform ();
	at.rotate (Math.toRadians (rot), newImage.getWidth () / 2, newImage.getHeight () / 2);
	at.translate (newImage.getWidth () / 2 - image.getWidth () / 2, newImage.getHeight () / 2 - image.getHeight () / 2);
	AffineTransformOp op = new AffineTransformOp (at, AffineTransformOp.TYPE_BILINEAR);
	g.drawImage (op.filter (image, null), 0, 0, null);
	g.dispose ();

	//xDrawLoc = xLoc - newImage.getWidth()/2 + image.getWidth()/2;
	//yDrawLoc = yLoc - newImage.getHeight()/2 + image.getHeight()/2;

	return newImage; //Return rotated image
    }


    public boolean onCollision (Blob blob)
    {

	//if (checkSwordCollision (blob))
	//{
	double damp = -0.1;
	if (blob.tag == GameRule.WALL)
	{
	    if (boundBox [Consts.NORTH] > blob.boundBox [Consts.NORTH])
	    {
		//Top is inside
		if (boundBox [Consts.SOUTH] < blob.boundBox [Consts.SOUTH])
		{
		    xVel*=damp;
		}
		else
		{
		    //Reflect Y
		    yVel*=damp;
		}
	    }
	    else
	    {
		//Top collision //Might be corner too though
		//Reflect Y
		yVel*=damp;
	    }
	    if (boundBox [Consts.WEST] > blob.boundBox [Consts.WEST])
	    {
		//left is inside
		if (boundBox [Consts.EAST] < blob.boundBox [Consts.EAST])
		{
		    //reflect Y
		    yVel*=damp;
		}
		else
		{
		    //reflect X
		    xVel*=damp;
		}

	    }
	    else
	    {
		//Reflect X
		xVel*=damp;
	    }
	    accelCD = 10;
	}

	else if (blob.tag == GameRule.PLAYER)
	{
	    return true;
	}


	else if (blob.tag == GameRule.SWORD)
	{
	    Sword sword = (Sword)blob;
	    if(sword.checkSwordCollision(this))
	    {
		return true;
	    }
	    return false;
	}


	else if (blob.tag == GameRule.ENEMY)
	{
	    //
	}


	//}
	return false;
    }


    




    /*public void run()
    {
	while(true)
	{
	    try{Thread.sleep(Consts.CALCFPS);}catch(Exception e){};
	    this.calculateControl();
	}
    }*/

} // Tank class
