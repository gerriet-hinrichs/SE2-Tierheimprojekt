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
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;

import de.stuff42.apigenerator.Config;
import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.data.type.TypeDataElement;
import de.stuff42.apigenerator.data.type.UnsupportedTypeElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Object or interface type element.
 */
public class ObjectTypeElement extends TypeDataElement<TypeElement> {

    /**
     * Object or interface name.
     */
    private Lazy<String> name;

    /**
     * Object or interface fields.
     */
    private Lazy<List<FieldTypeElement>> fields;

    /**
     * Parent or interface type.
     */
    private Lazy<TypeDataElement<?>> parent;

    /**
     * Type or interface parameters.
     */
    private Lazy<List<TypeDataElement<?>>> typeParameters;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public ObjectTypeElement(TypeElement element, RestControllerProcessor processor) {
        super(element, processor);

        name = new Lazy<>(() -> element.getSimpleName().toString());
        parent = new Lazy<>(() -> processor.processDataType(element.getSuperclass()));
        fields = new Lazy<>(() -> {
            List<FieldTypeElement> fieldList = new LinkedList<>();
            for (Element member : element.getEnclosedElements()) {
                if (member.getKind().isField() && member.getModifiers().contains(Modifier.PUBLIC)) {
                    FieldTypeElement fieldTypeElement = new FieldTypeElement((VariableElement) member, processor);
                    fieldList.add(fieldTypeElement);
                }
            }
            return fieldList;
        });
        typeParameters = new Lazy<>(() -> {
            List<TypeDataElement<?>> typeParameterList = new LinkedList<>();
            for (TypeParameterElement parameterElement : element.getTypeParameters()) {
                typeParameterList.add(processor.processDataType(parameterElement.asType()));
            }
            return typeParameterList;
        });
    }

    @Override
    public String getRawTypescriptName() {

        // give any if we are not a class
        if (element.getKind() == ElementKind.CLASS) {
            return "Api." + name.value();
        }
        return "any";
    }

    @Override
    public String getTypescriptName() {
        if (element.getKind() == ElementKind.CLASS) {
            return getRawTypescriptName() +
                    Utilities.generateGenericArguments(typeParameters.value(), false);
        }
        return "any";
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // inner indentation
        String innerIndentation1 = Utilities.getIndentationString(level + 1);
        String innerIndentation2 = Utilities.getIndentationString(level + 2);

        // namespace start
        sb.append(indentation).append("declare namespace Api {\n");

        // object export (as typescript interface)
        sb.append(innerIndentation1).append("export interface ").append(name.value());
        sb.append(Utilities.generateGenericArguments(typeParameters.value(), true));

        // special parent handling (do not have "{}" or "any" as parent)
        TypeDataElement<?> parentElement = parent.value();
        if (!(parentElement.ignoreWithinBondsAndInheritance() || parentElement instanceof UnsupportedTypeElement)) {
            sb.append(" extends ").append(parent.value().getTypescriptName());
        }
        sb.append(" {\n");

        for (FieldTypeElement field : fields.value()) {
            field.generateTypescript(sb, level + 2, innerIndentation2);
        }
        sb.append(innerIndentation1).append("}\n");

        // namespace end
        sb.append(indentation).append("}\n");
    }

    @Override
    public String getExportFileName() {

        // only export if we have a class
        if (element.getKind() == ElementKind.CLASS) {
            return Config.DATA_PATH + "/" + name.value() + ".d.ts";
        }
        return null;
    }

    @Override
    public TypeMirror getTypeMirror() {
        return element.asType();
    }

    @Override
    public boolean ignoreWithinBondsAndInheritance() {

        // we only care about classes
        return element.getKind() != ElementKind.CLASS;
    }

    @Override
    public boolean supportsNull() {
        return true;
    }
}
