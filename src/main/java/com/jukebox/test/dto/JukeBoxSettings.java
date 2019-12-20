
package com.jukebox.test.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "settings"
})
public class JukeBoxSettings implements Serializable
{

    @JsonProperty("settings")
    private List<Setting> settings = null;
    private final static long serialVersionUID = 8721297911643271325L;

    @JsonProperty("settings")
    public List<Setting> getSettings() {
        return settings;
    }

    @JsonProperty("settings")
    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public JukeBoxSettings withSettings(List<Setting> settings) {
        this.settings = settings;
        return this;
    }

}
