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

## 前后端分离跨域问题

我们在前后端分离的情况下，浏览器由于同源策略，不会携带cookie，这让我们在做登录认证的时候，遇到了麻烦。通过自定义请求头携带token，可以实现认证，自定义请求头，会产生非简单请求，分两次发送，一次预请求，预请求通过，才会发送普通请求

在使用springboot解决跨域问题，主要是通过**跨域资源共享**（**cors**），还有其他解决方式，没试过

其主要实现原理，就是拦截请求和响应，在响应头中添加cors相关的响应头，例如`Access-Control-Allow-Origin`

代码实现只需两步，真的很简单，过程却很曲折

1. `@CrossOrigin`

   该注解可以作用在方法上，也可以作用在controller类上，**开启跨域**，默认**所有origin**都允许，所有方法被允许，**所有请求头**被允许，也可以指定origin，例如192.168.50.145:5533，这样就只有origin是这个地址的请求会被响应

   该注解有以下属性可以配置

   - `origins`
   - `methods`
   - `allowedHeaders`
   - `exposedHeaders`
   - `allowCredentials`
   - `maxAge`

   和全局配置的方法时一一对应的

   基于注解的方式，是更加细粒度的cors控制，基于过滤器的方式，是全局的cors方式，两种允许组合

2. 跨域配置（全局cors）

   ```java
   @Configuration
   public class SaTokenConfiguration implements WebMvcConfigurer {
   
       private StpUtil StpUserUtil;
       /**
        * 使用cors解决跨域问题，nginx不需要额外配置
        * 为了使前后端分离，我们需要使用跨域资源共享解决跨域问题
        * 主要还是为了让跨域能够带cookie,或者自定义请求头，进行认证授权
        * @param registry
        */
       @Override
       public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/**")
                   .allowedOrigins("http://127.0.0.1:5500", "http://192.168.50.17:6688")
                   .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                   .allowCredentials(true)
                   .maxAge(3600)
                   .allowedHeaders("*");
           // 经测试，允许的额外请求头参数，可以设为"satoken", "content-type"
           // content-type 在登陆时用到啦，为了方便我们还是使用了通配符
       }
       
       // 注册Sa-Token的拦截器
       @Override
       public void addInterceptors(InterceptorRegistry registry) {
           // 注册路由拦截器，自定义验证规则
           registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
   
               // 登录验证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
   //            SaRouter.match("/**", "/user/doLogin", StpUtil::checkLogin);
   
               // 这里把预请求给过滤掉
               // 跨域的自定义请求头，会发送两次请求，但是预请求中不包含自定义请求头
               // 因此已定义请求头也会鉴权，铁定失败，我们在这里过滤一下
               if (!"OPTIONS".equals(req.getMethod())) {
                   // 登录验证 -- 排除多个路径
                   SaRouter.match(Collections.singletonList("/**"), Arrays.asList("/user/doLogin", "/user/captcha"), StpUtil::checkLogin);
               }
   
           })).addPathPatterns("/**")
                   .excludePathPatterns("/doc.html","/v2/api-docs", "/swagger-resources/configuration/ui",
                           "/swagger-resources", "/swagger-resources/configuration/security",
                           "/swagger-ui.html", "/webjars/**", "/images/**", "/layuiadmin/**", "/login.html");
       }
   }
   ```

   继承**WebMvcConfigurer**，java8以上使用，主要时接口允许有默认实现方法了

   重写**addCorsMappings**方法，配置跨域项

   > 1. `allowedOrigins`允许请求的origin，就是前端的地址
   > 2. `allowedMethods`允许的http方法，`*`代表**GET,POST,HEAD**不是所有
   > 3. `allowCredentials`允许带cookie
   > 4. `maxAge`在此时间内相同请求，不会再发送预检请求
   > 5. `allowedHeaders`允许的自定义请求头，一般设为通配符

使用 **nginx** 作为前端的代理服务器，**不需要额外配置跨域**，网上说两边都要配置，实践下来发现，只要后端配置一下就好，使用`nginx`配置也很简单，也是这几项

有人说使用了`nginx`进行请求的代理，通过将跨域请求转为同域请求，也可以实现跨域，没实践，暂不确定

在使用**satoken框架**做认证的时候，可以在**cookie**，**自定义请求头**，**请求体**中带token，进行认证授权

但是前后端分离，没法使用cookie方式，请求体的方式没试过，使用的自定义请求头的方式（作者推荐）

**需要注意**，预检请求（option请求）要**过滤**掉，它没法带token，也无需做认证，拦截器实现路径认证鉴权，会把所有请求都拦截

