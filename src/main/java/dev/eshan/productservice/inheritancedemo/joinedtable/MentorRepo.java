package dev.eshan.productservice.inheritancedemo.joinedtable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jt_mr")
public interface MentorRepo extends JpaRepository<Mentor, Long> {
    @Override
    <S extends Mentor> S save(S entity);
}
