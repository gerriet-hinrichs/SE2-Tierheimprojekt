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

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import de.stuff42.apigenerator.Utilities;
import de.stuff42.apigenerator.data.DataElement;
import de.stuff42.apigenerator.data.type.TypeDataElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;
import de.stuff42.utils.data.Lazy;

/**
 * Method data element.
 */
public class Method extends DataElement<ExecutableElement> {

    /**
     * Method return type.
     */
    private Lazy<TypeDataElement<?>> returnType;

    /**
     * Method parameters.
     */
    private Lazy<List<Parameter>> parameters;

    /**
     * Belonging controller instance.
     */
    private Controller controller;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element    Mirror element.
     * @param processor  Processor instance.
     * @param controller Controller instance.
     */
    Method(ExecutableElement element, RestControllerProcessor processor, Controller controller) {
        super(element, processor);
        this.controller = controller;
        returnType = new Lazy<>(() -> processor.processDataType(element.getReturnType()));
        parameters = new Lazy<>(() -> {
            List<? extends VariableElement> parameterElements = element.getParameters();
            List<Parameter> parameterList = new ArrayList<>(parameterElements.size());

            for (VariableElement parameterElement : parameterElements) {
                Parameter parameter = new Parameter(parameterElement, processor, this);
                parameterList.add(parameter);
            }
            return parameterList;
        });
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // inner indentation info
        int innerLevel = level + 1;
        String innerIndentation = Utilities.getIndentationString(innerLevel);

        // method header
        sb.append(indentation).append("public static ").append(element.getSimpleName()).append("(");
        boolean first = true;
        for (Parameter parameter : parameters.value()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            parameter.generateTypescript(sb, 0, "");
        }

        // TODO @gerriet Remove additional null
        sb.append("): (").append(processor.getTypeAlias(getAliasBaseName() + "$return", returnType.value())).append(") | null {\n");

        // method body

        // TODO @gerriet Add ajax call
        sb.append(innerIndentation).append("return null;\n");

        // method end
        sb.append(indentation).append("}\n");
    }

    @Override
    public TypeMirror getTypeMirror() {
        return element.asType();
    }

    /**
     * Returns the alias base name for this controller.
     *
     * @return Alias base name.
     */
    String getAliasBaseName() {
        return controller.getAliasBaseName() + '$' + element.getSimpleName().toString();
    }
}
