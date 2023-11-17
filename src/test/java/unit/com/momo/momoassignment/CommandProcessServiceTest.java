package unit.com.momo.momoassignment;

import com.momo.momoassignment.common.ExceptionConstants;
import com.momo.momoassignment.model.Account;
import com.momo.momoassignment.service.AccountService;
import com.momo.momoassignment.service.BillService;
import com.momo.momoassignment.service.CommandProcessService;
import com.momo.momoassignment.service.PaymentService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static com.momo.momoassignment.common.CommonUtils.formatDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CommandProcessServiceTest {

    @Mock
    BillService billService;

    @Mock
    PaymentService paymentService;

    @Mock
    AccountService accountService;

    CommandProcessService commandProcessService;

    @BeforeEach
    void setup() {
        commandProcessService = new CommandProcessService();
    }

    @AfterEach
    void tearDown() {
        billService = null;
        paymentService = null;
        accountService = null;
    }

    @Test
    void processCommand_shouldPrintMessage_whenGivenInvalidCommand() {
        final var input = new String[]{"invalid_command"};

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        commandProcessService.processCommand(input);

        assertThat(outContent.toString()).contains("Unrecognised command: ");
    }

    @Test
    void processCommand_shouldThrowException_whenGivenInvalidCommand() {
        final String[] input = null;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));

        commandProcessService.processCommand(input);

        assertThat(outContent.toString()).contains("NullPointerException");
    }

    @Test
    void processCommand_shouldThrowException_whenGivenInsufficientArguments() {
        final String[] input = {"create_bill", "1", "2"};

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));

        commandProcessService.processCommand(input);

        assertThat(outContent.toString()).contains(ExceptionConstants.ERR_LOG_MISSING_ARGUMENT);
    }

    @Test
    void processCommand_shouldProcess_whenCashIn() {
        final String[] input = {"cash_in", "1"};

        // setup mock
        accountService = mock(AccountService.class);
        when(accountService.cashIn(anyString())).thenReturn(1000L);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        commandProcessService.processCommand(input);

        assertThat(outContent.toString())
                .contains("Your available balance: 1");
    }

    @Test
    void processCommand_shouldReturnInvalidValue_whenGivenInvalidArgument() {
        final String[] input = {"cash_in", "aaaa"};

        // setup mock
        accountService = mock(AccountService.class);
        when(accountService.cashIn(anyString())).thenReturn(-1L);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        commandProcessService.processCommand(input);

        assertThat(outContent.toString().trim()).isEmpty();
    }

    @Test
    void processCommand_shouldProcess_whenListBill() {
        final String[] input = {"list_bill"};

        // setup mock
        billService = mock(BillService.class);
        Account account = mock(Account.class);
        doNothing().when(billService).getAllBill();
        when(account.getBills()).thenReturn(List.of());

        commandProcessService.processCommand(input);
        verify(account, times(1)).getBills();
    }

}
