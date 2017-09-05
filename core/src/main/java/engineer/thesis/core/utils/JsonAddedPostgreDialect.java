package engineer.thesis.core.utils;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class JsonAddedPostgreDialect extends PostgreSQL94Dialect {
    public JsonAddedPostgreDialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
