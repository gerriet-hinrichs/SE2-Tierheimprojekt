package de.stuff42.se2tierheimprojekt.service;

import de.stuff42.se2tierheimprojekt.system.BaseComponent;

/**
 * Base service class providing utility methods for all services.
 */
public abstract class BaseService extends BaseComponent {

    /**
     * @return Phase number (defaults to phase <code>1</code>).
     *
     * @inheritDoc
     */
    @Override
    public int getPhase() {
        return 1;
    }

    @Override
    protected void startAction() {
        logger.info("Service {} loaded.", super.getName());
    }
}
