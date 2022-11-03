package in.org.practice.multidb.rest;

import in.org.practice.multidb.entity.postgres.Product;
import in.org.practice.multidb.entity.sql.Customer;
import in.org.practice.multidb.repository.postgres.ProductRepo;
import in.org.practice.multidb.repository.sql.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class Controller {
    ProductRepo productRepo;
    CustomerRepo customerRepo;

    @GetMapping("/customers")
    List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @GetMapping("/products")
    List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @PostMapping("/customers")
    boolean saveCustomer(@RequestBody Customer customer) {
        boolean status = false;
        try {
            customerRepo.save(customer);
            status = true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }

    @PostMapping("/product")
    boolean saveCustomer(@RequestBody Product product) {
        boolean status = false;
        try {
            productRepo.save(product);
            status = true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return status;
    }
}
