/**
 *
 */
package dev.lhoz.dependency.inspection.service;

import org.apache.commons.lang3.StringUtils;

import dev.lhoz.dependency.inspection.service.central.result.Doc;

/**
 * @author lhoz
 *
 */
public final class MavenInspectionService extends InspectionService {
	private final static MavenInspectionService INSTANCE = new MavenInspectionService();

	/**
	 * @return
	 */
	public static MavenInspectionService getInstance() {
		return MavenInspectionService.INSTANCE;
	}

	/**
	 *
	 */
	private MavenInspectionService() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see dev.lhoz.dependency.inspection.service.InspectionService#reference(dev.lhoz.dependency.inspection.util.repository.maven.search.Doc)
	 */
	@Override
	protected String reference(final Doc doc) {
		final String dependency = "" + //
				"<dependency>" + //
				"<groupId>" + doc.getG() + "</groupId>" + //
				"<artifactId>" + doc.getA() + "</artifactId>" + //
				"<version>" + (StringUtils.isBlank(doc.getV()) ? doc.getLv() : doc.getV()) + "</version>" + //
				"</dependency>";
		return dependency;
	}

}
