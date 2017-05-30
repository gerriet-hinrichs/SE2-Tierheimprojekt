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
import {Sidebar, SidebarItem} from "../components/Sidebar";
import {QuestionApi} from "../clientApi/QuestionApi";
import QuestionModel = Api.QuestionModel;
import AnswerModel = Api.AnswerModel;
import QuestionAndAnswerIDModel = Api.QuestionAndAnswerIDModel;

export type answer = AnswerModel & {
    isChecked: KnockoutObservable<boolean>;
}

export type question = QuestionModel & {
    answerList: KnockoutObservableArray<answer>;
}

export class Fragebogen {
    public IsSidebarVisible: KnockoutObservable<boolean>;
    public sidebarItems = ko.observableArray<SidebarItem>();

    public questionList = ko.observableArray<question>(null);

    public get hasItems() {
        return this.questionList().length > 0;
    }

    constructor() {
        // Sidebar on component 'Fragebogen'
        this.IsSidebarVisible = ko.observable<boolean>(true);
        this.loadAsync();
    }

    public loadAsync() {
        // TODO: getFirstWithAnswers() Fragebogen aufbauen

        QuestionApi.getList().done((response: QuestionModel[]) => {
            response.forEach((q: QuestionModel) => {
                let sidebarItem: SidebarItem = {
                    Name: "Frage #" + q.sortOrder,
                    Title: q.text,
                    Anker: "#f" + q.sortOrder,
                    IsSelected: ko.observable<boolean>(false)
                } as SidebarItem;

                let answerList = ko.observableArray<answer>();
                QuestionApi.getAnswersForQuestion(q.id).done((r: AnswerModel[]) => {
                    r.forEach((r: AnswerModel) => {
                        let answer = {
                            text: r.text,
                            sortOrder: r.sortOrder,
                            questionId: r.questionId,
                            isChecked: ko.observable<boolean>(false)
                        } as answer;

                        answerList.push(answer);
                    });
                });

                let question = {
                    text: q.text,
                    sortOrder: q.sortOrder,
                    answers: q.answers,
                    answerList: answerList
                } as question;

                this.sidebarItems.push(sidebarItem);
                this.questionList.push(question);
            });
        });
    }

    public evaluate(component: KnockoutObservable<string>) {
        // TODO: Let the service evaluate chosen answers
        component("Auswertung");
    }
}