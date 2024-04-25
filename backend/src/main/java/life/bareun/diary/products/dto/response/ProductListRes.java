package life.bareun.diary.products.dto.response;

import java.util.List;
import life.bareun.diary.products.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductListRes {

    private List<ProductDto> products;
}
