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
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import de.stuff42.apigenerator.Config;
import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.data.type.TypeDataElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Object type element.
 */
public class ObjectTypeElement extends TypeDataElement<TypeElement> {

    /**
     * Object name.
     */
    private Lazy<String> name;

    /**
     * Object fields.
     */
    private Lazy<List<FieldTypeElement>> fields;

    /**
     * Parent type.
     */
    private Lazy<TypeDataElement<?>> parent;

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

    /**
     * Checks if the given type mirror is an object type.
     *
     * @param typeMirror            Type mirror element.
     * @param processingEnvironment Processing environment.
     *
     * @return If the given mirror element is an object type.
     */
    public static boolean isObject(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        Types typeUtils = processingEnvironment.getTypeUtils();
        Element element = typeUtils.asElement(typeMirror);

        return element.getKind() == ElementKind.CLASS;
    }

    @Override
    public String getTypescriptName() {
        StringBuilder sb = new StringBuilder();
        sb.append("Api.").append(name.value());

        // TODO @gerriet Gernerics
        for (int i = 0; i < typeParameters.value().size(); i++) {
            if (i == 0) {
                sb.append('<');
            } else {
                sb.append(", ");
            }
            sb.append("any");
            if (i == typeParameters.value().size() - 1) {
                sb.append('>');
            }
        }

        return sb.toString();
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

        if (!typeParameters.value().isEmpty()) {
            sb.append('<');
            boolean first = true;
            for (TypeDataElement<?> typeParameter : typeParameters.value()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                typeParameter.generateTypescript(sb, 0, "");
            }
            sb.append('>');
        }

        // special parent handling (do not have {} as parent)
        if (!parent.value().isRootObjectType()) {
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
        return Config.DATA_PATH + "/" + name.value() + ".d.ts";
    }
}
