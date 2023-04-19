package com.demo;

import com.demo.dao.WalletDo;
import com.demo.dao.WalletManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author keith
 */
@RestController
public class TestController {

    @Resource
    private WalletManager walletManager;

    @GetMapping("list")
    public void list(){
        walletManager.list();
    }

    @GetMapping("save")
    public void save(){
        WalletDo walletDo = new WalletDo();
        walletDo.setAge(1);
        walletDo.setBalance(BigDecimal.valueOf(1));
        walletManager.save(walletDo);
    }

    @GetMapping("update")
    public void update(Long id){
        WalletDo walletDo = new WalletDo();
        walletDo.setId(id);
        walletDo.setAge(2);
        walletDo.setBalance(BigDecimal.valueOf(1));
        walletManager.updateById(walletDo);
    }
}
