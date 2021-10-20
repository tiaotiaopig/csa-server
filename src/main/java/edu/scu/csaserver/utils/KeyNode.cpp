/*
 * Copyright (C) 2019 Xiao-Long Ren, Niels Gleinig, Dirk Helbing, Nino Antulov-Fantulin
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/


/*
 * This code repeatedly partitions the gcc (giant connected component) of
 * the network into two subnets by the spectcal clustering and Weighted
 * Vertex Cover algorithms, such that the size of the gcc is smaller than
 * a specific value. The output is the set of nodes that should be removed.
 * */

#include <iostream>
#include <fstream>
#include <set>
#include <vector>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <random>
#include <cstdlib>
#include <ctime>
#include "edu_scu_csaserver_utils_KeyNode.h"
using namespace std;

//const int NODE_NUM = 20;                // the number of nodes // 网络中节点总数
const char* FILE_NET;  // input format of each line: id1 id2 // 输入文件中每一行的格式：id1 id2
//const char* FILE_ID = "NodeSet_GND_weighted_CrimeNet.txt";  // output the id of the removed nodes in order  // 按序输出待移除的节点id
//const char* FILE_PLOT = "Plot_GND_weighted_CrimeNet.txt";   // format of each line: gcc removed_cost removed_nodes // 输出文件每一行的格式：极大连通分量 移除代价 移除节点？
const char* KEY_NODE = "keynode.txt";
const int REMOVE_STRATEGY = 3; // 1: weighted method: powerIterationB(); vertex_cover_2() -- remove the node with smaller degree first // 优先移除度数更小的节点
							   // 3: unweighted method with one-degree in vertex cover： powerIteration; vertex_cover() -- remove the node with larger degree first // 优先移除度数更大的节点
const int PLOT_SIZE = 1; // the removal size of each line in FILE_PLOT. E.g. PLOT_SIZE=2 means each line of FILE_PLOT is the result that remove two nodes from the network // 输出文件的每一行表示从网络中每移除PLOT_SIZE个节点后得到的结果
const int TARGET_SIZE = 4; // If the gcc size is smaller than TARGET_SIZE, the dismantling will stop. Default value can be 0.01*NODE_NUM  OR  1000 // 算法中止条件

// 读取JNI二维数组，填充到 A 中并返回
void input(vector<vector<int>*>* A, JNIEnv *env, jobjectArray array) {
    // 每个边的 source target 的 id
    int id1 = 0, id2 = 0;
    // 获取JNI二维数组的长度
    int len = env->GetArrayLength(array);
    // 二维数组被当成对象数组
    // 取出每个对象,转成一JNI维数组
    for(int i = 0; i < len; i++) {
        jintArray item = (jintArray)env->GetObjectArrayElement(array,i);
        // 我们默认每个一维数组的长度为2，记得做检验
//        int size = env->GetArrayLength(item);
        // jintArray 不能直接访问
        // 转化成 jint 数组，才能遍历访问
        jint* elem = env->GetIntArrayElements(item, NULL);
        // 逐行填充数据
        id1 = elem[0];
        id2 = elem[1];
        A->at(id1 - 1)->push_back(id2); // 节点id在A中的位置位于第id-1行
        A->at(id2 - 1)->push_back(id1); // 输入文件每行有两个节点，相互将对方push_back到节点的邻接节点向量中
    }
    // A是向量指针，我们在这里修改了，外面也跟着变化了，无需返回了
}
// read the links of the network, return A
void rdata(vector<vector<int>*>* A) {
	ifstream rd(FILE_NET);
	if (!rd) std::cout << "error opening file\n";

	int id1 = 0, id2 = 0;
	while (rd >> id1 >> id2) {
		A->at(id1 - 1)->push_back(id2); // 节点id在A中的位置位于第id-1行
		A->at(id2 - 1)->push_back(id1); // 输入文件每行有两个节点，相互将对方push_back到节点的邻接节点向量中
	}
	rd.close();
}

void multiplyByLaplacian(vector<vector<int>*>* A, vector<double>* x, vector<double>* y, int dmax)
{
	// y = L^tilda * x
	// y_i = sum_j L^tilda_{i,j} * x_j
	// y_i = sum_j (d_max - (d_i - A_ij)) * x_j
	for (int i = 0; i < A->size(); ++i) {
		y->at(i) = 0;
		// y_i = sum_j A_ij * x_j
		for (int j = 0; j < int(A->at(i)->size()); ++j) {
			y->at(i) = y->at(i) + x->at(A->at(i)->at(j) - 1);
		}
		// y_i = (dmax - d_i)*x_j  + y_i 
		// y_i = x_i * (dmax - degree_i) + y_i
		y->at(i) = x->at(i) * (dmax - int(A->at(i)->size())) + y->at(i);
	}
}

void multiplyByWeightLaplacian(vector<vector<int>*>* A, vector<double>* x, vector<double>* y, vector<int>* db, int dmax)
{
	// y = L^tilda * x
	// y_i = sum_j (c-L_ij) * x_j

	// y_i = sum_j { A_ij*(di-1)*x_j }
	for (int i = 0; i < A->size(); ++i) {
		y->at(i) = 0;
		// y_i = A_ij * x_j
		for (int j = 0; j < A->at(i)->size(); ++j) {
			y->at(i) = y->at(i) + x->at(A->at(i)->at(j) - 1);  // y_i = sum x_j
		}
		// y_i = (d_i - 1) * y_i
		y->at(i) = (A->at(i)->size() - 1) * y->at(i);
	}

	// 
	for (int i = 0; i < A->size(); ++i) {
		for (int j = 0; j < A->at(i)->size(); ++j) {
			y->at(i) = y->at(i) + x->at(A->at(i)->at(j) - 1) * A->at(A->at(i)->at(j) - 1)->size();
		}
		y->at(i) = y->at(i) + (dmax - db->at(i)) * x->at(i);
	}
}

void orthonormalize(vector<double>* x)
{
	double inner = 0;
	int n = int(x->size());
	for (int no = 0; no < n; ++no) {
		inner = inner + x->at(no) / sqrt(n);
	}

	double norm = 0;
	for (int no = 0; no < n; ++no) {
		x->at(no) = x->at(no) - inner / sqrt(n);
		norm = norm + x->at(no) * x->at(no);
	}
	norm = sqrt(norm);
	for (int no = 0; no < n; ++no) {
		x->at(no) = x->at(no) / norm;
	}
}

// return a vector [transfer] that mark all the nodes belongs to gcc
// if transter[i] = 0 then this node doesn't belong to the gcc
// if transter[i] != 0 then transter[i] is the new id of this node 
vector<int> get_gcc(vector<vector<int>*>* adj) {
	int n = int(adj->size()); // 输入矩阵的节点个数
	vector<int> id(n, 0); // store the cluster id of each node	// 存储每个节点所属的连通分量的编号

	int id_now = 0; // 连通分量id
	for (int i = 0; i < n; i++) // wide-first search, assign each connected cluster an id
	{
		if (id[i] == 0 && adj->at(i)->size() > 0) { // this node does not belong to any cluster yet && this node is not isolated
		// 如果节点尚未属于任何连通分量且它有邻居（即并不是孤立节点）
			set<int> set_nodes;	// 存放连通分量中的节点
			set_nodes.insert(i + 1); //现将下标i对应的节点i+1放入set_nodes集合
			id_now++; // 分配连通分量id
			while (set_nodes.size() > 0) // 若set_nodes集合不为空，先为这个集合中的所有节点分配连通分量id，再将其从set_nodes集合中删除
			{
				int node_now = *(--set_nodes.end()); // 取set_nodes集合中节点在A中的id
				id[node_now - 1] = id_now; // 为节点分配连通分量id

				set_nodes.erase(--set_nodes.end());  // 从set_nodes集合中删除已分配连通分量id的节点
				for (int k = 0; k<int(adj->at(node_now - 1)->size()); k++) // append
				// 如果该节点有非孤立邻居节点，且未给邻居节点分配连通分量id，则将邻居节点放入set_nodes集合
					if (id[adj->at(node_now - 1)->at(k) - 1] == 0 && adj->at(adj->at(node_now - 1)->at(k) - 1)->size() != 0)
						set_nodes.insert(adj->at(node_now - 1)->at(k));
			}
		}
	}

	int max_id = 0; // 连通分量id的最大值
	for (int i = 0; i < n; i++)
		if (max_id < id[i]) max_id = id[i];	//	寻找连通分量id的最大值

	vector<int> transfer(n, 0); // 保存了最大连通分量中节点新分配的id与A中节点原始id的映射
	if (max_id != 0) {  // max_id == 0 means the network is not connected, i.e. all the nodes are isolated
		vector<int> count(max_id, 0);  // 存放每个连通分量中的节点个数
		for (int i = 0; i < n; i++)
			if (id[i] != 0)
				count[id[i] - 1]++;	// 编号为id[i]的连通分量中节点的个数

		int max_size = 0; // store the size of the cluster with most nodes  // 尺寸最大的连通分量的大小
		int max_cluser_id = 0; // store the id of the cluster with most nodes  // 尺寸最大的连通分量的id
		for (int i = 0; i < max_id; i++) // find the cluster with most nodes
			if (max_size < count[i]) {
				max_size = count[i];
				max_cluser_id = i + 1;
			}

		id_now = 0; // 给最大连通分量中的节点分配新的id
		for (int i = 0; i < n; i++) {
			if (id[i] == max_cluser_id)
				transfer[i] = ++id_now;  // 如果节点不在最大连通分量中，它的transfer值为0
		}
	}
	return transfer;
}

// return eigenvector
vector<double> power_iteration(vector<vector<int>*>* adj)
{
	std::mt19937 generator;
	std::uniform_real_distribution<double> distribution(-1.0, 1.0);

	vector<double> x(int(adj->size()));
	vector<double> y(int(adj->size()));
	int n = int(adj->size());

	for (int i = 0; i < n; ++i) {
		x.at(i) = distribution(generator);
		y.at(i) = distribution(generator);
	}

	int dmax = 0;
	for (int i = 0; i < n; ++i) {
		if (int(adj->at(i)->size()) > dmax) {
			dmax = int(adj->at(i)->size());
		}
	}
	for (int i = 0; i < 30 * log(n) * sqrt(log(n)); ++i) {

		multiplyByLaplacian(adj, &x, &y, dmax);
		multiplyByLaplacian(adj, &y, &x, dmax);
		orthonormalize(&x);
		//if (i % 30 == 0) cout << i << " -- " << 30 * log(n) * sqrt(log(n)) << endl;
	}

	return x;
}

// return eigenvector B = WA+AW-A
vector<double> power_iterationB(vector<vector<int>*>* adj) {
	std::mt19937 generator;
	std::uniform_real_distribution<double> distribution(-1.0, 1.0);

	vector<double> x(adj->size());
	vector<double> y(adj->size());
	vector<int> db(adj->size()); //	db对应论文中的D_B矩阵，是构成拉普拉斯矩阵的被减矩阵
	int n = int(adj->size());

	for (int i = 0; i < n; ++i) {
		x.at(i) = distribution(generator);
		y.at(i) = distribution(generator);
	}

	int dmax = 0;
	int dmax2 = 0;
	for (int i = 0; i < n; ++i) { // 此处需要推导
		db.at(i) = int(adj->at(i)->size()) * int((adj->at(i)->size() - 1));
		for (int j = 0; j < adj->at(i)->size(); ++j) {
			db.at(i) = db.at(i) + int(adj->at(adj->at(i)->at(j) - 1)->size());
		}
		if (adj->at(i)->size() > dmax) { // dmax存储了连通分量中所有节点的最大度数
			dmax = int(adj->at(i)->size());
		}
		if (db.at(i) > dmax2) {
			dmax2 = db.at(i); // dmax2存储了db矩阵中的最大非零值
		}
	}
	// db矩阵构造完毕
	dmax = dmax * dmax + dmax2;
	// 对应论文的Spectral Approximation部分的第（iii）步，推测在进行幂迭代
	for (int i = 0; i < 30 * log(n) * sqrt(log(n)); ++i) { // 30*log(n)*log(n)
		multiplyByWeightLaplacian(adj, &x, &y, &db, dmax);
		multiplyByWeightLaplacian(adj, &y, &x, &db, dmax);
		orthonormalize(&x);
	}

	// 返回参数adj的特征向量
	return x;
}

// return the removing order of the nodes: 1,2,3,... The node with flag=0 will not be removed
// Clarkson's Greedy Algorithm for weighted set cover
vector<int> vertex_cover(vector<vector<int>*>* A_cover, vector<int> degree) {
	vector <int> flag(int(A_cover->size()), 0);
	int remove = 0;
	int total_edge = 0;
	for (int i = 0; i < int(A_cover->size()); i++)
		total_edge += int(A_cover->at(i)->size());

	while (total_edge > 0) {
		vector<int> degree_cover(int(A_cover->size()), 0);
		for (int i = 0; i < int(A_cover->size()); i++)
			degree_cover[i] = int(A_cover->at(i)->size());

		vector<double> value(int(A_cover->size()), 0);
		for (int i = 0; i < int(A_cover->size()); i++)
			if (degree_cover[i] == 0)
				value[i] = 999999;
			else
				value[i] = double(degree[i]) / double(degree_cover[i]);

		double min_v = 999999;
		int min_sub = 0;
		for (int i = 0; i<int(value.size()); i++)
			if (min_v > value[i]) {
				min_v = value[i];
				min_sub = i;
			}
		flag[min_sub] = ++remove;
		A_cover->at(min_sub)->clear();
		for (int i = 0; i < int(A_cover->size()); i++)
			for (vector<int>::iterator it = A_cover->at(i)->begin(); it != A_cover->at(i)->end(); )
			{
				if (*it == min_sub + 1) {
					A_cover->at(i)->erase(it);
					it = A_cover->at(i)->begin();
				}
				else it++;
			}
		degree_cover[min_sub] = 0;
		total_edge = 0;
		for (int i = 0; i < int(A_cover->size()); i++)
			total_edge += int(A_cover->at(i)->size());
	}
	return flag;
}

// Comparing with vertex_cover, this function use the adaptive degree from the original network
// remove the node with min(degree/degree_cover) first
// return the removing order of the nodes: 1,2,3,... The node with flag=0 will not be removed
//该函数只标记顶点覆盖集中应该删除的节点，每轮迭代标记degree/degree_cover值最小的节点，将此节点删除后统计顶点覆盖集中边的数量，如果边数为0则结束迭代，否则继续迭代。
vector<int> vertex_cover_2(vector<vector<int>*>* A_cover, vector<vector<int>*>* A_new_gcc) {
	vector<vector<int>*>* A_new_gcc_copy = new vector<vector<int>*>(int(A_new_gcc->size()));
	for (int i = 0; i < int(A_new_gcc->size()); i++) {
		A_new_gcc_copy->at(i) = new vector<int>(int(A_new_gcc->at(i)->size()));
		for (int j = 0; j<int(A_new_gcc->at(i)->size()); j++) {
			A_new_gcc_copy->at(i)->at(j) = A_new_gcc->at(i)->at(j);
		}
	}

	vector <int> flag(int(A_cover->size()), 0); // store the cover (removal) order of each node: 1,2,3...
	int remove = 0;
	int total_edge = 0;  // the total number of edges in A_cover
	for (int i = 0; i < int(A_cover->size()); i++)
		total_edge += int(A_cover->at(i)->size()); //由于在构造顶点覆盖集时有条件(i + 1) < A_new_gcc->at(i)->at(j)，所以边数不除以2

	while (total_edge > 0) {
		vector<int> degree(int(A_new_gcc_copy->size()), 0);
		for (int i = 0; i < int(A_new_gcc_copy->size()); i++) {
			degree[i] = int(A_new_gcc_copy->at(i)->size());
		}
		vector<int> degree_cover(int(A_cover->size()), 0);
		for (int i = 0; i < int(A_cover->size()); i++)
			degree_cover[i] = int(A_cover->at(i)->size());

		vector<double> value(int(A_cover->size()), 0);
		for (int i = 0; i < int(A_cover->size()); i++)
			if (degree_cover[i] == 0)
				value[i] = 99999;
			else
				value[i] = double(degree[i]) / double(degree_cover[i]); // 将总度数与不在同一集合中的邻居数之比最小的节点从连通分量和顶点覆盖中删除，如果节点总度数
			//相同，优先删除不在同一集合中的邻居数大的节点；若不在同一集合中的邻居数相同，暂时认为删除谁都可以，可以先删除总度数小的，也可以先删除总度数大的。

		double min_v = 9999;
		int min_sub = 0;
		for (int i = 0; i<int(value.size()); i++)
			if (min_v > value[i]) {
				min_v = value[i];
				min_sub = i;
			}
		flag[min_sub] = ++remove;
		A_cover->at(min_sub)->clear();	//在顶点覆盖集中删除节点
		A_new_gcc_copy->at(min_sub)->clear(); //在最大连通分量中删除节点
		// 下面两个for循环在顶点覆盖集和最大连通分量中搜索每个节点的邻居中是否有已删除节点，如果有则将其删除
		for (int i = 0; i < int(A_cover->size()); i++)
			for (vector<int>::iterator it = A_cover->at(i)->begin(); it != A_cover->at(i)->end(); )
			{
				if (*it == min_sub + 1) {
					A_cover->at(i)->erase(it);
					it = A_cover->at(i)->begin();
				}
				else it++;
			}

		for (int i = 0; i < int(A_new_gcc_copy->size()); i++)
			for (vector<int>::iterator it = A_new_gcc_copy->at(i)->begin(); it != A_new_gcc_copy->at(i)->end(); )
			{
				if (*it == min_sub + 1) {
					A_new_gcc_copy->at(i)->erase(it);
					it = A_new_gcc_copy->at(i)->begin();
				}
				else it++;
			}

		// degree_cover[min_sub] = 0;
		// 统计删除节点后的边数量
		total_edge = 0;
		for (int i = 0; i < int(A_cover->size()); i++)
			total_edge += int(A_cover->at(i)->size());
	}
	return flag;
}

// Remove nodes from the network A_new according to flag. The removed nodes will be store in nodes_id
void remove_nodes(vector<vector<int>*>* A_new, vector<int> flag, vector<double>* y_gcc, vector<double>* x_links, vector<double>* x_nodes, vector<int>* nodes_id) {
	int removed_nodes = 0, removed_links = 0;
	if (y_gcc->size() != 0) {
		removed_nodes = x_nodes->back();
		removed_links = x_links->back();
	}

	bool flag_size = false; // continue to remove?
	int target = 0;
	for (int k = 0; k<int(flag.size()); k++) {
		if (flag[k] != 0) {   // set target as the first removed node
			flag_size = true; // continue to remove
			target = k;
			break;
		}
	}
	// 找到了一个要删除的节点
	while (flag_size) { // continue to remove?
		flag_size = false;
		if (REMOVE_STRATEGY == 1) { // weighted case: find the node with minimum degree
			for (int k = 0; k<int(flag.size()); k++) {
				if (flag[k] != 0 && A_new->at(k)->size() < A_new->at(target)->size()) // compare the degree	// 挑选度数最小的节点删除
					target = k;
			}
		}
		else if (REMOVE_STRATEGY == 3) { // unweighted case: find the node with maximum degree
			for (int k = 0; k<int(flag.size()); k++) {
				if (flag[k] != 0 && A_new->at(k)->size() > A_new->at(target)->size()) // compare the degree // 挑选度数最大的节点删除
					target = k;
			}
		}

		int i = target;
		vector<int> transfer = get_gcc(A_new);
		if (flag[i] > 0 && transfer[i] != 0) {  // remove one node if the node in the remove list && the node in the gcc
			nodes_id->push_back(i + 1);
			removed_nodes++;
			removed_links += int(A_new->at(i)->size());
			A_new->at(i)->clear();

			for (int j = 0; j < int(A_new->size()); j++) {
				for (vector<int>::iterator it = A_new->at(j)->begin(); it != A_new->at(j)->end(); ) {
					if (*it == i + 1) {
						A_new->at(j)->erase(it);
						it = A_new->at(j)->begin();
					}
					else it++;
				}
			}
			if (removed_nodes % PLOT_SIZE == 0) { // record
				vector<int> transfer = get_gcc(A_new); // transfer has the same size with A_new
				int gcc_size = 0;
				for (int i = 0; i < int(A_new->size()); i++)
					if (transfer[i] != 0)
						gcc_size++;
				// 下面的for循环是没用的代码
				int temp = 0;
				for (int k = 0; k<int(A_new->size()); k++) {
					if (A_new->at(k)->size() != 0) temp++;
				}

				y_gcc->push_back(gcc_size);
				x_links->push_back(removed_links);
				x_nodes->push_back(removed_nodes);
			}
		}
		flag[target] = 0;

		for (int k = 0; k<int(flag.size()); k++) {
			if (flag[k] != 0) {  // set the target as the first removed node
				flag_size = true; // continue to remove
				target = k;
				break;
			}
		}

		if (!flag_size) { // reach the end of this round
			vector<int> transfer = get_gcc(A_new); // transfer has the same with A_new
			int gcc_size = 0;
			for (int i = 0; i < int(A_new->size()); i++)
				if (transfer[i] != 0)
					gcc_size++;
			if (PLOT_SIZE != 1) {
				y_gcc->push_back(gcc_size);
				x_links->push_back(removed_links);
				x_nodes->push_back(removed_nodes);
			}

			std::cout << "gcc size after this round's partition - " << gcc_size << "\n";
		}
	}
}

// Output the list of nodes that should be removed in order
void write(vector<double>* y_gcc, vector<double>* x_links, vector<double>* x_nodes, vector<int>* nodes_id) {
	ofstream wt_id(KEY_NODE);
	if (!wt_id) std::cout << "error creating file...\n";

	for (int i = 0; i<int(nodes_id->size()); i++)
		wt_id << nodes_id->at(i) << endl;
	wt_id.close();
}

void release_memory(vector<vector<int>*>* adj) {
	for (int i = 0; i < adj->size(); ++i) {
		delete adj->at(i);
	}
}

JNIEXPORT jintArray JNICALL Java_edu_scu_csaserver_utils_KeyNode_getKeyNode
  (JNIEnv *env, jobject thiz, jint node_num, jobjectArray topo_links) {
    //**** read adjacecy matrix from file  ****
	vector<vector<int>*>* A = new vector<vector<int>*>(node_num); // 矩阵A存放了输入文件中原始图的邻接形式
	for (int i = 0; i<int(A->size()); ++i)
		A->at(i) = new vector<int>(); // 矩阵A的行表示节点id，列表示它邻居节点的id
//	FILE_NET = env->GetStringUTFChars(input_path,NULL);
//	KEY_NODE = env->GetStringUTFChars(output_path,NULL);
//	rdata(A); // 读取输入文件，将每个节点的邻居push_back到向量中

    // 我们不再使用文件交互数据，文件路径不好确定
    // 我们直接访问JNI二维输入
    input(A, env, topo_links);

	vector<int> transfer_initial = get_gcc(A); // the elements' number of transfer_initial equals the number of nodes in A
	// 根据原始矩阵A找出其中的最大连通分量并存储在transfer_initial中，transfer_initial的下标对应A中的节点id，值为最大连通分量的id
	double node_size = 0, link_size = 0;
	for (int i = 0; i<int(transfer_initial.size()); i++)
		if (transfer_initial[i] != 0)
			node_size++; // 最大连通分量的尺寸

	// A_new是A的最大连通分量对应的、使用新分配的节点id表示的邻接矩阵
	vector<vector<int>*>* A_new = new vector<vector<int>*>(node_size);
	for (int i = 0; i < node_size; i++)
		A_new->at(i) = new vector<int>();
	for (int i = 0; i < int(transfer_initial.size()); i++) // 遍历所有节点，注意transfer_initial的大小等于矩阵A的行数
		for (int j = 0; j < int(A->at(i)->size()); j++) { // 遍历某节点的邻居
			if (transfer_initial[A->at(i)->at(j) - 1] != 0) { // 如果该节点的邻居也在极大连通分量中
				A_new->at(transfer_initial[i] - 1)->push_back(transfer_initial[A->at(i)->at(j) - 1]); // 则将节点i,j在极大连通分量中的新编号存储在A_new矩阵中
				link_size++; // A的最大连通分量中边的数量
			}
		}
	link_size = link_size / 2; // 在遍历到节点i时，添加其邻居j增加了一次link_size，当遍历到节点j时，添加其邻居i又增加了一次link_size，故此处除以2
	std::cout << "total nodes: " << node_size << " total links: " << link_size << endl;


	//**** partation the network to subnets ****
	vector<double>* y_gcc = new vector<double>();
	vector<double>* x_links = new vector<double>();
	vector<double>* x_nodes = new vector<double>();
	vector<int>* nodes_id = new vector<int>(); // store the nodes that should be removed
	int gcc_size = int(A->size());
	while (gcc_size > TARGET_SIZE)
	{
		vector<int> transfer = get_gcc(A_new); // the elements' number of transfer equals the number of nodes in A
											   // if transter[i] = 0 then this node doesn't belong to the gcc
											   // if transter[i] != 0 then transter[i] is the new id of this node in A_new_gcc
		// transfer存储了A_new的最大连通分量中节点新分配的id与其在A_new中节点原始id的映射
		gcc_size = 0;
		for (int i = 0; i < int(A_new->size()); i++)
			if (transfer[i] != 0)
				gcc_size++;  // A_new最大连通分量的尺寸
		vector<int> transfer_back(gcc_size, 0);  // transfer的下标为节点原始id，值为节点新id；transfer_back下标为节点新id，值为节点原始id
		for (int i = 0; i < gcc_size; i++)
			for (int j = 0; j< int(A_new->size()); j++) {
				if (transfer[j] == i + 1) {
					transfer_back[i] = j + 1; //transfer数组和transfer_back数组是节点在原图中的原始编号和在gcc中的新编号的相互映射
					break;
				}
			}

		// 在A_new_gcc中使用新分配的节点id表示A_new最大连通分量的邻接矩阵
		vector<vector<int>*>* A_new_gcc = new vector<vector<int>*>(gcc_size);
		for (int i = 0; i < gcc_size; i++)
			A_new_gcc->at(i) = new vector<int>();
		for (int i = 0; i < int(transfer.size()); i++) {
			if (transfer[i] != 0) { // 节点i在A_new的最大连通分量中且分配了新的id
				for (int j = 0; j < int(A_new->at(i)->size()); j++) {
					if (transfer[A_new->at(i)->at(j) - 1] != 0)  //节点i的邻居也在A_new的最大连通分量中且分配了新的id
						A_new_gcc->at(transfer[i] - 1)->push_back(transfer[A_new->at(i)->at(j) - 1]);  //在A_new_gcc中用新分配的节点id存储它们的邻接关系
				}
			}
		}

		// 大致思路是先求最大连通分量，再使用新分配的节点id表示出最大连通分量中节点的邻接关系

		// compute the eigenvector and seperate set
		vector<double> eigenvector; // 特征向量
		if (REMOVE_STRATEGY == 1)
			eigenvector = power_iterationB(A_new_gcc);
		else if (REMOVE_STRATEGY == 3)
			eigenvector = power_iteration(A_new_gcc);

		vector<int> flag; // mark all the nodes that should be removed to partition the network into subnet
						  // flag: 0: do not remove; 1,2,3.. removal order
		if (REMOVE_STRATEGY == 1 || REMOVE_STRATEGY == 3) {  // Weighted Vertex Cover
			vector<vector<int>*>* A_new_gcc_cover = new vector<vector<int>*>(int(A_new_gcc->size())); // 加权顶点覆盖（顶点覆盖：对于一个无向图，一个顶点覆盖是
			// 一个顶点子集，使得每条边都至少有一个顶点属于该集合

			for (int i = 0; i < gcc_size; i++) {
				A_new_gcc_cover->at(i) = new vector<int>(); // the subnet that all the links in it should be covered
			}
			
			for (int i = 0; i<int(A_new_gcc->size()); i++)
				for (int j = 0; j < int(A_new_gcc->at(i)->size()); j++) {
					if ((i + 1) < A_new_gcc->at(i)->at(j) &&  // Prevention of repeated calculation
						eigenvector[i] * eigenvector[A_new_gcc->at(i)->at(j) - 1] < 0) {// these two nodes do not in the same cluster //v中元素的符号标识它们在哪个集合中，同号表示在相同集合，异号表示在不同集合
						A_new_gcc_cover->at(i)->push_back(A_new_gcc->at(i)->at(j));
						A_new_gcc_cover->at(A_new_gcc->at(i)->at(j) - 1)->push_back(i + 1);
					}
				}
			if (REMOVE_STRATEGY == 1) {
				flag = vertex_cover_2(A_new_gcc_cover, A_new_gcc); // flag marks all the nodes that should be removed to partition the network into subnet
			}
			else if (REMOVE_STRATEGY == 3) {
				vector<int> degree_one(int(A_new_gcc->size()), 1);
				flag = vertex_cover(A_new_gcc_cover, degree_one); // flag marks all the nodes that should be removed to partition the network into subnet
			}
		}

		// remove nodes
		// 由于vertex_cover_2是以A_new的最大连通分量为操作对象，删除其中的节点，现在需要删除A_new中对应的节点，思路是根据flag的节点序号找到节点在原图中的序号
		// （flag使用的是顶点覆盖集的节点序号，即最大连通分量的节点序号，它与原图的节点序号对应关系存储在transfer向量中，则通过transfer_back即可查找最大连通
		// 分量中的节点序号与原图中节点序号的映射，进而将原图中节点的flag变量赋值）
		vector<int> flag_orginal(int(A_new->size()), 0);
		for (int i = 0; i<int(flag.size()); i++)
			if (flag[i] != 0)
				flag_orginal[transfer_back[i] - 1] = flag[i];

		remove_nodes(A_new, flag_orginal, y_gcc, x_links, x_nodes, nodes_id);

		transfer = get_gcc(A_new);
		gcc_size = 0;
		for (int i = 0; i < int(A_new->size()); i++)
			if (transfer[i] != 0)
				gcc_size++;
	}

	for (int i = 0; i<int(y_gcc->size()); i++) {
		y_gcc->at(i) = y_gcc->at(i) / node_size;
		x_nodes->at(i) = x_nodes->at(i) / node_size;
		x_links->at(i) = x_links->at(i) / link_size;
	}

	// 这里我们直接返回节点数组，不再输出到文件
	int key_len = int(nodes_id->size());
	// 创建jint数组，然后填充到JNI一维数组中并返回
	jint node_temp[key_len];
	// 创建一维JNI数组用于返回
	jintArray res = env->NewIntArray(key_len);
	// 填充数据
	for (int i = 0; i < key_len; i++) {
	    node_temp[i] = nodes_id->at(i);
	}
    // 设置一维JNI数组并返回
    env->SetIntArrayRegion(res, 0, key_len, node_temp);
//	write(y_gcc, x_links, x_nodes, nodes_id); // output the list of nodes that should be removed

	release_memory(A);
	A->clear();

	release_memory(A_new);
	A_new->clear();

	// 返回关键节点id数组
	return res;
}