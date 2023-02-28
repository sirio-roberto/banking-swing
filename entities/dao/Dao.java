package banking.entities.dao;

public interface Dao<T> {

    void insert(T obj);

    void delete(T obj);
}
