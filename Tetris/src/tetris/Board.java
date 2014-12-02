package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import tetris.Shape.Tetrominoes;


public class Board extends JPanel implements ActionListener {


	final int BoardWidth = 11;
	final int BoardHeight = 22;

	Timer timer;
	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	int numLinesRemoved = 0;
	int curX = 0;
	int curY = 0;
	JLabel statusbar;
	Shape curPiece;
	Tetrominoes[] board;
	int speed;


	public Board(Tetris parent) {
				
		setFocusable(true);
		curPiece = new Shape();
		speed = 400;
		timer = new Timer(speed, this);
		timer.start(); 

		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		//this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
		//this.setBorder(new EmptyBorder(10,100,10,100));
		/*this.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(Color.black, 4),
				BorderFactory.createLineBorder(Color.green, 4)));*/
		statusbar =  parent.getStatusBar();
		board = new Tetrominoes[BoardWidth * BoardHeight];
		addKeyListener(new TAdapter());
		clearBoard(); 
		this.setBackground(Color.white);

	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(300,600);

	}

	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}


	int squareWidth() { return (int) getSize().getWidth() / BoardWidth; }
	int squareHeight() { return (int) getSize().getHeight() / BoardHeight; }
	Tetrominoes shapeAt(int x, int y) { return board[(y * BoardWidth) + x]; }


	public void start()
	{
		/*if (isPaused)
			return;*/

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();
		speed = 400;

		newPiece();
		timer.setDelay(speed);
		timer.start();
		pause();
		statusbar.setText("Press p to start");
	}

	private void pause()
	{	
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {	
			timer.stop();
			statusbar.setText("Paused");			
		} else {
			timer.start();
			statusbar.setText(String.valueOf(numLinesRemoved*1000));
		}
		repaint();
	}

	public void paint(Graphics g)
	{ 
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();


		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
					drawSquare(g, 0 + j * squareWidth(),
							boardTop + i * squareHeight(), shape);
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = curX + curPiece.x(i);
				int y = curY - curPiece.y(i);
				drawSquare(g, 0 + x * squareWidth(),
						boardTop + (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape());
			}
		}
	}

	private void dropDown()
	{
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	private void oneLineDown()
	{
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}


	private void clearBoard()
	{
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	private void pieceDropped()
	{
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[(y * BoardWidth) + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	private void newPiece()
	{
		curPiece.setRandomShape();
		curX = BoardWidth / 2 + 1;
		curY = BoardHeight - 1 + curPiece.minY();

		if (!tryMove(curPiece, curX, curY)) {
			curPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			isStarted = false;
			statusbar.setText(
					"<html><p align='center'>Game Over <br>" 
					+ numLinesRemoved*1000 
					+ " points</p></html>");	
			addKeyListener(new TAdapterOver());
		}
	}

	private boolean tryMove(Shape newPiece, int newX, int newY)
	{
		//pause();
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.x(i);
			int y = newY - newPiece.y(i);
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
				return false;
			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}

		curPiece = newPiece;
		curX = newX;
		curY = newY;
		repaint();
		return true;
	}

	private void removeFullLines()
	{
		int numFullLines = 0;

		for (int i = BoardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < BoardHeight - 1; ++k) {
					for (int j = 0; j < BoardWidth; ++j)
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
			statusbar.setText(String.valueOf(numLinesRemoved*1000));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
			//gradually speed up the rate of decent
			if(speed > 50)
			{
				speed = speed - 10;
			}
			timer.setDelay(speed);
			//timer.restart();
			
		}
		//System.out.println(speed);
		//System.out.println(timer.getDelay());
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape)
	{
		Color colors[] = { new Color(0, 0, 0), new Color(200, 15, 255), 
				new Color(125, 13, 255), new Color(53, 12, 232), 
				new Color(0, 20, 255), new Color(12, 87, 232), 
				new Color(13, 160, 255), new Color(14, 170, 255)
		};


		Color color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		/*//borders top and left
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		//borders bottom right
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1,
				x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
				x + squareWidth() - 1, y + 1);*/
	}

	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {  
				return;
			}

			int keycode = e.getKeyCode();
			
			if (keycode == 'r' || keycode == 'R') {
				start();
				return;
			}
			
			if (keycode == 'p' || keycode == 'P') {
				pause();
				return;
			}	

			if (isPaused)
				return;

			switch (keycode) {
			case KeyEvent.VK_LEFT:
				tryMove(curPiece, curX - 1, curY);
				break;
			case KeyEvent.VK_RIGHT:
				tryMove(curPiece, curX + 1, curY);
				break;
			case KeyEvent.VK_DOWN:
				tryMove(curPiece.rotateRight(), curX, curY);
				break;
			case KeyEvent.VK_UP:
				tryMove(curPiece.rotateLeft(), curX, curY);
				break;
			case KeyEvent.VK_SPACE:
				oneLineDown();
				//dropDown();
				break;
			case 'd':
				oneLineDown();
				break;
			case 'D':
				oneLineDown();
				break;
			case 'r':
				start();
				break;
			case 'R':
				start();
				//System.out.println("R");
				break;
			}

		}
	}
	
	class TAdapterOver extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			int keycode = e.getKeyCode();
			//System.out.println(keycode);

			if (keycode == 'r' || keycode == 'R') {
				start();
			}	
		}
	}
}