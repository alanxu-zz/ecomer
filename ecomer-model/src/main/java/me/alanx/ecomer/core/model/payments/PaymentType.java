package me.alanx.ecomer.core.model.payments;

public enum PaymentType {
	
	
	
	CREDITCARD("creditcard", null), 
	FREE("creditcard", null), 
	COD("creditcard", null), 
	MONEYORDER("creditcard", "moneyorder"), 
	PAYPAL("creditcard", "paypal-express-checkout"), 
	PAYPAL_REST("creditcard", "paypal-rest"), 
	STRIPE("creditcard", "stripe"), 
	BEANSTREAM("creditcard", "beanstream"),
	WEPAY("creditcard", "wepay");

	
	private final String paymentType;
	private final String paymentName;
	
	private PaymentType(String type, String name) {
		paymentType = type;
		this.paymentName = name;
	}
	
	
	
    /**
	 * @return the paymentName
	 */
	public String getPaymentName() {
		return paymentName;
	}



	public static PaymentType fromString(String text) {
		    if (text != null) {
		      for (PaymentType b : PaymentType.values()) {
		    	String payemntType = text.toUpperCase(); 
		        if (payemntType.equalsIgnoreCase(b.name())) {
		          return b;
		        }
		      }
		    }
		    return null;
	}
}
