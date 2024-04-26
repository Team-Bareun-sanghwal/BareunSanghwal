package life.bareun.diary.product.service;

import java.security.SecureRandom;
import java.util.List;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.product.dto.ProductDto;
import life.bareun.diary.product.dto.response.ProductListRes;
import life.bareun.diary.product.dto.response.ProductStreakColorUpdateRes;
import life.bareun.diary.product.dto.response.ProductTreeColorUpdateRes;
import life.bareun.diary.product.entity.StreakColor;
import life.bareun.diary.product.entity.StreakColorGrade;
import life.bareun.diary.product.entity.TreeColor;
import life.bareun.diary.product.exception.ProductErrorCode;
import life.bareun.diary.product.exception.ProductException;
import life.bareun.diary.product.mapper.ProductMapper;
import life.bareun.diary.product.repository.ProductRepository;
import life.bareun.diary.product.repository.StreakColorGradeRepository;
import life.bareun.diary.product.repository.StreakColorRepository;
import life.bareun.diary.product.repository.TreeColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private static final String GOTCHA_STREAK_KEY = "gotcha_streak";
    private static final String GOTCHA_TREE_KEY = "gotcha_tree";
    private static final String STREAK_RECOVERY = "streak_recovery";

    private final static SecureRandom RANDOM = new SecureRandom();

    private final ProductRepository productRepository;
    private final StreakColorRepository streakColorRepository;
    private final StreakColorGradeRepository streakColorGradeRepository;
    private final TreeColorRepository treeColorRepository;

    private final MemberRepository memberRepository;
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
                    if (productDto.getKey().equals(STREAK_RECOVERY) && freeRecoveryCount > 0) {
                        productDto.setPrice(0);
                    }
                }
            )
            .toList();

        return new ProductListRes(products);
    }


    @Override
    @Transactional
    public ProductStreakColorUpdateRes buyStreakGotcha() {
        // 1. 스트릭 색상 등급 데이터를 가중치 기준 내림차순으로 정렬한 리스트
        List<StreakColorGrade> streakColorGrades = streakColorGradeRepository.findAll()
            .stream()
            .sorted(
                (o1, o2) -> Float.compare(o1.getWeight(), o2.getWeight())
            )
            .toList();

        // 끝 범위는 전체 색상 가중치의 합
        double bound = streakColorGrades.stream()
            .mapToDouble(val -> Double.valueOf(val.getWeight()))
            .sum();

        // origin 이상 bound 미만의 무작위 실수 값
        // 구간 [1.0, 101.0)의 수 중 랜덤 값을 추출하고
        // 열린 구간 (100.0, 101.0) 사이의 수가 추출되면 100.0으로 치환한다.
        double gotchaGradeWeight = Math.min(RANDOM.nextDouble(bound) + 1, bound);

        // 가중치를 반영한 랜덤 값을 뽑기 위한 변수
        double weightSum = 0.0;
        StreakColorGrade gotchaGrade = null;
        for (StreakColorGrade streakColorGrade : streakColorGrades) {
            weightSum += streakColorGrade.getWeight();
            if (gotchaGradeWeight < weightSum) {
                gotchaGrade = streakColorGrade;
                break;
            }
        }

        List<StreakColor> streakColors = streakColorRepository.findByStreakColorGrade(gotchaGrade);
        StreakColor gotchaStreakColor = streakColors.get(RANDOM.nextInt(streakColors.size()));

        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id).orElseThrow(
            () -> new MemberException(MemberErrorCode.NO_SUCH_USER)
        );

        Integer amount = productRepository.findByKey(GOTCHA_STREAK_KEY)
            .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_KEY))
            .getPrice();
        if (member.getPoint() < amount) {
            throw new ProductException(ProductErrorCode.INSUFFICIENT_BALANCE);
        }

        member.usePoint(amount);
        memberRepository.save(member);

        return new ProductStreakColorUpdateRes(gotchaStreakColor.getName());
    }

    @Override
    @Transactional
    public ProductTreeColorUpdateRes buyTreeGotcha() {
        // 1. 나무 색 전체 불러오기
        List<TreeColor> treeColors = treeColorRepository.findAll();

        // 2. 랜덤 뽑기
        int treeColorCount = treeColors.size();
        TreeColor gotchaTreeColor = treeColors.get(RANDOM.nextInt(treeColorCount));

        // 3. 나무 색 변경권 가격 정보 얻기
        Integer amount = productRepository.findByKey(GOTCHA_TREE_KEY)
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.INVALID_PRODUCT_KEY)
            )
            .getPrice();

        // 4. 포인트 사용하기
        Long memberId = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(memberId)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_USER)
            );
        member.usePoint(amount);

        // 5. 사용자 나무 색 변경
        Integer treeColorId = treeColorRepository.findById(gotchaTreeColor.getId())
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_TREE_COLOR)
            )
            .getId();
        member.changeTreeColor(treeColorId);

        // 6. 결과 반영
        memberRepository.save(member);

        return new ProductTreeColorUpdateRes(gotchaTreeColor.getName());
    }

}
