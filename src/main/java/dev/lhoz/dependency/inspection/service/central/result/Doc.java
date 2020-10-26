
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
@JsonPropertyOrder({ "id", "g", "a", "v", "p", "timestamp", "ec", "tags" })
public class Doc {

	@JsonProperty("a")
	private String a;
	@JsonIgnore
	private final Map<String, Object> additionalProperties = new HashMap<>();
	@JsonProperty("ec")
	private List<String> ec = null;
	@JsonProperty("g")
	private String g;
	@JsonProperty("id")
	private String id;
	@JsonProperty("latestVersion")
	private String lv;
	@JsonProperty("p")
	private String p;
	@JsonProperty("tags")
	private List<String> tags = null;
	@JsonProperty("timestamp")
	private Long timestamp;
	@JsonProperty("v")
	private String v;

	@JsonProperty("a")
	public String getA() {
		return this.a;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("ec")
	public List<String> getEc() {
		return this.ec;
	}

	@JsonProperty("g")
	public String getG() {
		return this.g;
	}

	@JsonProperty("id")
	public String getId() {
		return this.id;
	}

	@JsonProperty("latestVersion")
	public String getLv() {
		return this.lv;
	}

	@JsonProperty("p")
	public String getP() {
		return this.p;
	}

	@JsonProperty("tags")
	public List<String> getTags() {
		return this.tags;
	}

	@JsonProperty("timestamp")
	public Long getTimestamp() {
		return this.timestamp;
	}

	@JsonProperty("v")
	public String getV() {
		return this.v;
	}

	@JsonProperty("a")
	public void setA(final String a) {
		this.a = a;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("ec")
	public void setEc(final List<String> ec) {
		this.ec = ec;
	}

	@JsonProperty("g")
	public void setG(final String g) {
		this.g = g;
	}

	@JsonProperty("id")
	public void setId(final String id) {
		this.id = id;
	}

	@JsonProperty("latestVersion")
	public void setLv(final String lv) {
		this.lv = lv;
	}

	@JsonProperty("p")
	public void setP(final String p) {
		this.p = p;
	}

	@JsonProperty("tags")
	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty("v")
	public void setV(final String v) {
		this.v = v;
	}

}
