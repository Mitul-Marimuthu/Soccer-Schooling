/* Mitul Marimuthu, Andrew Zhou
 * SoccerSchooling5.java
 * This soccer-based game will help teach players advanced math skills - Semester 2
 * algebra 2/trig  level
 * 
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;	
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.Timer;
import java.awt.Insets;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

// andrew, mitul
// Class with main
public class SoccerSchooling5
{
	public SoccerSchooling5()
	{
	}
	
	public static void main(String[] args)
	{
		SoccerSchooling5 game = new SoccerSchooling5();
		game.run();
	}
	
	public void run()
	{
		JFrame frame = new JFrame("Soccer Schooling");
		frame.setSize(1000, 1000);
		frame.setLocation(400, 10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		AddCards ac = new AddCards();
		frame.getContentPane().add(ac);
		frame.setVisible(true);
	}
}
// Class that holds CardLayout
// andrew
class AddCards extends JPanel
{
	// some classes are made FV's so information in them can be accessed using
	// a series of getter methods
	private CardLayout cards; // the CardLayout
	private Opening o; // instance of the opening page
	private Options op; // instance of options panel (can be reset)
	private HighScores hs; // instance of leaderboard panel
	private GeneralInstructions gi; // instance of instructions panel 
	private MainPanel mp; // instance of the main game panel
	private BackButtonHandler bbh; // instance of the home button handler class
	private GenerateQuestions gq; // instance of the class that generates questions
	private EndPanel ep; // instance of the end panel
	private HighScoresGrid hsg; // instance of HighScoresGrid
	private HelpPanel hp; // instance of HelpPanel
	
	// sets every object that is shown - all of the panels
	public AddCards()
	{
		cards = new CardLayout();
		setLayout(cards);
		bbh = new BackButtonHandler(this, cards);
		o = new Opening(this, cards);
		op = new Options(bbh, this, cards);
		hsg = new HighScoresGrid(this);
		hs = new HighScores(bbh, hsg, this);
		gi = new GeneralInstructions(bbh);
		mp = new MainPanel(this, cards);
		PausePanel pp = new PausePanel(this, cards);
		gq = new GenerateQuestions(this);
		hp = new HelpPanel(this, cards);
		ep = new EndPanel(this, cards);
		add(o, "Opening");
		add(op, "Options");
		add(hs, "High Scores");
		add(gi, "Instructions");
		add(mp, "Main");
		add(pp, "Pause");
		add(hp, "Help");
		add(ep, "End");

	}
	// returns the instance of the options panel to reset it
	public Options getOptionsPanel()
	{
		return op;
	}
	
	// return instance of instructions panel so methods inside it can be called from
	// other classes
	public GeneralInstructions getInstructionsPanel()
	{
		return gi;
	}
	// returns instance of MainPanel
	public MainPanel getMainPanel()
	{
		return mp;
	}
	// returns instance 0f BackButtonHandler
	public BackButtonHandler getBackHandler()
	{
		return bbh;
	}
	// returns instance of GenerateQuestions
	public GenerateQuestions getGenerateQuestions()
	{
		return gq;
	}
	// returns instance of cards
	public CardLayout getCards()
	{
		return cards;
	}
	// returns the instance of EndPanel
	public EndPanel getEnd()
	{
		return ep;
	}
	// returns the instance of Opening
	public Opening getOpening()
	{
		return o;
	}
	// returns the instance of HighScoresGrid
	public HighScoresGrid getHSG()
	{
		return hsg;
	}
	// returns the instance of HighScores
	public HighScores getHS()
	{
		return hs;
	}
	// returns the instance of EndPanel
	public HelpPanel getHP()
	{
		return hp;
	}
}
// andrew
class Opening extends JPanel implements ActionListener
{
	private JTextField name; // Text Field to enter name
	private String nameInput; // input from the text field
	private AddCards ac1; // parent of the CardLayout
	private CardLayout cards1; // the CardLayout
	private boolean nameTyped; // to see if the name has been typed in order to continue
	private Image image, footImage; // the opening image
	private int footX, footY; // coordinates of the animation
	private Timer footTimer; // timer for the animation
	private int counter; // to count the number of times actionPerformed is called
	
	// sets teh FV's and adds the components
	public Opening(AddCards acIn, CardLayout cardsIn)
	{
		counter = 0;
		footTimer = new Timer(25, this);
		nameTyped = false;
		ac1 = acIn;
		cards1 = cardsIn;
		setTimer();
		image = getOpeningImage("openingImage.jpg");
		footImage = getOpeningImage("foot.png");
		setLayout(null);
		setBackground(Color.GREEN);
		Font font = new Font("Arial", Font.BOLD, 20);
		JLabel forName = new JLabel("Name:");
		forName.setBackground(Color.WHITE);
		forName.setOpaque(true);
		forName.setFont(font);
		forName.setBounds(260,570, 100,60);
		
		JLabel reminder = new JLabel("Make sure to enter your name first!");
		reminder.setFont(font);
		reminder.setOpaque(true);
		reminder.setBounds(325,470, 340,50);
		
		name = new JTextField("");
		name.setFont(font);
		nameInput = new String("");
		name.setBounds(370,550, 330,100);
		name.addActionListener(new TextField1Handler());
		add(name);
		JButton play = new JButton("Play");
		JButton instructions = new JButton("Instructions");
		JButton highScores = new JButton("Leaderboard");
		JButton exit = new JButton("Exit");
		play.setBounds(300,700, 400,30);
		instructions.setBounds(300,760, 400,30);
		highScores.setBounds(300,820, 400,30);
		exit.setBounds(300,880, 400,30);
		OpeningButtonsHandler obh = new OpeningButtonsHandler();
		
		play.addActionListener(obh);
		instructions.addActionListener(obh);
		highScores.addActionListener(obh);
		exit.addActionListener(obh);
		
		add(play);
		add(instructions);
		add(highScores);
		add(forName);
		add(reminder);
		add(exit);
		
		footTimer.start();
	}
	// sets the opening image
	public Image getOpeningImage(String nameOfImage)
	{
		Image imageOut = null;
		String fileName1 = nameOfImage;
		File openingImageFile = new File(fileName1);
		try
		{
			imageOut = ImageIO.read(openingImageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\nThe image " + fileName1 + " could not be found\n\n");
			e.printStackTrace();
		}
		return imageOut;
	}
	// sets the starting coordinates for the opening animation
	public void setTimer()
	{
		footX = 250;
		footY = -700;
	}
	
	// draws on the screen (the image, name)
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0,0, this.getWidth(), this.getHeight(), this);
		g.drawImage(footImage, footX, footY, 600,650, this);
		g.setColor(Color.RED);
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 130));
		g.drawString("Soccer", 270,200);
		g.drawString("Schooling", 270,400);
	}
	// moves the foot up and down
	public void actionPerformed(ActionEvent evt)
	{
		counter++;
		if (counter <= 25)
			footY += 20;
		else if (counter >= 40 && counter <= 65)
			footY -=20;
		else if (counter == 80)
		{
			counter = 0;
			setTimer();
		}
		repaint();
	}
	// getter method for the JTextField that has the user's name
	public JTextField getNameField()
	{
		return name;
	}
	
	// Handler class for the JTextField
	// gets the name and sets nameTyped for the buttons
	class TextField1Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			nameTyped = true;
			nameInput = name.getText();
			ac1.getInstructionsPanel().setName(nameInput);
			ac1.getMainPanel().getNameForTopPanel(nameInput);
		}
	}
	// Handler Class for the JButtons on this panel
	// sends the user to a different panel based on what they click
	class OpeningButtonsHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String action = evt.getActionCommand();
			String mode = ac1.getOptionsPanel().getMode();
			String diff = ac1.getOptionsPanel().getDifficulty();
			if (nameTyped && !name.getText().equals("Enter your name"))
			{
				if (action.equals("Play"))
					cards1.show(ac1, "Options");
				else if (action.equals("Instructions"))
					cards1.show(ac1, "Instructions");
				else if (action.equals("Leaderboard"))
				{
					if (mode.equals(""))
					{
						ac1.getHSG().setScores();
					}
					else if (!mode.equals("") && diff.equals(""))
					{
						ac1.getHSG().setScores();
					}
					else
					{
						ac1.getHSG().defaultName();
					}
					ac1.getHS().setLabel();
					cards1.show(ac1, "High Scores");
				}
			}
			else
				repaint();
			if (action.equals("Exit"))
				System.exit(1);
		}
	}
}
// Mitul
// class where all the buttons are added (just home buttons for now)
class AddButton extends JPanel
{
	// 2 constructors for this class for: just the button, or the button with a gap
	public AddButton(JButton button)
	{
		button.setPreferredSize(new Dimension(250,50));
		add(button);
	}
	
	public AddButton(JButton button, int vGapIn)
	{
		button.setPreferredSize(new Dimension(250,50));
		setLayout(new FlowLayout(FlowLayout.CENTER, 0,vGapIn));
		add(button);
		setBackground(Color.BLACK);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
// mitul
class Options extends JPanel implements ActionListener
{
	private CardLayout option2, option3; // CardLayout for smooth option picking
	private JPanel option2Panel, option3Panel; // Panels for CardLayouts above
	private int counter; // to know when to show the next card
	private Image optionsImage; // the image displayed
	private String mode, difficulty, inputMethod; // what the user clicks
	private AddCards ap5; // instance of AddCards
	private CardLayout cards5; // instance of CardLayout
	private ButtonGroup modeGroup, difficultyGroup, inputMethodGroup; // the ButtonGroups
	
	// initializes and adds everything to the panel
	public Options(BackButtonHandler bbhIn, AddCards apIn, CardLayout cardsIn)
	{
		ap5 = apIn;
		cards5 = cardsIn;
		mode = difficulty = inputMethod = new String("");
		counter = 1;
		OptionsPanel op = new OptionsPanel();
		
		option2Panel = new JPanel();
		option3Panel = new JPanel();
		
		option2 = new CardLayout();
		option3 = new CardLayout();
		
		option2Panel.setLayout(option2);
		option3Panel.setLayout(option3);
		
		option2Panel.add(new JPanel(), "Default");
		option3Panel.add(new JPanel(), "Default");
		
		setBackground(Color.BLUE);
		setLayout(new BorderLayout());
		
		optionsImage = getOptionsImage();
		Font font = new Font("Arial", Font.BOLD, 30);

		JButton back = new JButton("Home");
		
		JPanel southPanel = new JPanel();
		southPanel.add(new AddButton(back));
		JButton progress = new JButton("Start");
		progress.setPreferredSize(new Dimension(250, 50));
		progress.addActionListener(this);
		southPanel.add(progress);
		add(southPanel, BorderLayout.SOUTH);
		back.addActionListener(bbhIn);
		RadioButtonHandler rbh = new RadioButtonHandler();
		JRadioButton trig = new JRadioButton("Trigonometry");
		JRadioButton prob = new JRadioButton("Probability");
		JRadioButton stats = new  JRadioButton("Statistics");
		modeGroup = new ButtonGroup();
		setButtons(trig, prob, stats, modeGroup, rbh, font);
		
		JRadioButton simple = new JRadioButton("Simple");
		JRadioButton difficult =  new JRadioButton("Difficult");
		difficultyGroup = new ButtonGroup();
		setButtons(simple, difficult, difficultyGroup, rbh, font);
		
		JRadioButton mouse = new JRadioButton("Mouse");
		JRadioButton keyboard = new JRadioButton("Keyboard");
		inputMethodGroup = new ButtonGroup();
		setButtons(mouse, keyboard, inputMethodGroup, rbh, font);
		
		op.add(new MakePanel1("Game mode", trig, prob, stats, font, optionsImage));
		option2Panel.add(new MakePanel2("Difficulty", simple, difficult, font, optionsImage), "Diff");
		option3Panel.add(new MakePanel3("Input Method", mouse, keyboard, font, optionsImage), "Input");
		op.add(option2Panel);
		op.add(option3Panel);
		add(op, BorderLayout.CENTER);
	}
	// sets the image for this panel
	public Image getOptionsImage()
	{
		Image imageOut2 = null;
		String fileName2 = "optionsImage.jpg";
		File optionsImageFile = new File(fileName2);
		try
		{
			imageOut2 = ImageIO.read(optionsImageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\nThe image " + fileName2 + " could not be found\n\n");
			e.printStackTrace();
		}
		return imageOut2;
	}
	 // sets the buttons and button groups for groups of 3 buttons
	public void setButtons(JRadioButton button1, JRadioButton button2, JRadioButton button3,
		ButtonGroup bg, RadioButtonHandler listener, Font fontIn)
	{
		button1.addActionListener(listener);
		button2.addActionListener(listener);
		button3.addActionListener(listener);
		
		button1.setFont(fontIn);
		button2.setFont(fontIn);
		button3.setFont(fontIn);
		
		bg.add(button1);
		bg.add(button2);
		bg.add(button3);
	}
	// sets Buttons and ButtonGroups for groups of 2 buttons
	public void setButtons(JRadioButton button1, JRadioButton button2, ButtonGroup bg, 
		RadioButtonHandler listener, Font fontIn)
	{
		button1.addActionListener(listener);
		button2.addActionListener(listener);
		
		button1.setFont(fontIn);
		button2.setFont(fontIn);
		
		bg.add(button1);
		bg.add(button2);
	}
	// resets the "animation" by resetting everything needed for it
	public void reset()
	{
		option2.show(option2Panel, "Default");
		option3.show(option3Panel, "Default");
		counter = 1;
		modeGroup.clearSelection();
		difficultyGroup.clearSelection();
		inputMethodGroup.clearSelection();
	}
	// Handler class for radio buttons
	// saves FV's to the user's input and switches cards when needed
	class RadioButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equals("Trigonometry") || command.equals("Probability") ||
				command.equals("Statistics"))
				mode = evt.getActionCommand();
			
			counter++;
			if (counter >= 2)
			{
				option2.show(option2Panel, "Diff");
			}
			if (counter >= 3 && (command.equals("Simple") || command.equals("Difficult")))
			{
				option3.show(option3Panel, "Input");
				difficulty = evt.getActionCommand();
			}
			if (counter >= 4 && (command.equals("Mouse") || command.equals("Keyboard")))
			{
				inputMethod = command;
			}
		}
	}
	
	// getter methods for the information
	
	public String getDifficulty()
	{
		return difficulty;
	}
	
	public String getMode()
	{
		return mode;
	}
	
	public String getInput()
	{
		return inputMethod;
	}
	
	// handler method for play button
	// calls methods in MainPanel to set everything needed
	public void actionPerformed(ActionEvent evt)
	{
		if (counter >= 4)
		{
			cards5.show(ap5, "Main");
			ap5.getMainPanel().setVars(difficulty);
			ap5.getMainPanel().setMode(inputMethod);
			ap5.getMainPanel().getGameTimer().start();
			ap5.getGenerateQuestions().getQuestion();
		}
	}
}
// mitul
class OptionsPanel extends JPanel
{
	// makes the grid layout for the options panel
	public OptionsPanel()
	{
		setLayout(new GridLayout(3,1));
	}
}
// mitul
// Makes the panel for the first set of options
class MakePanel1 extends JPanel
{
	private Image image2; // image from options panel
	
	// sets everything needed. 
	public MakePanel1(String option, JRadioButton button1, JRadioButton button2,
		JRadioButton button3, Font fontIn, Image imIn)
	{
		image2 = imIn;
		setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
		JLabel name = new JLabel(option);
		name.setForeground(Color.RED);
		name.setFont(fontIn);
		add(name);
		add(button1);
		add(button2);
		add(button3);
	}
	// draws one third of the image on this panel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image2, 0,0, this.getWidth(), this.getHeight(), 0,0, image2.getWidth(this),
			image2.getHeight(this)/3, this);
	}
}
// mitul
// Makes the panel for the second set of options
class MakePanel2 extends JPanel
{
	private Image image3; // image from options panel
	
	// sets everything needed
	public MakePanel2(String option, JRadioButton button1, JRadioButton button2, Font fontIn,
		Image imIn)
	{
		image3 = imIn;
		setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
		JLabel name = new JLabel(option);
		name.setForeground(Color.RED);
		name.setFont(fontIn);
		add(name);
		add(button1);
		add(button2);
	}
	// draws the second third of the image
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image3, 0,0, this.getWidth(), this.getHeight(), 0, image3.getHeight(this)/3,
			image3.getWidth(this), (int)(image3.getHeight(this)*(2.0/3)), this);
	}
}
// mitul
class MakePanel3 extends JPanel
{
	private Image image4; // image from options panel
	
	// sets everything
	public MakePanel3(String option, JRadioButton button1, JRadioButton button2, Font fontIn,
		Image imIn)
	{
		image4 = imIn;
		setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
		JLabel name = new JLabel(option);
		name.setForeground(Color.RED);
		name.setFont(fontIn);
		add(name);
		add(button1);
		add(button2);
	}
	// draws last third of the image
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image4, 0,0, this.getWidth(), this.getHeight(), 0,(int)(image4.getHeight(this)*(2.0/3)),
			image4.getWidth(this), image4.getHeight(this), this);
	}
}
// andrew
class GeneralInstructions extends JPanel
{
	private JLabel welcome; // the JLabel that displays the name
	// sets everything for this panel
	public GeneralInstructions(BackButtonHandler bbhIn)
	{
		Font labelFont = new Font("Arial", Font.BOLD + Font.ITALIC, 50);
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
		JPanel labelPanel = new JPanel();
		welcome = new JLabel("Welcome");
		welcome.setFont(labelFont);
		labelPanel.setPreferredSize(new Dimension(1000,70));
		labelPanel.add(welcome);
		add(labelPanel, BorderLayout.NORTH);
		JButton back2 = new JButton("Home");
		add(new AddButton(back2), BorderLayout.SOUTH);
		back2.addActionListener(bbhIn);
		InstructionsPanel ip = new InstructionsPanel();
		add(ip, BorderLayout.CENTER);
	}
	// sets the Label with the name entered from before
	public void setName(String nameIn)
	{
		welcome.setText("Welcome " + nameIn);
	}
}
//andrew 
class InstructionsPanel extends JPanel
{ 
	private Image gameImage;
	// panel with gridlayout that displays instructions
	// sets JTextArea with instructions
	public InstructionsPanel()
	{
		setBackground(Color.BLACK);
		gameImage = getGameImage();
		setLayout(new GridLayout(2,1));
		Font instructionFont = new Font("Arial", Font.BOLD, 20);
		JTextArea instructions = new JTextArea();
		Insets tAInset = new Insets(15, 40, 15, 40);
		instructions.setMargin(tAInset);
		instructions.setLineWrap(true);  
		instructions.setWrapStyleWord(true);
		instructions.setEditable(false);
		instructions.setFont(instructionFont);
		instructions.setText("\t\t         Welcome to Soccer Schooling!\n\nIn this math "
			+ "based game, " 
			+ "you will attempt to make saves a goalie by answering the question shown correctly."
			+ " However, there will a timer ticking down. The amount of time that you have " 
			+ "depends on the difficulty you choose. Simple difficulty will have 3 minutes, " 
			+ "while difficult difficulty will have 4.\n\nFor every question you answer right, 3 " 
			+ "seconds will be added to clock, BUT, for every question you answer wrong, "
			+ "time will be deducted from the clock. The amount of time deducted depends " 
			+ "on the total number of questions you answer wrong, each time increasing by " 
			+ "3 seconds. However, we do want you to learn from you mistakes, so for every " 
			+ " time you answer incorrectly, you will be redirected to a help page where general" 
			+ " information on how to solve the type of problem is given. You can also access this " 
			+ "page while playing the game by clicking on the puase button on the top left corner. "
			+ "From there you will be redirected to a page where you can access the help page as " 
			+ "well as restart or got to the home page. No time will be lost on these pages."
			+ " Each completed attempt will be stored and you will be able to view them via " 
			+ "the leaderboard page on the home screen."
			+ "\n\nIn order to get started, click the back button at the bottom, enter your name, " 
			+ "and click \"play\" to be redirected to a page where you can choose how to play."
			+ " You can choose between 3 different game modes, 2 difficulties, and can choose "
			+ "whether you want to play with you keyboard or mouse.\n\nFor mouse mode, "
			+ "you will see buttons on the screen and will attempt to choose the button "
			+ "with the correct answer.\n\nFor keyboard, you will see labels in the same fashion "
			+ " and will answer using the \"Q\", \"E\", \"A\", and \"D\" keys, in respect " 
			+ "to where it appears on the page (Q is top left, E is top right, A is bottom left, "
			+ " D is bottom right).\n\nOnce you finish this stage, you can begin!"
			+ "\n\nAn image of actual gameplay is attached below.");
		JScrollPane panelWithInstructions = new JScrollPane(instructions);
		add(panelWithInstructions);
	}
	
	// draws the image of gameplay
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(gameImage, 200, this.getHeight()/2, 600, this.getHeight()/2, this);
	}
	
	// gets the image of actual gameplay
	// specific to this image
	public Image getGameImage()
	{
		Image imageOut2 = null;
		String fileName2 = "gameCapture.png";
		File optionsImageFile = new File(fileName2);
		try
		{
			imageOut2 = ImageIO.read(optionsImageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\nThe image " + fileName2 + " could not be found\n\n");
			e.printStackTrace();
		}
		return imageOut2;
	}
}
// andrew
class HighScores extends JPanel implements ActionListener
{
	private HighScoresGrid hsg2; // instance of HighScoresGrid
	private JLabel nameOfFile; // the label with the current leaderboard
	private AddCards ac12; // the AddCards instance
	
	// sets and adds everything
	public HighScores(BackButtonHandler bbhIn, HighScoresGrid hsgIn, AddCards acIn)
	{
		ac12 = acIn;
		hsg2 = hsgIn;
		setBackground(Color.RED);
		setLayout(new BorderLayout());
		JButton back3 = new JButton("Home");
		JMenuBar modeBar = getMenuBar();
		nameOfFile = new JLabel("");
		Font labelFont = new Font("Arial", Font.BOLD, 30);
		nameOfFile.setFont(labelFont);
		JPanel m1 = new JPanel();
		m1.setLayout(new FlowLayout(FlowLayout.CENTER, 200,0));
		m1.add(modeBar);
		m1.add(nameOfFile);
		add(m1, BorderLayout.NORTH);
		add(new AddButton(back3), BorderLayout.SOUTH);
		add(hsg2, BorderLayout.CENTER);
		back3.addActionListener(bbhIn);
	}
	
	// sets the label appropriately with teh game mode and difficulty
	public void setLabel()
	{
		String mode3 = ac12.getOptionsPanel().getMode();
		String diff3 = ac12.getOptionsPanel().getDifficulty();
		if (mode3.equals(""))
		{
			nameOfFile.setText("Current: Trigonometry-Simple");
		}
		else if (!mode3.equals("") && diff3.equals(""))
		{
			nameOfFile.setText("Current: Trigonometry-Simple");
		}
		else
		{
			nameOfFile.setText("Current: " + mode3 + "-" + diff3);
		}
	}
	
	// sets the menu bar
	public JMenuBar getMenuBar()
	{
		JMenuBar bar = new JMenuBar();
		JMenu mode = new JMenu("Mode");
			
		JMenuItem simpTrig = new JMenuItem("Trigonometry-Simple");
		JMenuItem diffTrig = new JMenuItem("Trigonometry-Difficult");
		JMenuItem simpProb = new JMenuItem("Probability-Simple");
		JMenuItem diffProb = new JMenuItem("Probability-Difficult");
		JMenuItem simpStat = new JMenuItem("Statistics-Simple");
		JMenuItem diffStat = new JMenuItem("Statistics-Difficult");
			
		simpTrig.addActionListener(this);
		diffTrig.addActionListener(this);
		simpProb.addActionListener(this);
		diffProb.addActionListener(this);
		simpStat.addActionListener(this);
		diffStat.addActionListener(this);
		
		mode.add(simpTrig);
		mode.add(diffTrig);
		mode.add(simpProb);
		mode.add(diffProb);
		mode.add(simpStat);
		mode.add(diffStat);
				
		bar.add(mode);
		return bar;
	}
	
	// handler for the menubar
	// calls a method to change the JLabels 
	public void actionPerformed(ActionEvent evt)
	{
		String page = evt.getActionCommand();
		nameOfFile.setText("Current: " + page);
		hsg2.runIt(page);
	}
}

// mitul
class HighScoresGrid extends JPanel
{
	private JLabel[][] namesAndScores; // 5 x 2 2d array to hold names and scores
	private String fileName; // the file to read
	private AddCards ac11; // the instance of AddCards
	
	// sets and adds everything
	public HighScoresGrid(AddCards acIn)
	{
		ac11 = acIn;
		setBackground(Color.BLACK);
		setLayout(new GridLayout(6,2, 100,50));
		Font labelFont = new Font("Arial", Font.BOLD, 40);
		JLabel name = new JLabel("Name:");
		JLabel score = new JLabel("Score:");
		name.setOpaque(true);
		score.setOpaque(true);
		name.setFont(labelFont);
		score.setFont(labelFont);
		fileName = new String("Trigonometry-Simple.txt");
		add(name);
		add(score);
		namesAndScores = new JLabel[5][2];
		System.out.println(namesAndScores.length);
		for (int i = 0; i < namesAndScores.length; i++)
		{
			for (int j = 0; j < namesAndScores[0].length; j++)
			{
				namesAndScores[i][j] = new JLabel();
				namesAndScores[i][j].setOpaque(true);
				namesAndScores[i][j].setFont(labelFont);
				add(namesAndScores[i][j]);
			}
		}
		
	}
	// sets the layout to the leaderboard of the one that the user just played
	public void defaultName()
	{
		fileName = ac11.getOptionsPanel().getMode() + "-" + ac11.getOptionsPanel().getDifficulty() + ".txt";
		setScores();
	}
	
	// sets the new file
	public void runIt(String fileIn)
	{
		fileIn += ".txt";
		fileName = fileIn;
		setScores();
	}
	
	// reads in the file and sets the information to teh panel using the JLabel array 
	public void setScores()
	{
		Scanner readIn = null;
		File inFile = new File(fileName);
		try
		{
			readIn = new Scanner(inFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("\n\nThe file " + fileName + " could not be found\n\n");
			System.exit(5);
		}
		String line = new String("");
		int place = 0;
		while(readIn.hasNext() && place < 5)
		{
			line = readIn.nextLine();
			if (!line.equals(""))
			{
				namesAndScores[place][0].setText(line.substring(0, line.lastIndexOf("-")).trim());
				namesAndScores[place][1].setText(line.substring(line.lastIndexOf("-") + 1).trim());
				place++;
			}
		}
		for (int i = 0; i < namesAndScores.length; i++)
		{
			if(i >= place)
			{
				for (int j = 0; j < namesAndScores[0].length; j++)
					namesAndScores[i][j].setText("");
			}
		}
	}
}

// Handler class for the home button
class BackButtonHandler implements ActionListener
{
	private AddCards ac2; // instance of AddCards
	private CardLayout cards2; // instance of CardLayout
	
	// sets both FV's 
	public BackButtonHandler(AddCards acIn, CardLayout cardsIn)
	{
		ac2 = acIn;
		cards2 = cardsIn;
	}
	// stops and resets the timers/stats and switches back to the home page
	public void actionPerformed(ActionEvent evt)
	{
		cards2.show(ac2, "Opening");
		ac2.getOptionsPanel().reset();
		ac2.getMainPanel().getGameTimer().stop();
		ac2.getGenerateQuestions().getPQ().resetAsked();
		ac2.getMainPanel().getBMP().resetSlider();
	}
}

// mitul
class MainPanel extends JPanel implements ActionListener
{
	private CardLayout mainGameCards; // CardLayout to swap between animation panel and game panel
	private JPanel mainPanelParent; // parent for Card above
	private Timer gameTimer; // Timer for the game
	private AddCards ap4; // parent of the main cardlayout
	private CardLayout cards3; // cardLayout for above
	private int score, fontSize, time, answersWrong; //player stats
	private SetButtonLayout sbl; //instance of buttonmode panel
	private BottomMainPanel bmp; // instance of the bottom of the panel
	private TopMainPanel tmp; // instance of the top of the panel
	private Image goalImage; // image of the goal
	private SetLabelLayout sll; // instance of the keyboard mode panel
	private Image[] imagesNeeded; // array of the images for the animation
	private Image ball; // the image of the ball
	private Animation anime; // the instance of Animation
	
	// add everything to the frame
	public MainPanel(AddCards apIn, CardLayout cardsIn)
	{
		mainGameCards = new CardLayout();
		mainPanelParent = new JPanel();
		mainPanelParent.setLayout(mainGameCards);
		sbl = new SetButtonLayout(this);
		sll = new SetLabelLayout(this);
		anime = new Animation(this);
		mainPanelParent.add(sbl, "Button Mode");
		mainPanelParent.add(sll, "Keyboard Mode");
		mainPanelParent.add(anime, "Animation");
		bmp = new BottomMainPanel(sbl, sll);
		tmp = new TopMainPanel(apIn, cardsIn);
		ap4 = apIn;
		cards3 = cardsIn;
		goalImage = getGoalImage();
		setLayout(new BorderLayout());
		add(bmp, BorderLayout.SOUTH);
		add(tmp, BorderLayout.NORTH);
		add(mainPanelParent, BorderLayout.CENTER);
		gameTimer = new Timer(1000, this);
		ball = getAnimationImage("ball.jpg");
		
		String[] names = new String[]{"squat.png", "topLeft.png", "topRight.png", "bottomLeft.png",
			"bottom right.png"};
		imagesNeeded = new Image[5];
		for (int i = 0; i < imagesNeeded.length; i++)
		{
			imagesNeeded[i] = getAnimationImage(names[i]);
		}
	}

	// so the stats can be reset in between games
	// resets stats
	public void setVars(String difficultyIn)
	{
		score = 0;
		fontSize = 15;
		answersWrong = 0;
		if (difficultyIn.equals("Simple"))
			time = 180;
		else if (difficultyIn.equals("Difficult"))
			time = 240;
		sbl.setFonts(fontSize);
		bmp.setLabels(score, time);
	}
	// returns the instance of the Animation class
	public Animation getAnimation()
	{
		return anime;
	}
	// gets the images needed for the animation
	public Image getAnimationImage(String nameOfImage)
	{
		Image imageOut = null;
		String fileName1 = nameOfImage;
		File openingImageFile = new File(fileName1);
		try
		{
			imageOut = ImageIO.read(openingImageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\nThe image " + fileName1 + " could not be found\n\n");
			e.printStackTrace();
		}
		return imageOut;
	}
	
	// getter method for the image array
	public Image[] getImageArray()
	{
		return imagesNeeded;
	}
	// getter method for the ball
	public Image ballImage()
	{
		return ball;
	}
	// getter method for the bottom of the panel
	public BottomMainPanel getBMP()
	{
		return bmp;
	}
	// for the game timer
	// lowers time by 1 second every second, and if it goes below 0, stops the game
	public void actionPerformed(ActionEvent evt)
	{
		time--;
		if (time > 0)
			bmp.setLabels(score, time);
		else
		{
			stopGame();
		}
	}
	// getter method for the time
	public int getTimeLeft()
	{
		return time;
	}
	// to generate the first question
	public void runGame()
	{
		if (time > 0)
			ap4.getGenerateQuestions().getQuestion();
	}
	// getter method for the timer
	public Timer getGameTimer()
	{
		return gameTimer;
	}
	// getter method for AddCards
	public AddCards getAddCards()
	{
		return ap4;
	}
	// getter method for button mode
	public SetButtonLayout getButtonLayout()
	{
		return sbl;
	}
	// getter method for keyboard mode
	public SetLabelLayout getLabelLayout()
	{
		return sll;
	}
	// getter method for the JPanel that holds the game cards
	public JPanel getMainPanelParent()
	{
		return mainPanelParent;
	}
	// getter method for the CardLayout for above
	public CardLayout getMainCards()
	{
		return mainGameCards;
	}
	
	// selects which game panel should be shown
	public void setMode(String inputIn)
	{
		if (inputIn.equals("Mouse"))
		{
			mainGameCards.show(mainPanelParent, "Button Mode");
		}
		else if (inputIn.equals("Keyboard"))
		{
			mainGameCards.show(mainPanelParent, "Keyboard Mode");
		}
		bmp.setMode(inputIn);
	}
	// updates score and time
	public void updateScore(boolean ifCorrectIn)
	{
		if (ifCorrectIn)
		{
			score++;
			time += 3;
		}
		else
		{
			answersWrong++;
			time -= 3*answersWrong;
		}
		if (time > 0)
			bmp.setLabels(score, time);
		else
		{
			bmp.setLabels(score, 0);
		}
	}
	
	// gets the player name for the top panel
	public void getNameForTopPanel(String nameIn)
	{
		tmp.setName(nameIn);
	}
	
	// stops the game when time is up
	// runs and shows the endpanel
	public void stopGame()
	{
		gameTimer.stop();
		anime.getAnimationTimer().stop();
		anime.resetFrame();
		ap4.getOptionsPanel().reset();
		ap4.getEnd().run(ap4.getOpening().getNameField().getText(), score);
		cards3.show(ap4, "End");
	}
	
	// sets the image of the goal
	public Image getGoalImage()
	{
		Image imageOut = null;
		String goalFileName = "goal.jpg";
		File goalFile = new File(goalFileName);
		try
		{
			imageOut = ImageIO.read(goalFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\nThe image " + goalFileName + " could not be found.\n\n");
			e.printStackTrace();
		}
		return imageOut;
	}
}
// mitul
class Animation extends JPanel implements ActionListener
{
	private int[] desiredX; // the x coordinates for the 4 cornes
	private int[] desiredY; // the y coordinates for the 4 corners
	private boolean correct; // if the user answered correctly
	private int soccerX, soccerY, goalieX, goalieY, goalWidth, goalHeight; // values needed for animation
	private int soccerTravelX, soccerTravelY, goalieTravelX, goalieTravelY; // values needed for animation
	private Timer animationTimer; // the timer for the animation
	private int timePassed, picture, correctCorner; // amount of time passed, the picture to use,
		// which answer is right
	private MainPanel mp4; // the instance of MainPanel
	
	// sets everything
	public Animation(MainPanel mpIn)
	{
		mp4 = mpIn;
		desiredX = new int[]{250, 650, 150, 650};
		desiredY = new int[]{150, 150, 450, 450};
		correct = false;
		resetFrame();
		soccerTravelX = 0;
		soccerTravelY = 0;
		goalieTravelX = 0;
		goalieTravelY = 0;
		animationTimer = new Timer(10, this);
		timePassed = 0;
		picture = 0;
	}
	// resets all of the information
	public void resetFrame()
	{
		timePassed = 0;
		picture = 0;
		soccerX = 450;
		soccerY = 700;
		goalieX = 375;
		goalieY = 325;
		goalWidth = 100;
		goalHeight = 150;
	}
	// getter method for the animation timer
	public Timer getAnimationTimer()
	{
		return animationTimer;
	}
	// figures out where the goalie and ball need to go and how much each image needs
	// to travel in each timer event
	public void startFrame(boolean ifCorrect, int pictureIn, int correctIndex)
	{
		correct = ifCorrect;
		picture = pictureIn;
		soccerTravelX = (int)(Math.abs(450 - desiredX[correctIndex])/15);
		soccerTravelY = (int)(Math.abs(700 - desiredY[correctIndex])/15);
		goalieTravelX = (int)(Math.abs(375 - desiredX[picture - 1])/15);
		goalieTravelY = (int)(Math.abs(325 - desiredY[picture - 1])/15);
		correctCorner = correctIndex;
		animationTimer.start();
	}
	// paints the images
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(mp4.getGoalImage(), 0,0, this.getWidth(), this.getHeight(), 75,205, 890,640, this);
		g.drawImage(mp4.getImageArray()[picture], goalieX, goalieY, goalWidth, goalHeight, this);
		g.drawImage(mp4.ballImage(), soccerX,soccerY, 100,100, this);
	}
	
	// does the animating by moving the ball and goalie the smae amount for 15 events (150 milliseconds)
	// and then pauses for 35 events (350 milliseconds), and then proceeds to account for 
	// the decision based on the correct answer. 
	public void actionPerformed(ActionEvent evt)
	{
		timePassed++;
		if (timePassed <=15)
		{
			if (picture == 1 || picture == 3)
			{
				goalieX -= goalieTravelX;
				if (picture == 3)
					goalieX += 2;
				goalWidth = 200;
				goalHeight = 200;
			}
			else
			{
				goalieX += goalieTravelX;
				goalWidth = 200;
				goalHeight = 150;
			}
				
			if (correctCorner == 0 || correctCorner == 2)
				soccerX -= soccerTravelX;
			else
				soccerX += soccerTravelX;
			if (picture == 1 || picture == 2)
				goalieY -= goalieTravelY;
			else
				goalieY += goalieTravelY;
			soccerY -= soccerTravelY;
			repaint();
		}
		// changes cards when entire animation is over
		else if (timePassed >= 50)
		{
			animationTimer.stop();
			resetFrame();
			repaint();
			if (correct)
				mp4.updateScore(correct);
			else
			{
				mp4.updateScore(correct);
				mp4.getGameTimer().stop();
				String mode = mp4.getAddCards().getOptionsPanel().getMode();
				if (mode.equals("Trigonometry"))
				{
					mp4.getAddCards().getHP().getImage(0);
				}
				else if (mode.equals("Probability"))
				{
					mp4.getAddCards().getHP().getImage(1);
				}
				else
					mp4.getAddCards().getHP().getImage(2);
				mp4.getAddCards().getCards().show(mp4.getAddCards(), "Help");
			}
			mp4.getAddCards().getGenerateQuestions().getQuestion();
			if (mp4.getAddCards().getOptionsPanel().getInput().equals("Mouse"))
				mp4.getMainCards().show(mp4.getMainPanelParent(), "Button Mode");
			else if (mp4.getAddCards().getOptionsPanel().getInput().equals("Keyboard"))
			{
				mp4.getMainCards().show(mp4.getMainPanelParent(), "Keyboard Mode");
				mp4.getLabelLayout().resetAnswered();
			}
		}
	}
}

// mitul
class SetButtonLayout extends JPanel implements ActionListener
{
	private JButton option1, option2, option3, option4; // the 4 options
	private Font fontShown; // the font of the options
	private JTextArea question; // the question shown
	private MainPanel mp2; // instance of mainPanel to access the cards to switch to animation
	private Image goalImage2; // image of the goal
	private String[] choices; // answer options and question
	// sets everything
	public SetButtonLayout(MainPanel mpIn)
	{
		mp2 = mpIn;
		goalImage2 = mp2.getGoalImage();
		setBackground(Color.GREEN);
		option1 = new JButton("option1");
		option1.setPreferredSize(new Dimension(150, 50));
		option2 = new JButton("2");
		option2.setPreferredSize(new Dimension(150, 50));
		option3 = new JButton("3");
		option3.setPreferredSize(new Dimension(150, 50));
		option4 = new JButton("4");
		option4.setPreferredSize(new Dimension(150, 50));
		setLayout(new FlowLayout(FlowLayout.CENTER, 200, 100));
		question = new JTextArea("question", 5, 40);
		Font instructionFont = new Font("Arial", Font.BOLD, 20);
		Insets tAInset2 = new Insets(15,30,15,30);
		question.setMargin(tAInset2);
		question.setLineWrap(true);  
		question.setWrapStyleWord(true);
		question.setEditable(false);
		question.setFont(instructionFont);
		option1.addActionListener(this);
		option2.addActionListener(this);
		option3.addActionListener(this);
		option4.addActionListener(this);
		add(option1);
		add(option2);
		add(question);
		add(option3);
		add(option4);
		fontShown = new Font("Arial", Font.BOLD, 15);
		setFonts(15);
		choices = new String[5];
	}
	
	// sets the fonts when changed
	public void setFonts(int fontIn)
	{
		Font fontShown = new Font("Arial",  Font.BOLD, fontIn);
		option1.setFont(fontShown);
		option2.setFont(fontShown);
		option3.setFont(fontShown);
		option4.setFont(fontShown);
		question.setFont(fontShown);
	}
	// sees if the user answered right and starts the animation
	// handler method  for the 4 buttons
	public void actionPerformed(ActionEvent evt)
	{
		String choice = evt.getActionCommand();
		boolean correct = false;
		int searchFor = mp2.getAddCards().getGenerateQuestions().getAnswerIndex() + 1;
		String playerChoice = choice.substring(0, choice.indexOf("."));
		String fileName4 = mp2.getAddCards().getOptionsPanel().getMode();
		if (fileName4.equals("Trigonometry"))
			fileName4 = "Trig instructions";
		else if (fileName4.equals("Probability"))
			fileName4 = "prob instructions";
		else
			fileName4 = "stats instructions";
		String search = "" + searchFor;
		if (choice.indexOf(search) == 0)
		{
			correct = true;
		}
		if (!correct)
		{
			mp2.getAddCards().getHP().addQuestionToText(fileName4, choices[4], choices[searchFor -1],
				choice);
		}
			
		mp2.getMainCards().show(mp2.getMainPanelParent(), "Animation");
		mp2.getAnimation().startFrame(correct, Integer.parseInt(playerChoice), searchFor - 1);
	}
	
	// sets the buttons and JTextArea with the question and options
	// saves them to the options array
	public void setQuestions(String[] optionsIn, String questionIn)
	{
		option1.setText("1. " + optionsIn[0]);
		option2.setText("2. " + optionsIn[1]);
		option3.setText("3. " + optionsIn[2]);
		option4.setText("4. " + optionsIn[3]);
		question.setText(questionIn);
		for (int i = 0; i < optionsIn.length; i++)
		{
			choices[i] = optionsIn[i];
		}
		choices[4] = questionIn;
	}
	
	// draws the images
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(goalImage2, 0,0, this.getWidth(), this.getHeight(), 75,205, 890,640, this);
		g.drawImage(mp2.getImageArray()[0], 400, 350, 200, 200, this);
		g.drawImage(mp2.ballImage(), 450,700, 100,100, this);
	}
}

//mitul
class BottomMainPanel extends JPanel
{
	private JSlider fontSlider; // the slider for font size
	private JLabel saves, remainingTime; // to display score and remaining time
	private SetButtonLayout sbl2; // in order to access the font method to change fonts
	private SetLabelLayout sll2; // in order to access the font method to chnage fonts
	private String mode4; // the input method
	
	// add everything to the panel
	public BottomMainPanel(SetButtonLayout sblIn, SetLabelLayout sllIn)
	{
		sll2 = sllIn;
		sbl2 = sblIn;
		setLayout(new GridLayout(1,3));
		setPreferredSize(new Dimension(1000,100));
		fontSlider = null;
		createSlider();
		fontSlider.addChangeListener(new FontSliderHandler());
		saves = new JLabel("Saves: ", JLabel.CENTER);
		remainingTime = new JLabel("Time: ", JLabel.CENTER);
		Font labelFont = new Font("Arial", Font.BOLD, 30);
		saves.setFont(labelFont);
		remainingTime.setFont(labelFont);
		add(fontSlider);
		add(saves);
		add(remainingTime);
		mode4 = new String("");
	}
	
	// sets the slider to default
	public void createSlider()
	{
		fontSlider = new JSlider(JSlider.HORIZONTAL,12,18, 15);
		fontSlider.setMajorTickSpacing(2);	
		fontSlider.setPaintTicks(true);
		fontSlider.setLabelTable( fontSlider.createStandardLabels(2) ); 
		fontSlider.setPaintLabels(true);
	}
	// setes the information on the JSlider
	public void resetSlider()
	{
		fontSlider.setValue(15);
		sll2.setFonts(15);
		sbl2.setFonts(15);
	}
	
	// sets the labels with the remaining time and current score
	public void setLabels(int scoreIn, int timeIn)
	{
		saves.setText("Saves: " + scoreIn);
		remainingTime.setText("Time: " + timeIn); 
	}
	
	// sets the input method for later use
	public void setMode(String modeIn)
	{
		mode4 = modeIn;
	}
	
	// handler class for JSlider
	class FontSliderHandler implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			int size = fontSlider.getValue();
			if (mode4.equals("Mouse"))
				sbl2.setFonts(size);
			else
				sll2.setFonts(size);
		}
	}
}

// andrew
class TopMainPanel extends JPanel implements ActionListener
{
	private AddCards ac6; // intance of Panel with main Cards
	private CardLayout cards6; // instance of CardLayout (for pause panel)
	private JLabel name; //to display player name
	
	// adds everything to the panel
	public TopMainPanel(AddCards acIn, CardLayout cardsIn)
	{
		ac6 = acIn;
		cards6 = cardsIn;
		Font font4 = new Font("Arial", Font.BOLD, 20);
		setLayout(new FlowLayout(FlowLayout.CENTER, 200,0));
		setPreferredSize(new Dimension(1000,50));
		JButton pause = new JButton("Pause");
		pause.setPreferredSize(new Dimension(120,50));
		JButton exit = new JButton("Home");
		exit.setPreferredSize(new Dimension(120,50));
		exit.addActionListener(ac6.getBackHandler());
		pause.addActionListener(this);
		name = new JLabel();
		name.setFont(font4);
		add(pause);
		add(name);
		add(exit);
	}
	
	// sets the name recieved from MainPanel
	public void setName(String nameIn)
	{
		name.setText(nameIn);
	}
	
	// Handler class for the pause button
	// calls a method in HelpPanel to decide which file to and image to read in
	public void actionPerformed(ActionEvent evt)
	{
		String mode = ac6.getOptionsPanel().getMode();
		if (mode.equals("Trigonometry"))
		{
			ac6.getHP().setPanel("Trig instructions");
			ac6.getHP().getImage(0);
		}
		else if (mode.equals("Probability"))
		{
			ac6.getHP().setPanel("prob instructions");
			ac6.getHP().getImage(1);
		}
		else
		{
			ac6.getHP().setPanel("stats instructions");
			ac6.getHP().getImage(2);
		}
		cards6.show(ac6, "Pause");
		ac6.getMainPanel().getGameTimer().stop();
	}
}

// andrew
class SetLabelLayout extends JPanel implements KeyListener, MouseListener
{
	private JLabel option1, option2, option3, option4; // the 4 options
	private JTextArea question; // the question shown
	private MainPanel mp3; // instance of mainPanel to access the cards to switch to animation
	private Image goalImage3; // image of the goal
	private boolean answered; // so the user doesn't click more than one answer per question
	private String[] choices2; // answer options and question
	// sets everything
	public SetLabelLayout(MainPanel mpIn)
	{
		answered = false;
		mp3 = mpIn;
		goalImage3 = mp3.getGoalImage();
		setBackground(Color.GREEN);
		option1 = new JLabel("option1");
		option1.setPreferredSize(new Dimension(150, 50));
		option1.setBackground(Color.LIGHT_GRAY);
		option1.setOpaque(true);
		
		option2 = new JLabel("2");
		option2.setPreferredSize(new Dimension(150, 50));
		option2.setBackground(Color.LIGHT_GRAY);
		option2.setOpaque(true);
		
		option3 = new JLabel("3");
		option3.setPreferredSize(new Dimension(150, 50));
		option3.setBackground(Color.LIGHT_GRAY);
		option3.setOpaque(true);
		
		option4 = new JLabel("4");
		option4.setPreferredSize(new Dimension(150, 50));
		option4.setBackground(Color.LIGHT_GRAY);
		option4.setOpaque(true);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 200, 100));
		question = new JTextArea("QUESTION", 5, 40);
		Insets tAInset3 = new Insets(15,30,25,30);
		question.setMargin(tAInset3);
		Font instructionFont = new Font("Arial", Font.BOLD, 20);
		question.setLineWrap(true);  
		question.setWrapStyleWord(true);
		question.setEditable(false);
		question.setFont(instructionFont);
		
		add(option1);
		add(option2);
		add(question);
		add(option3);
		add(option4);
		setFonts(15);
		
		addKeyListener(this);
		addMouseListener(this);
		choices2 = new String[5];
	}
	
	// sets the fonts when changed
	public void setFonts(int fontIn)
	{
		Font fontShown = new Font("Arial",  Font.BOLD, fontIn);
		option1.setFont(fontShown);
		option2.setFont(fontShown);
		option3.setFont(fontShown);
		option4.setFont(fontShown);
		question.setFont(fontShown);
	}
	
	public void mouseClicked(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	// requests focus in user clicks off the main panel
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
	}
	
	public void mouseReleased(MouseEvent evt) {}
	
	// sets the labels and text area with the question and answers
	public void setQuestions(String[] optionsIn, String questionIn)
	{
		option1.setText("1. " + optionsIn[0]);
		option2.setText("2. " + optionsIn[1]);
		option3.setText("3. " + optionsIn[2]);
		option4.setText("4. " + optionsIn[3]);
		question.setText(questionIn);
		for (int i = 0; i < optionsIn.length; i++)
		{
			choices2[i] = optionsIn[i];
		}
		choices2[4] = questionIn;
	}
	
	// draws the goal image
	public void paintComponent(Graphics g)
	{
		requestFocusInWindow();
		super.paintComponent(g);
		g.drawImage(goalImage3, 0,0, this.getWidth(), this.getHeight(), 75,205, 890,640, this);
		g.drawImage(mp3.getImageArray()[0], 400, 350, 200, 200, this);
		g.drawImage(mp3.ballImage(), 450,700, 100,100, this);
	}
	// so the user can answer again
	public void resetAnswered()
	{
		answered = false;
	}
	// sees which option the user entered and starts the animation
	// handler method for keyboard mode
	public void keyPressed(KeyEvent evt)
	{
		int typed = evt.getKeyCode();
		if (!answered && (typed == 'Q' || typed == 'E' || typed == 'A' || typed == 'D'))
		{
			String choice = new String("");
			if (typed == 'Q')
			{
				choice = option1.getText();
			}
			else if (typed == 'E')
				choice = option2.getText();
			else if (typed == 'A')
				choice = option3.getText();
			else if (typed == 'D')
				choice = option4.getText();
			boolean correct = false;
			int searchFor = mp3.getAddCards().getGenerateQuestions().getAnswerIndex() + 1;
			String playerChoice = choice.substring(0, choice.indexOf("."));
			String fileName5 = mp3.getAddCards().getOptionsPanel().getMode();
			if (fileName5.equals("Trigonometry"))
				fileName5 = "Trig instructions";
			else if (fileName5.equals("Probability"))
				fileName5 = "prob instructions";
			else
				fileName5 = "stats instructions";
			String search = "" + searchFor;
			if (choice.indexOf(search) == 0)
			{
				correct = true;
			}
			if (!correct)
			{
				mp3.getAddCards().getHP().addQuestionToText(fileName5, choices2[4], choices2[searchFor -1],
					choice);
			}
			answered = true;
			mp3.getMainCards().show(mp3.getMainPanelParent(), "Animation");
			mp3.getAnimation().startFrame(correct, Integer.parseInt(playerChoice), searchFor - 1);
		}
	}
	
	public void keyReleased(KeyEvent evt) {}
	public void keyTyped(KeyEvent evt) {}
}

// andrew
class PausePanel extends JPanel implements ActionListener
{
	private AddCards ac7; // the instance of AddCards
	private CardLayout cards7; // the instance of the CardLayout
	
	// adds everything
	public PausePanel(AddCards acIn, CardLayout cardsIn)
	{
		setBackground(Color.DARK_GRAY);
		ac7 = acIn;
		cards7 = cardsIn;
		setLayout(new GridLayout(4,1, 0,100));
		JButton home = new JButton("Home");
		JButton help = new JButton("Help");
		JButton resume = new JButton("Resume");
		JButton restart = new JButton("Restart");
		
		home.setPreferredSize(new Dimension(200,100));
		help.setPreferredSize(new Dimension(200,100));
		resume.setPreferredSize(new Dimension(200,100));
		restart.setPreferredSize(new Dimension(200,100));
		
		home.addActionListener(ac7.getBackHandler());
		help.addActionListener(this);
		resume.addActionListener(this);
		restart.addActionListener(this);
		
		add(new AddButton(resume, 38));
		add(new AddButton(help, 39));
		add(new AddButton(restart, 38));
		add(new AddButton(home, 39));
	}
	// paints the background
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	// switches cards depending on which button the user entered
	public void actionPerformed(ActionEvent evt)
	{
		String diff = ac7.getOptionsPanel().getDifficulty();
		String action = evt.getActionCommand();
		String mode = ac7.getOptionsPanel().getMode();
		if (mode.equals("Trigonometry"))
			mode = "Trig instructions";
		else if (mode.equals("Probability"))
			mode = "prob instructions";
		else
			mode = "stats instructions";
		int timeLeft = ac7.getMainPanel().getTimeLeft();
		if (action.equals("Resume"))
		{
			if (timeLeft > 0)
			{
				ac7.getMainPanel().getGameTimer().start();
				cards7.show(ac7, "Main");
				ac7.getHP().setPanel(mode);
			}
			else
				ac7.getMainPanel().stopGame();
		}
		else if (action.equals("Restart"))
		{
			ac7.getMainPanel().setVars(diff);
			cards7.show(ac7, "Main");
			ac7.getMainPanel().getGameTimer().start();
			ac7.getGenerateQuestions().getQuestion();
			ac7.getGenerateQuestions().getPQ().resetAsked();
			ac7.getMainPanel().getBMP().resetSlider();
			ac7.getHP().setPanel(mode);
		}
		else if (action.equals("Help"))
			cards7.show(ac7, "Help");
	}
}
// andrew, mitul
class GenerateQuestions
{
	private String[] answerOptions; // the set of options
	private String answer; // the correct answer
	private int answerIndex; // the index of the correct answer
	private TrigQuestions tq; // instance of TrigQuestions
	private AddCards ac8; // instance of AddCards
	private ProbQuestions pq; // instance of ProbQuestions
	private StatsQuestions sq; // instance of StatsQuestions
	// mitul
	// sets the FVs
	public GenerateQuestions(AddCards acIn)
	{
		ac8 = acIn;
		tq = new TrigQuestions(this);
		pq = new ProbQuestions(this);
		sq = new StatsQuestions(this);
		answerOptions = new String[4];
		answer = new String("");
		answerIndex = 0;
	}
	// mitul
	// gets the question for the correct game mode
	public void getQuestion()
	{
		String game = ac8.getOptionsPanel().getMode();
		String difficulty = ac8.getOptionsPanel().getDifficulty();
		if (game.equals("Trigonometry") && difficulty.equals("Simple"))
		{
			tq.easyTrigQuestion();
		}
		else if (game.equals("Trigonometry") && difficulty.equals("Difficult"))
			tq.difficultTrigQuestion();
		else if (game.equals("Probability") && difficulty.equals("Simple"))
			pq.runEasyMode();
		else if (game.equals("Probability") && difficulty.equals("Difficult"))
			pq.runDiffMode();
		else if (game.equals("Statistics") && difficulty.equals("Simple"))
			sq.runSimp();
		else if (game.equals("Statistics") && difficulty.equals("Difficult"))
			sq.runDifficult();
	}
	
	// andrew
	// sets the other answer options - the wrong ones
	// svaes everything to the anserOptions array
	public void setAnswerOptions(double answerIn, int indexIn, String questionIn2)
	{
		if (ac8.getOptionsPanel().getMode().equals("Trigonometry"))
		{
			answerIndex = indexIn;
			answer = String.format("%.2f", answerIn);
			if(answerIn > 10 || answerIn < -10)
				answer = "Infinity";
			answerOptions[answerIndex] =  answer;
			for (int j = 0; j < answerOptions.length; j++)
			{
				if (j != answerIndex)
				{
					answerOptions[j] = getWrongAnswers(questionIn2);
				}
			}
		}
		else if (ac8.getOptionsPanel().getMode().equals("Probability"))
		{
			answerIndex = indexIn;
			if (answerIn < 1)
				answer = "" + String.format("%.6f", answerIn);
			else
				answer  = "" + answerIn;
			answerOptions[answerIndex] = answer;
			for (int i = 0; i < answerOptions.length; i++)
			{
				if (i != answerIndex)
				{
					if (answerIn < 1)
					{
						double addTo = 0.0;
						do
						{
							addTo = (Math.random()*1);
						} while (addTo == 0.0);
						answerOptions[i] = "" + String.format("%.6f", (answerIn + addTo));
					}
					else
					{
						int addTo = 0;
						do
						{
							addTo = (int)(Math.random()*201 - 100);
						} while (addTo == 0);
						answerOptions[i] = "" + (answerIn + addTo);
					}
				}
			}
		}
		else
		{
			answerIndex = indexIn;
			answer  = String.format("%.4f", answerIn);
			answerOptions[answerIndex] = answer;
			for (int m = 0; m < answerOptions.length; m++)
			{
				if (m != answerIndex)
				{
					answerOptions[m] = String.format("%.4f", (Math.random()*1));
				}
			}
		}
		// sets the question and answer to teh components
		if (ac8.getOptionsPanel().getInput().equals("Keyboard"))
		{
			ac8.getMainPanel().getLabelLayout().setQuestions(answerOptions, questionIn2);
		}
		else
			ac8.getMainPanel().getButtonLayout().setQuestions(answerOptions, questionIn2);
	}
	// sets the options if the answer is a String
	public void setArrayWithOptions(String[] optionsIn, int indexIn, String questionIn)
	{
		for (int i = 0; i < optionsIn.length; i++)
		{
			answerOptions[i] = optionsIn[i];
		}
		answerIndex = indexIn;
		answer = answerOptions[answerIndex];
		if (ac8.getOptionsPanel().getInput().equals("Keyboard"))
		{
			ac8.getMainPanel().getLabelLayout().setQuestions(answerOptions, questionIn);
		}
		else
			ac8.getMainPanel().getButtonLayout().setQuestions(answerOptions, questionIn);
	}
	// returns the instance of ProbQuestions
	public ProbQuestions getPQ()
	{
		return pq;
	}
	
	// mitul, getter  method for the answer index
	public int getAnswerIndex()
	{
		return answerIndex;
	}
	// andrew, returns one of the wrong anawer choices - only used for trig mode
	public String getWrongAnswers(String questionIn)
	{
		String wrong = new String("");
		if (ac8.getOptionsPanel().getMode().equals("Trigonometry"))
		{
			if (questionIn.indexOf("sin") != -1 || questionIn.indexOf("cos") != -1)
			{
				wrong += String.format("%.2f", (Math.random()*2 - 1 ));
			}
			else if (questionIn.indexOf("csc") != -1 || questionIn.indexOf("sec") != -1)
			{
				int posOrNeg = (int)(Math.random()*2 + 1);
				if (posOrNeg == 1)
				{
					wrong += String.format("%.2f", (Math.random() * 4 + 1));
				}
				else
				{
					wrong += String.format("%.2f", (Math.random()* 4 - 5));
				}
			}
			else
				wrong += String.format("%.2f", (Math.random()*10 - 5));
		}
		return wrong;
	}
}
// andrew
class TrigQuestions
{
	private String[] trigFunctions; // 6 trig functions
	private double[] angleMeasures; // simple angle measures
	private double angle; // the angle used
	private GenerateQuestions gq2; // instance of GenerateQuestions
	
	// sets everything
	public TrigQuestions(GenerateQuestions gqIn)
	{
		gq2 = gqIn;
		trigFunctions = new String[]{"sin", "cos", "tan", "csc", "sec", "cot"};
		angleMeasures = new double[]{0, 30, 45, 60, 90};
		angle = 0;
	}
	
	// generates a question for simple mode
	public void easyTrigQuestion()
	{
		int num = (int)(Math.random()*6);
		String question = new String("");
		question += trigFunctions[num] + " (";
		num = (int)(Math.random()*5);
		int mult = (int)(Math.random() * 4  + 1);
		angle = (angleMeasures[num]*mult);
		question +=  angle + ")";
		getAnswer(question);
	}
	// generates a question for difficult mode
	public void difficultTrigQuestion()
	{
		int num = (int)(Math.random()*6);
		String question = new String("");
		question += trigFunctions[num] + " (";
		num = (int)(Math.random()*5);
		int mult = (int)(Math.random() * 33 - 16);
		angle = (angleMeasures[num]*mult);
		question +=  angle + ")";
		getAnswer(question);
	}
	
	// gets the answer to the question
	// converts degrees to radians and solves
	public void getAnswer(String questionIn)
	{
		int index = (int)(Math.random()*4);
		double answer = 0.0;
		angle = angle * (Math.PI/180);
		if(questionIn.indexOf("sin") != -1)
			answer = Math.sin(angle);
		else if (questionIn.indexOf("cos") != -1)
			answer = Math.cos(angle);
		else if (questionIn.indexOf("tan") != -1)
			answer = Math.tan(angle);
		else if (questionIn.indexOf("csc") != -1)
			answer = 1/(Math.sin(angle));
		else if (questionIn.indexOf("sec") != -1)
			answer = 1/(Math.cos(angle));
		else if (questionIn.indexOf("cot") != -1)
			answer = 1/(Math.tan(angle));
		gq2.setAnswerOptions(answer, index, questionIn);
	}
}

// mitul, andrew
class ProbQuestions
{
	private Scanner reader; // Scanner object
	private String namedFile; // name of file
	private boolean[] asked; // to see if the word has already been used
	private String[] type; // 2 different prompts
	private int[] repeats; // to see if letters repeat
	private String word; // the word the use
	private String finalQuestion; // the question
	private GenerateQuestions gq3; // the GeneralInstructions alias
	
	// sets everything
	// both
	public ProbQuestions(GenerateQuestions gqIn)
	{
		gq3 = gqIn;
		reader = null;
		namedFile = new String("words.txt");
		type = new String[] {"How many words of", "How many different ways"};
		asked = new boolean[168];
		repeats = new int[26];
		word = new String("");
		finalQuestion = new String("");
	}
	
	// runs the process for simple mode
	// both
	public void runEasyMode()
	{
		repeats = new int[26];
		getWord();
		double selectRand = (int)(Math.random()*2);
		if (selectRand == 0)
			selectRand = getCriteria1();
		else if (selectRand == 1)
			selectRand = getCriteria2();
		int index = (int)(Math.random()*4);
		gq3.setAnswerOptions(selectRand, index, finalQuestion);
	}
	// runs difficult mode
	public void runDiffMode()
	{
		double answerOut = diffQuestion();
		int index = (int)(Math.random()*4);
		gq3.setAnswerOptions(answerOut, index, finalQuestion);
	}
	// restes the asked array
	public void resetAsked()
	{
		asked = new boolean[168];
	}
	
	// andrew
	// reads the file to get a random word
	public void getWord()
	{
		File file = new File(namedFile);
		try
		{
			reader = new Scanner(file);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("\n\nThe file " + namedFile + " could not be found.\n\n");
			System.exit(7);
		}
		int randNum = -1;
		do
		{
			randNum = (int)(Math.random() * 168);
		} while (asked[randNum]);
		asked[randNum] = true;
		for(int i = 0; i < randNum; i++)
		{
			reader.nextLine();
		}
		word = reader.nextLine().trim();
		reader.close();
	}
	
	// andrew
	// uses prompt 1 to generate a random question
	// how many different words of ___ can be written from ____
	public double getCriteria1()
	{
		int howMany = (int)(Math.random()*(word.length()-1) + 1);
		double answer = getFactorial(word.length())/(getFactorial(word.length() - howMany));
		finalQuestion = type[0] + " " + howMany + " can be written from '" + word + "'?";
		return answer;
	}
	
	// recursive method for factorials
	// mitul
	public long getFactorial(int numIn)
	{
		if (numIn == 0)
			return 1;
		else
		{
			return numIn * getFactorial(numIn-1);
		}
	}
	
	// mitul
	// uses prompt 2 to generate a random question
	// how many different ways can you write ____
	public double getCriteria2()
	{
		char atI = '1';
		word = word.toUpperCase();
		for (int i = 0; i < word.length(); i++)
		{
			atI = word.charAt(i);
			repeats[atI - 65]++;
		}
		double answer = getFactorial(word.length());
		for (int j = 0; j < repeats.length; j++)
		{
			answer /= getFactorial(repeats[j]);
		}
		word = word.toLowerCase();
		finalQuestion = type[1] + " can you write the word '" + word + "'?";
		return answer;
	}
	
	// generates the question for difficult mode
	// andrew
	// if I have __ red, __ blue, and __ white marbles, what is the prob of getting
	// at least ___ (color) marbles if ___ are drawn?
	public double diffQuestion()
	{
		int amtRed = (int)(Math.random()*5 + 2);
		int amtBlue = (int)(Math.random()*5 + 2);
		int amtWhite = (int)(Math.random()*5 + 2);
		int total = amtRed + amtBlue + amtWhite;
		int searchFor = (int)(Math.random()*3);
		int[] eachOne = new int[3];
		int startAt = 0;
		String color = "";
		eachOne[0] = amtRed;
		eachOne[1] = amtBlue;
		eachOne[2] = amtWhite;
		int draw = 0;
		// decides which color to search for
		if (searchFor == 0)
		{
			color = "red";
			draw = (int)(Math.random() * (eachOne[0] - 1) + 1); 
		}
		else if (searchFor == 1)
		{
			color = "blue";
			draw = (int)(Math.random() * (eachOne[1] - 1) + 1); 
		}
		else
		{
			color = "white";
			draw = (int)(Math.random() * (eachOne[2] - 1) + 1); 
		}
		int howMany = (int)(Math.random()*(draw*(3.0/4)) + 1);
		double answer = 0;
		finalQuestion = String.format("You have %d red, %d blue, and %d white marbles " +
			"in a bag. %d are drawn. What is the probability of getting at least %d %s marbles."
			, amtRed, amtBlue, amtWhite, draw, howMany, color);
		double calculate = 0;
		// calculates the answer
		for (int i = howMany; i <= draw; i++)
		{
			calculate = (double)(getFactorial((eachOne[searchFor])));
			calculate /= (double)(getFactorial((eachOne[searchFor] - i)));
			calculate /= ((double)(getFactorial(i)));
			answer += calculate;
		}
		answer /= ((double)(getFactorial(total))/(double)(getFactorial(total - draw) * getFactorial(draw)));
		return answer;
	}
}

// Mitul
class StatsQuestions 
{
	private GenerateQuestions gq4; // the GenerateQuestions instance
	private double[] area; // areas of the normal curve
	private int mean, sd; // the mean and standard deviation
	private String questionAsked; // the question
	private String[] studies, samples; // the types of data collection, and the types of sampling methods
	
	// sets everything
	public StatsQuestions(GenerateQuestions gqIn)
	{
		gq4 = gqIn;
		area = new double[]{.15, 2.35, 13.5, 34, 34, 13.5, 2.35, .15};
		studies = new String[] {"observational study", "survey", "simulation", "experiment"};
		mean = 0;
		samples = new String[]{"cluster", "stratified", "convenience", "self-selected",
			"systemic"};
		sd = 0;
		questionAsked = new String("");
	}
	// runs difficult mode
	public void runDifficult()
	{
		int ask = (int)(Math.random()*4);
		if (ask < 3)
		{
			double answerOut = getDiffQuestion();
			int indexOut = (int)(Math.random()*4);
			gq4.setAnswerOptions(answerOut, indexOut, questionAsked);
		}
		else
			diffQuest2();
	}
	// gets the first type of difficult question
	// what is the probability a student scores ___ if mean and sd are given
	public double getDiffQuestion()
	{
		mean = (int)(Math.random()*100 + 1);
		sd = (int)(Math.random()*((double)mean/2));
		int numberOfSD = (int)(Math.random()*7 - 3);
		questionAsked = "In a standardized test where\nmean: " + mean + "\nstandard deviation: " 
			+ sd + "\n\nWhat " + "is the probability that a student scored";
		String symbol = "";
		int greatOrLess = (int)(Math.random()*2);
		if (greatOrLess == 0)
			symbol = ">";
		else
			symbol = "<";
			
		double find = (mean + (sd*numberOfSD));
		double answer = 0;
		if (symbol.equals(">"))
		{
			questionAsked += " above a " + find + "?";
			find = 5 + numberOfSD;
			answer = 100;
			if (find < 5)
			{
				for (int i = area.length-1; i > find; i--)
				{
					answer -= area[i];
				}
			}
			else
			{
				answer = 100;
				for (int j = 0; j < find-1; j++)
				{
					answer -= area[j];
				}
			}
		}
		else
		{
			answer = 0;
			questionAsked += " below a " + find + "?";
			find = 5 + numberOfSD;
			for (int m = 0; m < find-1; m++)
			{
				answer += area[m];
			}
		}
		answer /= 100;
		return answer;
	}
	// runs simple mode
	public void runSimp()
	{
		int opt = (int)(Math.random()*3);
		if (opt == 0)
			simpQuest2();	
		else 
			simpQuest1();
			
	}
	// gets question type 1 for simple mode
	// mean and sd are given, which score is ___ standard deviations away
	public void simpQuest1()
	{
		mean = (int)(Math.random()*100 + 1);
		sd = (int)(Math.random()*((double)mean/2));
		int numberOfSD = (int)(Math.random()*7 - 3);
		int answer = mean + (sd*numberOfSD);
		questionAsked = "If\nmean: " + mean + "\nstandard deviation: " + sd + "\n\n" +
			"what value is " + numberOfSD + " standard deviations away?";
		String[] options = new String[4];
		int indexOut = (int)(Math.random()*4);
		options[indexOut] = "" +answer;
		for (int i = 0; i < options.length; i++)
		{
			if (i != indexOut)
			{
				options[i] = "" + (int)(Math.random()*100 + 1);
			}
		}
		
		gq4.setArrayWithOptions(options, indexOut, questionAsked);
	}
	// gets question type 2 for simple mode
	// which sampling method is this -- given from file. 
	public void simpQuest2()
	{
		int get = (int)(Math.random() * 19 + 1);
		Scanner readIn2 = null;
		File scenarios2 = new File("sampling.txt"); // mitul wrote this file
		try
		{
			readIn2 = new Scanner(scenarios2);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("\n\nThe file " + scenarios2 + " could not be found.\n\n");
			System.exit(8);
		}
		for (int i = 0; i < get; i++)
		{
			readIn2.nextLine();
		}
		int[] indices = new int[4];
		int randNum = 0;
		
		for (int j = 0; j < indices.length; j++)
		{
			indices[j] = -1;
		} 
		String scenario = readIn2.nextLine();
		String[] returnOut = new String[4];
		int indexOfScenario = Integer.parseInt(scenario.substring(scenario.lastIndexOf("-")+ 1).trim());
		randNum = (int)(Math.random()*4);
		indices[randNum] = indexOfScenario;
		for (int m = 0; m < indices.length; m++)
		{
			randNum = (int)(Math.random()*5);
			for (int n = 0; n < indices.length; n++)
			{
				if (randNum == indices[n])
				{
					m = 0;
					n = 4;
				}
			}
			indices[m] = randNum;
		}
		int indexOut = 0;
		for (int y = 0; y < indices.length; y++)
		{
			returnOut[y] = samples[indices[y]];
		}
		for (int x = 0; x < indices.length; x++)
		{
			if (indexOfScenario == indices[x])
				indexOut = x;
		}
		questionAsked = "I recently did a survey where "  + scenario.substring(0,
			scenario.lastIndexOf("-")).trim()  + ". What sampling method did I use?";
		gq4.setArrayWithOptions(returnOut, indexOut, questionAsked);
	}
	// gets the second type of difficult question
	// what surveying method is this -- given from file
	public void diffQuest2()
	{
		int get = (int)(Math.random() * 40 + 1);
		Scanner readIn = null;
		File scenarios = new File("studies.txt"); // andrew wrote this file
		try
		{
			readIn = new Scanner(scenarios);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("\n\nThe file " + scenarios + " could not be found.\n\n");
			System.exit(8);
		}
		for (int i = 0; i < get; i++)
		{
			readIn.nextLine();
		}
		int[] indices = new int[4];
		int randNum = 0;
		
		for (int j = 0; j < indices.length; j++)
		{
			indices[j] = -1;
		} 
		
		for (int m = 0; m < indices.length; m++)
		{
			randNum = (int)(Math.random()*indices.length);
			for (int n = 0; n < indices.length; n++)
			{
				if (randNum == indices[n])
				{
					m = 0;
					n = 4;
				}
			}
			indices[m] = randNum;
		}
		String scenario = readIn.nextLine();
		String[] returnOut = new String[4];
		int indexOfScenario = Integer.parseInt(scenario.substring(scenario.lastIndexOf("-")+ 1).trim());
		int indexOut = 0;
		for (int x = 0; x < indices.length; x++)
		{
			if (indexOfScenario == indices[x])
				indexOut = x;
		}
		for (int y = 0; y < indices.length; y++)
		{
			returnOut[y] = studies[indices[y]];
		}
		questionAsked = "I recently did a survey where "  + scenario.substring(0,
			scenario.lastIndexOf("-")).trim()  + ". What surveying method did I use?";
		gq4.setArrayWithOptions(returnOut, indexOut, questionAsked);
	}
}

// mitul
class HelpPanel extends JPanel implements ActionListener
{
	private AddCards ac9; // instance of AddCards
	private CardLayout cards9; // instance of the CardLayout
	private PictPanel pcp; // instance of PictPanel
	private JTextArea instructions; // JTextArea qith instructions
	// adds everything
	public HelpPanel(AddCards acIn, CardLayout cardsIn)
	{
		ac9 = acIn;
		cards9 = cardsIn;
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		JButton back = new JButton("Back");
		JPanel backPanel = new JPanel();
		backPanel.add(back);
		add(backPanel, BorderLayout.SOUTH);
		back.addActionListener(this);
		pcp = new PictPanel();
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		Font instructionFont = new Font("Arial", Font.BOLD, 20);
		instructions = new JTextArea();
		Insets tAInset = new Insets(15, 40, 15, 40);
		instructions.setMargin(tAInset);
		instructions.setLineWrap(true);  
		instructions.setWrapStyleWord(true);
		instructions.setEditable(false);
		instructions.setFont(instructionFont);
		JScrollPane jsp = new JScrollPane(instructions);
		JPanel panelWithPane = new JPanel();
		panelWithPane.setLayout(new BorderLayout());
		panelWithPane.setPreferredSize(new Dimension(1000,350));
		panelWithPane.add(jsp);
		centerPanel.add(panelWithPane, BorderLayout.SOUTH);
		centerPanel.add(pcp, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);
	}
	// sets teh image for the helpPanel
	public void getImage(int selected)
	{
		pcp.setImage(selected);
	}
	// sets the panel with the wanted text file
	public void setPanel(String fileName)
	{
		fileName += ".txt";
		instructions.setText(getTheText(fileName));
	}
	// adds teh questions and answer and player choice to the end of teh TextArea
	// only if user is sent here from answering wrong
	public void addQuestionToText(String fileNameIn, String question, String right, String wrong)
	{
		fileNameIn += ".txt";
		instructions.setText(getTheText(fileNameIn) + "\n\nQUESTION: " + question + "\n\n" +
			"YOUR ANSWER: " + wrong + "\nRIGHT ANSWER: " + right);
	}
	
	// reads the correct file
	public String getTheText(String fileNameIn4)
	{
		Scanner fileReader = null;
		File inFile = new File(fileNameIn4);
		try
		{
			fileReader = new Scanner(inFile);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("\n\nThe file " + fileNameIn4 + " could not be found\n\n");
			System.exit(6);
		}
		String added = new String("");
		while (fileReader.hasNext())
		{
			added += fileReader.nextLine() + "\n";
		}
		return added;
	}
	// switches back to the pause panel
	public void actionPerformed(ActionEvent evt)
	{
		cards9.show(ac9, "Pause");
	}
}

// andrew
class PictPanel extends JPanel
{
	private Image[] helpImages; // the images
	private int select; // which image to use
	
	// sets everything
	public PictPanel()
	{
		String[] namesOfImages = new String[]{"unitcircle.png", "comb_perm.jpg", "normal.png"};
		helpImages = new Image[3];
		select = 0;
		
		for (int i = 0; i < helpImages.length; i++)
		{
			helpImages[i] = getHelpImage(namesOfImages[i]);
		}
	}
	
	// sets which image to use
	public void setImage(int selectIn)
	{
		select = selectIn;
		repaint();
	}
	
	// reads in all the images
	public Image getHelpImage(String nameOfImage)
	{
		Image imageOut = null;
		String fileName1 = nameOfImage;
		File openingImageFile = new File(fileName1);
		try
		{
			imageOut = ImageIO.read(openingImageFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\nThe image " + fileName1 + " could not be found\n\n");
			e.printStackTrace();
		}
		return imageOut;
	}
	
	// draws the image
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(helpImages[select], 0,0, this.getWidth(), this.getHeight(), this);
	}
}

// andrew, mitul
class EndPanel extends JPanel implements ActionListener
{
	private Scanner fileReader; // Scanner object
	private PrintWriter fileWriter; // PrintWriter object
	private JLabel highScore, playerScore, place; // stats
	private AddCards ac10; // instance of AddCards
	private CardLayout cards10; // CardLayout for AddCards
	
	// sets and adds everything
	// andrew
	public EndPanel(AddCards acIn, CardLayout cardsIn)
	{
		Font labelFont = new Font("Arial", Font.BOLD, 30);
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3,1, 0,100));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 300,0));
		centerPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setBackground(Color.DARK_GRAY);
		ac10 = acIn;
		cards10 = cardsIn;
		fileReader = null;
		fileWriter = null;
		highScore = new JLabel();
		playerScore = new JLabel();
		place = new JLabel();
		highScore.setOpaque(true);
		playerScore.setOpaque(true);
		place.setOpaque(true);
		highScore.setFont(labelFont);
		playerScore.setFont(labelFont);
		place.setFont(labelFont);
		centerPanel.add(highScore);
		centerPanel.add(playerScore);
		centerPanel.add(place);
		JButton playAgain = new JButton("Play again");
		JButton home = new JButton("Home");
		playAgain.addActionListener(this);
		home.addActionListener(ac10.getBackHandler());
		bottomPanel.add(playAgain);
		bottomPanel.add(home);
		add(bottomPanel, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	// runs the class - to read and write the file
	// andrew
	public void run(String nameIn, int scoreIn)
	{
		playerScore.setText("Your score: " + scoreIn);
		String fileName = ac10.getOptionsPanel().getMode() + "-" + 
			ac10.getOptionsPanel().getDifficulty() + ".txt";
		String result = readFile(fileName, nameIn, scoreIn);
		writeToFile(fileName, result);
		setHighScore(fileName);
	}
	
	// finds the high score
	// andrew
	public void setHighScore(String fileNameIn3)
	{
		File inFile = new File(fileNameIn3);
		try
		{
			fileReader = new Scanner(inFile);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("\n\nThe file " + fileNameIn3 + " could not be found\n\n");
			System.exit(4);
		}
		String line = new String("");
		do
		{
			line = fileReader.nextLine();
			if(!line.equals(""))
				highScore.setText("High Score: " + 
					Integer.parseInt(line.substring(line.lastIndexOf("-") + 1).trim()));
		} while (line.equals(""));
	}
	
	// reads the file and places the players name and score in the appropriate area
	// mitul
	public String readFile(String fileNameIn, String nameIn2, int scoreIn2)
	{
		String resultOut = new String("");
		File file = new File(fileNameIn);
		try
		{
			fileReader = new Scanner(file);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("\n\nThe file " + fileNameIn + " could not be found\n\n");
			System.exit(2);
		}
		int placement = 0;
		boolean placed = false;
		String firstLine = new String("");
		String lastLine = new String("");
		// makes sure the first line is not a blank one
		if (fileReader.hasNext())
		{
			while (lastLine.equals(""))
			{
				lastLine = fileReader.nextLine();
			} 
		}
		// reads the file 
		while (fileReader.hasNext())
		{
			placement++;
			firstLine = fileReader.nextLine();
			if (!placed &&
			Integer.parseInt(lastLine.substring(lastLine.lastIndexOf("-") +1).trim()) <= scoreIn2)
			{
				resultOut += nameIn2 + "\t-\t" + scoreIn2 + "\n";
				resultOut += lastLine + "\n";
				place.setText("Leaderboard #: " + placement);
				placed = true; 
			}
			else if (!placed && 
				Integer.parseInt(lastLine.substring(lastLine.lastIndexOf("-") +1).trim()) >= scoreIn2 && 
				Integer.parseInt(firstLine.substring(firstLine.lastIndexOf("-") + 1).trim()) <= scoreIn2)
			{
				resultOut += lastLine + "\n";
				resultOut += nameIn2 + "\t-\t" + scoreIn2 + "\n";
				place.setText("Leaderboard #: " + (placement + 1));
				placed = true; 
			}
			else
				resultOut += lastLine + "\n";
			lastLine = firstLine;
		}
		resultOut += firstLine + "\n";
		// puts the name and score at the bottom is its not already added
		if (!placed && lastLine.lastIndexOf("-") != -1)
		{
			if (Integer.parseInt(lastLine.substring(lastLine.lastIndexOf("-") +1).trim()) <= scoreIn2)
			{
				resultOut += nameIn2 + "\t-\t" + scoreIn2 + "\n";
				resultOut += lastLine + "\n";
				place.setText("Leaderboard #: 1");
				placed = true;
			}
		}
		if (!placed)
		{
			resultOut += nameIn2 + "\t-\t" + scoreIn2 + "\n";
			place.setText("Leaderboard #: " + (placement + 1));
		}
		return resultOut;
	}
	
	// writes to the updated file
	// mitul
	public void writeToFile(String fileNameIn2, String resultIn)
	{
		File outFile = new File(fileNameIn2);
		try
		{
			fileWriter = new PrintWriter(outFile);
		}
		catch(IOException e)
		{
			System.err.println("\nERROR: Cannot create the file \"" + fileNameIn2 +
				"\".");
			 System.exit(3);
		}
		fileWriter.print(resultIn);
		fileWriter.close();
	}
	
	// switches to different cards when needed
	// andrew
	public void actionPerformed(ActionEvent evt)
	{
		ac10.getMainPanel().setVars(ac10.getOptionsPanel().getDifficulty());
		cards10.show(ac10, "Main");
		if (ac10.getOptionsPanel().getInput().equals("Mouse"))
			ac10.getMainPanel().getMainCards().show(ac10.getMainPanel().getMainPanelParent(), "Button Mode");
		else
			ac10.getMainPanel().getMainCards().show(ac10.getMainPanel().getMainPanelParent(), "Keyboard Mode");
		ac10.getMainPanel().getGameTimer().start();
		ac10.getGenerateQuestions().getQuestion();
	}
}

