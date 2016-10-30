// The "CulmTonyNg2015" class.
/*
Programmed by: Tony Ng
Last Modified: 30/05/2015
Purpose: Main Class, creates applet, and menus,
	 Explaination of program can be found at User Manual
*/
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.net.*;
import java.io.*;
import java.awt.image.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Container;
import java.util.ArrayList;
import java.awt.FlowLayout;

public class CulmTonyNg2016 extends Applet implements KeyListener, ActionListener, Runnable, MouseListener, MouseMotionListener
{
    int menu = Consts.MENUMAIN;
    Button buttonPlay, buttonOptions, buttonCredits, buttonExit, buttonMain, buttonOptions2, buttonControls, buttonPlay_EndGame;
    Button buttonControlHandles[] = new Button[Consts.KEYSPERCLASS];
    String controlLabels[];
    Game game;
    public Thread t;
    Sword sword;
    Enemy enemy;
    Keys keys;
    Player player = null;
    boolean changeKey = false, controlLabelsShow = false, gameGo = false;
    int keyChange = 0;
    private Image background;
    public void init ()
    {

	player = new Player(GameRule.PLAYER_BOX, 0, game, GameRule.IMG_DANNY);
	sword = new Sword(1, "images/Sword.png", player);
	enemy = new Enemy(150, 150, "images/Enemy.png", player);
	resize (Consts.XSCREENSIZE, Consts.YSCREENSIZE);
	addMouseMotionListener(this);
	addMouseListener(this);
	addKeyListener (this);
	setFocusable(true);
	setLayout (null);

	keys = new Keys(Consts.KEYFILE, Consts.KEYSPERCLASS );
	
	controlLabels = new String[Consts.KEYSPERCLASS];
	for(int i = 0; i < Consts.KEYSPERCLASS; i++)
	{
	    controlLabels[i] = KeyEvent.getKeyText(keys.getKey(i));
	}
	
	buttonPlay = new Button ("Play");
	buttonCredits = new Button ("Credits");
	buttonExit = new Button ("Exit");
	buttonMain = new Button ("Main Menu");;
	buttonControls = new Button ("Controls");
	buttonPlay_EndGame = new Button ("End Game"); //End Game Button cause easy
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i] = new Button();
	    buttonControlHandles[i].setLabel(keys.getLabel(i));
	    System.out.println(keys.getLabel(i));
	}
	buttonPlay.setFont (Buttons.MAINBUTTONFONT);
	buttonCredits.setFont (Buttons.MAINBUTTONFONT);
	buttonExit.setFont (Buttons.MAINBUTTONFONT);
	buttonMain.setFont (Buttons.MAINBUTTONFONT);
	buttonControls.setFont (Buttons.MAINBUTTONFONT);


	buttonPlay.setLocation              (Buttons.PlayX,         Buttons.PlayY);
	buttonCredits.setLocation           (Buttons.CreditsX,      Buttons.CreditsY);
	buttonExit.setLocation              (Buttons.ExitX,         Buttons.ExitY);
	buttonMain.setLocation              (Buttons.MainX,         Buttons.MainY);
	buttonControls.setLocation          (Buttons.ControlsX,     Buttons.ControlsY);
	buttonPlay_EndGame.setLocation      (Buttons.Play_EndGameX, Buttons.Play_EndGameY);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    if(i < buttonControlHandles.length)
	    {
		buttonControlHandles[i].setLocation (190, 130 + i*52);
	    }
	}
	

	buttonPlay.setSize (100, 30);
	buttonCredits.setSize (100, 30);
	buttonExit.setSize (100, 30);
	buttonMain.setSize (100, 30);
	buttonControls.setSize (100, 30);
	buttonPlay_EndGame.setSize (70, 20);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setSize (110, 30);
	}

	buttonMain.setVisible (false);
	buttonPlay_EndGame.setVisible (false);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}
	//System.out.println(getCursorLocation());
	add (buttonPlay);
	add (buttonCredits);
	add (buttonExit);
	add (buttonMain);
	add (buttonControls);
	add (buttonPlay_EndGame);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    add (buttonControlHandles[i]);
	}

	buttonPlay.addActionListener (this);
	buttonCredits.addActionListener (this);
	buttonExit.addActionListener (this);
	buttonMain.addActionListener (this);
	buttonControls.addActionListener (this);
	buttonPlay_EndGame.addActionListener (this);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].addActionListener (this);
	}
	
	background = getImage (getDocumentBase (), Consts.IMG_MAIN);
	repaint();
    }


    public void paint (Graphics g)
    {   
	g.dispose();
	update(g); 
    }
    public void update( Graphics g ) 
    {
	g.drawImage(background, 0,0,this);
	
	if(controlLabelsShow)
	{
	    for(int i = 0; i < controlLabels.length; i++)
	    {
		if(i < buttonControlHandles.length)
		{
		    g.drawString(controlLabels[i], 305, 150+i*52);
		}
	    }
	}
    }
    
    public void updateControlLabels()
    {
	for(int i = 0; i < Consts.KEYSPERCLASS; i++)
	{
	    controlLabels[i] = (KeyEvent.getKeyText(keys.getKey(i)));
	}
    }

	
    public void keyPressed (KeyEvent e) //Send command to tank
    {
	if(player != null)
	{
	    int command = keys.check(e.getKeyCode());
	    if (command > -1)
	    {
		player.sendCommand(command);
	    }
	}
	if(changeKey)
	{
	    keys.setKey(keyChange, e.getKeyCode());
	    changeKey = false;
	    updateControlLabels();
	    repaint();
	}
	e.consume ();
    }


    public void keyReleased (KeyEvent e) //Send finished with command to tank
    {
	if(player != null)
	{
	    int command = keys.check(e.getKeyCode());
	    if (command > -1)
	    {
	       player.revokeCommand(command);
	    }
	}
	e.consume ();
    }


    public void keyTyped (KeyEvent e)
    {
    }

    public void actionPerformed (ActionEvent evt) //Handle Buttons
    {
	String command = evt.getActionCommand ();

	if (command.equals ("Play"))
	{
	    play ();
	}
	else if (command.equals ("Credits"))
	{
	    creditsMenu ();
	}
	else if (command.equals ("Exit"))
	{
	    System.exit (0);
	}
	else if (command.equals ("Main Menu"))
	{
	    mainMenu ();
	}
	else if (command.equals ("Controls"))
	{
	    controlsMenu ();
	}
	else if (command.equals ("End Game"))
	{
	    endGame ();
	}
	else
	{
	    for(int i = 0; i < buttonControlHandles.length; i++)
	    {
		if(command.equals (buttonControlHandles[i].getLabel()))
		{
		    changeKey = true;
		    keyChange = i;
		    this.requestFocusInWindow();
		    return;
		}
	    }
	}
    }
    
    public void run()
    {
	while(gameGo) //Gets image from the game and draws it
	{
	    try{Thread.sleep(Consts.CALCFPS);}          catch(Exception e){};
	    //game.givePoints(sword.points);
	    background = game.draw();
	    //System.out.println(sword.xThether + "\t" + sword.yThether + "\t" + sword.xTip + "\t" + sword.yTip + "\t");
	    //game.addObject(new Bullet(250, 250, 30));
	    repaint();
	}
    }
    public void endGame() //Reset variables and go to main menu
    {
	gameGo = false;
	try{Thread.sleep(Consts.CALCFPS*2);}catch(Exception e){}; //Let's drawing finish before deleting game
	game = null;
	player = null;
	mainMenu();
	
    }

    public void play ()
    {
	buttonPlay.setVisible (false);
	buttonCredits.setVisible (false);
	buttonExit.setVisible (false);
	buttonMain.setVisible (false);
	buttonControls.setVisible (false);
	buttonPlay_EndGame.setVisible (true);
	controlLabelsShow = false;
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}
	
	game = new Game(Practice.IMG_BACKGROUND, new ArrayList());
	for(int i = 0; i < GameRule.WALLS.length; i++)
	{
	    game.addObject(GameRule.WALLS[i]);
	}
	player = new Player(GameRule.PLAYER_BOX, 0, game, GameRule.IMG_DANNY);
	sword = new Sword(0, "images/Sword.png", player);
	game.addObject(sword);
	game.addObject(player);
       // game.addObject(enemy)
	game.start();
	gameGo = true;
	t = new Thread(this);
	t.start();
    }


    public void creditsMenu ()
    {
	buttonPlay.setVisible (false);
	buttonCredits.setVisible (false);
	buttonExit.setVisible (false);
	buttonMain.setVisible (true);
	buttonControls.setVisible (false);
	buttonPlay_EndGame.setVisible (false);
	controlLabelsShow = false;
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}
	background = getImage (getDocumentBase (), Consts.IMG_CREDITS);
	repaint ();
    }


    public void mainMenu ()
    {
	buttonPlay.setVisible (true);
	buttonCredits.setVisible (true);
	buttonExit.setVisible (true);
	buttonMain.setVisible (false);
	buttonControls.setVisible (true);
	buttonPlay_EndGame.setVisible (false);
	controlLabelsShow = false;
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}
	background = getImage (getDocumentBase (), Consts.IMG_MAIN);
	
	repaint ();
    }
    
    public void controlsMenu ()
    {
	
	buttonPlay.setVisible (false);
	buttonCredits.setVisible (false);
	buttonExit.setVisible (false);
	buttonMain.setVisible (true);
	buttonControls.setVisible (false);
	buttonPlay_EndGame.setVisible (false);
	controlLabelsShow = true;    
	
	changeKey = false;
       
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (true);
	}
	background = getImage (getDocumentBase (), Consts.IMG_CONTROLS);
	repaint ();
    }
    
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
	if(game != null){
	    //enemy = new Enemy(100,100,"images/Danny1.png",player);
	    game.addObject(enemy);
	    game.printData();
	    System.out.println("Done");
	    System.out.println(enemy.boundBox[0] + "\t" + enemy.boundBox[1] + "\t" + enemy.boundBox[2] + "\t" + enemy.boundBox[3] + "\t" ); 
	}
	System.out.println("X: " + e.getX() + " Y: " + e.getY());
       
    }
    public void mouseMoved(MouseEvent e) 
    {
	sword.giveMouseCoord(e.getX(), e.getY());
    }
    public void mouseDragged(MouseEvent e)
    {
     
    }
} // CulmTonyNg2014 class
