# csa-server
a server code for our project:csa(网络态势感知)
## 项目结构说明
* config

    放置Java配置类

* controller

    Web层,处理前端请求并返回响应

* domain

    实体类,和数据库的表一一对应

* mapper

    dao层,数据库访问

* ro(request object)

    对前端的请求信息进行封装,直接用map获取虽然简单,但是后期维护成问题

* vo(view object)

    对响应给前端的信息进行封装

* utils

    一些封装好的工具类

    我们实现关键节点的c++代码和打包好的.so文件都在这里

## 数据库导入

我们使用了数据库版本控制工具 **flyway**

并在 application.yml 中进行了配置,只需要创建数据库,剩下的表和数据,由工具替我们完成,只需要启动项目,就会自动执行

实现原理:flyway在我们数据库中创建了一个history表,来进行版本控制



## 关键节点实现

由于关键节点的算法是C++代码实现,使用Java翻译一遍工作量过大,我们采用**JNI**的方式,实现Java调用C++

主要实现步骤:

1. 使用Java创建一个本地方法

    ```java
    package edu.scu.csaserver.utils;
    
    public class KeyNode {
        private native int[] getKeyNode(int node_num, int[][] links);
    }
    ```

2. 生成头文件,并用C++实现

    ```bash
    # 有包名(还要指定编码)
    # 需要在src/main/java下执行,而不是项目根目录
    # java/ 下一级目录就是edu
    javah -encoding utf-8 -classpath . -jni edu.scu.csaserver.utils.KeyNode
    ```

    将生成的头文件放在和KeyNode.java同一目录,然后编写C++代码实现头文件的函数,方法签名要一致

    ```c++
    JNIEXPORT jintArray JNICALL Java_edu_scu_csaserver_utils_KeyNode_getKeyNode
      (JNIEnv *env, jobject thiz, jint node_num, jobjectArray topo_links) {
        ...C++代码实现
    }
    ```

    这里主要涉及Java数据类型转JNI,JNI类型和C++的转化

3. 打包成动态链接库

    ```bash
    # linux 下编译(是在Linux下运行而不是Windows,我蠢了)
    g++ -shared  KeyNode.cpp -I "/usr/local/develop/jdk1.8.0_251/include" -I "/usr/local/develop/jdk1.8.0_251/include/linux" -o KeyNode.so -fPIC
    # windows 下编译（-I指定头文件路径）
     g++ -shared  testJNI.cpp -I "C:\Program Files\Java\jdk1.8.0_271/include" -I "C:\Program Files\Java\jdk1.8.0_271/include\win32"  -o testJNI.dll
    ```

    jni的两个头文件路径,我们需要手动指定

4. 在Java中加载动态库,并使用

    ```java
    public int[] getKeyNodeIds(int nodeIdMax, int[][] links) {
            // System.out.println(System.getProperty("java.library.path"));
            // 默认从 java.library.path 下加载 .dll文件
            // 放在其中一个目录即可
            // 为了快速加载，我们可能要指定绝对路径
            // 改用加载方式，Runtime.getRuntime().load(soLibFilePath);
            // 已经改了
            // 需要加文件名后缀，这就有点坑啊
            // 为了开发方便我们还是要区分系统
            String filename;
            String osName = System.getProperty("os.name");
            // ide运行是项目根目录(pom.xml就位于该目录下)
            // jar包运行是jar包所在目录
            String runDir = System.getProperty("user.dir");
            if (osName.startsWith("Windows")) {
                filename = runDir + "\\KeyNode.dll";
            } else {
                // 路径分隔符还不一样
                filename = runDir + "/KeyNode.so";
            }
            Runtime.getRuntime().load(filename);
    //        System.loadLibrary("KeyNode");
            return getKeyNode(nodeIdMax, links);
        }
    ```

    我们使用了绝对路径的加载方式,默认是当前运行目录
