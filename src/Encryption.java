import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * Encryption
 * @author Universe
 * TPs, ek_ID_i -> CT
 */
public class Encryption
{
	public static Element f(Element x, Element[] V)
	{
		Element eRet = x.getField().newElement(1).duplicate();
		for (Element v : V)
			eRet = eRet.mul(x.sub(v)).duplicate();
		return eRet;
	}
	
	public static PARS encryption(PARS pars)
    {
		Element C[] = new Element[5], C_pi[] = new Element[5], a[] = new Element[pars.get_n()];
		
		/* four random numbers */
		Element s = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
		Element s_1 = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
		Element s_2 = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
		Element alpha = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
		
		/* computing */
		//Element V_i = PARS.H2(pars.get_Omega().powZn(s), pars).duplicate().getImmutable();
		C[0] = pars.get_g3().powZn(s);
		C[1] = pars.get_v1().powZn(s.sub(s_1));
		C[2] = pars.get_v2().powZn(s_1);
		C[3] = pars.get_v3().powZn(s.sub(s_2));
		C[4] = pars.get_v4().powZn(s_2);
		
		/* alpha */
		for (int i = 0; i < pars.get_n(); ++i)
			a[i] = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
		pars.set_a(a);
		
		/* computing */
		C_pi[0] = pars.get_g1().powZn(alpha);
		C_pi[1] = pars.get_ek_ID_i()[1].powZn(pars.get_ek_ID_i()[0]).add(pars.get_T().powZn(alpha));
		C_pi[2] = PARS.e(pars.get_T(), pars.get_S()).powZn(alpha);
		C_pi[3] = PARS.H3(PARS.combine(C, a, new Element[] {C_pi[0], C_pi[1], C_pi[2]}), pars);
		try
		{
			C_pi[4] = pars.get_ek_ID_i()[1].add(pars.get_ek_ID_i()[0]); // ek_ID_i[0, 1] = x_i, Z_i 
		}
		catch (Throwable e)
		{
			C_pi[4] = pars.get_ek_ID_i()[1];
		}
		
		/* set */
		pars.set_C(C);
		pars.set_C_pi(C_pi);
		return pars;
    }
}