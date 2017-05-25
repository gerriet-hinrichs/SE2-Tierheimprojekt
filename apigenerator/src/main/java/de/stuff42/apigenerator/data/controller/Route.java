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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.stuff42.apigenerator.data.DataElement;
import de.stuff42.apigenerator.processor.RestControllerProcessor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Route data element.
 */
public class Route extends DataElement<RequestMapping> {

    /**
     * Registered path variables.
     */
    private Map<String, String> pathVariables = new HashMap<>();

    /**
     * Registered query parameters.
     */
    private Map<String, String> queryParameters = new HashMap<>();

    /**
     * Creates new data class instance from the given element.
     *
     * @param element   Mirror element.
     * @param processor Processor instance.
     */
    Route(RequestMapping element, RestControllerProcessor processor) {
        super(element, processor);
    }

    @Override
    public void generateTypescript(StringBuilder sb, int level, String indentation) {
        sb.append("`");

        // process route with regex magic replace
        Pattern pattern = Pattern.compile("\\{(.*?)(:.*?)?}");
        Matcher matcher = pattern.matcher(getRawRoute());
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String variableName = matcher.toMatchResult().group(1);
            String replacement;
            if (pathVariables.containsKey(variableName)) {
                replacement = "\\${encodeURI(\"\" + " + pathVariables.get(variableName) + ")}";
            } else {
                replacement = "null";
            }
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);
        sb.append(result);

        // add query parameters
        if (!queryParameters.isEmpty()) {
            sb.append('?');
            for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                sb.append(entry.getKey()).append('=');
                sb.append("${encodeURIComponent(\"\" + ").append(entry.getValue()).append(")}");
            }
        }

        sb.append("`");
    }

    /**
     * Adds a path variable reference to this route data element.
     *
     * @param pathVariableName       Path variable name.
     * @param typescriptVariableName Typescript variable name.
     */
    void addPathVariable(String pathVariableName, String typescriptVariableName) {
        pathVariables.put(pathVariableName, typescriptVariableName);
    }

    /**
     * Adds a query parameter to this route data element.
     *
     * @param queryParameterName     Query parameter name.
     * @param typescriptVariableName Typescript variable name.
     */
    void addQueryParameter(String queryParameterName, String typescriptVariableName) {
        queryParameters.put(queryParameterName, typescriptVariableName);
    }

    /**
     * Returns the HTTP method for this route.
     *
     * @return HTTP method string.
     */
    public String getMethod() {
        RequestMethod[] method = element.method();
        if (method.length == 0) {

            // default to GET
            return RequestMethod.GET.name();
        }

        // use the first one otherwise
        return method[0].name();
    }

    /**
     * Returns the raw route string from annotation for this data element.
     *
     * @return Raw route string.
     */
    private String getRawRoute() {
        String[] routes = element.value();
        if (routes.length == 0) {

            // default to ""
            return "";
        }

        // use the first one otherwise
        return routes[0];
    }
}
