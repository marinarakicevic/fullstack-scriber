package com.maven.musicapplication.component;

import com.maven.musicapplication.dto.Artist;
import com.maven.musicapplication.dto.Release;
import com.maven.musicapplication.dto.Track;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseHandler {

    public Map<String, Object> generateResponseForTracks(List<Track> suggestion) {

        List<Map<String,Object>> tracks = new ArrayList<>();

        for (Track t:
             suggestion) {

            Map<String, Object> track = mapTrack(t);
            tracks.add(track);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("suggestions", tracks);

        return response;
    }

    public Map<String, Object> generateResponseForArtists(List<Artist> suggestion) {

        List<Map<String,Object>> artists = new ArrayList<>();

        for (Artist a:
                suggestion) {

            Map<String, Object> artist = mapArtist(a);
            artists.add(artist);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("suggestions", artists);

        return response;
    }

    public Map<String, Object> generateResponseForReleases(List<Release> suggestion) {

        List<Map<String,Object>> releases = new ArrayList<>();

        for (Release r:
                suggestion) {

            Map<String, Object> release = mapRelease(r);
            releases.add(release);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("suggestions", releases);

        return response;
    }

    public Map<String, List<Map<String,Object>>>  generateResponseForAll(List<Object> suggestions) {

        List<Map<String,Object>> artists = new ArrayList<>();
        List<Map<String,Object>> tracks = new ArrayList<>();
        List<Map<String,Object>> releases = new ArrayList<>();

        for (Object o:
                suggestions) {

            if (o instanceof Artist) {

                Artist a = (Artist) o;
                Map<String, Object> artist = mapArtist(a);
                artists.add(artist);
            } else if (o instanceof Track) {

                Track t = (Track) o;
                Map<String, Object> track = mapTrack(t);
                tracks.add(track);
            } else if (o instanceof Release) {

                Release r = (Release) o;
                Map<String, Object> release = mapRelease(r);
                releases.add(release);
            }
        }

        Map<String, List<Map<String,Object>>> all = new LinkedHashMap<>();
        all.put("artists", artists);
        all.put("tracks", tracks);
        all.put("releases", releases);

        return all;
    }

    private Map<String, Object> mapTrack(Track t) {

        Map<String, Object> track = new LinkedHashMap<>();
        track.put("title", t.getTitle());
        track.put("duration", t.getDuration());

        Map<String, Object> release = new LinkedHashMap<>();
        Release r = t.getRelease();
        putRelease(release, r);

        track.put("release", release);

        return track;
    }

    private Map<String, Object> mapArtist(Artist a) {

        Map<String, Object> artist = new LinkedHashMap<>();
        putArtist(artist, a);

        List<Release> artistsReleases = a.getReleases();

        List<Map<String, Object>> releases = new ArrayList<>();

        for (Release r:
                artistsReleases) {

            Map<String, Object> release = new LinkedHashMap<>();
            putRelease(release, r);

            releases.add(release);
        }

        artist.put("releases", releases);

        return artist;
    }

    private Map<String, Object> mapRelease(Release r) {

        Map<String, Object> release = new LinkedHashMap<>();
        putRelease(release, r);

        List<Artist> releasesArtists = r.getArtists();

        List<Map<String, Object>> artists = new ArrayList<>();

        for (Artist a:
                releasesArtists) {

            Map<String, Object> artist = new LinkedHashMap<>();
            putArtist(artist, a);

            artists.add(artist);
        }

        release.put("artist", artists);

        return release;
    }

    private void putRelease(Map<String, Object> release, Release r) {

        release.put("id", r.getId());
        release.put("title", r.getTitle());
        release.put("notes", r.getNotes());
    }

    private void putArtist(Map<String, Object> artist, Artist a) {

        artist.put("id", a.getId());
        artist.put("name", a.getName());
    }

}
