package com.java25.java25.springboot4.ibm.db2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.java25.java25.springboot4.ibm.db2.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Correctly derived: no @Query needed
    User findByUsername(String username);

    // Fixed: Parameter names now match
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);

    // Fixed: Added @Modifying and changed return type to void/int
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    int deleteByUser(@Param("id") int id);

    // Alternative: Use derived delete (cleaner)
    @Transactional
    void deleteById(int id);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)    
    @Transactional
    @Query("UPDATE User u SET u.qrcodeurl = :qrcodeurl, u.secret = :secret, mfaactivated = true WHERE u.id = :id")
    int updateQrcodeurlById(
        @Param("id") Long id, 
        @Param("qrcodeurl") String qrcodeurl, 
        @Param("secret") String secret
    );  
        
    @Modifying(clearAutomatically = true, flushAutomatically = true)     
    @Transactional
    @Query("UPDATE User u SET u.qrcodeurl = NULL, u.secret = NULL, mfaactivated = false WHERE u.id = :id")
    int updateQrcodeurlByIdToNull(
        @Param("id") Long id 
    );      
        
}
