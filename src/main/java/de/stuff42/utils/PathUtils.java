/*
 * Utilities for use in various applications.
 * Copyright (C) 2017
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
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
package de.stuff42.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.stuff42.utils.data.Lazy;

/**
 * Path related utility functions.
 */
public final class PathUtils {

    /**
     * Lazy initialized application root.
     */
    private static Lazy<Path> applicationRoot = new Lazy<>(() -> {

        // do a first guess on the application path
        Path path;
        try {

            // try to get path from jar file
            path = new File(PathUtils.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath()).toPath().getParent().toAbsolutePath();
        } catch (URISyntaxException e) {

            // use the current directory otherwise
            path = Paths.get("").toAbsolutePath();
        }

        // adjust path (if required)
        if (path.endsWith("lib")) {

            // we're in environment from application distribution plugin
            // so application root is the parent directory
            path = path.getParent();
        }
        else if(path.getParent().endsWith("build")) {

            // we're in gradle environment within temporary build directory
            if (path.endsWith("libs")) {

                // this class is packed into a dependency jar
                // so the application root is three levels above
                path = path.getParent().getParent().getParent();
            } else if (path.endsWith("classes")) {

                // this class is not jet bundled into a jar
                // so the application root is two levels above
                path = path.getParent().getParent();
            }
        }

        // return detected path
        return path;
    });

    /**
     * Hide default constructor.
     */
    private PathUtils() {
        // hide default constructor
    }

    /**
     * Returns the application root path.
     *
     * @return Application root path.
     */
    public static Path getApplicationRoot() {
        return applicationRoot.value();
    }
}
