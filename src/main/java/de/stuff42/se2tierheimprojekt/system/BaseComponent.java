package de.stuff42.se2tierheimprojekt.system;

import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import de.stuff42.utils.logging.LoggingUtils;

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
