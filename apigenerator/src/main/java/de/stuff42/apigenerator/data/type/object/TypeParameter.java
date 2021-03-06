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

import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

import de.stuff42.apigenerator.data.type.TypeDataElement;
import de.stuff42.apigenerator.data.type.UnsupportedTypeElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Type parameter element.
 */
public class TypeParameter extends TypeDataElement<TypeVariable> {

    /**
     * Variable name.
     */
    private Lazy<String> name;

    /**
     * Bond for this type variable.
     * We ignore interfaces, so we only have a parent class as bond.
     */
    private Lazy<TypeDataElement<?>> bond;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public TypeParameter(TypeVariable element, RestControllerProcessor processor) {
        super(element, processor);
        name = new Lazy<>(() -> element.asElement().getSimpleName().toString());
        bond = new Lazy<>(() -> processor.processDataType(element.getUpperBound()));
    }

    @Override
    public String getTypescriptName() {
        return name.value();
    }

    @Override
    public boolean supportsNull() {
        return bond.value().supportsNull();
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {
        sb.append(name.value());

        // handle bond properly
        TypeDataElement<?> bondElement = bond.value();
        if (!(bondElement.ignoreWithinBondsAndInheritance() || bondElement instanceof UnsupportedTypeElement)) {
            sb.append(" extends ").append(bondElement.getNullAwareTypescriptName());
        }
    }

    @Override
    public TypeMirror getTypeMirror() {
        return element;
    }
}
