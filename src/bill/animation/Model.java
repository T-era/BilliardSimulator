package bill.animation;

public interface Model<TEvent extends Event> {
	TEvent nextEvent();
	void stepTime(double dTime);
}
