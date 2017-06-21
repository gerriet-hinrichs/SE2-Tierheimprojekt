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
 *  Auswertung component
 */
import ResultModel = Api.ResultModel;
import {QuestionApi} from "../clientApi/QuestionApi";
import {SidebarItem} from "../components/Sidebar";
import {App} from "../App";
import QuestionApi$evaluateQuestionnaire$answers = Alias.QuestionApi$evaluateQuestionnaire$answers;
import QuestionModel = Api.QuestionModel;
import AnswerModel = Api.AnswerModel;
import {question} from "./Fragebogen";

export type answer = AnswerModel;

export interface IAuswertungParams {
    questionList: KnockoutObservableArray<question>;
}

export class Auswertung {
    public questionList: KnockoutObservableArray<question>;
    public questionnaireResult = ko.observable<ResultModel>();
    public IsSidebarVisible: KnockoutObservable<boolean>;

    // Observable lists for ad hoc rendering
    public sidebarItems = ko.observableArray<SidebarItem>([]);

    /**
     * Busy indicator
     */
    public IsBusy = ko.observable<boolean>(false);

    public get hasItems() {
        return !!this.questionnaireResult();
    }

    constructor(params: IAuswertungParams) {
        this.questionList = params.questionList;
        this.IsSidebarVisible = ko.observable<boolean>(true);
    }

    public loadAsync(parent: App) {
        // Build up the answers object for passing to evaluation
        // Map: [QUESTION_ID] -> ANSWER_ID[]
        let answers: QuestionApi$evaluateQuestionnaire$answers = {};

        let q = this.questionList();
        for (let question of q) {
            if (question.isAnswered()) {
                answers[question.id] = question.answerType == "RADIO_BUTTON" ? [question.selectedAnswer()] : question.selectedAnswers();
            }
        }

        this.IsBusy(true);
        QuestionApi.evaluateQuestionnaire(answers).done((result: ResultModel) => {
            this.questionnaireResult(result);

            result.foundAnimals.forEach((animal: any) => {
                this.sidebarItems.push({
                    Name: animal.name,
                    Title: animal.race,
                    Anker: "#a" + animal.id,
                    IsSelected: ko.observable<boolean>(false)
                });
            });

            parent.questionnaireResult = this.questionnaireResult;
        }).always(() => this.IsBusy(false));
    }
}