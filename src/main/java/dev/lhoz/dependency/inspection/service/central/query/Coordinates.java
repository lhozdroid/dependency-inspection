/**
 *
 */
package dev.lhoz.dependency.inspection.service.central.query;

import lombok.Data;

/**
 * @author lhoz
 *
 */
@Data
public class Coordinates {
	@Translation(name = "a")
	private String artifact;

	@Translation(name = "l")
	private String classifier;

	@Translation(name = "g")
	private String group;

	@Translation(name = "p")
	private String packaging;

	@Translation(name = "v")
	private String version;
}
