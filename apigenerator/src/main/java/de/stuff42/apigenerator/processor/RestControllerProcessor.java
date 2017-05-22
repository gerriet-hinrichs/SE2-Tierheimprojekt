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
package de.stuff42.apigenerator.processor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import de.stuff42.apigenerator.Config;
import de.stuff42.apigenerator.annotation.GenerateClientApi;
import de.stuff42.apigenerator.data.DataElement;
import de.stuff42.apigenerator.data.controller.Controller;
import de.stuff42.apigenerator.data.type.*;
import de.stuff42.apigenerator.data.type.object.ObjectTypeElement;
import de.stuff42.apigenerator.data.type.object.TypeParameter;
import de.stuff42.utils.PathUtils;
import de.stuff42.utils.UtilsConfig;
import de.stuff42.utils.exception.ExceptionPrinter;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RestController;

/**
 * Processor that generates client API for controller classes.
 */
@SupportedAnnotationTypes("de.stuff42.apigenerator.annotation.GenerateClientApi")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RestControllerProcessor extends AbstractProcessor {

    /**
     * Directory to generate typescript sources to.
     */
    private final static File OUTPUT_DIRECTORY = new File(PathUtils.getApplicationRoot().toFile(), Config.OUTPUT_PATH);

    /**
     * Annotation to be processed by this class.
     */
    private final static Class<? extends Annotation> ANNOTATION = GenerateClientApi.class;

    // setup before processing start
    static {
        UtilsConfig.setRelevantPackages("de.stuff42.apigenerator");
    }

    /**
     * Already exported types.
     */
    private Map<String, TypeDataElement<?>> exportedTypes = new HashMap<>();

    /**
     * Called before the object processing starts.
     *
     * @param environment Processing environment.
     *
     * @return If object should be processed within the current step. If <code>false</code> is returned {@link
     * #processObject(RoundEnvironment, Element)} will not be called and the current processing step continues with
     */
    private boolean startProcessingStep(@NotNull RoundEnvironment environment) {
        try {

            // clear output directory
            FileUtils.deleteDirectory(OUTPUT_DIRECTORY);
            if (OUTPUT_DIRECTORY.mkdirs()) {
                message(Kind.NOTE, "Created parent directories for output.");
            }
            if (OUTPUT_DIRECTORY.mkdir()) {
                message(Kind.NOTE, "Created directory for output.");
            }
        } catch (IOException e) {
            message(Kind.ERROR, "Failed to clean output directory: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Processes a found object.
     *
     * @param environment Processing environment.
     * @param element     Object data information.
     */
    private void processObject(@NotNull RoundEnvironment environment, @NotNull Element element) {
        try {
            if (element.getKind().isClass() && element instanceof TypeElement && element.getAnnotation(RestController.class) != null) {

                // process object
                Controller controller = new Controller((TypeElement) element, this);
                controller.processElement();
                generateApiElement(controller);
            } else {
                message(Kind.WARNING, "Skipping " + element.asType().toString());
            }
        } catch (Throwable t) {
            message(Kind.ERROR, "Failed to process '" + element.asType().toString() + "': " + t.getMessage()
                    + System.lineSeparator() + ExceptionPrinter.printThrowable(t));
        }
    }

    /**
     * Performs an annotation processing step that processes the Controller annotation.
     *
     * @param annotations Annotations to be processed within this step.
     * @param environment Processing environment.
     *
     * @return <code>true</code> if the Controller annotation was processed, <code>false</code> otherwise.
     */
    @Override
    public boolean process(@NotNull Set<? extends TypeElement> annotations, @NotNull RoundEnvironment environment) {

        // do not do anything if there was an error
        if (environment.errorRaised()) {

            // skip further processing
            return true;
        }

        // only do stuff if we have at least one element
        Set<? extends Element> elements = environment.getElementsAnnotatedWith(ANNOTATION);
        if (!elements.isEmpty()) {

            // gradle does not end with line break so we print one here
            System.err.println();

            // call step start method and determine if we have to process objects
            if (this.startProcessingStep(environment)) {

                // stop processing if we have errors
                if (environment.errorRaised()) {

                    // skip further processing
                    return true;
                }

                // process each element
                for (Element element : elements) {
                    this.processObject(environment, element);
                }
            }

            // stop processing if we have errors
            if (environment.errorRaised()) {
                // skip further processing
                return true;
            }

            // call step finalized method
            return true;
        }

        // no elements found: we did everything
        return true;
    }

    /**
     * Prints the given message.
     *
     * @param kind    Message type.
     * @param message Message text.
     */
    private void message(@NotNull Diagnostic.Kind kind, @NotNull String message) {
        processingEnv.getMessager().printMessage(kind, message);
    }

    /**
     * Hook to initiate data type processing.
     *
     * @param element Type element to be processed as data type.
     *
     * @return Created type data element instance.
     */
    public TypeDataElement<?> processDataType(TypeMirror element) {

        // try to find already exported element
        String key = element.toString();
        if (exportedTypes.containsKey(key)) {
            return exportedTypes.get(key);
        }

        TypeDataElement<?> result;

        // primitive type?
        if (PrimitiveType.isPrimitive(element)) {
            result = new PrimitiveType(element, this);
        }

        // offset date time?
        else if (DateTimeTypeElement.isDate(element, processingEnv)) {
            result = new DateTimeTypeElement(element, this);
        }

        // map?
        else if (MapTypeElement.isMap(element, processingEnv)) {
            result = new MapTypeElement(element, this);
        }

        // iterable type or array?
        else if (IterableTypeElement.isIterable(element, processingEnv)) {
            result = new IterableTypeElement(element, this);
        }

        // enum?
        else if (EnumTypeElement.isEnum(element, processingEnv)) {
            result = new EnumTypeElement((TypeElement) processingEnv.getTypeUtils().asElement(element), this);
        }

        // object?
        else if (ObjectTypeElement.isObject(element, processingEnv)) {
            result = new ObjectTypeElement((TypeElement) processingEnv.getTypeUtils().asElement(element), this);
        }

        // intersection?
        else if (IntersectionTypeElement.isIntersectionType(element)) {
            result = new IntersectionTypeElement((IntersectionType) element, this);
        }

        // wildcard?
        else if (WildCardTypeElement.isWildCard(element)) {
            result = new WildCardTypeElement(element, this);
        }

        // type parameter?
        else if (TypeParameter.isTypeParameter(element, processingEnv)) {
            result = new TypeParameter((TypeVariable) element, this);
        }

        // not supported...
        else {
            message(Kind.WARNING, "Unsupported type: " + element.toString());
            result = new UnsupportedTypeElement(element, this);
        }

        // add exported type to map
        exportedTypes.put(key, result);

        // start actual element processing
        result.processElement();

        // export
        generateApiElement(result);

        // return finished type
        return result;
    }

    /**
     * Writes contents to file.
     *
     * @param fileName Output file name  relative to {@link #OUTPUT_DIRECTORY}.
     * @param content  Content to be written to file.
     */
    private void generateApiFile(String fileName, String content) {
        message(Kind.NOTE, "Writing " + fileName);
        File outputFile = new File(OUTPUT_DIRECTORY, fileName);
        if (outputFile.getParentFile().exists() || outputFile.getParentFile().mkdirs()) {
            try {
                FileUtils.writeStringToFile(outputFile, content, Charset.forName("UTF-8"));
            } catch (IOException e) {
                message(Kind.ERROR, "Failed to write to file '" + fileName + "': " + e.getMessage());
            }
        } else {
            message(Kind.ERROR, "Failed to create missing parent directories for output.");
        }
    }

    /**
     * Generates output for the given data element.
     *
     * @param dataElement Data element.
     */
    private void generateApiElement(DataElement<?> dataElement) {

        // check if there is an export file name
        String exportFileName = dataElement.getExportFileName();
        if (exportFileName != null) {

            // generate typescript code
            StringBuilder sb = new StringBuilder();
            dataElement.generateTypescript(sb);

            // write to file
            generateApiFile(exportFileName, sb.toString());
        }
    }
}