import urllib2
import urllib
import sys
import time
import random
from random import randint
from random import uniform

def postRequest(sn, count, delta):
	url = "http://192.168.1.190/push.php?"
	values = {'sn':sn, 'count':count,'delta':delta}
	data = urllib.urlencode(values)
	req = urllib2.Request(url, data)
	response = urllib2.urlopen(req)
	result = response.read()
	response.close()

	return result

useRandData = 1

num_sn = int(sys.argv[1])
currentID = 0;

sn = dict([('nullf','nulld')])
for i in range(0, num_sn):
	currentID += 1
	key = '0000000'+str(currentID)
	key = str(key[8-len(key):])
	sn[key] = {'count':0,'delta':0} 

myKeys = sn.keys()


		
while 1:
	# Because the camera does not seem to be working out, we can generate random data
	# to use to test the windows/android/ios app
	if(useRandData==1):
		for i in range(0, num_sn):
			sn[myKeys[i]]['count'] = randint(1,10)
			sn[myKeys[i]]['delta'] = uniform(0,1)	
	currentID = 0
	for i in range(0, num_sn):
		currentID += 1
		key = '0000000'+str(currentID)
		key = str(key[8-len(key):])
		print postRequest(key, sn[key]['count'], sn[key]['delta'])
		time.sleep(3)
	
