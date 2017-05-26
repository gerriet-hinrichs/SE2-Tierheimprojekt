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
package de.stuff42.apigenerator.data.type.object;

import java.util.LinkedList;
import java.util.List;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.data.type.TypeDataElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Declared type element.
 * <p>
 * Type element for type access on generic types with bound parameters.
 */
public class DeclaredTypeElement extends TypeDataElement<DeclaredType> {

    /**
     * Parent type.
     */
    private Lazy<TypeDataElement<?>> type;

    /**
     * Type parameters.
     */
    private Lazy<List<TypeDataElement<?>>> typeParameters;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public DeclaredTypeElement(DeclaredType element, RestControllerProcessor processor) {
        super(element, processor);
        type = new Lazy<>(() -> processor.processDeclared(element));
        typeParameters = new Lazy<>(() -> {
            LinkedList<TypeDataElement<?>> parameters = new LinkedList<>();
            for (TypeMirror parameterMirror : element.getTypeArguments()) {
                parameters.add(processor.processDataType(parameterMirror));
            }
            return parameters;
        });
    }

    @Override
    public String getRawTypescriptName() {
        return type.value().getRawTypescriptName();
    }

    @Override
    public String getTypescriptName() {
        return getRawTypescriptName() + Utilities.generateGenericArguments(typeParameters.value(), false);
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {
        type.value().generateTypescript(sb, level, indentation);
    }

    @Override
    public TypeMirror getTypeMirror() {
        return element;
    }

    @Override
    public boolean ignoreWithinBondsAndInheritance() {
        return type.value().ignoreWithinBondsAndInheritance();
    }

    @Override
    public boolean supportsNull() {
        return type.value().supportsNull();
    }
}
