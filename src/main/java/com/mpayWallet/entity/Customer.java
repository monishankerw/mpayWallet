package com.mpayWallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	@OneToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;

	@NotBlank(message = "Customer name is required")
	private String customerName;

	@NotBlank(message = "Address is required")
	private String customerAddress;

	@NotBlank(message = "State is required")
	private String customerState;

	@Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
	private String mobile;

	@Email(message = "Invalid email format")
	private String email;

	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

}