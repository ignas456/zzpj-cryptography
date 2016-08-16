package pl.zzpj.cryptography.interfaces;

public interface IFFunction {
	public void calculateKSubKeys(byte key[]);
	public byte[] perform(byte[] input, int roundNumber);
}
