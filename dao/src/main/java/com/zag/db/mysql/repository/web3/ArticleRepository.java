package com.zag.db.mysql.repository.web3;

import com.zag.db.mysql.po.web3.ArticlePo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.db.mysql.repository.web3
 * @ClassName: ArticleRepository
 * @Description: java类作用描述
 * @Author: skyhuihui
 * @CreateDate: 2018/8/2 16:34
 * @UpdateUser: skyhuihui
 * @UpdateDate: 2018/8/2 16:34
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticlePo, Long>, ArticleDao {

    List<ArticlePo> findAllByAccount(String account, Pageable pageable);

}
