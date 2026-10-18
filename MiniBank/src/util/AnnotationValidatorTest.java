
package MiniBank.src.util;

import MiniBank.src.model.annotation.Id;
import MiniBank.src.model.annotation.Positive;
import MiniBank.src.model.annotation.MaxLength;

public class AnnotationValidatorTest {

    static class Sample {
        @Id
        private String id = "AC0001";

        @Positive
        private long balance = 100;

        @MaxLength(10)
        private String name = "Vaidehi";
    }

    public static void main(String[] args) {
        Sample sample = new Sample();

        AnnotationValidator.validate(sample);
        System.out.println("Valid object passed validation.");

        try {
            Sample invalid = new Sample();
            invalid.balance = -100;
            AnnotationValidator.validate(invalid);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid object rejected: " + e.getMessage());
        }
    }
}
