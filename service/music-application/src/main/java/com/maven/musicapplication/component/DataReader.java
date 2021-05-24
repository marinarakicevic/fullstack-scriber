package com.maven.musicapplication.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maven.musicapplication.dto.Artist;
import com.maven.musicapplication.dto.Release;
import com.maven.musicapplication.dto.Track;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataReader {

    private ObjectMapper objectMapper;

    public DataReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Release> readAllReleases() {

        List<Release> allReleases = new ArrayList<>();

        try {

            InputStream is = new ClassPathResource("data.json").getInputStream();
            JsonNode jsonNode = objectMapper.readTree(is);

            JsonNode releases = jsonNode.get("releases");

            for (int i = 0; i < releases.size(); i++) {

                JsonNode release = releases.get(i);

                Long id = release.get("Id").asLong();
                String title = release.get("Title").asText();
                String notes = release.get("Notes").asText();

                List<Artist> artists = readArtists(release.get("Artists"));

                List<Track> trackList = readTracks(release.get("TrackList"));

                Release newRelease = new Release();
                newRelease.setId(id);
                newRelease.setTitle(title);
                newRelease.setNotes(notes);
                newRelease.setArtists(artists);
                newRelease.setTrackList(trackList);

                allReleases.add(newRelease);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }

        return allReleases;
    }

    private List<Track> readTracks(JsonNode trackList) {

        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < trackList.size(); i++) {

            JsonNode track =  trackList.get(i);

            String title = track.get("Title").asText();
            String duration = track.get("Duration").asText();

            Track newTrack = new Track();
            newTrack.setTitle(title);
            newTrack.setDuration(duration);

            tracks.add(newTrack);
        }

        return tracks;
    }

    private List<Artist> readArtists(JsonNode artists) {

        List<Artist> retArtists = new ArrayList<>();

        for (int i = 0; i < artists.size(); i++) {

            JsonNode artist =  artists.get(i);

            Long id = artist.get("Id").asLong();
            String name = artist.get("Name").asText();

            Artist newArtist = new Artist();
            newArtist.setId(id);
            newArtist.setName(name);

            retArtists.add(newArtist);
        }

        return retArtists;
    }
}
