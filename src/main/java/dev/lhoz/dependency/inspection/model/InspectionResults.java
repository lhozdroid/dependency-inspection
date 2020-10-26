/**
 *
 */
package dev.lhoz.dependency.inspection.model;

import java.util.List;

import lombok.Data;

/**
 * @author lhoz
 *
 */
@Data
public class InspectionResults {
	private List<String> missing;
	private List<String> references;
}
