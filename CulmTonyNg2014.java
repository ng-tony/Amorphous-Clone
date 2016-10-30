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
import java.awt.FlowLayout;
public class CulmTonyNg2014 extends Applet implements KeyListener, ActionListener, Runnable
{
    int menu = Consts.MENUMAIN;
    Button buttonPlay, buttonOptions, buttonCredits, buttonExit, buttonMain, buttonOptions2, buttonControls, buttonPlay_EndGame;
    Button buttonControlHandles[] = new Button[Consts.KEYSPERCLASS];
    String controlLabels[];
    Game game;
    public Thread t;
    Keys keys;
    Player player = null;
    boolean changeKey = false, controlLabelsShow = false, gameGo = false;
    int keyChange = 0;
    private Image background;
    
    public void init ()
    {
	resize (Consts.XSCREENSIZE, Consts.YSCREENSIZE);
	addKeyListener (this);
	setFocusable(true);
	setLayout (null);

	keys = new Keys(Consts.KEYFILE, Consts.KEYSPERCLASS );
	
	controlLabels = new String[Consts.KEYSPERCLASS];
	for(int i = 0; i < Consts.KEYSPERCLASS; i++)
	{
	    controlLabels[Consts.KEYSPERCLASS] = KeyEvent.getKeyText(keys.getKey(i));
 
	}
	
	buttonPlay = new Button ("Play");
	buttonOptions = new Button ("Options");
	buttonCredits = new Button ("Credits");
	buttonExit = new Button ("Exit");
	buttonMain = new Button ("Main Menu");
	buttonOptions2 = new Button ("Options");
	buttonControls = new Button ("Controls");
	buttonPlay_EndGame = new Button ("End Game"); //End Game Button cause easy
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setLabel(keys.getLabel(i));
	}
	
	buttonPlay.setFont (Buttons.MAINBUTTONFONT);
	buttonOptions.setFont (Buttons.MAINBUTTONFONT);
	buttonCredits.setFont (Buttons.MAINBUTTONFONT);
	buttonExit.setFont (Buttons.MAINBUTTONFONT);
	buttonMain.setFont (Buttons.MAINBUTTONFONT);
	buttonOptions2.setFont (Buttons.MAINBUTTONFONT);
	buttonControls.setFont (Buttons.MAINBUTTONFONT);


	buttonPlay.setLocation              (Buttons.PlayX,         Buttons.PlayY);
	buttonOptions.setLocation           (Buttons.OptionsX,      Buttons.OptionsY);
	buttonCredits.setLocation           (Buttons.CreditsX,      Buttons.CreditsY);
	buttonExit.setLocation              (Buttons.ExitX,         Buttons.ExitY);
	buttonMain.setLocation              (Buttons.MainX,         Buttons.MainY);
	buttonOptions2.setLocation          (Buttons.Options2X,     Buttons.Options2Y);
	buttonControls.setLocation          (Buttons.ControlsX,     Buttons.ControlsY);
	buttonPlay_EndGame.setLocation      (Buttons.Play_EndGameX, Buttons.Play_EndGameY);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    if(i < buttonControlHandles.length/2)
	    {
		buttonControlHandles[i].setLocation (190, 130 + i*52);
	    }
	    else
	    {
		buttonControlHandles[i].setLocation (545, 130 + (i-5)*52);
	    }
	}
	

	buttonPlay.setSize (235, 60);
	buttonOptions.setSize (235, 60);
	buttonCredits.setSize (235, 60);
	buttonExit.setSize (235, 60);
	buttonMain.setSize (235, 60);
	buttonOptions2.setSize (235, 60);
	buttonControls.setSize (235, 60);
	buttonPlay_EndGame.setSize (70, 20);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setSize (110, 30);
	}

	buttonMain.setVisible (false);
	buttonOptions2.setVisible (false);
	buttonControls.setVisible (false);
	buttonPlay_EndGame.setVisible (false);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}

	add (buttonPlay);
	add (buttonOptions);
	add (buttonCredits);
	add (buttonExit);
	add (buttonMain);
	add (buttonOptions2);
	add (buttonControls);
	add (buttonPlay_EndGame);
	
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    add (buttonControlHandles[i]);
	}

	buttonPlay.addActionListener (this);
	buttonOptions.addActionListener (this);
	buttonCredits.addActionListener (this);
	buttonExit.addActionListener (this);
	buttonMain.addActionListener (this);
	buttonOptions2.addActionListener (this);
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
		if(i < buttonControlHandles.length/2)
		{
		    g.drawString(controlLabels[i], 305, 150+i*52);
		}
		else
		{
		    g.drawString(controlLabels[i], 660, 150+(i-5)*52);
		}
	    }
	}
    }
    
    public void updateControlLabels()
    {
	for(int i = 0; i < 5; i++)
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
	    playMenu ();
	}
	else if (command.equals ("Options"))
	{
	    optionsMenu ();
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
	    background = game.draw();
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

    public void playMenu ()
    {
	buttonPlay.setVisible (false);
	buttonOptions.setVisible (false);
	buttonCredits.setVisible (false);
	buttonExit.setVisible (false);
	buttonMain.setVisible (true);
	buttonControls.setVisible (false);
	buttonOptions2.setVisible (false);
	buttonPlay_EndGame.setVisible (false);
	controlLabelsShow = false;
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}
	background = getImage (getDocumentBase (), Consts.IMG_PLAYMENU);
	repaint ();
    }


    public void creditsMenu ()
    {
	buttonPlay.setVisible (false);
	buttonOptions.setVisible (false);
	buttonCredits.setVisible (false);
	buttonExit.setVisible (false);
	buttonMain.setVisible (true);
	buttonControls.setVisible (false);
	buttonOptions2.setVisible (false);
	buttonPlay_EndGame.setVisible (false);
	controlLabelsShow = false;
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}
	background = getImage (getDocumentBase (), Consts.IMG_CREDITS);
	repaint ();
    }

    public void optionsMenu ()
    {
	buttonPlay.setVisible (false);
	buttonOptions.setVisible (false);
	buttonCredits.setVisible (false);
	buttonExit.setVisible (false);
	buttonMain.setVisible (true);
	buttonControls.setVisible (true);
	buttonOptions2.setVisible (false);
	buttonPlay_EndGame.setVisible (false);
	controlLabelsShow = false;
	for(int i = 0; i < buttonControlHandles.length; i++)
	{
	    buttonControlHandles[i].setVisible (false);
	}
	background = getImage (getDocumentBase (), Consts.IMG_OPTIONS);
	repaint ();
    }


    public void mainMenu ()
    {
	buttonPlay.setVisible (true);
	buttonOptions.setVisible (true);
	buttonCredits.setVisible (true);
	buttonExit.setVisible (true);
	buttonMain.setVisible (false);
	buttonControls.setVisible (false);
	buttonOptions2.setVisible (false);
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
	buttonOptions.setVisible (false);
	buttonCredits.setVisible (false);
	buttonExit.setVisible (false);
	buttonMain.setVisible (false);
	buttonControls.setVisible (false);
	buttonOptions2.setVisible (true);
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
} // CulmTonyNg2014 class
