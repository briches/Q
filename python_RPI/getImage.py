import picamera
import os
from SimpleCV import * 
from time import sleep



camera = picamera.PiCamera()
camera.capture('image.jpg')


img = Image('image.jpg')

print img.listHaarFeatures()

faces = img.findHaarFeatures('upper_body.xml')

matches = 0

if faces:
	for i in faces:
		matches += 1
	
	print matches

	faces = faces.sortArea()

	bigFace = faces[-1]

	bigFace.draw()

	img.show()

	time.sleep(20)

os.remove('image.jpg')