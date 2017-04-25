import {Foo} from "./Foo";

/**
 * Simple example class with dependency to be tested.
 */
export class Bar {

    private foo: Foo = new Foo();

    public getName(): string {
        return "Bar";
    }

    public getSentence(): string {
        return `Me, ${this.getName()}, and my friend ${this.foo.getName()}.`;
    }
}