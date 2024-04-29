package life.bareun.diary.product.dto.response;

import java.util.List;
import life.bareun.diary.product.dto.ProductDto;

public record ProductListResDto(
    List<ProductDto> products
) {

}