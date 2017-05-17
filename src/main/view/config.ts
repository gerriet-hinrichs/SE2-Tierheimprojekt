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

// configure module loading
requirejs.config({

    // base uri for script files20
    baseUrl: "static/view/",
    paths: {

        // for libraries we explicitly use CDNs and have to list them here
        // (we only choose the minified version if we are not on debug)
        "jquery": "//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min",
        "knockout": "//cdnjs.cloudflare.com/ajax/libs/knockout/3.4.0/knockout-min",
        "knockout.punches": "//mbest.github.io/knockout.punches/knockout.punches.min",
        "knockout-amd-helpers": "//raw.githubusercontent.com/rniemeyer/knockout-amd-helpers/v1.0.0/build/knockout-amd-helpers.min"
    }
});

// load app to start client side application
requirejs(["App"]);