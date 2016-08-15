package pl.zzpj.cryptography.des.algorithm;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.zzpj.cryptography.des.utils.ArrayUtils;
import pl.zzpj.cryptography.des.utils.BitJuggler;
import pl.zzpj.cryptography.des.utils.MatrixUtils;
import pl.zzpj.cryptography.interfaces.IDes;
import pl.zzpj.cryptography.interfaces.IFFunction;

@Component
public class DES implements IDes {
  
  private IFFunction fFunction;
  
  @Autowired
  public DES(IFFunction fFunction) {
    this.fFunction = fFunction;
  }

  public byte[] encrypt(byte[] src, boolean isDecrypt) {
    byte[][] srcIn2D = ArrayUtils.transformToTwoDimensionsArray(src);
    
    byte[][] tmp = new byte[srcIn2D.length][8];
    
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = this.encrypt8ByteBlock(srcIn2D[i], isDecrypt);
    }
    
    if (isDecrypt) {
      int counter = 0;
      for(int i = tmp.length - 1; i >= 0; i--) {
        for (int j = tmp[i].length - 1; j >= 0; j--) {
          if(tmp[i][j] == 0) {
            counter++;
          } else {
            break;
          }
        }
      }
      if (counter > 7) counter = 0;
      tmp[tmp.length - 1] = Arrays.copyOfRange(tmp[tmp.length - 1], 0, 8 - counter);
    }

    return ArrayUtils.transformToOneDimensionArray(tmp);
  }
  
  
  private byte[] encrypt8ByteBlock(byte[] block, boolean isDecrypt) {
    byte[] permutedOrginalBlock = MatrixUtils.permute(block, DESPermutationTables.IP);
    
    byte[] left = BitJuggler.getBits(permutedOrginalBlock, 0, 32);
    byte[] right = BitJuggler.getBits(permutedOrginalBlock, 32, 32);
    
    for (int i = 0; i < 16; i++) {
      byte[] tmpR = right;
      
      if (isDecrypt) {
        right = fFunction.perform(right, 15 - i);
      } else {
        right = fFunction.perform(right, i);
      }
      
      right = BitJuggler.xorArrays(left, right);
      left = tmpR;
      
    }
    
    byte[] result = BitJuggler.concatBitSeries(right, 32, left, 32);    
    result = MatrixUtils.permute(result, DESPermutationTables.FP);
    
    return result;
  }
  
  
}
