import entity.BPARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * BEncryption
 * @author Universe
 * bpk, ETP -> CT_TP
 */
public class BEncryption
{
	public static Element f(Element x, Element[] V)
	{
		Element eRet = x.getField().newElement(1).duplicate();
		for (Element v : V)
			eRet = eRet.mul(x.sub(v)).duplicate();
		return eRet;
	}
	
	public static BPARS bEncryption(BPARS bPars)
    {
		Element C[][] = new Element[bPars.get_n()][5];
		
		for (int i = 0; i < bPars.get_n(); ++i)
		{
			/* three random numbers */
			Element s = bPars.get_Zp_star().newRandomElement().duplicate().getImmutable();
			Element s_1 = bPars.get_Zp_star().newRandomElement().duplicate().getImmutable();
			Element s_2 = bPars.get_Zp_star().newRandomElement().duplicate().getImmutable();
			
			/* computing */
			//Element V_i = BPARS.H2(BPARS.e(bPars.get_ETP()[i], bPars.get_g()).powZn(s), bPars);
			C[i][0] = bPars.get_g1().powZn(s);
			C[i][1] = bPars.get_v1().powZn(s.sub(s_1));
			C[i][2] = bPars.get_v2().powZn(s_1);
			C[i][3] = bPars.get_v3().powZn(s.sub(s_2));
			C[i][4] = bPars.get_v4().powZn(s_2);
		}
		
		bPars.set_CT_TP(C);
		return bPars;
    }
}