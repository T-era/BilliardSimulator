package bill.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;

public class ClickSound {
	public static final byte[] SOUND = new byte[] {
		32,0,0,0,
		-32,0,0,0,
		64,0,0,0,
		-64,0,0,0,
		127,0,0,0,
		-127,0,0,0,
		64,0,0,0,
		-64,0,0,0,
		32,0,0,0,
		-32 };
	public static final int SAMPLE_RATE = 44100;
	private final Info info;

	public ClickSound() {
		AudioFormat audio_format = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
		info = new DataLine.Info(SourceDataLine.class, audio_format);
	}

	public void play() {
		try {
			SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);
			line.open();
			line.start();
			line.write(SOUND, 0, SOUND.length);
			line.drain();
		} catch (LineUnavailableException ex) {
			// Do nothing (can't play)
		}
	}
}
