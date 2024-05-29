DROP DATABASE IF EXISTS `bareun`;
CREATE DATABASE `bareun`;
USE `bareun`;

########################################################################################## 스트릭
######################################################################### 스트릭 색상 등급
-- 스트릭 색상 등급 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_streak_color_grade`(
    IN `name_param` VARCHAR(31),
    IN `weight_param` FLOAT
)
    BEGIN
        INSERT
            INTO `streak_color_grade` (
                `name`,
                `weight`
            )
            VALUES (
                `name_param`,
                `weight_param`
            )
        ;
    END$$
DELIMITER ;

-- 스트릭 색상 테마 등급 테이블
DROP TABLE IF EXISTS `streak_color_grade`;
CREATE TABLE `streak_color_grade`(
    `id`                          INT(2) PRIMARY KEY AUTO_INCREMENT,
    `name`                        ENUM('COMMON', 'RARE', 'UNIQUE') NOT NULL,
    `weight`                      FLOAT UNIQUE NOT NULL
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

-- 스트릭 색상 테마 등급 데이터 삽입
CALL `insert_streak_color_grade`('COMMON', 90);
CALL `insert_streak_color_grade`('RARE', 15);
CALL `insert_streak_color_grade`('UNIQUE', 5);

######################################################################### 스트릭 색상 등급
-- 스트릭 색상 테마 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_streak_color`(
    IN `name_param` VARCHAR(31),
    IN `grade_id_param` BIGINT
)
    BEGIN
        INSERT
            INTO `streak_color`(
                `name`,
                `grade_id`
            )
            VALUES (
                `name_param`,
                `grade_id_param`
            );
    END$$
DELIMITER ;

-- 스트릭 색상 테마 테이블 생성
DROP TABLE IF EXISTS `streak_color`;
CREATE TABLE `streak_color`(
    `id`                          INT(5) PRIMARY KEY AUTO_INCREMENT,
    `name`                        VARCHAR(31) UNIQUE NOT NULL,
    `grade_id`                    INT,
    FOREIGN KEY (`grade_id`) REFERENCES `streak_color_grade`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

-- 스트릭 색상 테마 데이터 삽입
CALL `insert_streak_color`('red', 1);
CALL `insert_streak_color`('purple', 1);
CALL `insert_streak_color`('indigo', 1);
CALL `insert_streak_color`('blue', 1);
CALL `insert_streak_color`('green', 1);
CALL `insert_streak_color`('yellow', 1);
CALL `insert_streak_color`('orange', 1);
CALL `insert_streak_color`('black', 1);

CALL `insert_streak_color`('wine', 2);
CALL `insert_streak_color`('gold', 2);
CALL `insert_streak_color`('bareun_sanghwal', 2);
CALL `insert_streak_color`('minchodan', 2);
CALL `insert_streak_color`('cherry_blossom', 2);
CALL `insert_streak_color`('sky', 2);
CALL `insert_streak_color`('spring', 2);

CALL `insert_streak_color`('dippindots', 3);
CALL `insert_streak_color`('rainbow', 3);
CALL `insert_streak_color`('rose', 3);
CALL `insert_streak_color`('sunny_summer', 3);


########################################################################################## 나무
######################################################################### 나무
-- 나무 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_tree`(
    IN `level_param` INT,
    IN `range_from` INT,
	IN `range_to` INT
)
    BEGIN
        INSERT
            INTO `tree`(
                `level`,
                `range_from`,
                `range_to`
            )
            VALUES (
                `level_param`,
                `range_from`,
				`range_to`
            );
    END$$
DELIMITER ;

DROP TABLE IF EXISTS `tree`;
CREATE TABLE `tree`(
    `id`                        INT(5) PRIMARY KEY AUTO_INCREMENT,
    `level`                     INT    NOT NULL,
    `range_from`                INT    NOT NULL,
	`range_to`                  INT    NOT NULL
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

-- 나무 삽입
CALL `insert_tree`(1, 2, 20);
CALL `insert_tree`(2, 4, 30);
CALL `insert_tree`(3, 7, 40);
CALL `insert_tree`(4, 10, 60);
CALL `insert_tree`(5, 15, 90);


######################################################################### 나무 색상 등급
-- 나무 색상 등급 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_tree_color_grade`(
    IN `name_param` VARCHAR(31),
    IN `weight_param` FLOAT
)
    BEGIN
        INSERT
            INTO `tree_color_grade` (
                `name`,
                `weight`
            )
            VALUES (
                `name_param`,
                `weight_param`
            )
        ;
    END$$
DELIMITER ;

-- 나무 색상 테마 등급 테이블
DROP TABLE IF EXISTS `tree_color_grade`;
CREATE TABLE `tree_color_grade`(
    `id`                          INT(2) PRIMARY KEY AUTO_INCREMENT,
    `name`                        ENUM('COMMON', 'UNIQUE') NOT NULL,
    `weight`                      FLOAT UNIQUE NOT NULL
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

-- 나무 색상 테마 등급 데이터 삽입
CALL `insert_tree_color_grade`('COMMON', 80);
CALL `insert_tree_color_grade`('UNIQUE', 20);


######################################################################### 나무 색상 테마
-- 나무 색상 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_tree_color`(
    IN `name_param` VARCHAR(31),
    IN `grade_id_param` INT
)
    BEGIN
        INSERT
            INTO `tree_color`(
                `name`,
                `grade_id`
            )
            VALUES (
                `name_param`,
                `grade_id_param`
            );
    END$$
DELIMITER ;

DROP TABLE IF EXISTS `tree_color`;
CREATE TABLE `tree_color`(
    `id`                          INT(5) PRIMARY KEY AUTO_INCREMENT,
    `name`                        VARCHAR(31) NOT NULL,
    `grade_id`                    INT(5),
    FOREIGN KEY (`grade_id`) REFERENCES `tree_color_grade`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

-- 나무 색상 테마 삽입
CALL insert_tree_color('red', 1);
CALL insert_tree_color('green', 1);
CALL insert_tree_color('blue', 1);
CALL insert_tree_color('yellow', 1);
CALL insert_tree_color('orange', 1);
CALL insert_tree_color('purple', 1);
CALL insert_tree_color('gold', 2);
CALL insert_tree_color('silver', 2);
CALL insert_tree_color('cotton_candy', 2);
CALL insert_tree_color('cherry_blossom', 2);


######################################################################### 오늘의 한마디 테이블 생성
-- 데일리 문구 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_daily_phrase` (
  IN `phrase_param`               VARCHAR(256)
)
    BEGIN
        INSERT
            INTO `daily_phrase`(
                `phrase`
            )
            VALUES (
                `phrase_param`
            )
        ;
    END$$
DELIMITER ;

DROP TABLE IF EXISTS `daily_phrase`;
CREATE TABLE `daily_phrase`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `phrase`                      VARCHAR(256)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 스트릭 문구 테이블 생성
CALL insert_daily_phrase('오늘은 꾸준히 노력하는 하루를 보내세요. 작은 습관이 큰 결과를 만듭니다.');
CALL insert_daily_phrase('성실함은 당신을 결코 배신하지 않습니다. 오늘도 한 걸음 더 나아가세요.');
CALL insert_daily_phrase('매일 조금씩 발전하는 습관을 기르세요. 오늘의 작은 실천이 미래를 만듭니다.');
CALL insert_daily_phrase('성실함이 길을 만들고, 습관이 그 길을 닦습니다. 오늘도 당신의 길을 걸으세요.');
CALL insert_daily_phrase('습관은 인생을 바꾸는 힘이 있습니다. 오늘 하루를 통해 그 힘을 느껴보세요.');
CALL insert_daily_phrase('작은 성실함이 모여 큰 신뢰를 만듭니다. 오늘도 신뢰를 쌓는 하루가 되길.');
CALL insert_daily_phrase('꾸준함은 결국 빛을 발합니다. 오늘도 꾸준히 당신의 길을 가세요.');
CALL insert_daily_phrase('좋은 습관을 계속해서 반복하면, 언젠가는 그것이 당신을 이끌어줍니다.');
CALL insert_daily_phrase('하루하루를 성실히 살아가는 것이 성공으로 가는 지름길입니다.');
CALL insert_daily_phrase('작은 습관이 모여 큰 성과를 만들어냅니다. 오늘의 습관을 점검해보세요.');
CALL insert_daily_phrase('성실함은 모든 문을 열 수 있는 열쇠입니다. 오늘도 그 열쇠를 잘 활용해보세요.');
CALL insert_daily_phrase('오늘의 노력이 내일의 습관이 됩니다. 좋은 습관을 만드는 하루 되세요.');
CALL insert_daily_phrase('습관은 만들기까지는 어렵지만, 한 번 생기면 큰 힘이 됩니다.');
CALL insert_daily_phrase('오늘 한 번 더 노력하는 것이 내일을 변화시킵니다.');
CALL insert_daily_phrase('꾸준한 성실함이 당신의 가장 큰 무기입니다. 오늘도 그 무기를 잘 사용하세요.');
CALL insert_daily_phrase('작은 행동이 습관이 되고, 습관이 당신의 인생을 만듭니다.');
CALL insert_daily_phrase('매일 같은 시간에 일어나는 습관, 그 작은 시작이 큰 힘을 발휘합니다.');
CALL insert_daily_phrase('오늘도 변함없이 최선을 다하는 당신, 그 자체로 빛나고 있습니다.');
CALL insert_daily_phrase('습관이란 자신도 모르는 사이에 자신을 변화시킵니다.');
CALL insert_daily_phrase('성실하게 매일을 살아가다 보면, 어느새 성공의 문턱에 서 있을 것입니다.');
CALL insert_daily_phrase('오늘 하루, 습관을 바꾸려는 노력이 큰 결과를 가져올 것입니다.');
CALL insert_daily_phrase('성실함은 늘 보상을 가져다 줍니다. 오늘의 보상을 기대해도 좋습니다.');
CALL insert_daily_phrase('작은 습관의 반복이 큰 성공을 만듭니다. 오늘도 작은 성공을 쌓아가세요.');
CALL insert_daily_phrase('성실함으로 하루를 시작하는 것만으로도 큰 성취를 이룰 수 있습니다.');
CALL insert_daily_phrase('습관을 통해 삶의 질을 높일 수 있습니다. 오늘도 자신을 단련하세요.');
CALL insert_daily_phrase('꾸준히 임하는 자세가 오늘 당신을 빛나게 할 것입니다.');
CALL insert_daily_phrase('오늘 하루 성실하게 살면, 그 하루가 당신을 변화시킵니다.');
CALL insert_daily_phrase('좋은 습관은 좋은 인생을 만듭니다. 오늘도 좋은 습관을 실천하세요.');
CALL insert_daily_phrase('꾸준함이 가장 확실한 성공의 비결입니다. 오늘도 멈추지 마세요.');
CALL insert_daily_phrase('하루의 작은 성실함이 모여 인생을 만듭니다. 오늘도 성실한 하루를.');
CALL insert_daily_phrase('매일의 노력이 모여 비로소 대성공의 날이 옵니다.');
CALL insert_daily_phrase('오늘 하루를 성실하게 보내세요. 그것이 내일을 만듭니다.');
CALL insert_daily_phrase('습관은 삶의 거울입니다. 오늘의 거울을 깨끗이 닦아놓으세요.');
CALL insert_daily_phrase('성실함은 시간이 지남에 따라 그 가치가 커집니다.');
CALL insert_daily_phrase('매일 같은 행동을 반복하는 것에서 변화의 기회가 옵니다.');
CALL insert_daily_phrase('습관을 만들어가는 과정 자체가 성장입니다.');
CALL insert_daily_phrase('오늘 당신이 한 성실한 행동이 미래를 밝힙니다.');
CALL insert_daily_phrase('습관의 힘을 믿으세요. 오늘의 작은 변화가 큰 결과를 가져옵니다.');
CALL insert_daily_phrase('성실함은 결코 시간 낭비가 아닙니다. 매 순간 최선을 다하세요.');
CALL insert_daily_phrase('작은 습관이 모여 삶을 변화시킵니다. 오늘 하루도 그 변화를 위한 하루가 되세요.');
CALL insert_daily_phrase('꾸준함으로 일관성을 가지세요. 그것이 성공으로 이끕니다.');
CALL insert_daily_phrase('매일 조금씩 발전하는 자신을 느낄 수 있을 것입니다.');
CALL insert_daily_phrase('성실하게 매 순간을 살아가는 것이 진정한 성공입니다.');
CALL insert_daily_phrase('작은 실천이 모여 큰 변화를 이끕니다. 오늘도 그 변화를 만드세요.');
CALL insert_daily_phrase('습관은 우리의 미래를 만드는 거대한 힘입니다. 그 힘을 실천하세요.');
CALL insert_daily_phrase('오늘 하루를 성실하게 보내는 것만으로도 큰 성과를 얻을 수 있습니다.');
CALL insert_daily_phrase('꾸준한 노력으로 하루하루가 쌓이면, 언젠가는 큰 성공의 날이 올 것입니다.');
CALL insert_daily_phrase('성실하게 살아가다 보면 어느새 목표에 다다를 수 있습니다.');
CALL insert_daily_phrase('습관은 당신이 누구인지를 말해줍니다. 오늘도 좋은 습관을 선택하세요.');
CALL insert_daily_phrase('오늘의 작은 습관이 내일의 큰 성과를 만듭니다.');
CALL insert_daily_phrase('성실함은 언제나 최고의 선택입니다. 오늘도 그 선택을 고수하세요.');
CALL insert_daily_phrase('매일의 성실함이 모여 꿈을 실현시킵니다.');
CALL insert_daily_phrase('좋은 습관으로 하루를 시작하면, 하루 종일 좋은 기운이 계속됩니다.');
CALL insert_daily_phrase('오늘의 작은 습관이 내일의 큰 변화를 가져옵니다.');
CALL insert_daily_phrase('습관은 당신의 미래를 예측하는 지표입니다. 오늘도 좋은 습관을 만드세요.');
CALL insert_daily_phrase('매일 조금씩 성실하게 살면, 인생 전체가 바뀔 것입니다.');
CALL insert_daily_phrase('꾸준한 습관이 당신을 성공으로 이끕니다.');
CALL insert_daily_phrase('성실함이 오늘을 밝히고 내일을 만듭니다.');
CALL insert_daily_phrase('습관을 잘 관리하세요. 그것이 바로 당신의 미래를 만드는 길입니다.');
CALL insert_daily_phrase('오늘 하루를 통해 좋은 습관을 만들어가세요.');
CALL insert_daily_phrase('성실함이 당신의 인생에 긍정적인 영향을 미칩니다.');
CALL insert_daily_phrase('매일의 노력이 습관이 되면, 그 습관이 당신을 성공으로 이끕니다.');
CALL insert_daily_phrase('성실함과 꾸준함은 언제나 보상받습니다.');
CALL insert_daily_phrase('습관은 생각보다 더 큰 힘을 갖고 있습니다. 그 힘을 활용하세요.');
CALL insert_daily_phrase('매일 성실하게 살아가면, 삶이 점점 더 나아집니다.');
CALL insert_daily_phrase('습관의 힘을 경험하세요. 매일 같은 시간에 일어나 보세요.');
CALL insert_daily_phrase('오늘의 작은 습관이 당신을 성공으로 이끕니다.');
CALL insert_daily_phrase('성실함으로 일관하면, 세상은 당신에게 길을 열어줄 것입니다.');
CALL insert_daily_phrase('매일을 성실하게 살아가세요. 그것이 바로 성공으로 가는 길입니다.');
CALL insert_daily_phrase('습관이 당신을 만듭니다. 오늘 하루를 잘 살아가세요.');
CALL insert_daily_phrase('성실함은 큰 보상을 가져옵니다. 오늘도 그 보상을 위해 노력하세요.');
CALL insert_daily_phrase('좋은 습관은 인생을 바꾸는 강력한 도구입니다.');
CALL insert_daily_phrase('습관을 통해 삶을 변화시키세요. 매일 조금씩 변화를 주세요.');
CALL insert_daily_phrase('꾸준한 노력이 큰 결과를 만듭니다. 오늘도 꾸준히 노력하세요.');
CALL insert_daily_phrase('매일의 성실함이 당신을 더 멀리까지 이끌 것입니다.');
CALL insert_daily_phrase('오늘 하루 성실하게 살아가는 것이 성공의 비결입니다.');
CALL insert_daily_phrase('습관을 잘 가꾸면, 그것이 당신의 가장 큰 자산이 됩니다.');
CALL insert_daily_phrase('성실하게 매 순간을 살아가세요. 그것이 인생을 변화시키는 방법입니다.');
CALL insert_daily_phrase('습관을 바꾸면 삶이 바뀝니다. 오늘부터 습관을 점검해보세요.');
CALL insert_daily_phrase('매일 같은 시간에 일어나는 습관을 만드세요. 그것이 모든 것을 바꿀 수 있습니다.');
CALL insert_daily_phrase('꾸준히 성실함을 유지하는 것이 성공의 열쇠입니다.');
CALL insert_daily_phrase('매일의 작은 노력이 쌓여 큰 성공을 이룹니다.');
CALL insert_daily_phrase('좋은 습관은 당신의 가장 좋은 친구입니다. 오늘도 그 친구와 함께 하세요.');
CALL insert_daily_phrase('매일 조금씩 발전하는 습관을 키우세요. 그것이 인생을 바꿉니다.');
CALL insert_daily_phrase('성실함은 당신의 가장 좋은 특성입니다. 오늘도 그 특성을 발휘하세요.');

########################################################################################## 사용자
######################################################################### 사용자 테이블 생성
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`(
    `id`                        BIGINT PRIMARY KEY AUTO_INCREMENT,
    `sub`                       VARCHAR(255) UNIQUE,
    `nickname`                  VARCHAR(20),
    `gender`                    ENUM('M', 'F', 'N') DEFAULT 'N',
    `birth`                     DATE,
    `job`                       ENUM('STUDENT', 'EMPLOYEE', 'HOUSEWIFE', 'JOB_SEEKER', 'SELF_EMPLOYED'),
    `provider`                  ENUM('GOOGLE', 'KAKAO') NOT NULL,
    `role`                      ENUM('ROLE_USER', 'ROLE_ADMIN') DEFAULT 'ROLE_USER',
    `created_datetime`          DATETIME DEFAULT NOW(),
    `updated_datetime`          DATETIME ON UPDATE NOW(),
    `point`                     INT(17) UNSIGNED DEFAULT 0,
    `current_streak_color_id`   INT(5),
    `current_tree_id`           INT(5) DEFAULT 1 COMMENT '나무의 레벨, 용량 등의 정보',
    `current_tree_color_id`     INT(5) DEFAULT 11,
    `last_harvested_date`       DATE,
	`paid_recovery_count`       INT(17) DEFAULT 0 COMMENT '사용자가 구매한 리커버리 수',
	`is_deleted`                BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (`current_tree_id`) REFERENCES `tree`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 사용자 리커버리
-- 해당 사용자가 갖고 있는 무료 리커버리 수
DROP TABLE IF EXISTS `member_recovery`;
CREATE TABLE `member_recovery`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_id`                   BIGINT,
    `free_recovery_count`         INT UNSIGNED DEFAULT 1,
	`current_recovery_price`      INT UNSIGNED DEFAULT 140,
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 사용자 한마디
-- 하루마다 갱신되는 오늘의 한마디 테이블
DROP TABLE IF EXISTS `member_daily_phrase`;
CREATE TABLE `member_daily_phrase`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_id`                   BIGINT,
    `daily_phrase_id`             BIGINT,
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`),
    FOREIGN KEY (`daily_phrase_id`) REFERENCES `daily_phrase`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 사용자 스트릭
-- 해당 사용자의 일일 스트릭 현황
DROP TABLE IF EXISTS `member_daily_streak`;
CREATE TABLE `member_daily_streak`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_id`                   BIGINT,
    `created_date`                DATETIME DEFAULT NOW(),
    `achieve_tracker_count`       INT DEFAULT 0,
    `total_tracker_count`         INT DEFAULT 0,
    `is_stared`                   BOOLEAN DEFAULT FALSE,
    `achieve_type`                ENUM('NOT_ACHIEVE', 'ACHIEVE', 'NOT_EXISTED', "RECOVERY") NOT NULL,
    `current_streak`              INT UNSIGNED NOT NULL,
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

-- 해당 사용자의 총 스트릭 현황
DROP TABLE IF EXISTS `member_total_streak`;
CREATE TABLE `member_total_streak`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_id`                   BIGINT,
    `longest_streak`              BIGINT NOT NULL,
    `achieve_streak_count`        BIGINT NOT NULL,
    `total_streak_count`          INT UNSIGNED NOT NULL,
    `total_tracker_count`         INT UNSIGNED NOT NULL,
    `achieve_tracker_count`       INT UNSIGNED NOT NULL,
    `star_count`                  INT UNSIGNED NOT NULL,
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;


########################################################################################## 사용자
######################################################################### 알림 카테고리 생성
-- 해당 카테고리의 이름과 아이콘
DROP TABLE IF EXISTS `notification_category`;
CREATE TABLE `notification_category`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `content`                        VARCHAR(100) NOT NULL,
    `icon`                        VARCHAR(255) NOT NULL
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

-- 알림 카테고리 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_notification_category`(
    IN `content_param` VARCHAR(100),
    IN `icon_param` VARCHAR(255)
)
    BEGIN
        INSERT
            INTO `notification_category`(
                `content`,
                `icon`
            )
            VALUES (
                `content_param`,
                `icon_param`
            );
    END$$
DELIMITER ;

######################################################################### 스트릭 문구 테이블 생성
CALL insert_notification_category('아직 수확하지 않은 랜덤 포인트가 있어요! 지금 바로 수확하러 가볼까요?', 'https://kr.object.ncloudstorage.com//bareun-object-storage/tracker_achieve_image/40fdebdf-940d-4c9c-9376-8a34215b1875.png');
CALL insert_notification_category('오늘의 스트릭을 채울 수 있는 시간이 2시간 남았어요!', 'https://kr.object.ncloudstorage.com//bareun-object-storage/tracker_achieve_image/e073a218-ddef-41c5-b1d7-f4093b782797.png');
CALL insert_notification_category('%s님의 %d년 %d월 리캡이 생성되었어요! 지금 확인해보세요', 'https://kr.object.ncloudstorage.com//bareun-object-storage/tracker_achieve_image/951c6b54-fd6a-439f-973c-35d20912e317.png');
CALL insert_notification_category('홈 화면에서 %s님의 오늘의 한 마디를 확인해보세요', 'https://kr.object.ncloudstorage.com//bareun-object-storage/tracker_achieve_image/8e26d309-4b4f-430e-9cc7-6d3ac7b1d22c.png');
CALL insert_notification_category('오늘로 스트릭 %d일째! %s', 'https://kr.object.ncloudstorage.com//bareun-object-storage/tracker_achieve_image/a1a802a4-a06e-4290-972c-9f5d2ba72b83.png');


######################################################################### 알림 테이블 생성
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `notification_category_id`    BIGINT,
    `member_id`                   BIGINT,
    `content`                     VARCHAR(100) NOT NULL,
    `is_read`                     BOOLEAN DEFAULT FALSE,
    `created_datetime`            DATETIME DEFAULT NOW(),
    FOREIGN KEY (`notification_category_id`) REFERENCES `notification_category`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 스트릭 문구 테이블 생성
-- 스트릭 문구 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_streak_phrase`(
    IN `id_param` VARCHAR(100),
    IN `phrase_param` VARCHAR(255)
)
    BEGIN
        INSERT
            INTO `streak_phrase`(
                `id`,
                `phrase`
            )
            VALUES (
                `id_param`,
                `phrase_param`
            );
    END$$
DELIMITER ;

DROP TABLE IF EXISTS `streak_phrase`;
CREATE TABLE `streak_phrase`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `phrase`                      VARCHAR(256)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

CALL insert_streak_phrase(1, '와우 정말 멋져요!');
CALL insert_streak_phrase(2, '조금만 더 힘내볼까요?');
CALL insert_streak_phrase(3, '끈기가 대단해요!');
CALL insert_streak_phrase(4, '꾸준함이 대단해요!');
CALL insert_streak_phrase(5, '앞으로도 파이팅!');
CALL insert_streak_phrase(6, '해빗 유지의 천재!');
CALL insert_streak_phrase(7, '해빗 마스터를 향해~');
CALL insert_streak_phrase(8, '해빗 없이 못 살아~');
CALL insert_streak_phrase(9, '차곡차곡 쌓여가는 해빗 기록!');
CALL insert_streak_phrase(10, '최장 스트릭을 향해!');


########################################################################################## 리캡
######################################################################### 리캡 생성
DROP TABLE IF EXISTS `recap`;
CREATE TABLE `recap`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_id`                   BIGINT,
    `created_datetime`       
	 
	 
	      DATETIME DEFAULT NOW(),
    `whole_streak`                INT NOT NULL,
    `max_habit_image`             VARCHAR(255),
    `most_frequency_word`         VARCHAR(15),
    `most_frequency_time`         ENUM('DAWN', 'MORNING', 'EVENING', 'NIGHT'),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;


########################################################################################## 해빗
######################################################################### 해빗 생성
-- 설정 가능한 해빗
DROP TABLE IF EXISTS `habit`;
CREATE TABLE `habit`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`                        VARCHAR(63) NOT NULL,
    FULLTEXT INDEX searchNameIdx (`name`) WITH PARSER `ngram`
)  ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 사용자 해빗 생성
-- 사용자가 실제로 추가한 해빗
DROP TABLE IF EXISTS `member_habit`;
CREATE TABLE `member_habit`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `habit_id`                    BIGINT,
    `member_id`                   BIGINT,
    `alias`                       VARCHAR(63) NOT NULL,
    `created_datetime`            DATETIME DEFAULT NOW(),
    `succeeded_datetime`          DATETIME NULL ON UPDATE NOW(),
    `icon`                        VARCHAR(63) NOT NULL,
    `is_deleted`                  BOOLEAN DEFAULT FALSE,
    `maintain_way`                ENUM('DAY', 'PERIOD'),
    `maintain_amount`             INT NOT NULL,
    FOREIGN KEY (`habit_id`) REFERENCES `habit`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_UNICODE_CI;

######################################################################### 사용자 해빗의 요일 생성
-- 사용자가 실제로 추가한 해빗이 만약 요일 방식일 때
DROP TABLE IF EXISTS `habit_day`;
CREATE TABLE `habit_day`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_habit_id`             BIGINT,
    `day`                         INT NOT NULL,
    FOREIGN KEY (`member_habit_id`) REFERENCES `member_habit`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 해빗 트래커 생성
DROP TABLE IF EXISTS `habit_tracker`;
CREATE TABLE `habit_tracker`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_id`                   BIGINT,
    `member_habit_id`             BIGINT,
    `succeeded_time`              DATETIME NULL ON UPDATE NOW(),
    `content`                     VARCHAR(127),
    `image`                       VARCHAR(255),
    `day`                         INT NOT NULL,
    `created_year`                INT NOT NULL,
    `created_month`               INT NOT NULL,
    `created_day`                 INT NOT NULL,
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`),
    FOREIGN KEY (`member_habit_id`) REFERENCES `member_habit`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 추천을 위한 해빗 생성
-- 해빗 추천은 카테고리로 묶여 하나의 set으로 취급된다.
DROP TABLE IF EXISTS `habit_recommend`;
CREATE TABLE `habit_recommend`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `habit_id`                    BIGINT,
    FOREIGN KEY (habit_id) REFERENCES `habit`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 해빗 생성
DROP TABLE IF EXISTS `habit_daily_streak`;
CREATE TABLE `habit_daily_streak`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_habit_id`             BIGINT,
    `created_date`                DATE DEFAULT (CURRENT_DATE) NOT NULL,
    `achieve_type`                ENUM('NOT_ACHIEVE', 'ACHIEVE', 'NOT_EXISTED') NOT NULL,
    `current_streak`              INT UNSIGNED NOT NULL,
    FOREIGN KEY (`member_habit_id`) REFERENCES `member_habit`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

DROP TABLE IF EXISTS `habit_total_streak`;
CREATE TABLE `habit_total_streak`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_habit_id`             BIGINT,
    `longest_streak`              BIGINT DEFAULT 0,
    `achieve_tracker_count`       INT UNSIGNED DEFAULT 0,
    `total_tracker_count`         INT UNSIGNED DEFAULT 0,
    FOREIGN KEY (`member_habit_id`) REFERENCES `member_habit`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

######################################################################### 리캡 관련 테이블 생성
DROP TABLE IF EXISTS `recap_habit_ratio`;
CREATE TABLE `recap_habit_ratio`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `habit_id`                    BIGINT,
    `recap_id`                    BIGINT,
    `created_year`                INT,
    `created_month`               INT,
    `ratio`                       DOUBLE NOT NULL,
    FOREIGN KEY (`habit_id`) REFERENCES `habit`(`id`),
    FOREIGN KEY (`recap_id`) REFERENCES `recap`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

DROP TABLE IF EXISTS `recap_habit_accomplished`;
CREATE TABLE `recap_habit_accomplished`(
    `id`                          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `member_habit_id`             BIGINT,
    `recap_id`                    BIGINT,
    `created_year`                INT,
    `created_month`               INT,
    `is_best`                     BOOLEAN DEFAULT FALSE,
    `action_count`                INT,
    `miss_count`                  INT,
    `achievement_rate`            INT,
    FOREIGN KEY (`member_habit_id`) REFERENCES`member_habit`(`id`),
    FOREIGN KEY (`recap_id`) REFERENCES `recap`(`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;

########################################################################################## 상품
-- 상품 데이터 삽입 프로시저
DELIMITER $$
CREATE PROCEDURE `insert_product` (
  IN `product_key_param`  VARCHAR(30),
  IN `name_param`         VARCHAR(100),
  IN `introduction_param` VARCHAR(300),
  IN `decription_param`   VARCHAR(300),
  IN `price_param`        INT
)
    BEGIN
        INSERT
            INTO `product`(
                `product_key`,
                `name`,
                `introduction`,
                `description`,
                `price`
            )
            VALUES (
				    `product_key_param`,
                `name_param`,
                `introduction_param`,
                `decription_param`,
                `price_param`
            )
        ;
    END$$
DELIMITER ;

######################################################################### 상품 테이블 생성
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`(
    `id`                          INT(2) PRIMARY KEY AUTO_INCREMENT,
    `product_key`                 VARCHAR(30) NOT NULL,
    `name`                        VARCHAR(100) NOT NULL,
    `introduction`                VARCHAR(150) NOT NULL,
    `description`                 VARCHAR(300) NOT NULL,
    `price`                       INT NOT NULL
) DEFAULT CHARACTER SET utf8mb4 COLLATE UTF8MB4_GENERAL_CI;
CALL insert_product(
    'gotcha_streak',
    '알쏭달쏭 스트릭',
    '스트릭 색상을 랜덤으로 바꿔요',
    '사용하면 프로필의 스트릭 색상을 12가지 색상과 일부 특별한 색상 중 하나로 바꿔줘요. 아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요.',
    80
);
CALL insert_product(
    'gotcha_tree',
    '알쏭달쏭 나무',
    '나무 색상을 랜덤으로 바꿔요',
    '사용하면 나무의 색상이 12가지 색상 중 하나로 바뀌어요. 아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요.',
    80
);
CALL insert_product(
    'recovery',
    '스트릭 리커버리',
    '최근 한 달 중 하나의 스트릭을 복구할 수 있어요',
    '최근 한 달 이내의 스트릭 하나를 복구할 수 있어요. 복구된 결과는 리캡에는 포함되지 않아요.',
    150
);

INSERT INTO `habit` (`id`, `name`)
VALUES  (1, '걷기'),
        (2, '러닝'),
        (3, '웨이트 트레이닝'),
        (4, '요가'),
        (5, '필라테스'),
        (6, '홈트레이닝'),
        (7, '수영'),
        (8, '축구'),
        (9, '농구'),
        (10, '배구'),
        (11, '야구'),
        (12, '골프'),
        (13, '배드민턴'),
        (14, '세팍타크로'),
        (15, '핸드볼'),
        (16, '테니스'),
        (17, '게이트볼'),
        (18, '계단 오르기'),
        (19, '비치발리볼'),
        (20, '런지'),
        (21, '스쿼트'),
        (22, '턱걸이'),
        (23, '윗몸 일으키기'),
        (24, '마라톤'),
        (25, '팔굽혀펴기'),
        (26, '제자리달리기'),
        (27, '등산'),
        (28, '노젓기'),
        (29, '자전거타기'),
        (30, '줄넘기'),
        (31, '탁구'),
        (32, '라켓볼'),
        (33, '스케이트'),
        (34, '스키'),
        (35, '볼링'),
        (36, '에어로빅'),
        (37, '플랭크'),
        (38, '크런치'),
        (39, '럭비'),
        (40, '레슬링'),
        (41, '리듬체조'),
        (42, '복싱'),
        (43, '킥복싱'),
        (44, '비치핸드볼'),
        (45, '서핑'),
        (46, '수구'),
        (47, '스쿼시'),
        (48, '소프트볼'),
        (49, '승마'),
        (50, '유도'),
        (51, '태권도'),
        (52, '펜싱'),
        (53, '필드하키'),
        (54, '크로스핏'),
        (55, '하이킹'),
        (56, '스프린트'),
        (57, '사이클링'),
        (58, '무에타이'),
        (59, '주짓수'),
        (60, '클라이밍'),
        (61, '스노우보드'),
        (62, '보디빌딩'),
        (63, '발레'),
        (64, '카누'),
        (65, '카약'),
        (66, '스카이다이빙'),
        (67, '카트 레이싱'),
        (68, '모터 스포츠'),
        (69, '카포에라'),
        (70, '아이스 하키'),
        (71, '컬링'),
        (72, '봅슬레이'),
        (73, '루지'),
        (74, '스켈레톤'),
        (75, '유산소 운동'),
        (76, '무산소 운동'),
        (77, '스킨스쿠버'),
        (78, '씨름'),
        (79, '역도'),
        (80, '피구'),
        (81, '양궁'),
        (82, '검도'),
        (83, '합기도'),
        (84, '로잉'),
        (85, '당구'),
        (86, '크리켓'),
        (87, '티볼'),
        (88, '정구'),
        (89, '마루운동'),
        (90, '택견'),
        (91, '쿵푸'),
        (92, '경보'),
        (93, '보치아'),
        (94, '족구'),
        (95, '킨볼'),
        (96, '얼티밋프리스비'),
        (97, '조정'),
        (98, '펠로타'),
        (99, '스피닝'),
        (100, '래프팅'),
        (101, '하루 계획 세우고 완료하기'),
        (102, '산책하기'),
        (103, '일찍 일어나기'),
        (104, '물 마시기'),
        (105, '청소하기'),
        (106, '분리수거하기'),
        (107, '체조하기'),
        (108, '설거지하기'),
        (109, '세탁하기'),
        (110, '이불접기'),
        (111, '멀티탭 끄기'),
        (112, '콘센트 뽑기'),
        (113, '아침 먹기'),
        (114, '점심 먹기'),
        (115, '저녁 먹기'),
        (116, '야식 먹지않기'),
        (117, '군것질 하지않기'),
        (118, '대중교통 타기'),
        (119, '일정 정리하기'),
        (120, '잠 자기'),
        (121, '스트레칭하기'),
        (122, '조깅하기'),
        (123, '건강검진하기'),
        (124, '샤워하기'),
        (125, '과식하지 않기'),
        (126, '영양제 먹기'),
        (127, '영어단어 외우기'),
        (128, '나쁜 말 하지않기'),
        (129, '길거리에 쓰레기 버리지 않기'),
        (130, '균형잡힌 식사하기'),
        (131, '긍정적인 확언하기'),
        (132, '일회용품 사용 줄이기'),
        (133, '패스트푸드 자제하기'),
        (134, '지각하지 않기'),
        (135, '불평하지 않기'),
        (136, '할 일 미루지 않기'),
        (137, '가계부 작성하기'),
        (138, '잡지 읽기'),
        (139, '긍정적인 자기대화하기'),
        (140, '새로운 경험 하기'),
        (141, '험담 피하기'),
        (142, '절약하기'),
        (143, '과음하지 않기'),
        (144, '메모하기'),
        (145, '손 소독하기'),
        (146, '손 씻기'),
        (147, '애완동물 산책하기'),
        (148, '견과류 먹기'),
        (149, '다이어리 쓰기'),
        (150, '피부 관리하기'),
        (151, '차 마시기'),
        (152, '책상 정리하기'),
        (153, '경청하기'),
        (154, '반성하기'),
        (155, '감사하기'),
        (156, '사랑하기'),
        (157, '칭찬하기'),
        (158, '체성분 검사하기'),
        (159, '체중 재기'),
        (160, '신발 정리하기'),
        (161, '옷장 정리하기'),
        (162, '하루에 한 번 양보하기'),
        (163, '기록하기'),
        (164, '음악 감상하기'),
        (165, '다림질하기'),
        (166, '장식하기'),
        (167, '꾸미기'),
        (168, '안경 닦기'),
        (169, '욕하지 않기'),
        (170, '외국어 공부'),
        (171, '코딩 배우기'),
        (172, '자격증 공부'),
        (173, '동호회 참여'),
        (174, '금융 시장 분석하기'),
        (175, '온라인 강의 시청하기'),
        (176, '학술회의 참여하기'),
        (177, '신문 읽기'),
        (178, '블로그 운영하기'),
        (179, '발표 연습하기'),
        (180, '자기계발 서적 읽기'),
        (181, '독서'),
        (182, '독후감 쓰기'),
        (183, '진로 탐색하기'),
        (184, '취업 활동'),
        (185, '독서모임 참여하기'),
        (186, '필사하기'),
        (187, '오디오북 듣기'),
        (188, '전자책 읽기'),
        (189, '자원봉사하기'),
        (190, '온라인 포럼 참여하기'),
        (191, '토이 프로젝트 개발'),
        (192, '세미나 참석하기'),
        (193, '스터디 참여하기'),
        (194, '환경보호 실천하기'),
        (195, '지역사회 기여하기'),
        (196, '휴식하기'),
        (197, '디지털디톡스'),
        (198, '특강 듣기'),
        (199, '사회 문제 분석하기'),
        (200, '공부하기'),
        (201, '우선순위 설정하기'),
        (202, '수학 퍼즐 풀기'),
        (203, '미술관 방문하기'),
        (204, '목표 설정하기'),
        (205, '훈련하기'),
        (206, '사색하기'),
        (207, '자연 체험하기'),
        (208, '고전 읽기'),
        (209, '기업분석하기'),
        (210, '명언 쓰기'),
        (211, '고사성어 쓰기'),
        (212, '악기 연주하기'),
        (213, '그림 그리기'),
        (214, '댄스 배우기'),
        (215, '꽃꽂이 하기'),
        (216, '요리하기'),
        (217, '키보드 관리하기'),
        (218, '캘리그라피 하기'),
        (219, '프라모델 조립하기'),
        (220, '레고 블럭 조립하기'),
        (221, '애니메이션 시청하기'),
        (222, '드라마 시청하기'),
        (223, '영화 시청하기'),
        (224, '맛집 다녀오기'),
        (225, '미술관 관람하기'),
        (226, '수집하기'),
        (227, '뜨개질하기'),
        (228, '십자수하기'),
        (229, '스킬 자수하기'),
        (230, '펠트 인형 만들기'),
        (231, '베이킹하기'),
        (232, '사진 찍기'),
        (233, '글 쓰기'),
        (234, '사격하기'),
        (235, '에어소프트 게임하기'),
        (236, '해외여행하기'),
        (237, '국내여행하기'),
        (238, '연극 관람하기'),
        (239, '연주회 관람하기'),
        (240, '뮤지컬 관람하기'),
        (241, '콘서트 관람하기'),
        (242, '도예'),
        (243, '서예'),
        (244, '만화책 읽기'),
        (245, '작곡하기'),
        (246, '칵테일 제조하기'),
        (247, '홈 레코딩하기'),
        (248, '생활용품 제작하기'),
        (249, '종이접기'),
        (250, '나무 공예'),
        (251, '종이 공예'),
        (252, '다도'),
        (253, '술 음미하기'),
        (254, '텃밭 가꾸기'),
        (255, '별 보기'),
        (256, '인형 만들기'),
        (257, '홈카페 메뉴 만들기'),
        (258, '맛있는 음식 먹기'),
        (259, '축제 참여하기'),
        (260, '보드게임하기'),
        (261, '클레이 공예'),
        (262, '원예'),
        (263, '비트박스하기'),
        (264, '방 꾸미기'),
        (265, '반려동물과 시간 보내기'),
        (266, '가구 제작하기'),
        (267, '드림캐처 만들기'),
        (268, '액자 만들기'),
        (269, '글라스 데코 만들기'),
        (270, '미니어처 만들기'),
        (271, '가죽 공예'),
        (272, '네온사인 만들기'),
        (273, '캠핑하기'),
        (274, '노래하기'),
        (275, '오락실 가기'),
        (276, '놀이공원 가기'),
        (277, '직소 퍼즐 맞추기'),
        (278, '온라인 게임하기'),
        (279, '퍼즐 풀기'),
        (280, '방탈출하기'),
        (281, '영화관 가기'),
        (282, '화분 가꾸기'),
        (283, '마술 배우기'),
        (284, '마사지 받기'),
        (285, '알고리즘 문제 풀기'),
        (286, '수수께끼 풀기'),
        (287, '채보하기'),
        (288, '커스텀 키보드 만들기'),
        (289, '드라이브하기'),
        (290, '금속 공예'),
        (291, '명상'),
        (292, '스트레스 관리하기'),
        (293, '가족과 연락하기'),
        (294, '안 가본 곳 가보기'),
        (295, '좋아하는 노래 듣기'),
        (296, '낮잠자기'),
        (297, '과거에 쓴 일기 읽어보기'),
        (298, '심리 상담 받기'),
        (299, '감정 일기 쓰기'),
        (300, '템플 스테이하기'),
        (301, '종교 활동하기'),
        (302, '업무 체크리스트 관리하기'),
        (303, '제 시간에 업무 완료하기'),
        (304, '동료에게 1일 1 칭찬하기'),
        (305, '바른 자세로 앉기'),
        (306, '쪽잠자기'),
        (307, '일찍 출근하기'),
        (308, '피드백 듣기'),
        (309, '인사하기'),
        (310, '업무 매뉴얼 숙지하기'),
        (311, '나 자신 되돌아보기'),
        (312, '저축하기'),
        (313, '투자하기');
