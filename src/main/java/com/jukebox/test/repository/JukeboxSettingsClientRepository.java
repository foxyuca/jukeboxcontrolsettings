package com.jukebox.test.repository;

import com.jukebox.test.dto.JukeBoxSettings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name="settings", url="${externalJukeboxServer.url}")
public interface JukeboxSettingsClientRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/touchtunes/tech-assignment/settings")
    Optional<JukeBoxSettings> getAll();

}
