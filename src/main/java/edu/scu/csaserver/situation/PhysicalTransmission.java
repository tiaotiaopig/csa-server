package edu.scu.csaserver.situation;

import edu.scu.csaserver.domain.Link;

import java.util.List;

/**
 * 计算网络的物理传输效能（态势）
 * 主要指标包括：带宽，时延，丢包率，信噪比
 * 我们使用归一化 + 加权平均的方法
 * 计算出每个边的得分，结合拓扑边权，计算出整个网络的传输效能
 * @author Lifeng
 * @date 2021/9/21 15:55
 */
public class PhysicalTransmission {
    private static final float bandwidthLimit = 200;
    private static final float delayLimit = 200;
    private static final float losePackageRateLimit = 1;
    private static final float snrLimit = 100;

    private static final float bwWeight = 0.3f;
    private static final float delayWeight = 0.3f;
    private static final float lprWeight = 0.3f;
    private static final float snrWeight = 0.1f;

    private static float normalizePositive (float curr, float limit) {
        return curr / limit;
    }

    private static float normalizeNegative (float curr, float limit) {
        return 1 - curr / limit;
    }

    public static void normalize (List<Link> links) {
        if (links.isEmpty()) return ;
        Link first = links.get(0);
        float bwMin = first.getBandwidth(), bwMax = bwMin;
        float delayMin = first.getDelay(), delayMax = delayMin;
        float snMin = first.getSn(), snMax = snMin;
        for (Link link : links) {
            bwMin = Math.min(bwMin, link.getBandwidth());
            bwMax = Math.max(bwMax, link.getBandwidth());
            delayMin = Math.min(delayMin, link.getDelay());
            delayMax = Math.max(delayMax, link.getDelay());
            snMin = Math.min(snMin, link.getSn());
            snMax = Math.max(snMax, link.getSn());
        }
        // 进行归一化
        for (Link link : links) {
            link.setBandwidth((link.getBandwidth() - bwMin) / (bwMax - bwMin));
            link.setDelay(1 - (link.getDelay() - delayMin) / (delayMax - delayMin));
            link.setLosePackage(1 - link.getLosePackage() / 100);
            link.setSn((link.getSn() - snMin) / (snMax - snMin));
        }
    }
}
