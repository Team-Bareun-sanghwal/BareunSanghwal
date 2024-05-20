package life.bareun.diary.product.service;

import life.bareun.diary.product.dto.response.ProductListResDto;
import life.bareun.diary.product.dto.response.ProductRecoveryPurchaseResDto;
import life.bareun.diary.product.dto.response.ProductStreakColorUpdateResDto;
import life.bareun.diary.product.dto.response.ProductTreeColorUpdateResDto;

public interface ProductService {

    ProductListResDto productList();

    ProductStreakColorUpdateResDto buyStreakGotcha();

    ProductTreeColorUpdateResDto buyTreeGotcha();

    ProductRecoveryPurchaseResDto buyRecovery();
}
