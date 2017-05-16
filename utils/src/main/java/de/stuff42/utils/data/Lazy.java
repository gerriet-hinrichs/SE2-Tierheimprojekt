/*
 * Utilities for use in various applications.
 * Copyright (C) 2017
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
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
