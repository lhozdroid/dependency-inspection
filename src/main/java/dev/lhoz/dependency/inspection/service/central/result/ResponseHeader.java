
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
@JsonPropertyOrder({ "status", "QTime", "params" })
public class ResponseHeader {

	@JsonIgnore
	private final Map<String, Object> additionalProperties = new HashMap<>();
	@JsonProperty("params")
	private Params params;
	@JsonProperty("QTime")
	private Long qTime;
	@JsonProperty("status")
	private Long status;

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("params")
	public Params getParams() {
		return this.params;
	}

	@JsonProperty("QTime")
	public Long getQTime() {
		return this.qTime;
	}

	@JsonProperty("status")
	public Long getStatus() {
		return this.status;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("params")
	public void setParams(final Params params) {
		this.params = params;
	}

	@JsonProperty("QTime")
	public void setQTime(final Long qTime) {
		this.qTime = qTime;
	}

	@JsonProperty("status")
	public void setStatus(final Long status) {
		this.status = status;
	}

}
