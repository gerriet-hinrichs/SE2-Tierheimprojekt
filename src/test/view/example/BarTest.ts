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

// enable chai assertion syntax
var chai: Chai.ChaiStatic = require("chai");

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
    // Keep in mind that we have to give proxyquire the faked exports of the foo "module"
    // (which should only be the class itself.

    // NOTE: The FooStub has to be defined before using it here
    "./Foo": {Foo: FooStub},
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