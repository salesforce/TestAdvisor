/**
 * 
 */
package com.salesforce.cqe.copado;

/**
 * @author gneumann
 *
 */
public class CustomerCopado {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String clientIDValue = "3MVG9UYNN.XsP1WIJGi9P3k1G3t0TMXfU4_JvbAfSnVB1lbsBbzm6C90y992sZlzWf122yq145THgC68mg84Q";
        String clientSecretValue = "36448E86D5B8F628ADFDA03C148E22894AF3B790B6CC722297919BB00FECF6DE";
        String usernameValue = "gneumann@copa.do.pleasekeep";
        String passwordValue = "jGI0nPHapu@$";
        String domainURI = "https://copado--PleaseKeep.cs49.my.salesforce.com";

		String accessToken = null;
		try {
			accessToken = ResultManager.getAccessToken(clientIDValue, clientSecretValue, usernameValue, passwordValue);
			ResultManager.retrieveStatus(domainURI, accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

}
