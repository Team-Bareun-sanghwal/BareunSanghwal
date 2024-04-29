package life.bareun.diary.product.dto.response;

import java.util.List;
import life.bareun.diary.product.dto.ProductDto;

public record ProductListRes(
    List<ProductDto> products
) {

}