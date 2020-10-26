/**
 *
 */
package dev.lhoz.dependency.inspection.model;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhoz
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LibraryDefinitionList extends ArrayList<LibraryDefinition> {
	private static final long serialVersionUID = 1L;
}
