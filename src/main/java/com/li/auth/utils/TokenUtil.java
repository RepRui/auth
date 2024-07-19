package com.li.auth.utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TokenUtil {
    /**
     * 过期时间()
     */
    public static final TimeUnit ACCESS_EXPIRE_UNIT = TimeUnit.MILLISECONDS;

    /**
     * 过期时间(单位:毫秒)
     */
    public static long ACCESS_EXPIRE_TIME = 5 * 60 * 1000L;//5分鐘的毫秒數


    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */
    public  static String SYSTEM_SECRET = "sBg01svFT5s4eIzmHpJXpY26bjBQuJHwXibO8rbz3Uw=";//secretKey

    /**
     * jwt签发者
     */
    public  static String SYSTEM_JWT_ISS = "LI-SHI-HAI";
    /**
     * jwt主题
     */
    public  static String SYSTEM_SUBJECT = "PROJECT-NAME";

    /**
     * 加密算法
     */
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

    /**
     * 秘钥实例
     */
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SYSTEM_SECRET.getBytes());


    /**
        这些是一组预定义的声明，它们 不是强制性的，而是推荐的 ，以 提供一组有用的、可互操作的声明 。
        iss: jwt签发者
        sub: jwt所面向的用户
        aud: 接收jwt的一方
        exp: jwt的过期时间，这个过期时间必须要大于签发时间
        nbf: 定义在什么时间之前，该jwt都是不可用的.
        iat: jwt的签发时间
        jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
        生成新的JJWT令牌并
     */
    public static String genAccessToken(String username) {
        // 令牌id
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusMillis(ACCESS_EXPIRE_TIME));


        return Jwts.builder()
                // 设置头部信息header
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")//使用SHA-256的HMAC
                .and()
                // 设置自定义负载信息payload
                .claim("username", username)
                // 令牌ID
                .id(uuid)
                // 过期日期
                .expiration(exprireDate)
                // 签发时间
                .issuedAt(new Date())
                // 主题
                .subject(SYSTEM_SUBJECT)
                // 签发者
                .issuer(SYSTEM_JWT_ISS)
                // 签名
                .signWith(KEY, ALGORITHM)
                .compact();
    }

    /**
     * 解析token
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }

    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    public static Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }

    /**
     * 获取JWT令牌的过期时间
     *
     * @param token JWT令牌
     * @return 过期时间的毫秒级时间戳
     */
    public static long getExpirationTime(String token) {
        Claims claims = parsePayload(token);
        if (claims != null) {
            return claims.getExpiration().getTime();
        }
        return 0L;
    }
    /**
     * 获取payload中的用户信息
     *
     * @param token JWT Token
     * @return 用户信息
     */
    public static String getUserFromToken(String token) {
        String user = "";
        Claims claims = parsePayload(token);
        if (claims != null) {
            user = (String) claims.get("username");
        }
        return user;
    }


    /**
     * 随机生成AES256 SecretKey
     * @return
     * @throws Exception
     */
    public static String genSecretKey() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }


}
