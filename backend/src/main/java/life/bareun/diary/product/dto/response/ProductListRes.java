package life.bareun.diary.product.dto.response;

import java.util.List;
import life.bareun.diary.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductListRes {

    private List<ProductDto> products;
}
