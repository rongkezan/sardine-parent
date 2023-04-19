package com.demo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.WalletMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yesitao
 * @since 2021-11-24
 */
@Service
public class WalletManagerImpl extends ServiceImpl<WalletMapper, WalletDo> implements WalletManager {

}
