This is a project of my Android homework.

## Requirements:
1. Rasberry Pi 3 (with camera module)
2. Android Phone
3. dlib
4. leancloud

## Introduction:
This is a very naive system divided into three parts:
1. RPi captures frames, detects faces and uploads to leancloud.
2. PC pulls the faces, and executes face recognition via deep learning and uploads the results to leancloud.
3. Android Phone pulls the results and show the information.

## Installation:
1. go to this page: https://github.com/ageitgey/face_recognition and install dlib, face_recognition
2. go to this page: https://leancloud.cn/docs/demo.html for more information about leancloud.
