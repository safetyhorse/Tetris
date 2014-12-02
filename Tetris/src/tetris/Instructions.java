package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

public class Instructions extends JPanel
{
	JLabel instructionbar;
	String instructiontext;
	
	public Instructions()
	{
		instructiontext = ""
				+ "<html><body><table>"
				+ "<tr><td align='right'>p</td><td>=</td><td align='left'>Pause</td></tr>"
				//+ "<tr><td align='right'>d</td><td>=</td><td align='left'>drop faster</td></tr>"				
				+ "<tr><td align='right'>\u2190</td><td>=</td><td align='left'>Left</td></tr>"
				+ "<tr><td align='right'>\u2192</td><td>=</td><td align='left'>Right</td></tr>"
				+ "<tr><td align='right'>\u2191</td><td>=</td><td align='left'>Rotate Left</td></tr>"
				+ "<tr><td align='right'>\u2193</td><td>=</td><td align='left'>Rotate Right</td></tr>"
				+ "<tr><td align='right'>space</td><td>=</td><td align='left'>Drop Faster</td></tr>"
				+ "<tr><td align='right'>r</td><td>=</td><td align='left'>Restart</td></tr>"
				+ "</table></body></html>";
		instructionbar = new JLabel(instructiontext);
		//instructionbar.setBorder(BorderFactory.createLineBorder(Color.green));
		//this.setBorder(BorderFactory.createLineBorder(Color.yellow));
		//instructionbar.setAlignmentY(Component.CENTER_ALIGNMENT);
		//add(instructionbar, BorderLayout.CENTER);
		//this.setAlignmentY(Component.CENTER_ALIGNMENT);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		add(instructionbar, gbc);
		this.setBackground(Color.white);
		//this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		/*this.setBorder(new CompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 2, Color.white),
				BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK)));*/
		
		try
		{
			File f = new File("Champagne & Limousines Bold Italic.ttf");
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,
					in);
			Font dynamicFont28Pt = dynamicFont.deriveFont(18f);
			instructionbar.setFont(dynamicFont28Pt);

		}
		catch(Exception e)
		{
			System.out.println("Loading Font Didn't Work");
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200,600);

	}
}
