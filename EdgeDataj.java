
public class EdgeDataj 
{
	public static double[][][] edgeMatrix;
	public static double[][][] edgeMatrixTemp;
	//public static double[][][] edgeMatrixInit;

	public static void Construct(int n)
	{
		edgeMatrix = new double[n][n][7];
		edgeMatrixTemp = new double[n][n][4]; 
		// edgeMatrixInit = new double[n][n][4];
	}

	public static void Store_BF(int row, int col, double bf)
	{
		edgeMatrix[row][col][0] = bf;		
	}

	public static void Store_F(int row, int col, double f)
	{
		edgeMatrix[row][col][1] = f;		
	}

	public static void Store_D(int row, int col, double d)
	{
		edgeMatrix[row][col][2] = d;		
	}

	public static void Store_G(int row, int col, double g)
	{
		edgeMatrix[row][col][3] = g;		
	}	

	public static void Store_Ae(int row, int col, double ae)
	{
		edgeMatrix[row][col][4] = ae;		
	}

	public static void Store_Be(int row, int col, double be)
	{
		edgeMatrix[row][col][5] = be;		
	}

	public static void Store_PhiPrime(int row, int col, double pp)
	{
		edgeMatrix[row][col][6] = pp;		
	}	


	public static void Add_BF(int row, int col, double bf)
	{
		edgeMatrix[row][col][0] = edgeMatrix[row][col][0] + bf;
	}

	public static void Add_F(int row, int col, double f)
	{
		edgeMatrix[row][col][1] = edgeMatrix[row][col][1] + f;
	}

	public static void Add_G(int row, int col, double g)
	{
		edgeMatrix[row][col][3] = edgeMatrix[row][col][3] + g;
	}	

	public static void Add_Ae(int row, int col, double ae)
	{
		edgeMatrix[row][col][4] = edgeMatrix[row][col][4] + ae;
	}

	public static void Add_Be(int row, int col, double be)
	{
		edgeMatrix[row][col][5] = edgeMatrix[row][col][5] + be;
	}	

	public static void Subt_F(int row, int col, double f)
	{
		edgeMatrix[row][col][1] = edgeMatrix[row][col][1] - f;
	}

	public static double Return_BF(int row, int col)
	{
		return edgeMatrix[row][col][0];
	}

	public static double Return_F(int row, int col)
	{
		return edgeMatrix[row][col][1];
	}

	public static double Return_D(int row, int col)
	{
		return edgeMatrix[row][col][2];
	}

	public static double Return_G(int row, int col)
	{
		return edgeMatrix[row][col][3];
	}

	public static double Return_Ae(int row, int col)
	{
		return edgeMatrix[row][col][4];
	}

	public static double Return_Be(int row, int col)
	{
		return edgeMatrix[row][col][5];
	}

	public static double Return_PhiPrime(int row, int col)
	{
		return edgeMatrix[row][col][3];
	}	

	public static void Reset_F(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrix[i][j][1] = 0.0;	
			}
		}
	}

	public static void ResetMult_G(int n)  // in this case reset to a value of 1 because this is a mult penalty and 1=no penalty
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrix[i][j][3] = 1;	
			}
		}
	}		

	public static void Reset_Ae(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrix[i][j][4] = 0.0;	
			}
		}
	}	

	public static void Reset_Be(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrix[i][j][5] = 0.0;	
			}
		}
	}		

	public static void Reset_D(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrix[i][j][2] = 0.0;	
			}
		}
	}	

	public static void ResetD_Temp(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrixTemp[i][j][2] = 0.0;	
			}
		}
	}	

	public static void ResetF_Temp(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrixTemp[i][j][1] = 0.0;	
			}
		}
	}	

	public static void ResetF(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrix[i][j][1] = 0.0;	
			}
		}
	}

	public static void ResetPhiPrime(int n)
	{
		for(int i = 0;  i < n ; i++)
		{
			for(int j = 0;  j < n ; j++)
			{
				edgeMatrix[i][j][6] = 0.0;	
			}
		}
	}



	public static void Add_F_Temp(int row, int col, double f)
	{
		edgeMatrixTemp[row][col][1] = edgeMatrixTemp[row][col][1] + f;
	}

	public static double Return_F_Temp(int row, int col)
	{
		return edgeMatrixTemp[row][col][1];
	}	

	public static void Store_D_Temp(int row, int col, double d)
	{
		edgeMatrixTemp[row][col][2] = d;		
	}	

	public static double Return_D_Temp(int row, int col)
	{
		return edgeMatrixTemp[row][col][2];
	}

	public static void Store_PhiPrime_Temp(int row, int col, double p)
	{
		edgeMatrixTemp[row][col][3] = p;		
	}	


}

