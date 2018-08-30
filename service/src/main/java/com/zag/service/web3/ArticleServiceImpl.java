package com.zag.service.web3;

import com.google.common.collect.Lists;
import com.zag.db.mysql.po.web3.ArticlePo;
import com.zag.db.mysql.repository.web3.ArticleRepository;
import com.zag.service.AbstractBaseService;
import com.zag.service.IdService;
import com.zag.transfrom.web3.ArticlePoToSearchRespVo;
import com.zag.vo.web3.req.ArticleAddReqVo;
import com.zag.vo.web3.req.ArticleReqVo;
import com.zag.vo.web3.resp.ArticleRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.service.web3
 * @ClassName: ${TYPE_NAME}
 * @Description: 区块链文章service
 * @Author: skyhuihui
 * @CreateDate: 2018/8/2 16:45
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/2 16:45
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Service
public class ArticleServiceImpl extends AbstractBaseService implements ArticleService  {

    @Autowired
    private ArticleRepository articleRepository;

    //查询用户发表文章
    @Override
    public List<ArticleRespVo> selectArticle(ArticleReqVo reqVo) {
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户账号-------请求vo: {}", reqVo);
        }
        List<ArticlePo> articlePoList=articleRepository.findAllByAccount(reqVo.getAccount(), new PageRequest(reqVo.getPage(), reqVo.getSize()));
        List<ArticleRespVo> articleRespVos= Lists.transform(articlePoList, new ArticlePoToSearchRespVo());
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户账号-------请求: {}, 返回值: {}", reqVo, articleRespVos);
        }
        return articleRespVos;
    }

    @Override
    public List<ArticleRespVo> selectAllArticle(ArticleReqVo reqVo) {
        Page<ArticlePo> articlePoList=articleRepository.findAll(new PageRequest(reqVo.getPage(), reqVo.getSize()));
        List<ArticleRespVo> articleRespVos= Lists.transform(articlePoList.getContent(), new ArticlePoToSearchRespVo());
        if (logger.isDebugEnabled()) {
            logger.debug("返回值: {}", articleRespVos);
        }
        return articleRespVos;
    }

    @Override
    public List<ArticleRespVo> insertArticle(ArticleAddReqVo reqVo) {
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户发表信息-------请求vo: {}", reqVo);
        }
        ArticlePo articlePo = new ArticlePo();
        articlePo.setAccount(reqVo.getAccount());
        articlePo.setContent(reqVo.getContent());
        articlePo.setTime(reqVo.getTime());
        IdService idService=new IdService(0);
        articlePo.setId(idService.nextId());
        articleRepository.save(articlePo);
        List<ArticlePo> articlePoList=articleRepository.findAllByAccount(reqVo.getAccount(), new PageRequest(reqVo.getPage(), reqVo.getSize()));
        List<ArticleRespVo> articleRespVos= Lists.transform(articlePoList, new ArticlePoToSearchRespVo());
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户发表信息-------请求: {}, 返回值: {}", reqVo, articleRespVos);
        }
        return articleRespVos;
    }


}
