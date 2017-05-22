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
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Map type data element.
 */
public class MapTypeElement extends TypeDataElement<TypeMirror> {

    /**
     * Map type mirror.
     */
    private static DeclaredType mapType;

    /**
     * Key type data.
     */
    private Lazy<TypeDataElement<?>> keyType;

    /**
     * Element type data.
     */
    private Lazy<TypeDataElement<?>> valueType;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public MapTypeElement(TypeMirror element, RestControllerProcessor processor) {
        super(element, processor);

        // we know that we have exactly two argument
        keyType = new Lazy<>(() -> {
            List<? extends TypeMirror> typeArguments = ((DeclaredType) element).getTypeArguments();
            return processor.processDataType(typeArguments.get(0));
        });
        valueType = new Lazy<>(() -> {
            List<? extends TypeMirror> typeArguments = ((DeclaredType) element).getTypeArguments();
            return processor.processDataType(typeArguments.get(1));
        });
    }

    /**
     * Checks if the given type mirror is a map type.
     *
     * @param typeMirror            Type mirror element.
     * @param processingEnvironment Processing environment.
     *
     * @return If the given mirror element is a map type.
     */

    public static boolean isMap(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        Types typeUtils = processingEnvironment.getTypeUtils();

        // load iterable type mirror if required
        if (mapType == null) {

            // create generic iterable type mirror with a single type parameter argument
            Elements elementUtils = processingEnvironment.getElementUtils();
            WildcardType WILDCARD_TYPE_NULL = typeUtils.getWildcardType(null, null);
            TypeElement iterableTypeElement = elementUtils.getTypeElement(Map.class.getName());
            mapType = typeUtils.getDeclaredType(iterableTypeElement, WILDCARD_TYPE_NULL, WILDCARD_TYPE_NULL);
        }

        // check for map
        if (typeUtils.isAssignable(typeMirror, mapType)) {

            // ensure we do not have a raw type
            return ((DeclaredType) typeMirror).getTypeArguments().size() == 2;
        }

        // no matching type
        return false;
    }

    @Override
    public String getTypescriptName() {
        return "{ [key: " + keyType.value().getTypescriptName() + "]: " + valueType.value().getTypescriptName() + " }";
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // nothing to do here
    }
}
