package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)    // 데이터 변경 시 꼭 필요
@RequiredArgsConstructor   // final로 생성한 멤버에 대해서만 생성자가 만들어짐
public class MemberService {

    private final MemberRepository memberRepository;

    /*@Autowired   // setter 메서드를 이용한 주입 => 필드보다 덜 까다로움
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    // @Autowired   // 생성자를 이용한 주입 [권장]  *최근에는 @Autowired 어노테이션을 빼도 생성자만 있으면 자동으로 스프링에서 주입해줌
    /*public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional(readOnly = false)     // false를 unique 제약조건으로 거는 것을 권장
    public Long join(Member member) {
        validateDuplicateMember(member);  // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
