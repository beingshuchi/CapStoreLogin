package com.cg.capstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.capstore.beans.FeedbackProductBean;
@Repository
public interface IFeedbackProductRepo extends JpaRepository<FeedbackProductBean, String>{
	@Query("SELECT AVG(rating) FROM FeedbackProductBean  ")
	Double avgRating();
}
