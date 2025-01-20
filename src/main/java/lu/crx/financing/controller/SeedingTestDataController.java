package lu.crx.financing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.crx.financing.services.SeedingTestDataService;

/**
 * Utility service for database seeding. Should not be tested. This service itself provides the testing data
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/yb-test")
public class SeedingTestDataController {

    private final SeedingTestDataService seedingTestDataService;

    @Operation(summary = "Seeding the database by the generated values for performance testing purposes",
            description = "Can be invoked to generate and insert around 500000 testing records in the database. "
                    + "This may take a long time: from 2 to 10 minutes, depending on the configuration of the workstation. "
                    + "If it failed with a timeout exception, please check the logs, if there are no errors, "
                    + "then seeding continues in the background. Please wait until the phrase "
                    + "appears in the log: 'SeedingTestDataController: seedDb done'")
    @PostMapping(value = "/v1/test/seeding")
    public ResponseEntity<Void> seedDb() {
        log.info("SeedingTestDataController: seedDb requested, {}", LocalDateTime.now(ZoneOffset.UTC));

        seedingTestDataService.seeding();

        log.info("SeedingTestDataController: seedDb finished, {}", LocalDateTime.now(ZoneOffset.UTC));
        return ResponseEntity.accepted().build();
    }
}
