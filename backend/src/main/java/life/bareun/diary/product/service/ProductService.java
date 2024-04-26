package life.bareun.diary.product.service;

import life.bareun.diary.product.dto.response.ProductListRes;

public interface ProductService {

    ProductListRes productList();

    String buyStreakGotcha();

}
