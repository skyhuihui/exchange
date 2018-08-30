package com.zag.db.mysql.repository.ex;

import com.zag.db.mysql.po.web3.TransactionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.db.mysql.repository.ex
 * @ClassName: ${TYPE_NAME}
 * @Description: 交易记录
 * @Author: skyhuihui
 * @CreateDate: 2018/8/28 10:39
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/28 10:39
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionPo, Long>, TransactionDao{
}
