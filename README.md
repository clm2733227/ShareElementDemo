# ShareElementDemo
1、此处有两种图片资源，recylerview显示的为缩略，viewpager中显示的为详情图。</br>
2、recyclerview跳转viewpager中，share element动画实现。图片框架Glide。</br>
3、viewpager加载页面，先用缩略图drawable作为placeholder，实现模糊过渡，再显示大图的效果。</br>
4、开始过渡动画前，必须确保viewpager中的页面已经加载完成，否则会造成动画跳动。</br>
5、关闭页面前，需要始终保证viewpager中有且仅有一个数据设置有share element name，如果有多个的话会造成图片错位。

![image](https://github.com/clm2733227/ShareElementDemo/blob/master/app/gif/animation.gif ) 
