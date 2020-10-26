
package dev.lhoz.dependency.inspection.service.central.result;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "q", "core", "indent", "fl", "start", "sort", "rows", "wt", "version" })
public class Params {

	@JsonIgnore
	private final Map<String, Object> additionalProperties = new HashMap<>();
	@JsonProperty("core")
	private String core;
	@JsonProperty("fl")
	private String fl;
	@JsonProperty("indent")
	private String indent;
	@JsonProperty("q")
	private String q;
	@JsonProperty("rows")
	private String rows;
	@JsonProperty("sort")
	private String sort;
	@JsonProperty("start")
	private String start;
	@JsonProperty("version")
	private String version;
	@JsonProperty("wt")
	private String wt;

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("core")
	public String getCore() {
		return this.core;
	}

	@JsonProperty("fl")
	public String getFl() {
		return this.fl;
	}

	@JsonProperty("indent")
	public String getIndent() {
		return this.indent;
	}

	@JsonProperty("q")
	public String getQ() {
		return this.q;
	}

	@JsonProperty("rows")
	public String getRows() {
		return this.rows;
	}

	@JsonProperty("sort")
	public String getSort() {
		return this.sort;
	}

	@JsonProperty("start")
	public String getStart() {
		return this.start;
	}

	@JsonProperty("version")
	public String getVersion() {
		return this.version;
	}

	@JsonProperty("wt")
	public String getWt() {
		return this.wt;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("core")
	public void setCore(final String core) {
		this.core = core;
	}

	@JsonProperty("fl")
	public void setFl(final String fl) {
		this.fl = fl;
	}

	@JsonProperty("indent")
	public void setIndent(final String indent) {
		this.indent = indent;
	}

	@JsonProperty("q")
	public void setQ(final String q) {
		this.q = q;
	}

	@JsonProperty("rows")
	public void setRows(final String rows) {
		this.rows = rows;
	}

	@JsonProperty("sort")
	public void setSort(final String sort) {
		this.sort = sort;
	}

	@JsonProperty("start")
	public void setStart(final String start) {
		this.start = start;
	}

	@JsonProperty("version")
	public void setVersion(final String version) {
		this.version = version;
	}

	@JsonProperty("wt")
	public void setWt(final String wt) {
		this.wt = wt;
	}

}
