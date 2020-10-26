
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
@JsonPropertyOrder({ "responseHeader", "response" })
public class Response {

	@JsonIgnore
	private final Map<String, Object> additionalProperties = new HashMap<>();
	@JsonProperty("response")
	private Response_ response;
	@JsonProperty("responseHeader")
	private ResponseHeader responseHeader;

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("response")
	public Response_ getResponse() {
		return this.response;
	}

	@JsonProperty("responseHeader")
	public ResponseHeader getResponseHeader() {
		return this.responseHeader;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("response")
	public void setResponse(final Response_ response) {
		this.response = response;
	}

	@JsonProperty("responseHeader")
	public void setResponseHeader(final ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

}
