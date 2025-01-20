package lu.crx.financing.persistence.entity;

import io.ebean.Model;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A FinancingResults is an entity that that represents the results of the financing. It contains applied financing
 * term, financing rate, financing date, early payment amount, purchaser financing settings;
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancingResults extends Model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The duration in days between the financing(date of invoice processing) date and the Invoice's maturity date. In
     * days
     */
    @Basic(optional = false)
    private int financingTerm;

    /**
     * The actual financing rate of Purchaser for a particular Invoice, proportional to its financing term. Calculated
     * as financingRate = annualRate * financingTerm / 360. In Bps
     */
    @Basic(optional = false)
    private int financingRate;

    /**
     * The date on which the financing has occurred. Date of invoice processing
     */
    @Basic(optional = false)
    private LocalDate financingDate;

    /**
     * The amount of money paid by the Purchaser to the Creditor for the particular financed invoice on financing date.
     * This amount is less than the value of the invoice. In cents
     */
    @Basic(optional = false)
    private long earlyPaymentAmount;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Invoice invoice;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private PurchaserFinancingSettings purchaserFinancingSettings;
}
