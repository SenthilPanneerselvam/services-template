package com.zero.template.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.template.constants.AppConstants;
import com.zero.template.core.JwtClaims;
import com.zero.template.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * 
 * @author senthilkumarpanneerselvam
 * 
 * A service to deal with creating and validating signed JWT.
 * This loads the public & private key during class initialization.
 * The loaded private and public key is used for creating and validating jwt.
 * This class creates a signed JWT by a suitable algorithm selected by the jjwt plugin.
 * 
 * https://gist.github.com/destan/b708d11bd4f403506d6d5bb5fe6a82c5
 * 
 * Link used to generate private and public key file and also to load the keys
 *
 */
public class JwtService {

	// key file names
	private static final String PUBLIC_KEY_PATH = "public_key.pem";
	private static final String PRIVATE_KEY_PATH = "private_key_pkcs8.pem";

	
	// cache instances of key files
	private static final PublicKey publicKey;
	private static final PrivateKey privateKey;
	

	static {

		try {
			publicKey = loadPublicKey();
			privateKey = loadPrivateKey();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			throw new RuntimeException("Error loading keys");
		}

	}

	/**
	 * This generates the JWT using the loaded private key.
	 * The claims set are the userId and roleCode. The userName is set in the sub.
	 * Expiration of the JWT is set to one year from the time creating the signed JWT.
	 * @param user
	 * @return String - the generated signed JWT
	 */
	public static String generateJWT(User user) {
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.YEAR, 1);
		String jws = Jwts.builder().setSubject(user.getUserName()).
				claim(AppConstants.USER_ID, user.getId()).
				// basic assumption that the role will not be updated often
				// if not the case should consider removing role code from jws
				claim(AppConstants.ROLE_CODE, user.getRole().getCode()).
				signWith(privateKey).
				setExpiration(expiry.getTime()).
				compact();
		
		return jws;
	}

	/**
	 * 
	 * Validates, parses and returns the claims in the signed JWT.
	 * If the JWT is not valid(if the signature is not valid), this method
	 * returns a null Object. 
	 * 
	 * If the JWT is valid, then returns the claims object
	 * 
	 * @param signed JWT in the form of String
	 * @return An {@link Optional} of {@link JwtClaims}. Populated if the JWT is valid.
	 */
	public static Optional<JwtClaims> parseJWT(String jws) {
		JwtClaims parsedClaims = null;
		Jws<Claims> claims;

		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(publicKey)
					.build()
					.parseClaimsJws(jws);
			Claims body = claims.getBody();
			System.out.println(body);
			ObjectMapper mapper = new ObjectMapper();
			parsedClaims = mapper.convertValue(body, JwtClaims.class);
		} catch (JwtException ex) {
			// ignore the exception, as the signature is not valid
		}
		return Optional.ofNullable(parsedClaims);
	}

	/**
	 * Load the public key into java Object
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static PublicKey loadPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		String publicKeyContent = loadData(PUBLIC_KEY_PATH);
		publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "");
		// create a key factory
		KeyFactory kf = KeyFactory.getInstance("RSA");
		// Load a public key
		X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

		return pubKey;
	}

	/**
	 * Loads the private key into java object
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static PrivateKey loadPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		String privateKeyContent = loadData(PRIVATE_KEY_PATH);
		privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "");
		// create a key factory
		KeyFactory kf = KeyFactory.getInstance("RSA");
		// Load the private key
		PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
		PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

		return privKey;
	}

	/**
	 * A utility function to load the data from the file used in loading public and private key
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	private static String loadData(String filePath) throws IOException {
		Resource resource = new ClassPathResource(filePath);
		File file = resource.getFile();
		return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}

	public static void main(String[] args)
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, URISyntaxException {

		String privateKeyContent = new String(Files.readAllBytes(Paths.get(
				"/Users/senthilkumarpanneerselvam/Projects/services-template/source/services-template/src/main/resources/private_key_pkcs8.pem")));
		String publicKeyContent = new String(Files.readAllBytes(Paths.get(
				"/Users/senthilkumarpanneerselvam/Projects/services-template/source/services-template/src/main/resources/public_key.pem")));

		privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "");
		publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "");
		;

		KeyFactory kf = KeyFactory.getInstance("RSA");

		PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
		PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

		X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

		System.out.println(privKey);
		System.out.println(pubKey);
	}

}
