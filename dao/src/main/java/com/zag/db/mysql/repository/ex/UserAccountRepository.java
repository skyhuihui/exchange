package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.UserAccountPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: repository
 * @Author: skyhuihui
 * @CreateDate: 2018/8/23 11:38
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/23 11:38
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountPo, Long>, UserAccountDao{

    @Query(value = "SELECT accountPo FROM UserAccountPo accountPo  WHERE accountPo.userPo.id = :userId")
    UserAccountPo findByUserPo(@Param("userId") Long userId);

    UserAccountPo findByAddress(String address);
}
