# Introduction
A discussion community website prototype with common functionalities.  

- Implemented Trie tree to filter out sensitive words.  
- Used Spring Security for permission control to replace the interceptor, and applied personal authentication scheme to replace the security authentication process, making permission authentication and control more convenient and flexible.  
- Stored login tickets and verification codes with Redis to solve the problem of session distribution.  
- Applied Redis advanced data type HyperLogLog to count UV (Unique Visitor), and Bitmap to count DAU (Daily Active User).  
- Used Kafka to process system notifications such as comments, likes, and follows, and used event encapsulation to build a powerful asynchronous messaging system.
- Applied Elasticsearch for global search, and add keyword highlighting and other functions through event encapsulation.

## To-Do List:
生成长图和pdf    
实现网站UV和DAU统计    
将用户头像等信息存于AWS云服务器  

## Tech stack:




