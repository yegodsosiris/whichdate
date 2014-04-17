
package com.googleapi.json.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "end",
    "start",
    "attendees",
    "endTimeUnspecified",
    "description",
    "location",
    "summary"
})
public class CalendarEvent {

    @JsonProperty("end")
    private End end;
    @JsonProperty("start")
    private Start start;
    @JsonProperty("attendees")
    private List<Attendee> attendees = new ArrayList<Attendee>();
    @JsonProperty("endTimeUnspecified")
    private Boolean endTimeUnspecified;
    @JsonProperty("description")
    private String description;
    @JsonProperty("location")
    private String location;
    @JsonProperty("summary")
    private String summary;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("end")
    public End getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(End end) {
        this.end = end;
    }

    @JsonProperty("start")
    public Start getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Start start) {
        this.start = start;
    }

    @JsonProperty("attendees")
    public List<Attendee> getAttendees() {
        return attendees;
    }

    @JsonProperty("attendees")
    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    @JsonProperty("endTimeUnspecified")
    public Boolean getEndTimeUnspecified() {
        return endTimeUnspecified;
    }

    @JsonProperty("endTimeUnspecified")
    public void setEndTimeUnspecified(Boolean endTimeUnspecified) {
        this.endTimeUnspecified = endTimeUnspecified;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
