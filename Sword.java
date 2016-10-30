// The "Tank" class.
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;

public class Sword extends Blob //implements Runnable
{
    boolean commands[];
    Player player;
    int xTip, yTip;
    int xThether, yThether;
    int swordLength = 62;
    int mouseCounter = 0;
    int oldMouseX, newMouseX;
    int oldMouseY, newMouseY;
    int wallTimer = 0;
    int accelTimeOut = 0;
    int points = 0;
    double angAccel = 0;
    double oldMouseAngle = 0;
    int rot;
    Thread t;
    public Sword (int ang, String img, Player s)
    {

	tag = GameRule.SWORD;
	player = s;
	//boundBox = b;
	xLoc = 0;
	yLoc = 0;
	xDrawLoc = player.xDrawLoc;
	yDrawLoc = player.yDrawLoc;
	rot = ang;
	xThether = (int) ((player.boundBox [2] + player.boundBox [3]) / 2);
	yThether = (int) ((player.boundBox [0] + player.boundBox [1]) / 2);
	xTip = xThether + swordLength;
	yTip = yThether;
	newMouseX = oldMouseX = newMouseY = newMouseX = 0;
	angVel = 0;
	accelTimeOut = 0;

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
	xThether = (player.boundBox [3] + 24);
	yThether = (player.boundBox [0] + 24);
	double angle = 0;
	double changeInRot = 0;
	if ((oldMouseX - newMouseX) != 0.0 && (oldMouseY - newMouseY) != 0.0)
	{
	    double dist12 = Math.sqrt (Math.pow (xThether - oldMouseX, 2) + Math.pow (yThether - oldMouseY, 2));
	    double dist13 = Math.sqrt (Math.pow (xThether - newMouseX, 2) + Math.pow (yThether - newMouseY, 2));
	    double dist23 = Math.sqrt (Math.pow (oldMouseX - newMouseX, 2) + Math.pow (oldMouseY - newMouseY, 2));

	    angle = Math.abs (Math.acos ((Math.pow (dist12, 2) + Math.pow (dist13, 2) - Math.pow (dist23, 2)) / (2 * dist12 * dist13)));

	    //double y = (double)newMouseY + (double)oldMouseY - yThether;
	    //double x = (double)newMouseX + (double)oldMouseX - xThether;
	    //angle =  Math.atan(Math.abs(y) /Math.abs(x));

	    changeInRot = Math.abs (angle - oldMouseAngle);
	    oldMouseAngle = angle;

	}
	angAccel = (changeInRot) * 0.05;
	//System.out.println(changeInRot);
	/*if(Math.abs(angAccel) > 0)
	{
	    angAccel = angAccel/ 2;
	    if( Math.abs(angAccel) < 0.5)
	    {
		angAccel = 0;
	    }
	}*/

	if (Math.abs (angAccel) < 0.001)
	{
	    angAccel = 0;
	}
	if(accelTimeOut > 0)
	{
	    accelTimeOut--;
	    angAccel = 0;
	}
  
	//System.out.println( angVel +"\t" +angAccel + "\t" + (angAccel/Math.abs(angAccel) * Math.abs(angVel)*0.01) + "\t"  + (angAccel - (angAccel/Math.abs(angAccel) * Math.abs(angVel)*0.01)));
	if (angVel != 0.0)
	{
	    angAccel = (angAccel - (angVel / Math.abs (angVel) * Math.abs (angVel) * 0.1));
	}
	//System.out.println(angVel);
	if (Math.abs (angVel) < 0.02)
	{
	    angVel = 0;
	}
	angVel += angAccel;
	//rot += Math.toDegrees(angVel);
	rot += Math.toDegrees (angVel);

	//dSystem.out.println (Math.toDegrees (rot));
	xTip = xThether + (int) (Math.cos (Math.toRadians (rot)) * swordLength);
	yTip = yThether + (int) (Math.sin (Math.toRadians (rot)) * swordLength);
	oldMouseX = newMouseX;
	oldMouseY = newMouseY;
	xDrawLoc = player.boundBox [Consts.WEST] - 144 + 24;
	yDrawLoc = player.boundBox [Consts.NORTH] - 144 + 24;

	/*xThether = (int) ((player.boundBox [2] + player.boundBox [3])/2);
	yThether = (int) ((player.boundBox [0] + player.boundBox [1])/2);
	double angle = 0;
	if ((xThether - xTip) != 0 && (yThether - yTip) != 0)
	{
	    angle = Math.atan ((yThether - yTip) / (xThether - xTip));


	    double l = (Math.sqrt ((xThether - xTip) * (xThether - xTip) + (yThether - yTip) * (yThether - yTip)) - swordLength);
	    if (Math.abs (l) > 2)
	    {
		double co = 0.2;
		if (l > 0)
		{
		    xAccel = (xThether - xTip) / Math.abs ((xThether - xTip)) * l * Math.cos (angle) * co;
		    yAccel = (yThether - yTip) / Math.abs ((yThether - yTip)) * l * Math.sin (angle) * co;
		}
		else if (l < 0)
		{
		    xAccel =  (xThether - xTip) / Math.abs ((xThether - xTip)) * l * Math.cos (angle) * co;
		    yAccel =  (yThether - yTip) / Math.abs ((yThether - yTip)) * l * Math.sin (angle) * co;
		}
	    }
	    rot = (int) Math.toDegrees (angle);
	}
	xAccel /= 4;
	yAccel /= 4;
	if(xAccel < 0.2)
	{
	    xAccel = 0;
	}
	if(yAccel < 0.2)
	{
	    yAccel = 0;
	}
	xVel += xAccel;
	yVel += yAccel;
	xTip += xVel;
	yTip += yVel;

	xDrawLoc = xThether;
	yDrawLoc = yThether;
	boundBox [Consts.NORTH] = player.yDrawLoc - GameRule.SWORD_BOUND;
	boundBox [Consts.SOUTH] = boundBox [Consts.NORTH] + GameRule.SWORD_BOUND;
	boundBox [Consts.WEST] = player.xDrawLoc - GameRule.SWORD_BOUND;
	boundBox [Consts.EAST] = boundBox [Consts.WEST] + GameRule.SWORD_BOUND;
	//System.out.println(xVel + " " + yVel + " " +Math.sqrt(xVel*xVel + yVel*yVel));*/
	boundBox [Consts.NORTH] = player.yDrawLoc - 24;
	boundBox [Consts.SOUTH] = boundBox [Consts.NORTH] + 144;
	boundBox [Consts.WEST] = player.xDrawLoc - 24;
	boundBox [Consts.EAST] = boundBox [Consts.WEST] + 144;

	wallTimer--;
	if(player == null)
	{
	    boundBox[0] = 100000;
	    xDrawLoc = 100000;
	}

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
	/*g.setColor (Color.BLACK);
	g.drawString ("a", xTip, yTip);
	g.drawString ("b", xThether, yThether);*/
	g.dispose ();

	//xDrawLoc = xLoc - newImage.getWidth()/2 + image.getWidth()/2;
	//yDrawLoc = yLoc - newImage.getHeight()/2 + image.getHeight()/2;

	return newImage; //Return rotated image
    }


    public boolean onCollision (Blob blob)
    {
	if (checkSwordCollision (blob))
	{
	    if (blob.tag == GameRule.WALL)
	    {
		if (wallTimer < 0)
		{
		    angVel = -angVel;
		    if(angVel > 2)
		    {
			rot += Math.toDegrees (angVel);
		    }
		    else
		    {
			rot += angVel/Math.abs(angVel) * 2;
		    }
		    wallTimer = GameRule.SWORD_TIMER;
		    accelTimeOut = GameRule.SWORD_TIMER;
		}
	    }
	    else if (blob.tag == GameRule.PLAYER)
	    {
		//what
	    }
	    else if (blob.tag == GameRule.SWORD)
	    {
		//Do nothing
	    }
	    else if (blob.tag == GameRule.ENEMY)
	    {
		this.angVel /= 2;
		points++;
	    }
	}
	return false;
    }


    public boolean checkSwordCollision (Blob blob)
    {

	/*int east = Math.max(xThether, xTip);
	int west = Math.min(xThether, xTip);
	int north = Math.min(yThether, yTip);
	int south = Math.max(yThether, yTip);
	int xR = Math.min (blob.boundBox [Consts.EAST], east);
	int xL = Math.max (blob.boundBox [Consts.WEST], west);

	if (xR <= xL)
	    return false;
	else
	{
	    int yB = Math.min (blob.boundBox [Consts.SOUTH], south);
	    int yT = Math.max (blob.boundBox [Consts.NORTH], north);
	    if (yB > yT)
		return true;
	    else
		return false;
	}*/
	int x1 = xThether;
	int x2 = xTip;
	int y1 = yThether;
	int y2 = yTip;
	int minX = blob.boundBox[Consts.WEST];
	int minY = blob.boundBox[Consts.NORTH];
	int maxX = blob.boundBox[Consts.EAST];
	int maxY = blob.boundBox[Consts.SOUTH];

	if ((x1 <= minX && x2 <= minX) || (y1 <= minY && y2 <= minY) || (x1 >= maxX && x2 >= maxX) || (y1 >= maxY && y2 >= maxY))
	    return false;
	if( (x2-x1) != 0)
	{
	    float m = (y2 - y1) / (x2 - x1);

	float y = m * (minX - x1) + y1;
	if (y > minY && y < maxY)
	    return true;

	y = m * (maxX - x1) + y1;
	if (y > minY && y < maxY)
	    return true;

	float x = (minY - y1) / m + x1;
	if (x > minX && x < maxX)
	    return true;

	x = (maxY - y1) / m + x1;
	if (x > minX && x < maxX)
	    return true;
	}
	return false;
	
    }


    public void giveMouseCoord (int x, int y)
    {
	if (mouseCounter == GameRule.MOUSE_UPDATE)
	{
	    /*oldMouseX = newMouseX;
	    oldMouseY = newMouseY;*/

	    newMouseX = x;
	    newMouseY = y;

	    mouseCounter = 0;
	}
	else
	{
	    mouseCounter++;
	}
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
