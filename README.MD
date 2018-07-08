### 加密工作模式介绍

1. ECB （电子密码本）  
每块独立加密；  
优点是分块可以并行计算；  
缺点是同样的原文得到相同的密文，容易被攻击  

2. CBC （密码分组链接）  
密码分组链接；
每块加密依赖于前一块的密文；  
优点是同样的原文得到不同的密文，原文的微小改动影响后面全部密文；  
缺点是加密需要串行处理，误差会传递  
```Java
// CBC模式 初始化时需要额外的参数
IvParameterSpec iv = new IvParameterSpec(password.getBytes());
cipher.init(Cipher.ENCRYPT_MODE, key, iv);
```

如果不指定，默认 工作模式/填充模式 就是 ECB/PKCS5Padding 



### 一些概念

#### [编码](https://baike.baidu.com/item/编码/80092)

编码是[信息](https://baike.baidu.com/item/%E4%BF%A1%E6%81%AF/111163)从一种形式或格式转换为另一种形式的过程也称为计算机编程语言的代码简称编码。用预先规定的方法将文字、数字或其它对象编成数码，或将信息、数据转换成规定的电脉冲信号。编码在[电子计算机](https://baike.baidu.com/item/%E7%94%B5%E5%AD%90%E8%AE%A1%E7%AE%97%E6%9C%BA/191373)、[电视](https://baike.baidu.com/item/%E7%94%B5%E8%A7%86/228945)、遥控和通讯等方面广泛使用。编码是信息从一种形式或格式转换为另一种形式的过程。[解码](https://baike.baidu.com/item/%E8%A7%A3%E7%A0%81/10944752)，是编码的逆过程。  

**常用编码方式** Base64  

#### [消息摘要](https://www.zhihu.com/question/53248302)

将长度不固定的消息（message)作为输入参数，运行特定的Hash函数，生成固定长度的输出，这个输出就是Hash，也称为这个消息的消息摘要（Message Digest）

**常用消息摘要算法** MD5 SHA1 SHA256  

#### [密码学](https://zh.wikipedia.org/wiki/密码学)

**[柯克霍夫原則](https://zh.wikipedia.org/wiki/柯克霍夫原則)** 简单来说就是，加密算法公开，密匙保密，密匙未泄露信息就是安全的

**常用对称加密** DES 3DES AES

**常用非对称加密** RSA