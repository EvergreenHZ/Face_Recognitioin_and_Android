import cv2
import leancloud
import face_recognition
import os
from datetime import datetime
from StringIO import StringIO
import numpy as np
import urllib


# login
leancloud.init("mwORjkJ3JMiewYfCelSfEdqJ-gzGzoHsz", "3bS3E5dp0mst4nOJcYDDENRk")
class DetectedFacesTable(leancloud.Object):  # get image
    pass

class visitRecord(leancloud.Object):  # upload result
    pass
# Load a sample picture
master_image = face_recognition.load_image_file("../res/obama.jpg")
master_face_encoding = face_recognition.face_encodings(master_image)[0]

# Initialize some variables
face_locations = []
face_encodings = []

last = 0
g_count = 0
while True:
    todo = leancloud.Object.extend('DetectedFacesTable')  # create the table obj
    query = todo.query  # get a query obj
    query_list = query.greater_than('num', last).find()  # set conditions and find records
    length = len(query_list)
    print 'keep querting'
    if (length == 0):
        continue
    last = query_list[length - 1].get('num')  # mark, last
    print 'start recognition'

    for result in query_list:  # for each image
        image = result.get('image')
        url = image.url
        req = urllib.urlopen(url)
        arr = np.asarray(bytearray(req.read()), dtype=np.uint8)
        face = cv2.imdecode(arr, -1)
        # now I get the image: face
        
        name = 'unknown'
        face_locations = face_recognition.face_locations(face)
        face_encodings = face_recognition.face_encodings(face, face_locations)

        for face_encoding in face_encodings: # actually, only one face
            # See if the face is a match for the known face(s)
            match = face_recognition.compare_faces([master_face_encoding], face_encoding)
            name = "unknown"

            if match[0]:  # 
                name = "master"

        g_count = g_count + 1
        Todo = visitRecord()
        Todo.set('name', name)
        mbuffer = cv2.imencode('.jpg', face)[1].tostring()
        recoged_face = leancloud.File('image.jpg', StringIO(mbuffer))
        t_time = str(datetime.now()).split('.')[0]
        Todo.set('visittime', t_time)
        Todo.set('image', recoged_face)
        Todo.set('num', g_count)
        Todo.save()
        
        cv2.imshow('face', face)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
