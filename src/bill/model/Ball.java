package bill.model;

import java.awt.Color;
import java.awt.Graphics;

import bill.phys.Motion;
import bill.phys.Motion.DivideResult;
import bill.phys.Point;

public class Ball {
	private static final boolean showDebugLine = false;
	public static final int SIZE = 25;
	public static final Color[] COLORS = new Color[] {
		Color.WHITE,
		Color.YELLOW,
		Color.BLUE,
		Color.RED,
		new Color(160,0,160),
		new Color(255,128,0),
		new Color(0,160,0),
		new Color(128,0,0),
		new Color(0,0,0),
	};
	public final int id; 
	public Point pos;
	private Motion v;

	public Ball(int id, Point pos, Motion v) {
		this.pos = pos;
		this.v = v;
		this.id = id;
	}

	public Motion getMotion() {
		return v;
	}

	public void hit(Ball arg) {
		Motion my = this.v;
		Motion ot = arg.v;
		DivideResult dr1 = my.divide(this.pos, arg.pos);
		DivideResult dr2 = ot.divide(this.pos, arg.pos);
		this.v = dr1.myMotion.add(dr2.otherMotion);
		arg.v = dr1.otherMotion.add(dr2.myMotion);
	}

	public void hitWall(boolean verticalWall) {
		if (verticalWall) {
			v = new Motion(- v.dx, v.dy);
		} else {
			v = new Motion(v.dx, - v.dy);
		}
	}

	public void move(double dt) {
		pos = new Point(pos.x + v.dx * dt, pos.y + v.dy * dt);
	}
	public void draw(Graphics g) {
		int cid = id > 8 ? id - 8 : id;
		Color c = COLORS[cid];
		if (id <= 8) {
			g.setColor(c);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillArc((int)pos.x - SIZE, (int)pos.y - SIZE, SIZE * 2, SIZE * 2, 0, 360);
		if (id > 8) {
			g.setColor(c);
			g.fillArc((int)pos.x - SIZE, (int)pos.y - SIZE, SIZE * 2, SIZE * 2, 15, 60);
			g.fillArc((int)pos.x - SIZE, (int)pos.y - SIZE, SIZE * 2, SIZE * 2, 195, 60);
			g.fillPolygon(
					new int[] {
							(int)(pos.x - Math.cos(Math.PI*2.0/24.0) * SIZE),//15
							(int)(pos.x - Math.cos(Math.PI*10.0/24.0) * SIZE),//75
							(int)(pos.x - Math.cos(Math.PI*26.0/24.0) * SIZE),//195
							(int)(pos.x - Math.cos(Math.PI*34.0/24.0) * SIZE) },//255
					new int[] {
							(int)(pos.y + Math.sin(Math.PI*2.0/24.0) * SIZE),
							(int)(pos.y + Math.sin(Math.PI*10.0/24.0) * SIZE),
							(int)(pos.y + Math.sin(Math.PI*26.0/24.0) * SIZE),
							(int)(pos.y + Math.sin(Math.PI*34.0/24.0) * SIZE) },
					4);
		}
		g.setColor(Color.BLACK);
		if (id != 0) {
			g.setColor(Color.WHITE);
			g.fillArc((int)pos.x-SIZE/4, (int)pos.y-SIZE*3/4, SIZE, SIZE, 0, 360);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(id),(int)pos.x-(id > 9 ? 5 : 0),(int)pos.y);
		}
		if (showDebugLine) {
			g.drawLine((int)pos.x, (int)pos.y, (int)(pos.x + v.dx * 10), (int)(pos.y + v.dy * 10));
		}
	}
}
