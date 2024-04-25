package life.bareun.diary.products.mapper;

import life.bareun.diary.products.dto.ProductDto;
import life.bareun.diary.products.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Product -> ProductDto 매핑
    @Mapping(source = "product.key", target = "key")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "introduction", target = "introduction")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    ProductDto toProductDto(Product product);
}
