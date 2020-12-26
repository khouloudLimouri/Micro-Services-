package org.sid.inventoryservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
  @Bean
    CommandLineRunner start(ProductRepositoy productRepositoy){
        return args -> {
            productRepositoy.save(new Product(null,"ordinatieur",7000,12));
            productRepositoy.save(new Product(null,"portable",800,12));
            productRepositoy.save(new Product(null,"imprimante",2000,12));
            productRepositoy.findAll().forEach(p->{
               System.out.println(p.toString());
                    });


        };
    }
}
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
 class Product{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private double quantity;


 }
 @RepositoryRestResource
 interface ProductRepositoy extends JpaRepository<Product,Long>{

 }
