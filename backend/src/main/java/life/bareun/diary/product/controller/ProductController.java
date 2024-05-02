package life.bareun.diary.product.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.product.dto.response.ProductListResDto;
import life.bareun.diary.product.dto.response.ProductRecoveryPurchaseResDto;
import life.bareun.diary.product.dto.response.ProductStreakColorUpdateResDto;
import life.bareun.diary.product.dto.response.ProductTreeColorUpdateResDto;
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
    public ResponseEntity<BaseResponse<ProductListResDto>> productList() {
        ProductListResDto products = productService.productList();

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
    public ResponseEntity<BaseResponse<ProductStreakColorUpdateResDto>> gotchaStreak() {
        ProductStreakColorUpdateResDto streakColorUpdateRes = productService.buyStreakGotcha();
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
    public ResponseEntity<BaseResponse<ProductTreeColorUpdateResDto>> gotchaTree() {
        ProductTreeColorUpdateResDto treeColorUpdateRes = productService.buyTreeGotcha();
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "나무 테마 변경권을 구매했습니다.",
                    treeColorUpdateRes
                )
            );
    }

    @PatchMapping("/recovery")
    public ResponseEntity<BaseResponse<ProductRecoveryPurchaseResDto>> buyRecovery() {
        ProductRecoveryPurchaseResDto productRecoveryPurchaseResDto = productService.buyRecovery();
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "스트릭 리커버리를 구매했습니다.",
                    productRecoveryPurchaseResDto
                )
            );
    }

}
