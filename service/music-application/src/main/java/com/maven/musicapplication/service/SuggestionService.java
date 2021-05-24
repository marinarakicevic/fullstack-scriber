package com.maven.musicapplication.service;

import com.maven.musicapplication.dto.Artist;
import com.maven.musicapplication.dto.Release;
import com.maven.musicapplication.dto.Track;

import java.io.IOException;
import java.util.List;

public interface SuggestionService {

    public List<Track> getTracksSuggestions(String title) throws IOException;
    public List<Artist> getArtistsSuggestions(String name) throws IOException;
    public List<Release> getReleasesSuggestions(String title) throws IOException;
    public List<Object> getAllSuggestions(String query) throws IOException;
}
