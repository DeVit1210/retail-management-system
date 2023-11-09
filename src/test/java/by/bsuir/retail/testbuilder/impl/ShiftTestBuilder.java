package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
@With
public class ShiftTestBuilder implements TestBuilder<Shift> {
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime = LocalDateTime.now().plusHours(12);
    private Cashier cashier = null;
    private boolean active = true;
    @Override
    public Shift build() {
        return Shift.builder()
                .startTime(startTime)
                .endTime(endTime)
                .cashier(cashier)
                .active(active)
                .build();
    }
}
