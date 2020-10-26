/**
 *
 */
package dev.lhoz.dependency.inspection.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.lhoz.dependency.inspection.model.InspectionResults;
import dev.lhoz.dependency.inspection.model.LibraryDefinition;
import dev.lhoz.dependency.inspection.model.LibraryDefinitionList;
import dev.lhoz.dependency.inspection.service.central.query.Coordinates;
import dev.lhoz.dependency.inspection.service.central.result.Doc;
import dev.lhoz.dependency.inspection.service.central.result.DocList;
import dev.lhoz.dependency.inspection.ui.Logger;

/**
 * @author lhoz
 *
 */
public abstract class InspectionService {
	/**
	 * @param jars
	 */
	public InspectionResults searchReferences(final Collection<File> jars) {
		final InspectionResults results = new InspectionResults();
		final List<String> references = new ArrayList<>();
		final List<String> missing = new ArrayList<>();

		final CentralRepositoryService mavenCentral = CentralRepositoryService.getInstance();
		final LibraryForensicsService library = LibraryForensicsService.getInstance();

		final List<LibraryDefinitionList> jarDefinitions = library.inspect(jars);

		Integer total = 0;
		for (LibraryDefinitionList definitions : jarDefinitions) {
			total = total + definitions.size();
		}

		Integer inspected = 0;
		final List<DocList> jarDocs = new ArrayList<>();
		for (final LibraryDefinitionList definitions : jarDefinitions) {
			Boolean first = true;
			Boolean found = false;
			String name = null;
			
			Integer inspectedLocal = inspected;
			
			for (final LibraryDefinition definition : definitions) {
				if (first) {
					name = definition.getName();
					first = false;
				}

				final Coordinates coordinates = new Coordinates();
				coordinates.setArtifact(definition.getName());
				coordinates.setGroup(definition.getPack());
				coordinates.setPackaging("jar");
				coordinates.setVersion(definition.getVersion());

				final DocList docs = mavenCentral.query(coordinates);
				if (docs != null && docs.size() > 0) {
					jarDocs.add(docs);
					found = true;
					break;
				}
				
				inspectedLocal = inspectedLocal + 1;
				Logger.getInstance().log("Inspecting... [" + ((inspectedLocal * 100) / total) + "%]", 0L, true);
			}

			if (!found) {
				missing.add(name);
			}
			
			inspected = inspected + definitions.size();
			Logger.getInstance().log("Inspecting... [" + ((inspected * 100) / total) + "%]", 0L, true);
		}

		for (final DocList docs : jarDocs) {
			for (final Doc doc : docs) {
				final String reference = this.reference(doc);
				references.add(reference);
				break;
			}
		}

		results.setReferences(references);
		results.setMissing(missing);

		return results;
	}

	/**
	 * @param doc
	 */
	protected abstract String reference(Doc doc);
}
