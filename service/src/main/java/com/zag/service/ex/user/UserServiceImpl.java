package com.zag.service.ex.user;

import com.alibaba.fastjson.JSONObject;
import com.zag.core.util.FileUtil;
import com.zag.core.web3_eth.CreatEthAccount;
import com.zag.core.web3_eth.DecryptWallet;
import com.zag.core.web3_eth.EthWithdraw;
import com.zag.db.mysql.po.web3.UserAccountPo;
import com.zag.db.mysql.po.web3.UserPo;
import com.zag.db.mysql.repository.ex.UserAccountRepository;
import com.zag.db.mysql.repository.ex.UserRepository;
import com.zag.service.AbstractBaseService;
import com.zag.service.IdService;
import com.zag.service.JwtSign;
import com.zag.transfrom.ex.UserPoToFindRespVo;
import com.zag.vo.ex.user.req.UserAddMailReqVo;
import com.zag.vo.ex.user.req.UserAddPhoneOrMailReqVo;
import com.zag.vo.ex.user.req.UserAddPhoneReqVo;
import com.zag.vo.ex.user.req.UserFindReqVo;
import com.zag.vo.ex.user.resp.JwtUser;
import com.zag.vo.ex.user.resp.UserRespVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.service.ex.user
 * @ClassName: UserServiceImpl
 * @Description: 用户服务实体
 * @Author: skyhuihui
 * @CreateDate: 2018/8/8 10:37
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/8 10:37
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Service
public class UserServiceImpl extends AbstractBaseService implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserRespVo insertMailUser(UserAddMailReqVo reqVo) {
        if (logger.isDebugEnabled()) {
            logger.debug("邮箱注册-------请求vo: {}", reqVo);
        }
        UserPo userPo;
        userPo = userRepository.findByMailbox(reqVo.getMailbox());
        if(Objects.nonNull(userPo)){
            return null;
        }
        userPo = new UserPo();
        userPo.setName(reqVo.getName());
        userPo.setMailbox(reqVo.getMailbox());
        userPo.setPassword(reqVo.getPassword());
        IdService idService=new IdService(0);
        userPo.setId(idService.nextId());
        userRepository.save(userPo);
        saveUserAccount(userPo);
        userPo = userRepository.findByMailbox(reqVo.getMailbox());
        UserPoToFindRespVo userPoToFindRespVo = new UserPoToFindRespVo();
        UserRespVo userRespVo = userPoToFindRespVo.apply(userPo);
        if(Objects.nonNull(userPo)){
            if(Objects.nonNull(userRespVo)){
                String jwtToken = JwtSign.sign(new JwtUser(userRespVo.getId(), userRespVo.getName(), userRespVo.getType(), userRespVo.getUserExamineEnums()), 24L * 3600L * 1000L * 7L);
                userRespVo.setJwtToken(jwtToken);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("邮箱注册 返回值: {}", userRespVo);
        }
        return userRespVo;
    }

    @Override
    public UserRespVo insertPhoneUser(UserAddPhoneReqVo reqVo) {
        if (logger.isDebugEnabled()) {
            logger.debug("手机注册-------请求vo: {}", reqVo);
        }
        UserPo userPo;
        userPo = userRepository.findByPhone(reqVo.getPhone());
        if(Objects.nonNull(userPo)){
            return null;
        }
        userPo = new UserPo();
        userPo.setArea(reqVo.getArea());
        userPo.setName(reqVo.getName());
        userPo.setPhone(reqVo.getPhone());
        userPo.setPassword(reqVo.getPassword());
        IdService idService=new IdService(0);
        userPo.setId(idService.nextId());
        userRepository.save(userPo);
        saveUserAccount(userPo);
        userPo = userRepository.findByPhone(reqVo.getPhone());
        UserPoToFindRespVo userPoToFindRespVo = new UserPoToFindRespVo();
        UserRespVo userRespVo = userPoToFindRespVo.apply(userPo);
        if(Objects.nonNull(userPo)){
            if(Objects.nonNull(userRespVo)){
                String jwtToken = JwtSign.sign(new JwtUser(userRespVo.getId(), userRespVo.getName(), userRespVo.getType(), userRespVo.getUserExamineEnums()), 24L * 3600L * 1000L * 7L);
                userRespVo.setJwtToken(jwtToken);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("手机注册 返回值: {}", userRespVo);
        }
        return userRespVo;
    }

    @Override
    public UserRespVo findUser(UserFindReqVo reqVo) {
        if (logger.isDebugEnabled()) {
            logger.debug("手机/邮箱登录-------请求vo: {}", reqVo);
        }
        UserRespVo userRespVo = new UserRespVo();
        UserPo userPo;
        if(StringUtils.isNotEmpty(reqVo.getMailbox())){
            userPo = userRepository.findByMailboxAndPassword(reqVo.getMailbox(), reqVo.getPassword());
        }else{
            userPo = userRepository.findByPhoneAndPassword(reqVo.getPhone(), reqVo.getPassword());
        }
        if(Objects.nonNull(userPo)){
            UserPoToFindRespVo userPoToFindRespVo = new UserPoToFindRespVo();
            userRespVo = userPoToFindRespVo.apply(userPo);
            if(Objects.nonNull(userRespVo)){
                String jwtToken = JwtSign.sign(new JwtUser(userRespVo.getId(), userRespVo.getName(), userRespVo.getType(), userRespVo.getUserExamineEnums()), 24L * 3600L * 1000L * 7L);
                userRespVo.setJwtToken(jwtToken);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("登录-------请求: {}, 返回值: {}", reqVo, userRespVo);
        }
        return userRespVo;
    }

    @Override
    public UserRespVo findUserById(Long id) {
        UserPo userPo = userRepository.findOne(id);
        UserPoToFindRespVo userPoToFindRespVo = new UserPoToFindRespVo();
        UserRespVo userRespVo = userPoToFindRespVo.apply(userPo);
        if (logger.isDebugEnabled()) {
            logger.debug("当前请求用户, 返回值: {}", userRespVo);
        }
        return userRespVo;
    }

    @Override
    public UserRespVo addPhoneOrMail(UserAddPhoneOrMailReqVo reqVo) {
        if (logger.isDebugEnabled()) {
            logger.debug("已存在用户添加手机号/邮箱号-------请求vo: {}", reqVo);
        }
        if(StringUtils.isNotEmpty(reqVo.getMail())){
            userRepository.updateMailBox(reqVo.getMail(), reqVo.getUserId());
        }else{
            userRepository.updatePhone(reqVo.getPhone(), reqVo.getUserId());
        }
        return findUserById(reqVo.getUserId());
    }

    // 保存用户区块链账户地址
    private void saveUserAccount(UserPo userPo){
        IdService idService=new IdService(0);
        UserAccountPo userAccountPo = new UserAccountPo();
        // 保存钱包到本地
        String ethAccount = CreatEthAccount.saveEthAccount("E:\\Wallet\\"+userPo.getId()+"\\eth",);//  你自己设置的密码
        // 存储地址进总账户， （如果自己搭节点可不用这种方法，infura 不支持admin创建账户，无法查看节点创建的所有账户，所以要进行保存进总账户地址里）
        String keystore = FileUtil.readToString("E:\\Wallet\\"+userPo.getId()+"\\eth\\"+ethAccount);
        JSONObject jsStr = JSONObject.parseObject(keystore);
        FileUtil.saveFile("E:\\Wallet\\all\\all.txt","0x"+jsStr.get("address").toString());
        // 信息保存进数据库
        userAccountPo.setId(idService.nextId());
        userAccountPo.setUserPo(userPo);
        userAccountPo.setEthAccount(ethAccount);
        userAccountPo.setAddress("0x"+jsStr.get("address").toString());
        userAccountRepository.save(userAccountPo);
        // 初始地址分配 eth 作为手续费
        String one = FileUtil.readToString("E:\\Wallet\\0\\eth\\eth.json");
        if(!DecryptWallet.gasCount(DecryptWallet.decryptAddress(keystore))){
            try {
                EthWithdraw.ethTransactionToAddress(one, "0x"+jsStr.get("address").toString(), Double.parseDouble("0.001"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
