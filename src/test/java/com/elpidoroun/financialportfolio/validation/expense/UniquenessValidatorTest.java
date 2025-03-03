package com.elpidoroun.financialportfolio.validation.expense;
import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.ExpenseTestFactory;
import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UniquenessValidatorTest {

    @Mock
    private ExpenseRepositoryOperations expenseRepositoryOperations;
    private UniquenessValidator uniquenessValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        uniquenessValidator = new UniquenessValidator(expenseRepositoryOperations);
    }

    @Test
    void success_expense_is_unique() {
        when(expenseRepositoryOperations.findByName("UniqueExpense")).thenReturn(Optional.empty());

        Result<Nothing, String> result = uniquenessValidator.validate(null, ExpenseTestFactory.createExpense());

        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void fail_expense_not_unique() {
        Expense entity = ExpenseTestFactory.createExpense();
        when(expenseRepositoryOperations.findByName(any())).thenReturn(Optional.of(entity));

        Result<Nothing, String> result = uniquenessValidator.validate(null, ExpenseTestFactory.createExpense());

        assertThat(result.isFail()).isTrue();
        assertThat(result.getError()).isPresent().hasValue("Expense with name: rent already exists");
    }

    @Test
    public void priority_and_method_name(){
        assertThat(uniquenessValidator.priority()).isEqualTo(0);
        assertThat(uniquenessValidator.name()).isEqualTo("UniquenessValidator");

    }
}