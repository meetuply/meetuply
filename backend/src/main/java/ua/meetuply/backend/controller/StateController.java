package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.meetuply.backend.model.State;
import ua.meetuply.backend.service.StateService;

@RequestMapping("api/states")
@RestController
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public @ResponseBody
    Iterable<State> getStates() {
        return stateService.getAll().values();
    }

}
