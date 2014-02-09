filename = "data.txt"
f = open(filename, 'a')
tp = 35 #This many data before the direction is reversed
count = 0
dir = 1

for i in range(0, tp*4):
	f.write(str(count))
	count += 1 * dir
	if(count==tp-1):
		dir*=-1
	if(count==1): 
		dir*=-1
	f.write(chr(12)) # I don't have the stupid backslash character in this charset
f.close()

