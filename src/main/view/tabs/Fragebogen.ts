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
import {App} from "../App";

export interface IFragebogenParams {
    questionList: KnockoutObservableArray<question>;
}

export type question = QuestionModel & {
    answerList: KnockoutObservableArray<AnswerModel>;
    isAnswered: KnockoutComputed<boolean>;
    selectedAnswer: KnockoutObservable<number>;
}

export class Fragebogen {
    public IsSidebarVisible: KnockoutObservable<boolean>;

    // Passed question items that are already answered
    public questionList: KnockoutObservableArray<question>;

    // Observable lists for ad hoc rendering
    public sidebarItems = ko.observableArray<SidebarItem>([]);
    public items = ko.observableArray<question>([]);

    // Indicator for not receiving questions anymore
    public isLastQuestion = ko.observable<boolean>(false);

    /**
     * Busy indicator
     */
    public IsBusy = ko.observable<boolean>(false);

    public get hasItems() {
        return this.items().length > 0;
    }

    public itemCount = ko.pureComputed<number>(() => {
        return this.items().length;
    });

    public lastQuestion = ko.pureComputed<question | null>(() => {
        let items = this.items();
        if (items.length == 0) {
            return null;
        }
        return items[items.length - 1];
    });

    public showNextButton = ko.pureComputed<boolean>(() => {
        let last = this.lastQuestion();
        return last != null && !this.isLastQuestion() ? last.isAnswered() : false;
    });

    constructor(params: IFragebogenParams) {
        this.questionList = params.questionList;
        // Sidebar on component 'Fragebogen'
        this.IsSidebarVisible = ko.observable<boolean>(true);

        if (this.questionList().length > 0) {
            // Add already answered questions to current instance
            // In case of tab switching around
            this.questionList().forEach(q => {
                if (!q.answers) return;

                // Build up sidebar
                let sItem = {
                    Name: "Frage #" + q.sortOrder,
                    Anker: "#f" + q.id,
                    Title: q.text
                } as SidebarItem;
                this.sidebarItems.push(sItem);

                this.items.push(q);
            });
        } else {
            this.loadAsync();
        }
    }

    public loadAsync() {
        this.IsBusy(true);
        this.items.removeAll();
        QuestionApi.getFirstWithAnswers().done((q: QuestionModel) => {
            // objects possibly null
            if (!q.answers) return;

            // Build up sidebar
            let sItem = {
                Name: "Frage #" + q.sortOrder,
                Anker: "#f" + q.id,
                Title: q.text
            } as SidebarItem;
            this.sidebarItems.push(sItem);

            let answerList = ko.observableArray<AnswerModel>([]);
            q.answers.forEach((answer: AnswerModel) => {
                let aItem = {
                    id: answer.id,
                    text: answer.text,
                    sortOrder: answer.sortOrder
                } as AnswerModel;
                answerList.push(aItem);
            });


            let selectedAnswerObservable = ko.observable<number>();
            let qItem = {
                id: q.id,
                text: q.text,
                sortOrder: q.sortOrder,
                answers: q.answers,
                answerType: q.answerType,
                answerList: !!a ? answerList : [],
                isAnswered: ko.pureComputed<boolean>(() => selectedAnswerObservable() != null),
                selectedAnswer: selectedAnswerObservable
            } as question;
            this.items.push(qItem);

        }).always(() => this.IsBusy(false));
    }

    public continue(q: question) {
        let params = {
            questionID: q.id,
            answerID: q.selectedAnswer()
        } as QuestionAndAnswerIDModel;

        this.IsBusy(true);
        QuestionApi.getNextForAnswer(params).done((r: QuestionModel) => {

            // Build up sidebar
            let sItem = {
                Name: "Frage #" + r.sortOrder,
                Anker: "#f" + r.id,
                Title: r.text
            } as SidebarItem;
            this.sidebarItems.push(sItem);

            let answerList = ko.observableArray<AnswerModel | null>(r.answers);

            let selectedAnswerObservable = ko.observable<number>();
            let qItem = {
                id: r.id,
                text: r.text,
                sortOrder: r.sortOrder,
                answers: r.answers,
                answerList: answerList,
                isAnswered: ko.pureComputed<boolean>(() => selectedAnswerObservable() != null),
                selectedAnswer: selectedAnswerObservable
            } as question;
            this.items.push(qItem);

            this.scrollTo(sItem.Anker);

        }).fail(() => this.isLastQuestion(true)).always(() => this.IsBusy(false));
    }

    public evaluate(parent: App) {
        parent.questionList = this.items;
        parent.currentComponent("Auswertung");
    }

    public next(parent: App) {
        let q = this.lastQuestion();
        if(q != null) {
            parent.questionList = this.items;
            this.continue(q);
        }
    }

    public scrollTo(anker: string) {
        // Fetch sidebar element to scroll to and click it
        let href: string = "a[href='" + anker + "']";
        $(href)[0].click();
    }
}