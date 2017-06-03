# ShareElementDemo

总结</br>
1、此处有两种图片资源，recylerview显示的为缩略，viewpager中显示的为详情图。</br>
2、recyclerview跳转viewpager中，share element动画实现。图片框架Glide。</br>
3、viewpager加载页面，先用缩略图drawable作为placeholder，实现模糊过渡，再显示大图的效果。</br>
4、开始过渡动画前，必须确保viewpager中的页面已经加载完成，否则会造成动画跳动。</br>
5、关闭页面前，需要始终保证viewpager中有且仅有一个数据设置有share element name，如果有多个的话会造成图片错位。
</br>
</br>
summary</br>
1.There are two kinds of pictures in this demo.Recyclerview shows the thumbnail and the viewpager shows the original pictures.</br>
2.use Glide 4.0.0 as pictures loader.</br>
3.when loading the viewpager pictures, use the thumbnail already loaded in recyclerview as the placeholder,and then shows the original picture.</br>
4.Before the animation begins, make sure that the viewpager imageview has already layout completed, or it will frame skip.</br>
5.Before finishing the viewpager activity,make sure there is only one imageview had set the share element name,or it will occur pictures malposition. </br>
</br>
</br>
</br>
![image](https://github.com/clm2733227/ShareElementDemo/blob/master/app/gif/animation.gif ) 
</br>
