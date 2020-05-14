package com.wen.spit.repository;

import com.wen.common.model.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpitRepository extends MongoRepository<Spit, String> {

    Page<Spit> findByParentId(String parentId, Pageable pageable);

}
