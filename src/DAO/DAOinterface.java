package DAO;

import java.util.ArrayList;

/**
 *
 * @author phucp
 */
public interface DAOinterface<P> {

    public int insert(P p);

    public int update(P p);

    public int delete(String p);

    public ArrayList<P> selectAll();

    public P selectById(String p);

    int getAutoIncrement();
}
