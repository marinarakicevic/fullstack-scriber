package com.maven.musicapplication.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Artist {

    private Long id;
    private String name;
    private List<Release> releases;

    public void addRelease(Release release) {

        if (releases == null) {

            releases = new ArrayList<>();
        }

        releases.add(release);
    }

}
