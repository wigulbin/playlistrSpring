package com.augmen.playlistr.Spotify.Tag;

import com.augmen.playlistr.Spotify.Attributes.Attribute;
import com.augmen.playlistr.Spotify.Attributes.RangeAttribute;
import com.augmen.playlistr.services.TagService;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TAG")
public class Tag {
    @Id
    @GeneratedValue
    private long id;
    private String name = "";
    private String color = "";
    @OneToMany(cascade = {CascadeType.ALL})
    List<Attribute> attributes = new ArrayList<>();
    @OneToMany
    List<Tag> tags = new ArrayList<>();

    public Tag(String name) {
        this.id = Math.abs(new Random().nextLong());
        this.name = name;
        this.color = Integer.toHexString(new Random().nextInt(0x444444, 0xFFFFFF));
    }

    protected Tag() {
    }

    public final static List<Tag> currentTags = new ArrayList<>();

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
        if(currentTags.size() == 0){
            Map<String, List<Attribute>> tagAttributeMap = new HashMap<>();
            tagAttributeMap.put("Minor", List.of(new Attribute("mode", 0)));
            tagAttributeMap.put("Major", List.of(new Attribute("mode", 1)));
            tagAttributeMap.put("Key of C", List.of(new Attribute("key", 0)));
            tagAttributeMap.put("Key of C#", List.of(new Attribute("key", 1)));
            tagAttributeMap.put("Key of D", List.of(new Attribute("key", 2)));
            tagAttributeMap.put("Key of D#", List.of(new Attribute("key", 3)));
            tagAttributeMap.put("Key of E", List.of(new Attribute("key", 4)));
            tagAttributeMap.put("Key of F", List.of(new Attribute("key", 5)));
            tagAttributeMap.put("Key of F#", List.of(new Attribute("key", 6)));
            tagAttributeMap.put("Key of G", List.of(new Attribute("key", 7)));
            tagAttributeMap.put("Key of G#", List.of(new Attribute("key", 8)));
            tagAttributeMap.put("Key of A", List.of(new Attribute("key", 9)));
            tagAttributeMap.put("Key of A#", List.of(new Attribute("key", 10)));
            tagAttributeMap.put("Key of B", List.of(new Attribute("key", 11)));

            tagAttributeMap.put("Has Vocals", List.of(new RangeAttribute("instrumentalness", 0f, 0.49f)));
            tagAttributeMap.put("No Vocals", List.of(new RangeAttribute("instrumentalness", 0.5f, 1f)));
            tagAttributeMap.put("Live", List.of(new RangeAttribute("liveness", 0.8f, 1f)));
            tagAttributeMap.put("Not Live", List.of(new RangeAttribute("liveness", 0f, 0.79f)));
            tagAttributeMap.put("Spoken Word", List.of(new RangeAttribute("speechiness", 0.66f, 1f)));
            tagAttributeMap.put("Music/Speech", List.of(new RangeAttribute("speechiness", 0.33f, 0.65f)));
            tagAttributeMap.put("Music", List.of(new RangeAttribute("speechiness", 0f, 0.32f)));

            tagAttributeMap.forEach((key, value) -> currentTags.add(new Tag(key).addAttributes(value)));
        }

        return currentTags;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
