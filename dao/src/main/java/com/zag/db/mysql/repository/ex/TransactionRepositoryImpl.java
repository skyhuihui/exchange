package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.TransactionPo;
import com.zag.support.jpa.repository.CommonJpaRepositoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:39
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:39
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class TransactionRepositoryImpl extends CommonJpaRepositoryBean<TransactionPo, Long> implements TransactionDao{
    @Autowired
    public TransactionRepositoryImpl(EntityManager em) {
        super(TransactionPo.class, em);
    }
}

