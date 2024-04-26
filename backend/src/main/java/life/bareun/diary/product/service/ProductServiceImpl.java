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

    private static final String GOTCHA_TREE_KEY = "gotcha_tree";

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
                    if (productDto.getKey().equals("streak_recovery") && freeRecoveryCount > 0) {
                        productDto.setPrice(0);
                    }
                }
            )
            .toList();

        return new ProductListRes(products);
    }


    @Override
    @Transactional
    public String buyStreakGotcha() {
        List<StreakColorGrade> streakColorGrades = streakColorGradeRepository.findAll()
            .stream()
            .sorted(
                (o1, o2) -> Float.compare(o1.getWeight(), o2.getWeight())
            )
            .toList();

        double origin = 0;  // 시작 범위
        double bound = streakColorGrades.stream()
            .mapToDouble(val -> Double.valueOf(val.getWeight()))
            .sum();

        double gotchaGradeWeight = RANDOM.nextDouble(origin, bound); // randomDouble(from, to);
        StreakColorGrade gotchaGrade = null;
        for (int i = 0; i < streakColorGrades.size(); ++i) {
            if (gotchaGradeWeight < streakColorGrades.get(i).getWeight()) {
                gotchaGrade = streakColorGrades.get(i);
                break;
            }
        }

        List<StreakColor> streakColors = streakColorRepository.findByStreakColorGrade(gotchaGrade);
        StreakColor gotchaStreakColor = streakColors.get(RANDOM.nextInt(1, streakColors.size()));

        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id).orElseThrow(
            () -> new MemberException(MemberErrorCode.NO_SUCH_USER)
        );

        Integer amount = productRepository.findByKey("gotcha_streak")
            .orElseThrow(() -> new ProductException(ProductErrorCode.INVALID_PRODUCT_KEY))
            .getPrice();
        if (member.getPoint() < amount) {
            throw new ProductException(ProductErrorCode.INSUFFICIENT_BALANCE);
        }

        member.usePoint(amount);
        memberRepository.save(member);

        return gotchaStreakColor.getName();
    }

    @Override
    @Transactional
    public String buyTreeGotcha() {
        // 1. 나무 색 전체 불러오기
        List<TreeColor> treeColors = treeColorRepository.findAll();

        // 2. 랜덤 뽑기
        int treeColorCount = treeColors.size();
        TreeColor gotchTreeColor = treeColors.get(RANDOM.nextInt(treeColorCount));

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
        Integer treeColorId = treeColorRepository.findById(gotchTreeColor.getId())
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_TREE_COLOR)
            )
            .getId();
        member.changeTreeColor(treeColorId);

        // 6. 결과 반영
        memberRepository.save(member);

        return gotchTreeColor.getName();
    }

    // private double randomDouble(double from, double to) {
    //     return from + (RANDOM.nextDouble() * (to - from));
    // }

    // private int randomInt(int from, int to) {
    //     return RANDOM.nextInt(from, to);
    // }
}
