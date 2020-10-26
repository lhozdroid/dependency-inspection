/**
 *
 */
package dev.lhoz.dependency.inspection.service.thread;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import dev.lhoz.dependency.inspection.model.InspectionResults;
import dev.lhoz.dependency.inspection.service.InspectionService;
import dev.lhoz.dependency.inspection.ui.Logger;
import dev.lhoz.dependency.inspection.ui.MainFrame;
import lombok.Data;

/**
 * @author lhoz
 *
 */
@Data
public class InspectionThread<T extends InspectionService> implements Runnable {

	private MainFrame frame;
	private T inspectionService;
	private String path;
	private InspectionResults results;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		final File folder = new File(this.path);

		Logger.getInstance().log("Inspecting... [0%]", true);

		if (folder != null && folder.isDirectory()) {
			final Collection<File> jars = FileUtils.listFiles(folder, new String[] { "jar" }, false);

			if (jars.size() > 0) {
				results = this.inspectionService.searchReferences(jars);

				Logger.getInstance().log("Found:   [" + results.getReferences().size() + "] references");
				Logger.getInstance().log("Missing: [" + results.getMissing().size() + "] references");
			} else {
				Logger.getInstance().log("There are no jars to inspect");
			}
			
			String folderPath = FilenameUtils.separatorsToUnix(folder.getAbsolutePath() + "/");
			File references = new File(FilenameUtils.separatorsToSystem(folderPath + "references.txt"));
			File missing = new File(FilenameUtils.separatorsToSystem(folderPath + "missing.txt"));
			
			try {
				FileUtils.writeLines(references, results.getReferences());
				FileUtils.writeLines(missing, results.getMissing());
				
				Logger.getInstance().log("Saved [references.txt]");
				Logger.getInstance().log("Saved [missing.txt]");
				Logger.getInstance().log("Please check your folder");
			} catch (IOException e) {
				Logger.getInstance().log("Unable to save inspection results");
			}
		} else {
			Logger.getInstance().log("The path [" + this.path + "] is not a folder");
		}

		this.frame.unlock();
	}

}
