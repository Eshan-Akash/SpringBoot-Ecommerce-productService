//package dev.eshan.productservice.inheritancedemo.tableperclass;
//
//import dev.eshan.productservice.ProductserviceApplication;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class Main implements CommandLineRunner {
//    private MentorRepo mentorRepo;
//    @Override
//    public void run(String... args) throws Exception {
//        Mentor mentor = new Mentor();
//        mentor.setName("Eshan");
//        mentor.setEmail("eshan@gmail.com");
//        mentor.setAvgRating(4.5);
//        mentorRepo.save(mentor);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ProductserviceApplication.class, args);
//    }
//}
