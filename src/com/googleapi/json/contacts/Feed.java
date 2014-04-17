
package com.googleapi.json.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Generated("com.googlecode.jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {

    private String xmlns;
    private String xmlns$openSearch;
    private String xmlns$gContact;
    private String xmlns$batch;
    private String xmlns$gd;
    private Id id;
    private Updated updated;
    private List<Category> category = new ArrayList<Category>();
    private Title title;
    private List<Link> link = new ArrayList<Link>();
    private List<Author> author = new ArrayList<Author>();
    private Generator generator;
    private OpenSearch$totalResults openSearch$totalResults;
    private OpenSearch$startIndex openSearch$startIndex;
    private OpenSearch$itemsPerPage openSearch$itemsPerPage;
    private List<Entry> entry = new ArrayList<Entry>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXmlns$openSearch() {
        return xmlns$openSearch;
    }

    public void setXmlns$openSearch(String xmlns$openSearch) {
        this.xmlns$openSearch = xmlns$openSearch;
    }

    public String getXmlns$gContact() {
        return xmlns$gContact;
    }

    public void setXmlns$gContact(String xmlns$gContact) {
        this.xmlns$gContact = xmlns$gContact;
    }

    public String getXmlns$batch() {
        return xmlns$batch;
    }

    public void setXmlns$batch(String xmlns$batch) {
        this.xmlns$batch = xmlns$batch;
    }

    public String getXmlns$gd() {
        return xmlns$gd;
    }

    public void setXmlns$gd(String xmlns$gd) {
        this.xmlns$gd = xmlns$gd;
    }

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

    public List<Link> getLink() {
        return link;
    }

    public void setLink(List<Link> link) {
        this.link = link;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public OpenSearch$totalResults getOpenSearch$totalResults() {
        return openSearch$totalResults;
    }

    public void setOpenSearch$totalResults(OpenSearch$totalResults openSearch$totalResults) {
        this.openSearch$totalResults = openSearch$totalResults;
    }

    public OpenSearch$startIndex getOpenSearch$startIndex() {
        return openSearch$startIndex;
    }

    public void setOpenSearch$startIndex(OpenSearch$startIndex openSearch$startIndex) {
        this.openSearch$startIndex = openSearch$startIndex;
    }

    public OpenSearch$itemsPerPage getOpenSearch$itemsPerPage() {
        return openSearch$itemsPerPage;
    }

    public void setOpenSearch$itemsPerPage(OpenSearch$itemsPerPage openSearch$itemsPerPage) {
        this.openSearch$itemsPerPage = openSearch$itemsPerPage;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
