// side effect import to load jQuery
import "jquery";

// private (not exported) app class
class App {

    public constructor() {
        // if we get here, requireJS has properly loaded jQuery and this file
        $("#requireWorks").text("RequireJS works!");
    }
}

// create app instance
let app = new App();