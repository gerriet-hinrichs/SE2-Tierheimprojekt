package de.stuff42.se2tierheimprojekt.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import de.stuff42.se2tierheimprojekt.controller.BaseController;
import de.stuff42.se2tierheimprojekt.db.TestEntity;
import de.stuff42.se2tierheimprojekt.service.test.TestService;

@RestController
public class TestController extends BaseController<TestService> {

    /**
     * Creates instance for the given service.
     *
     * @param serviceInstance Service instance.
     */
    @Autowired
    public TestController(TestService serviceInstance) {
        super(serviceInstance);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Iterable<TestEntity> getList() {
        return service.getList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test/{text}")
    public TestEntity add(@PathVariable String text) {
        logger.info(text);
        return service.add(text);
    }
}
