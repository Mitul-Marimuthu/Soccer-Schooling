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

import java.io.File;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

class MainPanel extends JPanel
{
	private CardLayout mainGameCards;
	private JPanel mainPanelParent;
	private Timer gameTimer;
	private AddPanels ap4;
	private CardLayout cards3;
	private Options o3;
	
	public MainPanel(AddPanels apIn, CardLayout cardsIn, Options opIn)
	{
		mainGameCards = new CardLayout();
		mainPanelParent = new JPanel();
		mainPanelParent.setLayout(mainGameCards);
		mainPanelParent.add(new SetButtonLayout(), "Button Mode");
		add(mainPanelParent, BorderLayout.CENTER);
		ap4 = apIn;
		cards3 = cardsIn;
		o3 = opIn;
		setLayout(new BorderLayout());
	}
	
	public void setMode(String inputIn)
	{
		if (inputIn.equals("Mouse"))
			mainGameCards.show(mainPanelParent, "Button Mode");
	}
}

class SetButtonLayout extends JPanel
{
	private JButton option1, option2, option3, option4;
	
	public SetButtonLayout()
	{
		option1 = option2 = option3 = option4 = new JButton("option");
		setLayout(new FlowLayout(FlowLayout.CENTER, 450, 100));
		JTextArea question = new JTextArea("question", 10, 15);
		add(option1);
		add(option2);
		add(option3);
		add(option4);
	}
}
