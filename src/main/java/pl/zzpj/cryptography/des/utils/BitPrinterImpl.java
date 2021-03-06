package pl.zzpj.cryptography.des.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.BitPrinter;

@Service
public class BitPrinterImpl implements BitPrinter {

	private BitJuggler bitJuggler;
	
	@Autowired
	public BitPrinterImpl(BitJuggler bitJuggler) {
		this.bitJuggler = bitJuggler;
	}
	
	public String printBits(byte[] source) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < source.length * 8; i++) {
			if (i % 8 == 0 && i != 0) {
				sb.append(" ");
			}
			sb.append(bitJuggler.getBit(source, i));
		}

		return sb.toString();
	}
	
}
