
package com.googleapi.json.contacts;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Generated("com.googlecode.jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GContact$groupMembershipInfo {

    private String deleted;
    private String href;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
