package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.CurrencyAllPo;
import com.zag.db.mysql.po.web3.CurrencyUserPo;
import com.zag.db.mysql.po.web3.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/8/28 10:20
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:20
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Repository
public interface CurrencyUserRepository extends JpaRepository<CurrencyUserPo, Long>, CurrencyUserDao{

    CurrencyUserPo findByUserPoAndCurrencyAllPo(UserPo userPo, CurrencyAllPo currencyAllPo);

}
