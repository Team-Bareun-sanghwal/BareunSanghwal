package life.bareun.diary.products.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.products.dto.response.ProductListRes;
import life.bareun.diary.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> productList() {
        ProductListRes products = productService.productList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "상품 목록 받아라",
                    products
                )
            );
    }
}
