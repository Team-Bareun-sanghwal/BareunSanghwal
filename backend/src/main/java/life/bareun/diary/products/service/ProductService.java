package life.bareun.diary.products.service;

import life.bareun.diary.products.dto.response.ProductListRes;

public interface ProductService {

    ProductListRes productList();

    String buyStreakGotcha();

}
