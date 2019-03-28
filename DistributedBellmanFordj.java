import java.util.ArrayList;

public class DistributedBellmanFordj 
{
	public static int MAX_MININUM_PATHS = 10; 
	public static int COSTBOUND = 1;
	public static int BIGNUMBER = 1000000000;
	public static int [] cost_vertex;
	public static int [][] pre_vertex;
	public static double [][] delay_vertex;
	public static boolean [] delay_change_check_vertex;
	public static ArrayList<Integer> shortestPath = null;
	public static ArrayList[] edgeAdjList;

	public static void Initialize(int n)
	{
		cost_vertex = new int [n];
		pre_vertex = new int [n][COSTBOUND+1];
		delay_vertex = new double [n][COSTBOUND+1];
		delay_change_check_vertex = new boolean [n];
		shortestPath = new ArrayList<Integer>();
		edgeAdjList = new ArrayList[n];
		/*for(int i = 0; i<n; i++){
			
		}*/

		for(int i = 0 ; i < n ; ++i)
		{
			cost_vertex[i] = BIGNUMBER;
			delay_change_check_vertex[i] = false;
			for(int j = 0 ;j <= COSTBOUND ; ++j)
			{
				delay_vertex[i][j] = BIGNUMBER;
				pre_vertex[i][j] = -1;
			}
		}
	}

	public static boolean Delay_Change(int n)
	{
		boolean CHANGE = false;
		for(int i = 0 ; i < n ; ++i)
		{	
			CHANGE = CHANGE | delay_change_check_vertex[i];			
		}
		return CHANGE;

	}

	// I deleted the parameter edgeAdjList and created it inside this method.
	// Also, I added following lines from ****************** to **********************.
	public static ArrayList<Integer> ShortestDelayPaths(int n, int m, int source, int sink, int [][] costMatrix, double [][]delayMatrix)
	{
		Initialize(n);

		//************************


		for(int i = 0; i < n ; i++) {
			{    
				edgeAdjList[i]=new ArrayList<Integer>();
				for(int j = 0 ; j < n ; j++) {
				
				if( (i != j) && (delayMatrix[i][j] >= 0.0) ) {
					edgeAdjList[i].add(j);
				}
			}
			}
			}
			

		//************************

		//cost_vertex[source] = 0;
		for(int j = 0 ; j <= COSTBOUND ; ++j)
			//for(int j = 0 ; j <= 10000 ; ++j)
			delay_vertex[source][j] = 0;

		do 
		{
			for(int i = 0 ; i < n ; ++i)
				delay_change_check_vertex[i] = false;
			for(int i = 0; i < n ; ++i)
			{
				for(int j = 0 ; j < edgeAdjList[i].size() ; ++j)
				{
					int cur_node = i;
					int next_node = (Integer)edgeAdjList[i].get(j);
					//if(cur_node == 24 && next_node == 4 && cost_matrix[cur_node][next_node]>1) //added code
					//System.out.println("BellmanFord: cost for link 24 4 "+cost_matrix[cur_node][next_node]); // added code

					for(int c = 0 ; c <= COSTBOUND ; ++c)
					{
						int cost = c+costMatrix[cur_node][next_node];

						if(cost <= COSTBOUND)
						{
							if(delay_vertex[next_node][cost] > delay_vertex[cur_node][c]+delayMatrix[cur_node][next_node])
							{
								delay_change_check_vertex[next_node] = true;
								delay_vertex[next_node][cost] = delay_vertex[cur_node][c]+delayMatrix[cur_node][next_node];
								pre_vertex[next_node][cost] = cur_node;
							}
						}
					}
				}
			}		
		}while(Delay_Change(n));

		int min_delay_index = 0;
		for(int j = 1 ; j <= COSTBOUND ; ++j)
		{
			if(delay_vertex[sink][min_delay_index] > delay_vertex[sink][j])
			{
				min_delay_index = j;
			}
		}

		int temp = 0;
		int cur_node = sink;
		int pre_node = pre_vertex[cur_node][min_delay_index];

		while(pre_node != -1)
		{
			temp = pre_node;
			shortestPath.add(cur_node);
			pre_node = pre_vertex[pre_node][min_delay_index-costMatrix[pre_node][cur_node]];
			cur_node = temp;
		}

		if(shortestPath.size()> 0)
			shortestPath.add(cur_node);

		int size = shortestPath.size();
		for(int i = 0 ; i < size/2 ; ++i)
		{
			int t1 = shortestPath.get(i);
			int t2 = shortestPath.get(size-i-1);
			shortestPath.remove(i);
			shortestPath.add(i,t2);
			shortestPath.remove(size-i-1);
			shortestPath.add(size-i-1,t1);			
		}

		if(shortestPath.isEmpty())
			return null;
		else
		{		
			double delaytemp = 0.0;
			for(int i = 0 ; i < shortestPath.size()-1 ; ++i)
			{
				delaytemp += delayMatrix[shortestPath.get(i)][shortestPath.get(i+1)];
			}
			if(delaytemp < BIGNUMBER)
			{
				//if(cost_matrix[24][4]>1) //new code
				//{
				//System.out.println("Bellman-Ford S P "+shortestPath); //new code
				//}
				return shortestPath;
			}
			else
				//return null;
				//shortestPath.clear();
				return null;
		}
	}
}
