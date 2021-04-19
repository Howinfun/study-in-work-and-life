
# 一、设计模式为啥老是用不好？

想要写出更屌的代码，提高代码的健壮性和可扩展性，那么设计模式可谓是必学的技能。

关于学习设计模式，大家可能都觉得设计模式的概念太过于抽象，理解起来有点费劲；又或者看的时候是理解了，但是写起代码时，却毫无头绪，压根不知道可以套用哪个设计模式。

对，可以看到我使用了 “套” 这个字眼，正是因为我们无法深入理解设计模式的设计理念和使用场景，所以我们往往是想让我们的代码套用设计模式，而不理会业务场景是否合适。

关于设计模式的学习，我不会推荐任何书，因为我自己也没看过，哈哈哈。我看过的是龙哥的设计模式系列文章，里面的文章不但会介绍设计模式的概念，也会用非常有趣的场景去讲解设计模式的设计理念，下面先分享一波链接：[龙哥设计模式全集](https://blog.csdn.net/zuoxiaolong8810/category_1434962.html)。

对于我自己而言，关于设计模式的使用，除非是非常深刻的理解了，又或者某种设计模式的使用场景非常的清晰明确（例如创建型设计模式中的单例模式、结构型设计模式中的组合模式、行为型设计模式中的策略模式等等），不然我也不知道该如何使用，和什么时候使用。

# 二、在阅读开源框架源码中学习设计模式！

**想学习设计模式的使用方式，何不研究一下各大优秀的开源框架的源码。**

想更深层次的理解设计模式，往往阅读优秀的框架和中间件的源码是非常好的方式。优秀的开源框架和中间件，里面都使用了大量的设计模式，使得框架的实用性、可扩展性和性能非常的高。

很巧，今天在工作的空余时间中，我继续阅读一本关于并发的书，并看到关于 Netty 的内置解码器，其中最常用的有 ReplayingDecoder，它是 ByteToMessageDecoder 的子类，作用是： 在读取ByteBuf缓冲区的数据之前，需要检查缓冲区是否有足够的字节；若ByteBuf中有足够的字节，则会正常读取；反之，如果没有足够的字节，则会停止解码。

它是如何做到自主控制解码的时机的呢？其实底层是使用了 ReplayingDecoderByteBuf 这个继承于 ByteBuf 的实现类。而它使用了装饰器设计模式。

## 1、在 Netty 中如何自定义实现整数解码器？

### 1.1、ByteToMessageDecoder：

我们需要自定义类需要继承 ByteToMessageDecoder 抽象类，然后重写 decode 方法即可。

看代码：

```java
/**
 * @author Howinfun
 * @desc
 * @date 2020/8/21
 */
public class MyIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() >= 4){
            
            int num = byteBuf.readInt();
            System.out.println("解码出一个整数："+num);
            list.add(num);
        }
    }
}
```

我们可以看到非常的简单，就是不断地判断缓冲区里的的可读字节数是否大于等于4（Java 中整数的大小）；如果是的话就读取4个字节大小的内容，然后放到结果集里面。

### 1.2、ReplayingDecoder：

我们需要自定义类需要继承 ReplayingDecoder 类，然后重写 decode 方法即可。

看代码：

```java
/**
 * @author Howinfun
 * @desc
 * @date 2020/8/21
 */
public class MyIntegerDecoder2 extends ReplayingDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int num = byteBuf.readInt();
        System.out.println("解码出一个整数："+num);
        list.add(num);
    }
}
```

这个实现更加简单，那就是去掉判断，直接调用 ByteBuf 的 readInt() 方法去获取整数即可。

### 1.3、测试用例：

**1.3.1、自定义业务处理器：**

先创建一个业务处理器 IntegerProcessHandler，用于处理上面的自定义解码器解码之后的 Java Integer 整数。其功能是：读取上一站的入站数据，把它转换成整数，并且输出到Console控制台上。

码如下：

```java
/**
 * @author Howinfun
 * @desc
 * @date 2020/8/21
 */
public class IntegerProcessorHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Integer integer = (Integer) msg;
        System.out.println("打印出一个整数："+integer);
    }
}
```

这个业务处理器非常的简单，直接继承 ChannelInBoundHandlerAdapter，然户重写 channelRead() 方法即可。

**1.3.2、利用 EmbeddedChannel 进行测试：**

为了测试入站处理器，需要确保通道能接收到 ByteBuf 入站数据。这里调用 writeInbound 方法，模拟入站数据的写入，向嵌入式通道 EmbeddedChannel 写入100次 ByteBuf 入站缓冲；每一次写入仅仅包含一个整数。

EmbeddedChannel 的 writeInbound 方法模拟入站数据，会被流水线上的两个入站处理器所接收和处理。接着，这些入站的二进制字节被解码成一个一个的整数，然后逐个地输出到控制台上。

看代码：

```java
public class Test{
    public static void main(String[] args){
      ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
          @Override
          protected void initChannel(EmbeddedChannel channel) throws Exception {
              // 继承 ByteToMessageDecoder 抽象类的自定义解码器
              // channel.pipeline().addLast(new MyIntegerDecoder()).addLast(new IntegerProcessorHandler());
              // 继承 ReplayingDecoder 类的自定义解码器
              channel.pipeline().addLast(new MyIntegerDecoder2()).addLast(new IntegerProcessorHandler());
          }
      };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 0;j < 20;j++){
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(j);
            channel.writeInbound(byteBuf);
        }
        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
```

通过测试，两个自定义 Decoder 都是没问题的。而他们的最大不同点在于：继承抽象类 ByteToMessageDecoder 的解码器需要判断可读字节数是否大于等于4，大于等于才可以读取一个整数出来；而继承 ReplayingDecoder 的解码器直接调用 readInt() 方法即可。

## 2、解读 ReplayingDecoder 的原理

其实其中的原理非常的简单，我们可以直接从 ReplayingDecoder 的源码入手：

### 2.1、ReplayingDecoder的构造函数：

首先是构造函数，此处我们用了无参构造函数：

```java
protected ReplayingDecoder() {
    this((Object)null);
}

protected ReplayingDecoder(S initialState) {
    this.replayable = new ReplayingDecoderByteBuf();
    this.checkpoint = -1;
    this.state = initialState;
}
```

我们可以看到，主要是初始化了 ReplayingDecoderByteBuf（其实就是加了点料的 ByteBuf）、checkpoint（读指针下标） 和 state。我们这篇文章不需要理会 state 属性，这个属性是稍微高级一点的用法。
我们最需要关注的是 ReplayingDecoderByteBuf 这个类。

### 2.2、继续探讨 ReplayingDecoderByteBuf：

那么接下来看看 ReplayingDecoderByteBuf 的源码。

**2.2.1、ReplayingDecoderByteBuf 的属性：**

```java
final class ReplayingDecoderByteBuf extends ByteBuf {
    private static final Signal REPLAY;
    private ByteBuf buffer;
    private boolean terminated;
    private SwappedByteBuf swapped;
    static final ReplayingDecoderByteBuf EMPTY_BUFFER;

    ReplayingDecoderByteBuf() {
    }
    //...
}
```

我们可以看到，它继承了 ByteBuf 抽象类，并且里面包含一个 ByteBuf 类型的 buffer 属性，剩余的其他属性暂时不需要看懂。

**2.2.2、瞧一瞧 readInt() 方法：**

那么接下来，我们就是直接看 ReplayingDecoderByteBuf 的 readInt() 方法了，因为我们知道，在上面的自定义解码器 MyIntegerDecoder2 的 decode() 方法中，只需要直接调用 ByteBuf（也就是 ReplayingDecoderByteBuf） 的 readInt() 方法即可解码一个整数。

```java
public int readInt() {
    this.checkReadableBytes(4);
    return this.buffer.readInt();
}
```

readInt() 方法非常简单，首先是调用 checkReadableBytes() 方法，并且传入 4。根据方法名，我们就可以猜到，先判断缓冲区中是否有4个可读字节；如果是的话，就调用 buffer 的 readInt() 方法，读取一个整数。

**2.2.3、继续看看 checkReadableBytes() 方法：**

代码如下：

```java
private void checkReadableBytes(int readableBytes) {
    if (this.buffer.readableBytes() < readableBytes) {
        throw REPLAY;
    }
}
```

方法非常简单，其实和我们上面的 MyIntegerDecoder 一样，就是判断缓冲区中是否有 4个字节的可读数据，如果不是的话，则抛出异常。

**2.2.4、Signal 异常：**

而我们最需要关注的就是这个异常，这个异常是 ReplayingDecoder 的静态成员变量。它是继承了 error 的异常类，是 netty 提供配合 ReplayingDecoder 一起使用的。

至于如何使用，我们可以看到 ReplayingDecoder 的 callDecode() 方法：

```java
protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    // 调用 ReplayingDecoderByteBuf 的 setCumulation() 方法，使用 ReplayingDecoderByteBuf 装饰 ByteBuf
    this.replayable.setCumulation(in);

    try {
        while(in.isReadable()) {
            int oldReaderIndex = this.checkpoint = in.readerIndex();
            int outSize = out.size();
            if (outSize > 0) {
                // 将结果集流到下一个 InBoundChannel
                fireChannelRead(ctx, out, outSize);
                out.clear();
                if (ctx.isRemoved()) {
                    break;
                }

                outSize = 0;
            }

            S oldState = this.state;
            int oldInputLength = in.readableBytes();

            try {
                // 调用自定义解码器的 decode() 方法进行解码
                this.decodeRemovalReentryProtection(ctx, this.replayable, out);
                if (ctx.isRemoved()) {
                    break;
                }

                if (outSize == out.size()) {
                    if (oldInputLength == in.readableBytes() && oldState == this.state) {
                        throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() must consume the inbound data or change its state if it did not decode anything.");
                    }
                    continue;
                }
            } catch (Signal var10) {
                // 如果不是 Sinal 异常，则往外抛
                var10.expect(REPLAY);
                if (!ctx.isRemoved()) {
                    // 设置读指针为原来的位置
                    int checkpoint = this.checkpoint;
                    if (checkpoint >= 0) {
                        in.readerIndex(checkpoint);
                    }
                }
                break;
            }

            // ......
        }

    } catch (DecoderException var11) {
        throw var11;
    } catch (Exception var12) {
        throw new DecoderException(var12);
    }
}
```

到这里，我们可以捋一下思路：

1. 当缓冲区数据流到继承 ReplayingDecoder 的解码器时，会先判断结果集是否有数据，如果有则流入到下一个 InBoundChannel；
2. 接着会调用自定义解码器的 decode() 方法，而这里就是是直接调用 ByteBuf 的 readInt() 方法，即 ReplayingDecoderByteBuf 的 readInt() 方法；里面会先判断可读字节大小是否大于 4，如果大于则读取，否则抛出 Signal 这个 Error 类型的异常。
3. 如果 ReplayingDecoder 捕捉 Signal 这个异常，会先判断 checkpoint（即读指针下标不） 是否为零，如果不是则重新设置读指针下标，然后跳出读循环。

ReplayingDecoder 能做到自主控制解码的时机，是因为使用 ReplayingDecoderByteBuf 对 ByteBuf 进行修饰，在调用 ByteBuf 的方法前，会先调用自己的判断逻辑，这也就是我们常说的装饰器模式。



# 三、装饰器模式的特点

首先，被装饰的类和装饰类都是继承同一个类（抽象类）或实现同一个接口。

接着，被装饰类会作为装饰类的成员变量。

最后，在执行被装饰类的方法前后，可能会调用装饰类的方法。

**场景总结：**

装饰器模式常用于这么一个场景：在不修改类的状态（属性或行为）下，对类的功能进行扩展！

当然啦，这是我自己个人的总结，大家可去阅读专业的书籍来证实这是否正确。如果有更好的总结，可以留言给我，让我也学习学习~

