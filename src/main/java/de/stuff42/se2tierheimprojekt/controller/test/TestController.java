package de.stuff42.se2tierheimprojekt.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import de.stuff42.se2tierheimprojekt.controller.BaseController;
import de.stuff42.se2tierheimprojekt.db.FakeTableEntry;
import de.stuff42.se2tierheimprojekt.service.FakeService;

@RestController
public class TestController extends BaseController<FakeService> {

    /**
     * Creates instance for the given service.
     *
     * @param serviceInstance Service instance.
     */
    @Autowired
    public TestController(FakeService serviceInstance) {
        super(serviceInstance);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Iterable<FakeTableEntry> getList() {
        return service.getList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test/{text}")
    public FakeTableEntry add(@PathVariable String text) {
        logger.info(text);
        return service.add(text);
    }
}
