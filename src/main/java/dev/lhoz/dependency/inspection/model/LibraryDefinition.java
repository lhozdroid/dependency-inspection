/**
 *
 */
package dev.lhoz.dependency.inspection.model;

import lombok.Data;

/**
 * @author lhoz
 *
 */
@Data
public class LibraryDefinition {
	private String name;
	private String pack;
	private String version;

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	public Boolean equals(final LibraryDefinition a, final LibraryDefinition b) {
		Boolean same = false;
		if (a.getName() != null && a.getName().equals(b.getName()) && //
				a.getPack() != null && a.getPack().equals(b.getPack()) && //
				a.getVersion() != null && a.getVersion().equals(b.getVersion())) {
			same = true;
		}

		return same;
	}
}
