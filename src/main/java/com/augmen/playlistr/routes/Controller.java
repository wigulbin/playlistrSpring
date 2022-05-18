package com.augmen.playlistr.routes;

import com.augmen.playlistr.Spotify.API.Playlists;
import com.augmen.playlistr.Spotify.API.Track;
import com.augmen.playlistr.Spotify.API.Tracks;
import com.augmen.playlistr.Spotify.Spotify;
import com.augmen.playlistr.Spotify.SpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SessionAttributes({"client", "profile"})
@org.springframework.stereotype.Controller
public class Controller {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        Spotify.initialize();
        model.addAttribute("appName", appName);
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
        model.addAttribute("fetchPlaylists", true);

        return new ModelAndView ("redirect:/", model);
    }

    @GetMapping("/playlist")
    public ModelAndView getPlaylists(ModelMap model){
        SpotifyClient client = (SpotifyClient) model.getAttribute("client");
        Playlists playlists = client.getPlaylistsForUser();

        return new ModelAndView("redirect:/", model);
    }

    //Todo fix tracks so info is populated
    @GetMapping("/tracks")
    public ModelAndView getTracks(ModelMap model){
        SpotifyClient client = (SpotifyClient) model.getAttribute("client");
        List<Track> tracks = client.getTrackListForCurrentUser();
        model.addAttribute("tracks", tracks);

        return new ModelAndView("tracks", model);
    }
}
