package xyz.spacexplore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("xyz.spacexplore.domain")
public class LaserBoomApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(LaserBoomApplication.class, args);
    }

}
