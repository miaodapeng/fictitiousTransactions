package com.vedeng.trader.dao;

import com.newtask.model.YxgAccount;
import javax.inject.Named;

/**
 * @author daniel
 * @date 2020/03/04
 **/
@Named("accountMapper")
public interface AccountMapper {

    YxgAccount getAccount(Integer start);
}
