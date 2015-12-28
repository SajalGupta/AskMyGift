package com.turningcloud.askmygift;

public interface ApplicationMetadata {

	public enum applicationEnvironment { PROD, DEV };
	
	String INSUFFICIENT_PARAMETER_CODE = "AMG1001";
	String INSUFFICIENT_PARAMETER_MSG = "Insufficient Parameters received. Unable to process your request";
	
	String SUCCESS_CODE = "AMG1002";
	String SUCCESS_MSG = "Request completed successfully";
	
	String FAILURE_CODE = "AMG1003";
	String FAILURE_MSG = "Unable to process your request";
	
	String EXISTING_USER_CODE = "AMG1004";
	String EXISTING_USER_MSG = "Existing user found with the same data, generatin secure code to confirm";
	
	String CLASS_ADDRESS = "com.askmygift.giftdairy.dao.entity.Address";
	String CLASS_DIARY = "com.askmygift.giftdairy.dao.entity.Diary";
	String CLASS_EVENT = "com.askmygift.giftdairy.dao.entity.Event";
	String CLASS_PREFERENCES = "com.askmygift.giftdairy.dao.entity.Preferences";
	String CLASS_PRODUCT = "com.askmygift.giftdairy.dao.entity.Product";
	String CLASS_USER = "com.askmygift.giftdairy.dao.entity.User";
	
}
