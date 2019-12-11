# Implementation of AES CBC, AES CTR, Hash(SHA-256, SHA-512, SHA3-256), RSA, DSA

---
The code implements the following encryption/decryption/signing methods.
## AES
### AES Cipher Block Chaining(CBC)
In CBC mode, each block of plaintext is XORed with the previous ciphertext block before being encrypted. This way, each ciphertext block depends on all plaintext blocks processed up to that point. To make each message unique, an initialization vector must be used in the first block

Draw back: <br>
  >*Sequential, cannot be paralleized <br>
  *Message must be padded <br>
 

### AES Counter(CTR)
Counter mode turns a block cipher into a stream cipher. It generates the next keystream block by encrypting successive values of a "counter". The counter can be any function which produces a sequence which is guaranteed not to repeat for a long time, although an actual increment-by-one counter is the simplest and most popular.<br>
CTR mode has similar characteristics to OFB, but also allows a random access property during decryption. CTR mode is well suited to operate on `a multi-processor machine where blocks can be encrypted in parallel`. Furthermore, it does not suffer from the short-cycle problem that can affect OFB.
<br>Property:<br>
>- ciphertext can have the same length as the plaintext; <br>
>- if the last plaintext block is incomplete, we just truncate the last
cipherblock and transmit it; <br>
>- Hardware and software efficiency: multiple blocks can be encrypted or
decrypted in parallel; <br>
>- Preprocessing: encryption can be done in advance; the rest is only XOR; <br>
>- Random access: ith block of plaintext or ciphertext can be processed
independently of others; <br>
>- Security: at least as secure as other modes (i.e., CPA-secure);  <br>
>- Simplicity: doesn’t require decryption or decryption key scheduling; <br>

Note: counter can not be resued!<br>

AES Summary:<br>
---
AES is the current block cipher standard - it offers strong security and fast performance<br>

- Five encryption modes are specified as part of the standard

-  ECB mode is not for secure encryption

-  any other encryption mode achieves sufficient security

-  use one of these modes for encryption even if the message is a single
block


-  Strong randomness is required for cryptographic purposes
 

## HASH
A hash function h is an efficiently-computable function that maps an input x
of an arbitrary length to a (short) fixed-length output h(x).<br>

Desired properties:
- it is computationally hard to invert h(x)
- it is computationally hard to find collisions in h

Usage:
- Password
- Digital Signature
- Intrusion detection and forensics


### SHA-256
### SHA-512
### SHA3-256
SHA3 is the latest. 
REF: https://en.wikipedia.org/wiki/SHA-3;<br>

Although part of the same series of standards, SHA-3 is internally different from the MD5-like structure of SHA-1 and SHA-2.<br>
NIST does not currently plan to withdraw SHA-2 or remove it from the revised Secure Hash Standard. The purpose of SHA-3 is that it can be directly substituted for SHA-2 in current applications if necessary, and to significantly improve the robustness of NIST's overall hash algorithm toolkit.<br>



## RSA
Encrypt and decrypt the files above with PKCS #1 v2 padding<br>
RSA is the most commonly used public-key encryption algorithm invented
by Rivest, Shamir, and Adleman in 1978; relies on the fact that factoring large numbers is hard; given only n, it is hard to find p or q, which are used as a trapdoor.<br>

- complicated factoring algorithms that run in sub-exponential (but
super-polynomial) time in the length of n exist
- a 768-bit modulus was factored in 2009
- 1024-bit moduli could be factored very soon
- moduli of length 2048 are expected to be secure until 2030

>Plain (or textbook) RSA is not close to secure<br>
>Padded RSA, we can randomize ciphertext by padding each m with random bits<br>
>PKCS #1 v1.5 was a widely used standard for padded RSA<br>
>PKCS #1 v2.0 utilizes OAEP (Optimal Asymmetric Encryption Padding)<br>
>>the newer version mitigates some attacks on v1.5 and is known to be
CCA-secure<br>

`In RSA, decryption is significantly slower than encryption, with key
generation being the slowest`




## Digital Signature Algorithm(DSA)
2048/3072-bit DSA key
Its design was influenced by prior ElGamal and Schnorr signature
schemes<br>
It assumes the difficulty of the discrete logarithm problem<br>
No formal security proof exists<br>



Project Related
===
Tested on small file: 1kb, large file: 1MB;<br>
Key size in AES: 128 bit or 256 bit;<br>
## Observation:
RSA asymmetric method takes significant longer time
to generate the key, encrypt and decrypt the contents. <br>

RSA shows longer time to decrypt than
encrypt specially for large files. <br>

CTR with longer key length has the best performance specially when
dealing with large files. <br>

Due to the natural property of RSA, it requires the input bytes to be small in
order to achieve relatively enough safety thus requires dividing the input before encrypting.<br>


--HASH<br>
The SHA3-256 has the best performance in both big file process and small file process. The advantage
becomes more obvious when the file size grows . The SHA 256 takes the noticeable longer time to hash
big files.<br>

--DSA<br>
The key length has impact on the running time of signing and signature verification but
the file size has more essential impact on it. But still the growth is slower than the growth of file size.
