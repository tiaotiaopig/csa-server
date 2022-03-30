#!/usr/bin/env python
# coding: utf8

# *****************************************************
# CAUTION: you have to install tshark tool
# *****************************************************

import os
import time, datetime
import struct
import sys, os

tshark_path = "D:/wireshark/tshark.exe"


def Path_judgement():
    if not os.path.exists(tshark_path):
        print("Sorry: tshark not found in path: %s" % tshark_path)
        print(
            "Maybe you did not install tshark. Or you should change this source file at LINE 11 to tell me the right installed path.")
        sys.exit(-1)
    if len(sys.argv) < 2:
        print("Usage: python generate_metadata_from_pcap.py <pcap file> <metadata_dir>")
        sys.exit(-1)
    in_path = sys.argv[1]
    if not os.path.exists(in_path):
        print("Usage: python generate_metadata_from_pcap.py <pcap file> <   >")
        print("Error: not found file %s" % in_path)
        sys.exit(-1)
    out_dir = sys.argv[2]
    print(out_dir)
    if not os.path.exists(out_dir):
        print("Usage: python generate_metadata_from_pcap.py <pcap file> <metadata_dir>")
        print("Error: not found dir %s" % out_dir)
        sys.exit(-1)
    tmp_dir = "./src/main/resources/analyse/"
    out_path = os.path.join(out_dir, os.path.basename(in_path) + ".txt")
    return in_path, out_dir, tmp_dir, out_path


def Tshark_analysis(in_path, out_dir, tmp_dir, out_path):
    os.system(tshark_path + "  -T fields -E separator=\"^\" "
                            # "-e frame.cap_len "
                            "-e frame.protocols "
                            
                            "-r %s  >%s/tsharkResult.txt" % (in_path, tmp_dir))


def Pcap_analysis(in_path, out_dir, tmp_dir, out_path):
    # 读取pcap文件，解析相应的信息，为了在记事本中显示的方便。
    payloadResultwithBlank = ("%s/payloadResultwithBlank.txt" % tmp_dir)
    fpcap = open(in_path, 'rb')
    ftxt = open(payloadResultwithBlank, 'w')
    string_data = fpcap.read()
    # pcap文件包头解析
    # Magic：4B：0×1A 2B 3C 4D:用来识别文件自己和字节顺序。0xa1b2c3d4用来表示按照原来的顺序读取，0xd4c3b2a1表示下面的字节都要交换顺序读取。一般，我们使用0xa1b2c3d4
    # Major：2B，0×02 00:当前文件主要的版本号
    # Minor：2B，0×04 00当前文件次要的版本号
    # ThisZone：4B 时区。GMT和本地时间的相差，用秒来表示。如果本地的时区是GMT，那么这个值就设置为0.这个值一般也设置为0
    # SigFigs：4B时间戳的精度；全零
    # SnapLen：4B最大的存储长度（该值设置所抓获的数据包的最大长度，如果所有数据包都要抓获，将该值设置为65535； 例如：想获取数据包的前64字节，可将该值设置为64）
    # LinkType：4B链路类型
    pcap_header = {}
    pcap_header['magic_number'] = string_data[0:4]
    pcap_header['version_major'] = string_data[4:6]
    pcap_header['version_minor'] = string_data[6:8]
    pcap_header['thiszone'] = string_data[8:12]
    pcap_header['sigfigs'] = string_data[12:16]
    pcap_header['snaplen'] = string_data[16:20]
    pcap_header['linktype'] = string_data[20:24]

    step = 0
    packet_num = 0
    packet_data = []
    pcap_packet_header = {}
    i = 24

    # 16个字节的数据报报头
    # Timestamp：时间戳高位
    # Timestamp：时间戳地位
    # Caplen：当前数据区的长度
    # Len：离线数据长度

    if struct.unpack('I', pcap_header['magic_number'])[0] == 2712847316:
        while (i < len(string_data)):
            # 数据包头各个字段
            pcap_packet_header['GMTtime'] = string_data[i:i + 4]
            pcap_packet_header['MicroTime'] = string_data[i + 4:i + 8]
            pcap_packet_header['caplen'] = string_data[i + 8:i + 12]
            pcap_packet_header['len'] = string_data[i + 12:i + 16]
            # print(pcap_packet_header['len'])
            # 求出此包的包长len
            # packet_len = int(pcap_packet_header['len'], 16)
            # packet_len2 = struct.pack('B', pcap_packet_header['len'])
            # packet_len = int(packet_len2, 16)
            packet_len = struct.unpack('I', pcap_packet_header['caplen'])[0]
            # print(hex(packet_len))
            # 写入此包数据
            packet_data.append(string_data[i + 16:i + 16 + packet_len])
            i += packet_len + 16
            packet_num += 1

    elif struct.unpack('I', pcap_header['magic_number'])[0] == 3569595041:
        while (i < len(string_data)):
            # 数据包头各个字段
            pcap_packet_header['GMTtime'] = string_data[i:i + 4]
            pcap_packet_header['MicroTime'] = string_data[i + 4:i + 8]
            pcap_packet_header['caplen'] = string_data[i + 8:i + 12]
            pcap_packet_header['len'] = string_data[i + 12:i + 16]
            # print(pcap_packet_header['len'])
            # 求出此包的包长len
            # packet_len = int(pcap_packet_header['len'], 16)
            # packet_len2 = struct.pack('B', pcap_packet_header['len'])
            # packet_len = int(packet_len2, 16)

            packet_len = struct.unpack('!I', pcap_packet_header['caplen'])[0]
            # print(hex(packet_len))
            # print(packet_len)
            # 写入此包数据
            packet_data.append(string_data[i + 16:i + 16 + packet_len])
            i += packet_len + 16
            packet_num += 1

    # 把pcap文件里的数据包信息写入result.txt
    for i in range(packet_num):
        ftxt.write(''.join('%02x' % x for x in packet_data[i]) + '\n')

    ftxt.close()  # payloadResultwithBlank.txt
    fpcap.close()

    infp = open(payloadResultwithBlank, "r")
    payloadResultOver = ("%s/payloadResultOver.txt" % tmp_dir)
    outfp = open(payloadResultOver, "w")
    lines = infp.readlines()
    for li in lines:
        if li.split():
            outfp.writelines(li)
    infp.close()
    outfp.close()

    def copyTimeMetadata(string):
        string = string.split('^')
        string.insert(11, string[11])
        return string

    payloadFile = open("%s/payloadResultOver.txt" % tmp_dir)
    tsharkFile = open("%s/tsharkResult.txt" % tmp_dir)
    tsharkData = []
    payload = []
    meteData = []

    for line in tsharkFile:
        line = line.replace("\n", "")
        line = copyTimeMetadata(line)
        tsharkData.append(line)

    for line in payloadFile:
        line = line.replace("\n", "")
        payload.append(line)

    count1 = len(payload)
    for i in range(0, count1):
        tsharkData[i].insert(80, payload[i])  # 第80字段装data
        if (tsharkData[i][76] == "<Root>"):
            tsharkData[i][76] = tsharkData[i][54]

    meteDataWithPayload = open("%s/meteDataWithPayload.txt" % tmp_dir, 'w')
    for line in tsharkData:
        meteDataWithPayload.write("^".join(line) + "\n")
        # meteDataWithPayload.write(",".join(line)+"\n")
    finallyMetedata = []
    dataListFromQuery = []
    dataListFromRespon = []
    QueriesName_map = {}
    DNSQueryName = 55 - 1
    destPort = 6 - 1
    DNSDelay = 0

    with open("%s/meteDataWithPayload.txt" % tmp_dir) as f:
        lines = f.readlines()
        for index, line in enumerate(lines):
            line = line.replace("\n", "")
            dataFromQuery = line.split("^")
            if 89 < len(dataFromQuery) and dataFromQuery[destPort] == "53":  # 此时是请求报文，合并到请求报文中
                dataListFromQuery.append(dataFromQuery)  # dataListFromQuery列表保存的全是请求字段
                QueriesName = dataFromQuery[DNSQueryName]
                QueriesName_map[QueriesName] = index
        count = len(QueriesName_map)  # 计算总共多少条请求报文
        for line in lines:
            dataFromRespon = line.split("^")
            if 89 < len(dataFromRespon) and dataFromRespon[destPort] != "53":
                NAME = dataFromRespon[DNSQueryName]  # 响应报文中的域名
                if (NAME in QueriesName_map):
                    for i in range(0, count):
                        if dataListFromQuery[i][DNSQueryName] == NAME:
                            dataListFromQuery[i][12] = dataFromRespon[12]
                            dataListFromQuery[i][53] = dataFromRespon[53]
                            dataListFromQuery[i][57] = dataFromRespon[57]
                            dataListFromQuery[i][58] = dataFromRespon[58]
                            dataListFromQuery[i][89] = dataFromRespon[89]
                            DNSDelay = (float(dataListFromQuery[i][12]) - float(dataListFromQuery[i][11])) * 1000000
                            dataListFromQuery[i][57] = str(DNSDelay)
                else:
                    print("warning: The response message could not find the requested message", line)
                    pass

    meteDataFile = open(out_path, 'w')
    for line in dataListFromQuery:
        if line[53] != "":
            line[59] = line[59].replace(",", ";")
            meteDataFile.write("^".join(line) + "\n")
    meteDataFile.close()

    print("%s generated from pcap file %s OK." % (out_path, in_path))


if __name__ == "__main__":
    try:
        in_path, out_dir, tmp_dir, out_path = Path_judgement()
        Tshark_analysis(in_path, out_dir, tmp_dir, out_path)
        Pcap_analysis(in_path, out_dir, tmp_dir, out_path)

    except Exception as e:
        print('[Error]:\n', e)
