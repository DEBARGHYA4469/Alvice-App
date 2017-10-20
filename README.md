<html>

<h2>ALVICE</h2>

This is an Android App Built as a project for <b>Microsoft code.fun.do</b>.This app is built in <b>Android Studio/Java</b>.
The app takes a picture from a live android phone Camera,processes it with the help of <a href="https://azure.microsoft.com/en-in/services/cognitive-services/computer-vision/"><b>Computer Vision API of Microsoft</a>.
  
The app can send the extracted string through query and get wikipedia summary of the text from <b>Flask-</b>powered WebApp speacially designed for this purpose.

The APK of file can be downloaded from this <a href="https://drive.google.com/open?id=0B0CwmRhMKkKXanRsc0I4Y1VkLTA">drive</a>



<h2>Bugs and Crash Issues</h2></ul>

Due to shortage of time in developing this project there are few bugs in the code and crashing issues involved.Hope to clear the bugs with the next commit.
The app runs best in <b>Potrait</b>Mode of Android.It crashes due to slow or unstable network problems.The next commit would perhaps try to come up with network checks and handle the runtime errors.
Also conversion from the image to bitmap was done crudely..so it fails to detect low resolution and low light texts....The app works perfectly fine for typed-fonts but fails quite often with handwritten texts due to poor conversion code to bitmap.Hope next commit brings a perfect way of identifying data from any real objects. 

</html>
