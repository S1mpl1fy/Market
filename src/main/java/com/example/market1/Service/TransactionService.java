package com.example.market1.Service;

import com.example.market1.DAO.GoodsDAO;
import com.example.market1.DAO.TransactionDAO;
import com.example.market1.DAO.UserDAO;
import com.example.market1.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {
    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    GoodsDAO goodsDAO;

    public int addTransation(Transaction transaction){
        return transactionDAO.addTransaction(transaction);
    }

    public Transaction getTransaction(int id){
        return transactionDAO.getTransaction(id);
    }

    public int updateTransaction(int id, int status){
        return transactionDAO.updateStatus(id, status);
    }
}
