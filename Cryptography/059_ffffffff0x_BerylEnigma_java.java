#
# This is the "master security properties file".
#
# An alternate java.security properties file may be specified
# from the command line via the system property
#
#    -Djava.security.properties=<URL>
#
# This properties file appends to the master security properties file.
# If both properties files specify values for the same key, the value
# from the command-line properties file is selected, as it is the last
# one loaded.
#
# Also, if you specify
#
#    -Djava.security.properties==<URL> (2 equals),
#
# then that properties file completely overrides the master security
# properties file.
#
# To disable the ability to specify an additional properties file from
# the command line, set the key security.overridePropertiesFile
# to false in the master security properties file. It is set to true
# by default.

# In this file, various security properties are set for use by
# java.security classes. This is where users can statically register
# Cryptography Package Providers ("providers" for short). The term
# "provider" refers to a package or set of packages that supply a
# concrete implementation of a subset of the cryptography aspects of
# the Java Security API. A provider may, for example, implement one or
# more digital signature algorithms or message digest algorithms.
#
# Each provider must implement a subclass of the Provider class.
# To register a provider in this master security properties file,
# specify the provider and priority in the format
#
#    security.provider.<n>=<provName | className>
#
# This declares a provider, and specifies its preference
# order n. The preference order is the order in which providers are
# searched for requested algorithms (when no specific provider is
# requested). The order is 1-based; 1 is the most preferred, followed
# by 2, and so on.
#
# <provName> must specify the name of the Provider as passed to its super
# class java.security.Provider constructor. This is for providers loaded
# through the ServiceLoader mechanism.
#
# <className> must specify the subclass of the Provider class whose
# constructor sets the values of various properties that are required
# for the Java Security API to look up the algorithms or other
# facilities implemented by the provider. This is for providers loaded
# through classpath.
#
# Note: Providers can be dynamically registered instead by calls to
# either the addProvider or insertProviderAt method in the Security
# class.

#
# List of providers and their preference orders (see above):
#
security.provider.1=SUN
security.provider.2=SunRsaSign
security.provider.3=SunEC
security.provider.4=SunJSSE
security.provider.5=SunJCE
security.provider.6=SunJGSS
security.provider.7=SunSASL
security.provider.8=XMLDSig
security.provider.9=SunPCSC
security.provider.10=JdkLDAP
security.provider.11=JdkSASL
security.provider.12=SunMSCAPI
security.provider.13=SunPKCS11
security.provider.14=org.bouncycastle.jce.provider.BouncyCastleProvider

#
# A list of preferred providers for specific algorithms. These providers will
# be searched for matching algorithms before the list of registered providers.
# Entries containing errors (parsing, etc) will be ignored. Use the
# -Djava.security.debug=jca property to debug these errors.
#
# The property is a comma-separated list of serviceType.algorithm:provider
# entries. The serviceType (example: "MessageDigest") is optional, and if
# not specified, the algorithm applies to all service types that support it.
# The algorithm is the standard algorithm name or transformation.
# Transformations can be specified in their full standard name
# (ex: AES/CBC/PKCS5Padding), or as partial matches (ex: AES, AES/CBC).
# The provider is the name of the provider. Any provider that does not
# also appear in the registered list will be ignored.
#
# There is a special serviceType for this property only to group a set of
# algorithms together. The type is "Group" and is followed by an algorithm
# keyword. Groups are to simplify and lessen the entries on the property
# line. Current groups are:
#   Group.SHA2 = SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256
#   Group.HmacSHA2 = HmacSHA224, HmacSHA256, HmacSHA384, HmacSHA512
#   Group.SHA2RSA = SHA224withRSA, SHA256withRSA, SHA384withRSA, SHA512withRSA
#   Group.SHA2DSA = SHA224withDSA, SHA256withDSA, SHA384withDSA, SHA512withDSA
#   Group.SHA2ECDSA = SHA224withECDSA, SHA256withECDSA, SHA384withECDSA, \
#                     SHA512withECDSA
#   Group.SHA3 = SHA3-224, SHA3-256, SHA3-384, SHA3-512
#   Group.HmacSHA3 = HmacSHA3-224, HmacSHA3-256, HmacSHA3-384, HmacSHA3-512
#
# Example:
#   jdk.security.provider.preferred=AES/GCM/NoPadding:SunJCE, \
#         MessageDigest.SHA-256:SUN, Group.HmacSHA2:SunJCE
#
#jdk.security.provider.preferred=


#
# Sun Provider SecureRandom seed source.
#
# Select the primary source of seed data for the "NativePRNG", "SHA1PRNG"
# and "DRBG" SecureRandom implementations in the "Sun" provider.
# (Other SecureRandom implementations might also use this property.)
#
# On Unix-like systems (for example, Linux/MacOS), the
# "NativePRNG", "SHA1PRNG" and "DRBG" implementations obtains seed data from
# special device files such as file:/dev/random.
#
# On Windows systems, specifying the URLs "file:/dev/random" or
# "file:/dev/urandom" will enable the native Microsoft CryptoAPI seeding
# mechanism for SHA1PRNG and DRBG.
#
# By default, an attempt is made to use the entropy gathering device
# specified by the "securerandom.source" Security property.  If an
# exception occurs while accessing the specified URL:
#
#     NativePRNG:
#         a default value of /dev/random will be used.  If neither
#         are available, the implementation will be disabled.
#         "file" is the only currently supported protocol type.
#
#     SHA1PRNG and DRBG:
#         the traditional system/thread activity algorithm will be used.
#
# The entropy gathering device can also be specified with the System
# property "java.security.egd". For example:
#
#   % java -Djava.security.egd=file:/dev/random MainClass
#
# Specifying this System property will override the
# "securerandom.source" Security property.
#
# In addition, if "file:/dev/random" or "file:/dev/urandom" is
# specified, the "NativePRNG" implementation will be more preferred than
# DRBG and SHA1PRNG in the Sun provider.
#
securerandom.source=file:/dev/random

#
# A list of known strong SecureRandom implementations.
#
# To help guide applications in selecting a suitable strong
# java.security.SecureRandom implementation, Java distributions should
# indicate a list of known strong implementations using the property.
#
# This is a comma-separated list of algorithm and/or algorithm:provider
# entries.
#
securerandom.strongAlgorithms=Windows-PRNG:SunMSCAPI,DRBG:SUN

#
# Sun provider DRBG configuration and default instantiation request.
#
# NIST SP 800-90Ar1 lists several DRBG mechanisms. Each can be configured
# with a DRBG algorithm name, and can be instantiated with a security strength,
# prediction resistance support, etc. This property defines the configuration
# and the default instantiation request of "DRBG" SecureRandom implementations
# in the SUN provider. (Other DRBG implementations can also use this property.)
# Applications can request different instantiation parameters like security
# strength, capability, personalization string using one of the
# getInstance(...,SecureRandomParameters,...) methods with a
# DrbgParameters.Instantiation argument, but other settings such as the
# mechanism and DRBG algorithm names are not currently configurable by any API.
#
# Please note that the SUN implementation of DRBG always supports reseeding.
#
# The value of this property is a comma-separated list of all configurable
# aspects. The aspects can appear in any order but the same aspect can only
# appear at most once. Its BNF-style definition is:
#
#   Value:
#     aspect { "," aspect }
#
#   aspect:
#     mech_name | algorithm_name | strength | capability | df
#
#   // The DRBG mechanism to use. Default "Hash_DRBG"
#   mech_name:
#     "Hash_DRBG" | "HMAC_DRBG" | "CTR_DRBG"
#
#   // The DRBG algorithm name. The "SHA-***" names are for Hash_DRBG and
#   // HMAC_DRBG, default "SHA-256". The "AES-***" names are for CTR_DRBG,
#   // default "AES-128" when using the limited cryptographic or "AES-256"
#   // when using the unlimited.
#   algorithm_name:
#     "SHA-224" | "SHA-512/224" | "SHA-256" |
#     "SHA-512/256" | "SHA-384" | "SHA-512" |
#     "AES-128" | "AES-192" | "AES-256"
#
#   // Security strength requested. Default "128"
#   strength:
#     "112" | "128" | "192" | "256"
#
#   // Prediction resistance and reseeding request. Default "none"
#   //  "pr_and_reseed" - Both prediction resistance and reseeding
#   //                    support requested
#   //  "reseed_only"   - Only reseeding support requested
#   //  "none"          - Neither prediction resistance not reseeding
#   //                    support requested
#   pr:
#     "pr_and_reseed" | "reseed_only" | "none"
#
#   // Whether a derivation function should be used. only applicable
#   // to CTR_DRBG. Default "use_df"
#   df:
#     "use_df" | "no_df"
#
# Examples,
#   securerandom.drbg.config=Hash_DRBG,SHA-224,112,none
#   securerandom.drbg.config=CTR_DRBG,AES-256,192,pr_and_reseed,use_df
#
# The default value is an empty string, which is equivalent to
#   securerandom.drbg.config=Hash_DRBG,SHA-256,128,none
#
securerandom.drbg.config=

#
# Class to instantiate as the javax.security.auth.login.Configuration
# provider.
#
login.configuration.provider=sun.security.provider.ConfigFile

#
# Default login configuration file
#
#login.config.url.1=file:${user.home}/.java.login.config

#
# Class to instantiate as the system Policy. This is the name of the class
# that will be used as the Policy object. The system class loader is used to
# locate this class.
#
policy.provider=sun.security.provider.PolicyFile

# The default is to have a single system-wide policy file,
# and a policy file in the user's home directory.
#
policy.url.1=file:${java.home}/conf/security/java.policy
policy.url.2=file:${user.home}/.java.policy

# Controls whether or not properties are expanded in policy and login
# configuration files. If set to false, properties (${...}) will not
# be expanded in policy and login configuration files. If commented out or
# set to an empty string, the default value is "false" for policy files and
# "true" for login configuration files.
#
policy.expandProperties=true

# Controls whether or not an extra policy or login configuration file is
# allowed to be passed on the command line with -Djava.security.policy=somefile
# or -Djava.security.auth.login.config=somefile. If commented out or set to
# an empty string, the default value is "false".
#
policy.allowSystemProperty=true

# whether or not we look into the IdentityScope for trusted Identities
# when encountering a 1.1 signed JAR file. If the identity is found
# and is trusted, we grant it AllPermission. Note: the default policy
# provider (sun.security.provider.PolicyFile) does not support this property.
#
policy.ignoreIdentityScope=false

#
# Default keystore type.
#
keystore.type=pkcs12

#
# Controls compatibility mode for JKS and PKCS12 keystore types.
#
# When set to 'true', both JKS and PKCS12 keystore types support loading
# keystore files in either JKS or PKCS12 format. When set to 'false' the
# JKS keystore type supports loading only JKS keystore files and the PKCS12
# keystore type supports loading only PKCS12 keystore files.
#
keystore.type.compat=true

#
# List of comma-separated packages that start with or equal this string
# will cause a security exception to be thrown when passed to the
# SecurityManager::checkPackageAccess method unless the corresponding
# RuntimePermission("accessClassInPackage."+package) has been granted.
#
package.access=sun.misc.,\
               sun.reflect.

#
# List of comma-separated packages that start with or equal this string
# will cause a security exception to be thrown when passed to the
# SecurityManager::checkPackageDefinition method unless the corresponding
# RuntimePermission("defineClassInPackage."+package) has been granted.
#
# By default, none of the class loaders supplied with the JDK call
# checkPackageDefinition.
#
package.definition=sun.misc.,\
                   sun.reflect.

#
# Determines whether this properties file can be appended to
# or overridden on the command line via -Djava.security.properties
#
security.overridePropertiesFile=true

#
# Determines the default key and trust manager factory algorithms for
# the javax.net.ssl package.
#
ssl.KeyManagerFactory.algorithm=SunX509
ssl.TrustManagerFactory.algorithm=PKIX

#
# The Java-level namelookup cache policy for successful lookups:
#
# any negative value: caching forever
# any positive value: the number of seconds to cache an address for
# zero: do not cache
#
# default value is forever (FOREVER). For security reasons, this
# caching is made forever when a security manager is set. When a security
# manager is not set, the default behavior in this implementation
# is to cache for 30 seconds.
#
# NOTE: setting this to anything other than the default value can have
#       serious security implications. Do not set it unless
#       you are sure you are not exposed to DNS spoofing attack.
#
#networkaddress.cache.ttl=-1

# The Java-level namelookup cache policy for failed lookups:
#
# any negative value: cache forever
# any positive value: the number of seconds to cache negative lookup results
# zero: do not cache
#
# In some Microsoft Windows networking environments that employ
# the WINS name service in addition to DNS, name service lookups
# that fail may take a noticeably long time to return (approx. 5 seconds).
# For this reason the default caching policy is to maintain these
# results for 10 seconds.
#
networkaddress.cache.negative.ttl=10

#
# Properties to configure OCSP for certificate revocation checking
#

# Enable OCSP
#
# By default, OCSP is not used for certificate revocation checking.
# This property enables the use of OCSP when set to the value "true".
#
# NOTE: SocketPermission is required to connect to an OCSP responder.
#
# Example,
#   ocsp.enable=true

#
# Location of the OCSP responder
#
# By default, the location of the OCSP responder is determined implicitly
# from the certificate being validated. This property explicitly specifies
# the location of the OCSP responder. The property is used when the
# Authority Information Access extension (defined in RFC 5280) is absent
# from the certificate or when it requires overriding.
#
# Example,
#   ocsp.responderURL=http://ocsp.example.net:80

#
# Subject name of the OCSP responder's certificate
#
# By default, the certificate of the OCSP responder is that of the issuer
# of the certificate being validated. This property identifies the certificate
# of the OCSP responder when the default does not apply. Its value is a string
# distinguished name (defined in RFC 2253) which identifies a certificate in
# the set of certificates supplied during cert path validation. In cases where
# the subject name alone is not sufficient to uniquely identify the certificate
# then both the "ocsp.responderCertIssuerName" and
# "ocsp.responderCertSerialNumber" properties must be used instead. When this
# property is set then those two properties are ignored.
#
# Example,
#   ocsp.responderCertSubjectName=CN=OCSP Responder, O=XYZ Corp

#
# Issuer name of the OCSP responder's certificate
#
# By default, the certificate of the OCSP responder is that of the issuer
# of the certificate being validated. This property identifies the certificate
# of the OCSP responder when the default does not apply. Its value is a string
# distinguished name (defined in RFC 2253) which identifies a certificate in
# the set of certificates supplied during cert path validation. When this
# property is set then the "ocsp.responderCertSerialNumber" property must also
# be set. When the "ocsp.responderCertSubjectName" property is set then this
# property is ignored.
#
# Example,
#   ocsp.responderCertIssuerName=CN=Enterprise CA, O=XYZ Corp

#
# Serial number of the OCSP responder's certificate
#
# By default, the certificate of the OCSP responder is that of the issuer
# of the certificate being validated. This property identifies the certificate
# of the OCSP responder when the default does not apply. Its value is a string
# of hexadecimal digits (colon or space separators may be present) which
# identifies a certificate in the set of certificates supplied during cert path
# validation. When this property is set then the "ocsp.responderCertIssuerName"
# property must also be set. When the "ocsp.responderCertSubjectName" property
# is set then this property is ignored.
#
# Example,
#   ocsp.responderCertSerialNumber=2A:FF:00

#
# Policy for failed Kerberos KDC lookups:
#
# When a KDC is unavailable (network error, service failure, etc), it is
# put inside a secondary list and accessed less often for future requests. The
# value (case-insensitive) for this policy can be:
#
# tryLast
#    KDCs in the secondary list are always tried after those not on the list.
#
# tryLess[:max_retries,timeout]
#    KDCs in the secondary list are still tried by their order in the
#    configuration, but with smaller max_retries and timeout values.
#    max_retries and timeout are optional numerical parameters (default 1 and
#    5000, which means once and 5 seconds). Please note that if any of the
#    values defined here are more than what is defined in krb5.conf, it will be
#    ignored.
#
# Whenever a KDC is detected as available, it is removed from the secondary
# list. The secondary list is reset when krb5.conf is reloaded. You can add
# refreshKrb5Config=true to a JAAS configuration file so that krb5.conf is
# reloaded whenever a JAAS authentication is attempted.
#
# Example,
#   krb5.kdc.bad.policy = tryLast
#   krb5.kdc.bad.policy = tryLess:2,2000
#
krb5.kdc.bad.policy = tryLast

#
# Kerberos cross-realm referrals (RFC 6806)
#
# OpenJDK's Kerberos client supports cross-realm referrals as defined in
# RFC 6806. This allows to setup more dynamic environments in which clients
# do not need to know in advance how to reach the realm of a target principal
# (either a user or service).
#
# When a client issues an AS or a TGS request, the "canonicalize" option
# is set to announce support of this feature. A KDC server may fulfill the
# request or reply referring the client to a different one. If referred,
# the client will issue a new request and the cycle repeats.
#
# In addition to referrals, the "canonicalize" option allows the KDC server
# to change the client name in response to an AS request. For security reasons,
# RFC 6806 (section 11) FAST scheme is enforced.
#
# Disable Kerberos cross-realm referrals. Value may be overwritten with a
# System property (-Dsun.security.krb5.disableReferrals).
sun.security.krb5.disableReferrals=false

# Maximum number of AS or TGS referrals to avoid infinite loops. Value may
# be overwritten with a System property (-Dsun.security.krb5.maxReferrals).
sun.security.krb5.maxReferrals=5

#
# This property contains a list of disabled EC Named Curves that can be included
# in the jdk.[tls|certpath|jar].disabledAlgorithms properties.  To include this
# list in any of the disabledAlgorithms properties, add the property name as
# an entry.
#jdk.disabled.namedCurves=

#
# Algorithm restrictions for certification path (CertPath) processing
#
# In some environments, certain algorithms or key lengths may be undesirable
# for certification path building and validation.  For example, "MD2" is
# generally no longer considered to be a secure hash algorithm.  This section
# describes the mechanism for disabling algorithms based on algorithm name
# and/or key length.  This includes algorithms used in certificates, as well
# as revocation information such as CRLs and signed OCSP Responses.
# The syntax of the disabled algorithm string is described as follows:
#   DisabledAlgorithms:
#       " DisabledAlgorithm { , DisabledAlgorithm } "
#
#   DisabledAlgorithm:
#       AlgorithmName [Constraint] { '&' Constraint } | IncludeProperty
#
#   AlgorithmName:
#       (see below)
#
#   Constraint:
#       KeySizeConstraint | CAConstraint | DenyAfterConstraint |
#       UsageConstraint
#
#   KeySizeConstraint:
#       keySize Operator KeyLength
#
#   Operator:
#       <= | < | == | != | >= | >
#
#   KeyLength:
#       Integer value of the algorithm's key length in bits
#
#   CAConstraint:
#       jdkCA
#
#   DenyAfterConstraint:
#       denyAfter YYYY-MM-DD
#
#   UsageConstraint:
#       usage [TLSServer] [TLSClient] [SignedJAR]
#
#   IncludeProperty:
#       include <security property>
#
# The "AlgorithmName" is the standard algorithm name of the disabled
# algorithm. See the Java Security Standard Algorithm Names Specification
# for information about Standard Algorithm Names.  Matching is
# performed using a case-insensitive sub-element matching rule.  (For
# example, in "SHA1withECDSA" the sub-elements are "SHA1" for hashing and
# "ECDSA" for signatures.)  If the assertion "AlgorithmName" is a
# sub-element of the certificate algorithm name, the algorithm will be
# rejected during certification path building and validation.  For example,
# the assertion algorithm name "DSA" will disable all certificate algorithms
# that rely on DSA, such as NONEwithDSA, SHA1withDSA.  However, the assertion
# will not disable algorithms related to "ECDSA".
#
# The "IncludeProperty" allows a implementation-defined security property that
# can be included in the disabledAlgorithms properties.  These properties are
# to help manage common actions easier across multiple disabledAlgorithm
# properties.
# There is one defined security property:  jdk.disabled.NamedCurves
# See the property for more specific details.
#
#
# A "Constraint" defines restrictions on the keys and/or certificates for
# a specified AlgorithmName:
#
#   KeySizeConstraint:
#     keySize Operator KeyLength
#       The constraint requires a key of a valid size range if the
#       "AlgorithmName" is of a key algorithm.  The "KeyLength" indicates
#       the key size specified in number of bits.  For example,
#       "RSA keySize <= 1024" indicates that any RSA key with key size less
#       than or equal to 1024 bits should be disabled, and
#       "RSA keySize < 1024, RSA keySize > 2048" indicates that any RSA key
#       with key size less than 1024 or greater than 2048 should be disabled.
#       This constraint is only used on algorithms that have a key size.
#
#   CAConstraint:
#     jdkCA
#       This constraint prohibits the specified algorithm only if the
#       algorithm is used in a certificate chain that terminates at a marked
#       trust anchor in the lib/security/cacerts keystore.  If the jdkCA
#       constraint is not set, then all chains using the specified algorithm
#       are restricted.  jdkCA may only be used once in a DisabledAlgorithm
#       expression.
#       Example:  To apply this constraint to SHA-1 certificates, include
#       the following:  "SHA1 jdkCA"
#
#   DenyAfterConstraint:
#     denyAfter YYYY-MM-DD
#       This constraint prohibits a certificate with the specified algorithm
#       from being used after the date regardless of the certificate's
#       validity.  JAR files that are signed and timestamped before the
#       constraint date with certificates containing the disabled algorithm
#       will not be restricted.  The date is processed in the UTC timezone.
#       This constraint can only be used once in a DisabledAlgorithm
#       expression.
#       Example:  To deny usage of RSA 2048 bit certificates after Feb 3 2020,
#       use the following:  "RSA keySize == 2048 & denyAfter 2020-02-03"
#
#   UsageConstraint:
#     usage [TLSServer] [TLSClient] [SignedJAR]
#       This constraint prohibits the specified algorithm for
#       a specified usage.  This should be used when disabling an algorithm
#       for all usages is not practical. 'TLSServer' restricts the algorithm
#       in TLS server certificate chains when server authentication is
#       performed. 'TLSClient' restricts the algorithm in TLS client
#       certificate chains when client authentication is performed.
#       'SignedJAR' constrains use of certificates in signed jar files.
#       The usage type follows the keyword and more than one usage type can
#       be specified with a whitespace delimiter.
#       Example:  "SHA1 usage TLSServer TLSClient"
#
# When an algorithm must satisfy more than one constraint, it must be
# delimited by an ampersand '&'.  For example, to restrict certificates in a
# chain that terminate at a distribution provided trust anchor and contain
# RSA keys that are less than or equal to 1024 bits, add the following
# constraint:  "RSA keySize <= 1024 & jdkCA".
#
# All DisabledAlgorithms expressions are processed in the order defined in the
# property.  This requires lower keysize constraints to be specified
# before larger keysize constraints of the same algorithm.  For example:
# "RSA keySize < 1024 & jdkCA, RSA keySize < 2048".
#
# Note: The algorithm restrictions do not apply to trust anchors or
# self-signed certificates.
#
# Note: This property is currently used by Oracle's PKIX implementation. It
# is not guaranteed to be examined and used by other implementations.
#
# Example:
#   jdk.certpath.disabledAlgorithms=MD2, DSA, RSA keySize < 2048
#
#
jdk.certpath.disabledAlgorithms=MD2, MD5, SHA1 jdkCA & usage TLSServer, \
    RSA keySize < 1024, DSA keySize < 1024, EC keySize < 224

#
# Legacy algorithms for certification path (CertPath) processing and
# signed JAR files.
#
# In some environments, a certain algorithm or key length may be undesirable
# but is not yet disabled.
#
# Tools such as keytool and jarsigner may emit warnings when these legacy
# algorithms are used. See the man pages for those tools for more information.
#
# The syntax is the same as the "jdk.certpath.disabledAlgorithms" and
# "jdk.jar.disabledAlgorithms" security properties.
#
# Note: This property is currently used by the JDK Reference
# implementation. It is not guaranteed to be examined and used by other
# implementations.

jdk.security.legacyAlgorithms=SHA1, \
    RSA keySize < 2048, DSA keySize < 2048

#
# Algorithm restrictions for signed JAR files
#
# In some environments, certain algorithms or key lengths may be undesirable
# for signed JAR validation.  For example, "MD2" is generally no longer
# considered to be a secure hash algorithm.  This section describes the
# mechanism for disabling algorithms based on algorithm name and/or key length.
# JARs signed with any of the disabled algorithms or key sizes will be treated
# as unsigned.
#
# The syntax of the disabled algorithm string is described as follows:
#   DisabledAlgorithms:
#       " DisabledAlgorithm { , DisabledAlgorithm } "
#
#   DisabledAlgorithm:
#       AlgorithmName [Constraint] { '&' Constraint }
#
#   AlgorithmName:
#       (see below)
#
#   Constraint:
#       KeySizeConstraint | DenyAfterConstraint
#
#   KeySizeConstraint:
#       keySize Operator KeyLength
#
#   DenyAfterConstraint:
#       denyAfter YYYY-MM-DD
#
#   Operator:
#       <= | < | == | != | >= | >
#
#   KeyLength:
#       Integer value of the algorithm's key length in bits
#
# Note: This property is currently used by the JDK Reference
# implementation. It is not guaranteed to be examined and used by other
# implementations.
#
# See "jdk.certpath.disabledAlgorithms" for syntax descriptions.
#
jdk.jar.disabledAlgorithms=MD2, MD5, RSA keySize < 1024, \
      DSA keySize < 1024

#
# Algorithm restrictions for Secure Socket Layer/Transport Layer Security
# (SSL/TLS/DTLS) processing
#
# In some environments, certain algorithms or key lengths may be undesirable
# when using SSL/TLS/DTLS.  This section describes the mechanism for disabling
# algorithms during SSL/TLS/DTLS security parameters negotiation, including
# protocol version negotiation, cipher suites selection, named groups
# selection, signature schemes selection, peer authentication and key
# exchange mechanisms.
#
# Disabled algorithms will not be negotiated for SSL/TLS connections, even
# if they are enabled explicitly in an application.
#
# For PKI-based peer authentication and key exchange mechanisms, this list
# of disabled algorithms will also be checked during certification path
# building and validation, including algorithms used in certificates, as
# well as revocation information such as CRLs and signed OCSP Responses.
# This is in addition to the jdk.certpath.disabledAlgorithms property above.
#
# See the specification of "jdk.certpath.disabledAlgorithms" for the
# syntax of the disabled algorithm string.
#
# Note: The algorithm restrictions do not apply to trust anchors or
# self-signed certificates.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
#
# Example:
#   jdk.tls.disabledAlgorithms=MD5, SSLv3, DSA, RSA keySize < 2048, \
#       rsa_pkcs1_sha1, secp224r1
jdk.tls.disabledAlgorithms=SSLv3, TLSv1, TLSv1.1, RC4, DES, MD5withRSA, \
    DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL

#
# Legacy algorithms for Secure Socket Layer/Transport Layer Security (SSL/TLS)
# processing in JSSE implementation.
#
# In some environments, a certain algorithm may be undesirable but it
# cannot be disabled because of its use in legacy applications.  Legacy
# algorithms may still be supported, but applications should not use them
# as the security strength of legacy algorithms are usually not strong enough
# in practice.
#
# During SSL/TLS security parameters negotiation, legacy algorithms will
# not be negotiated unless there are no other candidates.
#
# The syntax of the legacy algorithms string is described as this Java
# BNF-style:
#   LegacyAlgorithms:
#       " LegacyAlgorithm { , LegacyAlgorithm } "
#
#   LegacyAlgorithm:
#       AlgorithmName (standard JSSE algorithm name)
#
# See the specification of security property "jdk.certpath.disabledAlgorithms"
# for the syntax and description of the "AlgorithmName" notation.
#
# Per SSL/TLS specifications, cipher suites have the form:
#       SSL_KeyExchangeAlg_WITH_CipherAlg_MacAlg
# or
#       TLS_KeyExchangeAlg_WITH_CipherAlg_MacAlg
#
# For example, the cipher suite TLS_RSA_WITH_AES_128_CBC_SHA uses RSA as the
# key exchange algorithm, AES_128_CBC (128 bits AES cipher algorithm in CBC
# mode) as the cipher (encryption) algorithm, and SHA-1 as the message digest
# algorithm for HMAC.
#
# The LegacyAlgorithm can be one of the following standard algorithm names:
#     1. JSSE cipher suite name, e.g., TLS_RSA_WITH_AES_128_CBC_SHA
#     2. JSSE key exchange algorithm name, e.g., RSA
#     3. JSSE cipher (encryption) algorithm name, e.g., AES_128_CBC
#     4. JSSE message digest algorithm name, e.g., SHA
#
# See SSL/TLS specifications and the Java Security Standard Algorithm Names
# Specification for information about the algorithm names.
#
# Note: If a legacy algorithm is also restricted through the
# jdk.tls.disabledAlgorithms property or the
# java.security.AlgorithmConstraints API (See
# javax.net.ssl.SSLParameters.setAlgorithmConstraints()),
# then the algorithm is completely disabled and will not be negotiated.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
# There is no guarantee the property will continue to exist or be of the
# same syntax in future releases.
#
# Example:
#   jdk.tls.legacyAlgorithms=DH_anon, DES_CBC, SSL_RSA_WITH_RC4_128_MD5
#
jdk.tls.legacyAlgorithms=NULL, anon, RC4, DES, 3DES_EDE_CBC

#
# The pre-defined default finite field Diffie-Hellman ephemeral (DHE)
# parameters for Transport Layer Security (SSL/TLS/DTLS) processing.
#
# In traditional SSL/TLS/DTLS connections where finite field DHE parameters
# negotiation mechanism is not used, the server offers the client group
# parameters, base generator g and prime modulus p, for DHE key exchange.
# It is recommended to use dynamic group parameters.  This property defines
# a mechanism that allows you to specify custom group parameters.
#
# The syntax of this property string is described as this Java BNF-style:
#   DefaultDHEParameters:
#       DefinedDHEParameters { , DefinedDHEParameters }
#
#   DefinedDHEParameters:
#       "{" DHEPrimeModulus , DHEBaseGenerator "}"
#
#   DHEPrimeModulus:
#       HexadecimalDigits
#
#   DHEBaseGenerator:
#       HexadecimalDigits
#
#   HexadecimalDigits:
#       HexadecimalDigit { HexadecimalDigit }
#
#   HexadecimalDigit: one of
#       0 1 2 3 4 5 6 7 8 9 A B C D E F a b c d e f
#
# Whitespace characters are ignored.
#
# The "DefinedDHEParameters" defines the custom group parameters, prime
# modulus p and base generator g, for a particular size of prime modulus p.
# The "DHEPrimeModulus" defines the hexadecimal prime modulus p, and the
# "DHEBaseGenerator" defines the hexadecimal base generator g of a group
# parameter.  It is recommended to use safe primes for the custom group
# parameters.
#
# If this property is not defined or the value is empty, the underlying JSSE
# provider's default group parameter is used for each connection.
#
# If the property value does not follow the grammar, or a particular group
# parameter is not valid, the connection will fall back and use the
# underlying JSSE provider's default group parameter.
#
# Note: This property is currently used by OpenJDK's JSSE implementation. It
# is not guaranteed to be examined and used by other implementations.
#
# Example:
#   jdk.tls.server.defaultDHEParameters=
#       { \
#       FFFFFFFF FFFFFFFF C90FDAA2 2168C234 C4C6628B 80DC1CD1 \
#       29024E08 8A67CC74 020BBEA6 3B139B22 514A0879 8E3404DD \
#       EF9519B3 CD3A431B 302B0A6D F25F1437 4FE1356D 6D51C245 \
#       E485B576 625E7EC6 F44C42E9 A637ED6B 0BFF5CB6 F406B7ED \
#       EE386BFB 5A899FA5 AE9F2411 7C4B1FE6 49286651 ECE65381 \
#       FFFFFFFF FFFFFFFF, 2}

#
# TLS key limits on symmetric cryptographic algorithms
#
# This security property sets limits on algorithms key usage in TLS 1.3.
# When the amount of data encrypted exceeds the algorithm value listed below,
# a KeyUpdate message will trigger a key change.  This is for symmetric ciphers
# with TLS 1.3 only.
#
# The syntax for the property is described below:
#   KeyLimits:
#       " KeyLimit { , KeyLimit } "
#
#   WeakKeyLimit:
#       AlgorithmName Action Length
#
#   AlgorithmName:
#       A full algorithm transformation.
#
#   Action:
#       KeyUpdate
#
#   Length:
#       The amount of encrypted data in a session before the Action occurs
#       This value may be an integer value in bytes, or as a power of two, 2^29.
#
#   KeyUpdate:
#       The TLS 1.3 KeyUpdate handshake process begins when the Length amount
#       is fulfilled.
#
# Note: This property is currently used by OpenJDK's JSSE implementation. It
# is not guaranteed to be examined and used by other implementations.
#
jdk.tls.keyLimits=AES/GCM/NoPadding KeyUpdate 2^37

#
# Cryptographic Jurisdiction Policy defaults
#
# Import and export control rules on cryptographic software vary from
# country to country.  By default, Java provides two different sets of
# cryptographic policy files[1]:
#
#     unlimited:  These policy files contain no restrictions on cryptographic
#                 strengths or algorithms
#
#     limited:    These policy files contain more restricted cryptographic
#                 strengths
#
# The default setting is determined by the value of the "crypto.policy"
# Security property below. If your country or usage requires the
# traditional restrictive policy, the "limited" Java cryptographic
# policy is still available and may be appropriate for your environment.
#
# If you have restrictions that do not fit either use case mentioned
# above, Java provides the capability to customize these policy files.
# The "crypto.policy" security property points to a subdirectory
# within <java-home>/conf/security/policy/ which can be customized.
# Please see the <java-home>/conf/security/policy/README.txt file or consult
# the Java Security Guide/JCA documentation for more information.
#
# YOU ARE ADVISED TO CONSULT YOUR EXPORT/IMPORT CONTROL COUNSEL OR ATTORNEY
# TO DETERMINE THE EXACT REQUIREMENTS.
#
# [1] Please note that the JCE for Java SE, including the JCE framework,
# cryptographic policy files, and standard JCE providers provided with
# the Java SE, have been reviewed and approved for export as mass market
# encryption item by the US Bureau of Industry and Security.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
#
crypto.policy=unlimited

#
# The policy for the XML Signature secure validation mode. Validation of
# XML Signatures that violate any of these constraints will fail. The
# mode is enforced by default. The mode can be disabled by setting the
# property "org.jcp.xml.dsig.secureValidation" to Boolean.FALSE with the
# javax.xml.crypto.XMLCryptoContext.setProperty() method.
#
#   Policy:
#       Constraint {"," Constraint }
#   Constraint:
#       AlgConstraint | MaxTransformsConstraint | MaxReferencesConstraint |
#       ReferenceUriSchemeConstraint | KeySizeConstraint | OtherConstraint
#   AlgConstraint
#       "disallowAlg" Uri
#   MaxTransformsConstraint:
#       "maxTransforms" Integer
#   MaxReferencesConstraint:
#       "maxReferences" Integer
#   ReferenceUriSchemeConstraint:
#       "disallowReferenceUriSchemes" String { String }
#   KeySizeConstraint:
#       "minKeySize" KeyAlg Integer
#   OtherConstraint:
#       "noDuplicateIds" | "noRetrievalMethodLoops"
#
# For AlgConstraint, Uri is the algorithm URI String that is not allowed.
# See the XML Signature Recommendation for more information on algorithm
# URI Identifiers. For KeySizeConstraint, KeyAlg is the standard algorithm
# name of the key type (ex: "RSA"). If the MaxTransformsConstraint,
# MaxReferencesConstraint or KeySizeConstraint (for the same key type) is
# specified more than once, only the last entry is enforced.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
#
jdk.xml.dsig.secureValidationPolicy=\
    disallowAlg http://www.w3.org/TR/1999/REC-xslt-19991116,\
    disallowAlg http://www.w3.org/2001/04/xmldsig-more#rsa-md5,\
    disallowAlg http://www.w3.org/2001/04/xmldsig-more#hmac-md5,\
    disallowAlg http://www.w3.org/2001/04/xmldsig-more#md5,\
    disallowAlg http://www.w3.org/2000/09/xmldsig#sha1,\
    disallowAlg http://www.w3.org/2000/09/xmldsig#dsa-sha1,\
    disallowAlg http://www.w3.org/2000/09/xmldsig#rsa-sha1,\
    disallowAlg http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1,\
    disallowAlg http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1,\
    maxTransforms 5,\
    maxReferences 30,\
    disallowReferenceUriSchemes file http https,\
    minKeySize RSA 1024,\
    minKeySize DSA 1024,\
    minKeySize EC 224,\
    noDuplicateIds,\
    noRetrievalMethodLoops


#
# Deserialization JVM-wide filter factory
#
# A filter factory class name is used to configure the JVM-wide filter factory.
# The class must be public, must have a public zero-argument constructor, implement the
# java.util.function.BinaryOperator<java.io.ObjectInputFilter> interface, provide its
# implementation and be accessible via the application class loader.
# A builtin filter factory is used if no filter factory is defined.
# See java.io.ObjectInputFilter.Config for more information.
#
# If the system property jdk.serialFilterFactory is also specified, it supersedes
# the security property value defined here.
#
#jdk.serialFilterFactory=<classname>

#
# Deserialization JVM-wide filter
#
# A filter, if configured, is used by the filter factory to provide the filter used by
# java.io.ObjectInputStream during deserialization to check the contents of the stream.
# A filter is configured as a sequence of patterns, each pattern is either
# matched against the name of a class in the stream or defines a limit.
# Patterns are separated by ";" (semicolon).
# Whitespace is significant and is considered part of the pattern.
#
# If the system property jdk.serialFilter is also specified, it supersedes
# the security property value defined here.
#
# If a pattern includes a "=", it sets a limit.
# If a limit appears more than once the last value is used.
# Limits are checked before classes regardless of the order in the
# sequence of patterns.
# If any of the limits are exceeded, the filter status is REJECTED.
#
#   maxdepth=value - the maximum depth of a graph
#   maxrefs=value  - the maximum number of internal references
#   maxbytes=value - the maximum number of bytes in the input stream
#   maxarray=value - the maximum array length allowed
#
# Other patterns, from left to right, match the class or package name as
# returned from Class.getName.
# If the class is an array type, the class or package to be matched is the
# element type.
# Arrays of any number of dimensions are treated the same as the element type.
# For example, a pattern of "!example.Foo", rejects creation of any instance or
# array of example.Foo.
#
# If the pattern starts with "!", the status is REJECTED if the remaining
# pattern is matched; otherwise the status is ALLOWED if the pattern matches.
# If the pattern contains "/", the non-empty prefix up to the "/" is the
# module name;
#   if the module name matches the module name of the class then
#   the remaining pattern is matched with the class name.
#   If there is no "/", the module name is not compared.
# If the pattern ends with ".**" it matches any class in the package and all
# subpackages.
# If the pattern ends with ".*" it matches any class in the package.
# If the pattern ends with "*", it matches any class with the pattern as a
# prefix.
# If the pattern is equal to the class name, it matches.
# Otherwise, the status is UNDECIDED.
#
#jdk.serialFilter=pattern;pattern

#
# RMI Registry Serial Filter
#
# The filter pattern uses the same format as jdk.serialFilter.
# This filter can override the builtin filter if additional types need to be
# allowed or rejected from the RMI Registry or to decrease limits but not
# to increase limits.
# If the limits (maxdepth, maxrefs, or maxbytes) are exceeded, the object is rejected.
#
# Each non-array type is allowed or rejected if it matches one of the patterns,
# evaluated from left to right, and is otherwise allowed. Arrays of any
# component type, including subarrays and arrays of primitives, are allowed.
#
# Array construction of any component type, including subarrays and arrays of
# primitives, are allowed unless the length is greater than the maxarray limit.
# The filter is applied to each array element.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
#
# The built-in filter allows subclasses of allowed classes and
# can approximately be represented as the pattern:
#
#sun.rmi.registry.registryFilter=\
#    maxarray=1000000;\
#    maxdepth=20;\
#    java.lang.String;\
#    java.lang.Number;\
#    java.lang.reflect.Proxy;\
#    java.rmi.Remote;\
#    sun.rmi.server.UnicastRef;\
#    sun.rmi.server.RMIClientSocketFactory;\
#    sun.rmi.server.RMIServerSocketFactory;\
#    java.rmi.server.UID
#
# RMI Distributed Garbage Collector (DGC) Serial Filter
#
# The filter pattern uses the same format as jdk.serialFilter.
# This filter can override the builtin filter if additional types need to be
# allowed or rejected from the RMI DGC.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
#
# The builtin DGC filter can approximately be represented as the filter pattern:
#
#sun.rmi.transport.dgcFilter=\
#    java.rmi.server.ObjID;\
#    java.rmi.server.UID;\
#    java.rmi.dgc.VMID;\
#    java.rmi.dgc.Lease;\
#    maxdepth=5;maxarray=10000

#
# JCEKS Encrypted Key Serial Filter
#
# This filter, if configured, is used by the JCEKS KeyStore during the
# deserialization of the encrypted Key object stored inside a key entry.
# If not configured or the filter result is UNDECIDED (i.e. none of the patterns
# matches), the filter configured by jdk.serialFilter will be consulted.
#
# If the system property jceks.key.serialFilter is also specified, it supersedes
# the security property value defined here.
#
# The filter pattern uses the same format as jdk.serialFilter. The default
# pattern allows java.lang.Enum, java.security.KeyRep, java.security.KeyRep$Type,
# and javax.crypto.spec.SecretKeySpec and rejects all the others.
jceks.key.serialFilter = java.base/java.lang.Enum;java.base/java.security.KeyRep;\
  java.base/java.security.KeyRep$Type;java.base/javax.crypto.spec.SecretKeySpec;!*

# The iteration count used for password-based encryption (PBE) in JCEKS
# keystores. Values in the range 10000 to 5000000 are considered valid.
# If the value is out of this range, or is not a number, or is unspecified;
# a default of 200000 is used.
#
# If the system property jdk.jceks.iterationCount is also specified, it
# supersedes the security property value defined here.
#
#jdk.jceks.iterationCount = 200000

#
# PKCS12 KeyStore properties
#
# The following properties, if configured, are used by the PKCS12 KeyStore
# implementation during the creation of a new keystore. Several of the
# properties may also be used when modifying an existing keystore. The
# properties can be overridden by a KeyStore API that specifies its own
# algorithms and parameters.
#
# If an existing PKCS12 keystore is loaded and then stored, the algorithm and
# parameter used to generate the existing Mac will be reused. If the existing
# keystore does not have a Mac, no Mac will be created while storing. If there
# is at least one certificate in the existing keystore, the algorithm and
# parameters used to encrypt the last certificate in the existing keystore will
# be reused to encrypt all certificates while storing. If the last certificate
# in the existing keystore is not encrypted, all certificates will be stored
# unencrypted. If there is no certificate in the existing keystore, any newly
# added certificate will be encrypted (or stored unencrypted if algorithm
# value is "NONE") using the "keystore.pkcs12.certProtectionAlgorithm" and
# "keystore.pkcs12.certPbeIterationCount" values defined here. Existing private
# and secret key(s) are not changed. Newly set private and secret key(s) will
# be encrypted using the "keystore.pkcs12.keyProtectionAlgorithm" and
# "keystore.pkcs12.keyPbeIterationCount" values defined here.
#
# In order to apply new algorithms and parameters to all entries in an
# existing keystore, one can create a new keystore and add entries in the
# existing keystore into the new keystore. This can be achieved by calling the
# "keytool -importkeystore" command.
#
# If a system property of the same name is also specified, it supersedes the
# security property value defined here.
#
# If the property is set to an illegal value,
# an iteration count that is not a positive integer, or an unknown algorithm
# name, an exception will be thrown when the property is used.
# If the property is not set or empty, a default value will be used.
#
# Note: These properties are currently used by the JDK Reference implementation.
# They are not guaranteed to be examined and used by other implementations.

# The algorithm used to encrypt a certificate. This can be any non-Hmac PBE
# algorithm defined in the Cipher section of the Java Security Standard
# Algorithm Names Specification. When set to "NONE", the certificate
# is not encrypted. The default value is "PBEWithHmacSHA256AndAES_256".
#keystore.pkcs12.certProtectionAlgorithm = PBEWithHmacSHA256AndAES_256

# The iteration count used by the PBE algorithm when encrypting a certificate.
# This value must be a positive integer. The default value is 10000.
#keystore.pkcs12.certPbeIterationCount = 10000

# The algorithm used to encrypt a private key or secret key. This can be
# any non-Hmac PBE algorithm defined in the Cipher section of the Java
# Security Standard Algorithm Names Specification. The value must not be "NONE".
# The default value is "PBEWithHmacSHA256AndAES_256".
#keystore.pkcs12.keyProtectionAlgorithm = PBEWithHmacSHA256AndAES_256

# The iteration count used by the PBE algorithm when encrypting a private key
# or a secret key. This value must be a positive integer. The default value
# is 10000.
#keystore.pkcs12.keyPbeIterationCount = 10000

# The algorithm used to calculate the optional MacData at the end of a PKCS12
# file. This can be any HmacPBE algorithm defined in the Mac section of the
# Java Security Standard Algorithm Names Specification. When set to "NONE",
# no Mac is generated. The default value is "HmacPBESHA256".
#keystore.pkcs12.macAlgorithm = HmacPBESHA256

# The iteration count used by the MacData algorithm. This value must be a
# positive integer. The default value is 10000.
#keystore.pkcs12.macIterationCount = 10000

#
# Enhanced exception message information
#
# By default, exception messages should not include potentially sensitive
# information such as file names, host names, or port numbers. This property
# accepts one or more comma separated values, each of which represents a
# category of enhanced exception message information to enable. Values are
# case-insensitive. Leading and trailing whitespaces, surrounding each value,
# are ignored. Unknown values are ignored.
#
# NOTE: Use caution before setting this property. Setting this property
# exposes sensitive information in Exceptions, which could, for example,
# propagate to untrusted code or be emitted in stack traces that are
# inadvertently disclosed and made accessible over a public network.
#
# The categories are:
#
#  hostInfo - IOExceptions thrown by java.net.Socket and the socket types in the
#             java.nio.channels package will contain enhanced exception
#             message information
#
#  jar      - enables more detailed information in the IOExceptions thrown
#             by classes in the java.util.jar package
#
# The property setting in this file can be overridden by a system property of
# the same name, with the same syntax and possible values.
#
#jdk.includeInExceptions=hostInfo,jar

#
# Disabled mechanisms for the Simple Authentication and Security Layer (SASL)
#
# Disabled mechanisms will not be negotiated by both SASL clients and servers.
# These mechanisms will be ignored if they are specified in the "mechanisms"
# argument of "Sasl.createSaslClient" or the "mechanism" argument of
# "Sasl.createSaslServer".
#
# The value of this property is a comma-separated list of SASL mechanisms.
# The mechanisms are case-sensitive. Whitespaces around the commas are ignored.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
#
# Example:
#   jdk.sasl.disabledMechanisms=PLAIN, CRAM-MD5, DIGEST-MD5
jdk.sasl.disabledMechanisms=

#
# Policies for distrusting Certificate Authorities (CAs).
#
# This is a comma separated value of one or more case-sensitive strings, each
# of which represents a policy for determining if a CA should be distrusted.
# The supported values are:
#
#   SYMANTEC_TLS : Distrust TLS Server certificates anchored by a Symantec
#   root CA and issued after April 16, 2019 unless issued by one of the
#   following subordinate CAs which have a later distrust date:
#     1. Apple IST CA 2 - G1, SHA-256 fingerprint:
#        AC2B922ECFD5E01711772FEA8ED372DE9D1E2245FCE3F57A9CDBEC77296A424B
#        Distrust after December 31, 2019.
#     2. Apple IST CA 8 - G1, SHA-256 fingerprint:
#        A4FE7C7F15155F3F0AEF7AAA83CF6E06DEB97CA3F909DF920AC1490882D488ED
#        Distrust after December 31, 2019.
#
# Leading and trailing whitespace surrounding each value are ignored.
# Unknown values are ignored. If the property is commented out or set to the
# empty String, no policies are enforced.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be supported by other SE implementations. Also, this
# property does not override other security properties which can restrict
# certificates such as jdk.tls.disabledAlgorithms or
# jdk.certpath.disabledAlgorithms; those restrictions are still enforced even
# if this property is not enabled.
#
jdk.security.caDistrustPolicies=SYMANTEC_TLS

#
# FilePermission path canonicalization
#
# This security property dictates how the path argument is processed and stored
# while constructing a FilePermission object. If the value is set to true, the
# path argument is canonicalized and FilePermission methods (such as implies,
# equals, and hashCode) are implemented based on this canonicalized result.
# Otherwise, the path argument is not canonicalized and FilePermission methods are
# implemented based on the original input. See the implementation note of the
# FilePermission class for more details.
#
# If a system property of the same name is also specified, it supersedes the
# security property value defined here.
#
# The default value for this property is false.
#
jdk.io.permissionsUseCanonicalPath=false


#
# Policies for the proxy_impersonator Kerberos ccache configuration entry
#
# The proxy_impersonator ccache configuration entry indicates that the ccache
# is a synthetic delegated credential for use with S4U2Proxy by an intermediate
# server. The ccache file should also contain the TGT of this server and
# an evidence ticket from the default principal of the ccache to this server.
#
# This security property determines how Java uses this configuration entry.
# There are 3 possible values:
#
#  no-impersonate     - Ignore this configuration entry, and always act as
#                       the owner of the TGT (if it exists).
#
#  try-impersonate    - Try impersonation when this configuration entry exists.
#                       If no matching TGT or evidence ticket is found,
#                       fallback to no-impersonate.
#
#  always-impersonate - Always impersonate when this configuration entry exists.
#                       If no matching TGT or evidence ticket is found,
#                       no initial credential is read from the ccache.
#
# The default value is "always-impersonate".
#
# If a system property of the same name is also specified, it supersedes the
# security property value defined here.
#
#jdk.security.krb5.default.initiate.credential=always-impersonate

#
# Trust Anchor Certificates - CA Basic Constraint check
#
# X.509 v3 certificates used as Trust Anchors (to validate signed code or TLS
# connections) must have the cA Basic Constraint field set to 'true'. Also, if
# they include a Key Usage extension, the keyCertSign bit must be set. These
# checks, enabled by default, can be disabled for backward-compatibility
# purposes with the jdk.security.allowNonCaAnchor System and Security
# properties. In the case that both properties are simultaneously set, the
# System value prevails. The default value of the property is "false".
#
#jdk.security.allowNonCaAnchor=true

#
# The default Character set name (java.nio.charset.Charset.forName())
# for converting TLS ALPN values between byte arrays and Strings.
# Prior versions of the JDK may use UTF-8 as the default charset. If
# you experience interoperability issues, setting this property to UTF-8
# may help.
#
# jdk.tls.alpnCharset=UTF-8
jdk.tls.alpnCharset=ISO_8859_1

#
# JNDI Object Factories Filter
#
# This filter is used by the JNDI runtime to control the set of object factory classes
# which will be allowed to instantiate objects from object references returned by
# naming/directory systems. The factory class named by the reference instance will be
# matched against this filter. The filter property supports pattern-based filter syntax
# with the same format as jdk.serialFilter.
#
# Each pattern is matched against the factory class name to allow or disallow it's
# instantiation. The access to a factory class is allowed unless the filter returns
# REJECTED.
#
# Note: This property is currently used by the JDK Reference implementation.
# It is not guaranteed to be examined and used by other implementations.
#
# If the system property jdk.jndi.object.factoriesFilter is also specified, it supersedes
# the security property value defined here. The default value of the property is "*".
#
# The default pattern value allows any object factory class specified by the reference
# instance to recreate the referenced object.
#jdk.jndi.object.factoriesFilter=*
