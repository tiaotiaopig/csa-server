package edu.scu.csaserver.controller;

import edu.scu.csaserver.vo.Res;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GraphControllerTest {

    @Autowired
    private GraphController graphController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getGraph() {
        assertThat(graphController).isNotNull();
    }

    @Test
    void getGraph02() {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/graph/getGraph", Res.class)).isNotNull();
    }
}