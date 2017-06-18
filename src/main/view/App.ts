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
import ResultModel = Api.ResultModel;
import AnswerModel = Api.AnswerModel;
import QuestionModel = Api.QuestionModel;
import {question} from "./tabs/Fragebogen";

// app class
export class App {
    /**
     * Items for navigation and sidebar
     */
    public navigationItems = ko.observableArray<NavigationItem>([]);

    /**
     * Current component name for intern pseudo routing via module binding.
     * Default 'Start' for selected component on startup
     */
    public currentComponent: KnockoutObservable<string>;

    /**
     * Global progress store to bypass parameter passing through components
     */
    public questionList: KnockoutObservableArray<question>;
    public questionnaireResult: KnockoutObservable<ResultModel>;
    public isQuestionnaireInProgress = ko.pureComputed<boolean>(() => {
        return this.questionList().length > 0;
    });

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
        // Initializing
        this.currentComponent = ko.observable<string>("Start");
        this.questionList = ko.observableArray<question>([]);
        this.questionnaireResult = ko.observable<ResultModel>(null);

        this.setViewPort();
        this.prepareNavigationItems();

        // Automatic slide show icon
        //this.slideShow();

        $(function () {
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