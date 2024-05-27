package dev.bogdan.questionnaire.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

public class CustomIntegerArrayType implements UserType<Integer[]> {

    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<Integer[]> returnedClass() {
        return Integer[].class;
    }

    @Override
    public boolean equals(Integer[] integers, Integer[] j1) {
        return false;
    }

    @Override
    public int hashCode(Integer[] integers) {
        return 0;
    }

    @Override
    public Integer[] nullSafeGet(ResultSet resultSet, int position, SharedSessionContractImplementor session, Object o) throws SQLException {
        Array array = resultSet.getArray(position);
        return array != null ? (Integer[]) array.getArray() : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Integer[] integers, int index, SharedSessionContractImplementor session) throws SQLException {
        if (preparedStatement != null) {
            if (integers     != null) {
                Array array = session.getJdbcConnectionAccess().obtainConnection().createArrayOf("int", integers);
                preparedStatement.setArray(index, array);
            } else {
                preparedStatement.setNull(index, Types.ARRAY);
            }
        }
    }

    @Override
    public Integer[] deepCopy(Integer[] integers) {
        return new Integer[0];
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Integer[] integers) {
        return null;
    }

    @Override
    public Integer[] assemble(Serializable serializable, Object o) {
        return new Integer[0];
    }
}
