RxJava正在Android开发者中变得越来越流行。同时越来越多的项目开始引入这个开源项目。目前RxJava的版本已经更新到2.x版本，但是这篇文章是基于RxJava 1.x版本来写。下面记录了我怎么学习RxJava 1.x。

## RxJava是什么?
>RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.（一个在Java  VM上使用可观测序列组成异步和基于事件的程序的库。）

以上是Rxjava在Github主页的描述.然而，对于初学者并不太理解。[扔物线][1]大神用一个词总结Rxjava,那就是**异步**。大神认为它就是一个实现异步操作的库，而别的定语都是基于这个之上。这时，你可能会跟我一样有异步不是已经有Handler、AsynTask实现了，为什么还要用它的想法。

## RxJava的优势
「简洁，随着程序逻辑变得越来越复杂，它依然能够保持简洁」。逻辑的简洁，不是单纯代码量的少，RxJava的是一条从上到下的链式调用，没有任何嵌套，这在逻辑的简洁性上是具有优势的。Rxjava好在把什么复杂逻辑都能穿成一条线的简洁。

## 扩展的观察式模式
RxJava的异步实现，是通过一种扩展的观察者模式来实现的。

观察者模式不需要时刻盯着被观察者（例如定时检测被观察者），而是采用**注册**或者订阅的方式，告诉观察者：我需你的的某个状态，你要在它变化的时候通知我。Android开发经典的例子是点击监听器OnClickListener。其中View就是被观察者，onClickListener是观察者，通过setOnClickListener()方法订阅。当订阅成功后点击View时，Android Framework将点击事件通知给已订阅成功的OnClickListener（观察者）。采样这种方式，不需要实时检测被观察者的状态。

RxJava使用的就是类似这种形式的观察者模式。

### RxJava的观察者模式
RxJava有以下四个基本的概念：
 1. obserable: 被观察者
 2. Observer: 观察者
 3. subscribe：订阅
 4. 事件
 
Observer通过subscribe订阅obserable,当obserable需要的时候发出事件来通知Observer。

RxJava的事件回调除了onNext()方法之外，还有两个比较特殊的事件：onCompleted()和onError()。

- onCompleted()是在没有onNext（）事件都回调完成之后调用。
- onError() 是事件队列发生异常时调用，并终止所有事件发出。

onCompleted()和onError()只出现在事件队列一次并且**两个之中仅出现其中一个**。

Rxjava的观察者模式以下图：

![](http://7xrnko.com1.z0.glb.clouddn.com/QQ20170612-231115.png)


### RxJava的基本实现

- 1) 创建Observer(就是观察者)，它具体实现事件的行为。RxJava中的Observer接口实现方式如下：
```java
Observer<String> observer = new Observer<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};
```
除了Observer接口外，RxJava还内置了一个实现Observer的抽象类Subscriber。他们两个基本的使用方式是完全一样的。
```java
Subscriber<String> subscriber = new Subscriber<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};
```

- 2) 创建Observable(就是被观察者)，它决定什么时候触发事件以及触发怎样的事件。RxJava使用onCreate()方法来创建一个Observable。
```java
Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
    @Override
    public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("Hello");
        subscriber.onNext("Hi");
        subscriber.onNext("Aloha");
        subscriber.onCompleted();
    }
});
```

- 3) Subsribe(订阅)
```java
mObservable.subscribe(mObserver);
```

运行以下代码结果如下：
![](http://7xrnko.com1.z0.glb.clouddn.com/QQ20170613-000331.png)

以上就写到这里，接下来继续学习线程的控制Scheduler、变换和RxJava 的适用场景和使用方式。

  [1]: https://github.com/rengwuxian
  
  
# License

    Copyright 2017 zhonghanwen
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.  
