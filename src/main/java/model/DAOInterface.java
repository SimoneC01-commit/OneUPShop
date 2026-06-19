package model;

import java.sql.SQLException;
import java.util.List;

public interface DAOInterface<T,K>{
    void doSave(T entry) throws SQLException;
    T doRetrieveByKey(K key) throws SQLException;
    List<T> doRetrieveAll() throws SQLException;
    void doUpdate(T entry) throws SQLException;
    void doDelete(K key) throws SQLException;
}