## GCLogAnalysis.java 运行
### 一、-Xmx512m -Xms512m 的情况下
> java -Xmx512m -Xms512m `-XX:+UseSerialGC` -XX:+PrintGCDetails GCLogAnalysis

结果：执行结束!共生成对象次数: `7926`，执行FullGC 5次，年轻代GC10次 左右
> java -Xmx512m -Xms512m -XX:+PrintGCDetails GCLogAnalysis

结果：执行结束!共生成对象次数: `7320`， 执行FullGC 5次，YoungGC 21次左右

> java -Xmx512m -Xms512m `-XX:+UseConcMarkSweepGC` -XX:+PrintGC GCLogAnalysis

结果：执行结束!共生成对象次数:8543 执行FullGC 4次，YoungGC 12次左右

#### 在设置堆大小512m的情况下，CMS能够生成的对象次数最多，串行GC比并行GC效果稍好。在堆内存较小的情况下，并行和串行的GC效果相差不大，并行也没有体现出多线程的优势，反而还有线程上下文切换

### 二、 -Xmx2g -Xms2g 的情况下，调整执行时间到4s，由于时间段不会进行fullGC
> java -Xmx2g -Xms2g `-XX:+UseSerialGC` -XX:+PrintGCDetails GCLogAnalysis

串行结果：执行结束!共生成对象次数: `52170`，执行FullGC2次
> java -Xmx2g -Xms2g -XX:+PrintGCDetails GCLogAnalysis

并行结果：执行结束!共生成对象次数: `50661`， 执行FullGC 2次
> java -Xmx2g -Xms2g `-XX:+UseConcMarkSweepGC` -XX:+PrintGC GCLogAnalysis

CMS结果：执行结束!共生成对象次数: `54954`，执行FullGC 4次

#### 在增大堆大小的情况下，效率均有所提高。CMS的fullGC次数最多，因为CMS会预留一些buffer，所以会比串行gc提前进行GC


### 二、 -Xmx4g -Xms4g 的情况下，调整程序执行时间到8s
> java -Xmx4g -Xms4g `-XX:+UseSerialGC` -XX:+PrintGCDetails GCLogAnalysis

结果：执行结束!共生成对象次数: `119269` 执行FullGC 1次
> java -Xmx4g -Xms4g -XX:+PrintGCDetails GCLogAnalysis

结果：执行结束!共生成对象次数: `116429`， 执行FullGC 1次

> java -Xmx4g -Xms4g `-XX:+UseConcMarkSweepGC` -XX:+PrintGC GCLogAnalysis

结果：执行结束!共生成对象次数: `105430` 执行FullGC 2次

> java -Xmx4g -Xms4g `-XX:+UseG1GC` -XX:+PrintGC GCLogAnalysis

结果：G1执行结束!共生成对象次数: `122601`

#### 在增大堆到4g之后，串行gc的劣势就体现出来了，一次要回收很大的Edge区，即使1s内只进行了一次gc，但是执行的时间大概0.16s，时间比较长了导致性能下降，而并行GC性能也下降，在1s内执行了两次youngGC，一次0.1秒左右，一次0.14秒左右，导致性能还不如串行GC；G1较稳定，应该是只执行1s，G1的自动学习能力还没有得到体现，应该并行GC也有这个问题，自适应调整还没有开始就结束了


## gateway-server-0.0.1-SNAPSHOT.jar 运行

### -Xmx2g -Xms2g 情况下
> wrk -t 20 -c 100 -d 40s http://localhost:8088/api/hello

`并行GC` 结果：Requests/sec:  16571.02

`串行GC` 结果：Requests/sec:  20218.27

`CMS` 结果：Requests/sec:  17649.59

`G1GC` 结果：Requests/sec:  23278.21

结果显示，在2g堆的情况下，并行GC反而是吞吐量最低的GC

### -Xmx4g -Xms4g 情况下
> wrk -t 20 -c 100 -d 40s http://localhost:8088/api/hello

`并行GC` 结果：Requests/sec:  21994.78

`串行GC` 结果：Requests/sec:  17703.30

`CMS` 结果：Requests/sec:  22474.61

`G1GC` 结果：Requests/sec:  23463.83

结果显示，在4g堆下，基本符合想像中的情况，串行gc在大内存的情况下比较吃力，并行GC和CMS在结果中看起来差不多，他们各种有各自的侧重点


由于时间紧急，没有去进行参数调优查看效果，之后要回顾这里的压测情况，再进行参数调整看看情况