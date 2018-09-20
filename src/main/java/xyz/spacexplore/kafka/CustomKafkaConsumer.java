package xyz.spacexplore.kafka;

//@Component("customKafkaConsumer")
public class CustomKafkaConsumer {
//    @KafkaListener(id = "gs1-1", topics = {"test"})
    public void listen(String data) {
        System.out.println("SimpleConsumer收到消息：" + data);
    }
}
