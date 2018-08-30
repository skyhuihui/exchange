package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.CurrencyPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: 核心钱包（币种） repository
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:11
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:11
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Repository
public interface CurrencyRepository  extends JpaRepository<CurrencyPo, Long>, CurrencyDao{
}
