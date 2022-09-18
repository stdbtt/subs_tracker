package stdbtt.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import stdbtt.tracker.dto.ChannelDTO;
import stdbtt.tracker.dto.TrackingConfigDTO;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.service.TrackingConfigService;
import stdbtt.tracker.util.DtoToEntityUtil;
import stdbtt.tracker.util.TimeUnitWithMonth;
import stdbtt.tracker.util.TrackingConfigValidator;
import stdbtt.tracker.dto.CustomerDTO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class TrackingConfigController {

    private final HttpSession httpSession;

    private final TrackingConfigService trackingConfigService;

    private final TrackingConfigValidator trackingConfigValidator;

    private final DtoToEntityUtil dtoToEntityUtil;


    @Autowired
    public TrackingConfigController(HttpSession httpSession, TrackingConfigService trackingConfigService, TrackingConfigValidator trackingConfigValidator, DtoToEntityUtil dtoToEntityUtil) {
        this.httpSession = httpSession;
        this.trackingConfigService = trackingConfigService;
        this.trackingConfigValidator = trackingConfigValidator;
        this.dtoToEntityUtil = dtoToEntityUtil;
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

}
