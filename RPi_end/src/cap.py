# import the necessary packages
from picamera.array import PiRGBArray
from picamera import PiCamera
import time
import cv2
import leancloud
from datetime import datetime
from StringIO import StringIO
 
def mmax(a, b):
    if a > b:
        return a
    else:
        return b

def mmin(a, b):
    if a < b:
        return a
    else:
        return b;
# login
leancloud.init("mwORjkJ3JMiewYfCelSfEdqJ-gzGzoHsz", "3bS3E5dp0mst4nOJcYDDENRk")
class DetectedFacesTable(leancloud.Object):
    pass


face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml') # init classifier

# initialize the camera and grab a reference to the raw camera capture
camera = PiCamera()
camera.resolution = (480, 320)
camera.framerate = 32
rawCapture = PiRGBArray(camera, size=(480, 320))

# allow the camera to warmup
time.sleep(0.1)
 
count = 1
# capture frames from the camera
for frame in camera.capture_continuous(rawCapture, format="bgr", use_video_port=True):
        image = frame.array
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)  # get gray scale image
        height, width, channels = gray.shape

        faces = face_cascade.detectMultiScale(gray, 1.3, 5)  # detect the face
        num_of_faces = len(faces)
        if (num_of_faces == 0):
            cv2.imshow("Frame", image)
        else:
            print 'face detected'
            for (x, y, w, h) in faces:
                x_l_min = mmax(0, (int)(x - 0.3 * w))
                y_l_min = mmax(o, (int)(y - 0.3 * h))
                x_r_max = mmin((int)(x + 1.3 * w), width)
                y_r_max = mmin((int)(y + 1.3 * h), height)
                face = image[y_l_min:y_r_max, x_l_min:x_r_max]  # get a face, bigger
                mbuffer = cv2.imencode('.jpg', face)[1].tostring()
                todo = DetectedFacesTable()
                t_face = leancloud.File('image.jpg', StringIO(mbuffer))
                todo.set('image', t_face)
                todo.set('num', count)
                count = count + 1
                todo.save()

        cv2.imshow("Frame", image)
        # for next frame
	key = cv2.waitKey(1) & 0xFF
	rawCapture.truncate(0)
	if key == ord("q"):
            break
