# import pymysql
import json
import requests
import os


def getResult():
    filename = ''
    os.system("python ./generate_data_from_pcap3.0.py ./1.pcap ./")


def getJson():
    with open('./src/main/resources/analyse/tsharkResult.txt', encoding='utf-8') as f:
        data = f.readlines()
        show = []
        result = []

        num = []
        src = []
        dst = []
        protocol = []
        length = []
        mac_src = []
        mac_dst = []
        frame = []
        cdp_router = []
        ospf_lsa = []

        for i in data:
            result.append(i)
        for j in range(len(result)):
            num.append(j + 1)
            if result[j].split('^')[0] == '':
                src.append('None')
            else:
                src.append(result[j].split('^')[0])

            if result[j].split('^')[1] == '':
                dst.append('None')
            else:
                dst.append(result[j].split('^')[1])

            if result[j].split('^')[2] == '':
                protocol.append('None')
            else:
                protocol.append(
                    result[j].split('^')[2].replace('eth:', '').replace('ethertype:', '').replace(':',
                                                                                                  ' ').replace(
                        'x509sat', '').replace('x509ce', '').replace('pkix1explicit', '').replace(
                        'pkix1implicit', ''))

            if result[j].split('^')[3] == '':
                length.append('None')
            else:
                length.append(result[j].split('^')[3].replace('\n', ''))

            if result[j].split('^')[4] == '':
                mac_src.append('None')
            else:
                mac_src.append(result[j].split('^')[4].replace('\n', ''))

            if result[j].split('^')[5] == '':
                mac_dst.append('None')
            else:
                mac_dst.append(result[j].split('^')[5].replace('\n', ''))

#             if result[j].split('^')[6] == '':
#                 cdp_router.append('None')
#             else:
#                 cdp_router.append(result[j].split('^')[6].replace('\n', ''))
#             if result[j].split('^')[7] == '' or result[j].split('^')[7] == '\n':
#                 ospf_lsa.append('None')
#             else:
#                 ospf_lsa.append(result[j].split('^')[7].replace('\n', ''))

    for j in range(len(num)):
        show.append({
            'num': num[j],
            'src': src[j],
            'macSrc': mac_src[j],
            'dst': dst[j],
            'macDst': mac_dst[j],
            'protocol': protocol[j],
            'len': length[j],

            # 'CDP_Router': cdp_router[j],
            # 'OSPF_LSA': ospf_lsa[j]
        })
    print(cdp_router)
    print(ospf_lsa)
    return show

#
# def getEthDeposit():
#     show = getJson()
#     jsonData = []
#     for i in range(len(show)):
#         jsonData.append(show[i])
#         jsond = json.dumps(show[i], ensure_ascii=False)
#         # print(jsond)
#         post_headers = {'Content-Type': 'application/json', "Accept": "*/*"}
#         response = requests.post("http://localhost:8000/index", data=jsond, headers=post_headers)
#         # print(response)
#         # print(response.text)


if __name__ == '__main__':
    getResult()
    show = getJson()
    with open('tmp.json','w') as f:
        f.write(str(show))
#     getEthDeposit()
