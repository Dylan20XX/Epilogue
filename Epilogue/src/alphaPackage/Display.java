package alphaPackage;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import graphics.Assets;
import states.CharacterSelectionState;
import states.State;
import states.WorldSelectionState;

/*
 * Author: Alan
 * 
 * this class creates and customizes a frame and canvas for the game
 */
public class Display{
	//properties of the display class
	public static JFrame frame;
	public static JPanel panel;
	public static JScrollPane sp;
	public static JButton[] wsButtons = new JButton[10];
	private Canvas canvas; //allows to draw graphics
	
	private String title;
	private int width, height;
	
	//the display constructor takes in fields needed for the screen and calls the other methods
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
		customCursor();
	}
	
	//customizes frame/canvas settings
	public void createDisplay() {
		
		frame = new JFrame(title);
		frame.setSize(width,  height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close frame when click close
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //center the frame
		frame.setVisible(true);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBounds(900, 250 ,220, 500);
		panel.setVisible(false);
		panel.setBackground(Color.DARK_GRAY);
		
		//Add the scroll pane for the world select
		sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(900, 300, 220, 500);
		sp.setVisible(false);
		frame.add(sp);

		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false); //allows the game to focus without clicking the screen
		
		frame.add(canvas);
		frame.pack(); //allow frame to show canvas
	}
	
	//method that creates a custom cursor
	public void customCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image mouse = null;
		try {
			mouse = ImageIO.read(Assets.class.getResource("/UI/cursor1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        frame.setCursor(toolkit.createCustomCursor(mouse, new Point(0, 0), "Cursor"));
    }
	
	//getters and setters
	public Canvas getCanvas() {
		return canvas;
	}
	
	public static JFrame getFrame() {
		return frame;
	}

}