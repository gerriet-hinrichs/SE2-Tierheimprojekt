// configure module loading
requirejs.config({

    // base uri for script files
    baseUrl: "static/view/",
    paths: {

        // for libraries we explicitly use CDNs and have to list them here
        // (we only choose the minified version if we are not on debug)
        "knockout": "//cdnjs.cloudflare.com/ajax/libs/knockout/3.4.0/knockout" + (DEBUG ? "-debug" : "-min"),
        "jquery": "//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery" + (DEBUG ? "" : ".min"),
    }
});

// load app to start client side application
requirejs(["App"]);