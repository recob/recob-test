package com.recob.domain.holder;

import com.recob.domain.test.TestSchema;

import javax.validation.constraints.NotNull;

/**
 * Class holds single instance of test schema
 *
 * {@link TestSchemaHolder#testSchema} test schema for current application instance
 */

public class TestSchemaHolder {

    private static @NotNull TestSchema testSchema;

    public static TestSchema getTestSchema() {
        return testSchema;
    }

    public static TestSchema setTestSchema(TestSchema testSchema) {
        TestSchemaHolder.testSchema = testSchema;
        return testSchema;
    }
}
