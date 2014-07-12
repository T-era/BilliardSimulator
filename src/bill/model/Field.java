package bill.model;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bill.animation.Event;
import bill.animation.Model;
import bill.model.Hit.Wall;

public class Field implements Model<Field.HitEvent> {
	private final Dimension size;
	private final List<Ball> balls;
	private double timestamp = 0;

	public Field(Dimension size, Ball...args) {
		this.size = size;
		this.balls = new ArrayList<>(Arrays.asList(args));
	}

	public void next() {
		Hit hit = firstHit();
		if (hit == null) return ;

		hit.result(balls);

		timestamp += hit.dTime();
	}
	private Hit firstHit() {
		List<Hit> all = allHits();
		if (all.size() == 0)
			return null;
		Collections.sort(all, new Hit.HitByTime());
		return all.get(0);
		
	}
	private List<Hit> allHits() {
		List<Hit> all = new ArrayList<>();
		for (Ball b1 : balls) {
			for (Ball b2 : balls) {
				if (b1.id < b2.id) {
					double whenHit = new Thin_k(b1, b2).whenHit();
					if (whenHit >= 0) {
						all.add(new Hit.HitB(b1, b2, whenHit));
					}
				}
			}
			if (b1.getMotion().dx != 0) {
				all.add(new Hit.HitW(b1, Wall.Vertical, size));
			}
			if (b1.getMotion().dy != 0) {
				all.add(new Hit.HitW(b1, Wall.Horizontal, size));
			}
		}
		return all;
	}

	@Override
	public HitEvent nextEvent() {
		return new HitEvent(firstHit());
	}

	@Override
	public void stepTime(double dTime) {
		for (Ball ball : balls) {
			ball.move(dTime);
		}
		timestamp += dTime;
	}

	public void draw(Graphics g) {
		Font now = g.getFont();
		g.setFont(new Font(now.getName(), Font.BOLD, Ball.SIZE*4/5));
		for (Ball b : balls) {
			b.draw(g);
		}
		g.setFont(now);
		g.drawString(Double.toString(timestamp), 10, 10);
	}

	public class HitEvent implements Event {
		private final Hit hit;
		private HitEvent(Hit hit) {
			this.hit = hit;
		}
		@Override
		public double getDTime() {
			return hit.dTime;
		}

		@Override
		public void action() {
			hit.react(balls);
			timestamp += hit.dTime;
		}
	}
}
