package com.zag.db.mysql.repository.web3;

import com.zag.db.mysql.po.web3.ArticlePo;
import com.zag.support.jpa.repository.CommonJpaRepositoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.db.mysql.repository.web3
 * @ClassName: ArticleRepositoryImpl
 * @Description: java类作用描述
 * @Author: skyhuihui
 * @CreateDate: 2018/8/2 16:35
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/2 16:35
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class ArticleRepositoryImpl extends CommonJpaRepositoryBean<ArticlePo, Long> implements ArticleDao  {

    @Autowired
    public ArticleRepositoryImpl(EntityManager em) {
        super(ArticlePo.class, em);
    }
}
