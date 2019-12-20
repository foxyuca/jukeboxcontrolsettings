package com.jukebox.test.service;

import com.jukebox.test.dto.JukeBox;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Component
public interface JukeBoxControlSettingsService {

    Page<JukeBox> findAllJukeboxesBySettingId(UUID settingId, String model, Integer offSet, Integer size) throws Exception;
}
