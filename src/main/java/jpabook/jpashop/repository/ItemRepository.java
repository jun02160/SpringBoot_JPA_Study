package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);   // create
        } else {
            /**
             * merge란? 영속성 컨테이너에서 변하지 않음  *병합을 사용하면 원하는 속성만 선택하는 것이 아닌 모든 속성에서 변경이 이루어지므로 주의해야 함
             */
            em.merge(item);    // DB에 등록된 것을 가져온다 = update
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
