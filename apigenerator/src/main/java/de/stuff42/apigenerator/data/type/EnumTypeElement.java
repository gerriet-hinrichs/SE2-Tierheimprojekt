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
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import de.stuff42.apigenerator.Config;
import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Enum type element.
 */
public class EnumTypeElement extends TypeDataElement<TypeElement> {

    /**
     * List with all enum elements.
     */
    private Lazy<List<String>> elements;

    /**
     * Enum name.
     */
    private Lazy<String> name;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public EnumTypeElement(TypeElement element, RestControllerProcessor processor) {
        super(element, processor);
        name = new Lazy<>(() -> element.getSimpleName().toString());
        elements = new Lazy<>(() -> {
            List<? extends Element> enclosedElements = element.getEnclosedElements();
            List<String> valueList = new ArrayList<>(enclosedElements.size());
            for (Element enclosedElement : enclosedElements) {
                if (enclosedElement.getKind() == ElementKind.ENUM_CONSTANT) {
                    valueList.add(enclosedElement.getSimpleName().toString());
                }
            }
            return valueList;
        });
    }

    @Override
    public String getTypescriptName() {
        return "Api." + name.value();
    }

    @Override
    public boolean supportsNull() {
        return false;
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // inner indentation
        String innerIndentation1 = Utilities.getIndentationString(level + 1);
        String innerIndentation2 = Utilities.getIndentationString(level + 2);

        // namespace start
        sb.append(indentation).append("declare namespace Api {\n");

        // enum export
        sb.append(innerIndentation1).append("export type ").append(name.value()).append(" = ");
        boolean first = true;
        for (String value : elements.value()) {
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
    public String getExportFileName() {
        return Config.DATA_PATH + "/" + name.value() + ".d.ts";
    }

    @Override
    public TypeMirror getTypeMirror() {
        return element.asType();
    }
}
