package com.jukebox.test.service;

import com.jukebox.test.dto.JukeBox;
import com.jukebox.test.dto.Setting;
import com.jukebox.test.exception.NotFoundException;
import com.jukebox.test.repository.JukeboxClientRepository;
import com.jukebox.test.repository.JukeboxSettingsClientRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.*;

@Component
public class JukeBoxControlSettingsServiceImpl implements JukeBoxControlSettingsService {

    @Autowired
    private JukeboxClientRepository jukeboxClientRepository;

    @Autowired
    private JukeboxSettingsClientRepository jukeboxSettingsClientRepository;

    @Override
    public Page<JukeBox> findAllJukeboxesBySettingId(UUID settingId, String model, Integer offSet, Integer size) throws Exception {

        Setting setting = jukeboxSettingsClientRepository.getAll().orElseThrow(() -> new NotFoundException("Not found Elements for juke box settings Service"))
                .getSettings()
                .stream()
                .filter(
                        s -> s.getId().equals(settingId)
                )
                .findFirst()
                .orElseThrow(() -> new Exception("The setting $settingId doesn't exist"));
        List<JukeBox> result = jukeboxClientRepository.getAll()
                .orElseThrow(() -> new NotFoundException("Not found Elements for juke box settings Service"))
                .stream()
                .filter(jukeBox -> test(jukeBox, setting))
                .collect(toList());
        if(!StringUtils.isBlank(model)){
            result = result.stream()
                    .filter(jukeBox -> jukeBox.getModel().equals(model))
                    .collect(toList());
        }
        return new PageImpl<>(result, PageRequest.of(offSet, size), result.size());
    }

    private boolean test(JukeBox jukeBox, Setting setting){
        if (setting.getRequires().isEmpty() && jukeBox.getComponents().isEmpty()){
            return true;
        }
        return jukeBox.getComponents().stream().allMatch(component -> setting.getRequires().contains(component.getName()));
    }
}
