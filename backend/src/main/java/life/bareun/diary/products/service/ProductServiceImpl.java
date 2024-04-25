package life.bareun.diary.products.service;

import java.util.List;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.products.dto.ProductDto;
import life.bareun.diary.products.dto.response.ProductListRes;
import life.bareun.diary.products.mapper.ProductMapper;
import life.bareun.diary.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MemberRecoveryRepository memberRecoveryRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductListRes productList() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        int freeRecoveryCount = memberRecoveryRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_USER)
            )
            .getFreeRecovery();

        List<ProductDto> products = productRepository.findAll().stream()
            .map(ProductMapper.INSTANCE::toProductDto)
            .peek(
                productDto -> {
                    if (productDto.getKey().equals("streak_recovery") && freeRecoveryCount > 0) {
                        productDto.setPrice(0);
                    }
                }
            )
            .toList();

        return new ProductListRes(products);
    }
}
