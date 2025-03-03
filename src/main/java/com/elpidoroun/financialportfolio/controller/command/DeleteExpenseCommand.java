package com.elpidoroun.financialportfolio.controller.command;

import com.elpidoroun.financialportfolio.service.ExpenseRepositoryOperations;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.financialportfolio.model.Operations.DELETE_EXPENSE_BY_ID;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Component
public class DeleteExpenseCommand implements Command<DeleteExpenseCommand.DeleteExpenseRequest, Void> {

    @NonNull private final ExpenseRepositoryOperations expenseRepositoryOperations;

    @Override
    public Void execute(DeleteExpenseRequest request) {
        expenseRepositoryOperations.deleteById(request.getExpenseId());
        return null;
    }

    @Override
    public boolean isRequestIncomplete(DeleteExpenseRequest request) {
        return isNull(request) || isNull(request.getExpenseId());
    }

    @Override
    public String missingParams(DeleteExpenseRequest request) {
        if(isNull(request)){
            return "Request is empty";
        }

        return Stream.of(isNull(request.getExpenseId())? "ExpenseId is missing" : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return DELETE_EXPENSE_BY_ID.getValue(); }

    public static DeleteExpenseCommand.DeleteExpenseRequest request(String expenseId){
        return new DeleteExpenseCommand.DeleteExpenseRequest(expenseId);
    }

    protected static class DeleteExpenseRequest extends AbstractRequest {

        private final String expenseId;

        DeleteExpenseRequest(String expenseId){
            this.expenseId = expenseId;
        }
        public String getExpenseId(){ return expenseId; }

    }
}