package com.zag.rest.controller.web3j;

import com.zag.rest.controller.BaseController;
import com.zag.service.web3.ArticleService;
import com.zag.support.web.handler.JSONResult;
import com.zag.vo.web3.req.ArticleAddReqVo;
import com.zag.vo.web3.req.ArticleReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: web3-zag
 * @Package: com.zag.rest.controller.web3j
 * @ClassName: Web3jController
 * @Description: 控制器
 * @Author: skyhuihui
 * @CreateDate: 2018/8/2 16:39
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/2 16:39
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "app/api/v1/web3")
public class Web3jController extends BaseController {

    @Autowired
    private ArticleService articleService;

    //查询用户信息
    @ResponseBody
    @RequestMapping(value = "findArticle")
    public JSONResult getArticle(HttpServletRequest request) {
        ArticleReqVo reqVo = super.buildVo(request, ArticleReqVo.class);
        return new JSONResult(articleService.selectArticle(reqVo));
    }

    //查询所有用户信息
    @ResponseBody
    @RequestMapping(value = "findAllArticle")
    public JSONResult getAllArticle(HttpServletRequest request) {
        ArticleReqVo reqVo = super.buildVo(request, ArticleReqVo.class);
        return new JSONResult(articleService.selectAllArticle(reqVo));
    }

    //添加信息
    @ResponseBody
    @RequestMapping(value = "insertArticle")
    public JSONResult insertArticle(HttpServletRequest request) {
        ArticleAddReqVo reqVo = super.buildVo(request, ArticleAddReqVo.class);
        return new JSONResult(articleService.insertArticle(reqVo));
    }
}
