package bill.phys;

import bill.math.Determinant12;
import bill.math.Determinant22;

public class Motion {
	public final double dx;
	public final double dy;

	public Motion(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public DivideResult divide(Point myPos, Point otherPos) {
		Determinant22 equ = new Determinant22(
				otherPos.y-myPos.y,
				otherPos.x-myPos.x,
				myPos.x-otherPos.x,
				otherPos.y-myPos.y);
		Determinant12 ans = equ.reverse().cross(new Determinant12(dx, dy));
		double dmy = ans.v1;
		double dot = ans.v2;
		DivideResult dr = new DivideResult(
				new Motion(dmy*(otherPos.y - myPos.y), dmy*(myPos.x-otherPos.x)),
				new Motion(dot*(otherPos.x - myPos.x), dot*(otherPos.y-myPos.y)));
		return dr;
	}

	public class DivideResult {
		public final Motion myMotion;
		public final Motion otherMotion;

		private DivideResult(Motion my, Motion other) {
			this.myMotion = my;
			this.otherMotion = other;
		}
	}

	public Motion add(Motion arg) {
		return new Motion(
				dx + arg.dx,
				dy + arg.dy);
	}
}
