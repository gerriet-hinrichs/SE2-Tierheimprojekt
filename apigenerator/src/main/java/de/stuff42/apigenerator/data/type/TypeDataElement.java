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
package de.stuff42.apigenerator.data.type;

import javax.lang.model.type.TypeMirror;

import de.stuff42.apigenerator.data.DataElement;
import de.stuff42.apigenerator.data.transformer.TransformerDataElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Type data element base class.
 */
public abstract class TypeDataElement extends DataElement<TypeMirror> {

    /**
     * Transformer data element if there is a transformer for this type.
     */
    protected TransformerDataElement transformerDataElement = null;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public TypeDataElement(TypeMirror element, RestControllerProcessor processor) {
        super(element, processor);
    }

    /**
     * Returns the transformer data element for this type.
     * Returns <code>null</code> if there is no transformer for this type.
     *
     * @return Transformer data element or <code>null</code>.
     */
    public TransformerDataElement getTransformer() {
        return transformerDataElement;
    }

    /**
     * Returns the type name for use in typescript code.
     *
     * @return Typescript code name.
     */
    public abstract String getTypescriptName();
}
