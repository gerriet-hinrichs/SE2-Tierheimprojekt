package de.stuff42.se2tierheimprojekt.controller;

import de.stuff42.se2tierheimprojekt.model.AppStartModel;
import de.stuff42.se2tierheimprojekt.service.AppStartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller that provides the basic web-page frame.
 */
@Controller
public class AppStartController extends BaseController<AppStartService> {

    /**
     * Creates instance for the given service.
     *
     * @param serviceInstance Service instance.
     */
    @Autowired
    public AppStartController(AppStartService serviceInstance) {
        super(serviceInstance);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return createModelAndView("index", new AppStartModel(service));
    }

}
