package life.bareun.diary.products.service;

import java.util.List;
import life.bareun.diary.products.dto.ProductDto;
import life.bareun.diary.products.dto.response.ProductListResDto;
import life.bareun.diary.products.entity.Product;
import life.bareun.diary.products.mapper.ProductMapper;
import life.bareun.diary.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductListResDto productList() {
        List<ProductDto> products = productRepository.findAll().stream()
            .map(ProductMapper.INSTANCE::toProductDto)
            .toList();

        return new ProductListResDto(products);
    }
}
