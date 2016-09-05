package pl.zzpj.cryptography.des.algorithm.interfaces;

import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;

public interface Des {
	public void setKey(byte[] key) throws InvalidKeyException;
	public byte[] encrypt(byte[] source);
	public byte[] decrypt(byte[] source);
}
