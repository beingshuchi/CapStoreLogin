package com.cg.capstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.capstore.beans.WishlistBean;
@Repository
public interface IWishlistRepo extends JpaRepository<WishlistBean, String> {

}
