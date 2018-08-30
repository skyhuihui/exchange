package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.UserPo;
import com.zag.support.jpa.repository.CommonJpaRepositoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: UserRepositoryImpl
 * @Description: UserRepositoryImpl 实体
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:32
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:32
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class UserRepositoryImpl  extends CommonJpaRepositoryBean<UserPo, Long> implements UserDao {
    @Autowired
    public UserRepositoryImpl(EntityManager em) {
        super(UserPo.class, em);
    }
}
