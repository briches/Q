import urllib2
import urllib

url = "http://192.168.1.190/push.php?"

values = {'sn': sys.argv[1],
	'count' : sys.argv[2],
	'delta' : sys.argv[3]}

data = urllib.urlencode(values)

req = urllib2.Request(url, data)

response = urllib2.urlopen(req)

print response.read() 
