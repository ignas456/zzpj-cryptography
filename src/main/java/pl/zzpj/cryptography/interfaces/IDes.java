package pl.zzpj.cryptography.interfaces;

import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;

public interface IDes {
	public void setKey(byte[] key) throws InvalidKeyException;
	public byte[] encrypt(byte[] source);
	public byte[] decrypt(byte[] source);
}
