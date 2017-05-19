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

import javax.lang.model.element.Element;

import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Base for data classes.
 */
public abstract class DataElement<T extends Element> {

    /**
     * Mirror element this data element is generated from.
     */
    protected final T element;

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
        processElement();
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
    protected abstract void processElement();
}
