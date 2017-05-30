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

// side effect import to load jQuery
import "jquery";
import * as ko from "knockout";
import "knockout.punches";
import "knockout-amd-helpers";
import {NavigationItem} from "./components/Navigation";
import {QuestionData} from "./components/Question";

// app class
export class App {
    /**
     * Items for navigation and sidebar
     */
    public navigationItems = ko.observableArray<NavigationItem>();
    public questionItems = ko.observableArray<QuestionData>();

    /**
     * Current component name for intern pseudo routing via module binding.
     * Default 'Start' for selected component on startup
     */
    public currentComponent: KnockoutObservable<string>;

    /**
     * Sidebar to display
     */
    public IsSidebarVisible: KnockoutObservable<boolean>;

    /**
     * Margins for app-body to create moderate centered view
     */
    public viewMarginTop = ko.observable<string>("");
    public viewMarginBottom = ko.observable<string>("");
    // Left and right margin shall be always the same (horizontal centered)
    public viewMarginLeftRight = ko.observable<string>("");

    /**
     * Index for automatic slideshow
     */
    public index = 0;

    public constructor() {
        this.currentComponent = ko.observable<string>("Start");
        this.IsSidebarVisible = ko.observable<boolean>(false);

        this.setViewPort();
        this.prepareNavigationItems();
        this.prepareQuestionItems();

        // Automatic slide show icon
        //this.slideShow();

        $(function() {
            location.hash = '';
        });
    }

    public setViewPort() {
        this.viewMarginTop("20px");
        this.viewMarginBottom("15px");
        this.viewMarginLeftRight("20px");
    }

    public prepareNavigationItems() {
        this.navigationItems.push({
                Name: "Start",
                Title: "Start",
                IsSelected: ko.observable<boolean>(true)
            },
            {
                Name: "Fragen",
                Title: "Hier gehts zum Fragebogen!",
                IsSelected: ko.observable<boolean>(false)
            },
            {
                Name: "Impressum",
                Title: "Impressum",
                IsSelected: ko.observable<boolean>(false)
            },
            {
                Name: "Kontakt",
                Title: "Über uns",
                IsSelected: ko.observable<boolean>(false)
            });
    }

    public prepareQuestionItems() {
        this.questionItems.push({
            Name: "Question 1",
            Description: "This is question #1"
        }, {
            Name: "Question 2",
            Description: "This is question #2"
        }, {
            Name: "Question 3",
            Description: "This is question #3"
        }, {
            Name: "Question 4",
            Description: "This is question #4"
        });
    }

    /**
     * Automatic slide show for logo
     */
    public slideShow() {
        let container = document.getElementById("logo-icon");

        console.log("container", container);

        if(!!container) {
            if(this.index == 0) {
                $("#logo-icon").fadeIn("slow");
                container.style.backgroundImage = "url('/static/images/logo_1.png')";

                this.index = 1;
            } else {
                container.style.backgroundImage = "url('/static/images/logo_2.png')";
                this.index = 0;
            }
        }

        $('#logo-icon').fadeIn("slow");

        setTimeout(function() {
            this.slideShow()
        }.bind(this), 2000);
    }
}

// export active app instance so it can be used within other components directly
export let app = new App();

// get knockout to work
(<any>window)['ko'] = ko;
(<any>ko).amdTemplateEngine.defaultSuffix = ".html";
(<any>ko).amdTemplateEngine.defaultPath = "";
(<any>ko).amdTemplateEngine.defaultRequireTextPluginName = "require-text";
(<any>ko).bindingHandlers.module.multiExportHandling = true;
(<any>ko).punches.enableAll();
ko.applyBindings(app);