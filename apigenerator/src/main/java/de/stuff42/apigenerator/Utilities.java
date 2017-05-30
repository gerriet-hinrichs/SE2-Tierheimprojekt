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

import javax.lang.model.type.TypeMirror;

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
                    sb.append(typeParameter.getNullAwareTypescriptName());
                }
            }
            sb.append('>');
        }
        return sb.toString();
    }

    /**
     * Checks if surrounding parenthesis are required.
     * <p>
     * This method only works on valid typescript type strings.
     *
     * @param string              Input string.
     * @param nextCombineOperator Next type combine operator the result is used for.
     *
     * @return Input string with added surrounding parenthesis if necessary.
     */
    public static String addRequiredSurroundingParenthesis(String string, char nextCombineOperator) {

        // helper variables to detect parenthesis behavior
        boolean parenthesisRequired = false;
        int openParenthesisCount = 0;
        char foundTopLevelCombineOperator = 0;

        // iterate over the string
        char[] stringCharacters = string.toCharArray();
        char character;
        for (char stringCharacter : stringCharacters) {
            character = stringCharacter;
            if (character == '(') {

                // count open parenthesis
                openParenthesisCount++;
            } else if (character == ')') {

                // count closing parenthesis
                openParenthesisCount--;
            } else if (character == '&' || character == '|') {

                // only look in top level context for combine operators
                if (openParenthesisCount == 0) {

                    if (foundTopLevelCombineOperator == 0) {
                        foundTopLevelCombineOperator = character;
                    }

                    // if we have mixed operators or the found context is different from the next one
                    if (foundTopLevelCombineOperator != character || foundTopLevelCombineOperator != nextCombineOperator) {

                        // we need to add parenthesis
                        parenthesisRequired = true;
                        break;
                    }
                }
            }
        }

        // add parenthesis if required
        if (parenthesisRequired) {
            return "(" + string + ")";
        }
        return string;
    }

    /**
     * Extracts the actual type name from the given type mirror.
     *
     * @param mirror Type mirror.
     *
     * @return Actual type name.
     */
    public static String getTypeName(TypeMirror mirror) {

        // the result of toString() might contain annotation information so we need to strip them
        return mirror.toString().replaceAll("^(\\(.*?:: )?(.*?)\\)?$", "$2");
    }
}
