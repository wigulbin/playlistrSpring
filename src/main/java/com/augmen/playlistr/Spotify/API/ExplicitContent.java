package com.augmen.playlistr.Spotify.API;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExplicitContent {
    @JsonProperty("filter_enabled")
    private boolean filterEnabled;
    @JsonProperty("filter_locked")
    private boolean filterLocked;

    public ExplicitContent() {
    }

    public boolean isFilterEnabled() {
        return filterEnabled;
    }

    public void setFilterEnabled(boolean filterEnabled) {
        this.filterEnabled = filterEnabled;
    }

    public boolean isFilterLocked() {
        return filterLocked;
    }

    public void setFilterLocked(boolean filterLocked) {
        this.filterLocked = filterLocked;
    }
}
