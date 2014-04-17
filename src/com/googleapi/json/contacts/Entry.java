
package com.googleapi.json.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Generated("com.googlecode.jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry {

    private Id id;
    private Updated updated;
    private List<Category> category = new ArrayList<Category>();
    private Title title;
    private Content content;
    private List<Link> link = new ArrayList<Link>();
    private List<Gd$email> gd$email = new ArrayList<Gd$email>();
    private List<Gd$phoneNumber> gd$phoneNumber = new ArrayList<Gd$phoneNumber>();
    private List<GContact$groupMembershipInfo> gContact$groupMembershipInfo = new ArrayList<GContact$groupMembershipInfo>();
    private List<Gd$postalAddres> gd$postalAddress = new ArrayList<Gd$postalAddres>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Updated getUpdated() {
        return updated;
    }

    public void setUpdated(Updated updated) {
        this.updated = updated;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public List<Link> getLink() {
        return link;
    }

    public void setLink(List<Link> link) {
        this.link = link;
    }

    public List<Gd$email> getGd$email() {
        return gd$email;
    }

    public void setGd$email(List<Gd$email> gd$email) {
        this.gd$email = gd$email;
    }

    public List<Gd$phoneNumber> getGd$phoneNumber() {
        return gd$phoneNumber;
    }

    public void setGd$phoneNumber(List<Gd$phoneNumber> gd$phoneNumber) {
        this.gd$phoneNumber = gd$phoneNumber;
    }

    public List<GContact$groupMembershipInfo> getGContact$groupMembershipInfo() {
        return gContact$groupMembershipInfo;
    }

    public void setGContact$groupMembershipInfo(List<GContact$groupMembershipInfo> gContact$groupMembershipInfo) {
        this.gContact$groupMembershipInfo = gContact$groupMembershipInfo;
    }

    public List<Gd$postalAddres> getGd$postalAddress() {
        return gd$postalAddress;
    }

    public void setGd$postalAddress(List<Gd$postalAddres> gd$postalAddress) {
        this.gd$postalAddress = gd$postalAddress;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
