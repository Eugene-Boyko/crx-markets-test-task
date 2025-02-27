package lu.crx.financing.persistence.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Financing settings set by the purchaser for a specific creditor.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaserFinancingSettings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Creditor creditor;

    /**
     * The annual financing rate set by the purchaser for this creditor.
     */
    @Basic(optional = false)
    private int annualRateInBps;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Purchaser purchaser;

    // intentional denormalization, allows to avoid extra join with the Purchaser
    /**
     * The minimum financing term (duration between the financing date and the maturity date of the invoice).
     */
    @Basic(optional = false)
    private int minimumFinancingTermInDays;
}
