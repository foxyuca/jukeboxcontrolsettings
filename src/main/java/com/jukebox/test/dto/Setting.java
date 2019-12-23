
package com.jukebox.test.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "requires"
})
public class Setting implements Serializable
{

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("requires")
    private List<String> requires = null;
    private final static long serialVersionUID = 7123065388649551519L;

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }

    public Setting withId(UUID id) {
        this.id = id;
        return this;
    }

    @JsonProperty("requires")
    public List<String> getRequires() {
        return requires;
    }

    @JsonProperty("requires")
    public void setRequires(List<String> requires) {
        this.requires = requires;
    }

    public Setting withRequires(List<String> requires) {
        this.requires = requires;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("requires", requires).toString();
    }

}
