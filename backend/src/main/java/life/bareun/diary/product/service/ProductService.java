package life.bareun.diary.product.service;

import life.bareun.diary.product.dto.response.ProductListRes;
import life.bareun.diary.product.dto.response.ProductStreakColorUpdateRes;
import life.bareun.diary.product.dto.response.ProductTreeColorUpdateRes;

public interface ProductService {

    ProductListRes productList();

    ProductStreakColorUpdateRes buyStreakGotcha();

    ProductTreeColorUpdateRes buyTreeGotcha();

}
