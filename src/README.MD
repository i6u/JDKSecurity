### 工作模式介绍

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
