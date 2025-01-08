# Introduction
A discussion community website prototype with common functionalities:   

- Implemented Trie Tree data structure to filter out sensitive words.  
- Used Spring Security for permission control to replace the interceptor, and applied personal authentication scheme to replace the security authentication process, making permission authentication and control more convenient and flexible.  
- Stored login tickets and verification codes with Redis to solve the problem of session distribution.  
- Applied Redis advanced data type HyperLogLog to count UV (Unique Visitor), and Bitmap to count DAU (Daily Active User).  
- Used Kafka to process system notifications such as comments, likes, and follows, and used event encapsulation to build a powerful asynchronous messaging system.
- Applied Elasticsearch for global search, and add keyword highlighting and other functions through event encapsulation.
- For the trending module, I used distributed cache Redis and local cache Caffeine as multi-level cache to avoid cache avalanche, increased QPS by 20 times (10-200), and greatly improved the website access speed. I also used Quartz to regularly update the hot thread ranking.

## To-Do List:
生成长图和pdf    
实现网站UV和DAU统计    
将用户头像等信息存于AWS云服务器  

## Tech stack:
![arc](https://github.com/user-attachments/assets/e41b1268-797f-40fc-a77f-785624987894)

## A glimpse of the website: 
#### Index Page
![index](https://github.com/user-attachments/assets/006278f1-4019-418a-a556-d86e2774a85f)  
#### Message
![message](https://github.com/user-attachments/assets/dc25b0ff-9c28-4f81-b976-a33132f41efe)







