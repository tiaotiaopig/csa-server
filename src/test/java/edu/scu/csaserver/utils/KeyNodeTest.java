package edu.scu.csaserver.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KeyNodeTest {

    @Resource
    private KeyNode keyNode;
    @Test
    void getKeyNodeIds() {
        int[][] links = {
                {2, 1}, {4, 1}, {6, 1}, {6, 8}, {8, 9},
                {1, 3}, {9, 3}, {3, 7}, {3, 5}, {5, 10},
                {10, 11}, {11, 13}, {11, 14}, {13, 15}, {13, 16},
                {13, 17}, {15, 18}, {15, 20}, {16, 19}, {17, 18},
                {12, 18}, {12, 17}, {14, 16}, {19, 20}
        };
//        int[] keys = keyNode.getKeyNodeIds(20, links);
//        for (int key : keys) {
//            System.out.println(key);
//        }
    }
}