package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ScorePanel extends JPanel
{
	JLabel statusbar;
	
	public ScorePanel(Tetris parent)
	{
		statusbar =  parent.getStatusBar();
		//statusbar = new JLabel("GAME OVER YOU HOSER!");
		//statusbar.setBorder(BorderFactory.createLineBorder(Color.green));
		//statusbar.setBorder(new EmptyBorder(20,0,10,0));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		add(statusbar, gbc);
		//add(statusbar, BorderLayout.NORTH);
		//statusbar.setText("GAME OVER GAME OVER!");
		this.setBackground(Color.white);
		Border paddingBorder = BorderFactory.createEmptyBorder(0,10,0,10);
		statusbar.setBorder(paddingBorder);
		//this.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK));
		/*this.setBorder(new CompoundBorder(
				BorderFactory.createMatteBorder(0, 2, 0, 0, Color.white),
				BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK)));*/
		
		try
		{
			File f = new File("Champagne & Limousines Bold Italic.ttf");
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,
					in);
			Font dynamicFont28Pt = dynamicFont.deriveFont(24f);
			statusbar.setFont(dynamicFont28Pt);

		}
		catch(Exception e)
		{
			System.out.println("Loading Font Didn't Work");
		}
	}
	
	public ScorePanel setLabelText(String newText)
	{
		statusbar.setText(newText);
		return null;
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200,600);

	}/**/
}
