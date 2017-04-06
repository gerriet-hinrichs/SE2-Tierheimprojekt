package de.stuff42.utils.data;

import org.apache.commons.collections4.Factory;

/**
 * Class for thread save lazy initialized, immutable fields.
 */
public class Lazy<T> {

    /**
     * Contains the evaluated value.
     */
    private T value = null;

    /**
     * Factory to function to get the value.
     * Will be set to <code>null</code> once it has been called.
     */
    private Factory<T> valueFactory = null;

    /**
     * Creates a new lazy object with the given value factory.
     *
     * @param valueFactory Value factory.
     */
    public Lazy(Factory<T> valueFactory) {
        this.valueFactory = valueFactory;
    }

    /**
     * Checks if this instance was already evaluated.
     *
     * @return If this instance was already evaluated.
     */
    public boolean isEvaluated() {
        return this.valueFactory == null;
    }

    /**
     * Evaluates this instance (if required).
     * <p>
     * Calling this method multiple times will only perform evaluation within first call. Following calls will perform
     * no action.
     */
    public void evaluate() {

        // check if we have to do stuff
        if (!this.isEvaluated()) {

            // thread-save evaluation
            synchronized (this) {

                // check if others already had initialized (within the time we where blocked)
                if (!this.isEvaluated()) {

                    // call factory
                    this.value = this.valueFactory.create();

                    // reset factory (allow garbage collection and as info about evaluation)
                    this.valueFactory = null;
                }
            }
        }
    }

    /**
     * Evaluates (if needed) this lazy and returns its value.
     *
     * @return Contained lazy evaluated value.
     */
    public T value() {
        this.evaluate();
        return this.value;
    }
}
