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

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.type.TypeMirror;

import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Primitive type data.
 */
public class PrimitiveType extends TypeDataElement<TypeMirror> {

    /**
     * Mapping for java to primitive typescript type.
     */
    private static Map<String, String> primitiveTypes = new HashMap<>();

    // initialize primitive type mapping
    static {

        // integers
        primitiveTypes.put("byte", "number");
        primitiveTypes.put("java.lang.Byte", "number");

        primitiveTypes.put("short", "number");
        primitiveTypes.put("java.lang.Short", "number");

        primitiveTypes.put("int", "number");
        primitiveTypes.put("java.lang.Integer", "number");

        primitiveTypes.put("long", "number");
        primitiveTypes.put("java.lang.Long", "number");

        // floating point
        primitiveTypes.put("float", "number");
        primitiveTypes.put("java.lang.Float", "number");

        primitiveTypes.put("double", "number");
        primitiveTypes.put("java.lang.Double", "number");

        // string
        primitiveTypes.put("char", "string");
        primitiveTypes.put("java.lang.Char", "string");

        primitiveTypes.put("java.lang.String", "string");

        // object
        primitiveTypes.put("java.lang.Object", "{}");
    }

    /**
     * Typescript name for the primitive type.
     */
    private Lazy<String> name;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public PrimitiveType(TypeMirror element, RestControllerProcessor processor) {
        super(element, processor);
        name = new Lazy<>(() -> primitiveTypes.getOrDefault(element.toString(), "any"));
    }

    /**
     * Checks if the given type mirror is a primitive type.
     *
     * @param typeMirror Type mirror element.
     *
     * @return If the given mirror element is a primitive type.
     */
    public static boolean isPrimitive(TypeMirror typeMirror) {
        return primitiveTypes.containsKey(typeMirror.toString());
    }

    @Override
    public String getTypescriptName() {
        return name.value();
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // primitive types do not need to be exported
    }

    @Override
    public TypeMirror getTypeMirror() {
        return element;
    }

    @Override
    public boolean ignoreWithinBondsAndInheritance() {
        return "java.lang.Object".equals(element.toString());
    }
}
