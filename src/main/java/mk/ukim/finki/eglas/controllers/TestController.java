package mk.ukim.finki.eglas.controllers;

import mk.ukim.finki.eglas.services.TestProcedures;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestProcedures testProcedures;

    public TestController(TestProcedures testProcedures) {
        this.testProcedures = testProcedures;
    }

    @GetMapping("{id}")
    public Double test(@PathVariable Long id) {
        return testProcedures.getTurnoutByRealizationId(id);
    }
}
