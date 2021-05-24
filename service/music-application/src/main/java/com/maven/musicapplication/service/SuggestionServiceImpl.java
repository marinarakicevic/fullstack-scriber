package com.maven.musicapplication.service;

import com.maven.musicapplication.dto.Artist;
import com.maven.musicapplication.component.DataReader;
import com.maven.musicapplication.dto.Release;
import com.maven.musicapplication.dto.Track;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {


    private DataReader dataReader;

    private static List<Release> allReleases;

    public SuggestionServiceImpl(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @PostConstruct
    private void initReleases() {

       this.allReleases = new ArrayList<>();
       this.allReleases = dataReader.readAllReleases();
    }

    @Override
    public List<Track> getTracksSuggestions(String prefix) throws IOException {

        List<Track> suggestions = new ArrayList<>();

        for (Release r:
                allReleases) {

            if (suggestions.size() >= 5) {

                return suggestions.stream()
                        .limit(5)
                        .collect(Collectors.toList());
            }

            List<Track> trackList = r.getTrackList();
            trackList = getTracksWithPrefix(trackList, prefix);

            suggestions.addAll(trackList);
        }

        return suggestions;
    }

    @Override
    public List<Artist> getArtistsSuggestions(String prefix) throws IOException {

        List<Artist> suggestions = new ArrayList<>();

        for (Release r:
                allReleases) {

            if (suggestions.size() >= 5) {

                return suggestions.stream()
                        .limit(5)
                        .collect(Collectors.toList());
            }

            List<Artist> artists = r.getArtists();
            artists = getArtistsWithPrefix(artists, prefix);
            suggestions.addAll(artists);
        }

        return suggestions;
    }

    @Override
    public List<Release> getReleasesSuggestions(String prefix) throws IOException {

        List<Release> suggestions = allReleases.stream()
                .filter(r -> r.getTitle().toLowerCase().startsWith(prefix.toLowerCase()))
                .limit(5)
                .collect(Collectors.toList());

        return suggestions;
    }

    @Override
    public List<Object> getAllSuggestions(String prefix) throws IOException {

        List<Object> suggestions = new ArrayList<>();

        for (Release r:
                allReleases) {

            if (suggestions.size() >= 5) {
                return suggestions.stream()
                        .limit(5)
                        .collect(Collectors.toList());
            }

            String title = r.getTitle().toLowerCase();
            if (title.startsWith(prefix.toLowerCase())) {
                suggestions.add(r);
            }

            List<Artist> artists = r.getArtists();
            artists = getArtistsWithPrefix(artists, prefix);
            suggestions.addAll(artists);

            List<Track> tracks = r.getTrackList();
            tracks = getTracksWithPrefix(tracks, prefix);
            suggestions.addAll(tracks);
        }

        return suggestions;
    }

    private List<Artist> getArtistsWithPrefix(List<Artist> artists, String prefix) {

        artists = artists.stream()
                .filter(a -> a.getName().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());

        return artists;
    }

    private List<Track> getTracksWithPrefix(List<Track> tracks, String prefix) {

        tracks = tracks.stream()
                .filter(t -> t.getTitle().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());

        return tracks;
    }

}
