/**
 *
 */
package dev.lhoz.dependency.inspection.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.lhoz.dependency.inspection.service.central.query.Coordinates;
import dev.lhoz.dependency.inspection.service.central.query.Translation;
import dev.lhoz.dependency.inspection.service.central.result.DocList;
import dev.lhoz.dependency.inspection.service.central.result.Response;

/**
 * @author lhoz
 *
 */
public class CentralRepositoryService {
	private final static CentralRepositoryService INSTANCE = new CentralRepositoryService();

	/**
	 * @return
	 */
	public final static CentralRepositoryService getInstance() {
		return CentralRepositoryService.INSTANCE;
	}

	private final ObjectMapper mapper = new ObjectMapper();

	private final String URL = "https://search.maven.org/solrsearch/select";

	/**
	 *
	 */
	private CentralRepositoryService() {
	}

	/**
	 * @param coordinates
	 */
	public DocList query(final Coordinates coordinates) {
		DocList docs = new DocList();
		try {
			final URIBuilder builder = new URIBuilder(this.URL);
			builder.addParameter("wt", "json");
			builder.addParameter("q", this.buildQuery(coordinates));

			final String url = builder.build().toString();

			final CloseableHttpClient client = HttpClients.createDefault();
			final HttpGet get = new HttpGet(url);
			final HttpResponse response = client.execute(get);
			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				final String content = EntityUtils.toString(entity);
				final Response r = this.mapper.readValue(content, Response.class);

				docs = new DocList(r.getResponse().getDocs());
			}
		} catch (final Exception e) {
		}

		return docs;
	}

	/**
	 * @param coordinates
	 * @return
	 */
	private String buildQuery(final Coordinates coordinates) {
		final List<String> properties = new ArrayList<>();

		try {
			final Field[] fields = Coordinates.class.getDeclaredFields();
			for (final Field field : fields) {
				final Translation translation = field.getAnnotation(Translation.class);
				if (translation != null) {
					field.setAccessible(true);
					final String param = translation.name();
					final String value = field.get(coordinates) != null ? field.get(coordinates).toString() : null;

					if (param != null && value != null) {
						properties.add(String.format("%s:%s", param, value));
					}
				}
			}
		} catch (final Exception e) {
		}

		return StringUtils.join(properties, " AND ");
	}

}
