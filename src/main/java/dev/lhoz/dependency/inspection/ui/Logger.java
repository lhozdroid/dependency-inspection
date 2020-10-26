/**
 *
 */
package dev.lhoz.dependency.inspection.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import dev.lhoz.dependency.inspection.ui.console.LogThread;
import lombok.Data;

/**
 * @author lhoz
 *
 */
@Data
public class Logger {
	private final static Long DEFAULT_TIME = 5L;
	private final static Logger INSTANCE = new Logger();

	/**
	 * @return
	 */
	public final static Logger getInstance() {
		return Logger.INSTANCE;
	}

	private JTextArea console;

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 *
	 */
	private Logger() {
	}

	/**
	 *
	 */
	public void clean() {
		this.log("", Logger.DEFAULT_TIME, true);
	}

	/**
	 * @param text
	 */
	public void log(final String text) {
		this.log(text, Logger.DEFAULT_TIME, false);
	}

	/**
	 * @param text
	 * @param clean
	 */
	public void log(final String text, final Boolean clean) {
		this.log(text, Logger.DEFAULT_TIME, true);
	}

	/**
	 * @param text
	 * @param speed
	 */
	public void log(final String text, final Long speed) {
		this.log(text, speed, false);
	}

	/**
	 * @param text
	 * @param speed
	 * @param clean
	 */
	public void log(final String text, final Long speed, final Boolean clean) {
		final LogThread log = new LogThread();
		log.setConsole(this.console);
		log.setSpeed(speed);
		log.setText(text);
		log.setClean(clean);

		this.executor.execute(log);
	}
}
