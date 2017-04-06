package de.stuff42.se2tierheimprojekt.controller;

import de.stuff42.se2tierheimprojekt.system.BaseComponent;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

/**
 * Base controller class providing utility methods for all controllers.
 *
 * @param <T> Service class type.
 */
public abstract class BaseController<T> extends BaseComponent {

    /**
     * Service instance that belongs to this controller.
     */
    protected final T service;

    /**
     * Creates instance for the given service.
     *
     * @param serviceInstance Service instance.
     */
    public BaseController(T serviceInstance) {
        this.service = serviceInstance;
    }

    /**
     * Creates a {@link ModelAndView} instance for the given view and view data.
     *
     * @param view  View name to be used.
     * @param model View data.
     *
     * @return Created {@link ModelAndView} instance.
     */
    @NotNull
    public static ModelAndView createModelAndView(@NotNull String view, Object model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(view);

        // set model as root
        modelAndView.addObject("model", model);

        return modelAndView;
    }

    /**
     * @return Phase number (defaults to phase <code>2</code>).
     *
     * @inheritDoc
     */
    @Override
    public int getPhase() {
        return 2;
    }

    @Override
    protected void startAction() {
        logger.info("Controller {} loaded.", super.getName());
    }
}
