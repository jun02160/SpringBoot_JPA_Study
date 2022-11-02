package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded   // 내장타입은 둘 중 한 쪽만 있어도 OK
    private Address address;

    /**
     * 컬렉션은 필드에서 바로 초기화 (jpa에서 entity 영속 시 타입이 다르게 변환되기 때문)
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();   // 여기에서 변경해도 Order(FK)값은 변경되지 X

    public Member() {

    }

}
