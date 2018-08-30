package com.zag.service.web3;

import com.zag.vo.web3.req.ArticleAddReqVo;
import com.zag.vo.web3.req.ArticleReqVo;
import com.zag.vo.web3.resp.ArticleRespVo;

import java.util.List;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.service.web3
 * @ClassName: ArticleService
 * @Description: article  service
 * @Author: skyhuihui
 * @CreateDate: 2018/8/2 16:43
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/2 16:43
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public interface ArticleService {

    //检索用户文章数据
    List<ArticleRespVo> selectArticle(ArticleReqVo reqVo);

    //检索用户文章数据
    List<ArticleRespVo> selectAllArticle(ArticleReqVo reqVo);

    //添加用户文章数据
    List<ArticleRespVo> insertArticle(ArticleAddReqVo reqVo);

}
