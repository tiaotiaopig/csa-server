package edu.scu.csaserver.utils;

import edu.scu.csaserver.domain.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 使用关键节点两两之间求最短路径
 * 由于关键链路是基于关键节点求出的，所以必须关键节点先求出
 * 关键链路才能计算
 * 需要两个参数：
 *      edges, keyNodes
 * 这两个参数，我们只能通过redis拿到，否则只能和关键节点写在一起了
 * @author Lifeng
 * @date 2021/10/18 21:00
 */

@Component
public class KeyLink {

    private List<List<Integer>> adjacentTable;
    private boolean[] visited;

    public void init(int[][] links, int idMax) {
        visited = new boolean[idMax + 1];
        // 每次调用都要重新生成
        adjacentTable = new ArrayList<>();
        for (int i = 0; i <= idMax; i++) adjacentTable.add(new ArrayList<>());
        for (int[] link : links) {
            adjacentTable.get(link[0]).add(link[1]);
            adjacentTable.get(link[1]).add(link[0]);
        }
    }

    private void getPaths(int source, int target, List<Integer> selected, List<List<Integer>> paths) {
        if (source == target) {
            List<Integer> copy = new ArrayList<>(selected);
            copy.add(target);
            paths.add(copy);
            return;
        }
        // 做出选择
        selected.add(source);
        visited[source] = true;
        List<Integer> adjacent = adjacentTable.get(source);
        for (Integer adj : adjacent) {
            if (visited[adj]) continue;
            getPaths(adj, target, selected, paths);
        }
        // 撤销选择
        visited[source] = false;
        selected.remove(selected.size() - 1);
    }

    public List<Link> getKeyLinks(List<Integer> keyNodes) {
        // 我们暂时取前三个进行计算
        int limit = 3;
        int[] ids = new int[limit];
        List<Link> res = new ArrayList<>();
        List<List<Integer>> shorts = new ArrayList<>();
        for (int i = 0, len = keyNodes.size(); i < limit && i < len; i++) ids[i] = keyNodes.get(i);
        for (int source = 0; source < ids.length - 1; source++) {
            for (int target = source + 1; target < ids.length; target++) {
                List<List<Integer>> paths = new ArrayList<>();
                // 获取所有路径
                getPaths(ids[source], ids[target], new ArrayList<>(), paths);
                // 路径长度排序
                paths.sort(Comparator.comparingInt(List::size));
                // 直连的，直接作为关键链路，不参与求交集过程
                for (int i = 0, len = paths.size(); i < len; i++) {
                    List<Integer> path = paths.get(i);
                    if (path.size() == 2) {
                        Link link = new Link();
                        link.setSourceNodeId(path.get(0));
                        link.setTargetNodeId(path.get(1));
                        res.add(link);
                    } else {
                        shorts.add(path);
                    }
                    if (i < len - 1 && path.size() < paths.get(i + 1).size()) break;
                }
            }
        }
        res.addAll(intersect(shorts));
        return res;
    }

    private List<Link> intersect(List<List<Integer>> shorts) {
        // 我们使用字符串形式的边作为键
        int len = shorts.size();
        HashMap<String, Integer> cnt = new HashMap<>();
        // 因为我们是无向边，而路径是有向的
        String edge1, edge2;
        for (List<Integer> s : shorts) {
            for (int i = 0, size = s.size(); i < size - 1; i++) {
                edge1 = s.get(i) + "," + s.get(i + 1);
                edge2 = s.get(i + 1) + "," + s.get(i);
                cnt.put(edge1, cnt.getOrDefault(edge1, 0) + 1);
                cnt.put(edge2, cnt.getOrDefault(edge2, 0) + 1);
            }
        }
        // 使用 set 进行存储和去重
        HashSet<String> set = new HashSet<>();
        for (String s : cnt.keySet()) {
            String[] split = s.split(",");
            String equalsEdge = split[1] + "," + split[0];
            if (set.contains(s) || set.contains(equalsEdge)) continue;
            if (cnt.get(s) == len || cnt.get(equalsEdge) == len) set.add(s);
        }
        // 将字符串的边恢复出来
        List<Link> res = new ArrayList<>();
        for (String s : set) {
            String[] split = s.split(",");
            Link link = new Link();
            link.setSourceNodeId(Integer.parseInt(split[0]));
            link.setTargetNodeId(Integer.parseInt(split[1]));
            res.add(link);
        }
        return res;
    }
}
