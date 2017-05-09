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
package de.stuff42.se2tierheimprojekt.system;

import de.stuff42.utils.logging.LoggingUtils;

import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

/**
 * Base component providing some basic stuff like logger, etc.
 */
public abstract class BaseComponent implements SmartLifecycle {

    /**
     * Logger instance for this component.
     */
    protected final Logger logger;

    /**
     * Simplified name cache.
     */
    private String simplifiedName;

    /**
     * Flag if this component is currently running.
     */
    private boolean running = false;

    /**
     * Initializes logger.
     */
    protected BaseComponent() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Returns the name of this component used for logging.
     *
     * @return Component name.
     */
    public String getName() {
        if (simplifiedName == null) {
            simplifiedName = LoggingUtils.getSimplifiedName(this.getClass().getCanonicalName());
        }
        return simplifiedName;
    }

    /**
     * Action to be performed on component startup.
     * <p>
     * Only prints message about component loading on default.
     */
    protected void startAction() {
        logger.info("Component {} loaded.", getName());
    }

    /**
     * Action to be performed on component termination.
     * <p>
     * Does nothing on default.
     */
    protected void stopAction() {
        // do nothing on default
    }

    @Override
    public final void start() {
        if (running) {
            return;
        }
        running = true;
        this.startAction();
    }

    @Override
    public final void stop() {
        if (!running) {
            return;
        }
        running = false;
        this.stopAction();

    }

    @Contract(pure = true)
    @Override
    public final boolean isRunning() {
        return running;
    }

    /**
     * @return If this component should auto-startup (defaults to <code>true</code>).
     *
     * @inheritDoc
     */
    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public final void stop(Runnable callback) {
        stop();

        // the given callback MUST be called after termination
        callback.run();
    }

    /**
     * @return Phase number (defaults to phase <code>0</code>).
     *
     * @inheritDoc
     */
    @Override
    public int getPhase() {
        return 0;
    }
}
