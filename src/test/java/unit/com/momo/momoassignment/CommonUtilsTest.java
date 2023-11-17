package unit.com.momo.momoassignment;

import com.momo.momoassignment.common.CommonUtils;
import com.momo.momoassignment.model.Bill;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static com.momo.momoassignment.common.CommonUtils.formatDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommonUtilsTest {

    @Test
    void formatDate_shouldReturnValue() {
        final var input = LocalDate.of(2020, 10, 1);
        final var expected = "01/10/2020";

        assertThat(
                formatDate(input)
        ).isEqualTo(expected);
    }

    @Test
    void formatDate_shouldThrowException() {
        assertThatThrownBy(
                () -> formatDate(null)
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void printBillTable_shouldPrintMessage_whenNullOrEmptyArgument() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final var expected = "No bill to print out";

        CommonUtils.printBillTable(null);
        assertThat(outContent.toString()).contains(expected);

        outContent.reset();
        CommonUtils.printBillTable(List.of());
        assertThat(outContent.toString()).contains(expected);
    }

    @Test
    void printBillTable_shouldPrintTable() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        final var inputBills = List.of(
                new Bill(1, "TEST_TYPE", 100, LocalDate.now(), false, "TEST_PROVIDER"),
                new Bill(2, "TEST_TYPE", 200, LocalDate.now(), true, "TEST_PROVIDER")
        );

        CommonUtils.printBillTable(inputBills);
        assertThat(outContent.toString().split("\n"))
                .hasSize(3)
                .satisfies(strings -> {
                    final var outputCells = strings[1].split("\s+");
                    assertThat(outputCells).hasSize(6);
                    assertThat(outputCells[0]).isEqualTo("1");
                    assertThat(outputCells[1]).isEqualTo("TEST_TYPE");
                    assertThat(outputCells[2]).isEqualTo("100");
                    assertThat(outputCells[3]).isEqualTo(formatDate(LocalDate.now()));
                    assertThat(outputCells[4]).isEqualTo("NOT_PAID");
                    assertThat(outputCells[5]).isEqualTo("TEST_PROVIDER");
                })
                .satisfies(strings -> {
                    assertThat(strings[2].split("\s+")[4]).isEqualTo("PAID");
                });
    }

}
