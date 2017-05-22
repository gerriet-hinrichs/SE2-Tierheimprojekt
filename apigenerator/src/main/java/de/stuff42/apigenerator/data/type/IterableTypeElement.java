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

import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Type data element for iterable types.
 */
public class IterableTypeElement extends TypeDataElement<TypeMirror> {

    /**
     * Element type.
     */
    private TypeDataElement<?> elementType;

    /**
     * Type mirror for the generic iterable type (with one type argument)
     */
    private static DeclaredType iterableType = null;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public IterableTypeElement(TypeMirror element, RestControllerProcessor processor) {
        super(element, processor);
    }

    @Override
    public String getTypescriptName() {
        return elementType.getTypescriptName() + "[]";
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // nothing to do here
    }

    @Override
    public void processElement() {
        if (element.getKind() == TypeKind.ARRAY) {

            // array: get component type
            elementType = processor.processDataType(((ArrayType) element).getComponentType());
        } else {

            // iterable (we know that we have exactly one argument)
            List<? extends TypeMirror> typeArguments = ((DeclaredType) element).getTypeArguments();
            elementType = processor.processDataType(typeArguments.get(0));
        }
    }

    /**
     * Checks if the given type mirror is an iterable or array type.
     *
     * @param typeMirror            Type mirror element.
     * @param processingEnvironment Processing environment.
     *
     * @return If the given mirror element is an iterable type.
     */

    public static boolean isIterable(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        Types typeUtils = processingEnvironment.getTypeUtils();

        // load iterable type mirror if required
        if (iterableType == null) {

            // create generic iterable type mirror with a single type parameter argument
            Elements elementUtils = processingEnvironment.getElementUtils();
            WildcardType WILDCARD_TYPE_NULL = typeUtils.getWildcardType(null, null);
            TypeElement iterableTypeElement = elementUtils.getTypeElement(Iterable.class.getName());
            iterableType = typeUtils.getDeclaredType(iterableTypeElement, WILDCARD_TYPE_NULL);
        }

        // check if we have an array
        if (typeMirror.getKind() == TypeKind.ARRAY) {
            return true;
        }

        // check for iterables
        if (typeUtils.isAssignable(typeMirror, iterableType)) {

            // ensure we do not have a raw type
            return ((DeclaredType) typeMirror).getTypeArguments().size() == 1;
        }

        // no matching type
        return false;
    }
}
