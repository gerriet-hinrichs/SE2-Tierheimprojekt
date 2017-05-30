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

import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Primitive type data.
 */
public class PrimitiveType extends TypeDataElement<TypeMirror> {

    /**
     * Mapping for java to primitive typescript type.
     */
    private static Map<String, PrimitiveTypeInfo> primitiveTypes = new HashMap<>();

    // initialize primitive type mapping
    static {

        // integers
        primitiveTypes.put("byte", new PrimitiveTypeInfo("number", false));
        primitiveTypes.put("java.lang.Byte", new PrimitiveTypeInfo("number", true));

        primitiveTypes.put("short", new PrimitiveTypeInfo("number", false));
        primitiveTypes.put("java.lang.Short", new PrimitiveTypeInfo("number", true));

        primitiveTypes.put("int", new PrimitiveTypeInfo("number", false));
        primitiveTypes.put("java.lang.Integer", new PrimitiveTypeInfo("number", true));

        primitiveTypes.put("long", new PrimitiveTypeInfo("number", false));
        primitiveTypes.put("java.lang.Long", new PrimitiveTypeInfo("number", true));

        // floating point
        primitiveTypes.put("float", new PrimitiveTypeInfo("number", false));
        primitiveTypes.put("java.lang.Float", new PrimitiveTypeInfo("number", true));

        primitiveTypes.put("double", new PrimitiveTypeInfo("number", false));
        primitiveTypes.put("java.lang.Double", new PrimitiveTypeInfo("number", true));

        // number
        primitiveTypes.put("java.lang.Number", new PrimitiveTypeInfo("number", true));

        // string
        primitiveTypes.put("char", new PrimitiveTypeInfo("string", false));
        primitiveTypes.put("java.lang.Char", new PrimitiveTypeInfo("string", true));

        primitiveTypes.put("java.lang.String", new PrimitiveTypeInfo("string", true));

        // object
        primitiveTypes.put("java.lang.Object", new PrimitiveTypeInfo("{}", true));
    }

    /**
     * Typescript name for the primitive type.
     */
    private Lazy<String> name;

    /**
     * If <code>null</code> is supported.
     */
    private Lazy<Boolean> typeSupportsNull;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public PrimitiveType(TypeMirror element, RestControllerProcessor processor) {
        super(element, processor);
        name = new Lazy<>(() -> {
            PrimitiveTypeInfo typeInfo = primitiveTypes.get(Utilities.getTypeName(element));
            if (typeInfo == null) {
                return "any";
            }
            return typeInfo.tyescriptName;
        });
        typeSupportsNull = new Lazy<>(() -> {
            PrimitiveTypeInfo typeInfo = primitiveTypes.get(Utilities.getTypeName(element));
            return typeInfo == null || typeInfo.supportsNull;
        });
    }

    /**
     * Checks if the given type mirror is a primitive type.
     *
     * @param typeMirror Type mirror element.
     *
     * @return If the given mirror element is a primitive type.
     */
    public static boolean isPrimitive(TypeMirror typeMirror) {
        return primitiveTypes.containsKey(Utilities.getTypeName(typeMirror));
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
        return true;
    }

    @Override
    public boolean supportsNull() {
        return typeSupportsNull.value();
    }

    /**
     * Helper container class for type info.
     */
    private static class PrimitiveTypeInfo {

        /**
         * Typescript name.
         */
        private final String tyescriptName;

        /**
         * If <code>null</code> is supported.
         */
        private final boolean supportsNull;

        /**
         * Creates type info instance.
         *
         * @param typescriptName Typescript name.
         * @param supportsNull   If <code>null</code> is supported.
         */
        private PrimitiveTypeInfo(String typescriptName, boolean supportsNull) {
            this.tyescriptName = typescriptName;
            this.supportsNull = supportsNull;
        }
    }
}
