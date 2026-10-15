import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.FIELD)

@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.FIELD)

@interface MaxLength {
    int value();  
}
class SignupForm {

    @NotBlank
    private String username;

    @NotBlank
    @MaxLength(20)
    private String password;

    @NotBlank
    @MaxLength(50)
    private String email;


    // Constructor
    SignupForm(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}


// ======================================================
// 4. Validator using Reflection
// ======================================================

class FormValidator {

    public static List<String> validate(Object obj) {

        // List to store validation errors
        List<String> errors = new ArrayList<>();

        // Get the class of the object
        Class<?> clazz = obj.getClass();

        // Get all fields of the class
        Field[] fields = clazz.getDeclaredFields();

        // Check every field
        for (Field field : fields) {

            // Make private fields accessible
            field.setAccessible(true);

            try {

                // Get the actual value of the field
                Object value = field.get(obj);

                // =========================================
                // Check @NotBlank
                // =========================================

                if (field.isAnnotationPresent(NotBlank.class)) {

                    if (value == null ||
                        value.toString().trim().isEmpty()) {

                        errors.add(
                            field.getName() + " must not be blank"
                        );
                    }
                }


                // =========================================
                // Check @MaxLength
                // =========================================

                if (field.isAnnotationPresent(MaxLength.class)) {

                    // Get the annotation
                    MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                    // Get the maximum length
                    int max = annotation.value();

                    if (value != null &&
                        value.toString().length() > max) {

                        errors.add(
                            field.getName()
                            + " must not exceed "
                            + max
                            + " characters"
                        );
                    }
                }

            } catch (IllegalAccessException e) {

                errors.add(
                    "Cannot access field: "
                    + field.getName()
                );
            }
        }

        return errors;
    }
}


// ======================================================
// 5. Main class
// ======================================================

public class Main {

    public static void main(String[] args) {

        // Create an invalid form
        SignupForm form = new SignupForm(
                "",
                "thispasswordiswaytoolong123456",
                "student@example.com"
        );

        // Validate the form
        List<String> errors =
                FormValidator.validate(form);

        // Print errors
        if (errors.isEmpty()) {

            System.out.println("Form is valid!");

        } else {

            System.out.println("Validation errors:");

            for (String error : errors) {
                System.out.println("- " + error);
            }
        }
    }
}