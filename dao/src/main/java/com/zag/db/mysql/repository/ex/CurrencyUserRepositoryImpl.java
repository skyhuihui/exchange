package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.CurrencyUserPo;
import com.zag.db.mysql.po.web3.UserAccountPo;
import com.zag.support.jpa.repository.CommonJpaRepositoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/28 10:20
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:20
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class CurrencyUserRepositoryImpl extends CommonJpaRepositoryBean<CurrencyUserPo, Long> implements CurrencyUserDao{
    @Autowired
    public CurrencyUserRepositoryImpl(EntityManager em) {
        super(CurrencyUserPo.class, em);
    }
}