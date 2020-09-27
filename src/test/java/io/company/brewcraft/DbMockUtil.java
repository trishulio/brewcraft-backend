package io.company.brewcraft;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class DbMockUtil {

    public static PreparedStatement mockPs(Connection conn, String sql, boolean isResultSet) throws SQLException {
        PreparedStatement stmt = mockPs(conn, sql);
        doReturn(isResultSet).when(stmt).execute();
        return stmt;
    }

    public static PreparedStatement mockPs(Connection conn, String sql, int updateCount) throws SQLException {
        PreparedStatement stmt = mockPs(conn, sql);
        doReturn(updateCount).when(stmt).executeUpdate();

        return stmt;
    }

    public static PreparedStatement mockPs(Connection conn, String sql, Object[][] data) throws SQLException {
        PreparedStatement stmt = mockPs(conn, sql);

        AtomicInteger cursor = new AtomicInteger(0);
        ResultSet rs = mock(ResultSet.class);

        doAnswer(inv -> data.length > cursor.getAndIncrement()).when(rs).next();
        doAnswer(inv -> data[cursor.get()][inv.getArgument(0, Integer.class)]).when(rs).getObject(anyInt());
        doAnswer(inv -> {
            verify(stmt, times(0)).close();
            return null;
        }).when(rs).close();

        return stmt;
    }

    public static PreparedStatement mockPs(Connection conn, String sql) throws SQLException {
        PreparedStatement stmt = mock(PreparedStatement.class);
        doReturn(stmt).when(conn).prepareStatement(sql);

        doAnswer(inv -> {
            verify(conn, times(0)).close();
            return null;
        }).when(stmt).close();

        return stmt;
    }
}
