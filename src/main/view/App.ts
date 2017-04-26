// side effect import to load jQuery
import "jquery";
import * as ko from "knockout";
import "knockout.punches";
import {TestApi} from "./api/TestApi";

// app class
export class App {

    public knockoutWorkingMessage: string = "Knockout is working!";

    public itemList = ko.observableArray<TestEntity>([]);

    public textInput = ko.observable<string>("");

    public constructor() {
        // if we get here, requireJS has properly loaded jQuery and this file
        $("#requireWorks").text("RequireJS works!");

        this.loadAsync();
    }

    public loadAsync() {
        TestApi.getList().done(x => {
            this.itemList(x);
        });
    }

    public sentText() {
        TestApi.add(this.textInput()).done(x => {
            this.textInput("");
            this.itemList.push(x);
        });
    }
}

// export active app instance so it can be used within other components directly
export let app = new App();

// get knockout to work
(<any>window)['ko'] = ko;
(<any>ko).punches.enableAll();
ko.applyBindings(app);