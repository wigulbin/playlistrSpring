package com.augmen.playlistr.repositories;

import com.augmen.playlistr.Spotify.Tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
