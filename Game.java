// The "Game" class
/*
Programmed by: Tony Ng
Last Modified: 02/06/2014
Purpose: Stores all the blobs in the game, runs a thread to calculate new values of those blobs, draws the game to send to applet, handles collisions
*/
import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;


public class Game implements Runnable, Consts
{
    private ArrayList data = new ArrayList ();
    private ArrayList databuffer = new ArrayList ();
    private BufferedImage background, image;
    public boolean end = false;
    public Thread t;

    public Game (String imageName, ArrayList c)
    {
	try
	{
	    background = ImageIO.read (new File (imageName));
	}
	catch (Exception e)
	{
	}
	;

	data = c;
	t = new Thread (this);
	image = background;
    }


    public void start ()
    {
	t.start ();
    }


    public void stop ()
    {
	t.stop ();
    }


    public void addObject (Blob obj)  //add object
    {
	databuffer.add (obj);
    }


    public void addObject (Blob obj[])  //add object
    {
	databuffer.add (obj);
    }


    public boolean checkCollisionBox (int i, int j)
    {
	Object o = data.get (i);
	Object o1 = data.get (j);
	Blob a = (Blob) o;
	Blob b = (Blob) o1;

	int xR = Math.min (a.boundBox [EAST], b.boundBox [EAST]);
	int xL = Math.max (a.boundBox [WEST], b.boundBox [WEST]);

	if (xR <= xL)
	    return false;
	else
	{
	    int yB = Math.min (a.boundBox [SOUTH], b.boundBox [SOUTH]);
	    int yT = Math.max (a.boundBox [NORTH], b.boundBox [NORTH]);
	    if (yB > yT)
		return true;
	    else
		return false;
	}
    }


    public void turn ()
    {
	//Put data from buffer into data
	data.addAll (databuffer);
	databuffer.clear ();
	Iterator it = data.iterator ();
	while (it.hasNext ())
	{
	    Object obj = it.next ();
	    Blob a = (Blob) obj;
	    a.calculateTurn ();

	    if (a.boundBox [WEST] > Consts.XSCREENSIZE + GameRule.DELETE_BOUNDARY || a.boundBox [EAST] < 0 - GameRule.DELETE_BOUNDARY
		    || a.boundBox [SOUTH] > Consts.YSCREENSIZE + GameRule.DELETE_BOUNDARY || a.boundBox [NORTH] < 0 - GameRule.DELETE_BOUNDARY)
	    {
		a = null;
		it.remove ();
	    }

	}

	//Check collision for every object
	//int sze = data.size();
	for (int i = 0 ; i < data.size () ; i++)
	{
	    for (int j = i ; j < data.size () ; j++)
	    {
		if (i != j)
		{
		    if (checkCollisionBox (i, j))
		    {
			Blob a = (Blob) data.get (i);
			Blob b = (Blob) data.get (j);
			/*System.out.println ("collision" + i + "\t" + a.boundBox [0] + " " + a.boundBox [1] + " " + a.boundBox [2] + " " + a.boundBox [3] + " " + "and" + j + "\t" +
				b.boundBox [0] + " " + b.boundBox [1] + " " + b.boundBox [2] + " " + b.boundBox [3] + " ");
*/
			// Blob a = (Blob)data.get(i);
			//  Blob b = (Blob)data.get(j);
			boolean flag1 = a.onCollision (b);
			boolean flag2 = b.onCollision (a);
			if(flag1)
			    a = null;
			if(flag2)
			    b = null;
			if (flag1)
			{
			    data.remove (i);
			    if(flag2)
			    {
				data.remove(j-1);
				
			    }
			}
			else if (flag2)
			{
			    data.remove (j);
			}

		    }
		}
	    }
	}
	/*for(int i = sze-1; i >= sze; i--)
	{
	    if(data.get(i) == null)
	    {
		data.remove(i);
	    }
	}*/

	//Draw
	BufferedImage img = new BufferedImage (Consts.XSCREENSIZE, Consts.YSCREENSIZE, BufferedImage.TYPE_INT_ARGB);
	Graphics g;
	g = img.createGraphics ();
	g.drawImage (background, 0, 0, null);

	it = data.iterator ();
	while (it.hasNext ())
	{
	    Object obj = it.next ();
	    Blob s = (Blob) obj;
	    g.drawImage (s.draw (), s.xDrawLoc, s.yDrawLoc, null);
	}
	image = img;
	g.dispose ();
    }


    public void printData ()  //DEBUGGER YO
    {

	ArrayList data2 = new ArrayList (data);
	Iterator it = data2.iterator ();
	while (it.hasNext ())
	{
	    Object obj = it.next ();
	    Blob s = (Blob) obj;
	    System.out.println (s.tag + " " + s.boundBox [0] + " \t"
		    + s.boundBox [1] + " \t"
		    + s.boundBox [2] + " \t"
		    + s.boundBox [3] + " \t"
		    + s.xDrawLoc + " \t"
		    + s.yDrawLoc + " \t"
		    + s.xVel + " \t"
		    + s.yVel + " \t"
		    + s.xAccel + " \t"
		    + s.yAccel + " \t"
		    + s.angVel + " \t"
		    + s.rot );
	}
	System.out.println ("Worky");
    }


    public void run ()  //A clock that tells when to go to next turn
    {
	while (true)
	{
	    try
	    {
		Thread.sleep (Consts.CALCFPS);
	    }
	    catch (Exception e)
	    {
	    }
	    ;
	    turn ();
	}

    }


    public BufferedImage draw ()
    {
	return image;
    }
} // Game class
