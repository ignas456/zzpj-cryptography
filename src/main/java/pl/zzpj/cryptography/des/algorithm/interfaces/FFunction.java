package pl.zzpj.cryptography.des.algorithm.interfaces;

public interface FFunction {
	public void calculateKSubKeys(byte key[]);
	public byte[] perform(byte[] input, int roundNumber);
}
