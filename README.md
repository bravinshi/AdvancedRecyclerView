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



#����֧�ִ��  support interruption �ڶ�������ͣ��ߣ���ص����ǻ�ûˢ�£����أ������Ѿ�ˢ�£����أ����Ȼ��ص�����ʼλ��ʱ������֧�ִ�ϡ�

![](https://github.com/bravinshi/bravinTest/raw/master/source/a7.gif) 


��������ͬ���������Ҫ�Զ���Ч���������ʵ��ǰ��Ч����������Զ���һ���࣬�̳���Ŀ�е�CustomItemDecor��ʵ��
����ItemDecoration��Ҫ���RecylerViewʹ�ã��Ұ�ItemDecoration��Ҫ������ֱ�ӷ����Զ����RecylerView���ˣ�û�������
�����Զ����������Ҫ��ΪAdvancedRecylerView���ڲ���ʵ���ˡ�

For different requirements��u can realize another animation with self-defining a class extending CustomItemDecor(foreground)
u should not use ItemDecoration without RecylerView,in consideration of this,ur custom class shoule be inner class of AdvancedRecylerView


ʵ�ֱ���Ч�������Զ���̳�AdvancedDrawable����

self-defining a class extending AdvancedDrawable(background)


����Ҫ��лһ��Phoenix(by Yalantis)�����Ŀ����Ϊ�������Դ��ֱ�������õġ�������Ϊʲô��������ء�������š���������Ϊ�����������ɡ�

I wanna thank Yalantis for project Phoenix,i used its sources.u asked me why??? I am lazy~!