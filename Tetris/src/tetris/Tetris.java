package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Tetris extends JFrame {

	JLabel statusbar;
	ScorePanel scorePanel;


	public Tetris() {

		statusbar = new JLabel(" 0");
		statusbar.setBorder(BorderFactory.createLineBorder(Color.green));
		//statusbar.setSize(200,400);
		//add(statusbar, BorderLayout.EAST);
		ScorePanel scorePanel = new ScorePanel();
		add(scorePanel, BorderLayout.EAST);
		Instructions instructions = new Instructions();
		add(instructions, BorderLayout.WEST);
		Board board = new Board(this);
		add(board, BorderLayout.CENTER);
		board.start();

		//board.setSize(200, 400);
		//setSize(400,400);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//music();
		pack();
		
		//scorePanel.setLabelText("test!");
	}

	public JLabel getStatusBar() {
		return statusbar;		
	}

	public static void main(String[] args) {

		Tetris game = new Tetris();
		game.setLocationRelativeTo(null);
		game.setVisible(true);

	} 
	
	public static void music()
	{
		 try {
		        // From file
		        Sequence sequence = MidiSystem.getSequence(new File("the_cure-close_to_me.mid"));
		    
		        // From URL
		       // sequence = MidiSystem.getSequence(new URL("http://hostname/midiaudiofile"));
		    
		        // Create a sequencer for the sequence
		        Sequencer sequencer = MidiSystem.getSequencer();
		        sequencer.open();
		        sequencer.setSequence(sequence);
		        sequencer.setLoopCount(1000);
		        // Start playing
		        sequencer.start();
		    } catch (MalformedURLException e) {
		    } catch (IOException e) {
		    } catch (MidiUnavailableException e) {
		    } catch (InvalidMidiDataException e) {
		    }
	}
}