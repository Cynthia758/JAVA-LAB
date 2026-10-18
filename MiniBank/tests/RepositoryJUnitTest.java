
package MiniBank.tests;

import MiniBank.src.model.SavingsAccount;
import MiniBank.src.util.Repository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoryJUnitTest {

    @Test
    void testSaveAndFindById() {
        Repository<SavingsAccount> repository = new Repository<>();
        SavingsAccount account = new SavingsAccount("Vaidehi", 5000);

        repository.save(account);

        assertNotNull(repository.findById(account.getAccountNumber()));
        assertEquals(account, repository.findById(account.getAccountNumber()));
    }

    @Test
    void testFindAll() {
        Repository<SavingsAccount> repository = new Repository<>();

        repository.save(new SavingsAccount("Vaidehi", 5000));
        repository.save(new SavingsAccount("Jiya", 7000));

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testDelete() {
        Repository<SavingsAccount> repository = new Repository<>();
        SavingsAccount account = new SavingsAccount("Vaidehi", 5000);

        repository.save(account);

        assertTrue(repository.delete(account.getAccountNumber()));
        assertNull(repository.findById(account.getAccountNumber()));
    }

    @Test
    void testAverage() {
        Integer[] values = {10, 20, 30};

        assertEquals(20.0, Repository.average(values), 0.001);
    }

    @Test
    void testAverageRejectsEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> Repository.average(new Integer[0]));
    }
}
