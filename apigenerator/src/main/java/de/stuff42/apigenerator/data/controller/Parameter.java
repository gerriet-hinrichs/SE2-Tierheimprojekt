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
package de.stuff42.apigenerator.data.controller;

import javax.lang.model.element.VariableElement;

import de.stuff42.apigenerator.data.DataElement;
import de.stuff42.apigenerator.data.type.TypeDataElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Parameter data element class.
 */
public class Parameter extends DataElement<VariableElement> {

    /**
     * Parameter type.
     */
    private Lazy<TypeDataElement<?>> type;

    /**
     * Method instance for this parameter.
     */
    private Method method;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     * @param method    Method instance.
     */
    Parameter(VariableElement element, RestControllerProcessor processor, Method method) {
        super(element, processor);
        this.method = method;
        type = new Lazy<>(() -> processor.processDataType(element.asType()));
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // path variable?
        PathVariable pathVariable = element.getAnnotation(PathVariable.class);
        if (pathVariable != null) {
            method.addPathVariable("".equals(pathVariable.name()) ? element.getSimpleName().toString() : pathVariable.name(),
                    element.getSimpleName().toString());
        }

        // query parameter?
        RequestParam requestParam = element.getAnnotation(RequestParam.class);
        if (requestParam != null) {
            method.addQueryParameter("".equals(requestParam.name()) ? element.getSimpleName().toString() : requestParam.name(),
                    element.getSimpleName().toString());
        }

        // body parameter
        if (element.getAnnotation(RequestBody.class) != null) {
            method.setBodyVariableName(element.getSimpleName().toString());
        }

        // generate header
        sb.append(element.getSimpleName()).append(": ")
                .append(processor.getTypeAlias(getAliasBaseName(), type.value(), element.getAnnotation(NotNull.class) != null));
    }

    /**
     * Returns the alias base name for this controller.
     *
     * @return Alias base name.
     */
    private String getAliasBaseName() {
        return method.getAliasBaseName() + '$' + element.getSimpleName().toString();
    }
}
