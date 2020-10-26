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
public final class GradleInspectionService extends InspectionService {
	private final static GradleInspectionService INSTANCE = new GradleInspectionService();

	/**
	 * @return
	 */
	public static GradleInspectionService getInstance() {
		return GradleInspectionService.INSTANCE;
	}

	/**
	 *
	 */
	private GradleInspectionService() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see dev.lhoz.dependency.inspection.service.InspectionService#reference(dev.lhoz.dependency.inspection.util.repository.maven.search.Doc)
	 */
	@Override
	protected String reference(final Doc doc) {
		final String[] values = new String[] { doc.getG(), doc.getA(), StringUtils.isBlank(doc.getV()) ? doc.getLv() : doc.getV() };
		return "toCopy '" + StringUtils.join(values, ":") + "'";
	}

}
