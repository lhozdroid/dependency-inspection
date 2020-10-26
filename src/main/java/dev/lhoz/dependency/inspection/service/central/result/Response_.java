
package dev.lhoz.dependency.inspection.service.central.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "numFound", "start", "docs" })
public class Response_ {

	@JsonIgnore
	private final Map<String, Object> additionalProperties = new HashMap<>();
	@JsonProperty("docs")
	private List<Doc> docs = null;
	@JsonProperty("numFound")
	private Long numFound;
	@JsonProperty("start")
	private Long start;

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("docs")
	public List<Doc> getDocs() {
		return this.docs;
	}

	@JsonProperty("numFound")
	public Long getNumFound() {
		return this.numFound;
	}

	@JsonProperty("start")
	public Long getStart() {
		return this.start;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("docs")
	public void setDocs(final List<Doc> docs) {
		this.docs = docs;
	}

	@JsonProperty("numFound")
	public void setNumFound(final Long numFound) {
		this.numFound = numFound;
	}

	@JsonProperty("start")
	public void setStart(final Long start) {
		this.start = start;
	}

}
