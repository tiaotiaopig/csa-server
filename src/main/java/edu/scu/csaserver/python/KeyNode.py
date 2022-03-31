#!/usr/bin/env python3
# -*- encoding: utf-8 -*-
'''
@File    :   keyNode.py
@Time    :   2022/03/31 10:17:00
@Author  :   LiFeng
@Contact :   2807229316@qq.com
@Desc    :   None
'''
import numpy as np
import networkx as nx
import sys


def key_node_cc():
    '''接近中心性计算关键节点'''
    pair_wise = np.loadtxt(path_graph, dtype=np.int32)
    if np.min(pair_wise) > 0: pair_wise = pair_wise - 1
    graph = nx.from_edgelist(pair_wise, create_using=nx.Graph)
    close_cen = nx.closeness_centrality(graph)
    close_cen = sorted(close_cen.items(), key=lambda x: x[1], reverse=True)
    ids = [item[0] for item in close_cen]
    ids = ids[0 : len(ids) // 10]
    # 直接输出到标准输出流
    for id in ids: print(id)
    # np.savetxt(path_save + 'CC_result.txt', np.array(ids),  fmt="%0d", delimiter="\n")
    # for elem in close_cen: print(elem[0])

def key_node_d():
    '''使用节点度计算关键节点'''
    pair_wise = np.loadtxt(path_graph, dtype=np.int32)
    if np.min(pair_wise) > 0: pair_wise = pair_wise - 1
    graph = nx.from_edgelist(pair_wise, create_using=nx.Graph)
    deg = list(nx.degree(graph))
    deg = sorted(deg, key=lambda x: x[1], reverse=True)
    ids = [item[0] for item in deg]
    ids = ids[0 : len(ids) // 10]
    # 直接输出到标准输出流
    for id in ids: print(id)

def key_node_bc():
    '''介数中心性计算关键节点'''
    pair_wise = np.loadtxt(path_graph, dtype=np.int32)
    if np.min(pair_wise) > 0: pair_wise = pair_wise - 1
    graph = nx.from_edgelist(pair_wise, create_using=nx.Graph)
    close_cen = nx.betweenness_centrality(graph)
    close_cen = sorted(close_cen.items(), key=lambda x: x[1], reverse=True)
    ids = [item[0] for item in close_cen]
    ids = ids[0 : len(ids) // 10]
    # 直接输出到标准输出流
    for id in ids: print(id)

# path_graph = 'graph/BUP.txt'
func_name = sys.argv[1]
path_graph = sys.argv[2]

if __name__ == '__main__':
    if 'D' == func_name:
        key_node_d()
    elif 'BC' == func_name:
        key_node_bc()
    elif 'CC' == func_name:
        key_node_cc()