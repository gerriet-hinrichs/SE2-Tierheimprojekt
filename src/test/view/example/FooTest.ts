// enable chai assertion syntax
let chai: Chai.ChaiStatic = require("chai");

// import Foo. Since it has no dependencies (or this test does not want to use stubs)
// we can import it this way
import {Foo} from "../uut/example/Foo";

// the string is the test suite name visible in reports
describe("Component: example/Foo", () => {

    // the string is the name of the specific test visible in reports
    it("Test #1: Proper name", () => {
        let foo = new Foo();
        chai.expect(foo.getName()).to.be.equal("Foo");
    });

    it("Test #2: Not the wrong name", () => {
        let foo = new Foo();
        chai.expect(foo.getName()).not.to.be.equal("Bar");
    });
});