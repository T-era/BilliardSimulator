package bill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import bill.model.Ball;
import bill.model.Field;
import bill.model.Field.HitEvent;
import bill.phys.Motion;

import bill.phys.Point;

public class BilliardTable extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7233905728702943579L;
	private static final Dimension SIZE = new Dimension(1200,600);
	private static Ball[] btFly = new Ball[] {
		new Ball(0, new Point(300,50), new Motion(20,8)),
		new Ball(1, new Point(900,300), new Motion(0,0)),
			new Ball(2, new Point(944,275), new Motion(0,0)),
			new Ball(3, new Point(944,325), new Motion(0,0)),
				new Ball(4, new Point(988,250), new Motion(0,0)),
				new Ball(5, new Point(988,300), new Motion(0,0)),
				new Ball(6, new Point(988,350), new Motion(0,0)),
					new Ball( 7, new Point(1032,225), new Motion(0,0)),
					new Ball( 8, new Point(1032,275), new Motion(0,0)),
					new Ball( 9, new Point(1032,325), new Motion(0,0)),
					new Ball(10, new Point(1032,375), new Motion(0,0)),
						new Ball(11, new Point(1076,200), new Motion(0,0)),
						new Ball(12, new Point(1076,250), new Motion(0,0)),
						new Ball(13, new Point(1076,300), new Motion(0,0)),
						new Ball(14, new Point(1076,350), new Motion(0,0)),
						new Ball(15, new Point(1076,400), new Motion(0,0)),
		};
	private static final Field f = new Field(SIZE,
			btFly);

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = frame.getContentPane();
		final BilliardTable table = new BilliardTable();
		con.add(table, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.animation();
			}
		}).start();
	}

	private final double timeAByte = 1;
	public BilliardTable() {
		this.setBackground(new Color(0,128,16));
		this.setPreferredSize(SIZE);
	}
	private void animation() {
		double remain = timeAByte;
		HitEvent nextEvent = f.nextEvent();

		while (remain >= nextEvent.getDTime()) {
			nextEvent.action();
			remain -= nextEvent.getDTime();
			nextEvent = f.nextEvent();
		}
		if (remain > 0)
			f.stepTime(remain);
		repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		f.draw(g);
	}
}
