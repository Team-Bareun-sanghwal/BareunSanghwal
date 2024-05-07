package life.bareun.diary.product.mapper;

import life.bareun.diary.product.dto.ProductDto;
import life.bareun.diary.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Product -> ProductDto 매핑
    @Mapping(source = "name", target = "name")
    @Mapping(source = "introduction", target = "introduction")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    ProductDto toProductDto(Product product);
}
