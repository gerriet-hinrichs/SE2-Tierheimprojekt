// enable chai assertion syntax
let chai: Chai.ChaiStatic = require("chai");

// load proxyquire that allows stub injection
let proxyquire: Proxyquire = require("proxyquire");

// import Bar type definitions. Since we want to use a stub for Bar's dependency, we have to import
// the Bar to be tested differently (below stubs)
import {Bar} from "../uut/example/Bar";

// Foo stub class
class FooStub {

    public getName(): string {
        return "Foo-Stub";
    }
}

// import Bar with a changed import (the import path here is the same as for type definitions)
let BarExports = proxyquire("../uut/example/Bar", {

    // the key has to be exactly the import as used in the Bar class.
    // since a relative import is used there we need to specify that here too.
    // NOTE: The FooStub has to be defined before using it here
    "./Foo": FooStub,
});

// since above function returns the exports object we need to access the proper element
let UUT = BarExports.Bar;

// test suite
describe("Component: example/Bar", () => {

    it("Test #1: Proper name", () => {
        let bar: Bar = new UUT();
        chai.expect(bar.getName()).to.be.equal("Bar");
    });

    it("Test #2: Stub injection", () => {
        let bar: Bar = new UUT();
        chai.expect(bar.getSentence()).to.be.equal("Me, Bar, and my friend Foo-Stub.")
    });
});