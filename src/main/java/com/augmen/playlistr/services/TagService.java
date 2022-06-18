package com.augmen.playlistr.services;

import com.augmen.playlistr.Spotify.Tag.Tag;
import com.augmen.playlistr.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@org.jvnet.hk2.annotations.Service(name="tagService")
@Service("tagService")
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> list(){
        return tagRepository.findAll();
    }

    public List<Tag> save(List<Tag> tags){
        return tagRepository.saveAll(tags);
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }
}
