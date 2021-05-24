package com.maven.musicapplication.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Release {

    private Long id;
    private String title;
    private String notes;
    private List<Artist> artists;
    private List<Track> trackList;

    public void setArtists(List<Artist> artists) {

        for (Artist a:
             artists) {

            a.addRelease(this);
        }

        this.artists = artists;
    }

    public void setTrackList(List<Track> trackList) {

        for (Track t:
             trackList) {

            t.setRelease(this);
        }

        this.trackList = trackList;
    }

}
