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
/**
 *  Fragebogen component
 */
import {SidebarItem} from "../components/Sidebar";

export class Fragebogen {
    public IsSidebarVisible: KnockoutObservable<boolean>;
    public sidebarItems = ko.observableArray<SidebarItem>();

    constructor() {
        // No sidebar on component 'Kontakt'
        this.IsSidebarVisible = ko.observable<boolean>(true);

        this.prepareSidebarItems();
    }

    public prepareSidebarItems() {
        this.sidebarItems.push({
                Name: "Frage 1",
                Title: "Frage 1",
                Anker: "#f1",
                IsSelected: ko.observable<boolean>(true)
            },
            {
                Name: "Frage 2",
                Title: "Frage 2",
                Anker: "#f2",
                IsSelected: ko.observable<boolean>(false)
            },
            {
                Name: "Frage 3",
                Title: "Frage 3",
                Anker: "#f3",
                IsSelected: ko.observable<boolean>(false)
            },
            {
                Name: "Frage 4",
                Title: "Frage 4",
                Anker: "#f4",
                IsSelected: ko.observable<boolean>(false)
            });
    }
}