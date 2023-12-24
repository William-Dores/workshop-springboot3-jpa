package com.treinandospring.course.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.treinandospring.course.entities.Category;
import com.treinandospring.course.entities.Order;
import com.treinandospring.course.entities.OrderItem;
import com.treinandospring.course.entities.Payment;
import com.treinandospring.course.entities.Product;
import com.treinandospring.course.entities.User;
import com.treinandospring.course.entities.enums.OrderStatus;
import com.treinandospring.course.repositories.CategoryRepository;
import com.treinandospring.course.repositories.OrderItemRepository;
import com.treinandospring.course.repositories.OrderRepository;
import com.treinandospring.course.repositories.ProductRepository;
import com.treinandospring.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Category c1 = new Category(null, "Eletronics");
		Category c2 = new Category(null, "Books");
		Category c3 = new Category(null, "Computers");
		
		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");
		
		categoryRepository.saveAll(Arrays.asList(c1, c2, c3));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		p1.getCategories().add(c2);
		p2.getCategories().add(c1);
		p2.getCategories().add(c3);
		p3.getCategories().add(c3);
		p4.getCategories().add(c3);
		p5.getCategories().add(c2);
		productRepository.saveAll(Arrays.asList(p1,p2, p3, p4, p5));	

		
		
		User u1 = new User(null, "Teste 1111", "teste1@gmail.com", "111111111", "111111");
		User u2 = new User(null, "Teste 2222 ", "teste2@gmail.com", "2222222222", "222222");
		
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(null, Instant.parse("2019-06-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
		Order o3 = new Order(null, Instant.parse("2019-06-20T15:21:22Z"), OrderStatus.SHIPPED, u1);
		
		
		
		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p4.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p1.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());
		
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
		
		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
		o1.setPayment(pay1);
		
		orderRepository.save(o1);
	}
}
