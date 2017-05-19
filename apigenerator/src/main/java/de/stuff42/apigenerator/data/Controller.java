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
package de.stuff42.apigenerator.data;

import java.util.LinkedList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.springframework.web.bind.annotation.RequestMapping;

import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Controller data element class.
 */
public class Controller extends DataElement<TypeElement> {

    /**
     * Controller methods.
     */
    private List<Method> methods;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public Controller(TypeElement element, RestControllerProcessor processor) {
        super(element, processor);
    }

    /**
     * Returns the controller name.
     *
     * @return Controller name.
     */
    public String getName() {
        String fullJavaName = element.asType().toString();
        return fullJavaName.replaceAll(".*\\.(.*?)(Controller)?$", "$1");
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {
        sb.append("export class ").append(getName()).append(" {\n");
        for (Method method : methods) {
            method.generateTypescript(sb, level + 1);
        }
        sb.append("}\n");
    }

    @Override
    protected void processElement() {
        methods = new LinkedList<>();
        for (Element member : element.getEnclosedElements()) {

            // only process public methods that have a request mapping
            if (member instanceof ExecutableElement && member.getAnnotation(RequestMapping.class) != null) {
                this.methods.add(new Method((ExecutableElement) member, processor));
            }
        }
    }
}
