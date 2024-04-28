package dev.eshan.productservice;

import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.model.Price;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.CategoryRepository;
import dev.eshan.productservice.repositories.PriceRepository;
import dev.eshan.productservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ProductserviceApplication {//implements CommandLineRunner {

//	private MentorRepo mentorRepo;
//	private UserRepo userRepo;
//	private final ProductRepository productRepository;
//	private final CategoryRepository categoryRepository;
//	private final PriceRepository priceRepository;

//	public ProductserviceApplication(@Qualifier("tpc_mr") MentorRepo mentorRepo,
//									 @Qualifier("tpc_ur") UserRepo userRepo,
//									 ProductRepository productRepository,
//									 CategoryRepository categoryRepository) {
//		this.mentorRepo = mentorRepo;
//		this.userRepo = userRepo;
//		this.productRepository = productRepository;
//		this.categoryRepository = categoryRepository;
//	}

//	public ProductserviceApplication(ProductRepository productRepository,
//									 CategoryRepository categoryRepository,
//									 PriceRepository priceRepository) {
//		this.productRepository = productRepository;
//		this.categoryRepository = categoryRepository;
//		this.priceRepository = priceRepository;
//	}

	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Mentor mentor = new Mentor();
//		mentor.setName("Eshan");
//		mentor.setEmail("eshan@gmail.com");
//		mentor.setAvgRating(4.5);
//		mentorRepo.save(mentor);
//
//		User user = new User();
//		user.setName("John");
//		user.setEmail("john@gmail.com");
//		userRepo.save(user);
//
//		List<User> users = userRepo.findAll();
//		for (User u : users) {
//			System.out.println(u);
//		}

//		Price price = new Price();
//		price.setCurrency("Rupee");
////		priceRepository.save(price);
//
//		Category category = new Category();
//		category.setName("Electronics");
////		categoryRepository.save(category);
//
//		Product product = new Product();
//		product.setTitle("Laptop");
//		product.setDescription("Dell Laptop");
//		product.setPrice(price);
//		product.setCategory(category);
//		productRepository.save(product);

//		productRepository.deleteById("bc9355d4-abb7-4d04-bb81-3292a009d6d3");
//
//		List<Product> productList = productRepository.findDistinctByPrice_Currency("Rupee");

//		Optional<Category> categoryOptional = categoryRepository.findById("4d439708-a68e-4b9b-a8be-e88c8c89aac9");
//		if (categoryOptional.isPresent()) {
//			Category category1 = categoryOptional.get();
//			System.out.println(category1.getName());
//			System.out.println("Printing all products");
//
//			category1.getProducts().forEach(p -> System.out.println(p.getTitle()));

//			for (Product p : category1.getProducts()) {
//				System.out.println(p.getTitle());
//			}
//		} else {
//			// Handle the case when the category with the specified ID doesn't exist
//		}
//	}
}
