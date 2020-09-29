package com.goblinswap.pool;

import io.nuls.contract.sdk.Address;
import io.nuls.contract.sdk.Msg;
import io.nuls.contract.sdk.annotation.View;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class LPTokenWrapper extends Ownable {
    private BigInteger _totalSupply = BigInteger.ZERO;
    private Map<Address, BigInteger> _balances = new HashMap<Address, BigInteger>();

    @View
    public BigInteger totalSupply() {
        return _totalSupply();
    }

    protected BigInteger _totalSupply() {
        return _totalSupply;
    }

    @View
    public BigInteger balanceOf(Address account) {
        return _balanceOf(account);
    }

    protected BigInteger _balanceOf(Address account) {
        if (_balances.get(account) != null) {
            return _balances.get(account);
        } else {
            return BigInteger.ZERO;
        }
    }

    protected void stake(BigInteger amount) {
        _totalSupply = _totalSupply.add(amount);
        if (_balances.get(Msg.sender()) != null) {
            _balances.put(Msg.sender(), _balances.get(Msg.sender()).add(amount));
        } else {
            _balances.put(Msg.sender(), amount);
        }
    }

    protected void withdraw(BigInteger amount) {
        _totalSupply = _totalSupply.subtract(amount);
        _balances.put(Msg.sender(), _balances.get(Msg.sender()).subtract(amount));
        Msg.sender().transfer(amount);
    }
}
