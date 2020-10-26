/**
 *
 */
package dev.lhoz.dependency.inspection.service.central.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhoz
 *
 */
public class DocList extends ArrayList<Doc> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public DocList() {
	}

	/**
	 * @param docs
	 */
	public DocList(final List<Doc> docs) {
		this.addAll(docs);
	}
}
