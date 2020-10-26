/**
 *
 */
package dev.lhoz.dependency.inspection.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import dev.lhoz.dependency.inspection.model.LibraryDefinition;
import dev.lhoz.dependency.inspection.model.LibraryDefinitionList;
import dev.lhoz.dependency.inspection.ui.Logger;

/**
 * @author lhoz
 *
 */
public class LibraryForensicsService {
	private final static LibraryForensicsService INSTANCE = new LibraryForensicsService();

	/**
	 * @return
	 */
	public final static LibraryForensicsService getInstance() {
		return LibraryForensicsService.INSTANCE;
	}

	/**
	 *
	 */
	private LibraryForensicsService() {
	}

	/**
	 * @param jars
	 */
	public List<LibraryDefinitionList> inspect(final Collection<File> jars) {
		final List<LibraryDefinitionList> definitions = new ArrayList<>();

		final ExecutorService executor = Executors.newCachedThreadPool();
		for (final File jar : jars) {
			final Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					final LibraryDefinitionList jarDefinitions = LibraryForensicsService.this.inspect(jar);
					if (jarDefinitions != null) {
						definitions.add(jarDefinitions);
					}
				}
			});
			executor.execute(thread);
		}

		executor.shutdown();
		try {
			executor.awaitTermination(5, TimeUnit.MINUTES);
		} catch (final InterruptedException e) {
			Logger.getInstance().log("JAR inspection took longer than expected : " + e.getLocalizedMessage());
		}

		return definitions;
	}

	/**
	 * @param jar
	 */
	public LibraryDefinitionList inspect(final File jar) {
		final LibraryDefinitionList defs = new LibraryDefinitionList();

		FileInputStream stream = null;
		JarInputStream jarStream = null;

		try {
			// Obtains the name and version from REGEX
			String name = this.getName(jar);
			String version = this.getVersion(jar);

			// Checks if the REGEX was successful if not then goes for the file name
			name = !StringUtils.isBlank(name) ? name : FilenameUtils.getBaseName(jar.getAbsolutePath());
			version = !StringUtils.isBlank(version) ? version : null;

			defs.addAll(this.inspect(name, version, null));

			// Obtains information from the packages
			final Iterable<String> packages = this.packages(jar);
			for (final String pack : packages) {
				final LibraryDefinitionList ext = this.inspect(name, version, pack);
				for (final LibraryDefinition def : ext) {
					if (!defs.contains(def)) {
						defs.add(def);
					}
				}
			}

			// Obtains information from the manifest
			stream = new FileInputStream(jar);
			jarStream = new JarInputStream(stream);
			final Manifest manifest = jarStream.getManifest();

			if (manifest != null) {
				final Attributes attributes = manifest.getAttributes("mainAttributes");
				if (attributes != null) {
					name = attributes.getValue("Implementation-Title");
					version = attributes.getValue("Implementation-Version");

					final LibraryDefinitionList ext = this.inspect(name, version, null);
					for (final LibraryDefinition def : ext) {
						if (!defs.contains(def)) {
							defs.add(def);
						}
					}
				}
			}
		} catch (final Exception e) {
			Logger.getInstance().log("Error obtaining definition : " + e.getLocalizedMessage());
		} finally {
			this.close(stream);
			this.close(jarStream);
		}

		return defs;
	}

	/**
	 * @param name
	 * @return
	 */
	private String[] camlTransform(final String name) {
		final String camlTransform = name.replaceAll("([A-Z]|[0-9]+)", "-$1").toLowerCase();
		final String[] subnames = camlTransform.split("[-|\\.|_]");
		return subnames;
	}

	/**
	 * @param stream
	 */
	private void close(final InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException e) {
			}
		}
	}

	/**
	 * @param jar
	 * @return
	 */
	private String getName(final File jar) {
		return this.nameRegex(jar, 1);
	}

	/**
	 * @param jar
	 * @return
	 */
	private String getVersion(final File jar) {
		return this.nameRegex(jar, 2);
	}

	/**
	 * @param name
	 * @param version
	 * @param pack
	 * @return
	 */
	private LibraryDefinitionList inspect(final String name, final String version, final String pack) {
		final LibraryDefinitionList defs = new LibraryDefinitionList();

		if (!StringUtils.isNumeric(name) && !name.matches("[0-9]+.*")) {
			// Obtains sub-names for final inspection
			final String[] subnames = this.camlTransform(name);

			// Checks the values for it was called
			if (version != null && pack != null) {
				if (!StringUtils.isNumeric(pack)) {
					final LibraryDefinition def = new LibraryDefinition();
					def.setName(name);
					def.setVersion(version);
					def.setPack(pack);

					defs.add(def);
				} else {
					defs.addAll(this.inspect(name, pack, null));
					defs.addAll(this.inspect(name, version, null));
				}

			} else if (version != null) {
				final LibraryDefinition def = new LibraryDefinition();
				def.setName(name);
				def.setVersion(version);

				defs.add(def);
			} else if (pack != null) {
				if (!StringUtils.isNumeric(pack)) {
					final LibraryDefinition def = new LibraryDefinition();
					def.setName(name);
					def.setPack(pack);

					defs.add(def);
				} else {
					defs.addAll(this.inspect(name, pack, null));
				}
			} else {
				// The ultimate resource is that a sub-name is a package
				for (final String subname : subnames) {
					defs.addAll(this.inspect(name, null, subname));
				}
			}

			if (subnames.length > 1) {
				for (final String subname : subnames) {
					defs.addAll(this.inspect(subname, version, pack));
				}
			}
		}

		return defs;
	}

	/**
	 * @param jar
	 * @param group
	 * @return
	 */
	private String nameRegex(final File jar, final Integer group) {
		String value = null;
		final Pattern pattern = Pattern.compile("([a-zA-Z[-|\\.|_]]*[a-zA-Z]+[0-9a-zA-Z]*)+[-|\\.|_]*([0-9[-|\\.|_]]*[a-zA-Z[-|\\.|_]]*[a-zA-Z0-9]*)\\.jar");
		final Matcher matcher = pattern.matcher(jar.getName());
		if (matcher.matches()) {
			value = matcher.group(group).replaceAll("\"", StringUtils.EMPTY);
		}

		return value;
	}

	/**
	 * @param jar
	 * @return
	 * @throws IOException
	 */
	private Iterable<String> packages(final File jar) throws IOException {
		final Set<String> entryList = new TreeSet<>();

		final JarFile jarFile = new JarFile(jar);
		final Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			String name = entries.nextElement().getName();
			if (name.endsWith("/")) {
				name = name.substring(0, name.length() - 1);
				name = name.replaceAll("/", ".");
				name = name.replaceAll("\"", StringUtils.EMPTY);

				if (!name.equals("META-INF") && !name.contains("META-INF")) {
					entryList.add(name);
				}

			} else if (name.endsWith(".class")) {
				final List<String> parts = new ArrayList<>(Arrays.asList(name.split("/")));
				parts.remove(parts.size() - 1);
				name = StringUtils.join(parts, ".");
				name = name.replaceAll("\"", StringUtils.EMPTY);
				entryList.add(name);
			}

		}

		jarFile.close();
		return entryList;
	}
}
