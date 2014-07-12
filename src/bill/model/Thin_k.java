package bill.model;

import bill.phys.Motion;

public class Thin_k {
	private final Ball b1;
	private final Motion v1;
	private final Ball b2;
	private final Motion v2;
	private final double dx;
	private final double dy;
	private final double ddx;
	private final double ddy;

	Thin_k(Ball b1, Ball b2) {
		this.b1 = b1;
		this.b2 = b2;
		v1 = b1.getMotion();
		v2 = b2.getMotion();
		dx = b2.pos.x - b1.pos.x;
		dy = b2.pos.y - b1.pos.y;
		ddx = v2.dx - v1.dx;
		ddy = v2.dy - v1.dy;
	}

	double whenHit() {
		if (mayHit()) {
			return touchTime();
		}
		return -1;
	}
	private boolean mayHit() {
		double crossTime = crossTime();
		return crossTime > 0
			&& d2(crossTime) < 4*Ball.SIZE*Ball.SIZE;
	}
	private double touchTime() {
		double a = ddx*ddx+ddy*ddy;
		double b = 2.0*(dx*ddx+dy*ddy);
		double c = dx*dx+dy*dy-4*Ball.SIZE*Ball.SIZE;
		if (b < 0) {
			return (-b - Math.sqrt(b*b-4.0*a*c))/2.0/a;
		} else {
			return (-b + Math.sqrt(b*b-4.0*a*c))/2.0/a;
		}
		
	}

	private double crossTime() {
		return -(dx*ddx+dy*ddy)/(ddx*ddx+ddy*ddy);
	}
	private double d2(double when) {
		double x1 = b1.pos.x + v1.dx * when;
		double y1 = b1.pos.y + v1.dy * when;
		double x2 = b2.pos.x + v2.dx * when;
		double y2 = b2.pos.y + v2.dy * when;
		return (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1);
	}
}
