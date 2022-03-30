import pandas as pd
import os
import sys
import math
from redis import ConnectionPool, StrictRedis
import json


if (len(sys.argv)<2):
    print('arg error')
    sys.exit(-1)


os.system("python ./generate_data_from_pcap3.0.py ./target/classes/public/pcap/"+sys.argv[1])
# os.system("python ./generate_data_from_pcap3.0.py "+sys.argv[1])

file_2 = "./target/classes/analyse/payloadResultOver.txt"
file_1 = "./target/classes/analyse/tsharkResult.txt"
# target_file = "./target/classes/analyse/out.json"

data_1 = pd.read_csv(file_1, sep="^", names=["src", "macSrc", "dst", "macDst","protocol","len"],keep_default_na=False)
data_2 = pd.read_csv(file_2, names=["data"])["data"]
data_1.insert(0,"num", range(len(data_1)), allow_duplicates=True)
data_1.insert(data_1.shape[1], "data", data_2)
json_str = data_1.to_json(orient='records' ,date_format='iso')

parsed = json.loads(json_str)
data_1.to_json(target_file, indent=2, orient="records")

#
# pool = ConnectionPool(host='192.168.50.17', port=6379, decode_responses=True)
# rdb = StrictRedis(connection_pool=pool)
# rdb.hset('unnamedProtocol',sys.argv[1],str(parsed))