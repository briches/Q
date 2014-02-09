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

# Because the camera does not seem to be working out, we generate random data
# to use to test the windows/android/ios app
dl = 100
count = [[0 for i in xrange(4)] for i in xrange(dl)]
delta = [[0 for i in xrange(4)] for i in xrange(dl)]
for i in range(0,3):
	for j in range(0,dl):
		count[j][i] = (randint(1,10))
		delta[j][i] = (uniform(0,1))

while 1:
	for i in range(1,dl):
		time.sleep(3)
		print postRequest("00000002", str(count[i][1]), str(delta[i][1]))
		time.sleep(3)
		print postRequest("00000001", str(count[i][0]), str(delta[i][0]))
		time.sleep(3)
		print postRequest("00000003", str(count[i][2]), str(delta[i][2]))
		time.sleep(3)
		print postRequest("00000004", str(count[i][3]), str(delta[i][3]))
	
