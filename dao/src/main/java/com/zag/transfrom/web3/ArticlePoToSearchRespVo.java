package com.zag.transfrom.web3;

import com.google.common.base.Function;
import com.zag.db.mysql.po.web3.ArticlePo;
import com.zag.vo.web3.resp.ArticleRespVo;

import javax.annotation.Nullable;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.transfrom.web3
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/2 17:04
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/2 17:04
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class ArticlePoToSearchRespVo  implements Function<ArticlePo, ArticleRespVo> {

    @Override
    public ArticleRespVo apply(ArticlePo article) {
        ArticleRespVo articleRespVo = new ArticleRespVo();
        articleRespVo.setId(article.getId());
        articleRespVo.setAccount(article.getAccount());
        articleRespVo.setContent(article.getContent());
        articleRespVo.setTime(article.getTime());
        return articleRespVo;
    }
}
