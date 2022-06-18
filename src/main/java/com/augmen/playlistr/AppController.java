package com.augmen.playlistr;

import com.augmen.playlistr.Spotify.*;
import com.augmen.playlistr.Spotify.API.AudioFeature;
import com.augmen.playlistr.Spotify.API.Playlists;
import com.augmen.playlistr.Spotify.API.Track;
import com.augmen.playlistr.Spotify.Attributes.Attribute;
import com.augmen.playlistr.Spotify.Tag.Tag;
import com.augmen.playlistr.services.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SessionAttributes({"client", "profile"})
@Controller
public class AppController {

    @Autowired
    private TagService tagService;
    @Value("${spring.application.name}")
    String appName;
    @GetMapping("/")
    public String homePage(Model model) throws JsonProcessingException {
        Spotify.initialize();
        ObjectMapper mapper = new ObjectMapper();
        List<Tag> tags = tagService.list();
        if(tags.isEmpty())
        {
            tags.addAll(Tag.generateDefault());
            tagService.save(tags);
        }

        model.addAttribute("appName", appName);
        model.addAttribute("tagJson", mapper.writeValueAsString(Tag.generateDefault()));
        model.addAttribute("tags", tags);
        model.addAttribute("attributes", Attribute.ATTRIBUTES);
        return "playlist";
    }

    @GetMapping("/authorize")
    public RedirectView callback(RedirectAttributes attributes) {
        System.out.println("here");

        return new RedirectView(SpotifyClient.buildAuth());
    }

    @GetMapping("/callback")
    public ModelAndView callback(@RequestParam String code, ModelMap model) {
        SpotifyClient client = SpotifyClient.setupClient(code);
        model.addAttribute("client", client);

        return new ModelAndView ("redirect:/user", model);
    }

    @GetMapping("/user")
    public ModelAndView getUserInfo(HttpServletRequest request, ModelMap model){
        SpotifyClient client = (SpotifyClient) model.getAttribute("client");
        model.addAttribute("profile", client.getUserInfo());

        return new ModelAndView ("redirect:/", model);
    }

    @GetMapping("/playlist")
    public ModelAndView getPlaylists(ModelMap model){
        SpotifyClient client = (SpotifyClient) model.getAttribute("client");
        Playlists playlists = client.getPlaylistsForUser();

        return new ModelAndView("redirect:/", model);
    }

    @GetMapping("/tracks")
    public ModelAndView getTracks(ModelMap model){
        SpotifyClient client = (SpotifyClient) model.getAttribute("client");
        List<Track> tracks = client.getTrackListForCurrentUser();
        Map<String, AudioFeature> featuresByTrackid = client.analyzeTracks(tracks);

        List<TrackInfo> trackInfoList = new ArrayList<>();
        for (Track track : tracks) {
            TrackInfo info = new TrackInfo();
            info.setTrack(track);
            info.setAudioFeature(featuresByTrackid.get(track.getId()));

            trackInfoList.add(info);
        }

        Tagger.autoTagSongs(trackInfoList);

        model.addAttribute("trackInfoList", trackInfoList);
        model.addAttribute("tracks", tracks);
        model.addAttribute("featuresByTrackid", featuresByTrackid);

        return new ModelAndView("tracks", model);
    }

    @GetMapping("/viewAttributes")
    public ModelAndView getTracks(ModelMap model, @QueryParam("tagid") String id){
        model.addAttribute("attributes", Attribute.ATTRIBUTES);
        return new ModelAndView("editAttribute", model);
    }
}
