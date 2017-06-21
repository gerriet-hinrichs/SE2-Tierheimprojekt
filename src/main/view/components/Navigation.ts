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
 *  Navigation component
 */
import QuestionModel = Api.QuestionModel;
export type NavigationItem = {
    Name: string;
    Title: string;
    IsSelected: KnockoutObservable<boolean>;
};

export interface INavigationParams {
    items: KnockoutObservableArray<NavigationItem>;
    currentComponent: KnockoutObservable<string>;
    questionList: KnockoutObservableArray<QuestionModel>;
}

export class Navigation {
    public itemList: KnockoutObservableArray<NavigationItem>;
    public currentComponent: KnockoutObservable<string>;
    public questionList: KnockoutObservableArray<QuestionModel>;

    constructor(params: INavigationParams) {
        this.currentComponent = params.currentComponent;
        this.itemList = params.items;
        this.questionList = params.questionList;
    }

    public navigate(item: NavigationItem) {
        for (let item of this.itemList()) {
            item.IsSelected(false);
        }

        item.IsSelected(true);

        // Fallback if questionnaire just started and wanna continue
        // In this case, go to questionnaire instead of 'Fragen' component
        if (item.Name == "Fragen" && this.questionList().length > 0) {
            this.currentComponent("Fragebogen");
        } else {
            this.currentComponent(item.Name);
        }
    }
}