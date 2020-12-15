package io.company.brewcraft.util.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

    private Validator validator;

    @BeforeEach
    public void init() {
        validator = new Validator();
    }

    @Test
    public void testRule_SetsMsgAsError_WhenConditionIsFalse() {
        validator.rule(false, "This is an error message");
        assertThrows(ValidationException.class, () -> validator.raiseErrors(), "1. This is an error message");
    }

    @Test
    public void testRule_DoesNotSetMsgAsError_WhenConditionIsTrue() {
        validator.rule(true, "This is not an error message");
        validator.rule(false, "This is an error message");
        validator.rule(true, "This is not an error message");

        assertThrows(ValidationException.class, () -> validator.raiseErrors(), "1. This is an error message");
    }

    @Test
    public void testRaiseErrors_DoesNotThrowException_WhenNoErrorsExist() {
        validator.raiseErrors();
        validator.rule(true, "No exception will be raised because condition is true");
        validator.raiseErrors();
    }

    @Test
    public void testRaiseErrors_ConcatsErrorsIntoNumberedList() {
        validator.rule(false, "This is error A");
        validator.rule(true, "This message is ignored");
        validator.rule(false, "This is error B");
        validator.rule(true, "This message is ignored");
        validator.rule(false, "This is error C");

        String expected = ""
                + "1. This is error A\n"
                + "2. This is error B\n"
                + "3. This is error C\n";
        assertThrows(ValidationException.class, () -> validator.raiseErrors(), expected);
    }

    @Test
    public void testAssertion_CreatesAndThrowsExceptionObjectWithMsg_WhenConditionIsFalse() {
        assertThrows(RuntimeException.class, () -> {
            validator.assertion(false, RuntimeException.class, "This is the error message");
        }, "This is the error message");
    }

    @Test
    public void testAssertion_ThrowsSpecifiedExceptionType_WhenConditionIsFalse() {
        assertThrows(RuntimeException.class, () -> validator.assertion(false, RuntimeException.class));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> validator.assertion(false, ArrayIndexOutOfBoundsException.class));
        assertThrows(IndexOutOfBoundsException.class, () -> validator.assertion(false, IndexOutOfBoundsException.class));
    }

    @Test
    public void testAssertion_DoesNotThrowException_WhenConditionIsTrue() {
        validator.assertion(true, RuntimeException.class, "This will never be thrown");
    }
}
