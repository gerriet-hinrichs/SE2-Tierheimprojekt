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

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Types;

import de.stuff42.apigenerator.data.type.TypeDataElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Type parameter element.
 */
public class TypeParameter extends TypeDataElement<TypeVariable> {

    /**
     * Variable name.
     */
    private String name;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public TypeParameter(TypeVariable element, RestControllerProcessor processor) {
        super(element, processor);
    }

    @Override
    public String getTypescriptName() {
        return name;
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {
        sb.append(name);
    }

    @Override
    public void processElement() {

        // parameter name
        name = element.asElement().getSimpleName().toString();

        // TODO @gerriet type bonds?
    }

    /**
     * Checks if the given type mirror is a type parameter.
     *
     * @param typeMirror            Type mirror element.
     * @param processingEnvironment Processing environment.
     *
     * @return If the given mirror element is a type parameter.
     */
    public static boolean isTypeParameter(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        Types typeUtils = processingEnvironment.getTypeUtils();
        Element element = typeUtils.asElement(typeMirror);

        return element.getKind() == ElementKind.TYPE_PARAMETER;
    }
}
