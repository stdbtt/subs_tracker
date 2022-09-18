package stdbtt.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stdbtt.tracker.dto.TrackingConfigDTO;
import stdbtt.tracker.model.Channel;
import stdbtt.tracker.model.Customer;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.service.ChannelService;
import stdbtt.tracker.service.TrackingConfigService;
import stdbtt.tracker.service.TrackingLapService;
import stdbtt.tracker.util.ChartData;
import stdbtt.tracker.util.ChartDataMapper;
import stdbtt.tracker.util.DtoToEntityUtil;
import stdbtt.tracker.util.EntityToDtoUtil;
import stdbtt.tracker.dto.CustomerDTO;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TrackingLapController {

    private final HttpSession httpSession;

    private final TrackingConfigService trackingConfigService;

    private final TrackingLapService trackingLapService;

    private final ChannelService channelService;

    private final DtoToEntityUtil dtoToEntityUtil;

    private final EntityToDtoUtil entityToDtoUtil;

    @Autowired
    public TrackingLapController(HttpSession httpSession, TrackingConfigService trackingConfigService, TrackingLapService trackingLapService, ChannelService channelService, DtoToEntityUtil dtoToEntityUtil, EntityToDtoUtil entityToDtoUtil) {
        this.httpSession = httpSession;
        this.trackingConfigService = trackingConfigService;
        this.trackingLapService = trackingLapService;
        this.channelService = channelService;
        this.dtoToEntityUtil = dtoToEntityUtil;
        this.entityToDtoUtil = entityToDtoUtil;
    }

    @GetMapping("/trackingLap/selectStatsParameters")
    public String chooseTrackingConfigForm(Model model) {
        Customer customer = dtoToEntityUtil.convert((CustomerDTO) httpSession.getAttribute("customer"));
        List<TrackingConfigDTO> tDtos = trackingConfigService.findTrackingConfigsFetchChannelByCustomerName(customer.getName())
                .stream()
                .map(tc -> {
                    TrackingConfigDTO tDto = entityToDtoUtil.convert(tc);
                    tDto.setChannelDTO(entityToDtoUtil.convert(tc.getChannel()));
                    return tDto;
                })
                .collect(Collectors.toList());
        model.addAttribute("trackingConfigs", tDtos);
        model.addAttribute("trackingConfig", new TrackingConfigDTO());
        return "trackingLap/selectStatsParameters";
    }

    @PostMapping("/trackingLap/selectStatsParameters")
    public String chooseTrackingConfig(@ModelAttribute("trackingConfig") TrackingConfigDTO trackingConfigDTO,
                                       @RequestParam("start_time") String startTime,
                                       @RequestParam("end_time") String endTime) {
        httpSession.setAttribute("trackingConfig", trackingConfigDTO);
        httpSession.setAttribute("start_time", LocalDateTime.parse(startTime).atZone(ZoneId.systemDefault()).toOffsetDateTime());
        httpSession.setAttribute("end_time", LocalDateTime.parse(endTime).atZone(ZoneId.systemDefault()).toOffsetDateTime());
        return "redirect:/trackingLap/showStats";
    }

    @GetMapping("/trackingLap/showStats")
    public String showStats(Model model){
        TrackingConfigDTO tDto = (TrackingConfigDTO) httpSession.getAttribute("trackingConfig");
        Channel channel = channelService.findChannelByTrackingConfigId(tDto.getTrackingConfigId());
        model.addAttribute("channelName", channel.getName());
        return "trackingLap/showStats";
    }

    @ResponseBody
    @GetMapping("/trackingLap/chartData")
    public List<ChartData> getChartData() {
        TrackingConfig trackingConfig = dtoToEntityUtil.convert((TrackingConfigDTO) httpSession.getAttribute("trackingConfig"));
        OffsetDateTime startTime = (OffsetDateTime) httpSession.getAttribute("start_time");
        OffsetDateTime endTime = (OffsetDateTime) httpSession.getAttribute("end_time");
        return trackingLapService.findAllByTrackingConfigBetween(trackingConfig, startTime, endTime)
                .stream()
                .map(entityToDtoUtil::convert)
                .map(ChartDataMapper::map)
                .collect(Collectors.toList());
    }
}
