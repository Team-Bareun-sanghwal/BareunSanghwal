package life.bareun.diary.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String name;
    private String introduction;
    private String description;
    private Integer price;
}