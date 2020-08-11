package com.akproject.runshare.models.data;

import com.akproject.runshare.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Comment findById(int id);

    List<Comment> findByTrail_Id(Integer trail);

    List<Comment> findByRunSession_Id(Integer runSession);
}
