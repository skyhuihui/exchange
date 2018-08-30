package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.CurrencyPo;
import com.zag.db.mysql.po.web3.UserAccountPo;
import com.zag.support.jpa.repository.CommonJpaRepositoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:11
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:11
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class CurrencyRepositoryImpl extends CommonJpaRepositoryBean<CurrencyPo, Long> implements CurrencyDao{
    @Autowired
    public CurrencyRepositoryImpl(EntityManager em) {
        super(CurrencyPo.class, em);
    }
}