package xyz.spacexplore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("xyz.spacexplore.domain")
public class LaserBoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaserBoomApplication.class, args);
    }
}
