package com.augmen.playlistr.Spotify;

import com.augmen.playlistr.Spotify.API.Track;

import java.util.*;

public class Tag {
    private long id;
    private String name = "";
    List<Attribute> attributes = new ArrayList<>();
    List<Tag> tags = new ArrayList<>();

    public Tag(String name) {
        this.id = new Random().nextLong();
        this.name = name;
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }

    public Tag addAttributes(List<Attribute> newAttributes){
        newAttributes.forEach(newAttr -> attributes.add(newAttr));
        return this;
    }
    public Tag addAttribute(String name, Object value){
        attributes.add(new Attribute(name, value));
        return this;
    }
    public Tag addAttribute(Attribute attribute){
        attributes.add(attribute);
        return this;
    }

    public static List<Tag> generateDefault() {
        List<Tag> tags = new ArrayList<>();

        Map<String, List<Attribute>> tagAttributeMap = new HashMap<>();
        tagAttributeMap.put("Minor", List.of(new Attribute("mode", 0)));
        tagAttributeMap.put("Major", List.of(new Attribute("mode", 1)));
        tagAttributeMap.put("Key of C", List.of(new Attribute("key", 0)));

        tagAttributeMap.forEach((key, value) -> tags.add(new Tag(key).addAttributes(value)));
        return tags;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
