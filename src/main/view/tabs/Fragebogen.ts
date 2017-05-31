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


export type question = QuestionModel & {
    answerList: KnockoutObservableArray<AnswerModel>;
    isAnswered: KnockoutObservable<boolean>;
    selectedAnswer: KnockoutObservable<number>;
}

export class Fragebogen {
    public IsSidebarVisible: KnockoutObservable<boolean>;

    // Observable lists for ad hoc rendering
    public sidebarItems = ko.observableArray<SidebarItem>([]);
    public questionList = ko.observableArray<question>([]);

    /**
     * Busy indicator
     */
    public IsBusy = ko.observable<boolean>(false);

    public get hasItems() {
        return this.questionList().length > 0;
    }

    public lastQuestion = ko.pureComputed<question | null>(() => {
        let items = this.questionList();
        if (items.length == 0) {
            return null;
        }
        return items[items.length - 1];
    });

    public showNextButton = ko.pureComputed<boolean>(() => {
        let last = this.lastQuestion();
        return last != null ? last.isAnswered() : false;
    });

    constructor() {
        // Sidebar on component 'Fragebogen'
        this.IsSidebarVisible = ko.observable<boolean>(true);
        this.loadAsync();

        // Subscription on answeList to check if question has at least one answer checked
        this.questionList.subscribe((a) => {
            console.log("subscription: ", a);

        });
    }

    public loadAsync() {
        this.IsBusy(true);
        this.questionList.removeAll();
        QuestionApi.getFirstWithAnswers().done((q: QuestionModel) => {

            // Build up sidebar
            let sItem = {
                Name: "Frage #" + q.sortOrder,
                Anker: "#f" + q.id,
                Title: q.text
            } as SidebarItem;
            this.sidebarItems.push(sItem);

            let answerList = ko.observableArray<AnswerModel>([]);
            let a = q.answers;
            if (!!a) {
                a.forEach((answer: AnswerModel) => {
                    let aItem = {
                        id: answer.id,
                        text: answer.text,
                        sortOrder: answer.sortOrder
                    } as AnswerModel;
                    answerList.push(aItem);
                });
            }

            let selectedAnswerObservable = ko.observable<number>();
            let qItem = {
                id: q.id,
                text: q.text,
                sortOrder: q.sortOrder,
                answers: q.answers,
                answerList: !!a ? answerList : [],
                isAnswered: ko.pureComputed<boolean>(() => selectedAnswerObservable() != null),
                selectedAnswer: selectedAnswerObservable
            } as question;
            this.questionList.push(qItem);

        }).always(() => this.IsBusy(false));
    }

    public continue(q: question) {
        let params = {
            questionID: q.id,
            answerID: q.selectedAnswer()
        } as QuestionAndAnswerIDModel;

        console.log(params);

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

            let qItem = {
                id: r.id,
                text: r.text,
                sortOrder: r.sortOrder,
                answers: r.answers,
                answerList: answerList,
                isAnswered: ko.observable<boolean>(false),
                selectedAnswer: ko.observable<number>()
            } as question;
            this.questionList.push(qItem);

        }).always(() => this.IsBusy(false));
    }

    public evaluate(component: KnockoutObservable<string>) {
        // TODO: Let the service evaluate chosen answers
        component("Auswertung");
    }

    public next() {
        let q = this.lastQuestion();
        if(q != null) {
            this.continue(q);
        }
    }
}