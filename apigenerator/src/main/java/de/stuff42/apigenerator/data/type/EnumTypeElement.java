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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import de.stuff42.apigenerator.Config;
import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Enum type element.
 */
public class EnumTypeElement extends TypeDataElement<TypeElement> {

    /**
     * List with all enum elements.
     */
    private List<String> elements;

    /**
     * Enum name.
     */
    private String name;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public EnumTypeElement(TypeElement element, RestControllerProcessor processor) {
        super(element, processor);
    }

    @Override
    public String getTypescriptName() {
        return "Api." + name;
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // inner indentation
        String innerIndentation1 = Utilities.getIndentationString(level + 1);
        String innerIndentation2 = Utilities.getIndentationString(level + 2);

        // namespace start
        sb.append(indentation).append("declare namespace Api {\n");

        // enum export
        sb.append(innerIndentation1).append("export type ").append(name).append(" = ");
        boolean first = true;
        for (String value : elements) {
            if (first) {
                first = false;
            } else {
                sb.append("\n").append(innerIndentation2).append("| ");
            }
            sb.append('"').append(value).append('"');
        }
        sb.append(";\n");

        // namespace end
        sb.append(indentation).append("}\n");
    }

    @Override
    public void processElement() {

        // enum name
        name = element.getSimpleName().toString();

        // enum values
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        elements = new ArrayList<>(enclosedElements.size());
        for (Element element : enclosedElements) {
            if (element.getKind() == ElementKind.ENUM_CONSTANT) {
                elements.add(element.getSimpleName().toString());
            }
        }
    }

    /**
     * Checks if the given type mirror is an enum type.
     *
     * @param typeMirror            Type mirror element.
     * @param processingEnvironment Processing environment.
     *
     * @return If the given mirror element is an enum type.
     */
    public static boolean isEnum(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        Types typeUtils = processingEnvironment.getTypeUtils();
        Element element = typeUtils.asElement(typeMirror);
        return element.getKind() == ElementKind.ENUM;
    }

    @Override
    public String getExportFileName() {
        return Config.DATA_PATH + "/" + name + ".d.ts";
    }
}
