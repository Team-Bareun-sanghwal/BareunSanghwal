package life.bareun.diary.product.service;

import java.security.SecureRandom;
import java.util.List;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.product.dto.ProductDto;
import life.bareun.diary.product.dto.response.ProductListResDto;
import life.bareun.diary.product.dto.response.ProductRecoveryPurchaseResDto;
import life.bareun.diary.product.dto.response.ProductStreakColorUpdateResDto;
import life.bareun.diary.product.dto.response.ProductTreeColorUpdateResDto;
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

    private static final String GOTCHA_STREAK_NAME = "알쏭달쏭 스트릭";
    private static final String GOTCHA_TREE_NAME = "알쏭달쏭 나무";
    private static final String STREAK_RECOVERY_NAME = "스트릭 리커버리";
    private static final int FACTOR = 2; // 리커버리 구매 시 곱해질 값

    private final static SecureRandom RANDOM = new SecureRandom();

    private final ProductRepository productRepository;
    private final StreakColorRepository streakColorRepository;
    private final StreakColorGradeRepository streakColorGradeRepository;
    private final TreeColorRepository treeColorRepository;

    private final MemberRepository memberRepository;
    private final MemberRecoveryRepository memberRecoveryRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductListResDto productList() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        int freeRecoveryCount = memberRecoveryRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            )
            .getFreeRecoveryCount();

        List<ProductDto> products = productRepository.findAll().stream()
            .map(ProductMapper.INSTANCE::toProductDto)
            .peek(
                productDto -> {
                    if (productDto.getName().equals(STREAK_RECOVERY_NAME)) {
                        productDto.setPrice(
                            memberRecoveryRepository.findById(id)
                                .orElseThrow(
                                    () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
                                )
                                .getCurrentRecoveryPrice()
                        );
                    }
                }
            )
            .toList();

        return new ProductListResDto(products);
    }


    @Override
    @Transactional
    public ProductStreakColorUpdateResDto buyStreakGotcha() {
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
            () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
        );

        Integer amount = productRepository.findByName(GOTCHA_STREAK_NAME)
            .orElseThrow(() -> new ProductException(ProductErrorCode.NO_SUCH_PRODUCT))
            .getPrice();
        if (member.getPoint() < amount) {
            throw new ProductException(ProductErrorCode.INSUFFICIENT_BALANCE);
        }

        member.usePoint(amount);
        memberRepository.save(member);

        return new ProductStreakColorUpdateResDto(gotchaStreakColor.getName());
    }

    @Override
    @Transactional
    public ProductTreeColorUpdateResDto buyTreeGotcha() {
        // 1. 나무 색 전체 불러오기
        List<TreeColor> treeColors = treeColorRepository.findAll();

        // 2. 랜덤 뽑기
        int treeColorCount = treeColors.size();
        TreeColor gotchaTreeColor = treeColors.get(RANDOM.nextInt(treeColorCount));

        // 3. 나무 색 변경권 가격 정보 얻기
        Integer amount = productRepository.findByName(GOTCHA_TREE_NAME)
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_PRODUCT)
            )
            .getPrice();

        // 4. 포인트 사용하기
        Long memberId = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(memberId)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
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

        return new ProductTreeColorUpdateResDto(gotchaTreeColor.getName());
    }

    @Override
    @Transactional
    public ProductRecoveryPurchaseResDto buyRecovery() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        MemberRecovery memberRecovery = memberRecoveryRepository.findByMemberId(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
            );
        Integer price = memberRecovery.getCurrentRecoveryPrice();
        Integer point = member.getPoint();

        // 사용자 보유 포인트가 부족한 경우 예외 발생
        if (point < price) {
            throw new ProductException(ProductErrorCode.INSUFFICIENT_BALANCE);
        }

        // 구매 후 상태 반영
        member.buyRecovery(price);
        Member updatedMember = memberRepository.save(member);

        memberRecovery.afterPurchaseRecovery(FACTOR);
        memberRecoveryRepository.save(memberRecovery);

        // 반영된 후의 정보 반환
        return ProductRecoveryPurchaseResDto.builder()
            .paidRecoveryCount(updatedMember.getPaidRecoveryCount())
            .build();
    }
}