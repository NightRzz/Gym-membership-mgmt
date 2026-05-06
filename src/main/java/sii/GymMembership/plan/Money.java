package sii.GymMembership.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Money implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "monthly_price_amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	@Column(name = "currency_code", nullable = false, length = 3)
	private String currencyCode;
}
