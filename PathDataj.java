import java.util.ArrayList;

public class PathDataj {

	public static ArrayList[] pathMatrix;
	public static ArrayList[] pathMatrixTemp;	
	public static void Construct(int m)
	{
		pathMatrix = new ArrayList[m];
		pathMatrixTemp = new ArrayList[m]; 
		for(int i = 0 ; i < m ; ++i)
		{
			pathMatrix[i] = new ArrayList<double[]>();
			pathMatrixTemp[i] = new ArrayList<double[]>();
		}
	}

	public static void AddPathQuad(int user)
	{
		//pathMatrix[user] = new ArrayList<Double>(); 
		//pathMatrix[user].add;
		double pathQuad[] = new double[6];
		for(int col =0; col<4; col++)
		{
			pathQuad[col] = 0.0;
		}
		pathMatrix[user].add(pathQuad);
	}

	public static void Store_F(int user, int col, double f)
	{
		//pathMatrix[row].get(col)[1] = f;	
		double[] quad = (double[]) pathMatrix[user].get(col);
		quad[0] = f; //dimension 0 is flow
		pathMatrix[user].set(col, quad);
	}

	public static void Store_D(int user, int col, double d)
	{
		//pathMatrix[row][col][2] = d;	
		double[] quad = (double[]) pathMatrix[user].get(col);
		quad[1] = d;  //dimension 1 is delay
		pathMatrix[user].set(col, quad);		
	}	

	public static void Store_beta(int user, int col, double beta)
	{
		//pathMatrix[row][col][3] = beta;	
		double[] quad = (double[]) pathMatrix[user].get(col);
		quad[2] = beta;  //dimension 2 is beta
		pathMatrix[user].set(col, quad);		
	}

	public static void Store_lambda(int user, int col, double lambda)
	{
		//pathMatrix[row][col][3] = beta;	
		double[] quad = (double[]) pathMatrix[user].get(col);
		quad[5] = lambda;  //dimension 5 is lambda
		pathMatrix[user].set(col, quad);		
	}	

	public static void Store_alpha(int user, int col, double alpha)
	{
		//pathMatrix[row][col][4] = alpha;	
		double[] quad = (double[]) pathMatrix[user].get(col);
		quad[3] = alpha; //dimension 3 is alpha
		pathMatrix[user].set(col, quad);				
	}

	public static void Store_cost(int user, int col, double cost)
	{
		//pathMatrix[row][col][4] = alpha;	
		double[] quad = (double[]) pathMatrix[user].get(col);
		quad[4] = cost; //dimension 4 is cost
		pathMatrix[user].set(col, quad);				
	}

	public static double Return_F(int user, int col)
	{
		//return pathMatrix[row][col][1];
		double[] quad = (double[]) pathMatrix[user].get(col);
		return quad[0];		
	}

	public static double Return_D(int user, int col)
	{
		//return pathMatrix[row][col][2];
		double[] quad = (double[]) pathMatrix[user].get(col);
		return quad[1];			
	}	

	public static double Return_beta(int user, int col)
	{
		//return pathMatrix[row][col][3];
		double[] quad = (double[]) pathMatrix[user].get(col);
		return quad[2];			
	}

	public static double Return_lambda(int user, int col)
	{
		//return pathMatrix[row][col][5];
		double[] quad = (double[]) pathMatrix[user].get(col);
		return quad[5];			
	}	


	public static double Return_alpha(int user, int col)
	{
		//return pathMatrix[row][col][4];
		double[] quad = (double[]) pathMatrix[user].get(col);
		return quad[3];			
	}	

	public static double Return_cost(int user, int col)
	{
		//return pathMatrix[row][col][1];
		double[] quad = (double[]) pathMatrix[user].get(col);
		return quad[4];		
	}	

	public static int NumberOfPaths(int user)
	{
		int nofp =  pathMatrix[user].size();
		return nofp;			
	}		

	public static void ResetF(int m)
	{
		for(int i = 0; i < m; i++) //for each user
		{
			for(int col=0; col< pathMatrix[i].size(); col++) //for each path of user i
			{
				double[] quad = (double[]) pathMatrix[i].get(col);
				quad[0] = 0.0;
				pathMatrix[i].set(col, quad);		
			}
		}
	}
	public static void ResetD(int m)
	{
		for(int i = 0; i < m; i++) //for each user
		{
			for(int col=0; col< pathMatrix[i].size(); col++) //for each path of user i
			{
				double[] quad = (double[]) pathMatrix[i].get(col);
				quad[1] = 0.0;
				pathMatrix[i].set(col, quad);		
			}
		}		
	}
	public static void ResetBeta(int m)
	{
		for(int i = 0; i < m; i++) //for each user
		{
			for(int col=0; col< pathMatrix[i].size(); col++) //for each path of user i
			{
				double[] quad = (double[]) pathMatrix[i].get(col);
				quad[2] = 0.0;
				pathMatrix[i].set(col, quad);		
			}
		}
	}

	public static void ResetAlpha(int m)
	{
		for(int i = 0; i < m; i++) //for each user
		{
			for(int col=0; col< pathMatrix[i].size(); col++) //for each path of user i
			{
				double[] quad = (double[]) pathMatrix[i].get(col);
				quad[3] = 0.0;
				pathMatrix[i].set(col, quad);		
			}
		}
	}

	public static int ReturnSize(int user)
	{
		return pathMatrix[user].size();
	}

	public static double Return_alpha_Temp(int user, int col)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		return quad[3];			
	}		

	public static double Return_F_Temp(int user, int col)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		return quad[0];		
	}	

	public static void Store_F_Temp(int user, int col, double f)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		quad[0] = f;
		pathMatrixTemp[user].set(col, quad);
	}

	public static double Return_D_Temp(int user, int col)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		return quad[1];			
	}	

	public static void Store_D_Temp(int user, int col, double d)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		quad[1] = d;
		pathMatrixTemp[user].set(col, quad);		
	}		

	public static double Return_beta_Temp(int user, int col)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		return quad[2];			
	}	

	public static void Store_beta_Temp(int user, int col, double beta)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		quad[2] = beta;
		pathMatrixTemp[user].set(col, quad);		
	}	

	public static void Store_alpha_Temp(int user, int col, double alpha)
	{
		double[] quad = (double[]) pathMatrixTemp[user].get(col);
		quad[3] = alpha;
		pathMatrixTemp[user].set(col, quad);				
	}	


}
