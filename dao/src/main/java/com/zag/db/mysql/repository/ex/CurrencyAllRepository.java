package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.CurrencyAllPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:17
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Repository
public interface CurrencyAllRepository extends JpaRepository<CurrencyAllPo, Long>, CurrencyAllDao{

    CurrencyAllPo findByaddress(String address);
}
