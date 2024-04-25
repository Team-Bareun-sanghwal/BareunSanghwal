package life.bareun.diary.recap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.recap.entity.embed.Occasion;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recap")
public class Recap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    @Column(name = "created_datetime", updatable = false)
    private LocalDateTime createdDatetime;

    @Column(name = "whole_streak")
    private int wholeStreak;

    @Column(name = "max_habit_image")
    private String maxHabitImage;

    @Column(name = "most_frequency_word")
    private String mostFrequencyWord;

    @Column(name = "most_frequency_time")
    @Enumerated(EnumType.STRING)
    private Occasion mostFrequencyTime;

    @Builder
    public Recap(Member member, int wholeStreak, String maxHabitImage, String mostFrequencyWord,
        Occasion mostFrequencyTime) {
        this.member = member;
        this.wholeStreak = wholeStreak;
        this.maxHabitImage = maxHabitImage;
        this.mostFrequencyWord = mostFrequencyWord;
        this.mostFrequencyTime = mostFrequencyTime;
    }
}
