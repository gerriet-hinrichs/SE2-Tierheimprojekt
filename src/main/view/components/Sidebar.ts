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
 *  Sidebar component
 */
export type SidebarItem = {
    displayTitle: string;
    displaySubTitle: string;
};

export interface ISidebarParams {
    title: string;
    right?: boolean;
    large?: boolean;
    items?: KnockoutObservableArray<SidebarItem>;
}
;

export class Sidebar {

    // fetch dom element here
    public sidebar = $('#sidebar');
    public icon = this.sidebar.find('sidebar-icon').toggleClass('rotate-icon');

    public items = ko.observableArray<SidebarItem>();
    public numberOfItems = ko.pureComputed(() => {
        return this.items.Count;
    });

    public title = ko.observable<string>("");

    private _isOpen = ko.observable<boolean>(true);
    public isOpen = ko.pureComputed({
        read: () => {
            return this._isOpen();
        },
        write: s => {
            this._isOpen(s);
        }
    }).extend({deffered: true, notify: 'always'});

    constructor(params: ISidebarParams) {
        this.title(params.title);
        this.items = params.items;

        this.isOpen.subscribe(s => {
            this.toggleVisibility(s);
        });

        this._isOpen.subscribe(s => {
            this.rotateIcon();
        });
    }

    private toggleVisibility(visible: boolean) {
        if (visible && !this.sidebar.hasClass('visible')) {
            this.sidebar.addClass('visible');
        } else {
            this.sidebar.removeClass('visible');
        }
    }

    private rotateIcon() {
        // TODO
    }
}