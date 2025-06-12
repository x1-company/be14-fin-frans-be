package com.x1.frans.statistics.command.application.generator;

import java.time.LocalDateTime;

public interface StatisticsGenerator {

    void generate(LocalDateTime from, LocalDateTime to);

}
