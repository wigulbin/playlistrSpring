package com.augmen.playlistr.routes;

import com.augmen.playlistr.Spotify.Spotify;
import com.augmen.playlistr.Spotify.SpotifyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class Controller {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        Spotify spotify = Spotify.initialize();

        model.addAttribute("appName", appName);

        return "home";
    }

    @GetMapping("/authorize")
    public RedirectView callback(RedirectAttributes attributes) {
        System.out.println("here");

        return new RedirectView(SpotifyClient.buildAuth());
    }

    @GetMapping("/callback")
    public String callback(HttpServletRequest request) {
        SpotifyClient.setupClient(request);

        return "home";
    }

    @GetMapping("/playlist")
    public String getPlaylists(HttpServletRequest request){
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");

        return "";
    }
}
