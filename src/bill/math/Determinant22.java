package bill.math;

public class Determinant22 {
	public final double v11;
	public final double v12;
	public final double v21;
	public final double v22;

	public Determinant22(double v11, double v12, double v21, double v22) {
		this.v11 = v11;
		this.v12 = v12;
		this.v21 = v21;
		this.v22 = v22;
	}

	public Determinant22 add(Determinant22 arg) {
		return new Determinant22(
				this.v11 + arg.v11,
				this.v12 + arg.v12,
				this.v21 + arg.v21,
				this.v22 + arg.v22);
	}
	public Determinant22 multi(Determinant22 arg) {
		return new Determinant22(
			this.v11 * arg.v11 + this.v12 * arg.v21,
			this.v11 * arg.v12 + this.v12 * arg.v22,
			this.v21 * arg.v11 + this.v22 * arg.v21,
			this.v21 * arg.v12 + this.v22 * arg.v22);
	}
	public double abs() {
		return v11 * v22 - v12 * v21;
	}
	public Determinant22 reverse() {
		double abs = abs();
		return new Determinant22(
			v22 / abs,
			- v12 / abs,
			- v21 / abs,
			v11 / abs);
	}
	public Determinant12 cross(Determinant12 arg) {
		return new Determinant12(
				v11 * arg.v1 + v12 * arg.v2,
				v21 * arg.v1 + v22 * arg.v2);
	}
}
