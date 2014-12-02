package tetris;

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

public class TitlePanel extends JPanel
{
	JLabel title;
	
	public TitlePanel()
	{
		title = new JLabel("Tetris");
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		add(title, gbc);
		this.setBackground(Color.white);
		Border paddingBorder = BorderFactory.createEmptyBorder(0,20,0,20);
		title.setBorder(paddingBorder);
		
		try
		{
			File f = new File("Champagne & Limousines Bold Italic.ttf");
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,
					in);
			Font dynamicFont28Pt = dynamicFont.deriveFont(38f);
			title.setFont(dynamicFont28Pt);

		}
		catch(Exception e)
		{
			System.out.println("Loading Font Didn't Work");
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200,80);

	}
}
