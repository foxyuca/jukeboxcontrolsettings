package com.jukebox.test.service;

import com.jukebox.test.dto.Component;
import com.jukebox.test.dto.JukeBox;
import com.jukebox.test.dto.JukeBoxSettings;
import com.jukebox.test.dto.Setting;
import com.jukebox.test.repository.JukeboxClientRepository;
import com.jukebox.test.repository.JukeboxSettingsClientRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.nio.charset.Charset.forName;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@Import({JukeBoxControlSettingsServiceImpl.class, JukeboxClientRepository.class, JukeboxSettingsClientRepository.class})
public class JukeBoxControlSettingsServiceImplTest {


    @Autowired
    private  JukeBoxControlSettingsService jukeBoxControlSettingsService;

    @MockBean
    private JukeboxClientRepository jukeboxClientRepository;

    @MockBean
    private JukeboxSettingsClientRepository jukeboxSettingsClientRepository;

    private JukeBoxSettings jukeBoxSettings;

    private List<JukeBox> jukeBoxes = new ArrayList<>();

    @Before
    public void init() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .objectPoolSize(1)
                .randomizationDepth(3)
                .charset(forName("UTF-8"))
                .stringLengthRange(5, 10)
                .collectionSizeRange(1, 20)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(false)
                .ignoreRandomizationErrors(true);
        EasyRandom easyRandom = new EasyRandom(parameters);
        jukeBoxSettings = easyRandom.nextObject(JukeBoxSettings.class);

       generateFakeJukeBoxes(10, easyRandom);
    }

    private void generateFakeJukeBoxes(int quantity, EasyRandom easyRandom){
        int randomIndex = ThreadLocalRandom.current().nextInt(1, quantity);
        for (int i = 0; i < quantity; i++) {
            List<Component> components = new ArrayList<>();
            JukeBox jukeBox =  easyRandom.nextObject(JukeBox.class);
            jukeBox.getComponents().clear();
            jukeBoxSettings.getSettings().get(randomIndex).getRequires().forEach(s -> {
                Component component = new Component();
                component.setName(s);
                components.add(component);
            });
            jukeBox.setComponents(components);
            jukeBoxes.add(jukeBox);
        }
    }

    @Test
    public void testFindAllJukeboxesBySettingId_FilteringByModel_ShouldReturn_One() throws Exception {
        int randomIndex = ThreadLocalRandom.current().nextInt(1, 10);
        doReturn(Optional.of(jukeBoxes)).when(jukeboxClientRepository).getAll();
        doReturn(Optional.of(jukeBoxSettings)).when(jukeboxSettingsClientRepository).getAll();
       Page<JukeBox> page = jukeBoxControlSettingsService.findAllJukeboxesBySettingId(
                jukeBoxSettings.getSettings().get(randomIndex).getId(),
                jukeBoxes.get(randomIndex).getModel(),
                0,
                1
        );

      assertEquals(1,page.getContent().size());
      assertEquals(1,page.getTotalPages());
    }

    @Test
    public void testFindAllJukeboxesBySettingId() throws Exception {
        int randomIndex = ThreadLocalRandom.current().nextInt(1, 10);
        doReturn(Optional.of(jukeBoxes)).when(jukeboxClientRepository).getAll();
        doReturn(Optional.of(jukeBoxSettings)).when(jukeboxSettingsClientRepository).getAll();
        Page<JukeBox> page = jukeBoxControlSettingsService.findAllJukeboxesBySettingId(
                jukeBoxSettings.getSettings().get(randomIndex).getId(),
                "",
                0,
                1
        );

        assertEquals(10,page.getContent().size());
        assertEquals(10,page.getTotalPages());
    }


    @Test
    public void testFindAllJukeboxesBySettingId_With_Pagination_five() throws Exception {
        int randomIndex = ThreadLocalRandom.current().nextInt(1, 10);
        doReturn(Optional.of(jukeBoxes)).when(jukeboxClientRepository).getAll();
        doReturn(Optional.of(jukeBoxSettings)).when(jukeboxSettingsClientRepository).getAll();
        Page<JukeBox> page = jukeBoxControlSettingsService.findAllJukeboxesBySettingId(
                jukeBoxSettings.getSettings().get(randomIndex).getId(),
                "",
                0,
                5
        );

        assertEquals(10,page.getContent().size());
        assertEquals(2,page.getTotalPages());
    }

    @Test(expected = Exception.class)
    public void testFindAllJukeboxesBySettingId_Should_throw_error_with_invalid_settingId() throws Exception {
        doReturn(jukeBoxes).when(jukeboxClientRepository).getAll();
        doReturn(jukeBoxSettings).when(jukeboxSettingsClientRepository).getAll();
        Page<JukeBox> page = jukeBoxControlSettingsService.findAllJukeboxesBySettingId(
                 UUID.randomUUID(),
                "",
                0,
                5
        );
    }

    @Test(expected = Exception.class)
    public void test_When_Setting_Return_empty_Collections() throws Exception {
        doReturn(Optional.of(jukeBoxes)).when(jukeboxClientRepository).getAll();
        JukeBoxSettings jukeBoxSettings = new JukeBoxSettings();
        jukeBoxSettings.setSettings(new ArrayList<Setting>());
        doReturn(Optional.of(jukeBoxSettings)).when(jukeboxSettingsClientRepository).getAll();
        jukeBoxControlSettingsService.findAllJukeboxesBySettingId(
                UUID.randomUUID(),
                "",
                0,
                5
        );
    }

}
