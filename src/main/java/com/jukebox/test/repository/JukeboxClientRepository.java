package com.jukebox.test.repository;

import com.jukebox.test.dto.JukeBox;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;


@FeignClient(name="jukes", url="${externalJukeboxServer.url}")
public interface JukeboxClientRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/touchtunes/tech-assignment/jukes")
    Optional<List<JukeBox>> getAll();

}
