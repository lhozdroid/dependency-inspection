/**
 *
 */
package dev.lhoz.dependency.inspection.ui.console;

import javax.swing.JTextArea;

import lombok.Data;

/**
 * @author lhoz
 *
 */
@Data
public class LogThread implements Runnable {

	private Boolean clean;
	private JTextArea console;
	private Long speed;
	private String text;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (this.clean != null && this.clean) {
			this.console.setText("");
		}

		if (this.text != null && this.text.length() > 0) {
			for (int i = 0; i < this.text.length(); i++) {
				final String total = this.console.getText();
				this.console.setText(total + this.text.charAt(i));
				if (this.speed != null && this.speed.compareTo(0L) > 0) {
					try {
						Thread.sleep(this.speed);
					} catch (final InterruptedException e) {
					}
				}
			}

			final String total = this.console.getText();
			this.console.setText(total + "\n");
		}
	}
}
