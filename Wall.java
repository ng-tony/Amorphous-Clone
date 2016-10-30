// The "Wall" class.
/*
Programmed by: Tony Ng
Last Modified: 02/06/2014
Purpose: Defines Wall and their behaviours, inherits Blob
*/
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class Wall extends Blob
{
    public Wall(int a, int b,int c, int d)
    {
	tag = Consts.WALL;
	image = null;
	xLoc = 0;
	yLoc = 0;
	int k[] = {a,b,c,d};
	boundBox = k;
	xDrawLoc = boundBox[3]; //Nothing to draw, doesnt matter
	yDrawLoc = boundBox[0]; //Nothing to draw, doesnt matter
	try
	{
	    image = ImageIO.read( new File( Consts.IMG_BLANK ) ); //Needs some image to print
	    //Walls are drawn on background because they dont move anyways
	}
	catch(Exception e){}
	/*BufferedImage newImage = new BufferedImage(boundBox[2] - boundBox[3], boundBox[1] - boundBox[0], BufferedImage.TYPE_INT_ARGB );
	//BufferedImage newImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB );
	Graphics g =  newImage.getGraphics ();
	g.fillRect(boundBox[3], boundBox[0], boundBox[2]-boundBox[3], boundBox[1]-boundBox[0]);
	//System.out.println("Drawing Rect with x = " + xDrawLoc);
	image = newImage;
	g.dispose();*/
    };
    public void calculateTurn()
    {
	//Nothing here
    }
    public boolean onCollision (int id, int dir)
    {
	//No object will affect the wall
	return false;
    }
    public BufferedImage draw()
    {
	return image;
    }
} // Wall class
