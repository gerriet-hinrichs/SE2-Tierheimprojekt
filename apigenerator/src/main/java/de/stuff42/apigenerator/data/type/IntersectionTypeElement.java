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

import java.util.LinkedList;
import java.util.List;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Intersection type element.
 */
public class IntersectionTypeElement extends TypeDataElement<IntersectionType> {

    /**
     * Intersection type bounds.
     */
    private List<TypeDataElement<?>> bounds;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public IntersectionTypeElement(IntersectionType element, RestControllerProcessor processor) {
        super(element, processor);
    }

    /**
     * Checks if the given type mirror is an intersection type.
     *
     * @param typeMirror Type mirror element.
     *
     * @return If the given mirror element is an intersection type.
     */
    public static boolean isIntersectionType(TypeMirror typeMirror) {
        return typeMirror.getKind() == TypeKind.INTERSECTION;
    }

    @Override
    public String getTypescriptName() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (TypeDataElement<?> typeData : bounds) {
            if (first) {
                first = false;
            } else {
                sb.append(" & ");
            }
            sb.append(typeData.getTypescriptName());
        }
        return sb.toString();
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // nothing to do here
    }

    @Override
    public void processElement() {

        // grab bounds
        bounds = new LinkedList<>();
        for (TypeMirror elementType : element.getBounds()) {
            bounds.add(processor.processDataType(elementType));
        }
    }
}
