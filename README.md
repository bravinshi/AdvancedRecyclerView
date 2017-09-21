# AdvancedRecyclerView
RecyclerView with refresh animation and load animation

# two style 


## ItemDecoration(foreground)

refresh
![](https://github.com/bravinshi/bravinTest/raw/master/source/a1-1.gif) 


load


![](https://github.com/bravinshi/bravinTest/raw/master/source/a3.gif) 


## Drawable(background)


refresh


![](https://github.com/bravinshi/bravinTest/raw/master/source/a4.gif) 


load


![](https://github.com/bravinshi/bravinTest/raw/master/source/a5.gif) 



#动画支持打断  support interruption 在动画从最低（高）点回弹但是还没刷新（加载）或者已经刷新（加载）完成然后回弹到起始位置时，动画支持打断。

![](https://github.com/bravinshi/bravinTest/raw/master/source/a7.gif) 


鉴于需求不同，你可能需要自定义效果，如果想实现前景效果，你可以自定义一个类，继承项目中的CustomItemDecor来实现
由于ItemDecoration需要配合RecylerView使用，我把ItemDecoration需要的数据直接放在自定义的RecylerView里了，没有做解耦，
所以自定义类可能需要作为AdvancedRecylerView的内部类实现了。

For different requirements，u can realize another animation with self-defining a class extending CustomItemDecor(foreground)
u should not use ItemDecoration without RecylerView,in consideration of this,ur custom class shoule be inner class of AdvancedRecylerView


实现背景效果可以自定义继承AdvancedDrawable的类

self-defining a class extending AdvancedDrawable(background)


另外要感谢一下Phoenix(by Yalantis)这个项目，因为里面的资源是直接拿来用的。你问我为什么？？？想必。。。大概。。。是因为。。。我懒吧。

I wanna thank Yalantis for project Phoenix,i used its sources.u asked me why??? I am lazy~!