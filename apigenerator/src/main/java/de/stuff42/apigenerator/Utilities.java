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
package de.stuff42.apigenerator;

import java.util.List;

import de.stuff42.apigenerator.data.type.TypeDataElement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Class for generator utilities.
 */
public class Utilities {

    /**
     * Indentation base string.
     */
    private static final String INDENTATION_BASE = "    ";

    /**
     * Calculates an indentation string for the given level.
     *
     * @param level Indentation level.
     *
     * @return Indentation string.
     */
    @NotNull
    @Contract(pure = true)
    public static String getIndentationString(int level) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < level; i++) {
            result.append(INDENTATION_BASE);
        }
        return result.toString();
    }

    /**
     * Generates a code snipped for generic arguments.
     *
     * @param arguments List with generic arguments.
     * @param withBonds If bonds should be generated.
     *
     * @return Generated snippet.
     */
    @NotNull
    public static String generateGenericArguments(List<TypeDataElement<?>> arguments, boolean withBonds) {
        StringBuilder sb = new StringBuilder();
        if (!arguments.isEmpty()) {
            sb.append('<');
            boolean first = true;
            for (TypeDataElement<?> typeParameter : arguments) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                if (withBonds) {
                    typeParameter.generateTypescript(sb, 0, "");
                } else {
                    sb.append(typeParameter.getTypescriptName());
                }
            }
            sb.append('>');
        }
        return sb.toString();
    }
}
