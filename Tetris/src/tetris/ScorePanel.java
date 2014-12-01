package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ScorePanel extends JPanel
{
	JLabel statusbar;
	
	public ScorePanel(Tetris parent)
	{
		statusbar =  parent.getStatusBar();
		//statusbar = new JLabel("GAME OVER YOU HOSER!");
		//statusbar.setBorder(BorderFactory.createLineBorder(Color.green));
		statusbar.setBorder(new EmptyBorder(20,0,10,0));
		//this.setLayout(new GridBagLayout());
		//GridBagConstraints gbc = new GridBagConstraints();
		add(statusbar, BorderLayout.NORTH);
		statusbar.setText("GAME OVER GAME OVER!");
	}
	
	public ScorePanel setLabelText(String newText)
	{
		statusbar.setText(newText);
		return null;
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200,400);

	}/**/
}
