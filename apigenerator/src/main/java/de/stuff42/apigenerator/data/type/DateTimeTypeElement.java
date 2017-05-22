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

import java.time.OffsetDateTime;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import de.stuff42.apigenerator.processor.RestControllerProcessor;

/**
 * Date time type element.
 */
public class DateTimeTypeElement extends TypeDataElement<TypeMirror> {

    /**
     * Offset date time mirror.
     */
    private static TypeMirror offsetDateTimeMirror;

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    public DateTimeTypeElement(TypeMirror element, RestControllerProcessor processor) {
        super(element, processor);
    }

    @Override
    public String getTypescriptName() {
        return "Date";
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {

        // nothing to do here
    }

    @Override
    public void processElement() {

        // nothing to do here
    }

    /**
     * Checks if the given type mirror is an OffsetDateTime type.
     *
     * @param typeMirror            Type mirror element.
     * @param processingEnvironment Processing environment.
     *
     * @return If the given mirror element is an OffsetDateTime type.
     */
    public static boolean isDate(TypeMirror typeMirror, ProcessingEnvironment processingEnvironment) {
        Types typeUtils = processingEnvironment.getTypeUtils();

        // load OffsetDateTime type mirror if required
        if (offsetDateTimeMirror == null) {
            Elements elementUtils = processingEnvironment.getElementUtils();
            offsetDateTimeMirror = elementUtils.getTypeElement(OffsetDateTime.class.getName()).asType();
        }

        // check if assignment is possible
        return typeUtils.isAssignable(typeMirror, offsetDateTimeMirror);
    }
}
