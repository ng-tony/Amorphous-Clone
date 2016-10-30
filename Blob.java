// The "Blob" class.
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;

public class Blob //Shouldn't be created,
{
    public int tag;
    public int xLoc, yLoc;
    public int xDrawLoc, yDrawLoc;
    public int rot;

    public double xAccel, yAccel;
    public double xVel, yVel;
    public double frictAng;
    public double friction;
    public double angVel;

    public int xPivot, yPivot;

    public int drawSize;
    protected BufferedImage image;

    protected int boundBox[] = new int [4];


    public BufferedImage draw ()
    {
	BufferedImage newImage = new BufferedImage (image.getWidth () + image.getHeight (),
						    image.getWidth () + image.getHeight (),
						    BufferedImage.TYPE_INT_ARGB );
	Graphics2D g = (Graphics2D) newImage.getGraphics ();
	AffineTransform at = new AffineTransform();
	at.rotate(Math.toRadians (rot), newImage.getWidth()/2, newImage.getHeight()/2);
	at.translate(newImage.getWidth()/2 - image.getWidth()/2,newImage.getHeight()/2-image.getHeight()/2);
	AffineTransformOp op = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR); 
	g.drawImage(op.filter(image, null), 0, 0, null);
	g.dispose();
	
	//xDrawLoc = xLoc - newImage.getWidth()/2 + image.getWidth()/2;
	//yDrawLoc = yLoc - newImage.getHeight()/2 + image.getHeight()/2;
	
	return newImage; //Return rotated image
    }


    public void rotate (int deg)
    {
	rot += deg;
	if (rot % 360 > 0)
	{
	    rot /= ((rot % 360) * 360);
	}
    }


    public boolean onCollision (Blob blob)
    {
	return true;
    } //Boolean indicates if deleted


    public void calculateTurn ()
    {
      
    }
} // Blob class
