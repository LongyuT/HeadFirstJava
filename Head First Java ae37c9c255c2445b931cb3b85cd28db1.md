# Head First Java

Tags: Java
开始时间: October 18, 2023

# Program 1: 战舰游戏

目标：棋盘类的战舰游戏，目标是猜测对方战舰的坐标，然后轮流开炮攻击，命中数发就可以打沉对方的战舰。

初始化：7×7的方格中，每个战舰占用连续的三个方格，将这三格全部命中则击沉。

## 需求分析

1. 主程序Game类
    1. 初始化棋盘 `init()`
    2. 游戏开始 `GameStart()`
    3. 判断游戏是否结束 `GameEnd()`
2. 战舰对象DotCom类
    1. 实例变量表示位置 `ArrayList<String> positions`
    2. 实例变量表示战舰名字 `String name`
    3. 内部枚举类表示状态
    4. 判断是否击中和存货状态 `checkSelf(String p)`
3. 每次攻击后的返回信息类
    1. 是否命中
    2. 是否存活
4. 帮助处理输入输出的Helper类
    1. 随机生成战舰名称
    2. 随机生成可用的战舰位置
    3. 输出游戏开始时的标识 `GameStartOutput()`
    4. 一轮游戏 `round()`
    5. 游戏结束后输出游戏结束 `GameEndOutput()`
    

### 实践中遇到的问题

1. `foreach`中的 `remove/add` 导致的 `ConcurrentModificationException` **(**并发修改**)**异常
    1. 《阿里巴巴Java开发手册中》规定: 不要在foreach循环里进行元素的 `remove/add` 操作, `remove` 元素请使用Iterator方式.
    
    ```java
    List<String> list = new ArrayList<>(Arrays.asList("1", "2"));
    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
    	String item = iterator.next();
    	if (删除条件) {
    		iterator.remove();
    	}
    }
    ```
    
    b. 原因:  forEach循环使用场景必须是数组或者实现Iterable接口的容器类；该循环使用了Iterator的hasNext()和next()两个方法，ArrayList 中有一个修改元素计数标记 `modCount`，ArrayList中的迭代器实现类也有一个修改元素计数标记 `expectedModCount`，每次循环都需要调用hasNext 和 next方法，调用next方法前，就要比较 `modCount` 和 `expectedModCount`是否相等，若不等则抛出 `ConcurrentModificationException`异常。以上例子中forEach中调用了ArrayList的remove方法，这时候modCount做了改变；但是迭代器中的expectedModCount没有改变，所以两者不等，抛出异常。这个机制本身是为了线程安全而设置.
    
2. 值传递导致的 `NullPointerException` : 在 `List` 使用add方法的时候, 如果是add一个引用对象, 不应该以 `dotComs.add(dotcom)` 的方式, 而应当以 `dotComs.add(new DotCom(a, b))` 的形式来传递, 否则在该函数执行结束后, 函数中的 `dotcom` 会被析构释放, 在使用 `List` 中元素的时候会引发`NullPointerException`.

# Program 2: 音乐播放器

## 异常

异常是程序中的一些错误, 通常可以分为以下三种类型的异常:

1. 可检查异常**checked exceptions**: 顾名思义, 这是会被编译器所检查的, 无法被简单的忽略, 通常是用户错误或问题引起的异常, 在编写具有这部分异常的程序时, 必须要使用 `throw/throws` 来声明或抛出异常, `Exception` 类中, 除去 `RuntimeException` 外, 均是可检查异常.
2. 运行时异常**runtime exceptions**: 运行时异常不会被编译器检查, 但是可能被程序员编写代码时可避免的, 通常是 `RuntimeException`的子类.
3. 错误**errors**: 错误无法被编译检查到, 通常是脱离程序员控制的问题, 通常是 `Error` 的子类.

它们之间的关系如下:

![Untitled](Head%20First%20Java%20ae37c9c255c2445b931cb3b85cd28db1/Untitled.png)

## 异常处理

对于异常的处理, 通常使用 `throws` 关键字在方法声明中抛出, 或是使用 `throw` 关键字在代码中抛出.

### throws 关键字

`throws` 用于在方法声明时指定该方法可能抛出的异常, 该异常会被传递给调用该方法的代码, 可以在该代码中来进行处理. 例如 `readLine()` 方法可能会抛出 `IOException` 异常, 所以在调用该方法的代码中, 必须捕获或声明处理 `IOException` 异常.

```java
public void readFile(String filePath) throws IOException {
  BufferedReader reader = new BufferedReader(new FileReader(filePath));
  String line = reader.readLine();
  while (line != null) {
    System.out.println(line);
    line = reader.readLine();
  }
  reader.close();
}
```

### throw 关键字

`throw` 关键字用于在当前方法中抛出一个异常.

```java
public void checkNumber(int num) {
  if (num < 0) {
    throw new IllegalArgumentException("Number must be positive");
  }
}
```

## 捕获异常

### try/catch/finally

通常使用 `try/catch` 或 `try/catch/finally` 来捕获异常. `try` 代码块中的代码称为保护代码, 在 `catch` 语句中包含捕获异常类型的声明, 当发生异常时, 异常会被传递到对应异常类型的 `catch` 块进行处理. `finally` 关键字用来创建在 `try` 代码块后面执行的代码块, 无论是否发生异常, `finally` 代码块中的代码总会执行, 一般在这里运行清理类型等收尾善后性质的语句.

```java
try{
  // 程序代码
}catch(异常类型1 异常的变量名1){
  // 程序代码
}catch(异常类型2 异常的变量名2){
  // 程序代码
}finally{
  // 程序代码
}
```

### try-with-resources

`try-with-resources` 是JDK7以后新增的语法糖, 可以在语句执行完毕后确保每个资源都被自动关闭, 可以简化资源管理代码的编写. 以下两个代码本质上是等同的.

> `try(resource1 declaration; resource2 declaration)` 每个资源声明之间用 `;` 隔开.
> 

```java
    public static void main(String[] args) {
    String line;
        try(BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
            while ((line = br.readLine()) != null) {
                System.out.println("Line =>"+line);
            }
        } catch (IOException e) {
            System.out.println("IOException in try block =>" + e.getMessage());
        }
    }

	  public static void main(String[] args) {
        BufferedReader br = null;
        String line;

        try {
            System.out.println("Entering try block");
            br = new BufferedReader(new FileReader("test.txt"));
            while ((line = br.readLine()) != null) {
            System.out.println("Line =>"+line);
            }
        } catch (IOException e) {
            System.out.println("IOException in try block =>" + e.getMessage());
        } finally {
            System.out.println("Entering finally block");
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.println("IOException in finally block =>"+e.getMessage());
            }
        }
    }
```

### 自定义异常

编写自己的异常类:

- 所有异常都必须是 Throwable 的子类。
- 如果希望写一个检查性异常类，则需要继承 Exception 类。
- 如果你想写一个运行时异常类，那么需要继承 RuntimeException 类。

## MIDI

**MIDI: Musical Instrument Digital Interface**, 即电子乐器接口, 不同电子发声装置的标准协议, 包含播放功能装置的指令.

在发出任何声音之前, 首先需要取得 `Sequencer` 对象.

`Sequencer` 是一个接口, 包括MIDI音序器操作的方法, 类似于一个**播放器**, 其实现对象由 `MidiSystem.getSequencer()` 来获得, 可能会抛出 `MidiUnavailableException` . 

而 `Sequence` 则类似于一个CD, `Track` 类似于CD上的一首歌曲, `MidiEvent` 表示其中的一个指令(某个音符, 更换乐器等)

所以播放声音的步骤如下:

1. 取得 `Sequencer` 并打开

```java
Sequencer player = MidiSystem.getSequencer();
player.open();
```

1. 创建新的 `Sequence`

```java
Sequence seq = new Sequence(timing, 4);
```

1. 从 `Sequence` 中创建新的 `Track`

```java
Track t = seq.createTrack();
```

1. 填入 `MidiEvent` 并让 `Sequencer` 播放

```java
t.add(myMidiEvent1);
player.setSequence(seq);
```

## Swing

Swing 是 Java 的 GUI 工具包, 包括了两种元素: 组件和容器.

1. 组件(控件) 继承于 `JComponent` 类, 常见的组件有标签 `JLable` , 按键 `JButton` , 输入框 `JTextField` , 复选框 `JCheckBox` , 列表 `JList` .
2. 容器是一种可以包含组件的组件, 有两种容器.
    1. 重量级容器(顶层容器 top-level container), 不继承于 `JComponent` , 包括 `JFrame` , `JApplet` , `JWindow` , `JDialog`. 它们无法被别的容器包含, 只能作为界面程序的最顶级容器来包含其它组件.
    2. 轻量级容器(中间层容器), 继承于 `JComponent` , 包括 `JPanel`, `JScrollPane` , 中间层容器用来将若干个相关联的组件放在一起, 因为它们本身也是 `JComponent` , 所以也必须包含在其他的容器中.

创建GUI的流程

1. 创建窗体 frame

```java
JFrame frame = new JFrame();
```

1. 创建 widget (控件)

```java
JButton button = new JButton("some text");
```

1. 把 widget 加到 frame 上, 组件不是直接加上 frame 上, 而是加到 window 的 pane

```java
frame.getContentPane().add(button);
```

1. 显示 frame

```java
frame.setSize(300, 300);
frame.setVisible(true);
```

### 事件

如何在用户执行一个操作的时候, 按钮执行特定的工作呢, 这则需要取得与用户操作事件有关的过程, 这就需要监听事件的接口. 对 Java 而言, 事件几乎都是以对象来表示的, `MouseEvent` , `KeyEvent` , `WindowEvent` , `ActionEvent` 等.

事件源在用户做出相关动作时产生事件对象, 而程序作为监听者, 用相对应的监听者接口如 `MouseListener` 等.

以 `JButton` 为例, 这个过程如下的步骤:

1. 在声明中实现 `ActionListener` 接口

```java
public class someMethod implements ActionListener
```

1. 向按钮注册, 即说明监听事件

```java
button.addActionListener(this);
```

1. 定义事件处理的方法, 即实现 `ActionListener` 接口的 `actionPerformed()` 方法, 这样在按钮被点击时会以 `ActionEvent` 对象作为参数来调用此方法.

```java
public void actionPerformed(ActionEvent event) {
	//执行代码
}
```

### 绘制图形

通常使用 `Graphics` 对象来绘制图形, 最好的方式是自己创建有绘图功能的组件, 即创建 `JPanel` 的子类并覆盖掉 `paintComponent()` 方法.

```java
class MyDrawPanel extends JPanel {
	public void paintComponent(Graphics g) {
		g.setColor(Color.orange);
	}
}
```

> 但这个方法无法自己调用, 因为这个 `Graphics` 对象是个与实际屏幕有关的对象, 无法取得, 但可以调用 `repaint()` 来重新绘制.
> 

> 这个 `Graphics g` 对象实际上是 `Graphics2D` 实例, 所以可以通过 `Graphics2D g2d = (Graphics2D) g;` 转换成 `Graphics2D` 变量后使用子类的方法.
> 

### 布局

布局控制容器的位置, 即排列布局方式

| BorderLayout | 分为东南西北中五个方位 |
| --- | --- |
| FlowLayout | 流式布局, 从左到右中心放置, 一行放不下就换行 |
| GridLayout | 网格式布局 |
| GridBagLayout | 网格式, 但可以放置不同大小的组件 |
| BoxLayout | 把组件水平或者竖直排在一起 |
| SpringLayout | 按照一定的约束条件来组织组件 |

### 不同的按钮分别取得事件

当每个按钮执行不同的工作时, 则需要对不同的按钮分别取得事件, 此时考虑使用内部类的方式来实现, 以两个按钮为例.

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TwoButtons {

    JFrame frame;
    JLabel label;

    public static void main(String[] args) {
        TwoButtons gui = new TwoButtons();
        gui.go();
    }

    public void  go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton labelButton = new JButton("Change Label");
        labelButton.addActionListener(new LabelListener());

        JButton colorButton = new JButton("draw");
        colorButton.addActionListener(new ColorListener());

        label = new JLabel("im a label");
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.WEST, label);

        frame.setSize(300, 300);
        frame.setVisible(true);

    }

    class LabelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setText("some text");
        }
    }

    class ColorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.repaint();
        }
    }

    class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            g.fillRect(20, 50, 100, 100);

        }
    }
}
```

### 监听非GUI的事件

监听非GUI的事件本质上和监听GUI事件一样:

1. 实现监听者的接口
2. 向事件源注册
3. 等待事件源调用事件处理程序

监听MIDI事件的方式如下, 利用 `ControllerEventListener` , 来监控事件编号为 127 的 `ControllerEvent` , 这个事件不会做任何事情, 只是用来作为监控的事件.

```java
import javax.sound.midi.*;

public class MiniMusicPlayer2 implements ControllerEventListener {

    public static void main(String[] args) {
        MiniMusicPlayer2 player2 = new MiniMusicPlayer2();
        player2.go();
    }

    public void go() {
        Sequencer sequencer = null;
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
						//addControllerEventListener()方法需要一个 int[]的编号列表
            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(this, eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i < 60; i += 4) {
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(144, 1, 127, 100, i));
                track.add(makeEvent(128, 1, i, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void controlChange(ShortMessage event) {
        System.out.println("something happened");
    }

    public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        }
        catch (Exception e) {}
        return event;
    }
}
```

# Program 3: e-flashcard

## 序列化 Serialization

序列化: 把 Java 对象转成字节序列(二进制数据流)

反序列化: 把字节序列(二进制数据流)恢复成 Java 对象

### Serializable 接口

`Serializable` 接口是一个标记接口, 用于标志实现该接口的类是可序列化的

```java
public interface Serializable {
}

public class XX implements Serializable {
}
```

通常序列化对象的步骤如下:

1. 创建 `FileOutputStream` , 该对象用于存取文件, 而 `file.ser` 文件如果不存在, 则会自动被创建出来.

```java
FileOutputStream fileStream = new FileOutputStream("file.ser");
```

1. 创建 `ObjectOutputStream` , 该对象能让你写入对象, 但无法直接连接文件, 需要参数来指引.

```java
ObjectOutputStream os = new ObjectOutputStream(fileStream);
```

1. 写入对象, 将变量引用的对象序列化并写入 `file.ser` 这个文件

```java
os.writeObject(objectOne);
```

1. 关闭关联的输出串流

```java
os.close();
```

> 当其中某个实例变量不能或不应被实例化, 则应该标记为 `transient`
> 

### 反序列化

反序列化类似于序列化的反向操作

1. 创建 `FileInputStream` , 如果文件不存在, 就会抛出异常

```java
FileInputStream fileStream = new FileInputStream("file.ser");
```

1. 创建 `ObjectInputStream` 

```java
ObjectInputStream os = new ObjectInputStream(fileStream);
```

1. 读取对象, 每次调用 `readObject()` 都会从 stream 中读出下一个对象, 读取顺序和写入顺序相同, 次数超过会抛出异常.

```java
Object one = os.readObject();
```

1. 转换对象类型

```java
GameCharacter elf = (GameCharacter) one;
```

1. 关闭 `ObjectInputStream`

```java
os.close();
```

> 如果子类可以序列化而父类不可序列化时, 虽然不会报错, 但是父类中的属性会丢失.
> 

### 序列化识别: Version ID

由于类可能会有版本更替(修改), 所以会破坏兼容性, 为了保证安全, 每当对象被序列化的同时, 该对象都会加上一个类的版本识别 ID `serialVersionUID` , 这是根据类的结构信息计算出来的, 如果反序列化时, 版本不相符, JVM 则会抛出异常.

通常将 `serialVersionUID` 放在类中

```java
public class Dog {
	static final long serialVersionUID;
	
	private String name;
}
```

## 缓冲区 buffer

缓冲区的意义在于其效率高于没有使用缓冲区， 因为每趟磁盘操作比内存操作都有更大的消耗。

## 代码实现

设计用三个类来实现 e-flashcard

1. `QuizCardBuilder` : 设计并存储卡片的工具
2. `QuizCardPlayer` : 加载并播放卡片的引擎
3. `QuizCard` : 表示卡片数据的类

# Program 4: Chat Client

整体的工作流程:

1. 客户端连接到服务器
2. 服务器建立连接并将客户端加到连接清单中
3. 某客户端A发送信息道聊天服务器上
4. 服务器将信息发送给所有的客户端

即需要完成如下三个过程:

1. 如何建立客户端和服务器之间的初始连接
2. 如何传送信息到服务器
3. 如何接收来自服务器的信息

Java API 中已有很多相关的网络功能包来解决相关的问题.

## Socket

`Socket` (`java.net.Socket` ) 是代表两台机器之间网络连接的对象, 创建 `Socket` 连接, 需要两个参数, 服务器 IP 地址和用于收发数据的端口号.

```java
Socket chatSocket = new Socket("IP address", port);
```

> 每个服务器有 65536 个端口, 通常 0-1023端口都已经被保留给已知的特定服务, 所以我们通常从1024-65535中间挑选合适的端口号使用.
> 

利用 `BufferedReader` 从 `Socket` 上读取数据

```java
//建立对服务器的 Socket 连接
Socket chatSocket = new Socket("ip address", port);
//建立连接到 Socket 上底层输入串流的 InputStreamReader, 作为低层和高层串流间的桥梁
InpuStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
//建立 BufferedReader 来读取
//将 BufferedReader 链接到 InputStreamReader
BufferedReader reader = new BufferedReader(stream);
String message = reader.readLine();
```

用 `PrintWriter` 写数据到 `Socket` 上

```java
//对服务器建立 Socket 连接
Socket chatSocket = new Socket("ip address", port);
//建立链接到 Socket 的 PrintWriter
PrintWriter writer = new PrintWriter(chatSocket.getOutputStream());
//写入数据
writer.println("message to send");
```

Client 端的过程如下所示:

1. 客户端连上服务器并取得输入串流
2. 客户端从服务器读取消息

Server 端的过程:

1. 服务器应用程序对特定端口创建出 `ServerSocket` , 这会让服务器应用开始监听来自指定端口的客户端请求
2. 客户端对服务器端应用程序建立 `Socket` 连接
3. 服务器创建出与客户端通信的新 `Socket` , `accept()` 方法会在客户端连上来时, 返回一个 `Socket` 与客户端通信.

## 多线程

由于这个 Chat Client 需要同时拥有发送和获取信息的能力, 即同时执行的能力. 这需要新的线程 `thread` .

`Thread` 对象代表线程, 当需要启动新的线程时就建立 `Thread` 实例. 线程代表独立的执行空间, 每个 Java 应用程序会启动一个主线程 `main()` , JVM 会负责主线程的启动, 但程序员需要负责启动自己建立的线程.

### 启动多线程

1. 建立 `Runnable` 对象, 编写 `Runnable` 接口的实现类.

```java
Runnable threadJob = new MyRunnable();
```

1. 建立 `Thread` 对象, 并赋值 `Runnable` 

```java
Thread myTread = new Thread(threadJob);
```

1. 启动 `Thread`

```java
myThread.start();
```