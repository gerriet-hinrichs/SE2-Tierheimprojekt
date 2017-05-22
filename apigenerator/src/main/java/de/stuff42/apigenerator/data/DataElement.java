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
package de.stuff42.apigenerator.data;

import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Base for data classes.
 *
 * @param <T> Type mirror element type.
 */
public abstract class DataElement<T> {

    /**
     * Mirror element this data element is generated from.
     */
    protected final T element;

    /**
     * Processed flag.
     */
    private boolean processed = false;

    /**
     * Processor instance.
     */
    protected final RestControllerProcessor processor;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public DataElement(T element, RestControllerProcessor processor) {
        this.element = element;
        this.processor = processor;
    }

    /**
     * Generates the typescript code snippet for this data element into the given string builder.
     *
     * @param sb    String builder instance.
     * @param level Indentation for nested elements.
     */
    public void generateTypescript(StringBuilder sb, int level) {
        generateTypescript(sb, level, Utilities.getIndentationString(level));
    }

    /**
     * Generates the typescript code snippet for this data element into the given string builder.
     *
     * @param sb          String builder instance.
     * @param level       Indentation for nested elements.
     * @param indentation String for use as indentation to the given level.
     */
    public abstract void generateTypescript(StringBuilder sb, int level, String indentation);

    /**
     * Generates the typescript code snippet for this data element into the given string builder.
     *
     * @param sb String builder instance.
     */
    public void generateTypescript(StringBuilder sb) {
        generateTypescript(sb, 0);
    }

    /**
     * Processes the contained element.
     */
    public abstract void processElement();

    /**
     * Returns the export file name relative to the export directory.
     * This method returns <code>null</code> if there is no export required.
     *
     * @return Export file name or <code>null</code>.
     */
    public String getExportFileName() {
        return null;
    }

    /**
     * Returns the mirror element for this data element.
     *
     * @return Mirror element.
     */
    public T getElement() {
        return element;
    }
}
