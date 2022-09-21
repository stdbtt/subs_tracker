package stdbtt.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import stdbtt.tracker.dto.ChannelDTO;
import stdbtt.tracker.dto.TrackingConfigDTO;
import stdbtt.tracker.model.Customer;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.service.TrackingConfigService;
import stdbtt.tracker.util.DtoToEntityUtil;
import stdbtt.tracker.util.EntityToDtoUtil;
import stdbtt.tracker.util.TimeUnitWithMonth;
import stdbtt.tracker.util.TrackingConfigValidator;
import stdbtt.tracker.dto.CustomerDTO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TrackingConfigController {

    private final HttpSession httpSession;

    private final TrackingConfigService trackingConfigService;

    private final TrackingConfigValidator trackingConfigValidator;

    private final DtoToEntityUtil dtoToEntityUtil;

    private final EntityToDtoUtil entityToDtoUtil;


    @Autowired
    public TrackingConfigController(HttpSession httpSession, TrackingConfigService trackingConfigService, TrackingConfigValidator trackingConfigValidator, DtoToEntityUtil dtoToEntityUtil, EntityToDtoUtil entityToDtoUtil) {
        this.httpSession = httpSession;
        this.trackingConfigService = trackingConfigService;
        this.trackingConfigValidator = trackingConfigValidator;
        this.dtoToEntityUtil = dtoToEntityUtil;
        this.entityToDtoUtil = entityToDtoUtil;
    }

    @GetMapping("/trackingConfig/new")
    public String addTrackingConfigForm(Model model) {
        model.addAttribute("trackingConfig", new TrackingConfigDTO(new ChannelDTO()));
        model.addAttribute("channel", new ChannelDTO());
        model.addAttribute("time_units", TimeUnitWithMonth.values());
        return "trackingConfig/new";
    }


    @PostMapping("/trackingConfig/new")
    public String addTrackingConfig(@ModelAttribute("trackingConfig") @Valid TrackingConfigDTO trackingConfigDTO,
                                    BindingResult bindingResultTrackingConfig,
                                    @ModelAttribute("channel") @Valid ChannelDTO channelDTO,
                                    BindingResult bindingResultChannel, Model model) {
        trackingConfigDTO.setCustomerDTO((CustomerDTO) httpSession.getAttribute("customer"));
        trackingConfigDTO.setChannelDTO(channelDTO);
        trackingConfigValidator.validate(trackingConfigDTO, bindingResultTrackingConfig, bindingResultChannel);
        if (bindingResultTrackingConfig.hasErrors() || bindingResultChannel.hasErrors()) {
            model.addAttribute("time_units", TimeUnitWithMonth.values());
            return "trackingConfig/new";
        }
        TrackingConfig trackingConfig = dtoToEntityUtil.convert(trackingConfigDTO);
        trackingConfig.setCustomer(dtoToEntityUtil.convert(trackingConfigDTO.getCustomerDTO()));
        trackingConfig.setChannel(dtoToEntityUtil.convert(trackingConfigDTO.getChannelDTO()));
        trackingConfigService.addTrackingConfig(trackingConfig);
        return "redirect:/trackingLap/selectStatsParameters";
    }

    @GetMapping("/trackingConfig/management")
    public String management(Model model){
        CustomerDTO customerDTO = (CustomerDTO) httpSession.getAttribute("customer");
        List<TrackingConfigDTO> tDtos = trackingConfigService.findTrackingConfigsFetchChannelByCustomerName(customerDTO.getName())
                .stream()
                .map(tc -> entityToDtoUtil.convert(tc, true))
                .collect(Collectors.toList());
        model.addAttribute("trackingConfigs", tDtos);

        return "trackingConfig/management";
    }

    @DeleteMapping("/trackingConfig/{id}")
    public String delete(@PathVariable("id") int id){
        trackingConfigService.delete(new TrackingConfig(id));
        return "redirect:/trackingConfig/management";
    }

}
