package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: UserRepository
 * @Description: UserRepository
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:31
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:31
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserPo, Long>, UserDao {

    /**
     * 手机号密码查询用户
     * @author skyhuihui
     * @version V1.0
     * @param phone
     * @param password
     * @return UserPo
     */
    UserPo findByPhoneAndPassword(String phone, String password);

    /**
     * 邮箱密码查询用户
     * @author skyhuihui
     * @version V1.0
     * @param mailbox
     * @param password
     * @return UserPo
     */
    UserPo findByMailboxAndPassword(String mailbox, String password);

    UserPo findByPhone(String phone);

    UserPo findByMailbox(String mailbox);

    /**
     * 修改用户邮箱
     * @return void
     */
    @Transactional
    @Modifying
    @Query(value = "Update UserPo userPo set userPo.mailbox = :mailbox where userPo.id =:id")
    void updateMailBox(@Param("mailbox") String mailbox, @Param("id") Long id);

    /**
     * 修改用户手机号
     * @return void
     */
    @Transactional
    @Modifying
    @Query(value = "Update UserPo userPo set userPo.phone = :phone where userPo.id =:id")
    void updatePhone(@Param("phone") String phone, @Param("id") Long id);
}
