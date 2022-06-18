package com.augmen.playlistr;

import com.augmen.playlistr.Spotify.Tag.Tag;
import com.augmen.playlistr.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.net.URL;
import java.util.Map;

public final class Common {

    public static Map<String, Object> parseJsonStringForMap(String json) {
        JsonParser parser = JsonParserFactory.getJsonParser();
        return parser.parseMap(json);
    }

    public static String getResourcePath(String resourceName) {
        URL resource = Common.class.getClassLoader().getResource(resourceName);
        if(resource != null) {
            String path =resource.getPath();
            if(path.startsWith("/"))
                path = path.substring(1);
            return path;
        }

        return "";
    }
}
