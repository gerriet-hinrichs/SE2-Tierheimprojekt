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