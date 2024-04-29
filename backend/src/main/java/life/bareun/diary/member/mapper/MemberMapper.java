package life.bareun.diary.member.mapper;

import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(source = "nickname", target = "nickname")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "job", target = "job")
    @Mapping(source = "birth", target = "birthDate")
    MemberInfoResDto toMemberInfoRes(Member member);
}
