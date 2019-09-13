Order Management Project
======================

To start project 
-- gradle build  
-- spring-boot:run

It will create an H2 database and tables. 

You can use swagger using this address

http://localhost:8080/swagger-ui.html

you can login with these credentials

demo1@demo.com / 12345678
demo2@demo.com / 12345678
demo3@demo.com / 12345678

You can check code OrderService.java  
I used Java8 streams. Since it is very powerful and very fast. 

You can reach h2 console

http://localhost:8080/h2-console
use db url : jdbc:h2:./cgi_dev.db;DB_CLOSE_ON_EXIT=FALSE

you can create , update, updateStatus, listaspageable, listliveorder, delete ... 

enjoy 

Note : I only had a night(3 - 4 hour ) to do this, I did not have more time. 
If you want, I can add much more