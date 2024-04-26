package life.bareun.diary.product.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.product.dto.response.ProductListRes;
import life.bareun.diary.product.dto.response.ProductStreakColorUpdateRes;
import life.bareun.diary.product.dto.response.ProductTreeColorUpdateRes;
import life.bareun.diary.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<BaseResponse<ProductListRes>> productList() {
        ProductListRes products = productService.productList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "상품 목록을 읽어왔습니다.",
                    products
                )
            );
    }

    @PatchMapping("/color/streak")
    public ResponseEntity<BaseResponse<ProductStreakColorUpdateRes>> gotchaStreak() {
        ProductStreakColorUpdateRes streakColorUpdateRes = productService.buyStreakGotcha();
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "스트릭 변경권을 구매했습니다.",
                    streakColorUpdateRes
                )
            );
    }

    @PatchMapping("/color/tree")
    public ResponseEntity<BaseResponse<ProductTreeColorUpdateRes>> gotchaTree() {
        ProductTreeColorUpdateRes treeColorUpdateRes = productService.buyTreeGotcha();
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "나무 테마 변경권을 구매했습니다.",
                    treeColorUpdateRes
                )
            );
    }

}
