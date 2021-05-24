package com.maven.musicapplication.controller;

import com.maven.musicapplication.component.ResponseHandler;
import com.maven.musicapplication.dto.Artist;
import com.maven.musicapplication.dto.Release;
import com.maven.musicapplication.dto.Track;
import com.maven.musicapplication.service.SuggestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/suggest")
public class SuggestionController {

    private SuggestionService suggestionService;
    private ResponseHandler responseHandler;

    public SuggestionController(SuggestionService suggestionService, ResponseHandler responseHandler) {

        this.suggestionService = suggestionService;
        this.responseHandler = responseHandler;
    }

    @GetMapping("/tracks")
    public ResponseEntity<?> getTracksSuggestions(@RequestParam("prefix") String prefix) {

        try {

            List<Track> suggestion = suggestionService.getTracksSuggestions(prefix);
            Map<String, Object> responseBody = responseHandler.generateResponseForTracks(suggestion);

            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {

            return ResponseEntity.badRequest().body("Something went wrong.");
        }
    }

    @GetMapping("/artists")
    public ResponseEntity<?> getArtistsSuggestions(@RequestParam("prefix") String prefix) {

        try {

            List<Artist> suggestion = suggestionService.getArtistsSuggestions(prefix);
            Map<String, Object> responseBody = responseHandler.generateResponseForArtists(suggestion);

            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {

            return ResponseEntity.badRequest().body("Something went wrong.");
        }
    }

    @GetMapping("/releases")
    public ResponseEntity<?> getReleasesSuggestions(@RequestParam("prefix") String prefix) {

        try {

            List<Release> suggestion = suggestionService.getReleasesSuggestions(prefix);
            Map<String, Object> responseBody = responseHandler.generateResponseForReleases(suggestion);

            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {

            return ResponseEntity.badRequest().body("Something went wrong.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSuggestions(@RequestParam("prefix") String prefix) {

        try {

            List<Object> suggestion = suggestionService.getAllSuggestions(prefix);
            Map<String, Object> responseBody = responseHandler.generateResponseForAll(suggestion);

            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {

            return ResponseEntity.badRequest().body("Something went wrong.");
        }

    }
}
