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

export type auswertung = ResultModel;
export type question = QuestionModel;

export class Auswertung {
    public questionList = ko.observableArray<question>([]);
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

    constructor() {
        this.IsSidebarVisible = ko.observable<boolean>(true);
    }

    public loadAsync(parent: App) {
        let questions = parent.questionList();
        this.questionList(questions);

        // Build up the answers object for passing to evaluation
        // Map: [QUESTION_ID] -> ANSWER_ID[]
        let answers: QuestionApi$evaluateQuestionnaire$answers = {};

        // TODO: Rework without ANY cast
        for (let question of questions) {
            answers[question.id] = (<any>question).answers.map((a: any) => a.id);
        }


        this.IsBusy(true);
        QuestionApi.evaluateQuestionnaire(answers).done((result: ResultModel) => {
            this.questionnaireResult(result);

            // TODO: Rework without ANY cast
            result.foundAnimals.forEach((animal: any) => {
                this.sidebarItems.push({
                    Name: animal.name,
                    Title: animal.race,
                    Anker: "",
                    IsSelected: ko.observable<boolean>(false)
                });
            });

        }).always(() => this.IsBusy(false));
    }
}