package com.example.bilal.config.hazelcast;

public enum CassandraColumns {

    ID("id"), VALUE("value");

    private final String type;

    private CassandraColumns(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static CassandraColumns fromString(String text) {
        if (text != null) {
            for (CassandraColumns column : CassandraColumns.values()) {
                if (text.equalsIgnoreCase(column.type)) {
                    return column;
                }
            }
        }
        return null;
    }

}
