// import jquery
import "jquery";

export class TestApi {

    public static getList(): JQueryDeferred<TestEntity[]> {
        return <any>$.get("/test", null, "json");
    }

    public static add(text: string): JQueryDeferred<TestEntity> {
        return <any>$.post("/test/" + encodeURI(text), "", null, "json");
    }
}