package edu.scu.csaserver.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SituationServiceImplTest {

    @Autowired
    private SituationServiceImpl situationService;

    @Test
    void phyTrans() {
//        situationService.init();
//        System.out.println(situationService.phyTrans());
    }

    @Test
    void phyLinkElem() {
//        situationService.init();
//        System.out.println(situationService.phyLinkElem());
    }

    @Test
    void nodeProcess() {
//        situationService.init();
        System.out.println(situationService.nodeProcess());
    }

    @Test
    void npElem() {
//        situationService.init();
        System.out.println(situationService.npElem());
    }
}