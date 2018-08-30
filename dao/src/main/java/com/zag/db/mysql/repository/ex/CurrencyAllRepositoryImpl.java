package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.CurrencyAllPo;
import com.zag.support.jpa.repository.CommonJpaRepositoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:17
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class CurrencyAllRepositoryImpl extends CommonJpaRepositoryBean<CurrencyAllPo, Long> implements CurrencyAllDao{
    @Autowired
    public CurrencyAllRepositoryImpl(EntityManager em) {
        super(CurrencyAllPo.class, em);
    }
}
