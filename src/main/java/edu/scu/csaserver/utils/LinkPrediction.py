#!/usr/bin/env python3
# -*- encoding: utf-8 -*-
'''
@File    :   LinkPrediction.py
@Time    :   2022/03/31 16:48:55
@Author  :   LiFeng
@Contact :   2807229316@qq.com
@Desc    :   None
'''

import networkx as nx
import numpy as np
import sys

def read_file(filepath: str) -> nx.Graph:
    pair_wise = np.loadtxt(filepath, dtype=np.int32)
    if (np.min(pair_wise) > 0):
        pair_wise = pair_wise - 1
        np.savetxt(filepath, pair_wise)
    return nx.from_edgelist(pair_wise, create_using=nx.Graph)


def common_neighbor(filepath: str):
    '''
        根据文件路径,返回共同邻居数量降序排列的不存在的边
    '''
    graph = read_file(filepath)
    no_exist = nx.non_edges(graph)
    links = list(nx.common_neighbor_centrality(graph, no_exist))
    sorted(links, key=lambda elem: elem[2], reverse=True)
    return links[: len(links) // 20]

def page_rank(filepath: str):
    pass

def sim_rank(filepath: str):
    pass


if __name__ == '__main__':

    func = sys.argv[1]
    filepath = sys.argv[2]
    links = []
    if 'common_neighbor' == func:
        links = common_neighbor(filepath)
    elif 'page_rank' == func:
        links = page_rank(filepath)
    elif 'sim_rank' == func:
        links = sim_rank(filepath)

    for link in links:
        print(f'{link[0]} {link[1]}')

