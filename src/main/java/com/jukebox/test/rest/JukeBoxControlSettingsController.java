package com.jukebox.test.rest;

import com.jukebox.test.dto.JukeBox;
import com.jukebox.test.exception.NotFoundException;
import com.jukebox.test.service.JukeBoxControlSettingsService;
import com.jukebox.test.util.PaginatedResultsRetrievedEvent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/v1/jukebox")
public class JukeBoxControlSettingsController {

    @Autowired
    private JukeBoxControlSettingsService jukeBoxControlSettingsService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @ApiOperation(value = "Search Jukeboxes By setting ID", response = JukeBox.class)
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Jukeboxes returned successfully",
                    response = JukeBox.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "No Jukeboxes found for the requested setting id"
            ),
            @ApiResponse(
                    code = 402,
                    message = "Failed to read jukeboxes"
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        path = "/settingId/{settingId}",
        params = { "offset", "size" })
    public Page<JukeBox> findJukeBoxesBySettings( @ApiParam(value = "Required settingId to match with jukeboxes", required = true)
                                                      @PathVariable final UUID settingId,
                                                  @ApiParam(value = "Optional jukebox model to filter")
                                                      @RequestParam(required = false)final String model,
                                                  @ApiParam(value = "Optional number of the  page to filter")
                                                      @RequestParam(required = false) int offset,
                                                  @ApiParam(value = "Optional number of elements in the  page to filter")
                                                      @RequestParam(required = false) int size,
                                                 UriComponentsBuilder uriBuilder,
                                                 HttpServletResponse response) throws Exception {
        Page<JukeBox> resultPage = jukeBoxControlSettingsService.findAllJukeboxesBySettingId(settingId, model, offset, size);
        if (offset > resultPage.getTotalPages()) {
            throw new NotFoundException("Page not found");
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                JukeBox.class, uriBuilder, response, offset, resultPage.getTotalPages(), size));

        return resultPage;
    }


}
