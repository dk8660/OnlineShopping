package com.example.onlineshopping.boundedContext.product.service;

import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.product.entity.Product;
import com.example.onlineshopping.boundedContext.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public String getProductNameById(long id) {
        try {
            Product product = productRepository.findById(id).get();
            return product.getName();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public RsData tryRegister(String name, String description, String price, String stock, String category, long sellerId) {
        if(name == null || name.trim().isEmpty()) return RsData.of("F-1", "상품명을 입력해주세요.");
        if(price == null || price.trim().isEmpty()) return RsData.of("F-2", "상품의 가격을 입력해주세요.");

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(Integer.parseInt(price))
                .stock(Integer.parseInt(stock))
                .category(category)
                .seller(sellerId)
                .build();
        productRepository.save(product);

        return RsData.of("S-1", "상품 등록에 성공하였습니다.");
    }

    public RsData tryDelete(long id) {
        try {
            productRepository.deleteById(id);
            return RsData.of("S-1", "상품 삭제에 성공하였습니다.");
        } catch (Exception e) {
            return RsData.of("F-1", "상품 삭제에 실패하였습니다.");
        }
    }

    public Product getProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) return optionalProduct.get();
        return null;
    }

    public RsData tryUpdate(long id, String name, String description, String price, String stock, String category) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if(optionalProduct.isEmpty()) {return RsData.of("F-1", "존재하지 않는 상품입니다.");}
            
            Product product = optionalProduct.get();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Integer.parseInt(price));
            product.setStock(Integer.parseInt(stock));
            product.setCategory(category);
            productRepository.save(product);
            
            return RsData.of("S-1", "상품 정보를 수정하였습니다.");
        } catch (Exception e) {
            return RsData.of("F-2", "상품 정보 수정에 실패하였습니다.");
        }
    }

    public int tryGetPrice(long id) {
        try {
            Product product = productRepository.findById(id).get();
            return product.getPrice();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
