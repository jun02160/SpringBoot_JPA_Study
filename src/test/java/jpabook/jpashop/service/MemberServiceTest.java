package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
//    @Autowired EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("Park");

        //when
        Long saveId = memberService.join(member);

        //then
//        em.flush();    // DB의 영속성 컨텍스트에 있는 쿼리를 강제로 출력
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Park1");

        Member member2 = new Member();
        member2.setName("Park1");

        //when
        memberService.join(member1);
        memberService.join(member2);
        /*try {
            memberService.join(member1);
            memberService.join(member2);   // 예외가 발생해야 한다!
        } catch (IllegalStateException e) {
            return;
        }*/

        //then
        fail("예외가 발생해야 한다.");

    }

}