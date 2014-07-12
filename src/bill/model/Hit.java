package bill.model;

import java.awt.Dimension;
import java.util.Comparator;
import java.util.List;

import bill.phys.Motion;
import bill.sound.ClickSound;

public abstract class Hit {
	protected final double dTime;
	final double dTime() { return dTime; }
	protected Hit(double dTime) { this.dTime = dTime; }

	final void result(List<Ball> balls) {
		for (Ball b : balls) {
			b.move(dTime);
		}
		react(balls);
	}
	protected abstract void react(List<Ball> balls);

	public static class HitB extends Hit {
		private final ClickSound cs = new ClickSound();
		public final Ball ball1;
		public final Ball ball2;
	
		public HitB(Ball b1, Ball b2, double dTime) {
			super(dTime);
			this.ball1 = b1;
			this.ball2 = b2;
		}
		public void react(List<Ball> balls) {
			for (Ball b : balls) {
				if (b.id == ball1.id) {
					b.hit(ball2);
					cs.play();
//				} else if (b.id == ball2.id){
//					b.hit(ball1);
				}
			}
		}
	}
	public enum Wall { Vertical, Horizontal }
	public static class HitW extends Hit {
		public final Ball ball;
		public final Wall wall;
		public HitW(Ball b, Wall w, Dimension size) {
			super(when(b, w, size));
			this.ball = b;
			this.wall = w;
		}
		public void react(List<Ball> balls) {
			for (Ball b : balls) {
				if (b.id == ball.id) {
					b.hitWall(wall == Wall.Vertical);
				}
			}
		}
		private static double when(Ball b, Wall w, Dimension size) {
			Motion m = b.getMotion();
			if (w == Wall.Vertical) {
				if (m.dx < 0)
					return (0 -          (b.pos.x - Ball.SIZE)) / m.dx;
				else
					return (size.width - (b.pos.x + Ball.SIZE)) / m.dx;
			} else {
				if (m.dy < 0)
					return (0 -           (b.pos.y - Ball.SIZE)) / m.dy;
				else
					return (size.height - (b.pos.y + Ball.SIZE)) / m.dy;
			}
		}
	}
	
	public static class HitByTime implements Comparator<Hit> {
		@Override
		public int compare(Hit arg0, Hit arg1) {
			return Double.compare(arg0.dTime(), arg1.dTime());
		}
	}
}
