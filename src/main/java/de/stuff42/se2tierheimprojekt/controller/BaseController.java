/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
