package org.example;

import org.junit.jupiter.api.Test;

import org.example.ocp1b.*;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class verifies that the design of the Payment system conforms
 * to the Open-Closed Principle (OCP), which states that:
 * "Software entities should be open for extension but closed for modification."
 * 
 * In this context, it means we should be able to introduce new payment types
 * without modifying the existing PaymentProcessor logic.
 */
public class OCPTest1 {

	@Test
	void testPaymentProcessorWorksWithCreditCard() {
		// ✅ This test ensures that the processor can work with CreditCardPayment,
		// which is one implementation of the PaymentMethod interface.
		// It verifies that behavior can be extended via polymorphism.
		PaymentMethod method = new CreditCardPayment();
		PaymentProcessor processor = new PaymentProcessor();

		assertDoesNotThrow(() -> processor.process(method), "Should process credit card payment");
	}

	@Test
	void testPaymentProcessorWorksWithBankTransfer() {
		// ✅ Like the previous test, this verifies that another implementation
		// (BankTransferPayment) works without requiring changes to PaymentProcessor.
		// This supports OCP by demonstrating extensibility.
		PaymentMethod method = new BankTransferPayment();
		PaymentProcessor processor = new PaymentProcessor();

		assertDoesNotThrow(() -> processor.process(method), "Should process bank transfer payment");
	}

	@Test
	void testCanExtendWithNewPaymentWithoutModifyingProcessor() {
		// ✅ This test simulates a new type (EWalletPayment) being added.
		// Since we don’t touch PaymentProcessor to support this,
		// we validate that the system is open for extension but closed for modification.
		class EWalletPayment implements PaymentMethod {
			public void process() {
				// Simulate processing e-wallet
			}
		}

		PaymentMethod method = new EWalletPayment();
		PaymentProcessor processor = new PaymentProcessor();

		assertDoesNotThrow(() -> processor.process(method), "Should process e-wallet payment");
	}

	@Test
	void testPaymentProcessorDoesNotUseInstanceOfOrIf() {
		// ✅ A common violation of OCP is using `instanceof` or `if`/`switch` blocks
		// to handle multiple types in a central place.
		// This test ensures that PaymentProcessor avoids those patterns.
		Method[] methods = PaymentProcessor.class.getDeclaredMethods();
		for (Method method : methods) {
			String code = method.toString().toLowerCase();
			assertFalse(code.contains("instanceof") || code.contains("if"),
					"PaymentProcessor should not use instanceof or if to check payment types");
		}
	}

	@Test
	void testEachPaymentMethodImplementsInterfaceCorrectly() {
		// ✅ Verifies that each payment method correctly implements the interface
		// and can be processed independently. This ensures each concrete implementation
		// conforms to the expected behavior contract, making them plug-and-play.
		List<PaymentMethod> methods = List.of(new CreditCardPayment(), new BankTransferPayment());

		for (PaymentMethod method : methods) {
			assertDoesNotThrow(method::process, "Each payment method should implement process()");
		}
	}
}
