/* Mitul Marimuthu, Andrew Zhou
 * SoccerSchooling2.java
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

// andrew, mitul
// Class with main
public class SoccerSchooling2
{
	public SoccerSchooling2()
	{
	}
	
	public static void main(String[] args)
	{
		SoccerSchooling2 game = new SoccerSchooling2();
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
	private CardLayout cards; // the CardLayout
	private Opening o; // instance of the opening page
	private Options op; // instance of options panel (can be reset)
	private HighScores hs; // instance of leaderboard panel
	private GeneralInstructions gi; // instance of instructions panel 
	private MainPanel mp; // instance of the main game panel
	private BackButtonHandler bbh; // instance of the home button handler class
	private GenerateQuestions gq; // instance of the class that generates questions
	
	public AddCards()
	{
		cards = new CardLayout();
		setLayout(cards);
		bbh = new BackButtonHandler(this, cards);
		o = new Opening(this, cards);
		op = new Options(bbh, this, cards);
		hs = new HighScores(bbh);
		gi = new GeneralInstructions(bbh);
		mp = new MainPanel(this, cards);
		PausePanel pp = new PausePanel(this, cards);
		gq = new GenerateQuestions(this);
		HelpPanel hp = new HelpPanel(this, cards);
		add(o, "Opening");
		add(op, "Options");
		add(hs, "High Scores");
		add(gi, "Instructions");
		add(mp, "Main");
		add(pp, "Pause");
		add(hp, "Help");
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
	// moves the foot
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
	
	// Handler class for the JTextField
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
	class OpeningButtonsHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String action = evt.getActionCommand();
			if (nameTyped && !name.getText().equals("Enter your name"))
			{
				if (action.equals("Play"))
					cards1.show(ac1, "Options");
				else if (action.equals("Instructions"))
					cards1.show(ac1, "Instructions");
				else if (action.equals("Leaderboard"))
					cards1.show(ac1, "High Scores");
			}
			else
				repaint();
			if (action.equals("Exit"))
				System.exit(1);
		}
	}
}
// class where all the buttons are added (just home buttons for now)
class AddButton extends JPanel
{
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
	private AddCards ap5; // instnace of AddCards
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
	 // sets the buttons and button groups
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
	// resets the "animation"
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
	public void actionPerformed(ActionEvent evt)
	{
		if (counter >= 4)
		{
			cards5.show(ap5, "Main");
			ap5.getMainPanel().setMode(inputMethod);
			ap5.getMainPanel().setVars(difficulty);
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
		labelPanel.setPreferredSize(new Dimension(1000,100));
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
	// panel with gridlayout that displays instructions
	public InstructionsPanel()
	{
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
}
// mitul
// work in progress
class HighScores extends JPanel
{
	public HighScores(BackButtonHandler bbhIn)
	{
		setBackground(Color.RED);
		setLayout(new BorderLayout());
		JButton back3 = new JButton("Home");
		add(new AddButton(back3), BorderLayout.SOUTH);
		back3.addActionListener(bbhIn);
	}
}

// Handler class for the home button
class BackButtonHandler implements ActionListener
{
	private AddCards ac2; // instance of AddCards
	private CardLayout cards2; // instance of CardLayout
	
	public BackButtonHandler(AddCards acIn, CardLayout cardsIn)
	{
		ac2 = acIn;
		cards2 = cardsIn;
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		cards2.show(ac2, "Opening");
		ac2.getOptionsPanel().reset();
		ac2.getMainPanel().getGameTimer().stop();
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
	
	public Animation getAnimation()
	{
		return anime;
	}
	
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
	
	// selectes which game panel should be shown
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
		bmp.setLabels(score, time);
	}
	
	// gets the player name for the top panel
	public void getNameForTopPanel(String nameIn)
	{
		tmp.setName(nameIn);
	}
	
	// stops the game when time is up
	public void stopGame()
	{
		anime.getAnimationTimer().stop();
		gameTimer.stop();
		anime.resetFrame();
		ap4.getOptionsPanel().reset();
		cards3.show(ap4, "Opening");
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
	// figures out where the goalie and ball need to go
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
	
	// does the animating
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
	
	// sets everything
	public SetButtonLayout(MainPanel mpIn)
	{
		mp2 = mpIn;
		goalImage2 = mp2.getGoalImage();
		setBackground(Color.GREEN);
		option1 = new JButton("option1");
		option1.setPreferredSize(new Dimension(100, 50));
		option2 = new JButton("2");
		option2.setPreferredSize(new Dimension(100, 50));
		option3 = new JButton("3");
		option3.setPreferredSize(new Dimension(100, 50));
		option4 = new JButton("4");
		option4.setPreferredSize(new Dimension(100, 50));
		setLayout(new FlowLayout(FlowLayout.CENTER, 300, 100));
		question = new JTextArea("question", 5, 30);
		Font instructionFont = new Font("Arial", Font.BOLD, 20);
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
	public void actionPerformed(ActionEvent evt)
	{
		String choice = evt.getActionCommand();
		boolean correct = false;
		int searchFor = mp2.getAddCards().getGenerateQuestions().getAnswerIndex() + 1;
		String playerChoice = choice.substring(0, choice.indexOf("."));
		
		String search = "" + searchFor;
		if (choice.indexOf(search) == 0)
		{
			correct = true;
		}
		mp2.getMainCards().show(mp2.getMainPanelParent(), "Animation");
		mp2.getAnimation().startFrame(correct, Integer.parseInt(playerChoice), searchFor - 1);
	}
	
	// sets the buttons and JTextArea with thee question and options
	public void setQuestions(String[] optionsIn, String questionIn)
	{
		option1.setText("1. " + optionsIn[0]);
		option2.setText("2. " + optionsIn[1]);
		option3.setText("3. " + optionsIn[2]);
		option4.setText("4. " + optionsIn[3]);
		question.setText(questionIn);
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
	
	// sets the labels
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
	public void actionPerformed(ActionEvent evt)
	{
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
	
	// sets everything
	public SetLabelLayout(MainPanel mpIn)
	{
		answered = false;
		mp3 = mpIn;
		goalImage3 = mp3.getGoalImage();
		setBackground(Color.GREEN);
		option1 = new JLabel("option1");
		option1.setPreferredSize(new Dimension(100, 50));
		option1.setBackground(Color.LIGHT_GRAY);
		option1.setOpaque(true);
		
		option2 = new JLabel("2");
		option2.setPreferredSize(new Dimension(100, 50));
		option2.setBackground(Color.LIGHT_GRAY);
		option2.setOpaque(true);
		
		option3 = new JLabel("3");
		option3.setPreferredSize(new Dimension(100, 50));
		option3.setBackground(Color.LIGHT_GRAY);
		option3.setOpaque(true);
		
		option4 = new JLabel("4");
		option4.setPreferredSize(new Dimension(100, 50));
		option4.setBackground(Color.LIGHT_GRAY);
		option4.setOpaque(true);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 300, 100));
		question = new JTextArea("QUESTION", 5, 30);
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
	// requests focus
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
		
			String search = "" + searchFor;
			if (choice.indexOf(search) == 0)
			{
				correct = true;
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
		if (action.equals("Resume"))
		{
			cards7.show(ac7, "Main");
			ac7.getMainPanel().getGameTimer().start();
		}
		else if (action.equals("Restart"))
		{
			ac7.getMainPanel().setVars(diff);
			cards7.show(ac7, "Main");
			ac7.getMainPanel().getGameTimer().start();
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
	
	// mitul
	// sets the FVs
	public GenerateQuestions(AddCards acIn)
	{
		ac8 = acIn;
		tq = new TrigQuestions(this);
		answerOptions = new String[4];
		answer = new String("");
		answerIndex = 0;
	}
	// mitul
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
	}
	
	//andrew
	// sets the other answer options 
	public void setAnswerOptions(double answerIn, int indexIn, String questionIn2)
	{
		if (ac8.getOptionsPanel().getMode().equals("Trigonometry"))
		{
			answerIndex = indexIn;
			answer = String.format("%.2f", answerIn);
			if(answerIn > 10 || answerIn < -10)
				answer = "Infinity";
			answerOptions[answerIndex] =  answer;
			for (int i = 0; i < answerOptions.length; i++)
			{
				if (i != answerIndex)
				{
					answerOptions[i] = getWrongAnswers(questionIn2);
				}
			}
		}
		if (ac8.getOptionsPanel().getInput().equals("Keyboard"))
		{
			ac8.getMainPanel().getLabelLayout().setQuestions(answerOptions, questionIn2);
		}
		else
			ac8.getMainPanel().getButtonLayout().setQuestions(answerOptions, questionIn2);
	}
	
	// mitul, getter  method for the answer index
	public int getAnswerIndex()
	{
		return answerIndex;
	}
	// andrew, returns one of the wrong anaswer choices
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

// mitul
class HelpPanel extends JPanel implements ActionListener
{
	private AddCards ac9; // instance of AddCards
	private CardLayout cards9; // instance of the CardLayout
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
	}
	// switches back to the puase panel
	public void actionPerformed(ActionEvent evt)
	{
		cards9.show(ac9, "Pause");
	}
}
