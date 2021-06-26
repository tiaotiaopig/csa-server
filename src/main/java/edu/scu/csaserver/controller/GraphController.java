package edu.scu.csaserver.controller;

import edu.scu.csaserver.service.GraphService;
import edu.scu.csaserver.vo.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lifeng
 * @date 2021/6/25 20:15
 */

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private GraphService graphService;


    @CrossOrigin
    @GetMapping("/getGraph")
    public Graph getGraph () {
        return graphService.generateGraph();
    }
}
